package com.dsep.dao.dsepmeta.databackup.impl;

import java.util.List;

import com.dsep.dao.common.impl.DaoImpl;

public abstract class BackupDaoImpl extends DaoImpl{
	
	
	private String backupTableSpaceName = "DSEP_BACKUP";// 备份表空间的名称
	
	
	public String getBackupTableSpaceName() {
		return backupTableSpaceName;
	}

	public void setBackupTableSpaceName(String backupTableSpaceName) {
		this.backupTableSpaceName = backupTableSpaceName;
	}

	/**
	 * 获取数据类型和长度获取建表时的字符串 例：类型为NVARCHAR2，长度为200，则返回NVARCHAR2(100)
	 * 
	 * @param dataType
	 * @param dataLength
	 * @return
	 */
	private String GetTypeMessage(String dataType, String dataLength) {
		switch (dataType) {
		case "NVARCHAR2":
		case "NCHAR2":
			return dataType + "(" + Integer.parseInt(dataLength) / 2 + ")";
		case "VARCHAR2":
		case "CHAR":
			return dataType + "(" + dataLength + ")";
		default:
			return dataType;
		}
	}
	
	/**
	 * 返回某数据表的所有列格式，供建备份表使用用
	 * @param tableName
	 * @return 列格式，例："id varchar2(20),name varchar2(20)"

	 */
	protected String getTableColumn(String tableName){
		String formatColumn = "";
		String sql = "select column_name,data_type,data_length from user_tab_cols"
				+ "	where table_name = '" + tableName.toUpperCase() + "'";
		List<Object[]> columnResult = super.sqlScalarResults(sql, new String[] {
				"column_name", "data_type", "data_length" });
		for (Object[] column : columnResult) {
			formatColumn += column[0].toString() + " ";
			formatColumn += GetTypeMessage(column[1].toString().toUpperCase(),
					column[2].toString()) + ",";
		}
		return formatColumn.substring(0, formatColumn.length() - 1);
	}
	
	/**
	 * 返回某数据表的列，供备份和恢复使用
	 * @param tableName
	 * @return 所有的列，例："id,name,password"
	 * 
	 */
	protected String getBackupColumn(String tableName){
		String hql = "select column_name from user_tab_cols "
				+ "where table_name = '" + tableName.toUpperCase() + "'";
		List<String> columnResult = super.GetShadowResult(hql);
		String columnCollection = "";
		for (String column : columnResult) {
			columnCollection += column + ",";
		}
		return columnCollection.substring(0, columnCollection.length() - 1);
	}
	
	
	
}

