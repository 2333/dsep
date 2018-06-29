package com.dsep.dao.dsepmeta.databackup.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.dao.dsepmeta.databackup.DisciplineDataBackupDao;
import com.dsep.entity.User;

public class DisciplineDataBackupDaoImpl extends BackupDaoImpl implements
		DisciplineDataBackupDao {

	private String dateFormat = "yyyyMMdd_HHmmss";// 版本号中的时间格式
	private String backupTableAppendix = "_disciplinebackup";// 备份表的后缀
	private String versionIdName = "VERSION_ID";// 备份表中版本号字段的名称
	private String versionIdType = "VARCHAR2(50)";// 备份表中版本号字段的类型
	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getBackupTableAppendix() {
		return backupTableAppendix;
	}

	public void setBackupTableAppendix(String backupTableAppendix) {
		this.backupTableAppendix = backupTableAppendix;
	}

	public String getVersionIdName() {
		return versionIdName;
	}

	public void setVersionIdName(String versionIdName) {
		this.versionIdName = versionIdName;
	}

	public String getVersionIdType() {
		return versionIdType;
	}

	public void setVersionIdType(String versionIdType) {
		this.versionIdType = versionIdType;
	}


	/**
	 * 以字符串类型返回当前的时间，时间格式由类的dateFormat变量所定
	 * 
	 * @return 格式化系统时间
	 */
	protected String GetCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(this.getDateFormat());
		return sdf.format(new Date());
	}

	/*@Override
	public String getBackupTableName(String tableName) {
		// TODO Auto-generated method stub
		return tableName + this.getBackupTableAppendix();
	}*/
	
	/**
	 * 获取备份时的版本号
	 * 
	 * @param unitId
	 * @param discId
	 * @return
	 */
	private String getVersionId(String unitId, String discId) {
		return unitId + "_" + discId + "_" + GetCurrentTime();
	}


	/**
	 * 备份学科数据
	 * 
	 * @param tableName
	 *            需要备份的数据表的表名
	 * @param unitId
	 *            学校代码
	 * @param discId
	 *            学科代码
	 */
	public int backupDiscipline(String tableName,String backupTableName, 
			String unitId,String discId,String versionId) {
		String backupColumn = super.getBackupColumn(tableName);// 获取所需备份列,格式：col1,col2,col3
		String insertSql = "insert into " + backupTableName + "("
				+ versionIdName + "," + backupColumn + ")" + " select '"
				+ versionId + "'," + backupColumn
				+ " from " + tableName + " where UNIT_ID = '" + unitId
				+ "' and DISC_ID = '" + discId + "'";
		;// 备份语句
		int result = super.sqlBulkUpdate(insertSql);
		return result;
	}

	/**
	 * 数据恢复的第一步操作，删除原表数据
	 * 
	 * @param tableName
	 *            原表表名
	 * @param unitId
	 * @param discId
	 * @return
	 */
	private int DeleteDiscipline(String tableName, String unitId,
			String discId) {
		String deleteSql = "delete from " + tableName + " where UNIT_ID = '"
				+ unitId + "' and DISC_ID = '" + discId + "'";
		int result = super.sqlBulkUpdate(deleteSql);
		return result;
	}

	/**
	 * 数据恢复的第二步操作，将数据从备份表复制到原表
	 * 
	 * @param tableName
	 * @param unitId
	 * @param discId
	 * @param versionId
	 * @return
	 */
	private int RecoverDiscipline(String tableName, String backupTableName,
			String unitId,String discId, String versionId) {
		String backupColumn = super.getBackupColumn(tableName);
		String recoverSql = "Insert into " + tableName + "(" + backupColumn
				+ ")" + " select " + backupColumn + " from " + backupTableName
				+ " where UNIT_ID = '" + unitId + "' and DISC_ID = '"
				+ discId + "' and VERSION_ID = '" + versionId + "'";
		int result = super.sqlBulkUpdate(recoverSql);
		return result;
	}

	/**
	 * 学科数据的恢复
	 * 
	 * @param tableName
	 * @param unitId
	 * @param discId
	 * @return
	 */
	public int restoreDiscipline(String tableName,String backupTableName,
			String unitId,String discId, String versionId) {
		DeleteDiscipline(tableName, unitId, discId);
		int result = RecoverDiscipline(tableName,backupTableName,
				unitId, discId,versionId);
		return result;
	}

	/**
	 * 
	 */
	public int DeleteBackup(String tableName, String unitId,
			String discId) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 获取根据原表创建备份表的sql语句
	 * 
	 * @param tableName
	 *            原表表名
	 */
	protected String GetCreateBackupTableSql(String tableName,String backupTableName) {
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
		String createSql = GetCreateBackupTableSql(tableName,backupTableName);
		int result = super.sqlBulkUpdate(createSql);
		return result;
	}

	@Override
	public int GetCount(String tableName, String unitId, String discId) {
		// TODO Auto-generated method stub
		String countSql = "select count(*) from " + tableName
				+ " where UNIT_ID = '" + unitId + "' and DISC_ID = '"
				+ discId + "'";
		int result = super.sqlCount(countSql);
		return result;
	}

	@Override
	public int GetBackupCount(String tableName, String backupTableName,String unitId,
			String discId, String versionId) {
		// TODO Auto-generated method stub
		String countSql = "select count(*) from "
				+ backupTableName + " where UNIT_ID = '"
				+ unitId + "' and DISC_ID = '" + discId
				+ "' and versionId = '" + versionId + "'";
		int result = super.sqlCount(countSql);
		return result;
	}

	@Override
	public int deleteBackup(String versionId, String backupTableName) {
		// TODO Auto-generated method stub
		String sql = "delete from " + backupTableName + " where VERSION_ID = ?";
		Object[] values = new Object[1];
		values[0] = versionId;
		return super.sqlBulkUpdate(sql, values);
	}

	
}
