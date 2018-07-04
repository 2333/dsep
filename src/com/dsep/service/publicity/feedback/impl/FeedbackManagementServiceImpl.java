package com.dsep.service.publicity.feedback.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.scheduling.annotation.Async;

import com.dsep.dao.dsepmeta.databackup.BackupManagementDao;
import com.dsep.dao.dsepmeta.publicity.feedback.FeedbackManagementDao;
import com.dsep.dao.dsepmeta.publicity.feedback.FeedbackResponseDao;
import com.dsep.domain.dsepmeta.feedback.FeedbackMessage;
import com.dsep.entity.dsepmeta.BackupManagement;
import com.dsep.entity.dsepmeta.FeedbackManagement;
import com.dsep.entity.dsepmeta.FeedbackResponse;
import com.dsep.entity.dsepmeta.FeedbackManagement;
import com.dsep.entity.dsepmeta.PublicityManagement;
import com.dsep.entity.enumeration.EnumModule;
import com.dsep.entity.enumeration.databackup.BackupType;
import com.dsep.entity.enumeration.feedback.FeedbackStatus;
import com.dsep.entity.enumeration.feedback.FeedbackType;
import com.dsep.entity.enumeration.publicity.OpenStatus;
import com.dsep.entity.enumeration.publicity.PublicityStatus;
import com.dsep.service.dsepmeta.dsepmetas.DMBackupService;
import com.dsep.service.publicity.feedback.FeedbackManagementService;
import com.dsep.util.DateProcess;
import com.dsep.util.GUID;
import com.dsep.util.StringProcess;
import com.dsep.vm.CollectionTreeVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.feedback.FeedbackManagementVM;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class FeedbackManagementServiceImpl implements FeedbackManagementService{

	private FeedbackManagementDao feedbackManagementDao;
	private FeedbackResponseDao feedbackResponseDao;
	private BackupManagementDao backupManagementDao;
	private DMBackupService backupService;
	
	private static int successCount = 0;
	
	private static int failCount = 0;
	
	private void failed(){
		failCount++;
		StringProcess.testOutput("failCount:"+failCount+";success:"+successCount);
	}
	
	private void succeed(){
		successCount++;
		StringProcess.testOutput("failCount:"+failCount+";success:"+successCount);
	}

	public void setFeedbackManagementDao(FeedbackManagementDao feedbackManagementDao) {
		this.feedbackManagementDao = feedbackManagementDao;
	}

	public void setBackupManagementDao(BackupManagementDao backupManagementDao) {
		this.backupManagementDao = backupManagementDao;
	}

	public void setBackupService(DMBackupService backupService) {
		this.backupService = backupService;
	}

	public void setFeedbackResponseDao(FeedbackResponseDao feedbackResponseDao) {
		this.feedbackResponseDao = feedbackResponseDao;
	}


	@Override
	public FeedbackManagement getCurrentFeedbackRound() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		FeedbackManagement queryManage = new FeedbackManagement();
		queryManage.setOpenStatus(OpenStatus.OPEN.getStatus());
		return feedbackManagementDao.querySingleDataByCondition(queryManage);
	}



	@Override
	public boolean isCurrentFeedbackOpen() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean isCurrentFeedbackBegin() {
		// TODO Auto-generated method stub
		return false;
	}

	private FeedbackManagement getCurrentBeginFeedbackRound() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		FeedbackManagement queryManage = new FeedbackManagement();
		queryManage.setOpenStatus(OpenStatus.OPEN.getStatus());
		queryManage.setStatus(FeedbackStatus.FEEDBACKBEGIN.getStatus());
		return feedbackManagementDao.querySingleDataByCondition(queryManage);
	}


	@Override
	public List<CollectionTreeVM> getFeedbackTypeTree() {
		// TODO Auto-generated method stub
		List<CollectionTreeVM> feedbackTree = new ArrayList<CollectionTreeVM>();
		EnumModule feedbackEnum = new FeedbackType();
		Map<String,String> feedbackMap = feedbackEnum.getEnumMap();
		for( Map.Entry<String, String> mapEntry:feedbackMap.entrySet()){
			CollectionTreeVM newTreeVM = new CollectionTreeVM();
			newTreeVM.setId(mapEntry.getKey());
			newTreeVM.setName(mapEntry.getValue());
			newTreeVM.setValue(mapEntry.getKey());
			newTreeVM.setTitle(mapEntry.getValue());
			newTreeVM.setpId("root");
			feedbackTree.add(newTreeVM);
		}
		CollectionTreeVM rootVM = new CollectionTreeVM();
		rootVM.setId("root");
		rootVM.setName("反馈类型");
		rootVM.setOpen(true);
		feedbackTree.add(rootVM);
		return feedbackTree;
	}



	@Override
	public boolean openNewFeedbackRound() {
		// TODO Auto-generated method stub
		FeedbackManagement newRound = new FeedbackManagement();
		
		//增加一个备份的版本
		BackupManagement newBackup = new BackupManagement();
		newBackup.setId(GUID.get());
		newBackup.setBackupType(BackupType.FEEDBACK.getStatus());
		newBackup.setVersionId(GUID.get());
		newBackup.setBackupTime(new Date());
		String versionId = backupManagementDao.addBackup(newBackup);
		
		backupService.backupCenter(versionId); //备份采集数据
		
		//开启一个新的批次
		newRound.setId(GUID.get());
		newRound.setOpenStatus(OpenStatus.OPEN.getStatus());
		newRound.setStatus(FeedbackStatus.FEEDBACKOPEN.getStatus());
		newRound.setBackupVersionId(versionId);
		if( feedbackManagementDao.save(newRound) != null)
			return true;
		else
			return false;
	}

	@Override
	public boolean beginFeedback(String feedbackName, String beginTime,String endTime,
			String remark) throws IllegalArgumentException, IllegalAccessException, ParseException {
		// TODO Auto-generated method stub
		//改变当前批次的状态
		FeedbackManagement currentRound = this.getCurrentFeedbackRound();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = sdf.parse(beginTime);
		Date endDate = sdf.parse(endTime);
		
		currentRound.setFeedbackRoundName(feedbackName);
		currentRound.setBeginTime(beginDate);
		currentRound.setEndTime(endDate);
		//currentRound.setStatus(FeedbackStatus.FEEDBACKBEGIN.getStatus());
		currentRound.setRemark(remark);
		
		//改变反馈数据的状态
		feedbackResponseDao.updateWhenCenterBeginFeedback(currentRound.getId());
		return true;
	}
	@Override
	public String immediateFeedback() throws IllegalArgumentException, IllegalAccessException, ParseException {
		// TODO Auto-generated method stub
		//改变当前批次的状态
		FeedbackManagement currentRound = this.getCurrentFeedbackRound();
		
			if(StringProcess.isNull(currentRound.getFeedbackRoundName())){
				return "notSet";
			}else{
				currentRound.setStatus(FeedbackStatus.FEEDBACKBEGIN.getStatus());
				//改变反馈数据的状态
				feedbackResponseDao.updateWhenCenterBeginFeedback(currentRound.getId());
				return "success";
		} 
			
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getAllFeedbackRound() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		FeedbackManagement conditionalManagement = new FeedbackManagement();
		conditionalManagement.setStatus(FeedbackStatus.FEEDBACKBEGIN.getStatus());
		List<FeedbackManagement> roundList = feedbackManagementDao.queryByCondition(conditionalManagement, "openStatus", true);
		Map<String,String> roundMap = new LinkedMap();
		for(FeedbackManagement feedbackRound:roundList){
			roundMap.put(feedbackRound.getId(), feedbackRound.getFeedbackRoundName());
		}
		return roundMap;
	}


	@Override
	public FeedbackMessage getFeedbackRoundMessage(String feedbackRoundId) {
		// TODO Auto-generated method stub
		FeedbackManagement theRound = feedbackManagementDao.get(feedbackRoundId);
		FeedbackMessage theMessage = new FeedbackMessage();
		if(theRound != null){
			theMessage.setBeginTime(DateProcess.getShowingDate(theRound.getBeginTime()));
			theMessage.setEndTime(DateProcess.getShowingDate(theRound.getEndTime()));
			EnumModule module = new OpenStatus();
			theMessage.setOpenStatus(module.getShowingByStatus(theRound.getOpenStatus()));
			theMessage.setBackupVersionId(theRound.getBackupVersionId());
		}
		return theMessage;
	}

	@Override
	public PageVM<FeedbackManagementVM> getAllFeedbackRoundList(
			String orderName, boolean order_flag) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		FeedbackManagement queryManage = new FeedbackManagement();
		queryManage.setStatus(FeedbackStatus.FEEDBACKBEGIN.getStatus());
		List<FeedbackManagement> publicityList = feedbackManagementDao.queryByCondition(queryManage,orderName,order_flag);
		List<FeedbackManagementVM> vmList = new ArrayList<FeedbackManagementVM>();
		int totalCount = feedbackManagementDao.getCountByCondition(queryManage);
		for(int i=0;i < publicityList.size();i++){
			FeedbackManagement test = publicityList.get(i);
			FeedbackManagementVM newVm = new FeedbackManagementVM(test);
			vmList.add(newVm);
		}
		PageVM<FeedbackManagementVM> publicityRoundVm = new PageVM<FeedbackManagementVM>(1,totalCount,50,vmList);
		return publicityRoundVm;
	}

	@Override
	public boolean editFeedback(String roundId, String remark) throws NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		Map<String,Object> columnMap = new HashMap<String,Object>();
		columnMap.put("remark", remark);
		int result = feedbackManagementDao.updateColumn(roundId, columnMap);
		if( result > 0 )
			return true;
		else
			return false;
	}

	@Override
	/*@Async*/
	public String autoCloseFeedbackRound() throws IllegalArgumentException, IllegalAccessException{
		FeedbackManagement currentRound = this.getCurrentBeginFeedbackRound();
		try{
			if( currentRound == null || currentRound.getEndTime() == null){
				System.out.println("没有可开启的反馈批次");
				this.succeed();
				return null;
			}
			//result是预定关闭时间与当前时间的比较结果
			String result = DateProcess.CompareDateTimeByDate(currentRound.getEndTime(),new Date());
			if( result.equals("=")){
				this.closeFeedbackRound(currentRound);
				System.out.println("成功开启当前反馈批次");
				this.succeed();
				return null;
			}
			else if( result.equals("<")){
				this.closeFeedbackRound(currentRound);
				System.out.println("当期反馈批次延迟开启");
				this.succeed();
				return null;
			}
			else{
				System.out.println("尚未到反馈批次自动开启时间");
				this.succeed();
				return null;
			}
		}
		catch(Exception e){
			this.failed();
			return null;
		}
	}
	
	@Override
	public boolean feedbackFinish() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		FeedbackManagement currentRound = this.getCurrentFeedbackRound();
		currentRound.setActualEndTime(new Date());
		currentRound.setOpenStatus(OpenStatus.CLOSE.getStatus());
		return true;
	}
	
	private boolean closeFeedbackRound(FeedbackManagement currentRound){
		currentRound.setActualEndTime(new Date());
		currentRound.setOpenStatus(OpenStatus.CLOSE.getStatus());
		return true;
	}

	@Override
	public boolean deleteFeedbackRound(String feedbackRoundId) {
		// TODO Auto-generated method stub
		FeedbackManagement deleteManage = feedbackManagementDao.get(feedbackRoundId);
		String versionId = deleteManage.getBackupVersionId();
		backupService.deleteBackupData(versionId);
		feedbackManagementDao.deleteByKey(feedbackRoundId);
		return true;
	}
	

}
