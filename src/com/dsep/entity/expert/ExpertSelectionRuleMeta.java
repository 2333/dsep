package com.dsep.entity.expert;

public class ExpertSelectionRuleMeta {
	private int id;
	private Boolean isPrior;
	private String ruleENName;
	private String reflectSymbol;
	private String ruleCHName;
	private String operatorName;
	private int weight;
	private String matchedFuncName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getIsPrior() {
		return isPrior;
	}

	public void setIsPrior(Boolean isPrior) {
		this.isPrior = isPrior;
	}

	public String getRuleENName() {
		return ruleENName;
	}

	public void setRuleENName(String ruleENName) {
		this.ruleENName = ruleENName;
	}

	public String getReflectSymbol() {
		return reflectSymbol;
	}

	public void setReflectSymbol(String reflectSymbol) {
		this.reflectSymbol = reflectSymbol;
	}

	public String getRuleCHName() {
		return ruleCHName;
	}

	public void setRuleCHName(String ruleCHName) {
		this.ruleCHName = ruleCHName;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getMatchedFuncName() {
		return matchedFuncName;
	}

	public void setMatchedFuncName(String matchedFuncName) {
		this.matchedFuncName = matchedFuncName;
	}

}
