package com.dsep.service.publicity.objection.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.scheduling.annotation.Async;

import com.dsep.dao.dsepmeta.publicity.objection.PublicityManagementDao;
import com.dsep.domain.dsepmeta.publicity.PublicityMessage;
import com.dsep.entity.dsepmeta.BackupManagement;
import com.dsep.entity.dsepmeta.PublicityManagement;
import com.dsep.entity.enumeration.EnumModule;
import com.dsep.entity.enumeration.publicity.OpenStatus;
import com.dsep.entity.enumeration.publicity.PublicityStatus;
import com.dsep.entity.enumeration.publicity.RecentClose;
import com.dsep.service.publicity.objection.PublicityService;
import com.dsep.util.DateProcess;
import com.dsep.util.StringProcess;
import com.dsep.vm.BackupManageVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.publicity.PublicityManagementVM;

public class PublicityServiceImpl implements PublicityService{

	private PublicityManagementDao publicityManagementDao;
	
	private static int successCount = 0;
	
	private static int failCount = 0;
	
	public PublicityManagementDao getPublicityManagementDao() {
		return publicityManagementDao;
	}

	public void setPublicityManagementDao(PublicityManagementDao publicManagementDao) {
		this.publicityManagementDao = publicManagementDao;
	}

	@Override
	public boolean publicityFinish() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		publicityManagementDao.changeRecentClose();//将最近关闭的批次的“最近关闭”状态位变为false
		PublicityManagement currentRound = this.getCurrentPublicityRound();
		this.closePublicityRound(currentRound);
		return true;
	}
	
	
	private PublicityManagement getCurrentPublicityRound() throws IllegalArgumentException, IllegalAccessException{
		PublicityManagement queryPubManage = new PublicityManagement();
		queryPubManage.setStatus(PublicityStatus.PUBBEGIN.getStatus());
		queryPubManage.setOpenStatus(OpenStatus.OPEN.getStatus());
		return publicityManagementDao.querySingleDataByCondition(queryPubManage);
	}


	@Override
	public boolean isPublicityRoundBegin() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		if(this.getCurrentPublicityRound() != null)
			return true;
		else
			return false;
	}

	@Override
	public Map<String, String> getAllPublicityRound() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		PublicityManagement queryManage = new PublicityManagement();
		queryManage.setStatus(PublicityStatus.PUBBEGIN.getStatus());
		List<PublicityManagement> publicityList = publicityManagementDao.queryByCondition(queryManage,"openStatus",true);
		Map<String,String> roundMap = new LinkedMap();
		for(PublicityManagement management:publicityList){
			roundMap.put(management.getId(), management.getPublicRoundName());
		}
		return roundMap;
	}

	@Override
	public PublicityMessage getPublicityMessage(String publicityRoundId) {
		// TODO Auto-generated method stub
		PublicityManagement manageRound = publicityManagementDao.get(publicityRoundId);
		PublicityMessage theMessage = null;
		if( manageRound != null)
		{
			theMessage = new PublicityMessage();
			theMessage.setBeginTime(DateProcess.getShowingDate(manageRound.getBeginTime()));
			theMessage.setEndTime(DateProcess.getShowingDate(manageRound.getEndTime()));
			theMessage.setPublicityName(manageRound.getPublicRoundName());
			theMessage.setActualBeginTime(DateProcess.getShowingDate(manageRound.getActualBeginTime()));
			EnumModule module = new OpenStatus();
			theMessage.setOpenStatus(module.getShowingByStatus(manageRound.getOpenStatus()));
			theMessage.setVersionId(manageRound.getBackupVersionId());
		}
		return theMessage;
	}

	private boolean beginPublicityRound(PublicityManagement currentRound) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub	
		currentRound.setActualBeginTime(new Date());
		currentRound.setStatus(PublicityStatus.PUBBEGIN.getStatus());
		/*currentRound.setOpenStatus(PublicityStatus.PUBBEGIN.getStatus());*/
		return true;
	}
	
	private boolean closePublicityRound(PublicityManagement currentRound) throws IllegalArgumentException, IllegalAccessException{
		currentRound.setActualEndTime(new Date());
		currentRound.setOpenStatus(OpenStatus.CLOSE.getStatus());
		currentRound.setRecentClose(RecentClose.YES.getStatus());
		return true;
	}
	
	private PublicityManagement getUnbeginRound() throws IllegalArgumentException, IllegalAccessException{
		PublicityManagement theRound = new PublicityManagement();
		theRound.setStatus(PublicityStatus.PREPUBBEGIN.getStatus());
		return publicityManagementDao.querySingleDataByCondition(theRound);
	}

	@Override
	public PageVM<PublicityManagementVM> getAllPublicityRoundList(String orderName,boolean sord) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		PublicityManagement queryManage = new PublicityManagement();
		queryManage.setStatus(PublicityStatus.PUBBEGIN.getStatus());
		List<PublicityManagement> publicityList = publicityManagementDao.queryByCondition(queryManage,orderName,sord);
		List<PublicityManagementVM> vmList = new ArrayList<PublicityManagementVM>();
		int totalCount = publicityManagementDao.getCountByCondition(queryManage);
		for(int i=0;i < publicityList.size();i++){
			PublicityManagement test = publicityList.get(i);
			PublicityManagementVM newVm = new PublicityManagementVM(test);
			vmList.add(newVm);
		}
		PageVM<PublicityManagementVM> publicityRoundVm = new PageVM<PublicityManagementVM>(1,totalCount,50,vmList);
		return publicityRoundVm;
	}

	@Override
	public boolean editPublicity(String roundId, String remark) throws NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		Map<String,Object> columnMap = new HashMap<String,Object>();
		columnMap.put("remark", remark);
		int result = publicityManagementDao.updateColumn(roundId, columnMap);
		if( result > 0 )
			return true;
		else
			return false;
	}

	@Override
	public PublicityManagement getRecentCloseRound() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		PublicityManagement queryManage = new PublicityManagement();
		queryManage.setRecentClose(RecentClose.YES.getStatus());
		return publicityManagementDao.querySingleDataByCondition(queryManage);
	}

	@Override
	public String autoBeginPublicityRound() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		PublicityManagement currentRound = this.getUnbeginRound();
		if( currentRound == null || currentRound.getBeginTime() == null){
			System.out.println("没有可开启的公示批次");
			succeed();
			return null;
		}
		//result是预定关闭时间与当前时间的比较结果
		String result = DateProcess.CompareDateTimeByDate(currentRound.getBeginTime(),new Date());
		if( result.equals("=")){
			this.beginPublicityRound(currentRound);
			System.out.println("成功开启当前公示批次");
			this.succeed();
			return null;
		}
		else if( result.equals("<")){
			this.beginPublicityRound(currentRound);
			System.out.println("当期公示批次延迟开启");
			this.succeed();
			return null;
		}
		else{
			System.out.println("尚未到公示批次自动开启时间");
			this.failed();
			return null;
		}
	}

	@Override
	public boolean reopenRecentRound() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		PublicityManagement recentRound = this.getRecentCloseRound();
		recentRound.setOpenStatus(OpenStatus.OPEN.getStatus());
		return true;
	}

	@Override
	public String autoClosePublicityRound() throws IllegalArgumentException,
			IllegalAccessException {
		// TODO Auto-generated method stub
		try{
			PublicityManagement currentRound = this.getCurrentPublicityRound();
			if( currentRound == null || currentRound.getEndTime() == null ){
				System.out.println("没有可关闭的公示批次");
				succeed();
				return null;
			}
			//result是预定关闭时间与当前时间的比较结果
			String result = DateProcess.CompareDateTimeByDate(currentRound.getEndTime(),new Date());
			if( result.equals("=")){
				this.publicityFinish();
				System.out.println("成功关闭当前公示批次");
				succeed();
				return null;
			}
			/*else if( result.equals("<")){
				this.publicityFinish();
				System.out.println("当期公示批次延迟关闭");
				return null;
			}*/
			else{
				System.out.println("尚未到公示批次自动关闭时间");
				succeed();
				return null;
			}
		}
		catch(Exception e){
			failed();
			return null;
		}
	}
	
	private void failed(){
		failCount++;
		StringProcess.testOutput("failCount:"+failCount+";success:"+successCount);
	}
	
	private void succeed(){
		successCount++;
		StringProcess.testOutput("failCount:"+failCount+";success:"+successCount);
	}
	
}
