package com.dsep.service.expert.evaluation.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.expert.batch.EvalBatchDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalAchvDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalIndicWtDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalPaperDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalQuestionDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalResultDao;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.entity.dsepmeta.IndexMap;
import com.dsep.entity.expert.EvalAchv;
import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.EvalIndicWt;
import com.dsep.entity.expert.EvalPaper;
import com.dsep.entity.expert.EvalQuestion;
import com.dsep.entity.expert.EvalResult;
import com.dsep.entity.expert.Expert;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.service.expert.evaluation.EvalProgService;
import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.service.flow.EvalFlowService;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.expert.EvalQuestionVM;

public class EvalServiceImpl implements EvalService {
	private DMDiscIndexService discIndexService;
	private EvalFlowService evalFlowService;
	private EvalPaperDao evalPaperDao;
	private EvalQuestionDao evalQuestionDao;
	private EvalResultDao evalResultDao;
	private ExpertDao expertDao;
	private UnitService unitService;
	private DiscCategoryDao discCategoryDao;
	private EvalProgService evalProgService;
	private DisciplineService disciplineService;
	private EvalAchvDao evalAchvDao;
	private EvalIndicWtDao evalIndicWtDao;
	private EvalBatchDao evalBatchDao;

	@Override
	public void solidifyPapers(EvalBatch evalBatch) {
		// 获得本批次所有的paper，这里获取的paper都是meta的
		Set<EvalPaper> allPapers = evalBatch.getPapers();
		// 转化为数组进行遍历，而不是用集合进行遍历
		EvalPaper[] allPapersArr = allPapers.toArray(new EvalPaper[allPapers
				.size()]);

		List<String> discIds = getAllEvalDiscId();
		Set<String> discCatIds = new HashSet<String>();
		for (String discId : discIds) {
			// ！！下面一行需要删除，目前还没有配置，只有JSJ类的！！
			if (null == discCategoryDao.getCatByDiscId(discId))
				continue;

			discCatIds.add(discCategoryDao.getCatByDiscId(discId));
		}

		List<EvalPaper> papers = new ArrayList<EvalPaper>();
		for (String discCatId : discCatIds) {
			for (int i = 0; i < allPapersArr.length; i++) {
				EvalPaper p = allPapersArr[i];

				EvalPaper paper = new EvalPaper();
				paper.setDiscCatId(discCatId);
				// 一旦加入了discCatId之后，paper就不是meta的了
				paper.setIsMeta(false);
				paper.setExpertTypeId(p.getExpertTypeId());
				paper.setEvalBatch(evalBatch);
				evalBatch.getPapers().add(paper);
				paper.getEvalQuestions().addAll(p.getEvalQuestions());
				papers.add(paper);
			}
		}
		for (EvalPaper paper : papers) {
			evalPaperDao.save(paper);
		}
	}

