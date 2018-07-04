package com.dsep.entity.dsepmeta;

import java.io.Serializable;
import java.util.Date;

import com.dsep.entity.Attachment;

public class FeedbackResponse implements Serializable{


	private String id;
	private String feedbackRoundId;//反馈批次ID
	private String problemUnitId;//学校
	private String problemDiscId;//学科
	private String problemCollectEntityId;//采集实体ID
	private String problemCollectEntityName;//采集实体名称
	private String problemCollectItemId;//采集项ID
	private String problemCollectAttrId;//采集项字段ID
	private String problemCollectAttrName;//采集项字段名称
	private String problemCollectAttrValue;//有问题的采集项字段值
	private String importantAttrValue;//采集项关键字段值
	private int problemSeqNo;//采集项的序号
	private String problemContent;//问题的实体
	private String feedbackType;//反馈类型，目前有六类
	private String feedbackStatus;//状态，处理中、已提交等
	private Date responseTime;//第一次答复的时间
	private Date modifyTime;//最近修改时间
	private Date submitTime;//提交时间
	private String responseType;//答复类型，保留、删除或修改
	private String responseAdvice;//答复意见，为一段文本
	private String adviceValue;//答复类型为修改时用，建议修改的值
	private Attachment proveMaterial;//证明材料
	private String centerAdvice;//最终的处理结论，同意或不同意学校的意见
	/*private String proveMaterialId;//证明材料ID，答复类型为保留或修改时可能需要*/
	
	
	public FeedbackResponse(){
		setProblemSeqNo(-1);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFeedbackRoundId() {
		return feedbackRoundId;
	}
	public void setFeedbackRoundId(String feedbackRoundId) {
		this.feedbackRoundId = feedbackRoundId;
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
	public String getProblemCollectEntityId() {
		return problemCollectEntityId;
	}
	public void setProblemCollectEntityId(String problemCollectEntityId) {
		this.problemCollectEntityId = problemCollectEntityId;
	}
	public String getProblemCollectEntityName() {
		return problemCollectEntityName;
	}
	

	public void setProblemCollectEntityName(String problemCollectEntityName) {
		this.problemCollectEntityName = problemCollectEntityName;
	}
	public String getProblemCollectItemId() {
		return problemCollectItemId;
	}
	public void setProblemCollectItemId(String problemCollectItemId) {
		this.problemCollectItemId = problemCollectItemId;
	}
	public String getProblemCollectAttrId() {
		return problemCollectAttrId;
	}
	public void setProblemCollectAttrId(String problemCollectAttrId) {
		this.problemCollectAttrId = problemCollectAttrId;
	}
	public String getProblemCollectAttrName() {
		return problemCollectAttrName;
	}
	public void setProblemCollectAttrName(String problemCollectAttrName) {
		this.problemCollectAttrName = problemCollectAttrName;
	}
	public String getImportantAttrValue() {
		return importantAttrValue;
	}
	public void setImportantAttrValue(String importantAttrValue) {
		this.importantAttrValue = importantAttrValue;
	}
	public int getProblemSeqNo() {
		return problemSeqNo;
	}
	public void setProblemSeqNo(int problemSeqNo) {
		this.problemSeqNo = problemSeqNo;
	}
	public String getProblemContent() {
		return problemContent;
	}
	public void setProblemContent(String problemContent) {
		this.problemContent = problemContent;
	}
	public String getFeedbackType() {
		return feedbackType;
	}
	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}
	public String getFeedbackStatus() {
		return feedbackStatus;
	}
	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getAdviceValue() {
		return adviceValue;
	}
	public void setAdviceValue(String adviceValue) {
		this.adviceValue = adviceValue;
	}
	public Attachment getProveMaterial() {
		return proveMaterial;
	}
	public void setProveMaterial(Attachment proveMaterial) {
		this.proveMaterial = proveMaterial;
	}
	public String getResponseAdvice() {
		return responseAdvice;
	}
	public void setResponseAdvice(String responseAdvice) {
		this.responseAdvice = responseAdvice;
	}

	public String getProblemCollectAttrValue() {
		return problemCollectAttrValue;
	}

	public void setProblemCollectAttrValue(String problemCollectAttrValue) {
		this.problemCollectAttrValue = problemCollectAttrValue;
	}

	public String getCenterAdvice() {
		return centerAdvice;
	}

	public void setCenterAdvice(String centerAdvice) {
		this.centerAdvice = centerAdvice;
	}
	
	
	
}