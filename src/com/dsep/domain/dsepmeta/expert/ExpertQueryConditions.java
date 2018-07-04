package com.dsep.domain.dsepmeta.expert;

/**
 * inner开头表示在本库中查询 outer开头表示需要跨库查询 接口设计准备将inner和outer统一处理
 * 
 */
public class ExpertQueryConditions {
	private String innerExpertId;
	// 这个字段外库中没有,这里只是为了对称设计查询接口
	private String outerExpertId;

	private String innerExpertName;
	// 通过姓名查找未被选中的专家
	private String outerExpertName;

	private Integer innerCurrentCondition;
	// 这个字段外库中没有,这里只是为了对称设计查询接口
	private Integer outerCurrentCondition;

	// 专家参评的学科
	private String innerExpertDisc1;
	private String outerExpertDisc1;

	private String innerExpertDisc2;
	private String outerExpertDisc2;

	private String innerExpertNumber;
	// 通过专家码查找未被选中的专家
	private String outerExpertNumber;

	// 本库中没有这个字段
	private String innerExpertUnit;
	private String outerExpertUnit;

	// 本库中没有这个字段
	private String innerExpertIs211;
	private String outerExpertIs211;

	// 本库中没有这个字段
	private String innerExpertIs985;
	private String outerExpertIs985;

	private String currentBatchId;
	
	public String getCurrentBatchId() {
		return currentBatchId;
	}

	public void setCurrentBatchId(String currentBatchId) {
		this.currentBatchId = currentBatchId;
	}

	public String getInnerExpertId() {
		return innerExpertId;
	}

	public void setInnerExpertId(String innerExpertId) {
		this.innerExpertId = innerExpertId;
	}

	public String getOuterExpertId() {
		return outerExpertId;
	}

	public void setOuterExpertId(String outerExpertId) {
		this.outerExpertId = outerExpertId;
	}

	public String getInnerExpertName() {
		return innerExpertName;
	}

	public void setInnerExpertName(String innerExpertName) {
		this.innerExpertName = innerExpertName;
	}

	public String getOuterExpertName() {
		return outerExpertName;
	}

	public void setOuterExpertName(String outerExpertName) {
		this.outerExpertName = outerExpertName;
	}

	public Integer getInnerCurrentCondition() {
		return innerCurrentCondition;
	}

	public void setInnerCurrentCondition(Integer innerCurrentCondition) {
		this.innerCurrentCondition = innerCurrentCondition;
	}

	public Integer getOuterCurrentCondition() {
		return outerCurrentCondition;
	}

	public void setOuterCurrentCondition(Integer outerCurrentCondition) {
		this.outerCurrentCondition = outerCurrentCondition;
	}

	public String getInnerExpertDisc1() {
		return innerExpertDisc1;
	}

	public void setInnerExpertDisc1(String innerExpertDisc1) {
		this.innerExpertDisc1 = innerExpertDisc1;
	}

	public String getOuterExpertDisc1() {
		return outerExpertDisc1;
	}

	public void setOuterExpertDisc1(String outerExpertDisc1) {
		this.outerExpertDisc1 = outerExpertDisc1;
	}

	public String getInnerExpertDisc2() {
		return innerExpertDisc2;
	}

	public void setInnerExpertDisc2(String innerExpertDisc2) {
		this.innerExpertDisc2 = innerExpertDisc2;
	}

	public String getOuterExpertDisc2() {
		return outerExpertDisc2;
	}

	public void setOuterExpertDisc2(String outerExpertDisc2) {
		this.outerExpertDisc2 = outerExpertDisc2;
	}

	public String getInnerExpertNumber() {
		return innerExpertNumber;
	}

	public void setInnerExpertNumber(String innerExpertNumber) {
		this.innerExpertNumber = innerExpertNumber;
	}

	public String getOuterExpertNumber() {
		return outerExpertNumber;
	}

	public void setOuterExpertNumber(String outerExpertNumber) {
		this.outerExpertNumber = outerExpertNumber;
	}

	public String getInnerExpertUnit() {
		return innerExpertUnit;
	}

	public void setInnerExpertUnit(String innerExpertUnit) {
		this.innerExpertUnit = innerExpertUnit;
	}

	public String getOuterExpertUnit() {
		return outerExpertUnit;
	}

	public void setOuterExpertUnit(String outerExpertUnit) {
		this.outerExpertUnit = outerExpertUnit;
	}

	public String getInnerExpertIs211() {
		return innerExpertIs211;
	}

	public void setInnerExpertIs211(String innerExpertIs211) {
		this.innerExpertIs211 = innerExpertIs211;
	}

	public String getOuterExpertIs211() {
		return outerExpertIs211;
	}

	public void setOuterExpertIs211(String outerExpertIs211) {
		this.outerExpertIs211 = outerExpertIs211;
	}

	public String getInnerExpertIs985() {
		return innerExpertIs985;
	}

	public void setInnerExpertIs985(String innerExpertIs985) {
		this.innerExpertIs985 = innerExpertIs985;
	}

	public String getOuterExpertIs985() {
		return outerExpertIs985;
	}

	public void setOuterExpertIs985(String outerExpertIs985) {
		this.outerExpertIs985 = outerExpertIs985;
	}

}
