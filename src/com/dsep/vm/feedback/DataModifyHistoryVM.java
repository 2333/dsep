package com.dsep.vm.feedback;

import java.util.Date;

import com.dsep.entity.dsepmeta.DataModifyHistory;
import com.dsep.util.DateProcess;

public class DataModifyHistoryVM {
	private String Id;
	private String entityId;
	private String entityName;
	private String entityItemId;
	private String attrEnsName;
	private String attrName;
	private String attrOriginalValue;
	private String attrModifyValue;
	private String modifyTime;
	private String seqNo;
	private String unitId;
	private String discId;
	private String operateUserId;
	private String modifyType;//修改类型，包括修改和删除
	private String modifySource;//修改来源，包括反馈和数据修改
	
	public DataModifyHistoryVM(DataModifyHistory modifyHistory){
		setEntityId(modifyHistory.getEntityId());
		setEntityItemId(modifyHistory.getEntityItemId());
		setEntityName(modifyHistory.getEntityName());
		setModifyTime(modifyHistory.getModifyTime());
		setSeqNo(modifyHistory.getSeqNo());
		setUnitId(modifyHistory.getUnitId());
		setDiscId(modifyHistory.getDiscId());
		setOperateUserId(modifyHistory.getOperateUserId());
		setAttrName(modifyHistory.getAttrName());
		setAttrOriginalValue(modifyHistory.getAttrOriginalValue());
		setAttrModifyValue(modifyHistory.getAttrModifyValue());
		setId(modifyHistory.getId());
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

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
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

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
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

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		if( modifyTime != null )
			this.modifyTime = DateProcess.getShowingTime(modifyTime);
		else
			this.modifyTime = "/";
	}

	public String getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(int seqNo) {
		if( seqNo > 0)
			this.seqNo = seqNo+"";
		else
			this.seqNo = "/";
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
