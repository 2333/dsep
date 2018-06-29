package com.dsep.domain.dsepmeta.calculaterule;

import com.dsep.entity.dsepmeta.IndexMap;
import com.dsep.entity.expert.EvalResult;

public class IndexDomain {
	private EvalResult evalResult;
	private String indexItemId;
	private String discId;
	
	public IndexDomain(EvalResult evalResult, String indexItemId, String discId) {
		this.evalResult = evalResult;
		this.indexItemId = indexItemId;
		this.discId = discId;
	}
	
	public EvalResult getEvalResult() {
		return evalResult;
	}


	public void setEvalResult(EvalResult evalResult) {
		this.evalResult = evalResult;
	}


	public String getIndexItemId() {
		return indexItemId;
	}
	public void setIndexItemId(String indexItemId) {
		this.indexItemId = indexItemId;
	}
	public String getDiscId() {
		return discId;
	}
	public void setDiscId(String discId) {
		this.discId = discId;
	}
	
	
}
