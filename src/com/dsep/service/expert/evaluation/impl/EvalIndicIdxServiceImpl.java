package com.dsep.service.expert.evaluation.impl;

import java.util.ArrayList;
import java.util.List;

import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalPaperDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalQuestionDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalResultDao;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.domain.dsepmeta.expert.EvalIndicIdxAndScore;
import com.dsep.entity.dsepmeta.IndexMap;
import com.dsep.entity.expert.EvalPaper;
import com.dsep.entity.expert.EvalQuestion;
import com.dsep.entity.expert.EvalResult;
import com.dsep.entity.expert.Expert;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.service.expert.evaluation.EvalIndicIdxService;
import com.dsep.service.flow.EvalFlowService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.expert.eval.ConvertIdxMap;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.expert.EvalIndicIdxRowVM;
import com.dsep.vm.expert.EvalIndicIdxVM;
import com.dsep.vm.expert.L3rdIdxMapVM;

public class EvalIndicIdxServiceImpl implements EvalIndicIdxService {
	private DMDiscIndexService discIndexService;
	private EvalFlowService evalFlowService;
	private ExpertDao expertDao;
	private DiscCategoryDao discCategoryDao;
	private EvalQuestionDao evalQuestionDao;
	private EvalPaperDao evalPaperDao;
	private EvalResultDao evalResultDao;

	/**
	 * 该方法做n件事情 1、获取所有的指标体系，并构建一棵树，返回末级指标 2、获取某专家的所有指标体系题目
	 * 3、遍历指标体系树和指标体系题目(id一致)，把所有匹配的item放入indiIdxAndScores中 4、获取某专家对所有指标体系题目打的分数
	 * 5、遍历分数和indiIdxAndScores，把分数set到indiIdxAndScores中 6、调用构造前端显示的json的方法
	 */
	@Override
	public String showIndicIdxQAndResultsTable(CurrentBatchExpertInfo info) {
		Expert expert = expertDao.get(info.getExpertId());
		String discId = (expert.getUseDiscId()) ? expert.getDiscId()
				: expert.getDiscId2();

		// 获取所有的指标体系，并构建一棵树(有一级、二级和末级指标)
		List<L3rdIdxMapVM> idxMapTree = ConvertIdxMap
				.getL3rdIdxMap(discIndexService.getIndexMapsByDiscId(discId));

		List<String> idxIds = new ArrayList<String>();
		for (L3rdIdxMapVM idx : idxMapTree) {
			String str = idx.getWeight();
			if (str.startsWith(".")) {
				idx.setWeight("0" + str);
			}
			idxIds.add(idx.getId());
		}

		List<EvalIndicIdxAndScore> indiIdxAndScores = new ArrayList<EvalIndicIdxAndScore>();

		String discCatId = discCategoryDao.getCatByDiscId(discId);

		String expertType = expert.getExpertType();

		// 通过专家分类和学科门类拿到卷子信息
		EvalPaper paper = evalPaperDao.getEvalPaper(expertType, discCatId,
				expert.getEvalBatch().getId());

		// 这里可以优化，取出所有该专家所有的非meta的Question即可，idxIds没什么用！
		/*
		 * List<EvalQuestion> questions = evalQuestionDao.getIndexQuestions(
		 * expert.getId(), idxIds);
		 */
		List<EvalQuestion> questions = evalQuestionDao.getIndicIdxQs(
				paper.getId(), discId, false);
		// 专家无任务
		if (questions.size() == 0) {return null;}
		System.out.println("In EvalIndixIdxService");
		for (EvalQuestion q : questions) {
			System.out.println(q.getId());
		}

		// 比较questions和indexes
		for (L3rdIdxMapVM idx : idxMapTree) {
			EvalIndicIdxAndScore indiIdxAndScore = new EvalIndicIdxAndScore(idx);
			for (EvalQuestion q : questions) {
				if (idx.getId().equals(q.getSubQuestionId())) {
					indiIdxAndScore.setQuestionId(q.getId());
					break;
				}
			}
			indiIdxAndScores.add(indiIdxAndScore);
		}

		List<EvalResult> scores = evalResultDao.getResultsByQType(
				expert.getId(), QType.INDIC_IDX.toInt());

		List<EvalIndicIdxVM> vmList = new ArrayList<EvalIndicIdxVM>();
		// 下面的for循环把IndicatorWeightAndScore的ele和分数ele一一对应，关联是score.questionId =
		// domain.questoinId
		// 并封装到VM中展示
		for (EvalIndicIdxAndScore indiIdxAndScore : indiIdxAndScores) {
			Boolean findAllScoreAndNotMatch = true;
			for (EvalResult score : scores) {
				if (score.getEvalQuestion().getId()
						.equals(indiIdxAndScore.getQuestionId())) {
					// 构造方法会set分数
					EvalIndicIdxVM vm = new EvalIndicIdxVM(indiIdxAndScore,
							score);

					vmList.add(vm);
					findAllScoreAndNotMatch = false;
					break;
				}
			}
			if (findAllScoreAndNotMatch) {
				EvalIndicIdxVM vm = new EvalIndicIdxVM(indiIdxAndScore, null);
				vmList.add(vm);
			}
		}

		// 调用用于前端显示的代码
		return constructIndicIdxJson(vmList);
	}

