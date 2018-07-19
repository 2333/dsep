package com.dsep.vm.publicity;


import com.dsep.entity.Attachment;
import com.dsep.entity.dsepmeta.OriginalObjection;
import com.dsep.service.publicity.objection.OriginalObjectionService;
import com.dsep.vm.util.VmValueProcess;

public class OriginalObjectionVM {
	
	/*{name:'problemUnitId',index:'problemUnitId',align:'center',width:15},
    {name:'problemDiscId',index:'problemDiscId',align:'center',width:15},
    {name:'problemEntityId',index:'problemEntityId',align:'center',width:15},
    {name:'problemAttrId',index:'problemAttrId',align:'center',width:15},
    {name:'problemSeqNo',index:'problemSeqNo',align:'center',width:15},*/
	
	private String objectionId;
	private String objectUnitId;
	private String objectDiscId;
	
	private String objectEntityId;
	private String objectDataId;
	
	private String problemUnitId;
	private String problemDiscId;
	private String problemEntityName;
	private String importantAttrValue;
	private String problemSeqNo;
	
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
	
	private Attachment proveMaterial;
	
	
	public OriginalObjectionVM() {
		
	}

	public OriginalObjectionVM(OriginalObjection originalObjection,OriginalObjectionService originalObjectionService) {
		setObjectionId(originalObjection.getId());
		setObjectUnitId(originalObjection.getObjectUnitId());
		setObjectDiscId(originalObjection.getObjectDiscId());
		
		setUnitObjectContent(originalObjection.getUnitObjectContent());
		setUnitObjectType(originalObjection.getUnitObjectType());
		setUnitStatus(originalObjection.getUnitStatus());
		setUnitObjectCollectAttrId(originalObjection.getUnitObjectCollectAttrId());
		setUnitObjectCollectAttrName(originalObjection.getUnitObjectCollectAttrName());
		setUnitObjectCollectAttrValue(originalObjection.getUnitObjectCollectAttrValue());
		
		setCenterObjectContent(originalObjection.getCenterObjectContent());
		setCenterObjectType(originalObjection.getCenterObjectType());
		setCenterStatus(originalObjection.getCenterStatus());
		setCenterObjectCollectAttrId(originalObjection.getCenterObjectCollectAttrId());
		setCenterObjectCollectAttrName(originalObjection.getCenterObjectCollectAttrName());
		setCenterObjectCollectAttrValue(originalObjection.getCenterObjectCollectAttrValue());
		
		setObjectEntityId(originalObjection.getObjectCollectEntityId());
		setObjectDataId(originalObjection.getObjectCollectItemId());
		setProveMaterial(originalObjection.getProveMaterial());
		setProblemUnitId(originalObjection.getProblemUnitId());
		setProblemDiscId(originalObjection.getProblemDiscId());
		setProblemEntityName(originalObjection.getObjectCollectEntityName());
		setProblemSeqNo(originalObjection.getSeqNo());
		setImportantAttrValue(originalObjection.getImportantAttrValue());
	}


	public String getUnitObjectCollectAttrId() {
		return unitObjectCollectAttrId;
	}

	public void setUnitObjectCollectAttrId(String unitObjectCollectAttrId) {
		this.unitObjectCollectAttrId = VmValueProcess.getShowingString(unitObjectCollectAttrId);
	}

	public String getUnitObjectCollectAttrName() {
		return unitObjectCollectAttrName;
	}

	public void setUnitObjectCollectAttrName(String unitObjectCollectAttrName) {
		this.unitObjectCollectAttrName = VmValueProcess.getShowingString(unitObjectCollectAttrName);
	}

	public String getUnitObjectCollectAttrValue() {
		return unitObjectCollectAttrValue;
	}

	public void setUnitObjectCollectAttrValue(String unitObjectCollectAttrValue) {
		this.unitObjectCollectAttrValue = VmValueProcess.getShowingString(unitObjectCollectAttrValue);
	}

	public String getUnitStatus() {
		return unitStatus;
	}

	public void setUnitStatus(String unitStatus) {
		this.unitStatus = unitStatus;
	}

	public String getCenterObjectCollectAttrId() {
		return centerObjectCollectAttrId;
	}

	public void setCenterObjectCollectAttrId(String centerObjectCollectAttrId) {
		this.centerObjectCollectAttrId = VmValueProcess.getShowingString(centerObjectCollectAttrId);
	}

	public String getCenterObjectCollectAttrName() {
		return centerObjectCollectAttrName;
	}

	public void setCenterObjectCollectAttrName(String centerObjectCollectAttrName) {
		this.centerObjectCollectAttrName = VmValueProcess.getShowingString(centerObjectCollectAttrName);
	}

	public String getCenterObjectCollectAttrValue() {
		return centerObjectCollectAttrValue;
	}

	public void setCenterObjectCollectAttrValue(String centerObjectCollectAttrValue) {
		this.centerObjectCollectAttrValue = VmValueProcess.getShowingString(centerObjectCollectAttrValue);
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

	public String getCenterStatus() {
		return centerStatus;
	}

	public void setCenterStatus(String centerStatus) {
		this.centerStatus = centerStatus;
	}

	public void setProblemSeqNo(String problemSeqNo) {
		this.problemSeqNo = problemSeqNo;
	}

	public String getImportantAttrValue() {
		return importantAttrValue;
	}

	public void setImportantAttrValue(String importantAttrValue) {
		if( importantAttrValue != null && !importantAttrValue.equals("")){
			this.importantAttrValue = importantAttrValue;
		}
		else{
			this.importantAttrValue = "/";
		}
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

	public String getProblemEntityName() {
		return problemEntityName;
	}

	public void setProblemEntityName(String problemEntityName) {
		if( problemEntityName != null && !problemEntityName.equals("")){
			this.problemEntityName = problemEntityName;
		}
		else{
			this.problemEntityName = "/";
		}
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


	public String getUnitObjectType() {
		return unitObjectType;
	}

	public void setUnitObjectType(String unitObjectType) {
		this.unitObjectType = unitObjectType;
	}

	public String getObjectionId() {
		return objectionId;
	}

	public void setObjectionId(String id) {
		this.objectionId = id;
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

	public String getUnitObjectContent() {
		return unitObjectContent;
	}

	public void setUnitObjectContent(String unitObjectContent) {
		this.unitObjectContent = unitObjectContent;
	}

	public String getObjectEntityId() {
		return objectEntityId;
	}

	public void setObjectEntityId(String objectEntityId) {
		this.objectEntityId = objectEntityId;
	}

	public String getObjectDataId() {
		return objectDataId;
	}

	public void setObjectDataId(String objectDataId) {
		this.objectDataId = objectDataId;
	}

	public Attachment getProveMaterial() {
		return proveMaterial;
	}

	public void setProveMaterial(Attachment proveMaterial) {
		this.proveMaterial = proveMaterial;
	}

	
}
