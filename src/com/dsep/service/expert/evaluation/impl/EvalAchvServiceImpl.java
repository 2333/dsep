package com.dsep.service.expert.evaluation.impl;

import java.util.ArrayList;
import java.util.List;

import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalAchvDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalPaperDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalQuestionDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalResultDao;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.domain.dsepmeta.expert.EvalAchvAndScore;
import com.dsep.entity.expert.EvalAchv;
import com.dsep.entity.expert.EvalPaper;
import com.dsep.entity.expert.EvalQuestion;
import com.dsep.entity.expert.EvalResult;
import com.dsep.entity.expert.Expert;
import com.dsep.service.expert.evaluation.EvalAchvService;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalAchvVM;

public class EvalAchvServiceImpl implements EvalAchvService {
	private EvalQuestionDao evalQuestionDao;
	private EvalAchvDao evalAchvDao;
	private EvalResultDao evalResultDao;
	private ExpertDao expertDao;
	private DiscCategoryDao discCategoryDao;
	private EvalPaperDao evalPaperDao;

	/**
	 * 通过paper.id和question.subQuestionId获取questions
	 *  abc.0835.10006.R
	 *  abc.0835.10007.R
	 *  abe.0835.10008.R
	 */
	@Override
	public PageVM<EvalAchvVM> getAchvResults(CurrentBatchExpertInfo info,
			String subQuestionId, int pageIndex, int pageSize) {

		Expert expert = expertDao.get(info.getExpertId());

		String discId = (expert.getUseDiscId()) ? expert.getDiscId()
				: expert.getDiscId2();

		String discCatId = discCategoryDao.getCatByDiscId(discId);

		String expertType = expert.getExpertType();

		// 通过专家分类和学科门类拿到卷子信息
		EvalPaper paper = evalPaperDao.getEvalPaper(expertType, discCatId,
				expert.getEvalBatch().getId());

		List<EvalQuestion> questions = evalQuestionDao.getAchvQsByPage(
				paper.getId(), discId, subQuestionId, pageIndex, pageSize);
		
		/*for(int i=0;i<questions.size();i++){
			if(questions.get(i).getUnitId().equals(expert.getUnitId())){
				questions.remove(i);
				break;
			}	
		}*/

		List<EvalAchvAndScore> achievementAndScoreList = new ArrayList<EvalAchvAndScore>();

		for (EvalQuestion q : questions) {
			EvalAchv a = evalAchvDao.get(q.getSubQuestionId());
			achievementAndScoreList.add(new EvalAchvAndScore(q, a));
		}

		// 获得一个专家对所有权重题目打的分数
		List<EvalResult> scores = evalResultDao.getResultsByQType(
				expert.getId(), QType.ACHV.toInt());

		List<EvalAchvVM> vmList = new ArrayList<EvalAchvVM>();
		// 下面的for循环把IndicatorWeightAndScore的ele和分数ele一一对应
		// 关联是score.questionId = domain.questoinId
		// 并封装到VM中展示
		int i = 0;
		for (EvalAchvAndScore achievementAndScore : achievementAndScoreList) {
			Boolean findAllScoreAndNotMatch = true;
			for (EvalResult score : scores) {
				if (score.getEvalQuestion().getId()
						.equals(achievementAndScore.getQuestionId())) {
					// 构造方法会set分数
					EvalAchvVM vm = new EvalAchvVM(achievementAndScore, score,
							discId, i++);

					vmList.add(vm);
					findAllScoreAndNotMatch = false;
					break;
				}
			}
			if (findAllScoreAndNotMatch) {
				EvalAchvVM vm = new EvalAchvVM(achievementAndScore, null,
						discId, i++);
				vmList.add(vm);
			}
		}

		PageVM<EvalAchvVM> pageVM = new PageVM<EvalAchvVM>(1, vmList.size(),
				vmList.size(), vmList);
		return pageVM;
	}

	/*@Override
	public List<EvalQuestionVM> getAchvQs(CurrentBatchExpertInfo info,
			int scoreType, String subQuestionId) {
		return null;
	}*/

	@Override
	public String getAchvProg(String batchId, String expertId,
			String subQuestionId) {
		Expert expert = expertDao.get(expertId);
		if(expert.getUseDiscId()==null)
			expert.setUseDiscId(true);
		String discId = (expert.getUseDiscId()) ? expert.getDiscId()
				: expert.getDiscId2();
		String discCatId = discCategoryDao.getCatByDiscId(discId);
		String expertType = expert.getExpertType();

		EvalPaper paper = evalPaperDao.getEvalPaper(expertType, discCatId, batchId);

		List<EvalResult> actualResults = evalResultDao.getAchvResultsBySubQuestionId(
				expert.getId(), subQuestionId);

		List<EvalQuestion> questions = evalQuestionDao.getAllAchvQs(
				paper.getId(), discId, subQuestionId);
		if (0 == questions.size()) {
			return "无任务";
		}
		
		Boolean allSubmitted = true;
		if (0 == actualResults.size()) allSubmitted = false;
		for (EvalResult r : actualResults) {
			if (!"2".equals(r.getEvalValueState())) {
				allSubmitted = false;
				break;
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

	//----------------与业务逻辑无关的getter和setter-----------------------
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

	public EvalAchvDao getEvalAchvDao() {
		return evalAchvDao;
	}

	public void setEvalAchvDao(EvalAchvDao evalAchvDao) {
		this.evalAchvDao = evalAchvDao;
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

	public EvalPaperDao getEvalPaperDao() {
		return evalPaperDao;
	}

	public void setEvalPaperDao(EvalPaperDao evalPaperDao) {
		this.evalPaperDao = evalPaperDao;
	}

}
