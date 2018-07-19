package com.meta.service.impl;

import com.dsep.common.exception.BusinessException;
import com.meta.dao.MetaAttrCheckRuleDao;
import com.meta.dao.MetaCheckRuleDao;
import com.meta.domain.MetaCheckRuleDomain;
import com.meta.entity.MetaAttrCheckRule;
import com.meta.entity.MetaCheckRule;
import com.meta.service.MetaCheckRuleService;

public class MetaCheckRuleServiceImpl implements MetaCheckRuleService {
	private MetaCheckRuleDao metaCheckRuleDao;
	private MetaAttrCheckRuleDao metaAttrCheckRuleDao;
	@Override
	public MetaCheckRuleDomain getById(String attrCheckRuleId) {
		MetaAttrCheckRule attrCheckRule = metaAttrCheckRuleDao.get(attrCheckRuleId);
		if(attrCheckRule==null) throw new BusinessException("找不到对应的规则信息,规则编号：" + attrCheckRuleId);
		MetaCheckRule checkRule = metaCheckRuleDao.get(attrCheckRule.getRuleId());
		return new MetaCheckRuleDomain(checkRule, attrCheckRule);
	}
	
	
	public MetaCheckRuleDao getMetaCheckRuleDao() {
		return metaCheckRuleDao;
	}
	public void setMetaCheckRuleDao(MetaCheckRuleDao metaCheckRuleDao) {
		this.metaCheckRuleDao = metaCheckRuleDao;
	}
	public MetaAttrCheckRuleDao getMetaAttrCheckRuleDao() {
		return metaAttrCheckRuleDao;
	}
	public void setMetaAttrCheckRuleDao(MetaAttrCheckRuleDao metaAttrCheckRuleDao) {
		this.metaAttrCheckRuleDao = metaAttrCheckRuleDao;
	}
}
