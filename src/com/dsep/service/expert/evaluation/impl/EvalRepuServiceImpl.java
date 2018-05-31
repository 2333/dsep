package com.dsep.service.expert.evaluation.impl;

import java.util.ArrayList;
import java.util.List;

import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalPaperDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalQuestionDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalResultDao;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.domain.dsepmeta.expert.EvalRepuAndScore;
import com.dsep.entity.expert.EvalPaper;
import com.dsep.entity.expert.EvalQuestion;
import com.dsep.entity.expert.EvalResult;
import com.dsep.entity.expert.Expert;
import com.dsep.service.expert.evaluation.EvalRepuService;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalRepuVM;

public class EvalRepuServiceImpl implements EvalRepuService {
	private EvalPaperDao evalPaperDao;
	private EvalQuestionDao evalQuestionDao;
	private EvalResultDao evalResultDao;
	private ExpertDao expertDao;
	private DiscCategoryDao discCategoryDao;

	@Override
	public PageVM<EvalRepuVM> getRepuResults(CurrentBatchExpertInfo info) {
		Expert expert = expertDao.get(info.getExpertId());

		String discId = (expert.getUseDiscId()) ? expert.getDiscId()
				: expert.getDiscId2();

		String discCatId = discCategoryDao.getCatByDiscId(discId);

		String expertType = expert.getExpertType();

		// 通过专家分类和学科门类拿到卷子信息
		EvalPaper paper = evalPaperDao.getEvalPaper(expertType, discCatId,
				expert.getEvalBatch().getId());
		List<EvalQuestion> questions = evalQuestionDao.getRepuQs(paper.getId(),
				discId, false);
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getUnitId().equals(expert.getUnitId()))
				questions.remove(i);
		}
		List<EvalRepuAndScore> evalRepuAndScoreList = new ArrayList<EvalRepuAndScore>();

		// 获得一个专家对所有权重题目打的分数
		List<EvalResult> scores = evalResultDao.getResultsByQType(
				expert.getId(), QType.REPU.toInt());

		List<EvalRepuVM> vmList = new ArrayList<EvalRepuVM>();
		// 下面的for循环把IndicatorWeightAndScore的ele和分数ele一一对应，关联是score.questionId =
		// domain.questoinId
		// 并封装到VM中展示
		for (EvalQuestion q : questions) {
			Boolean findAllScoreAndNotMatch = true;
			for (EvalResult score : scores) {
				if (score.getEvalQuestion().getId().equals(q.getId())) {
					// 构造方法会set分数
					EvalRepuVM vm = new EvalRepuVM(q.getDiscId(),
							q.getUnitId(), score.getEvalValue(), score.getId(),
							q.getId());

					vmList.add(vm);
					findAllScoreAndNotMatch = false;
					break;
				}
			}
			if (findAllScoreAndNotMatch) {
				// 构造方法会set分数
				EvalRepuVM vm = new EvalRepuVM(q.getDiscId(), q.getUnitId(),
						null, null, q.getId());
				vmList.add(vm);
			}
		}

		PageVM<EvalRepuVM> pageVM = new PageVM<EvalRepuVM>(1, vmList.size(),
				vmList.size(), vmList);
		return pageVM;
	}

	@Override
	public String getRepuProg(String batchId, String expertId) {
		Expert expert = expertDao.get(expertId);
		if(expert.getUseDiscId()==null)
			expert.setUseDiscId(true);
		String discId = (expert.getUseDiscId()) ? expert.getDiscId()
				: expert.getDiscId2();
		String discCatId = discCategoryDao.getCatByDiscId(discId);
		String expertType = expert.getExpertType();

		EvalPaper paper = evalPaperDao.getEvalPaper(expertType, discCatId,
				batchId);

		List<EvalResult> actualResults = evalResultDao.getResultsByQType(
				expert.getId(), QType.REPU.toInt());

		List<EvalQuestion> questions = evalQuestionDao.getRepuQs(paper.getId(),
				discId, false);
		if (0 == questions.size()) {
			return "无任务";
		}
		Boolean allSubmitted = true;
		if (0 == actualResults.size())
			allSubmitted = false;
		for (EvalResult r : actualResults) {
			if (!"2".equals(r.getEvalValueState())) {
				allSubmitted = false;
			}
		}
		if (allSubmitted) {
			return "已提交";
		}
		if (0 == actualResults.size()) {
			return "未开始";
		} else if (actualResults.size() < questions.size()) {
			return "未完成";
		}

		for (EvalResult r : actualResults) {
			if (null == r.getEvalValue() || "".equals(r.getEvalValue())) {
				return "未完成";
			}
		}

		return "完成，未提交";
	}

	// ----------------与业务逻辑无关的getter和setter-----------------------
	public EvalQuestionDao getEvalQuestionDao() {
		return evalQuestionDao;
	}

	public void setEvalQuestionDao(EvalQuestionDao evalQuestionDao) {
		this.evalQuestionDao = evalQuestionDao;
	}

	public EvalResultDao getEvalResultDao() {
		return evalResultDao;
	}

	public void setEvalResultDao(EvalResultDao evalResultDao) {
		this.evalResultDao = evalResultDao;
	}

	public EvalPaperDao getEvalPaperDao() {
		return evalPaperDao;
	}

	public void setEvalPaperDao(EvalPaperDao evalPaperDao) {
		this.evalPaperDao = evalPaperDao;
	}

	public ExpertDao getExpertDao() {
		return expertDao;
	}

	public void setExpertDao(ExpertDao expertDao) {
		this.expertDao = expertDao;
	}

	public DiscCategoryDao getDiscCategoryDao() {
		return discCategoryDao;
	}

	public void setDiscCategoryDao(DiscCategoryDao discCategoryDao) {
		this.discCategoryDao = discCategoryDao;
	}

}
