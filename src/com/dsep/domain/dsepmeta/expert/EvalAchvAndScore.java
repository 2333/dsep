package com.dsep.domain.dsepmeta.expert;

import com.dsep.entity.expert.EvalAchv;
import com.dsep.entity.expert.EvalQuestion;

public class EvalAchvAndScore {
	private String questionId;
	private String subQuestionId;
	private String resultId;
	private String unitId;
	// 该数据对应的采集项
	private String collectId1;
	private String collectId2;
	private String collectId3;
	private String questionName;
	private String value;

	public EvalAchvAndScore(EvalQuestion q, EvalAchv a) {
		this.questionId = q.getId();
		this.subQuestionId = q.getSubQuestionId();

		this.unitId = q.getUnitId();
		this.questionName = a.getQuestionName();
		this.collectId1 = a.getCollectId1();
		this.collectId2 = a.getCollectId2();
		this.collectId3 = a.getCollectId3();
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getSubQuestionId() {
		return subQuestionId;
	}

	public void setSubQuestionId(String subQuestionId) {
		this.subQuestionId = subQuestionId;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getCollectId1() {
		return collectId1;
	}

	public void setCollectId1(String collectId1) {
		this.collectId1 = collectId1;
	}

	public String getCollectId2() {
		return collectId2;
	}

	public void setCollectId2(String collectId2) {
		this.collectId2 = collectId2;
	}

	public String getCollectId3() {
		return collectId3;
	}

	public void setCollectId3(String collectId3) {
		this.collectId3 = collectId3;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}