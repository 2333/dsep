package com.dsep.vm.feedback;

import java.util.Date;

import com.ctc.wstx.util.StringUtil;
import com.dsep.entity.Attachment;
import com.dsep.entity.dsepmeta.FeedbackResponse;
import com.dsep.entity.enumeration.EnumModule;
import com.dsep.entity.enumeration.feedback.CenterAdvice;
import com.dsep.entity.enumeration.feedback.FeedbackType;
import com.dsep.entity.enumeration.feedback.ResponseType;
import com.dsep.service.publicity.feedback.FeedbackResponseService;
import com.dsep.util.StringProcess;
import com.dsep.vm.util.VmValueProcess;

public class FeedbackResponseVM {
	
	private String id;
	private String problemUnitId;//学校
	private String problemDiscId;//学科
	private String problemCollectEntityId;//采集实体ID
	private String problemCollectEntityName;//采集实体名称
	private String problemCollectItemId;//采集项ID
	private String problemCollectAttrId;//采集项字段ID
	private String problemCollectAttrName;//采集项字段名称
	private String problemCollectAttrValue;//采集项字段值
	private String importantAttrValue;//采集项关键字段值
	private String problemSeqNo;//采集项的序号
	private String feedbackType;
	private String problemContent;//问题的实体
	private String responseType;//答复类型，保留、删除或修改
	private String adviceValue;//答复类型为修改时用，建议修改的值
	private Attachment proveMaterial;//证明材料ID，答复类型为保留或修改时可能需要
	private String centerAdvice; //中心处理结论，同意或不同意
	private int sameItemNumber;//对相同的数据项，反馈数据源中是否只有该条问题数据
	private String similarityProblemContent;
	
	public FeedbackResponseVM(){
		
	}

	
	public FeedbackResponseVM(FeedbackResponse response){
		setFeedbackResponse(response);
	}
	
	public FeedbackResponseVM(FeedbackResponse response,FeedbackResponseService feedbackResponseService) throws IllegalArgumentException, IllegalAccessException{
		setFeedbackResponse(response);	
		setSameItemNumber(response,feedbackResponseService);
	}
	
	private void setFeedbackResponse(FeedbackResponse response){
		setId(response.getId());
		setProblemUnitId(response.getProblemUnitId());
		setProblemDiscId(response.getProblemDiscId());
		setProblemCollectEntityId(response.getProblemCollectEntityId());
		setProblemCollectEntityName(response.getProblemCollectEntityName());
		setProblemCollectItemId(response.getProblemCollectItemId());
		setProblemCollectAttrId(response.getProblemCollectAttrId());
		setProblemCollectAttrName(response.getProblemCollectAttrName());
		setProblemCollectAttrValue(response.getProblemCollectAttrValue());
		setImportantAttrValue(response.getImportantAttrValue());
		setProblemSeqNo(response.getProblemSeqNo());
		setFeedbackType(response.getFeedbackType());
		setProblemContent(response.getProblemContent());
		setResponseType(response.getResponseType());
		setAdviceValue(response.getAdviceValue());
		setCenterAdvice(response.getCenterAdvice());
		setProveMaterial(response.getProveMaterial());
		setSimilarityProblemContent("数据重复填报");
	}
	
	public int getSameItemNumber() {
		return sameItemNumber;
	}
	public void setSameItemNumber(FeedbackResponse response,FeedbackResponseService feedbackResponseService) throws IllegalArgumentException, IllegalAccessException {
		this.sameItemNumber = feedbackResponseService.getSameProblemNumber(response);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public void setProblemSeqNo(String problemSeqNo) {
		if(problemSeqNo.equals("0")||problemSeqNo.equals("-1"))
			this.problemSeqNo = "/";
		else
			this.problemSeqNo = problemSeqNo;
	}
	
	

	public String getSimilarityProblemContent() {
		return similarityProblemContent;
	}


	public void setSimilarityProblemContent(String similarityProblemContent) {
		this.similarityProblemContent = similarityProblemContent;
	}


	public String getProblemCollectAttrValue() {
		return problemCollectAttrValue;
	}

	public void setProblemCollectAttrValue(String problemCollectAttrValue) {
		if( !StringProcess.isNull(problemCollectAttrValue))
			this.problemCollectAttrValue = problemCollectAttrValue;
		else
			this.problemCollectAttrValue = "/";
	}

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		EnumModule module = new FeedbackType();
		this.feedbackType = module.getShowingByStatus(feedbackType);
	}

	public void setSameItemNumber(int sameItemNumber) {
		this.sameItemNumber = sameItemNumber;
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
		if( !StringProcess.isNull(problemCollectEntityName))
			this.problemCollectEntityName = problemCollectEntityName;
		else
			this.problemCollectEntityName = "/";
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
		this.problemCollectAttrId = VmValueProcess.getShowingString(problemCollectAttrId);
	}
	public String getProblemCollectAttrName() {
		return problemCollectAttrName;
	}
	public void setProblemCollectAttrName(String problemCollectAttrName) {
		if( !StringProcess.isNull(problemCollectAttrName))
			this.problemCollectAttrName = VmValueProcess.getShowingString(problemCollectAttrName);
		else
			this.problemCollectAttrName = "/";
	}
	public String getImportantAttrValue() {		
		return importantAttrValue;
	}
	public void setImportantAttrValue(String importantAttrValue) {
		this.importantAttrValue = VmValueProcess.getShowingString(importantAttrValue);
	}
	public String getProblemSeqNo() {
		return problemSeqNo;
	}
	public void setProblemSeqNo(int problemSeqNo) {
		if( problemSeqNo <= 0 ){
			this.problemSeqNo = "/";
		}
		else
			this.problemSeqNo = problemSeqNo+"";
	}
	public String getProblemContent() {
		return problemContent;
	}
	public void setProblemContent(String problemContent) {
		this.problemContent = problemContent;
	}

	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		EnumModule module = new ResponseType();
		if( !StringProcess.isNull(responseType))
			this.responseType = module.getShowingByStatus(responseType);
		else
			this.responseType = "暂无";
	}
	public String getAdviceValue() {
		return adviceValue;
	}
	public void setAdviceValue(String adviceValue) {
		if( !StringProcess.isNull(adviceValue))
			this.adviceValue = adviceValue;
		else
			this.adviceValue = "/";
	}

	public String getCenterAdvice() {
		return centerAdvice;
	}

	public void setCenterAdvice(String centerAdvice) {
		EnumModule module = new CenterAdvice();
		if( !StringProcess.isNull(centerAdvice))
			this.centerAdvice = module.getShowingByStatus(centerAdvice);
		else
			this.centerAdvice = "/";
	}

	public Attachment getProveMaterial() {
		return proveMaterial;
	}

	public void setProveMaterial(Attachment proveMaterial) {
		this.proveMaterial = proveMaterial;
	}

	
	
}
