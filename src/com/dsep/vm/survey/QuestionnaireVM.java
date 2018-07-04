package com.dsep.vm.survey;

import com.dsep.entity.survey.Questionnaire;

public class QuestionnaireVM {

	private Questionnaire QNR;
	private String quesStatusName;
	
	public String getQuesStatusName() {
		return quesStatusName;
	}

	public void setQuesStatusName(String quesStatusName) {
		this.quesStatusName = quesStatusName;
	}

	public Questionnaire getQues() {
		return QNR;
	}

	public void setQues(Questionnaire ques) {
		this.QNR = ques;
	}
	
}
