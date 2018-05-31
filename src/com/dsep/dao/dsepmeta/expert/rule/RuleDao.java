package com.dsep.dao.dsepmeta.expert.rule;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;

/**
 * 
 * @author p_next
 * 处理rule的DAO
 * 当然，其中也包含了关于ruleDetail的级联操作，具体详见代码
 * 其实很多级联操作数据库都会根据设定的级联条件自动设置，但是这里仍然要强调cascade
 * 提示维护人员，rule和ruleDetail是1对N的级联关系
 */
public interface RuleDao extends DsepMetaDao<ExpertSelectionRule, String> {
	public abstract List<ExpertSelectionRule> page(int pageIndex, int pageSize,
			Boolean desc, String orderProperName, String evalBatchId);

	/**
	 * 
	 * @param ruleId
	 * @return
	 * 
	 * 通过ruleId来获取rule实体
	 */
	public abstract ExpertSelectionRule getRuleById(String ruleId);

	/**
	 * 
	 * @param rule
	 * @param details
	 * @return
	 * 
	 * 级联保存rule和其ruleDetail
	 */
	public abstract void cascadeSave(ExpertSelectionRule rule,
			List<ExpertSelectionRuleDetail> details);

	/**
	 * 
	 * @param rule
	 * 
	 * 级联删除rule和其ruleDetail
	 */
	public abstract void cascadeDelete(ExpertSelectionRule rule);

	/**
	 * 
	 * @param rule
	 * @param details
	 * 
	 * 级联更新rule和其ruleDetail
	 */
	public abstract void cascadeUpdate(ExpertSelectionRule rule,
			List<ExpertSelectionRuleDetail> details);

	/**
	 * 
	 * @param rule
	 * 
	 * 单独通过ruleId更新rule，不级联
	 */
	public abstract void update(ExpertSelectionRule rule);
	
	public abstract List<ExpertSelectionRule> getAll(String evalBatchId);
	
	public abstract void setLastUsedRule(String ruleId, String batchId);
	
	public abstract ExpertSelectionRule getLastUsedRule(String batchId);
}
