package com.dsep.vm.expert;

import com.dsep.domain.dsepmeta.expert.EvalAchvAndScore;
import com.dsep.entity.expert.EvalQuestion;
import com.dsep.entity.expert.EvalResult;
import com.dsep.util.Dictionaries;

public class EvalAchvVM {
	private String discName;
	private String unitName;
	private String discId;
	private String unitId;
	private String score;
	private String evaluate;
	private String id;
	// 问题表的id
	private String questionId;
	// 结果表中的id
	private String resultId;

	private String discipline;
	private String evalQuestionName;
	private String collectId1;
	private String collectId2;
	private String collectId3;
	private String subQuestionId;
	private EvalQuestion question;

	public EvalAchvVM(EvalAchvAndScore evalAchievementAndScore,
			EvalResult result, String discId, int id) {
		//this.id = evalAchievementAndScore.getResultId();
		this.id = String.valueOf(id);
		//this.discipline = evalAchievementAndScore.getId();
		this.collectId1 = evalAchievementAndScore.getCollectId1();
		this.collectId2 = evalAchievementAndScore.getCollectId2();
		this.collectId3 = evalAchievementAndScore.getCollectId3();
		this.evalQuestionName = evalAchievementAndScore.getQuestionName();
		this.questionId = evalAchievementAndScore.getQuestionId();
		this.discId = discId;
		this.unitId = evalAchievementAndScore.getUnitId();
		//this.evaluate = score;
		if (result != null) {
			this.resultId = result.getId();
		}
		this.unitName = Dictionaries.getUnitName(this.unitId);
		this.discName = Dictionaries.getDisciplineName(this.discId);
		if (null != result) {
			this.score = result.getEvalValue();
			this.evaluate = result.getEvalValue();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public String getEvalQuestionName() {
		return evalQuestionName;
	}

	public void setEvalQuestionName(String evalQuestionName) {
		this.evalQuestionName = evalQuestionName;
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

	public EvalQuestion getQuestion() {
		return question;
	}

	public void setQuestion(EvalQuestion question) {
		this.question = question;
	}

	public String getDiscName() {
		return discName;
	}

	public void setDiscName(String discName) {
		this.discName = discName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDiscId() {
		return discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

}