	@Override
	public void solidifyQuestions(EvalBatch evalBatch) {
		// 获得本批次所有的paper
		Set<EvalPaper> allPapers = evalBatch.getPapers();
		// 转化为数组进行遍历，而不是用集合进行遍历
		EvalPaper[] papers = allPapers.toArray(new EvalPaper[allPapers.size()]);

		List<EvalPaper> newPapers = new ArrayList<EvalPaper>();
		/**
		 * 配置的一组Paper-Question是这样的： 
		 * {paper-id, JSJ, 同行, 第一批} 
		 * {元Q1, 成果打分, } // 成果打分对应外表多个具体成果表id(如：专家团队，优秀学生)
		 * {元Q2, 指标权重, } // 指标权重对应外表多个item,不设置外键id
		 * 
		 * 生成后变成 
		 * {paper-id, JSJ, 同行, 第一批} 
		 * {非元Q1, 成果打分, 具体成果表id(专家团队), 0812, 10001} 
		 * {非元Q2, 成果打分, 具体成果表id(专家团队), 0812, 10002} 
		 * {非元Q3, 成果打分, 具体成果表id(专家团队), 0812, 10006} 
		 * {非元Q4, 成果打分, 具体成果表id(专家团队), 0835, 10003}
		 * {非元Q5, 成果打分, 具体成果表id(专家团队), 0835, 10007} 
		 * 
		 * {非元Q6, 成果打分, 具体成果表id(优秀学生), 0812, 10001} 
		 * {非元Q7, 成果打分, 具体成果表id(优秀学生), 0812, 10002} 
		 * {非元Q8, 成果打分, 具体成果表id(优秀学生), 0812, 10006} 
		 * {非元Q9, 成果打分, 具体成果表id(优秀学生), 0835, 10003} 
		 * {非元QX, 成果打分, 具体成果表id(优秀学生), 0835, 10007}
		 * 非元Q1~Q5和Q6~QX由元Q1生成
		 * 
		 * {非元QA, 指标权重, 重点实验室, 0812} 
		 * {非元QB, 指标权重, 专家头衔, 0812} 
		 * {非元QC, 指标权重, 学科论文, 0812} 
		 * {非元QD, 指标权重, 重点实验室, 0835} 
		 * {非元QE, 指标权重, 专家头衔, 0835} 
		 * {非元QF, 指标权重, 学科论文, 0835} 
		 * 非元QA~QC由元Q3生成
		 */

		for (int i = 0; i < papers.length; i++) {
			EvalPaper p = papers[i];
			// 直接update paper对象
			// paper.isMeta=true仅仅在没有在paper中加入JSJ等dics_cat_id时成立！
			// 即在执行solidifyPapers之前
			if (!p.getIsMeta()) {
				// 一个Paper代表一个学科大类，如JSJ，其中的指标体系和指标权重只需要被固化一次
				// boolean indicWtSolidified = false;
				// boolean indicIdxSolidified = false;

				// 获得Paper的所有题目
				Set<EvalQuestion> allQuestions = papers[i].getEvalQuestions();

				// paper规定的学科门类，如JSJ
				String paperDiscCatId = papers[i].getDiscCatId();

				// 获取某个学科门类下的很多学科(如JSJ下的0811、0829等)
				List<String> discIds = getAllEvalDiscId();

				// 以下到for循环结束把一个元Paper转化为关联n个含参评学科的Questions的实际Paper(以后专家任务的入口)
				for (String discId : discIds) {
					String discCatId = discCategoryDao.getCatByDiscId(discId);
					// 如果一个学科不是JSJ类，跳出(如0101是医学类的，不是JSJ类)
					// 这样也保证了有效的discId对应的discCatId一定与paper.discCatId一致
					if (!paperDiscCatId.equals(discCatId))
						continue;

					// 转化为数组进行遍历，而不是用集合进行遍历
					EvalQuestion[] questions = allQuestions
							.toArray(new EvalQuestion[allQuestions.size()]);
					for (int j = 0; j < questions.length; j++) {
						EvalQuestion q = questions[j];
						if (q.getIsMeta()) {
							// 如果该元question是有关于成果打分的(即和学校相关的)
							// 要给该元question替换成含实际学科、实际学校的question
							if (QType.ACHV.toInt() == q.getQType()) {
								p = geConcreteAchvQs(discId, p, q, discCatId);
							} else if (QType.INDIC_IDX.toInt() == q.getQType()) {
								// if (!indicIdxSolidified) {
								p = getConcreteIndiIdxQs(discId, p, q);
								// indicIdxSolidified = true;
								// }
							}
							// INDICATOR_WEIGHT的特点是一道元题目会生成n道实际题目
							else if (QType.INDIC_WT.toInt() == q.getQType()) {
								// if (!indicWtSolidified) {
								p = geConcreteIndiWeightQs(discId, p, q);
								// indicWtSolidified = true;
								// }
							} else if (QType.REPU.toInt() == q.getQType()
									|| QType.RANK.toInt() == q.getQType()) {
								p = geConcreteRepuOrRankQs(discId, p, q);
							}
						}
					}
				}
				newPapers.add(p);
			}
		}
		// 注：不删除元Paper和元Question，仅增加非元Paper和非元Question
		for (int i = 0; i < newPapers.size(); i++) {
			evalPaperDao.saveOrUpdate(newPapers.get(i));
		}
		// evalPaperDao.flush();
		// 不能删除本批次下的meta question

		// 不能删除本批次下的meta paper
	}

