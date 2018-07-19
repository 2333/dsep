package com.dsep.service.databackup.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.dao.dsepmeta.databackup.BackupManagementDao;
import com.dsep.dao.dsepmeta.databackup.DisciplineDataBackupDao;
import com.dsep.entity.dsepmeta.BackupManagement;
import com.dsep.entity.dsepmeta.PreEval;
import com.dsep.service.databackup.DataBackupService;
import com.dsep.service.dsepmeta.dsepmetas.DMBackupService;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.vm.BackupManageVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.flow.PreEvalVM;

/**
 * 
 * @author Panlinjie
 * 
 */
public class DataBackupServiceImpl implements DataBackupService {

	private static int WRONG_NUMBER = -10;

	private BackupManagementDao backupManagementDao;
	
	private DisciplineDataBackupDao disciplineDataBackupDao;
	
	private DMBackupService backupService;
	
	

	public DisciplineDataBackupDao getDisciplineDataBackupDao() {
		return disciplineDataBackupDao;
	}

	public void setDisciplineDataBackupDao(
			DisciplineDataBackupDao disciplineDataBackupDao) {
		this.disciplineDataBackupDao = disciplineDataBackupDao;
	}

	public DMBackupService getBackupService() {
		return backupService;
	}

	public void setBackupService(DMBackupService backupService) {
		this.backupService = backupService;
	}

	public BackupManagementDao getBackupManagementDao() {
		return backupManagementDao;
	}

	public void setBackupManagementDao(BackupManagementDao backupManagementDao) {
		this.backupManagementDao = backupManagementDao;
	}

	
	@Override
	public boolean backupDiscipline(String unitId, String discId) throws Exception {
		// TODO Auto-generated method stub
		BackupManagement queryManagement = new BackupManagement();
		queryManagement.setUnitId(unitId);
		queryManagement.setDiscId(discId);
		int count = backupManagementDao.getCountByCondition(queryManagement);
		if( count >= 5 ){
			String deleteVersionId = backupManagementDao.getEarliestVersionId(unitId,discId);
			this.deleteDiscipline(deleteVersionId, discId);
		}
	    String versionId = backupManagementDao.addDisciplineBackup(unitId, discId);
	    if(versionId == null || versionId == "" || versionId == "null"){
	    	throw new Exception("备份失败");
	    }
	    if( backupService.backupDiscipline(unitId, discId,versionId))
	    	return true;
	    else
	    	return false;
	}

	
	public boolean restoreDiscipline(String id,String unitId,String discId,String versionId) throws NoSuchFieldException, SecurityException{
		Map<String, Object> editRow = new HashMap<String, Object>();
		editRow.put("restoreTime",new Date());
		int result = backupManagementDao.updateColumn(id, editRow);
		if( result == 1 && backupService.restoreDiscipline(unitId, discId, versionId)){
		 	return true;
		}
		else{
		 	return false;
		}
	}

	
	@Override
	public PageVM<BackupManageVM> getAllBackupVersion(int pageIndex,int pageSize,boolean order_flag,String orderName,
			String unitId,String discId) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		/*List<BackupManagement> versionList = backupManagementDao.getAllVersion(pageSize,pageIndex,unitId, discId);*/
		BackupManagement management = new BackupManagement();
		management.setDiscId(discId);
		management.setUnitId(unitId);
		List<BackupManagement> versionList = backupManagementDao.queryByCondition(management, pageIndex, pageSize, order_flag, orderName);
		List<BackupManageVM> vmList = new ArrayList<BackupManageVM>(0);
		int totalCount = backupManagementDao.getDisciplineCount(unitId, discId);//数据总数
		for(int i=0;i < versionList.size();i++){
			BackupManagement test = versionList.get(i);
			BackupManageVM newVm = new BackupManageVM(test);
			vmList.add(newVm);
		}
		PageVM<BackupManageVM> versionVm = new PageVM<BackupManageVM>(pageIndex,totalCount,pageSize,vmList);
		return versionVm;
		/*return null;*/
	}

	@Override
	public int GetDataCount(String tableName, String schoolId,
			String disciplineId)  {
		// TODO Auto-generated method stub
		int count = disciplineDataBackupDao.GetCount(tableName, schoolId, disciplineId);
		return count;
	}

	@Override
	public int GetBackupDataCount(String tableName, String backupTableName,String schoolId,
			String disciplineId, String versionId)  {
		// TODO Auto-generated method stub
		int count = disciplineDataBackupDao.GetBackupCount(tableName, backupTableName,schoolId,
				disciplineId, versionId);
		return count;
	}

	@Override
	public int updateBackupVersion(String id,String remark) throws NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		Map<String, Object> editRow = new HashMap<String, Object>();
		editRow.put("remark", remark);
		return backupManagementDao.updateColumn(id, editRow);
	}

	@Override
	public int createTable() {
		// TODO Auto-generated method stub
		Map<String,String> backupMap = new HashMap<String,String>();
		backupMap.put("DSEP_C_ZJ_2013", "DSEP_B_ZJ_2013");
		backupMap.put("DSEP_C_TD_2013","DSEP_B_TD_2013");
		for( Map.Entry<String,String> entry:backupMap.entrySet()){
			disciplineDataBackupDao.createBackupTable(entry.getKey(),entry.getValue());
		}
		return 0;
	}

	@Override
	public boolean deleteDiscipline(String versionId, String discId) throws Exception {
		// TODO Auto-generated method stub
		int result = backupManagementDao.deleteVersion(versionId);//删除版本管理表的备份信息
		if( result != 1)
			throw new Exception("版本信息删除失败");
		return backupService.deleteDiscipline(versionId, discId);//删除备份数据
	}

	

}
