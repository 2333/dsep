package com.dsep.dao.dsepmeta.databackup;


import java.util.List;

import com.dsep.dao.common.Dao;

/**
 * 学科用户备份数据，有版本号，提供数据恢复功能
 * @author Panlinjie
 *
 */
public interface DisciplineDataBackupDao extends Dao{
	
	/**
	 * 备份学科数据
	 * @param tableName 原表表名
	 * @param backupTableName 备份表表名
	 * @param unitId 学校代码
	 * @param discId 学科代码
	 * @param versionId 备份的版本号
	 * @return
	 */
	public int backupDiscipline(String tableName,String backupTableName,String unitId,String discId,String versionId);
	
	/**
	 * 获取某学科某原表的数据行数
	 * @param tableName 原表表名
	 * @param unitId 学校代码
	 * @param discId 学科代码
	 * @return 数据行总数
	 */
	public int GetCount(String tableName,String unitId,String discId);
	
	/**
	 * 获取某学科某备份表某版本的数据行数
	 * @param tableName 原表表名
	 * @param unitId
	 * @param discId
	 * @param versionId 版本号
	 * @return
	 */
	public int GetBackupCount(String tableName,String backupTableName,String unitId,String discId,String versionId);
	
	/**
	 * 恢复学科数据到某个版本
	 * @param tableName 原表表名
	 * @param backupTableName 备份表表名
	 * @param unitId 学校ID
	 * @param discId 学科ID
	 * @param versionId 版本号
	 * @return
	 */
	public int restoreDiscipline(String tableName,String backupTableName,String unitId,String discId,String versionId);

	/**
	 * 删除某一个版本的备份
	 * @param versionId 版本号
	 * @param unitId 备份表表名
	 * @return
	 */
	public int deleteBackup(String versionId,String backupTableName);
	
	/**
	 * 创建备份表
	 * @param tableName 原表表名
	 * @param backupTableName 备份表表名
	 * @return 
	 */
	public int createBackupTable(String tableName,String backupTableName);
	

	
}
