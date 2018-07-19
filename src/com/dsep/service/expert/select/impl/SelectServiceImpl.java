package com.dsep.service.expert.select.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dsep.dao.dsepmeta.expert.rule.RuleDao;
import com.dsep.dao.dsepmeta.expert.rule.RuleDetailDao;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.dao.dsepmeta.expert.selection.OuterExpertDao;
import com.dsep.domain.dsepmeta.expert.ExpertNumberGroupByUnitId;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.Discipline;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.Expert;
import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.expert.select.ExpertCRUDService;
import com.dsep.service.expert.select.ExpertUtilService;
import com.dsep.service.expert.select.SelectService;
import com.dsep.service.expert.select.SelectionStrategyDelegationService;
import com.dsep.util.expert.ExpertEvalCurrentStatus;
import com.dsep.util.expert.eval.EvalBatchStatus;

public class SelectServiceImpl implements SelectService {
	private ExpertDao expertDao;
	private RuleDetailDao ruleDetailDao;
	private RuleDao ruleDao;
	private OuterExpertDao outerExpertDao;
	private SelectionStrategyDelegationService unitDivisionSelectionStrategyService;
	private ExpertCRUDService expertCRUDService;
	private ExpertUtilService expertUtilService;
	private DisciplineService disciplineService;

	public static Logger logger1 = Logger.getLogger("selectLog");

	
	/**
	 * 选择步骤： 
	 * 1.选择之前，从已选专家库中清除未发送邮件的专家 
	 * 2.获得输入条件、输入数据 
	 * 3.执行SelectionStrategyService接口的实现类进行遴选、输出 
	 * 4.保存到已选专家库中
	 */
	@Override
	public void select(String ruleId, String batchId, Boolean isReSelect)
			throws InstantiationException, IllegalAccessException {
		List<Expert> list = new ArrayList<Expert>();
		ExpertSelectionRule rule  = null;
		EvalBatch           batch = null;
		List<ExpertSelectionRuleDetail> ruleDetails = null;
		
		// 如果是补选，需要获得最近一次使用的遴选规则
		if (isReSelect) {
			rule = ruleDao.getLastUsedRule(batchId);
			ruleId = rule.getId();
		}
		
		// 步骤1.
		// 以下都是输入信息
		// 输入1.规则明细
		ruleDetails = ruleDetailDao.getAllRuleDetailsByRuleId(ruleId);
		rule        = ruleDao.get(ruleId);
		batch       = rule.getEvalBatch();
		batchId     = batch.getId();
		
		logger1.info("遴选规则ID为：" + ruleId + "\r\n\r\n");

		
		if (!isReSelect) {
			// 设置本批次最近使用的一个规则
			setLastUsedRule(ruleId, batchId);
		}

		// 步骤2.
		// 如果不是补选，在选择专家之前，先把已选专家库中的未发送邮件的专家信息清空
		// 注意：为什么不删除拒绝参评的专家？
		// 因为如果删除拒绝参评专家，他们可能又被遴选出来，又会被发送邀请邮件
		// 如果已经发送邮件了，不能删除，并做记录
		// 补选就不用删除了
		if (!isReSelect) {
			List<Expert> notDelExperts = deleteByBatch(batchId);
		}
		
		//需要log("哪些专家是没有被删除的，为什么！","warn");

		// 输入2.从外部数据库来的20W+专家信息
		List<OuterExpert> experts = null;
		//this.getExpertResultFromOtherDBDao.getAll();

		List<Discipline> allDisc = disciplineService.getAllDisciplines();
		List<String> excludedZJBHs = new ArrayList<String>();
		for (Discipline disc : allDisc) {
			String discId = disc.getId();
			excludedZJBHs = expertCRUDService
					.queryInnerExpertZJBHsByDiscIdOrDiscId2(discId, batchId);
			logger1.info("参评学科：" + discId + disc.getName());
			experts = this.outerExpertDao.getByDisc(
					discId, excludedZJBHs);
			logger1.info("待选专家数量：" + experts.size());
			// 输入3.从本系统来的参评学科信息和参评学校信息，这里需要参评单位和授权单位
			// ！！无数据！！暂定为null, null

			// 步骤3.
			// 输出信息：符合条件的专家
			List<Expert> list2 = new ArrayList<Expert>();
			if (0 == experts.size())
				continue;
			List<ExpertNumberGroupByUnitId> selectedNums = new ArrayList<ExpertNumberGroupByUnitId>();
			if (isReSelect) {
				List<ExpertEvalCurrentStatus> statusList = new ArrayList<ExpertEvalCurrentStatus>();
				statusList.add(ExpertEvalCurrentStatus.NotMailed);
				statusList.add(ExpertEvalCurrentStatus.Mailed);
				statusList.add(ExpertEvalCurrentStatus.Confirmed);
				statusList.add(ExpertEvalCurrentStatus.Finished);
				statusList.add(ExpertEvalCurrentStatus.Evaluating);
				statusList.add(ExpertEvalCurrentStatus.Remailed);
				
				// 该学科下每个学校已选的人员数目，用于补选
				selectedNums = expertUtilService
						.countExpertNumbersByConditionsGroupByUnitId(discId,
								batchId, statusList);
			}
			
			list2 = unitDivisionSelectionStrategyService.select(experts,
					ruleDetails, batch, selectedNums, disc.getId());

			saveExpertsAndCurrentDisc(list2, discId);
		}
	}

