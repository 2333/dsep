package com.dsep.entity.dsepmeta;

import java.util.Date;

public class DataModifyHistory {
	
	private String Id;
	private String entityId;
	private String entityName;
	private String entityItemId;
	private String attrEnsName;
	private String attrName;
	private String attrOriginalValue;
	private String attrModifyValue;
	private Date modifyTime;
	private int SeqNo;
	private String unitId;
	private String discId;
	private String operateUserId;
	private String modifyType;//修改类型，包括修改和删除
	private String modifySource;//修改来源，包括反馈和数据修改
	
	public DataModifyHistory(){
		setSeqNo(-1);
	}
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getEntityItemId() {
		return entityItemId;
	}
	public void setEntityItemId(String entityItemId) {
		this.entityItemId = entityItemId;
	}
	public String getAttrEnsName() {
		return attrEnsName;
	}
	public void setAttrEnsName(String attrEnsName) {
		this.attrEnsName = attrEnsName;
	}
	public String getAttrOriginalValue() {
		return attrOriginalValue;
	}
	public void setAttrOriginalValue(String attrOriginalValue) {
		this.attrOriginalValue = attrOriginalValue;
	}
	public String getAttrModifyValue() {
		return attrModifyValue;
	}
	public void setAttrModifyValue(String attrModifyValue) {
		this.attrModifyValue = attrModifyValue;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public int getSeqNo() {
		return SeqNo;
	}
	public void setSeqNo(int seqNo) {
		SeqNo = seqNo;
	}
	public String getOperateUserId() {
		return operateUserId;
	}
	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}
	public String getModifyType() {
		return modifyType;
	}
	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}
	public String getModifySource() {
		return modifySource;
	}
	public void setModifySource(String modifySource) {
		this.modifySource = modifySource;
	}
	
	
	
}
