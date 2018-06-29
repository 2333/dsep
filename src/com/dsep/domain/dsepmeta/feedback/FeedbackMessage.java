package com.dsep.domain.dsepmeta.feedback;

public class FeedbackMessage {
	private String FeedbackName;
	private String beginTime;
	private String endTime;
	private String openStatus;
	private String backupVersionId;
	
	
	public String getBackupVersionId() {
		return backupVersionId;
	}
	public void setBackupVersionId(String backupVersionId) {
		this.backupVersionId = backupVersionId;
	}
	public String getFeedbackName() {
		return FeedbackName;
	}
	public void setFeedbackName(String feedbackName) {
		FeedbackName = feedbackName;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOpenStatus() {
		return openStatus;
	}
	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus;
	}
	
	
}