	/**
	 * 
	 * 这是一段复杂的代码，主要使用了树的知识，开辟了两个栈aStack和bStak 实际 => 显示 =>
	 * json表示(n指明这行不显示，数字表示跨行数) 
	 * 1|1|1| => 1|1|1| => 5|2|1| 
	 * 1|1|2|     | |2|    n|n|1|
	 * 1|2|1|     |2|1|    n|3|1| 
	 * 1|2|2|     | |2|    n|n|1| 
	 * 1|2|3|     | |3|    n|n|1| 
	 * 2|1|1|    2|1|1|    1|1|1| 
	 * 
	 * 基本思路步骤 
	 * 1、扫第一行，分别如a栈、b栈，作为初始基准
	 * 
	 * 2、从第二行开始循环，如果第一列和第二列都和上一次相同（第三列一定不相同），
	 *    那么直接加入到list中，并指明第一列和第二列都不显示(设为"none")，
	 *    同时 记录第一行和第二行跨的行数要加一 
	 * 
	 * 3、如果第一列相同而第二列不相同，那么要把b栈的顶元素出栈，并设置第二行的跨行数， 
	 *    再把第二行跨行数重置，最后把当前循环的这一行加入到b栈中
	 * 
	 * 4、如果第一列不相同，那么要把a栈和b栈的顶元素都出栈，并分别设置其第一行和第二行
	 *    的跨行数，再把两个跨行数重置，最后把当前循环的这一行分别加入a栈中和b栈中
	 * 
	 * 5、处理最后行，把a栈和b栈的顶元素都出栈（里面一定都有最后一个元素未出栈）， 
	 *    并分别设置其第一行和第二行的跨行数
	 */
	private String constructIndicIdxJson(List<EvalIndicIdxVM> vmList) {
		EvalIndicIdxVM[] items = vmList.toArray(new EvalIndicIdxVM[vmList
				.size()]);

		List<EvalIndicIdxVM> showingList = new ArrayList<EvalIndicIdxVM>();

		EvalIndicIdxVM item = items[0];

		MyStack<EvalIndicIdxVM> aStack = new MyStack<EvalIndicIdxVM>();
		MyStack<EvalIndicIdxVM> bStack = new MyStack<EvalIndicIdxVM>();

		String prevAId = item.getaId(), prevBId = item.getbId();
		String bToAAnchor = item.getaId(), cToBAnchor = item.getbId();
		// A跨行数和B跨行数未知，先一律设为none，到时再改
		EvalIndicIdxVM vm = new EvalIndicIdxVM(item, bToAAnchor, cToBAnchor,
				"none", "none", "1");
		vm.setcActualVal(item.getScore());
		showingList.add(vm);
		aStack.push(vm);
		bStack.push(vm);

		int aCols = 1, bCols = 1;
		Float aScore = 0.00f;
		if (!"".equals(vm.getScore())) {
			aScore = Float.valueOf(vm.getScore());
		}

		Float bScore = 0.00f;
		if (!"".equals(vm.getScore())) {
			bScore = Float.valueOf(vm.getScore());
		}

		EvalIndicIdxVM tmp = null;
		for (int i = 1; i < items.length; i++) {
			EvalIndicIdxVM item2 = items[i];
			// 第一列相等
			if (item2.getaId().equals(prevAId)) {
				// 第二列相等
				if (item2.getbId().equals(prevBId)) {
					// bToAAnchor不用设置，A行和B行都是合并
					vm = new EvalIndicIdxVM(item2, bToAAnchor, cToBAnchor,
							"none", "none", "1");
					aCols++;
					bCols++;
					if (!"".equals(vm.getScore())) {
						aScore += Float.valueOf(vm.getScore());
					} else {
						aScore += 0.00f;
					}
					if (!"".equals(vm.getScore())) {
						bScore += Float.valueOf(vm.getScore());
					} else {
						bScore += 0.00f;
					}

					vm.setcActualVal(vm.getScore());
					showingList.add(vm);
					prevAId = item2.getaId();
					prevBId = item2.getbId();
				} 
				// 第二列不等
				else {
					// B跨行数
					tmp = bStack.pop();

					tmp.getAttr().setbNameIndex(
							new EvalIndicIdxRowVM(String.valueOf(bCols)));

					tmp.getAttr().setbValIndex(
							new EvalIndicIdxRowVM(String.valueOf(bCols)));

					tmp.setbActualVal(String.valueOf(bScore));

					aCols++;
					// reset B跨行数
					bCols = 1;
					if (!"".equals(vm.getScore())) {
						bScore += Float.valueOf(vm.getScore());
					} else {
						bScore += 0.00f;
					}

					cToBAnchor = item2.getbId();

					vm = new EvalIndicIdxVM(item2, bToAAnchor, cToBAnchor,
							"none", "none", "1");
					vm.setcActualVal(item2.getScore());
					showingList.add(vm);
					bStack.push(vm);
					prevAId = item2.getaId();
					prevBId = item2.getbId();
				}
			} 
			// 第一列不等
			else {
				// A跨行数
				tmp = aStack.pop();
				tmp.getAttr().setaNameIndex(
						new EvalIndicIdxRowVM(String.valueOf(aCols)));
				tmp.getAttr().setaValIndex(
						new EvalIndicIdxRowVM(String.valueOf(aCols)));
				tmp.setaActualVal(String.valueOf(aScore));

				// B跨行数
				tmp = bStack.pop();
				tmp.getAttr().setbNameIndex(
						new EvalIndicIdxRowVM(String.valueOf(bCols)));
				tmp.getAttr().setbValIndex(
						new EvalIndicIdxRowVM(String.valueOf(bCols)));

				tmp.setbActualVal(String.valueOf(bScore));
				// reset A跨行数
				aCols = 1;
				if (!"".equals(item2.getScore())) {
					aScore = Float.valueOf(item2.getScore());
				} else {
					aScore = 0.00f;
				}

				// reset B跨行数
				bCols = 1;
				if (!"".equals(item2.getScore())) {
					bScore = Float.valueOf(item2.getScore());
				} else {
					bScore = 0.00f;
				}

				bToAAnchor = item2.getaId();
				cToBAnchor = item2.getbId();
				vm = new EvalIndicIdxVM(item2, bToAAnchor, cToBAnchor, "none",
						"none", "1");

				vm.setcActualVal(item2.getScore());
				showingList.add(vm);
				aStack.push(vm);
				bStack.push(vm);
				prevAId = item2.getaId();
				prevBId = item2.getbId();
			}
		}

		// 最后处于aStack和bStack中的元素
		// A跨行数
		tmp = aStack.pop();
		tmp.getAttr().setaNameIndex(
				new EvalIndicIdxRowVM(String.valueOf(aCols)));
		tmp.getAttr()
				.setaValIndex(new EvalIndicIdxRowVM(String.valueOf(aCols)));
		tmp.setaActualVal(String.valueOf(aScore));
		// B跨行数
		tmp = bStack.pop();
		tmp.getAttr().setbNameIndex(
				new EvalIndicIdxRowVM(String.valueOf(bCols)));
		tmp.getAttr()
				.setbValIndex(new EvalIndicIdxRowVM(String.valueOf(bCols)));
		tmp.setbActualVal(String.valueOf(bScore));

		// return showlingList;
		/*for (EvalIndicIdxVM test : showingList) {
			System.out.println(test.getaName() + "$"
					+ test.getAttr().getaNameIndex().getDisplay() + "$"
					+ test.getbName() + "$"
					+ test.getAttr().getbNameIndex().getDisplay() + "$"
					+ test.getcName() + "$"
					+ test.getAttr().getcNameIndex().getDisplay());
		}*/
		return JsonConvertor.obj2JSON(showingList);
	}
	

