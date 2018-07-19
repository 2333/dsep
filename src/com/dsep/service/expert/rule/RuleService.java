package com.dsep.service.expert.rule;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.ExpertSelectionRuleVM;

/**
 * 
 * @author fanghongyu rule的增删改查 当然，其中也包含了一些级联的ruleDetail的处理，详情请看每一个方法
 */
@Transactional(propagation = Propagation.SUPPORTS)
public interface RuleService {
	/**
	 * 
	 * @param rule
	 * @param details
	 * 
	 *            增加rule和其ruleDetail，前台会传来一个rule实体和n个ruleDetail实体
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void saveRuleAndDetails(ExpertSelectionRule rule,
			List<ExpertSelectionRuleDetail> details, String evalBatchId);

	/**
	 * 
	 * @param ruleId
	 * @return
	 * 通过前台传来的ruleId级联删除rule和其ruleDetail实体
	 * 如果是最近一次使用的rule则不允许删除，返回false
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract boolean deleteRuleById(String ruleId);

	/**
	 * 
	 * @param rule
	 * @param details
	 * 
	 *            通过前台传来的一个rule实体和n个ruleDetail实体，对rule实体进行级联更新
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public abstract void updateRuleAndDetails(String oldRuleId,
			ExpertSelectionRule rule, List<ExpertSelectionRuleDetail> details);

	/**
	 * 
	 * @return
	 * 
	 *         获取所有的rule
	 */
	public abstract List<ExpertSelectionRule> getRules(String evalBatchId);

	/**
	 * 
	 * @param ruleId
	 * @return
	 * 
	 *         通过ruleId来查找rule
	 */
	public abstract ExpertSelectionRule getRuleById(String ruleId);

	/**
	 * 
	 * @param ruleId
	 * @return
	 * 
	 *         通过ruleId来查找级联的details
	 */
	public abstract List<ExpertSelectionRuleDetail> getDetailsByRuleId(
			String ruleId);

	/**
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderProperName
	 * @return
	 * 
	 *         分页的形式显示rule
	 */
	public abstract PageVM<ExpertSelectionRuleVM> getExpertSelectionRules(
			int pageIndex, int pageSize, Boolean desc, String orderProperName,
			String evalBatchId);

	public abstract ExpertSelectionRule getLastUsedRuleInABatch(String batchId);
}
