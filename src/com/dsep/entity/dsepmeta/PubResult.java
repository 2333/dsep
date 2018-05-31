package com.dsep.entity.dsepmeta;

import java.io.Serializable;

/**
 * 公共库比对结果表
 * @author Monar
 *
 */
public class PubResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6981166945976605024L;
	
	private String id;
	private String unitId;
	private String discId;
	private String entityId;
	private String entityChsName;
	private int seqNo;
	private String flagField;
	private String localItemId;
	private String localField;
	private String localChsField;
	private String localValue;
	private String publibId;
	private String pubItemId;
	private String pubValue;
	private char compareResult;
	
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
	public String getLocalItemId() {
		return localItemId;
	}
	public void setLocalItemId(String localItemId) {
		this.localItemId = localItemId;
	}
	public String getLocalField() {
		return localField;
	}
	public void setLocalField(String localField) {
		this.localField = localField;
	}
	public String getLocalValue() {
		return localValue;
	}
	public void setLocalValue(String localValue) {
		this.localValue = localValue;
	}
	public String getPublibId() {
		return publibId;
	}
	public void setPublibId(String publibId) {
		this.publibId = publibId;
	}
	public String getPubItemId() {
		return pubItemId;
	}
	public void setPubItemId(String pubItemId) {
		this.pubItemId = pubItemId;
	}
	public String getPubValue() {
		return pubValue;
	}
	public void setPubValue(String pubValue) {
		this.pubValue = pubValue;
	}
	public char getCompareResult() {
		return compareResult;
	}
	public void setCompareResult(char compareResult) {
		this.compareResult = compareResult;
	}
	
	public String getEntityChsName() {
		return entityChsName;
	}
	public void setEntityChsName(String entityChsName) {
		this.entityChsName = entityChsName;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public String getFlagField() {
		return flagField;
	}
	public void setFlagField(String flagField) {
		this.flagField = flagField;
	}
	public String getLocalChsField() {
		return localChsField;
	}
	public void setLocalChsField(String localChsField) {
		this.localChsField = localChsField;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
