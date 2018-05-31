package com.dsep.domain.dsepmeta.expert;

import java.util.List;

import com.dsep.util.expert.eval.ExpertType;
import com.dsep.util.expert.eval.QType;

public class PaperAndQuestionsFromFront {
	private ExpertType expertType;
	private List<QType> questionTypes;

	public ExpertType getExpertType() {
		return expertType;
	}

	public void setExpertType(ExpertType expertType) {
		this.expertType = expertType;
	}

	public List<QType> getQuestionTypes() {
		return questionTypes;
	}

	public void setQuestionTypes(List<QType> questionTypes) {
		this.questionTypes = questionTypes;
	}

}
