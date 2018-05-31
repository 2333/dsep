package com.dsep.dao.dsepmeta.expert.rule.impl;

import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.expert.rule.RuleMetaDao;
import com.dsep.entity.expert.ExpertSelectionRuleMeta;

public class RuleMetaDaoImpl extends DsepMetaDaoImpl<ExpertSelectionRuleMeta, String>
		implements RuleMetaDao {
	/**
	 * 规则元信息表
	 */
	private String getTableName(){
		return super.getTableName("E", "RULE_META");
	}
	
	@Override
	public List<ExpertSelectionRuleMeta> getAll() {
		String sql = "select * from " + getTableName();
		return super.sqlFind(sql);
	}
	
}