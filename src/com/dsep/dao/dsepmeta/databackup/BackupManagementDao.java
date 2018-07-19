package com.dsep.dao.dsepmeta.databackup;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.BackupManagement;

public interface BackupManagementDao extends 
	DsepMetaDao<BackupManagement, String>{
	
	/**
	 * 获取某学科的备份版本总数
	 * @param unitId 学校ID
	 * @param discId 学科ID
	 * @return
	 */
	public int getDisciplineCount(String unitId,String discId);
	
	/**
	 * 学科数据备份，增加一个新的备份版本号
	 * @param unitId 单位ID
	 * @param discId 学科ID
	 * @return 备份的版本号
	 */
	public String addDisciplineBackup(String unitId,String discId);
	
	/**
	 * 学位中心公示前数据备份
	 * @return
	 */
	public String addCenterPublicityBackup();
	
	/**
	 * 增加一个新的备份，如果成功则返回备份的版本号
	 * @param newBackup
	 * @return
	 */
	public String addBackup(BackupManagement newBackup);
	
	/**
	 * 获取某学科的所有备份版本
	 * @param unitId 单位ID
	 * @param discId 学科ID
	 * @return
	 */
	public List<BackupManagement> getAllVersion(int pageSize,int pageIndex,String unitId,String discId);
	
	/**
	 * 删除某版本
	 * @param versionId 版本号
	 * @return
	 */
	public int deleteVersion(String versionId);
	
	/**
	 * 获取最早备份的版本号
	 * @return
	 */
	public String getEarliestVersionId(String unitId,String discId);
	
}
