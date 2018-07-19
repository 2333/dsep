package com.dsep.service.expert.rule.impl;

import java.util.ArrayList;
import java.util.List;

import com.dsep.dao.dsepmeta.expert.batch.EvalBatchDao;
import com.dsep.dao.dsepmeta.expert.rule.RuleDao;
import com.dsep.dao.dsepmeta.expert.rule.RuleDetailDao;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.service.expert.rule.RuleService;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.ExpertSelectionRuleVM;

public class RuleServiceImpl implements RuleService {
	private RuleDao ruleDao;
	private RuleDetailDao ruleDetailDao;
	private EvalBatchDao evalBatchDao;

	@Override
	public void saveRuleAndDetails(ExpertSelectionRule rule,
			List<ExpertSelectionRuleDetail> details, String evalBatchId) {
		EvalBatch evalBatch = evalBatchDao.get(evalBatchId);

		//rule.setExpertSelectionRuleDetails(details);
		for (ExpertSelectionRuleDetail detail : details) {
			detail.setRule(rule);

			// 以下4行是模拟，需要删除
			if (null == detail.getItemName())
				detail.setItemName("test");
			detail.setIsNumber(true);
			detail.setIsNecessary(true);
			rule.getExpertSelectionRuleDetails().add(detail);
		}
		evalBatch.getRules().add(rule);
		rule.setEvalBatch(evalBatch);
		ruleDao.save(rule);
	}

	@Override
	public boolean deleteRuleById(String ruleId) {
		ExpertSelectionRule rule = ruleDao.get(ruleId);
		if (rule.getLastUsed()) {
			return false;
		} else {
			ruleDao.delete(rule);
			return true;
		}
	}

	@Override
	public void updateRuleAndDetails(String oldRuleId,
			ExpertSelectionRule rule, List<ExpertSelectionRuleDetail> details) {
		ruleDao.deleteByKey(oldRuleId);
		ruleDao.cascadeSave(rule, details);
	}

	@Override
	public List<ExpertSelectionRule> getRules(String evalBatchId) {
		return ruleDao.getAll(evalBatchId);
	}

	@Override
	public ExpertSelectionRule getRuleById(String ruleId) {
		return ruleDao.get(ruleId);
	}

	@Override
	public List<ExpertSelectionRuleDetail> getDetailsByRuleId(String ruleId) {
		return ruleDetailDao.getAllRuleDetailsByRuleId(ruleId);
	}

	@Override
	public PageVM<ExpertSelectionRuleVM> getExpertSelectionRules(int pageIndex,
			int pageSize, Boolean desc, String orderProperName,
			String evalBatchId) {
		List<ExpertSelectionRule> list = ruleDao.page(pageIndex, pageSize,
				desc, orderProperName, evalBatchId);
		List<ExpertSelectionRuleVM> vmList = new ArrayList<ExpertSelectionRuleVM>();
		for (ExpertSelectionRule rule : list) {
			ExpertSelectionRuleVM vm = new ExpertSelectionRuleVM(rule);
			vmList.add(vm);
		}
		int totalCount = ruleDao.Count();
		PageVM<ExpertSelectionRuleVM> result = new PageVM<ExpertSelectionRuleVM>(
				pageIndex, totalCount, pageSize, vmList);
		return result;
	}
	
	@Override
	public ExpertSelectionRule getLastUsedRuleInABatch(String batchId) {
		return ruleDao.getLastUsedRule(batchId);
	}

	/**
	 * ===============================================================
	 * 和业务逻辑“无关”的DAO的getter和setter方法放在最下面 do not be confused
	 */
	public RuleDao getRuleDao() {
		return ruleDao;
	}

	public void setRuleDao(RuleDao ruleDao) {
		this.ruleDao = ruleDao;
	}

	public RuleDetailDao getRuleDetailDao() {
		return ruleDetailDao;
	}

	public void setRuleDetailDao(RuleDetailDao ruleDetailDao) {
		this.ruleDetailDao = ruleDetailDao;
	}

	public EvalBatchDao getEvalBatchDao() {
		return evalBatchDao;
	}

	public void setEvalBatchDao(EvalBatchDao evalBatchDao) {
		this.evalBatchDao = evalBatchDao;
	}

}
