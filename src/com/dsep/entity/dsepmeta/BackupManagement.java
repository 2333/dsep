package com.dsep.entity.dsepmeta;

import java.io.Serializable;
import java.util.Date;

public class BackupManagement implements Serializable{
	
	
	/**
	 * 
	 */
	private String id;
	private String versionId;
	private String unitId;
	private String discId;
	private Date backupTime;
	private Date restoreTime;
	private String backupType;
	private String remark;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getDiscId() {
		return discId;
	}
	public void setDiscId(String discId) {
		this.discId = discId;
	}
	public Date getBackupTime() {
		return backupTime;
	}
	public void setBackupTime(Date backupTime) {
		this.backupTime = backupTime;
	}
	public Date getRestoreTime() {
		return restoreTime;
	}
	public void setRestoreTime(Date restoreTime) {
		this.restoreTime = restoreTime;
	}
	public String getBackupType() {
		return backupType;
	}
	public void setBackupType(String backupType) {
		this.backupType = backupType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
