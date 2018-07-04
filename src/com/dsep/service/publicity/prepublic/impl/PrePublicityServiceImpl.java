package com.dsep.service.publicity.prepublic.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dsep.common.exception.BeginPrePublicityException;
import com.dsep.dao.dsepmeta.base.CategoryDao;
import com.dsep.dao.dsepmeta.databackup.BackupManagementDao;
import com.dsep.dao.dsepmeta.publicity.objection.PublicityManagementDao;
import com.dsep.domain.dsepmeta.publicity.CollectionDelete;
import com.dsep.entity.dsepmeta.Category;
import com.dsep.entity.dsepmeta.PublicityManagement;
import com.dsep.entity.enumeration.publicity.OpenStatus;
import com.dsep.entity.enumeration.publicity.PublicityStatus;
import com.dsep.service.dsepmeta.dsepmetas.DMBackupService;
import com.dsep.service.dsepmeta.dsepmetas.publicity.DMPrePublicService;
import com.dsep.service.publicity.prepublic.PrePublicityService;
import com.dsep.util.DateProcess;
import com.dsep.util.GUID;
import com.dsep.util.StringProcess;
import com.dsep.vm.publicity.PublicityManagementVM;

public class PrePublicityServiceImpl implements PrePublicityService{

	private BackupManagementDao backupManagementDao;
	private DMBackupService backupService;
	private DMPrePublicService dmPrePublicService;
	private PublicityManagementDao publicityManagementDao;
	

	public void setPublicityManagementDao(
			PublicityManagementDao publicityManagementDao) {
		this.publicityManagementDao = publicityManagementDao;
	}

	public void setDmPrePublicService(DMPrePublicService dmPrePublicService) {
		this.dmPrePublicService = dmPrePublicService;
	}

	public void setBackupManagementDao(BackupManagementDao backupManagementDao) {
		this.backupManagementDao = backupManagementDao;
	}

	public void setBackupService(DMBackupService backupService) {
		this.backupService = backupService;
	}
	

	
	@Override
	public boolean deleteBackData(String entityId, String versionId,String pkValue,
			String seqNo,String unitId, String discId) {
		// TODO Auto-generated method stub
		boolean result = dmPrePublicService.deleteBackupRow(entityId, pkValue, seqNo, versionId, unitId, discId);
		return result;
	}


	@Override
	public boolean deleteBatchBackData(String entityId, String versionId,
			List<CollectionDelete> deleteList) throws Exception {
		// TODO Auto-generated method stub
		boolean result = false;
		for(CollectionDelete deleteItem:deleteList){
			result = dmPrePublicService.deleteBackupRow(entityId,versionId,
					deleteItem.getId(),deleteItem.getSeqNo(),
					deleteItem.getUnitId(),deleteItem.getDiscId());
			if( !result ){
				throw new Exception("删除数据出现错误");
			}
		}
		return result;
	}

	@Override
	public boolean openNewPublicityRound() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		if( !isOpenNewPrePublicityRound() ){
			throw new BeginPrePublicityException("公示轮次已开启");
		}
		String versionId = backupManagementDao.addCenterPublicityBackup();//增加新的备份版本
		boolean result = backupService.backupCenter(versionId);//备份数据
		if( !result ){
			throw new BeginPrePublicityException("预公示开启失败");
		}
		PublicityManagement newRound = new PublicityManagement();
		newRound.setId(GUID.get());
		newRound.setBackupVersionId(versionId);
		/*newRound.setBeginTime(new Date());*/
		newRound.setStatus(PublicityStatus.PREPUBBEGIN.getStatus());
		newRound.setOpenStatus(OpenStatus.OPEN.getStatus());
		String roundId = publicityManagementDao.save(newRound);//添加新的预公示轮次
		if( roundId == null || roundId == ""){
			throw new BeginPrePublicityException("预公示开启失败");
		}
		return true;
	}

	@Override
	public boolean isOpenNewPrePublicityRound() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		PublicityManagement newManage = this.getCurrentPublicityRound();
		if( newManage != null ){
			return false;
		}
		else
			return true;
	}

	@Override
	public String getPublicityRoundStatus() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		PublicityManagement newManage = this.getCurrentPublicityRound();
		if( newManage == null )
			return "0";
		else 
			return newManage.getStatus();
	}
	
	
	public PublicityManagement getCurrentPublicityRound() throws IllegalArgumentException, IllegalAccessException{
		PublicityManagement queryManage = new PublicityManagement();
		queryManage.setOpenStatus(OpenStatus.OPEN.getStatus());
		PublicityManagement currentManage = publicityManagementDao.querySingleDataByCondition(queryManage);
		return currentManage;
	}
	
	
	public PublicityManagementVM getCurrentPublicityRoundVM() throws IllegalArgumentException, IllegalAccessException{
		PublicityManagement currentManage = this.getCurrentPublicityRound();
		PublicityManagementVM currentVM = new PublicityManagementVM(currentManage);
		return currentVM;
	}
	
	

	@Override
	public boolean setPublicity(String publicityName,String beginTime, String endTime, String remark) throws IllegalArgumentException, IllegalAccessException, ParseException {
		// TODO Auto-generated method stub
		PublicityManagement currentManage = this.getCurrentPublicityRound();
		currentManage.setRemark(remark);
		currentManage.setBeginTime(DateProcess.getDatebaseDateFromInput(beginTime));
		currentManage.setEndTime(DateProcess.getDatebaseDateFromInput(endTime));
		currentManage.setPublicRoundName(publicityName);
		return true;
	}

	

	@Override
	public boolean deletePublicityRound(String publicityRoundId) {
		// TODO Auto-generated method stub
		PublicityManagement deleteManage = publicityManagementDao.get(publicityRoundId);
		String versionId = deleteManage.getBackupVersionId();
		backupService.deleteBackupData(versionId);
		publicityManagementDao.deleteByKey(publicityRoundId);
		return true;
	}

	@Override
	public boolean notPubBackData(String entityId, String versionId,
			String pkValue) {
		// TODO Auto-generated method stub
		return dmPrePublicService.notPublicBackupData(entityId, versionId, pkValue);
	}


	@Override
	public boolean notPubBackDataList(String entityId, String versionId,
			List<String> pkValueList) {
		// TODO Auto-generated method stub
		for(String pkValue:pkValueList){
			if( !this.notPubBackData(entityId, versionId, pkValue)){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean pubBackData(String entityId, String versionId, String pkValue) {
		// TODO Auto-generated method stub
		return dmPrePublicService.publicBackupData(entityId, versionId, pkValue);
	}

	@Override
	public boolean pubBackDataList(String entityId, String versionId,
			List<String> pkValueList) {
		// TODO Auto-generated method stub
		for(String pkValue:pkValueList){
			if( !this.pubBackData(entityId, versionId, pkValue)){
				return false;
			}
		}
		return true;
	}

	@Override
	public String beginPublicity() throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		PublicityManagement currentRound = this.getCurrentPublicityRound();
		if( StringProcess.isNull(currentRound.getPublicRoundName()) ){
			return "notset";
		}
		else{
			currentRound.setActualBeginTime(new Date());
			currentRound.setStatus(PublicityStatus.PUBBEGIN.getStatus());
			return "success";
		}
	}

	

}