	@Override
	public CurrentBatchExpertInfo initCurrentBatchExpertInfo(
			String expertLoginId, String batchId) {
		CurrentBatchExpertInfo info = new CurrentBatchExpertInfo();
		Expert expert = null;
		// 只有一个batchId
		// means已选专家库中只有一个专家记录，而不是一个专家被多个批次选中
		// 所以getExpertsByLoginId只会选出一条

		// 如果前台batchId只有一个，就传null
		// 否则弹框让专家选择批次，传入选择的batchId
		if (batchId == null) {
			List<Expert> experts = expertDao
					.getComfirmedExpertsByLoginId(expertLoginId);
			if (experts.size() > 1) {
				new Exception("getExpertByLoginId逻辑有错误");
			}
			expert = experts.get(0);
		} else {
			List<Expert> experts = expertDao
					.getComfirmedExpertsByLoginId(expertLoginId);

			for (Expert e : experts) {
				if (e.getEvalBatch().getId().equals(batchId)) {
					expert = e;
					break;
				}
			}
		}
		// info.setExpert(expert);

		String discId = (expert.getDiscId() != null) ? expert.getDiscId()
				: expert.getDiscId2();
		// info.setCurrentBatchExpertDiscId(discId);

		String discCatId = discCategoryDao.getCatByDiscId(discId);
		// info.setCurrentBatchExpertDiscCatId(discCatId);

		// 通过专家分类（行业专家/学术专家等）和学科门类拿到卷子信息
		EvalPaper paper = evalPaperDao.getEvalPaper(expert.getExpertType(),
				discCatId, expert.getEvalBatch().getId());
		// info.setCurrentPaper(paper);

		info.setCurrentBatchId(expert.getEvalBatch().getId());

		info.setRoutes(evalProgService.getRoutes(info));

		return info;
	}

	private EvalPaper getConcreteIndiIdxQs(String discId, EvalPaper p,
			EvalQuestion q) {
		List<IndexMap> indexes = discIndexService.getIndexMapsByDiscId(discId);
		for (IndexMap idx : indexes) {
			// 3表示末级指标
			if (idx.getIndexLevel() == 3) {
				EvalQuestion newQ = new EvalQuestion();

				newQ.setQType(q.getQType());
				newQ.setStorageMode(q.getStorageMode());
				// 表明不是元Question
				newQ.setIsMeta(false);
				newQ.setSubQuestionId(idx.getId());
				p.getEvalQuestions().add(newQ);
				newQ.getEvalPapers().add(p);
				newQ.setDiscId(discId);
			}
		}
		return p;
	}

	private EvalPaper geConcreteIndiWeightQs(String discId, EvalPaper p,
			EvalQuestion q) {
		List<EvalIndicWt> allWeights = new ArrayList<EvalIndicWt>();
		// 首先获得所有学科适用的公共的权重
		allWeights.addAll(evalIndicWtDao
				.getIndicatorWeightItemByDiscCatId("ALL"));

		// 其次获得该学科门类特有的权重打分
		String discCategory = discCategoryDao.getCatByDiscId(discId);
		allWeights.addAll(evalIndicWtDao
				.getIndicatorWeightItemByDiscCatId(discCategory));

		for (EvalIndicWt weight : allWeights) {
			EvalQuestion newQ = new EvalQuestion();
			newQ.setQType(q.getQType());
			newQ.setStorageMode(q.getStorageMode());
			// 表明不是元Question
			newQ.setIsMeta(false);
			// 不用set实际Question的具体的学校Id
			// 设置对应的子QuestionId
			newQ.setSubQuestionId(weight.getId());
			p.getEvalQuestions().add(newQ);
			newQ.getEvalPapers().add(p);
			newQ.setDiscId(discId);
		}
		return p;
	}

