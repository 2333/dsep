package com.dsep.controller.expert.evaluation;

import java.util.Date;

public class UtilEvalResultFromJSP {
	private String resultId;
	private String questionId;

	private String value;

	private Date firstEvalTime;
	private Date lastEvalTime;

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


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getFirstEvalTime() {
		return firstEvalTime;
	}

	public void setFirstEvalTime(Date firstEvalTime) {
		this.firstEvalTime = firstEvalTime;
	}

	public Date getLastEvalTime() {
		return lastEvalTime;
	}

	public void setLastEvalTime(Date lastEvalTime) {
		this.lastEvalTime = lastEvalTime;
	}

}
