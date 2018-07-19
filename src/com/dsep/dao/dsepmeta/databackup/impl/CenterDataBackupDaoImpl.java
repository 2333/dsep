package com.dsep.dao.dsepmeta.databackup.impl;

import com.dsep.dao.dsepmeta.databackup.CenterDataBackupDao;

public class CenterDataBackupDaoImpl extends BackupDaoImpl 
	implements CenterDataBackupDao
	{


	@Override
	public int backupCenter(String tableName, String backupTableName,
			String versionId) {
		// TODO Auto-generated method stub
		String backupColumn = super.getBackupColumn(tableName);// 获取所需备份列,格式：col1,col2,col3
		String insertSql = "insert into " + backupTableName
				+"(VERSION_ID," + backupColumn + ")" + " select '"
				+ versionId + "'," + backupColumn
				+ " from " + tableName + " ";
		;// 备份语句
		int result = super.sqlBulkUpdate(insertSql);
		return result;
	}

	@Override
	public boolean deleteCenterData(String versionId, String backupTableName) {
		// TODO Auto-generated method stub
		String deleteSql = "delete from " + backupTableName + " where VERSION_ID = '" +versionId+"'";
		super.sqlBulkUpdate(deleteSql);
		return true;
	}

	@Override
	public int backupDataWhenDelete(String tableName, String backupTableName,String entityItemId) {
		// TODO Auto-generated method stub
		String backupColumn = super.getBackupColumn(tableName);// 获取所需备份列,格式：col1,col2,col3
		String insertSql = "insert into " + backupTableName
				+ "(VERSION_ID," + backupColumn + ")" 
				+ " select 'center_delete'," + backupColumn
				+ " from " + tableName + " where id = '"+ entityItemId+"'";
		;// 备份语句
		int result = super.sqlBulkUpdate(insertSql);
		return result;
	}


	/**
	 * 获取根据原表创建备份表的sql语句
	 * 
	 * @param tableName
	 *            原表表名
	 */
	protected String getCreateBackupTableSql(String tableName,String backupTableName) {
		String createSql = "create table " + backupTableName
				+ "(VERSION_ID VARCHAR(32), " + super.getTableColumn(tableName) + ") ";
		createSql += "tablespace " + super.getBackupTableSpaceName().toUpperCase();
		return createSql;
	}

	/**
	 * 根据原表表名和表空间创建备份表
	 * 
	 * @param tableName
	 *            原表表名
	 * @return
	 */
	public int createBackupTable(String tableName,String backupTableName) {
		String createSql = getCreateBackupTableSql(tableName,backupTableName);
		int result = super.sqlBulkUpdate(createSql);
		return result;
	}



	@Override
	public int deleteBackupTable(String backupTableName) {
		// TODO Auto-generated method stub
		String deleteSql = "drop table "+backupTableName+"";
		int result = super.sqlBulkUpdate(deleteSql);
		return result;
	}

}