	private EvalPaper geConcreteAchvQs(String discId, EvalPaper p,
			EvalQuestion q, String discCatId) {
		List<EvalAchv> list = evalAchvDao.getByDiscCatId(discCatId);
		List<String> unitIds = unitService.getEvalUnitByDiscId(discId);
		for (EvalAchv achv : list) {
			for (String unitId : unitIds) {
				EvalQuestion newQ = new EvalQuestion();

				newQ.setQType(q.getQType());
				newQ.setStorageMode(q.getStorageMode());
				// 表明不是元Question
				newQ.setIsMeta(false);
				// 实际Question的具体的学科Id
				newQ.setDiscId(discId);
				// 实际Question的具体的学校Id
				newQ.setUnitId(unitId);
				// 设置对应的子QuestionId,即achv表的id
				newQ.setSubQuestionId(achv.getId());

				newQ.setQuestionName(achv.getQuestionName());

				p.getEvalQuestions().add(newQ);
				newQ.getEvalPapers().add(p);
			}
		}
		return p;
	}

	// 不用设置对应的子QuestionId
	private EvalPaper geConcreteRepuOrRankQs(String discId, EvalPaper p,
			EvalQuestion q) {
		List<String> unitIds = unitService.getEvalUnitByDiscId(discId);
		for (String unitId : unitIds) {
			EvalQuestion newQ = new EvalQuestion();

			newQ.setQType(q.getQType());
			newQ.setStorageMode(q.getStorageMode());
			// 表明不是元Question
			newQ.setIsMeta(false);
			// 实际Question的具体的学科Id
			newQ.setDiscId(discId);
			// 实际Question的具体的学校Id
			newQ.setUnitId(unitId);

			newQ.setQuestionName(q.getQuestionName());

			p.getEvalQuestions().add(newQ);
			newQ.getEvalPapers().add(p);
		}
		return p;
	}

	private List<String> getAllEvalDiscId() {
		Map<String, String> discIdAndName = disciplineService
				.getAllEvalDiscMap();
		Set<String> ids = discIdAndName.keySet();
		List<String> discIds = new ArrayList<String>();
		for (String id : ids) {
			discIds.add(id);
		}
		return discIds;
	}

	/**
	 * 1.通过expertId获得ExpertSelected实体 2.通过ExpertSelected实体获得专家打分学科ID:discCode
	 * 3.通过ExpertSelected实体获得专家分类（行业专家/学术专家等） 4.通过discCode获得学科门类category
	 * 5.通过专家分类和学科门类拿到卷子信息 6.通过卷子信息拿到题干信息
	 */
	public List<EvalQuestionVM> getQs(CurrentBatchExpertInfo info,
			int scoreType, String subQuestionId) {
		Expert expert = expertDao.get(info.getExpertId());

		String discId = (expert.getUseDiscId()) ? expert.getDiscId()
				: expert.getDiscId2();

		String discCatId = discCategoryDao.getCatByDiscId(discId);

		String expertType = expert.getExpertType();

		// 通过专家分类和学科门类拿到卷子信息
		EvalPaper paper = evalPaperDao.getEvalPaper(expertType, discCatId,
				expert.getEvalBatch().getId());
		List<EvalQuestion> questions = null;
		if (scoreType == QType.ACHV.toInt()) {
			questions = evalQuestionDao.getAllAchvQs(paper.getId(), discId,
					subQuestionId);
		} else if (scoreType == QType.INDIC_IDX.toInt()) {
			questions = evalQuestionDao.getIndicIdxQs(paper.getId(), discId,
					false);
		} else if (scoreType == QType.INDIC_WT.toInt()) {
			questions = evalQuestionDao.getIndicWtQs(paper.getId(), discId,
					false);
		} else if (scoreType == QType.REPU.toInt()) {
			questions = evalQuestionDao.getRepuQs(paper.getId(), discId, false);
		}

		return constructQuestionInfos(questions, discId, scoreType);
	}

	// 封装VM为了disc等的中文显示
	private List<EvalQuestionVM> constructQuestionInfos(
			List<EvalQuestion> questions, String discId, Integer scoreType) {
		List<EvalQuestionVM> evalQuestionVMList = new ArrayList<EvalQuestionVM>();
		EvalAchv achievement = null;
		if (questions.size() > 0 && scoreType == QType.ACHV.toInt()) {
			achievement = evalAchvDao.get(questions.get(0).getSubQuestionId());
		}

		for (EvalQuestion question : questions) {
			EvalQuestionVM vm = new EvalQuestionVM(question, discId,
					achievement);
			evalQuestionVMList.add(vm);
		}
		return evalQuestionVMList;
	}

