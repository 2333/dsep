package com.dsep.domain.dsepmeta.calculaterule;

import com.dsep.entity.expert.EvalResult;

public class IndicWtDomain {
	private EvalResult evalResult;
	private String discId;
	private String catId;
	private String indicWtId;
	
	public IndicWtDomain(EvalResult evalResult, String discId, String catId,
			String indicWtId) {
		this.evalResult = evalResult;
		this.discId = discId;
		this.catId = catId;
		this.indicWtId = indicWtId;
	}

	public EvalResult getEvalResult() {
		return evalResult;
	}

	public void setEvalResult(EvalResult evalResult) {
		this.evalResult = evalResult;
	}

	public String getDiscId() {
		return discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getIndicWtId() {
		return indicWtId;
	}

	public void setIndicWtId(String indicWtId) {
		this.indicWtId = indicWtId;
	}
	
}
