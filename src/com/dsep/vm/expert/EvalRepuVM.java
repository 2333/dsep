package com.dsep.vm.expert;

import com.dsep.util.Dictionaries;
import com.dsep.util.expert.eval.UnitCodeToUnitNameTmpNeedDel;

public class EvalRepuVM {
	private String discName;
	private String unitName;
	private String discId;
	private String unitId;
	private String score;
	private String resultId;
	private String evaluate;
	private String questionId;

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
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

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public EvalRepuVM() {

	}

	public EvalRepuVM(String discCode, String unitCode, String score,
			String resultId, String questionId) {
		this.discId = discCode;
		this.unitId = unitCode;
		this.score = score;
		//this.evaluate = score;
		this.resultId = resultId;
		
		this.unitName = Dictionaries.getUnitName(this.unitId);
		this.discName = Dictionaries.getDisciplineName(this.discId);
		this.questionId = questionId;
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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
