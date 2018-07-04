package com.dsep.dao.dsepmeta.expert.rule.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.expert.rule.RuleDao;
import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;

public class RuleDaoImpl extends DsepMetaDaoImpl<ExpertSelectionRule, String>
		implements RuleDao {

	/**
	 * Rule表
	 * 
	 * @return
	 */
	private String getRuleTableName() {
		return super.getTableName("E", "RULE");
	}

	@Override
	public ExpertSelectionRule getRuleById(String ruleId) {
		return super.get(ruleId);
	}

	@Override
	public void cascadeSave(ExpertSelectionRule rule,
			List<ExpertSelectionRuleDetail> details) {
		// rule set id
		// rule.setId(GUID.get());
		// rule set details
		rule.setExpertSelectionRuleDetails(details);

		for (ExpertSelectionRuleDetail detail : details) {
			// detail set id
			// detail.setId(GUID.get());
			// detail set rule
			detail.setRule(rule);
			if (null == detail.getItemName()) {
				detail.setItemName("test");
			}
			// 以下是模拟，需要删除
			detail.setIsNumber(true);
			detail.setIsNecessary(true);
		}
		super.save(rule);
		return;
	}

	/**
	 * 此处底层会自动级联删除
	 */
	@Override
	public void cascadeDelete(ExpertSelectionRule rule) {
		super.delete(rule);
	}

	@Override
	public void update(ExpertSelectionRule rule) {
		super.saveOrUpdate(rule);
	}

	@Override
	public void cascadeUpdate(ExpertSelectionRule rule,
			List<ExpertSelectionRuleDetail> details) {
		// 互相set
		rule.setExpertSelectionRuleDetails(details);
		for (ExpertSelectionRuleDetail detail : details) {
			// 以下为测试，需要在前台写
			detail.setIsNecessary(true);
			detail.setIsNumber(true);
			detail.setRule(rule);
		}
		super.saveOrUpdate(rule);
	}

	@Override
	public List<ExpertSelectionRule> page(int pageIndex, int pageSize,
			Boolean desc, String orderProperName, String evalBatchId) {
		String sql = "select * from " + getRuleTableName();
		// String sql = "select * from dsep_e_rule_2014";
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (!StringUtils.isBlank(evalBatchId)) {
			params.add(evalBatchId); // 参数
			conditionColumns.add("BATCH_ID");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);

		Object[] array = params.toArray(new Object[params.size()]);
		return super.sqlPage(sql, pageIndex, pageSize, desc, orderProperName,
				array);
	}

	@Override
	public List<ExpertSelectionRule> getAll(String evalBatchId) {
		String sql = "select * from " + getRuleTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if (!StringUtils.isBlank(evalBatchId)) {
			params.add(evalBatchId); // 参数
			conditionColumns.add("BATCH_ID");// 查询条件
		}
		sql += super.sqlAndConditon(conditionColumns);

		return super.sqlFind(sql, params);
	}

	@Override
	public void setLastUsedRule(String ruleId, String batchId) {
		String sql1 = "update " + getRuleTableName()
				+ " set LAST_USED='0' where BATCH_ID='" + batchId + "'";
		super.sqlBulkUpdate(sql1);
		String sql2 = "update " + getRuleTableName()
				+ " set LAST_USED='1' where ID='" + ruleId + "' and BATCH_ID='"
				+ batchId + "'";
		super.sqlBulkUpdate(sql2);
	}

	@Override
	public ExpertSelectionRule getLastUsedRule(String batchId) {
		String sql = "select * from " + getRuleTableName()
				+ " where LAST_USED='1' and BATCH_ID='" + batchId + "'";
		List<ExpertSelectionRule> list = super.sqlFind(sql);
		if (list.size() == 0) return null;
		else return list.get(0);
	}

}
