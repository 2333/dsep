package com.dsep.entity.dsepmeta;

import java.io.Serializable;
import java.util.Date;

public class SimilarityEntry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String id;
	private String entityId;
	private String entityChsName;
	private String userId;
	private Date checkDate;
	private char simFlag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	public String getEntityChsName() {
		return entityChsName;
	}
	public void setEntityChsName(String entityChsName) {
		this.entityChsName = entityChsName;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public char getSimFlag() {
		return simFlag;
	}
	public void setSimFlag(char simFlag) {
		this.simFlag = simFlag;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
