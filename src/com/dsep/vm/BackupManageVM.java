package com.dsep.vm;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dsep.entity.dsepmeta.BackupManagement;
import com.dsep.util.DateProcess;

public class BackupManageVM implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BackupManagement backup;
	private String remark;
	private String backupTime;
	private String restoreTime;
	
	public BackupManageVM(){
		
	}
	
	public BackupManageVM(BackupManagement backupManagement){
		setBackup(backupManagement);
		setRemark(this.backup.getRemark());
		setBackupTime(this.backup.getBackupTime());
		setRestoreTime(this.backup.getRestoreTime());
	}
	
	public BackupManagement getBackup() {
		return backup;
	}
	public void setBackup(BackupManagement backup) {
		this.backup = backup;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		if( remark != null && remark != "")
			this.remark = remark;
		else
			this.remark = "暂无";
	}
	public String getBackupTime() {
		return backupTime;
	}
	public void setBackupTime(Date backupTime) {
		this.backupTime = DateProcess.getShowingTime(backupTime);
	}
	public String getRestoreTime() {
		return restoreTime;
	}
	public void setRestoreTime(Date restoreTime) {
		this.restoreTime = DateProcess.getShowingTime(restoreTime);
	}
	
	
}
