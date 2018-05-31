package com.dsep.entity.dsepmeta;

import java.io.Serializable;

/**
 * 用于记录记录的有问题的属性的逻辑检查结果
 * 比如某个用户id为1234，检查了10001学校的1000学科的E201301A这个实体
 * 其中一个记录有一个字段错误
 *
 */
public class LogicCheckAttrResult implements Serializable {
	private static final long serialVersionUID = 8831136442046315272L;
	private String id;
	private String unitId;
	private String discId;
	private String entityId;
	//?这个字段意思是?
	private String dataId;
	// 一张表中的第几行记录
	private int seqNo;
	// 一个记录的某个属性
	private String attrId;
	
	// 不同用户的逻辑检查互不影响
	private String userId;

	// 指明错误的类型，比如是"数字"问题，"日期、时间"问题或者"百分比、比例"问题
	private String errorType;
	// 具体的错误，比如"不能为负数"
	private String error;
	
	private String entityChsName;
	private String attrChsName;

	
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

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getEntityChsName() {
		return entityChsName;
	}

	public void setEntityChsName(String entityChsName) {
		this.entityChsName = entityChsName;
	}

	public String getAttrChsName() {
		return attrChsName;
	}

	public void setAttrChsName(String attrChsName) {
		this.attrChsName = attrChsName;
	}

}
