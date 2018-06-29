package com.dsep.vm.expert;

import com.dsep.entity.expert.EvalAchv;
import com.dsep.entity.expert.EvalQuestion;

public class EvalQuestionVM {
	private String questionId;
	private String discipline;
	private String evalQuestionName;
	private String collectId1;
	private String collectId2;
	private String collectId3;
	private String collectId1Name;
	private String collectId2Name;
	private String collectId3Name;
	private String subQuestionId;
	private EvalQuestion question;

	public EvalQuestionVM(EvalQuestion question, String discCode, EvalAchv achv) {
		this.question = question;
		this.questionId = question.getId();
		this.discipline = discCode;
		this.subQuestionId = question.getSubQuestionId();
		if (achv != null) {
			this.collectId1 = achv.getCollectId1();
			this.collectId2 = achv.getCollectId2();
			this.collectId3 = achv.getCollectId3();
			this.collectId1Name = achv.getCollectId1Name();
			this.collectId2Name = achv.getCollectId2Name();
			this.collectId3Name = achv.getCollectId3Name();

			this.evalQuestionName = achv.getQuestionName();
		}
	}

	public EvalQuestion getQuestion() {
		return question;
	}

	public void setQuestion(EvalQuestion question) {
		this.question = question;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public EvalQuestionVM() {

	}

	public String getEvalQuestionName() {
		return evalQuestionName;
	}

	public void setEvalQuestionName(String evalQuestionName) {
		this.evalQuestionName = evalQuestionName;
	}

	public EvalQuestionVM(String discipline) {
		this.discipline = discipline;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
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

	public String getSubQuestionId() {
		return subQuestionId;
	}

	public void setSubQuestionId(String subQuestionId) {
		this.subQuestionId = subQuestionId;
	}

	public String getCollectId1Name() {
		return collectId1Name;
	}

	public void setCollectId1Name(String collectId1Name) {
		this.collectId1Name = collectId1Name;
	}

	public String getCollectId2Name() {
		return collectId2Name;
	}

	public void setCollectId2Name(String collectId2Name) {
		this.collectId2Name = collectId2Name;
	}

	public String getCollectId3Name() {
		return collectId3Name;
	}

	public void setCollectId3Name(String collectId3Name) {
		this.collectId3Name = collectId3Name;
	}

}
