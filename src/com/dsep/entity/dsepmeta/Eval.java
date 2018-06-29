package com.dsep.entity.dsepmeta;

import java.io.Serializable;
import java.util.Date;

public class Eval implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4709173580050822534L;
	private String id;
	private String unitId;
	private String discId;
	private String insertUserId;
	private Date insertTime;
	private String modifyUserId;
	private Date modifyTime;
	private boolean isEval;
	private boolean isUnitReport;
	private boolean isReport;
	private String discVersionNo;
	private String unitVersionNo;
	private String discStreamNo;
	private String unitStreamNo;
	private String briefId;//简况表ID
	private String discIntroduceId;//学科简介文件ID
	private Date createBriefTime;
	private int state;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public String getInsertUserId() {
		return insertUserId;
	}
	public void setInsertUserId(String insertUserId) {
		this.insertUserId = insertUserId;
	}
	
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public String getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public boolean getIsEval() {
		return isEval;
	}
	public void setIsEval(boolean isEval) {
		this.isEval = isEval;
	}
	
	public boolean getIsReport() {
		return isReport;
	}
	public void setIsReport(boolean isReport) {
		this.isReport = isReport;
	}
	
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public String getDiscVersionNo() {
		return discVersionNo;
	}
	public void setDiscVersionNo(String discVersionNo) {
		this.discVersionNo = discVersionNo;
	}
	
	public String getUnitVersionNo() {
		return unitVersionNo;
	}
	public void setUnitVersionNo(String unitVersionNo) {
		this.unitVersionNo = unitVersionNo;
	}
	
	public String getDiscStreamNo() {
		return discStreamNo;
	}
	public void setDiscStreamNo(String discStreamNo) {
		this.discStreamNo = discStreamNo;
	}
	
	public String getUnitStreamNo() {
		return unitStreamNo;
	}
	public void setUnitStreamNo(String unitStreamNo) {
		this.unitStreamNo = unitStreamNo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public boolean isUnitReport() {
		return isUnitReport;
	}
	public void setUnitReport(boolean isUnitReport) {
		this.isUnitReport = isUnitReport;
	}
	public String getBriefId() {
		return briefId;
	}
	public void setBriefId(String briefId) {
		this.briefId = briefId;
	}
	public String getDiscIntroduceId() {
		return discIntroduceId;
	}
	public void setDiscIntroduceId(String discIntroduceId) {
		this.discIntroduceId = discIntroduceId;
	}
	public Date getCreateBriefTime() {
		return createBriefTime;
	}
	public void setCreateBriefTime(Date createBriefTime) {
		this.createBriefTime = createBriefTime;
	}
	
}