	@Override
	public String getIndicIdxProg(String batchId, String expertId) {
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
				expert.getId(), QType.INDIC_IDX.toInt());

		List<EvalQuestion> questions = evalQuestionDao.getIndicIdxQs(
				paper.getId(), discId, false);
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
		Float indicIdxSum = 0.00f;
		for (EvalResult result : actualResults) {
			indicIdxSum += Float.valueOf(result.getEvalValue());
		}
		if (100.00f != indicIdxSum) {
			return "总分不是100.00";
		}
		return "完成，未提交";
	}

	@Override
	public List<EvalQuestion> getIndicatorIndexQuestions(String discId) {
		List<IndexMap> indexes = discIndexService.getIndexMapsByDiscId(discId);
		List<EvalQuestion> questions = new ArrayList<EvalQuestion>();
		for (IndexMap idx : indexes) {
			// 3表示末级指标
			if (idx.getIndexLevel() == 3) {
				EvalQuestion question = new EvalQuestion();
				question.setSubQuestionId(idx.getId());
				question.setQType(QType.INDIC_IDX.toInt());
				question.setStorageMode(1);
				question.setIsMeta(false);
				questions.add(question);
			}
		}
		return questions;
	}

	/*
	 * @Override public double getAchievementProcess(String expertId, String
	 * discId) { ExpertSelected expert = expertSelectedDao.get(expertId); String
	 * expertType = expert.getExpertType(); String discCategory =
	 * discCategoryDao.getCatByDiscId(discId); EvalPaper paper =
	 * evalPaperDao.getEvalPaper(expertType, discCategory); Set<EvalQuestion>
	 * questions = paper.getEvalQuestions(); int unitQuestionCount = 0;
	 * List<EvalResult> results = new ArrayList<EvalResult>(); for (EvalQuestion
	 * e : questions) { if (e.getQType() != 3) { List<EvalResult> m =
	 * evalResultDao.getEvalResultsByQuestionId( expertId, e.getId());
	 * results.addAll(m); } if (e != null) {
	 * //if(e.getCollectId1()!=null||e.getCollectId2
	 * ()!=null||e.getCollectId3()!=null) // unitQuestionCount++; } }
	 * System.out.println(unitQuestionCount); int unitEvalCount = 0; for
	 * (EvalResult o : results) { //if(o.getUnitId()!=null) unitEvalCount++; }
	 * System.out.println(unitEvalCount);
	 * 
	 * PageVM<EvalVM> evalVMPage = evalFlowService.getCollectEvalByPage(null,
	 * discId, null, true, null, 1, 1000, true, null);
	 * 
	 * @SuppressWarnings("unchecked") List<EvalVM> evalVMList = (List<EvalVM>)
	 * evalVMPage.getGridData().get( "rows"); int unitCount = evalVMList.size();
	 * int sum = unitQuestionCount * unitCount; double processData = (double)
	 * unitEvalCount / sum;
	 * 
	 * return processData; }
	 * 
	 * @Override public double getIndexProcess(String expertId, String discId) {
	 * ExpertSelected expert = expertSelectedDao.get(expertId); String
	 * expertType = expert.getExpertType(); String discCategory =
	 * discCategoryDao.getCatByDiscId(discId); EvalPaper paper =
	 * evalPaperDao.getEvalPaper(expertType, discCategory); Set<EvalQuestion>
	 * questions = paper.getEvalQuestions(); List<EvalResult> results = new
	 * ArrayList<EvalResult>(); int indexQuestionCount = 0; for (EvalQuestion e
	 * : questions) { //if(e.getIndexItemId()!=null){ if (true) {
	 * List<EvalResult> m = evalResultDao.getEvalResultsByQuestionId( expertId,
	 * e.getId(), discId); results.addAll(m); indexQuestionCount++; } } int
	 * indexResultCount = results.size(); double process = (double)
	 * indexResultCount / indexQuestionCount; return process; }
	 */

	// 与业务逻辑无关的getter和setter========================================
	public DMDiscIndexService getDiscIndexService() {
		return discIndexService;
	}

	public void setDiscIndexService(DMDiscIndexService discIndexService) {
		this.discIndexService = discIndexService;
	}

	public EvalFlowService getEvalFlowService() {
		return evalFlowService;
	}

	public void setEvalFlowService(EvalFlowService evalFlowService) {
		this.evalFlowService = evalFlowService;
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

	public EvalQuestionDao getEvalQuestionDao() {
		return evalQuestionDao;
	}

	public void setEvalQuestionDao(EvalQuestionDao evalQuestionDao) {
		this.evalQuestionDao = evalQuestionDao;
	}

	public EvalPaperDao getEvalPaperDao() {
		return evalPaperDao;
	}

	public void setEvalPaperDao(EvalPaperDao evalPaperDao) {
		this.evalPaperDao = evalPaperDao;
	}

	public EvalResultDao getEvalResultDao() {
		return evalResultDao;
	}

	public void setEvalResultDao(EvalResultDao evalResultDao) {
		this.evalResultDao = evalResultDao;
	}

}

class MyStack<T> {
	private int point;
	private List<T> list;

	public MyStack() {
		point = -1;
		list = new ArrayList<T>();
	}

	public void push(T ele) {
		list.add(ele);
		point++;
	}

	public T pop() {
		if (point < 0) {
			try {
				throw new Exception("栈为空，不能进行pop操作");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		T ele = list.get(point);
		list.remove(point--);
		return ele;
	}
}
