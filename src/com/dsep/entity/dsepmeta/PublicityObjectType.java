package com.dsep.entity.dsepmeta;

/**
 * 在提出异议时，每个实体对应的异议类型均是配置好的
 * @author Pangeneral
 *
 */
public class PublicityObjectType {
	private String id;
	private String objectTypeName;
	private String objectEntityId;
	private String objectAttrId;
	private String objectAttrName;
	
	/**
	 * 反馈时的反馈答复类型，包括修改、删除、保留三种
	 * modifyType的格式为"1;2",数字表示可能的答复类型，以数字隔开
	 */
	private String feedbackModifyType;
	
	
	public String getObjectAttrName() {
		return objectAttrName;
	}
	public void setObjectAttrName(String objectAttrName) {
		this.objectAttrName = objectAttrName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getObjectTypeName() {
		return objectTypeName;
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	public String getObjectEntityId() {
		return objectEntityId;
	}
	public void setObjectEntityId(String objectEntityId) {
		this.objectEntityId = objectEntityId;
	}
	public String getObjectAttrId() {
		return objectAttrId;
	}
	public void setObjectAttrId(String objectAttrId) {
		this.objectAttrId = objectAttrId;
	}
	public String getFeedbackModifyType() {
		return feedbackModifyType;
	}
	public void setFeedbackModifyType(String modifyType) {
		this.feedbackModifyType = modifyType;
	}
	
	
}
