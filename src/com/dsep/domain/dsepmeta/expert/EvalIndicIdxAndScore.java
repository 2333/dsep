package com.dsep.domain.dsepmeta.expert;

import com.dsep.entity.dsepmeta.Index;
import com.dsep.entity.dsepmeta.IndexMap;
import com.dsep.vm.expert.L3rdIdxMapVM;

public class EvalIndicIdxAndScore {
	// id:JSJ000I01 idxId:JSJ000

	private String score;
	private String state;
	private String questionId;
	private String resultId;
	private L3rdIdxMapVM l3rdIdxMapVM;

	public EvalIndicIdxAndScore(L3rdIdxMapVM l3rdIdxMapVM) {
		this.l3rdIdxMapVM = l3rdIdxMapVM;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public L3rdIdxMapVM getL3rdIdxMapVM() {
		return l3rdIdxMapVM;
	}

	public void setL3rdIdxMapVM(L3rdIdxMapVM l3rdIdxMapVM) {
		this.l3rdIdxMapVM = l3rdIdxMapVM;
	}

}