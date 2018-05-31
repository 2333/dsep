package com.meta.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.meta.domain.MetaCheckRuleDomain;

@Transactional(propagation=Propagation.SUPPORTS)
public interface MetaCheckRuleService {

	/**
	 * 根据属性校验ID获得属性校验领域对象
	 * @param attrCheckRuleId（该ID是在属性元信息表的逻辑校验规则字段中描述的，对应为属性校验规则表中的主键ID，不是规则表的ID）
	 * @return
	 */
	public abstract MetaCheckRuleDomain getById(String attrCheckRuleId);
}
