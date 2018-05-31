package com.dsep.entity.dsepmeta;

import java.io.Serializable;
/**
 * 公共库比对字段映射表
 * @author Monar
 *
 */
public class PubFieldConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4835634201651436917L;
	
	private String id;
	private String mapTableId;
	private String publibField;
	private String entityField;
	private String entityChsField;
	private char fieldType;
	private String normRule;
	private String compareRule;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMapTableId() {
		return mapTableId;
	}
	public void setMapTableId(String mapTableId) {
		this.mapTableId = mapTableId;
	}
	
	public String getPublibField() {
		return publibField;
	}
	public void setPublibField(String publibField) {
		this.publibField = publibField;
	}
	
	public String getEntityField() {
		return entityField;
	}
	public void setEntityField(String entityField) {
		this.entityField = entityField;
	}
	
	public String getEntityChsField() {
		return entityChsField;
	}
	public void setEntityChsField(String entityChsField) {
		this.entityChsField = entityChsField;
	}
	
	public char getFieldType() {
		return fieldType;
	}
	public void setFieldType(char fieldType) {
		this.fieldType = fieldType;
	}
	
	public String getNormRule() {
		return normRule;
	}
	public void setNormRule(String normRule) {
		this.normRule = normRule;
	}
	
	public String getCompareRule() {
		return compareRule;
	}
	public void setCompareRule(String compareRule) {
		this.compareRule = compareRule;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
