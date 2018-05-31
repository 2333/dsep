package com.dsep.entity.expert;

public class EvalProgress {
	private String id;
	private String expertId;
	private Integer questionType;
	private String subQuestionChName;
	private String subQuestionId;
	private Integer totalNumber;
	private Integer finishedNumber;
	private Integer sequ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public String getSubQuestionChName() {
		return subQuestionChName;
	}

	public void setSubQuestionChName(String subQuestionChName) {
		this.subQuestionChName = subQuestionChName;
	}

	public String getSubQuestionId() {
		return subQuestionId;
	}

	public void setSubQuestionId(String subQuestionId) {
		this.subQuestionId = subQuestionId;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Integer getFinishedNumber() {
		return finishedNumber;
	}

	public void setFinishedNumber(Integer finishedNumber) {
		this.finishedNumber = finishedNumber;
	}

	public Integer getSequ() {
		return sequ;
	}

	public void setSequ(Integer sequ) {
		this.sequ = sequ;
	}

}
