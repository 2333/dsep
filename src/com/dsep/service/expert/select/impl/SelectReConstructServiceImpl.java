package com.dsep.service.expert.select.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dsep.dao.dsepmeta.expert.rule.RuleDao;
import com.dsep.dao.dsepmeta.expert.rule.RuleDetailDao;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.dao.dsepmeta.expert.selection.OuterExpertDao;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.Discipline;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.Expert;
import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.expert.select.ExpertCRUDService;
import com.dsep.service.expert.select.ReConstructService;
import com.dsep.service.expert.select.SelectReConstructService;
import com.dsep.service.expert.select.SelectionStrategyDelegationService;
import com.dsep.service.expert.select.util.OuterExpertsToTreeConvertorValve;

public class SelectReConstructServiceImpl implements SelectReConstructService{
	private ExpertDao expertDao;
	private RuleDetailDao ruleDetailDao;
	private RuleDao ruleDao;
	private OuterExpertDao outerExpertDao;
	private SelectionStrategyDelegationService unitDivisionSelectionStrategyService;
	private ExpertCRUDService expertCRUDService;
	private DisciplineService disciplineService;
	private ReConstructService reConstructService;
	
	public ReConstructService getReConstructService() {
		return reConstructService;
	}
	public void setReConstructService(ReConstructService reConstructService) {
		this.reConstructService = reConstructService;
	}
	
	public static Logger logger1 = Logger.getLogger("selectLog");
	
	@Override
	public void select(String ruleId) throws InstantiationException,
			IllegalAccessException {
		List<Expert> prevAll = expertDao.getAll();
		
		List<Expert> list = new ArrayList<Expert>();
		//记录每个学校每个学科当前需要补选专家的数量
		Map<String,Map<String,Integer>> lack = new HashMap<String,Map<String,Integer>>();
		
		logger1.info("遴选规则ID为：" + ruleId + "\r\n\r\n");

		List<ExpertSelectionRuleDetail> ruleDetails = ruleDetailDao
				.getAllRuleDetailsByRuleId(ruleId);
		ExpertSelectionRule rule = ruleDao.get(ruleId);
		EvalBatch evalBatch = rule.getEvalBatch();
		//去除上次遴选选出的专家和批次的级联
		evalBatch.getExperts().removeAll(prevAll);
		//删除上次遴选的所有专家
		expertDao.deleteAll(prevAll);

		// 设置本批次最近使用的一个规则
		ruleDao.setLastUsedRule(ruleId, evalBatch.getId());
		//按YJXKM2获取补选专家
		List<OuterExpert> all = outerExpertDao.getAllByCondition();
		//转换提供补选的专家树
		Map<String,Map<String,List<OuterExpert>>> makeUpTree = OuterExpertsToTreeConvertorValve
				.convertToMakeUpTree(all);
		int startCount = 0;
		int endCount = 0;
		for(String k1:makeUpTree.keySet()){
			for(String k2:makeUpTree.get(k1).keySet())
				startCount += makeUpTree.get(k1).get(k2).size();
		}

		// 输入2.从外部数据库来的20W+专家信息
		List<OuterExpert> experts = null;

		List<Discipline> allDisc = disciplineService.getAllDisciplines();
		List<String> excludedZJBH = new ArrayList<String>();
		for (Discipline disc : allDisc) {
			logger1.info("参评学科：" + disc.getId() + disc.getName());
			experts = this.getOuterExpertDao().getByDisc2(disc.getId(), null, 1);
		
			logger1.info("待选专家数量：" + experts.size());
			// 输入3.从本系统来的参评学科信息和参评学校信息，这里需要参评单位和授权单位
			// ！！无数据！！暂定为null, null

			// 步骤3.
			// 输出信息：符合条件的专家
			List<Expert> list2 = new ArrayList<Expert>();
			if (0 == experts.size())
				continue;
			
			List<Object> objs = reConstructService.select(experts, ruleDetails, evalBatch, disc.getId(), 
					makeUpTree.get(disc.getId()));
			list2 = (List<Expert>) objs.get(0);
			makeUpTree.put(disc.getId(), (Map<String,List<OuterExpert>>)objs.get(1));
			lack.put(disc.getId(), (Map<String,Integer>)objs.get(2));
	
			for (Expert e : list2) {
				excludedZJBH.add(e.getExpertNumber());
			}
			list.addAll(list2);
			for (Expert expert : list2) {
				expertCRUDService.addExpert(expert);
			}
		}
		
		for(String k1:makeUpTree.keySet()){
			if(makeUpTree.get(k1)!=null){
				for(String k2:makeUpTree.get(k1).keySet()){
					if(makeUpTree.get(k1).keySet()!=null)
						endCount += makeUpTree.get(k1).get(k2).size();
				}
			}
		}
		logger1.info("遴选开始前可补选专家有" + startCount + "人");
		logger1.info("遴选结束后可补选专家有" + endCount + "人");
		logger1.info("\r\n\r\n");
		for(String k1:lack.keySet()){
			if(lack.get(k1)!=null){
				for(String k2:lack.get(k1).keySet()){
					if(lack.get(k1).get(k2)!=0){
						logger1.info("学科为" + k1 + "的学校" + k2 + "需补选" + lack.get(k1).get(k2) + "人");
					}	
				}
			}
		}
		for(Discipline disc:allDisc){
			if(makeUpTree.get(disc.getId())!=null&&lack.get(disc.getId())!=null){
				logger1.info("开始进行" + disc.getId() + "的补选\r\n");
				List<Object> objs = reConstructService.makeUpExperts(evalBatch, disc.getId(), makeUpTree.get(disc.getId()),
						lack.get(disc.getId()));
				List<Expert> makeUpList = (List<Expert>)objs.get(0);
				for (Expert expert : makeUpList) {
					expertCRUDService.addExpert(expert);
				}
				//lack.put(disc.getId(), (Map<String,Integer>)objs.get(1));
			}
			logger1.info("-----------------------");
		}
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
