package com.dsep.entity.dsepmeta;

import java.io.Serializable;
import java.util.Date;

import org.omg.CORBA.PRIVATE_MEMBER;

public class PreEval implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4039240548549956101L;
	private String id;
	private String unitId;
	private String discId;
	private String insertUserId;
	private Date insertTime;
	private String modifyUserId;
	private Date modifyTime;
	private boolean isEval;
	private boolean isReport;
	private boolean isUnitReport;
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public boolean getIsUnitReport() {
		return isUnitReport;
	}
	public void setIsUnitReport(boolean isUnitReport) {
		this.isUnitReport = isUnitReport;
	}
	
}
