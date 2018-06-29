package com.dsep.entity.dsepmeta;

import java.util.Date;

public class FeedbackManagement {
	
	private String id;
	private String feedbackRoundName;
	private Date actualBeginTime;
	private Date beginTime;
	private Date endTime;
	private Date actualEndTime;
	private String status;
	private String remark;
	private String openStatus;
	private String backupVersionId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFeedbackRoundName() {
		return feedbackRoundName;
	}
	public Date getActualBeginTime() {
		return actualBeginTime;
	}
	public void setActualBeginTime(Date actualBeginTime) {
		this.actualBeginTime = actualBeginTime;
	}
	public void setFeedbackRoundName(String feedbackRoundName) {
		this.feedbackRoundName = feedbackRoundName;
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
	public Date getActualEndTime() {
		return actualEndTime;
	}
	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = actualEndTime;
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
	public String getOpenStatus() {
		return openStatus;
	}
	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus;
	}
	public String getBackupVersionId() {
		return backupVersionId;
	}
	public void setBackupVersionId(String backupVersionId) {
		this.backupVersionId = backupVersionId;
	}
	
	
}