	@Override
	public void saveResults(List<EvalResult> results) {
		// 前端提交保证了results不为null
		for (EvalResult r : results) {
			r.setEvalValueState("1");
			if ("".equals(r.getId())) {
				// 最后一个参数，如果是update设为true，如果是save设为false
				evalResultDao.saveOrUpdate(r, r.getEvalQuestion(), false);
			} else {
				evalResultDao.saveOrUpdate(r, r.getEvalQuestion(), true);
			}
		}
	}

	@Override
	public void submitResults(CurrentBatchExpertInfo info) {
		Expert expert = expertDao.get(info.getExpertId());
		String discId = (expert.getUseDiscId()) ? expert.getDiscId()
				: expert.getDiscId2();
		String discCatId = discCategoryDao.getCatByDiscId(discId);
		String expertType = expert.getExpertType();

		EvalPaper paper = evalPaperDao.getEvalPaper(expertType, discCatId,
				info.getCurrentBatchId());

		evalResultDao.submit(paper.getId(), expert.getId());
	}

	/*
	 * @Override public Double getSubjectiveQuestionFinishedPCT(String expertId,
	 * String questionId, Integer questionType, String discId) { //
	 * 通过discCode获得该学科有多少个学校参评 // 这里我不想查分页信息，是不是应该让朱明远给我写非分页的接口！1000是模拟
	 * List<String> units = getAttendUnits(discId, 1, 1000); Integer
	 * finishedResultsNum = getSubjectiveQuestionFinishedNumber( expertId,
	 * questionId, questionType, discId);
	 * 
	 * // denominator是分母的意思 Double denominator = Double.valueOf(units.size());
	 * Double member = Double.valueOf(finishedResultsNum);
	 * 
	 * Double number = (member / denominator); return
	 * Double.parseDouble(String.format("%.2f", number)); }
	 */

	/*
	 * @Override public Integer getSubjectiveQuestionFinishedNumber(String
	 * expertId, String questionId, Integer questionType, String discId) {
	 * List<EvalResult> results = null; if (null != questionId) { results =
	 * evalResultDao.getEvalResultsByQuestionId(expertId, questionId, discId); }
	 * // 提供了取学科声誉和学科排名打分的简便方式 else if (null == questionId && questionType !=
	 * null) { results = evalResultDao.getEvalResultsByQuestionType(expertId,
	 * questionType, discId); } Integer totalFinishedNumber = 0; for (EvalResult
	 * result : results) { String value = result.getEvalValue(); value =
	 * value.replace(" ", ""); if (null == value || "".equals(value)) {
	 * 
	 * } else { totalFinishedNumber++; } } return totalFinishedNumber; }
	 */

