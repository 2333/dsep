package com.dsep.service.publicity.feedback.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dsep.dao.base.AttachmentDao;
import com.dsep.dao.dsepmeta.databackup.CenterDataBackupDao;
import com.dsep.dao.dsepmeta.publicity.feedback.FeedbackResponseDao;
import com.dsep.entity.Attachment;
import com.dsep.entity.dsepmeta.DataModifyHistory;
import com.dsep.entity.dsepmeta.FeedbackResponse;
import com.dsep.entity.enumeration.datamodify.ModifySource;
import com.dsep.entity.enumeration.datamodify.ModifyType;
import com.dsep.entity.enumeration.feedback.CenterAdvice;
import com.dsep.entity.enumeration.feedback.FeedbackResponseStatus;
import com.dsep.entity.enumeration.feedback.ResponseType;
import com.dsep.service.datamodify.DataModifyService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.publicity.feedback.FeedbackResponseService;
import com.dsep.util.Configurations;
import com.dsep.util.DateProcess;
import com.dsep.util.GUID;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.feedback.FeedbackResponseVM;
import com.meta.entity.MetaEntity;
import com.meta.service.MetaEntityService;

public class FeedbackResponseServiceImpl implements FeedbackResponseService{

	private FeedbackResponseDao feedbackResponseDao;
	private AttachmentDao attachmentDao;
	private DataModifyService dataModifyService;
	private CenterDataBackupDao centerDataBackupDao;
	private MetaEntityService metaEntityService;
	private DMCollectService collectService;
	
	
	public void setCollectService(DMCollectService collectService) {
		this.collectService = collectService;
	}

	public void setCenterDataBackupDao(CenterDataBackupDao centerDataBackupDao) {
		this.centerDataBackupDao = centerDataBackupDao;
	}
	
	

	public void setMetaEntityService(MetaEntityService metaEntityService) {
		this.metaEntityService = metaEntityService;
	}
	
	
	public void setDataModifyService(DataModifyService dataModifyService) {
		this.dataModifyService = dataModifyService;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public void setFeedbackResponseDao(FeedbackResponseDao feedbackResponseDao) {
		this.feedbackResponseDao = feedbackResponseDao;
	}

	@Override
	public PageVM<FeedbackResponseVM> getFeedbackResponseVM(int pageIndex,
			int pageSize, boolean desc, String orderPropName,
			FeedbackResponse conditionalResponse) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		List<FeedbackResponse> responseList = feedbackResponseDao.queryByCondition(conditionalResponse, pageIndex, pageSize, desc, orderPropName);
		List<FeedbackResponseVM> vmList = new ArrayList<FeedbackResponseVM>();
		for(FeedbackResponse response:responseList){
			FeedbackResponseVM newVm = new FeedbackResponseVM(response,this);
			vmList.add(newVm);
		}
		int count = feedbackResponseDao.getCountByCondition(conditionalResponse);
		return new PageVM<FeedbackResponseVM>(pageIndex,count,pageSize,vmList);
	}

	@Override
	public boolean deleteFeedbackResponse(String feedbackId) {
		// TODO Auto-generated method stub
		/*FeedbackResponse theResponse = feedbackResponseDao.get(feedbackId);
		theResponse.setFeedbackStatus(FeedbackResponseStatus.DELETE.getStatus());*/
		feedbackResponseDao.deleteByKey(feedbackId);
		return true;
	}

