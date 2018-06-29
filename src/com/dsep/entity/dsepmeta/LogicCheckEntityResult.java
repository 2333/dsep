package com.dsep.entity.dsepmeta;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 该entity用于表示某张表中的所有记录的所有属性的逻辑检查情况 相当于一个汇总表，如果所有记录都通过检查，passed字段会表示该表通过
 * 
 */
public class LogicCheckEntityResult implements Serializable {
	private static final long serialVersionUID = 1047811110272643L;
	private String id;
	private String unitId;
	private String discId;
	private String entityId;
	private String entityChsName;
	private String userId;
	private Date checkDate;
	private String conclusion;
	// true 表示有错误
	private Boolean hasError;

	// true 表示有警告
	private Boolean hasWarn;

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

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}


	public Boolean getHasError() {
		return hasError;
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}

	public Boolean getHasWarn() {
		return hasWarn;
	}

	public void setHasWarn(Boolean hasWarn) {
		this.hasWarn = hasWarn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
