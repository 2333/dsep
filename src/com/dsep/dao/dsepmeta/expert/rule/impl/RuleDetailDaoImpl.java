package com.dsep.dao.dsepmeta.expert.rule.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.expert.rule.RuleDetailDao;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;

public class RuleDetailDaoImpl extends
		DsepMetaDaoImpl<ExpertSelectionRuleDetail, String> implements
		RuleDetailDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpertSelectionRuleDetail> getAllRuleDetailsByRuleId(
			String ruleId) {
		String hql = "select detail from ExpertSelectionRuleDetail detail where detail.rule.id='"
				+ ruleId + "'";
		List<ExpertSelectionRuleDetail> details = super.hqlFind(hql);
		return details;
	}

	@Override
	public ExpertSelectionRuleDetail getRuleDetailByDetailId(String detailId) {
		return super.get(detailId);
	}

	@Override
	public void update(ExpertSelectionRuleDetail detail) {
		super.saveOrUpdate(detail);
	}

	@Override
	public void delete(ExpertSelectionRuleDetail detail) {
		super.delete(detail);
	}

}
