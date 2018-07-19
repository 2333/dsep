package com.dsep.entity.dsepmeta;

import java.util.Date;

public class PublicityManagement {

	private String id;
	private String publicRoundName;
	private String backupVersionId;
	private Date beginTime;
	private Date actualBeginTime;
	private Date endTime;
	private Date actualEndTime;
	private String status;//批次当前的状态，预公示、公示等
	private String remark;
	private String openStatus;//批次是否开启
	private String recentClose;
	
	
	public Date getActualBeginTime() {
		return actualBeginTime;
	}

	public void setActualBeginTime(Date actualBeginTime) {
		this.actualBeginTime = actualBeginTime;
	}

	public String getRecentClose() {
		return recentClose;
	}

	public String isRecentClose() {
		return recentClose;
	}

	public void setRecentClose(String recentClose) {
		this.recentClose = recentClose;
	}

	public String getOpenStatus() {
		return openStatus;
	}

	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus;
	}
	
	public Date getActualEndTime() {
		return actualEndTime;
	}

	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public String getBackupVersionId() {
		return backupVersionId;
	}

	public void setBackupVersionId(String backupVersionId) {
		this.backupVersionId = backupVersionId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublicRoundName() {
		return publicRoundName;
	}

	public void setPublicRoundName(String publicRoundName) {
		this.publicRoundName = publicRoundName;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
