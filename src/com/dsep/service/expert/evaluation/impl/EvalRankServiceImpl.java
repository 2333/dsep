package com.dsep.service.expert.evaluation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.dao.dsepmeta.expert.evaluation.EvalResultDao;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.entity.expert.EvalResult;
import com.dsep.entity.expert.Expert;
import com.dsep.service.expert.evaluation.EvalRankService;
import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.service.flow.EvalFlowService;
import com.dsep.util.expert.eval.QuickSort;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalRepuVM;

public class EvalRankServiceImpl implements EvalRankService {

	private EvalFlowService evalFlowService;	
	private EvalService evalService;
	private ExpertDao expertDao;
	private EvalResultDao evalResultDao;
	
	/**
	 * 只能init一次，应该是专家确认打分的时候初始化
	 * 之后不能再用!
	 *//*
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void initAllUnitsRanking(String questionId, String expertId) {
		EvalQuestionVM evalQuestionVM = null;
				//evalService.getQuestions(expertId,
				//Integer.valueOf(QuestionType.RANKING.toString())).get(0);
		EvalQuestion question = evalQuestionVM.getQuestion();
		// 通过expertId获得ExpertSelected实体
		ExpertSelected expert = expertSelectedDao.get(expertId);
		// 通过ExpertSelected实体获得专家打分学科ID:discId
		String discId = expert.getDiscId();
		String discId2 = expert.getDiscId2();
		// 在遴选时可能是以第一学科入选，也可能是以第二学科入选，但专家只能有一个打分学科
		discId = (null != discId) ? discId : discId2;
		// 第四个参数表示"参评"
		PageVM<EvalVM> evalVMPage = evalFlowService.getCollectEvalByPage(null,
				discId, null, true, null, 0, 0, true, null);
		@SuppressWarnings("unchecked")
		List<EvalVM> evalVMList = (List<EvalVM>) evalVMPage.getGridData().get("rows");
		
		// 通过discCode获得该学科有多少个学校参评
		List<String> units = new ArrayList<String>();
		for (EvalVM evalVM : evalVMList) {
			units.add(evalVM.getEval().getUnitId());
		}
		// 以下为模拟
		units.add("10001");units.add("10002");
		units.add("10007");units.add("10008");units.add("10010");
		units.add("10502");units.add("11001");units.add("18701");
		units.add("19001");units.add("19201");units.add("19608");
		units.add("21001");units.add("23001");units.add("30507");
		
		List<EvalResult> resultList = constructEvalResult(expertId, discId, units);
		for (EvalResult result : resultList) {
			evalResultDao.saveEvalResult(result, question);
		}
	}*/
	
	private List<EvalResult> constructEvalResult(String expertId, String discId, List<String> unitIds) {
		List<EvalResult> resultList = new ArrayList<EvalResult>();
		int initRanking = 1;
		for (String unitId : unitIds) {
			EvalResult result = new EvalResult();
			//result.setDisciplineId(discId);
			//result.setUnitId(unitId);
			result.setExpertId(expertId);
			// 按照取得的参评学校的初始顺序作为学校的初始排名
			result.setEvalValue(String.valueOf(initRanking++));
			resultList.add(result);
		}
		return resultList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateAUnitRanking(String questionId, String resultId,
			String expertId, String oldPosition, String newPosition) {
		evalResultDao.updateUnitRanking(questionId, expertId, resultId, oldPosition, newPosition);
	}

	@Override
	public PageVM<EvalRepuVM> getAllUnitsRanking(
			String questionId, String expertId) {
		// 通过expertId获得ExpertSelected实体
		Expert expert = expertDao.get(expertId);
		// 通过ExpertSelected实体获得专家打分学科ID:discId
		String discId = expert.getDiscId();
		String discId2 = expert.getDiscId2();
		// 在遴选时可能是以第一学科入选，也可能是以第二学科入选，但专家只能有一个打分学科
		discId = (null != discId) ? discId : discId2;
		
		List<EvalResult> resultList = null; 
				//evalResultDao.getEvalResultsByQuestionId(expertId,
				//questionId, discId);
		QuickSort.sort(resultList, 0, resultList.size() - 1);
		List<EvalRepuVM> vms = new ArrayList<EvalRepuVM>();
		EvalRepuVM vm = null;

		for (EvalResult result : resultList) {
			/*vm = new EvalRepuVM(
					//result.getDisciplineId(),
					//result.getUnitId(), 
					"", "", result.getEvalValue(), result.getId());
			vms.add(vm);*/
		}
		int totalCount = vms.size();
		PageVM<EvalRepuVM> result = new PageVM<EvalRepuVM>(
				1, totalCount, 1000, vms);
		return result;
		
	}

	
	
	// 与业务逻辑无关的getter和setter
	public EvalFlowService getEvalFlowService() {
		return evalFlowService;
	}

	public void setEvalFlowService(EvalFlowService evalFlowService) {
		this.evalFlowService = evalFlowService;
	}

	public EvalService getEvalService() {
		return evalService;
	}

	public void setEvalService(EvalService evalService) {
		this.evalService = evalService;
	}

	public ExpertDao getExpertDao() {
		return expertDao;
	}

	public void setExpertDao(ExpertDao expertDao) {
		this.expertDao = expertDao;
	}

	public EvalResultDao getEvalResultDao() {
		return evalResultDao;
	}

	public void setEvalResultDao(EvalResultDao evalResultDao) {
		this.evalResultDao = evalResultDao;
	}

	
	

}
