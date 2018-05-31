package com.dsep.entity.dsepmeta;

import java.io.Serializable;
import java.util.Date;

public class LogicCheckIdentityResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3478778647582926129L;

	private String id;
	private String unitId;
	private String discId;
	private String userId;
	private Date inputDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

}
