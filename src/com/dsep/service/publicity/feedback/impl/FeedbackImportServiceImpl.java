package com.dsep.service.publicity.feedback.impl;

import com.dsep.dao.dsepmeta.publicity.feedback.FeedbackImportDao;
import com.dsep.dao.dsepmeta.publicity.feedback.FeedbackResponseDao;
import com.dsep.entity.dsepmeta.FeedbackResponse;
import com.dsep.entity.dsepmeta.PublicityManagement;
import com.dsep.entity.enumeration.feedback.FeedbackType;
import com.dsep.service.publicity.feedback.FeedbackImportService;
import com.dsep.service.publicity.objection.PublicityService;

public class FeedbackImportServiceImpl implements FeedbackImportService{

	private PublicityService publicityService;
	private FeedbackImportDao feedbackImportDao;
	private FeedbackResponseDao feedbackResponseDao;
	
	public void setPublicityService(PublicityService publicityService) {
		this.publicityService = publicityService;
	}


	public void setFeedbackImportDao(FeedbackImportDao feedbackImportDao) {
		this.feedbackImportDao = feedbackImportDao;
	}



	public void setFeedbackResponseDao(FeedbackResponseDao feedbackResponseDao) {
		this.feedbackResponseDao = feedbackResponseDao;
	}


	/**
	 * 导入最近关闭的公示批次中的异议数据到反馈表中
	 * @param feedbackRoundId
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private int importRecentCloseRound(String feedbackRoundId) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		PublicityManagement recentRound = publicityService.getRecentCloseRound();
		return feedbackImportDao.originalObjectionImport(feedbackRoundId,recentRound.getId());
	}



	@Override
	public int importFeedbackDataSource(String feedbackRoundId,
			String feedbackType) throws IllegalArgumentException, IllegalAccessException {
		feedbackResponseDao.deleteFeedbackSource(feedbackRoundId,feedbackType);//导入前先执行删除语句
		if( feedbackType.equals(FeedbackType.OBJECTION.getStatus())){//异议数据
			return this.importRecentCloseRound(feedbackRoundId);
		}
		else if(feedbackType.equals(FeedbackType.DATAUNFORMAL.getStatus())){
			
		}
		else if(feedbackType.equals(FeedbackType.NOPROFMATERIAL.getStatus())){
			
		}
		else if(feedbackType.equals(FeedbackType.PUBNOTRIGHT.getStatus())){
			return feedbackImportDao.pubLibraryImport(feedbackRoundId);
		}
		else if(feedbackType.equals(FeedbackType.REPEATSUBMIT.getStatus())){
			return feedbackImportDao.similarityImport(feedbackRoundId);
		}
		else if(feedbackType.equals(FeedbackType.THESISQUOTE.getStatus())){
			
		}
		return -1;
	}

}
