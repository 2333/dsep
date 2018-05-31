package com.dsep.domain.dsepmeta.expert;

import java.util.List;
import java.util.Map;

import com.dsep.entity.expert.ExpertSelectionRuleDetail;

public class ExpertAndDiscUnitAndRule {
	private List<ExpertSelectionRuleDetail> ruleDetails;
	private List<OuterExpert> experts;
	private Map<String, List<String>> discipline_units;

	public List<ExpertSelectionRuleDetail> getRuleDetails() {
		return ruleDetails;
	}

	public void setRuleDetails(List<ExpertSelectionRuleDetail> ruleDetails) {
		this.ruleDetails = ruleDetails;
	}

	public List<OuterExpert> getExperts() {
		return experts;
	}

	public void setExperts(List<OuterExpert> experts) {
		this.experts = experts;
	}

	public Map<String, List<String>> getDiscipline_units() {
		return discipline_units;
	}

	public void setDiscipline_units(Map<String, List<String>> discipline_units) {
		this.discipline_units = discipline_units;
	}

}
