package com.dsep.entity.dsepmeta;

public class NormResult {

	private String id;
	private String entityId;
	private String entityChsName;
	private String fieldName;
	private String fieldChsName;
	private String oldValue;
	private String normValue;
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
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNormValue() {
		return normValue;
	}
	public void setNormValue(String normValue) {
		this.normValue = normValue;
	}
	public String getEntityChsName() {
		return entityChsName;
	}
	public void setEntityChsName(String entityChsName) {
		this.entityChsName = entityChsName;
	}
	public String getFieldChsName() {
		return fieldChsName;
	}
	public void setFieldChsName(String fieldChsName) {
		this.fieldChsName = fieldChsName;
	}
	
	
}
