package com.dsep.entity.dsepmeta;

public class NormConfigAttr {

	private String id;
	private String entityId;
	private String entityChsName;
	private String fieldName;
	private String fieldChsName;
	private String value;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