	/*
	 * @Override public Integer getIndicatorIndexFinishedNumber(String expertId,
	 * String discId) { List<EvalResult> results =
	 * evalResultDao.getResultsByQType( expertId, QType.INDIC_IDX.toInt(),
	 * discId); Integer totalFinishedNumber = 0; for (EvalResult result :
	 * results) { String value = result.getEvalValue(); if (value == null) {
	 * continue; } else { value = value.replace(" ", ""); System.out.print(value
	 * + ","); if (null == value || "".equals(value)) {
	 * 
	 * } else { totalFinishedNumber++; } } } return totalFinishedNumber; }
	 * 
	 * @Override public Integer getIndicatorWeightFinishedNumber(String
	 * expertId, String discId) { List<EvalResult> results =
	 * evalResultDao.getResultsByQType( expertId, QType.INDIC_WT.toInt(),
	 * discId); Integer totalFinishedNumber = 0; for (EvalResult result :
	 * results) { String value = result.getEvalValue(); //
	 * value的形式是：1,2,3,4。参数-1表示贪婪匹配 String[] valArr = value.split(",", -1); for
	 * (String val : valArr) { val = val.replace(" ", ""); if (null == val ||
	 * "".equals(val)) {
	 * 
	 * } else { totalFinishedNumber++; } } } return totalFinishedNumber; }
	 * 
	 * @Override public List<String> getAttendUnits(String discId, int
	 * pageIndex, int pageSize) { // 第四个参数表示"参评" PageVM<EvalVM> evalVMPage =
	 * evalFlowService.getCollectEvalByPage(null, discId, null, true, null,
	 * pageIndex, pageSize, true, null);
	 * 
	 * @SuppressWarnings("unchecked") List<EvalVM> evalVMList = (List<EvalVM>)
	 * evalVMPage.getGridData().get( "rows");
	 * 
	 * // 通过discCode获得该学科有多少个学校参评 List<String> units = new ArrayList<String>();
	 * for (EvalVM evalVM : evalVMList) {
	 * units.add(evalVM.getEval().getUnitId()); } return units; }
	 * 
	 * @Override public PageVM<EvalRepuVM> getResult(String expertId, String
	 * questionId, String discId, int pageIndex, int pageSize, boolean asc,
	 * String orderProperName) {
	 * 
	 * List<String> attendUnits = getAttendUnits(discId, pageIndex, pageSize);
	 * List<String> allUnits = getAttendUnits(discId, 1, 1000); int totalCount =
	 * allUnits.size(); List<EvalResult> results =
	 * evalResultDao.getEvalResultsByQuestionId( expertId, questionId, discId);
	 * List<EvalRepuVM> vms = new ArrayList<EvalRepuVM>(); EvalRepuVM vm = null;
	 * // 给有结果的学校赋打分结 for (String unit : attendUnits) { boolean alreadyScored =
	 * false; for (EvalResult result : results) { //if
	 * (unit.equals(result.getUnitId())) { // 临时！上面是原来写法 if (true) {
	 * alreadyScored = true; vm = new EvalRepuVM( //result.getDisciplineId(),
	 * result.getUnitId(), "", "", result.getEvalValue(), result.getId());
	 * vms.add(vm); break; } } if (alreadyScored) { continue; } else { vm = new
	 * EvalRepuVM(discId, unit, null, null); vms.add(vm); } }
	 * 
	 * PageVM<EvalRepuVM> result = new PageVM<EvalRepuVM>(pageIndex, totalCount,
	 * pageSize, vms); return result; }
	 */

	private List<IndexMap> convertIndexMapToTree(List<IndexMap> indexItems) {
		List<L1stIdx> tree = new ArrayList<L1stIdx>();
		for (IndexMap item : indexItems) {
			if (1 == item.getIndexLevel()) {
				L1stIdx firstLevelIndexItem = new L1stIdx();
				firstLevelIndexItem.setIdx(item);
				tree.add(firstLevelIndexItem);
			} else if (2 == item.getIndexLevel()) {
				L2ndIdx secondLevelIndexItem = new L2ndIdx();
				secondLevelIndexItem.setIdx(item);
				for (L1stIdx firstLevelIndexItem : tree) {
					// 寻找这个二级的指标体系的父指标体系
					if (item.getParentId().equals(
							firstLevelIndexItem.getIdx().getId())) {
						firstLevelIndexItem.getL2ndList().add(
								secondLevelIndexItem);
						break;
					}
				}
			} else if (3 == item.getIndexLevel()) {
				for (L1stIdx firstLevelIndexItem : tree) {
					List<L2ndIdx> secondLevel = firstLevelIndexItem
							.getL2ndList();
					{
						for (L2ndIdx secondLevelIndexItem : secondLevel) {
							if (item.getParentId().equals(
									secondLevelIndexItem.getIdx().getId())) {
								secondLevelIndexItem.getL3rdList().add(item);
								break;
							}
						}
					}
				}
			}
		}
		ArrayList<IndexMap> sequenceIndexMap = new ArrayList<IndexMap>();
		for (L1stIdx firstLevelIndexItem : tree) {
			sequenceIndexMap.add(firstLevelIndexItem.getIdx());
			List<L2ndIdx> secondLevel = firstLevelIndexItem.getL2ndList();
			for (L2ndIdx secondLevelIndexItem : secondLevel) {
				sequenceIndexMap.add(secondLevelIndexItem.getIdx());
				List<IndexMap> thirdLevel = secondLevelIndexItem.getL3rdList();
				for (IndexMap index : thirdLevel) {
					sequenceIndexMap.add(index);
				}
			}
		}
		return sequenceIndexMap;
	}