	@Override
	public void saveExpertsAndCurrentDisc(List<Expert> list,
			String currentDiscId) {
		for (Expert expert : list) 
			expertDao.addExpertSelected(expert);
		expertDao.flush();
	}
	
	@Override
	public List<Expert> deleteByBatch(String batchId) {
		return expertDao.deleteExpertsNotMailed(batchId);
	}
	
	@Override
	public void setLastUsedRule(String ruleId, String batchId) {
		ruleDao.setLastUsedRule(ruleId, batchId);
	}

	// ==================与业务逻辑无关的getter和setter========================
	public ExpertDao getExpertDao() {
		return expertDao;
	}

	public void setExpertDao(ExpertDao expertDao) {
		this.expertDao = expertDao;
	}

	public OuterExpertDao getOuterExpertDao() {
		return outerExpertDao;
	}

	public void setOuterExpertDao(OuterExpertDao outerExpertDao) {
		this.outerExpertDao = outerExpertDao;
	}

	public RuleDetailDao getRuleDetailDao() {
		return ruleDetailDao;
	}

	public void setRuleDetailDao(RuleDetailDao ruleDetailDao) {
		this.ruleDetailDao = ruleDetailDao;
	}

	public ExpertCRUDService getExpertCRUDService() {
		return expertCRUDService;
	}

	public void setExpertCRUDService(ExpertCRUDService expertCRUDService) {
		this.expertCRUDService = expertCRUDService;
	}
	
	public ExpertUtilService getExpertUtilService() {
		return expertUtilService;
	}

	public void setExpertUtilService(ExpertUtilService expertUtilService) {
		this.expertUtilService = expertUtilService;
	}

	public SelectionStrategyDelegationService getUnitDivisionSelectionStrategyService() {
		return unitDivisionSelectionStrategyService;
	}

	public void setUnitDivisionSelectionStrategyService(
			SelectionStrategyDelegationService unitDivisionSelectionStrategyService) {
		this.unitDivisionSelectionStrategyService = unitDivisionSelectionStrategyService;
	}

	public RuleDao getRuleDao() {
		return ruleDao;
	}

	public void setRuleDao(RuleDao ruleDao) {
		this.ruleDao = ruleDao;
	}

	public DisciplineService getDisciplineService() {
		return disciplineService;
	}

	public void setDisciplineService(DisciplineService disciplineService) {
		this.disciplineService = disciplineService;
	}
}
