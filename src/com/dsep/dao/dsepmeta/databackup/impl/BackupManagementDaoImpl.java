package com.dsep.dao.dsepmeta.databackup.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.databackup.BackupManagementDao;
import com.dsep.entity.dsepmeta.BackupManagement;
import com.dsep.entity.enumeration.databackup.BackupType;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class BackupManagementDaoImpl extends DsepMetaDaoImpl<BackupManagement, String>
	implements BackupManagementDao{

	private String getTableName(){
		return super.getTableName("B", "MANAGEMENT");
	}
	
	private String dateFormat = "yyyyMMdd_HHmmss";// 版本号中的时间格式
	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * 以字符串类型返回当前的时间，时间格式由类的dateFormat变量所定
	 * 
	 * @return 格式化系统时间
	 */
	protected String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(this.getDateFormat());
		return sdf.format(new Date());
	}
	
	/**
	 * 获取学位中心预公示前备份数据的版本号
	 * @return
	 */
	private String getCenterPublicVersionId(){
		return "center_public_"+getCurrentTime();
	}
	

	public String addBackup(BackupManagement backup){
		String id = super.save(backup);
		if( id != null && id != "")
			return backup.getVersionId();
		else
			return null;
	}
	
	/**
	 * 获取备份时的版本号
	 * 
	 * @param schoolId
	 * @param disciplineId
	 * @return
	 */
	private String getVersionId(String schoolId, String disciplineId) {
		return schoolId + "_" + disciplineId + "_" + getCurrentTime();
	}
	
	@Override
	public String addDisciplineBackup(String unitId, String discId) {
		// TODO Auto-generated method stub
		BackupManagement newBackup = new BackupManagement();
		String versionId = getVersionId(unitId,discId);
		newBackup.setVersionId(versionId);
		newBackup.setDiscId(discId);
		newBackup.setUnitId(unitId);
		newBackup.setBackupTime(new Date());
		newBackup.setBackupType("1");
		return addBackup(newBackup);
	}

	

	@Override
	public List<BackupManagement> getAllVersion(int pageSize,int pageIndex,
			String unitId, String discId) {
		// TODO Auto-generated method stub
		String sql = "select * from " + this.getTableName() + " where unit_id = ? "
				+ "and disc_id = ? and backup_type = ?";
		String[] values = new String[3];
		values[0] = unitId;
		values[1] = discId;
		values[2] = "1";
		List<BackupManagement> testBackupManagements = super.sqlPage(sql, pageIndex, pageSize, values);
		return testBackupManagements;
	}

	@Override
	public int deleteVersion(String versionId) {
		// TODO Auto-generated method stub
		String sql = "delete from " + this.getTableName() 
				+ " where VERSION_ID = ?";
		Object[] values = new Object[1];
		values[0] = versionId;
		return super.sqlBulkUpdate(sql, values);
	}

	@Override
	public int getDisciplineCount(String unitId, String discId) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from " + this.getTableName() + 
				" where UNIT_ID = ? and DISC_ID = ? and backup_type = ?";
		Object[] values = new Object[3];
		values[0] = unitId;
		values[1] = discId;
		values[2] = "1";
		return super.sqlCount(sql, values);
	}

	@Override
	public String addCenterPublicityBackup() {
		// TODO Auto-generated method stub
		BackupManagement newBackup = new BackupManagement();
		newBackup.setBackupType(BackupType.PUBLIC.getStatus());
		newBackup.setBackupTime(new Date());
		newBackup.setVersionId(getCenterPublicVersionId());
		return addBackup(newBackup);
	}

	@Override
	public String getEarliestVersionId(String unitId,String discId) {
		// TODO Auto-generated method stub
		String sql = "select version_id from (select version_id from "+
			this.getTableName() + " where unit_id = '"+unitId+"' and disc_id = '"
			+ discId + "' and backup_type = '1' "
			+ "order by backup_time) where rownum <= 1 ";
		Object test = super.sqlUniqueResult(sql);
		return test.toString();
	}
}
