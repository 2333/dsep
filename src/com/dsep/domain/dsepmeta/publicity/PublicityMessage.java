package com.dsep.domain.dsepmeta.publicity;

public class PublicityMessage {
	private String publicityName;
	private String actualBeginTime;
	private String beginTime;
	private String endTime;
	private String openStatus;
	private String versionId;
	
	
	
	public String getActualBeginTime() {
		return actualBeginTime;
	}
	public void setActualBeginTime(String actualBeginTime) {
		this.actualBeginTime = actualBeginTime;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getOpenStatus() {
		return openStatus;
	}
	public void setOpenStatus(String status) {
		this.openStatus = status;
	}
	public String getPublicityName() {
		return publicityName;
	}
	public void setPublicityName(String publicityName) {
		this.publicityName = publicityName;
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
	
	
}
