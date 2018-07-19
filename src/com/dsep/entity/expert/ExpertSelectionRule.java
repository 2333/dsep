package com.dsep.entity.expert;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(value = { "expertSelectionRuleDetails", "evalBatch" })
public class ExpertSelectionRule {
	private String id;
	private String ruleName;
	private Timestamp createDate;
	private Timestamp modifyDate;
	private Boolean lastUsed;
	private String commentForRule;
	private List<ExpertSelectionRuleDetail> expertSelectionRuleDetails = new ArrayList<ExpertSelectionRuleDetail>(
			0);
	private EvalBatch evalBatch = new EvalBatch();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Boolean getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Boolean lastUsed) {
		this.lastUsed = lastUsed;
	}

	public String getCommentForRule() {
		return commentForRule;
	}

	public void setCommentForRule(String commentForRule) {
		this.commentForRule = commentForRule;
	}

	public List<ExpertSelectionRuleDetail> getExpertSelectionRuleDetails() {
		return expertSelectionRuleDetails;
	}

	public void setExpertSelectionRuleDetails(
			List<ExpertSelectionRuleDetail> expertSelectionRuleDetails) {
		this.expertSelectionRuleDetails = expertSelectionRuleDetails;
	}

	public EvalBatch getEvalBatch() {
		return evalBatch;
	}

	public void setEvalBatch(EvalBatch evalBatch) {
		this.evalBatch = evalBatch;
	}

}
