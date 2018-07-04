package com.dsep.dao.dsepmeta.expert.rule;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;

public interface RuleDetailDao extends DsepMetaDao<ExpertSelectionRuleDetail, String> {
	public abstract List<ExpertSelectionRuleDetail> getAllRuleDetailsByRuleId(String ruleId);

	public abstract ExpertSelectionRuleDetail getRuleDetailByDetailId(String detailId);
	
	public abstract void update(ExpertSelectionRuleDetail detail);
	
	public abstract void delete(ExpertSelectionRuleDetail detail);
}