	@Override
	public int getSameProblemNumber(FeedbackResponse feedbackResponse) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		FeedbackResponse queryResponse = new FeedbackResponse();
		queryResponse.setProblemCollectItemId(feedbackResponse.getProblemCollectItemId());
		queryResponse.setFeedbackStatus(feedbackResponse.getFeedbackStatus());
		queryResponse.setFeedbackRoundId(feedbackResponse.getFeedbackRoundId());
		return feedbackResponseDao.getCountByCondition(queryResponse)-1;
	}

	@Override
	public PageVM<FeedbackResponseVM> getSameResponseVM(int pageIndex,
			int pageSize, boolean desc, String orderPropName,
			FeedbackResponse conditionalResponse) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		List<FeedbackResponse> responseList = feedbackResponseDao.queryByCondition(conditionalResponse, pageIndex, pageSize, desc, orderPropName);
		List<FeedbackResponseVM> vmList = new ArrayList<FeedbackResponseVM>();
		for(FeedbackResponse response:responseList){
			FeedbackResponseVM newVm = new FeedbackResponseVM(response);
			vmList.add(newVm);
		}
		int count = feedbackResponseDao.getCountByCondition(conditionalResponse);
		return new PageVM<FeedbackResponseVM>(pageIndex, count, pageSize, vmList);
	}

	@Override
	public FeedbackResponse getResponseById(String responseId) {
		// TODO Auto-generated method stub
		return feedbackResponseDao.get(responseId);
	}

	@Override
	public boolean saveResponse(String responseItemId, String responseType,
			String adviceValue, String responseAdvice) {
		// TODO Auto-generated method stub
		FeedbackResponse response = feedbackResponseDao.get(responseItemId);
		response.setAdviceValue(adviceValue);
		response.setResponseAdvice(responseAdvice);
		response.setModifyTime(new Date());
		if( (response.getProblemCollectEntityId() == null || 
			response.getProblemCollectEntityId().equals("")) &&
			!response.getResponseAdvice().equals("")){
			response.setResponseType(ResponseType.Write.getStatus());
		}
		else{
			response.setResponseType(responseType);
		}
		if( response.getResponseTime() == null){
			response.setResponseTime(new Date());
		}
		return true;
	}
	
	

	@Override
	public boolean deleteProveMaterial(String responseItemId, String attachmentId) {
		// TODO Auto-generated method stub
		FeedbackResponse response = feedbackResponseDao.get(responseItemId);
		response.setProveMaterial(null);
		response.setModifyTime(new Date());
		attachmentDao.deleteByKey(attachmentId);
		return true;
	}

	/**
	 * 设置反馈答复的答复时间
	 * @param theResponse
	 */
	private void setResponseTime(FeedbackResponse theResponse){
		if( theResponse.getResponseTime() == null){
			theResponse.setResponseTime(new Date());
		}
		theResponse.setModifyTime(new Date());
	}
	
	@Override
	public boolean uploadFile(String responseItemId, String proveMaterialId) {
		// TODO Auto-generated method stub
		Attachment attachment = attachmentDao.get(proveMaterialId);
		FeedbackResponse theResponse = feedbackResponseDao.get(responseItemId);
		theResponse.setProveMaterial(attachment);
		this.setResponseTime(theResponse);
		return true;
	}

	@Override
	public int testSave(FeedbackResponse saveResponse, String responseItemId) {
		// TODO Auto-generated method stub
		try {
			FeedbackResponse theResponse = feedbackResponseDao.get(responseItemId);
			if( theResponse.getResponseTime() == null){
				theResponse.setResponseTime(new Date());
			}
			theResponse.setModifyTime(new Date());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 是否所有的反馈项都给了处理意见
	 * @return
	 */
	public boolean isAllAdvice(String unitId,String feedbackType, String feedbackRoundId){
		int result = feedbackResponseDao.getNoAdviceNumber(unitId, feedbackType, feedbackRoundId);
		if( result == 0 )
			return true;
		else 
			return false;
	}

	@Override
	public boolean submitResponse(String unitId, String feedbackType, String feedbackRoundId) {
		// TODO Auto-generated method stub
		if( isAllAdvice(unitId,feedbackType, feedbackRoundId) ){
			return feedbackResponseDao.submitFeedbackResponse(unitId, feedbackType, feedbackRoundId);
		}
		else{
			return false;
		}
	}

	@Override
	public boolean saveDeleteResponse(String problemEntityItemId,
			String responseItemId, String responseType, String adviceValue,
			String responseAdvice, String feedbackRoundId) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		this.saveResponse(responseItemId, responseType, adviceValue, responseAdvice);
		int result = feedbackResponseDao.saveDeleteResponse(feedbackRoundId, problemEntityItemId);
		if( result < 0 )
			return false;
		else
			return true;
		/*FeedbackResponse queryResponse = new FeedbackResponse();
		queryResponse.setProblemCollectEntityId(problemEntityId);
		List<FeedbackResponse> responseList = feedbackResponseDao.queryByCondition(queryResponse);
		for(FeedbackResponse deleteResponse:responseList){
			if( !deleteResponse.getId().equals(responseItemId)){
				deleteResponse.setResponseType(ResponseType.DELETE.getStatus());
				this.setResponseTime(deleteResponse);
			}
		}
		return true;*/
	}
	
	/**
	 * 
	 * @param theHistory
	 * @param theResponse
	 */
	private void setDataModifyHistory(DataModifyHistory theHistory,FeedbackResponse theResponse,String userId){
		theHistory.setEntityId(theResponse.getProblemCollectEntityId());
		theHistory.setEntityName(theResponse.getProblemCollectEntityName());
		theHistory.setAttrEnsName(theResponse.getProblemCollectAttrId());
		theHistory.setAttrName(theResponse.getProblemCollectAttrName());
		theHistory.setAttrOriginalValue(theResponse.getProblemCollectAttrValue());
		theHistory.setAttrModifyValue(theResponse.getAdviceValue());
		theHistory.setId(GUID.get());
		theHistory.setEntityItemId(theResponse.getProblemCollectItemId());
		theHistory.setModifyTime(new Date());
		/*theHistory.setModifyType(ModifyType.CHANGE.getStatus());*/
		theHistory.setModifySource(ModifySource.FEEDBACK.getStatus());
		theHistory.setSeqNo(theResponse.getProblemSeqNo());
		theHistory.setDiscId(theResponse.getProblemDiscId());
		theHistory.setUnitId(theResponse.getProblemUnitId());
		theHistory.setOperateUserId(userId);
	}

	@Override
	public boolean agreeUnitAdvice(List<String> responseItemIdList,String userId) {
		// TODO Auto-generated method stub
		for(String responseItemId:responseItemIdList){
			FeedbackResponse theResponse = feedbackResponseDao.get(responseItemId);
			theResponse.setCenterAdvice(CenterAdvice.YES.getStatus());
			theResponse.setFeedbackStatus(FeedbackResponseStatus.FINISH.getStatus());
			DataModifyHistory theHistory = new DataModifyHistory();
			this.setDataModifyHistory(theHistory, theResponse,userId);
			if( theResponse.getResponseType().equals(ResponseType.CHANGE.getStatus())){
				theHistory.setModifyType(ModifyType.CHANGE.getStatus());
				dataModifyService.changeEntityData(theHistory);
			}
			else if( theResponse.getResponseType().equals(ResponseType.DELETE.getStatus())){
				theHistory.setModifyType(ModifyType.DELETE.getStatus());
	 			dataModifyService.deleteEntityData(theHistory);
				MetaEntity entity = metaEntityService.getById(theHistory.getEntityId());
				
				//备份要删除的数据
				centerDataBackupDao.backupDataWhenDelete(entity.getName(), 
					Configurations.getBackupTable(entity.getName(), "center_delete"),theHistory.getEntityItemId());
				
				//删除数据
				collectService.deleteRow(theHistory.getEntityId(), theHistory.getEntityItemId(), 
					theHistory.getSeqNo()+"",theHistory.getUnitId(),theHistory.getDiscId(), theHistory.getOperateUserId());
			}
		}
		return true;
	}

	@Override
	public boolean disagreeUnitAdvice(List<String> responseItemIdList,String userId) {
		// TODO Auto-generated method stub
		for(String responseItemId:responseItemIdList){
			FeedbackResponse theResponse = feedbackResponseDao.get(responseItemId);
			theResponse.setCenterAdvice(CenterAdvice.NO.getStatus());
			theResponse.setFeedbackStatus(FeedbackResponseStatus.FINISH.getStatus());
		}
		return true;
	}

	@Override
	public JqgridVM getSameItemJqgridVM(int pageIndex,
			int pageSize, boolean desc, String orderPropName,
			FeedbackResponse conditionalResponse) {
		// TODO Auto-generated method stub
		List<Map<String,String>> theList = feedbackResponseDao.getSameItemResponse(pageIndex,
				pageSize,desc,orderPropName,conditionalResponse);
		int totalCount = feedbackResponseDao.getSameItemResponseCount(conditionalResponse);
		JqgridVM theVm = new JqgridVM(pageIndex,totalCount,pageSize,theList);
		return theVm;
	}
}
