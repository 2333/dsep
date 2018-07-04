package com.dsep.dao.dsepmeta.databackup;

import com.dsep.dao.common.Dao;

public interface CenterDataBackupDao extends Dao{

	/**
	 * 学位中心备份数据
	 * @param tableName 原表表名
	 * @param backupTableName 备份表表名
	 * @param versionId 备份版本号
	 * @return 备份的数据条数
	 */
	public int backupCenter(String tableName,String backupTableName,String versionId);
	
	/**
	 * 删除某一版本的备份数据
	 * @param versionId
	 * @return
	 */
	public boolean deleteCenterData(String versionId,String backupTableName);
	
	/**
	 * 中心在删除数据时进行备份
	 * @param tableName 原表表名
	 * @param backupTableName 备份表表名
	 * @param userId 删除的用户
	 * @param dataItemId 数据项ID
	 * @return
	 */
	public int backupDataWhenDelete(String tableName,String backupTableName,String entityItemId);
	
	/**
	 * 创建备份表
	 * @param tableName 原表表名
	 * @param backupTableName 备份表表名
	 * @return 
	 */
	public int createBackupTable(String tableName,String backupTableName);

	/**
	 * 删除备份表
	 * @param backupTableName
	 */
	public int deleteBackupTable(String backupTableName);
}
