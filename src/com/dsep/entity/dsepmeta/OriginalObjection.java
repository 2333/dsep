package com.dsep.entity.dsepmeta;

import java.util.Date;

import com.dsep.entity.Attachment;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaEntity;

public class OriginalObjection {

	private String id;
	private String userId;
	private String currentPublicRoundId;
	private String objectCollectEntityId;
	private String objectCollectEntityName;
	private String objectCollectItemId;//采集项主键ID
	
	private String unitObjectCollectAttrId;//采集项数据有问题的字段ID
	private String unitObjectCollectAttrName;//采集项数据有问题的字段名
	private String unitObjectCollectAttrValue;//采集项有问题的字段值
	private String unitObjectContent;//单位给出的反馈意见
	private String unitObjectType;//单位给出的反馈类型
	private String unitStatus;//学校处理异议的状态，未提交、已提交
	
	private String centerObjectCollectAttrId;//中心修改后   采集项数据有问题的字段ID
	private String centerObjectCollectAttrName;//中心修改后   采集项数据有问题的字段名
	private String centerObjectCollectAttrValue;//中心修改后  采集项有问题的字段值
	private String centerObjectContent;//中心修改后的反馈意见
	private String centerObjectType;//中心修改后的异议类型
	private String centerStatus;//中心处理异议的状态，未启动、未处理、已处理
	
	private String objectUnitId;
	private String objectDiscId;
	private String problemUnitId;
	private String problemDiscId;
	private Date beginTime;
	private Date checkTime;
	private Integer seqNo;//采集项的序号
	private String importantAttrValue;//异议数据关键字段的值
	private Attachment proveMaterial;//证明材料
	
	
	public Attachment getProveMaterial() {
		return proveMaterial;
	}

	public void setProveMaterial(Attachment proveMaterial) {
		this.proveMaterial = proveMaterial;
	}

	public OriginalObjection(){
		setSeqNo(-1);
	}
	
	public String getObjectCollectEntityId() {
		return objectCollectEntityId;
	}
	
	

	public String getImportantAttrValue() {
		return importantAttrValue;
	}

	public void setImportantAttrValue(String importantAttrValue) {
		this.importantAttrValue = importantAttrValue;
	}

	public void setObjectCollectEntityId(String objectCollectEntityId) {
		this.objectCollectEntityId = objectCollectEntityId;
	}

	public String getObjectCollectEntityName() {
		return objectCollectEntityName;
	}

	public void setObjectCollectEntityName(String objectCollectEntityName) {
		this.objectCollectEntityName = objectCollectEntityName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}
	
	public String getUnitObjectCollectAttrValue() {
		return unitObjectCollectAttrValue;
	}

	public String getCenterObjectCollectAttrId() {
		return centerObjectCollectAttrId;
	}

	
	
	public String getUnitStatus() {
		return unitStatus;
	}

	public void setUnitStatus(String unitStatus) {
		this.unitStatus = unitStatus;
	}

	public String getCenterStatus() {
		return centerStatus;
	}

	public void setCenterStatus(String centerStatus) {
		this.centerStatus = centerStatus;
	}

	public void setCenterObjectCollectAttrId(String centerObjectCollectAttrId) {
		this.centerObjectCollectAttrId = centerObjectCollectAttrId;
	}

	public String getCenterObjectCollectAttrName() {
		return centerObjectCollectAttrName;
	}

	public void setCenterObjectCollectAttrName(String centerObjectCollectAttrName) {
		this.centerObjectCollectAttrName = centerObjectCollectAttrName;
	}

	public String getCenterObjectCollectAttrValue() {
		return centerObjectCollectAttrValue;
	}

	public void setCenterObjectCollectAttrValue(String centerObjectCollectAttrValue) {
		this.centerObjectCollectAttrValue = centerObjectCollectAttrValue;
	}

	public void setUnitObjectCollectAttrValue(String unitObjectCollectAttrValue) {
		this.unitObjectCollectAttrValue = unitObjectCollectAttrValue;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCurrentPublicRoundId() {
		return currentPublicRoundId;
	}

	public void setCurrentPublicRoundId(String currentPublicRoundId) {
		this.currentPublicRoundId = currentPublicRoundId;
	}

	public String getObjectCollectItemId() {
		return objectCollectItemId;
	}

	public void setObjectCollectItemId(String objectCollectItemId) {
		this.objectCollectItemId = objectCollectItemId;
	}

	public String getUnitObjectCollectAttrId() {
		return unitObjectCollectAttrId;
	}

	public void setUnitObjectCollectAttrId(String unitObjectCollectAttrId) {
		this.unitObjectCollectAttrId = unitObjectCollectAttrId;
	}

	public String getUnitObjectCollectAttrName() {
		return unitObjectCollectAttrName;
	}

	public void setUnitObjectCollectAttrName(String objectCollectAttrName) {
		this.unitObjectCollectAttrName = objectCollectAttrName;
	}

	public String getObjectUnitId() {
		return objectUnitId;
	}

	public void setObjectUnitId(String objectUnitId) {
		this.objectUnitId = objectUnitId;
	}

	public String getObjectDiscId() {
		return objectDiscId;
	}

	public void setObjectDiscId(String objectDiscId) {
		this.objectDiscId = objectDiscId;
	}

	public String getProblemUnitId() {
		return problemUnitId;
	}

	public void setProblemUnitId(String problemUnitId) {
		this.problemUnitId = problemUnitId;
	}

	public String getProblemDiscId() {
		return problemDiscId;
	}

	public void setProblemDiscId(String problemDiscId) {
		this.problemDiscId = problemDiscId;
	}

	public String getUnitObjectContent() {
		return unitObjectContent;
	}

	public void setUnitObjectContent(String unitObjectContent) {
		this.unitObjectContent = unitObjectContent;
	}

	public String getUnitObjectType() {
		return unitObjectType;
	}

	public void setUnitObjectType(String unitObjectType) {
		this.unitObjectType = unitObjectType;
	}

	public String getCenterObjectContent() {
		return centerObjectContent;
	}

	public void setCenterObjectContent(String centerObjectContent) {
		this.centerObjectContent = centerObjectContent;
	}

	public String getCenterObjectType() {
		return centerObjectType;
	}

	public void setCenterObjectType(String centerObjectType) {
		this.centerObjectType = centerObjectType;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

}
