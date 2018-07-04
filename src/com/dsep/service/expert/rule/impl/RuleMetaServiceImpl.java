package com.dsep.service.expert.rule.impl;

import java.util.List;

import com.dsep.dao.dsepmeta.expert.rule.RuleMetaDao;
import com.dsep.entity.expert.ExpertSelectionRuleMeta;
import com.dsep.service.expert.rule.RuleMetaService;

public class RuleMetaServiceImpl implements RuleMetaService {
	private RuleMetaDao ruleMetaDao;

	@Override
	public List<ExpertSelectionRuleMeta> getAll() {
		return ruleMetaDao.getAll();
	}
	
	// getter and setter
	public RuleMetaDao getRuleMetaDao() {
		return ruleMetaDao;
	}

	public void setRuleMetaDao(RuleMetaDao ruleMetaDao) {
		this.ruleMetaDao = ruleMetaDao;
	}

}
