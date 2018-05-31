package com.dsep.service.expert.evaluation.impl;

import java.util.ArrayList;
import java.util.List;

import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalIndicWtDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalPaperDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalQuestionDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalResultDao;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.domain.dsepmeta.expert.EvalIndicWtAndScore;
import com.dsep.entity.expert.EvalIndicWt;
import com.dsep.entity.expert.EvalPaper;
import com.dsep.entity.expert.EvalQuestion;
import com.dsep.entity.expert.EvalResult;
import com.dsep.entity.expert.Expert;
import com.dsep.service.expert.evaluation.EvalIndicWtService;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalIndicWtVM;

public class EvalIndicWtServiceImpl implements EvalIndicWtService {
	private EvalPaperDao evalPaperDao;
	private EvalQuestionDao evalQuestionDao;
	private EvalIndicWtDao evalIndicWtDao;
	private EvalResultDao evalResultDao;
	private ExpertDao expertDao;
	private DiscCategoryDao discCategoryDao;

	@Override
	public PageVM<EvalIndicWtVM> showIndicWtQAndResultsTable(
			CurrentBatchExpertInfo info) {
		Expert expert = expertDao.get(info.getExpertId());

		String discId = (expert.getUseDiscId()) ? expert.getDiscId()
				: expert.getDiscId2();

		String discCatId = discCategoryDao.getCatByDiscId(discId);

		String expertType = expert.getExpertType();

		// 通过专家分类和学科门类拿到卷子信息
		EvalPaper paper = evalPaperDao.getEvalPaper(expertType, discCatId,
				expert.getEvalBatch().getId());

		// 通过试卷id获取所有打分权重表中对应数据
		List<EvalIndicWt> list = this.evalIndicWtDao
				.getByPaperId(paper.getId());
		// 通过试卷id获取题目，注：打分权重表是一张独立配置的表，它和题目表是通过外键管理的
		List<EvalQuestion> questions = evalQuestionDao.getIndicWtQs(
				paper.getId(), discId, false);
		// 下面的for循环要把权重ele和题目ele一一对应，关联是weight.id = question.subQuestionId
		// 并封装到IndicatorWeightAndScore这个domain中
		List<EvalIndicWtAndScore> indicatorWeightAndScoreList = new ArrayList<EvalIndicWtAndScore>();
		for (EvalQuestion question : questions) {
			EvalIndicWtAndScore weightAndScore = null;
			for (EvalIndicWt ele : list) {
				if (ele.getId().equals(question.getSubQuestionId())) {
					weightAndScore = new EvalIndicWtAndScore(ele);
					weightAndScore.setQuestionId(question.getId());
					break;
				}
			}
			indicatorWeightAndScoreList.add(weightAndScore);
		}
		// 获得一个专家对所有权重题目打的分数
		List<EvalResult> scores = evalResultDao.getResultsByQType(
				expert.getId(), QType.INDIC_WT.toInt());

		List<EvalIndicWtVM> vmList = new ArrayList<EvalIndicWtVM>();
		// 下面的for循环把IndicatorWeightAndScore的ele和分数ele一一对应，关联是score.questionId =
		// domain.questoinId
		// 并封装到VM中展示
		for (EvalIndicWtAndScore indicatorWeightAndScore : indicatorWeightAndScoreList) {
			if(indicatorWeightAndScore==null)
				continue;
			if(indicatorWeightAndScore.getId().equals("ZSALL0101"))
				System.out.println("pasue!");
			Boolean findAllScoreAndNotMatch = true;
			for (EvalResult score : scores) {
				if (score.getEvalQuestion().getId()
						.equals(indicatorWeightAndScore.getQuestionId())) {
					// 构造方法会set分数
					EvalIndicWtVM vm = new EvalIndicWtVM(
							indicatorWeightAndScore, score);

					vmList.add(vm);
					findAllScoreAndNotMatch = false;
					break;
				}
			}
			if (findAllScoreAndNotMatch) {
				EvalIndicWtVM vm = new EvalIndicWtVM(indicatorWeightAndScore,
						null);
				vmList.add(vm);
			}
		}

		PageVM<EvalIndicWtVM> pageVM = new PageVM<EvalIndicWtVM>(1,
				vmList.size(), vmList.size(), vmList);
		return pageVM;
	}

	@Override
	public String getIndicWtProg(String batchId, String expertId) {

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
				expert.getId(), QType.INDIC_WT.toInt());
		List<EvalQuestion> questions = evalQuestionDao.getIndicWtQs(
				paper.getId(), discId, false);

		if (0 == questions.size()) {
			return "无任务";
		}

		Integer actualResultsNumber = 0;

		if (0 == actualResults.size()) {
			return "未开始";
		} else {
			for (EvalResult result : actualResults) {
				String[] valArr = result.getEvalValue().split(",", -1);
				for (int i = 0; i < valArr.length; i++) {
					if (!"".equals(valArr[i]))
						actualResultsNumber++;
				}
			}
		}

		List<EvalIndicWt> standardResults = evalIndicWtDao
				.getIndicatorWeightItemByDiscCatId(discCatId);
		standardResults.addAll(evalIndicWtDao
				.getIndicatorWeightItemByDiscCatId("ALL"));

		Boolean allSubmitted = true;
		if (0 == actualResults.size())
			allSubmitted = false;
		for (EvalResult r : actualResults) {
			if (!"2".equals(r.getEvalValueState())) {
				allSubmitted = false;
				break;
			}
		}
		if (allSubmitted) {
			return "已提交";
		}

		Integer standardResultsNumber = 0;
		for (EvalIndicWt wt : standardResults) {
			standardResultsNumber += Integer.valueOf(wt.getEffectItemNum());
		}
		if (0 == actualResults.size()) {
			return "未开始";
		}
		if (actualResultsNumber < standardResultsNumber) {
			return "未完成";
		}
		return "完成，未提交";
	}

	/*
	 * @Override public Integer getTotalNumbers() { // 通过试卷id获取所有打分权重表中对应数据
	 * List<EvalIndicatorWeight> list = this.evalIndicatorWeightDao
	 * .getByPaperId("");
	 * 
	 * Integer totalNumber = 0; for (EvalIndicatorWeight ele : list) {
	 * totalNumber += Integer.valueOf(ele.getEffectItemNum()); } return
	 * totalNumber; }
	 */

	// ----------------与业务逻辑无关的getter和setter-----------------------
	public EvalIndicWtDao getEvalIndicWtDao() {
		return evalIndicWtDao;
	}

	public void setEvalIndicWtDao(EvalIndicWtDao evalIndicWtDao) {
		this.evalIndicWtDao = evalIndicWtDao;
	}

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
