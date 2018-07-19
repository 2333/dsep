package com.dsep.entity.project;

public class ItemExecute {
	private String id;
	private String executeStatus;
	private String txtRecord;
	private String score;
	//private PassItem item;
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	/*
	public PassItem getItem() {
		return item;
	}
	public void setItem(PassItem item) {
		this.item = item;
	}*/
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExecuteStatus() {
		return executeStatus;
	}
	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}
	public String getTxtRecord() {
		return txtRecord;
	}
	public void setTxtRecord(String txtRecord) {
		this.txtRecord = txtRecord;
	}
	public PassItem getPassItem() {
		return passItem;
	}
	public void setPassItem(PassItem passItem) {
		this.passItem = passItem;
	}
	private PassItem passItem;
}