	/*
	 * @Override public void saveEvalIndexItem(String discId, String score,
	 * String indexItemId, String resultId, String expertId, String state,
	 * EvalQuestionVM question) { EvalQuestion q = question.getQuestion(); if
	 * (!resultId.equals("")) { EvalResult e = new EvalResult();
	 * e.setId(resultId); e.setEvalQuestion(q); //e.setDisciplineId(discId);
	 * e.setExpertId(expertId); if (score.equals("")) { e.setEvalValue(null);
	 * e.setEvalValueState(null); } else { e.setEvalValue(score);
	 * e.setEvalValueState(state); } evalResultDao.updateEvalAchievement(e); }
	 * else if (resultId.equals("") && (!score.equals(""))) { EvalResult result
	 * = new EvalResult(); result.setEvalQuestion(q);
	 * //result.setDisciplineId("0835"); result.setEvalValue(score);
	 * result.setExpertId(expertId); result.setEvalValueState(state);
	 * evalResultDao.save(result); } }
	 */

	// ==================================================================================
	// setter and getter nothing to do with business
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

	public EvalPaperDao getEvalPaperDao() {
		return evalPaperDao;
	}

	public void setEvalPaperDao(EvalPaperDao evalPaperDao) {
		this.evalPaperDao = evalPaperDao;
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

	public ExpertDao getExpertDao() {
		return expertDao;
	}

	public void setExpertDao(ExpertDao expertDao) {
		this.expertDao = expertDao;
	}

	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public DiscCategoryDao getDiscCategoryDao() {
		return discCategoryDao;
	}

	public void setDiscCategoryDao(DiscCategoryDao discCategoryDao) {
		this.discCategoryDao = discCategoryDao;
	}

	public EvalProgService getEvalProgService() {
		return evalProgService;
	}

	public void setEvalProgService(EvalProgService evalProgService) {
		this.evalProgService = evalProgService;
	}

	public DisciplineService getDisciplineService() {
		return disciplineService;
	}

	public void setDisciplineService(DisciplineService disciplineService) {
		this.disciplineService = disciplineService;
	}

	public EvalAchvDao getEvalAchvDao() {
		return evalAchvDao;
	}

	public void setEvalAchvDao(EvalAchvDao evalAchvDao) {
		this.evalAchvDao = evalAchvDao;
	}

	public EvalIndicWtDao getEvalIndicWtDao() {
		return evalIndicWtDao;
	}

	public void setEvalIndicWtDao(EvalIndicWtDao evalIndicWtDao) {
		this.evalIndicWtDao = evalIndicWtDao;
	}

	public EvalBatchDao getEvalBatchDao() {
		return evalBatchDao;
	}

	public void setEvalBatchDao(EvalBatchDao evalBatchDao) {
		this.evalBatchDao = evalBatchDao;
	}

}

class L1stIdx {
	IndexMap idx;
	List<L2ndIdx> l2ndList = new ArrayList<L2ndIdx>();

	public IndexMap getIdx() {
		return idx;
	}

	public void setIdx(IndexMap idx) {
		this.idx = idx;
	}

	public List<L2ndIdx> getL2ndList() {
		return l2ndList;
	}

	public void setL2ndList(List<L2ndIdx> l2ndList) {
		this.l2ndList = l2ndList;
	}
}

class L2ndIdx {
	IndexMap idx;
	List<IndexMap> l3rdList = new ArrayList<IndexMap>();

	public IndexMap getIdx() {
		return idx;
	}

	public void setIdx(IndexMap idx) {
		this.idx = idx;
	}

	public List<IndexMap> getL3rdList() {
		return l3rdList;
	}

	public void setL3rdList(List<IndexMap> l3rdList) {
		this.l3rdList = l3rdList;
	}
}