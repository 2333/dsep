package com.dsep.service.expert.evaluation.impl;

import java.util.ArrayList;
import java.util.List;

import com.dsep.dao.dsepmeta.expert.evaluation.EvalProgressDao;
import com.dsep.entity.dsepmeta.EvalProgress;
import com.dsep.service.expert.evaluation.EvalIndicWtService;
import com.dsep.service.expert.evaluation.EvalProgressServiceObsoleting;
import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.service.expert.evaluation.EvalIndicIdxService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.expert.eval.EvalProgressPCTFormatter;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalProgressVM;
import com.dsep.vm.expert.EvalQuestionVM;

/**
 * 看注释：DO NOT PRESS SHIFT+F!!
 * YOU'D BETTER SET THE CONFIG:
 * Window->Preferences->Java->Code Style
 * ->Formatter->Active Profile(press Edit)
 * ->Comments->all disable
 *
 */
public class EvalProgressServiceImplObsoleting implements EvalProgressServiceObsoleting {

	/*private EvalIndicIdxService evalIndicIdxService;
	private EvalService evalService;
	private EvalProgressDao evalProgressDao;
	private EvalIndicWtService evalIndicWtService;

	// 最早可以打分的题目序号
	//private Integer earliestQ;

	private String indicatorRoute = "/DSEP/evaluation/progress/indicator/";
	private String indicatorWeightRoute = "/DSEP/evaluation/progress/indicatorWeight/";
	private String achievementRoute = "/DSEP/evaluation/progress/achievement/";
	private String raputationRoute = "/DSEP/evaluation/progress/reputation/";
	private String rankingRoute = "/DSEP/evaluation/progress/ranking/";

	*//**
	 * 在专家确认打分时，初始化专家的打分进度
	 * 这里已知打分大项有：指标体系打分、指标权重打分、学科成果打分、学科声誉打分和学校排名
	 * 所以大项都是写死的，只有小项（如：学科成果打分的成果子项）是要在通过数据库动态生成的
	 * 
	 * 这里打分大项是有序的，因为以后在显示专家打分进度的表格和规定专家的打分进程时，要通过顺序进行
	 * 
	 * 这里采用的是sequ字段人工指定顺序，也就是顺序在前的实体先被保存，并且该实体应该先被打分
	 * 
	 *//*
	@Override
	public void initExpertEvalProgress(String expertId, String discId) {
		List<EvalProgress> list = new ArrayList<EvalProgress>();
		//==========================指标体系开始==============================
		@SuppressWarnings("rawtypes")
		PageVM page = this.evalService.getIndexMapTree(null);
		// ！指标体系打分和指标权重打分没有必要拿到EvalQuestionVM！
		// ！指标体系打分和指标权重打分也不需要setSubQuestionId，它们的存储方式和后三项不一样！
		EvalProgress indicatorIndexEvalProgressItem = createEvalProgressItem(
				expertId, page.getTotalCount(), null);
		indicatorIndexEvalProgressItem.setQuestionType(QType.INDIC_IDX
				.toInt());
		indicatorIndexEvalProgressItem.setSubQuestionChName("指标体系打分");
		list.add(indicatorIndexEvalProgressItem);
		//==========================指标体系结束==============================

		//==========================指标权重开始==============================
		// 以下为模拟
		EvalProgress indicatorWeightEvalProgressItem = new EvalProgress();
		indicatorWeightEvalProgressItem.setExpertId(expertId);
		indicatorWeightEvalProgressItem
				.setTotalNumber(1);
						//this.evalIndicatorWeightService
						//.getTotalNumbers());
		indicatorWeightEvalProgressItem
				.setQuestionType(QType.INDIC_WT.toInt());
		indicatorWeightEvalProgressItem.setSubQuestionChName("指标权重打分");
		list.add(indicatorWeightEvalProgressItem);
		//==========================指标权重结束==============================

		//==========================上下方式不一样============================

		//==========================学科成果开始==============================
		List<EvalQuestionVM> questions = null; 
				//this.evalService.getQuestions(
				//expertId, QuestionType.ACHIEVEMENT.toInt());
		// 这里是不是应该让朱明远给我写一个不分页的函数，还是传一个非常大的数?
		List<String> attendUnits = this.evalService.getAttendUnits(discId, 1,
				1000);
		for (EvalQuestionVM questionVM : questions) {
			list.add(createEvalProgressItem(expertId, attendUnits.size(),
					questionVM));
		}
		//==========================学科成果结束==============================

		//==========================学科声誉开始==============================
		EvalQuestionVM reputationQuestionVM = null;
				//this.evalService.getQuestions(
				//expertId, QuestionType.REPUTATION.toInt()).get(0);
		list.add(createEvalProgressItem(expertId, attendUnits.size(),
				reputationQuestionVM));
		//==========================学科声誉结束==============================

		//==========================学科排名开始==============================
		EvalQuestionVM rankingQuestionVM = null;
				//this.evalService.getQuestions(
				//expertId, QuestionType.RANKING.toInt()).get(0);
		list.add(createEvalProgressItem(expertId, attendUnits.size(),
				rankingQuestionVM));
		//==========================学科排名结束==============================

		for (int i = 1; i <= list.size(); i++) {
			EvalProgress item = list.get(i - 1);
			// 人工指定顺序，即上面的构建顺序
			item.setSequ(i);
			this.evalProgressDao.save(item);
		}
	}

	private EvalProgress createEvalProgressItem(String expertId,
			Integer totalNumber, EvalQuestionVM questionVM) {
		EvalProgress item = new EvalProgress();
		item.setExpertId(expertId);
		item.setTotalNumber(totalNumber);

		// 初始化时finishedNumber一律设置为0，重要！
		item.setFinishedNumber(0);

		// 指标权重和指标体系打分没有questionVM
		// 指标权重和指标体系没有必要关联questionId，只要根据QuestionType取数据
		if (null != questionVM) {
			item.setSubQuestionId(questionVM.getQuestionId());
			Integer questionType = questionVM.getQuestion().getQType();
			item.setQuestionType(questionType);
			item.setSubQuestionChName(questionVM.getEvalQuestionName());
		}
		return item;
	}

	*//** 格式：
	 * 一名名称->二级名称（是否固定）
	 * 指标打分->指标体系打分（固定的，每个专家都有的）
	 * 指标打分->指标权重打分（固定的，每个专家都有的）
	 * 成果主观项打分->高水平论文（不固定，不同的专家的主观项打分数目和名字都不一样）
	 * 成果主观项打分->优秀在校生与毕业生（不固定，不同的专家的主观项打分数目和名字都不一样）
	 * 学科声誉->null（固定的，每个专家都有的）
	 * 学科排名->null（固定的，每个专家都有的）
	 * 
	 * 前台展示时的JSON数据格式：
		{id : "1",	aName : "指标打分",
					aPCT : "30%",
					bName : "指标体系打分",
					bPCT : "50%",	cLink : "",
		attr: {	aNameIndex : {display: "2"},
				aPCTIndex  : {display: "2"},
				bNameIndex : {display: "1"},
				bPCTIndex  : {display: "1"}}},
		这其中：
		aNameIndex和aPCTIndex后面的属性display是一样的，因为它们的展示形式是一样的
		bNameIndex和bPCTIndex后面的属性display是一样的，因为它们的展示形式是一样的
		display是数字表示rowspan（合并单元格）的个数（"1"表示不合并），是"none"表示这行不显示 
		
		后台对应的VM有
		最外层的EvalProgressVM
		中间的EvalProgressAttrVM
		和EvalProgressAttrVM里面的EvalProgressRowVM 
	*//*
	@Override
	public String getProgressTableContent(String expertId, String discId) {
		//earliestQ = evalProgressDao.getEarliestQ(expertId);

		int earliestQ = 10;
		
		List<EvalProgressVM> list = new ArrayList<EvalProgressVM>();

		list.add(createVM(QType.INDIC_IDX, expertId, discId, earliestQ,
				indicatorRoute));

		list.add(createVM(QType.INDIC_WT, expertId, discId,
				earliestQ, indicatorWeightRoute));

		list.addAll(createAchievementItems(expertId, discId, earliestQ,
				achievementRoute));

		list.add(createVM(QType.REPU, expertId, discId, earliestQ,
				raputationRoute));

		list.add(createVM(QType.RANK, expertId, discId, earliestQ,
				rankingRoute));

		String jSON = JsonConvertor.obj2JSON(list);
		System.out.println(jSON);

		return jSON;
	}

	// 统一处理指标体系打分、指标权重打分、学科声誉打分、学科排名打分
	private EvalProgressVM createVM(QType type, String expertId,
			String discId, Integer earliestQ, String link) {
		// 因为已知问题项只有一个，直接get(0)
		EvalProgress entity = evalProgressDao.getEvalProgressByQuestionType(
				expertId, type.toInt()).get(0);
		// 因为指标体系打分、指标权重打分、学科声誉打分、学科排名打分都是一行
		// 所以手动设定mainCellDisplay = subCellDisplay = 1
		// mainCellName = subCellName = entity.getSubQuestionChName
		return createProgressTableContentItem(entity.getSubQuestionChName(),
				entity.getSubQuestionChName(), entity, "1", "1", earliestQ,
				link);
	}

	// 真正处理显示，合并表格的方法
	private EvalProgressVM createProgressTableContentItem(String mainCellName,
			String subCellName, EvalProgress evalProgressEntity,
			String mainCellDisplay, String subCellDisplay,
			Integer earliestItem, String link) {
		EvalProgressVM item = new EvalProgressVM();
		//item.setId(String.valueOf(evalProgressEntity.getId()));

		item.setaName(mainCellName);
		item.setbName(subCellName);

		Integer finishedNumber = evalProgressEntity.getFinishedNumber();
		Integer totalNumber = evalProgressEntity.getTotalNumber();
		Double PCT = (Double.valueOf(finishedNumber) / totalNumber);
		item.setaPCT(EvalProgressPCTFormatter.getStdPCT(PCT));
		item.setbPCT(EvalProgressPCTFormatter.getStdPCT(PCT));

		// 如果最早可以进行的打分项的序号比当前大，则当前可以打分
		// 如：最早可以打第五题的分数，那么前四题也可以打分，但第六题不可以
		if (earliestItem >= evalProgressEntity.getSequ()) {
			item.setcLink(link);
		}

		// 以下写死因为是格式是已知的、固定的
		item.getAttr().getaNameIndex().setDisplay(mainCellDisplay);
		item.getAttr().getaPCTIndex().setDisplay(mainCellDisplay);
		item.getAttr().getbNameIndex().setDisplay(subCellDisplay);
		item.getAttr().getbPCTIndex().setDisplay(subCellDisplay);
		return item;
	}

	// 因为成果打分有多项，所以要在for循环中处理，并解决格式问题
	private List<EvalProgressVM> createAchievementItems(String expertId,
			String discId, Integer earliestItem, String link) {
		List<EvalProgress> entities = evalProgressDao
				.getEvalProgressByQuestionType(expertId,
						QType.ACHV.toInt());
		List<EvalProgressVM> list = new ArrayList<EvalProgressVM>();

		boolean firstQuestion = true;
		Double totalPCT = 0D;
		for (EvalProgress entity : entities) {
			EvalProgressVM item = new EvalProgressVM();
			// 设定序号
			//item.setId(String.valueOf(entity.getId()));

			// 设定显示中文名称
			item.setaName("学科成果打分");
			item.setbName(entity.getSubQuestionChName());

			Integer finishedNumber = entity.getFinishedNumber();
			Integer totalNumber = entity.getTotalNumber();
			Double PCT = Double.valueOf(finishedNumber) / totalNumber;

			totalPCT += PCT * (1D / entities.size());
			// 因为参评学校都是一样的
			// 所以设定各个打分子项各占进度的1/n(n表示打分子项的个数，entities.size())
			item.setaPCT(EvalProgressPCTFormatter.getStdPCT(PCT
					* (1 / entities.size())));
			item.setbPCT(EvalProgressPCTFormatter.getStdPCT(PCT));

			// 设置前端显示格式
			if (firstQuestion) {
				item.getAttr().getaNameIndex()
						.setDisplay(String.valueOf(entities.size()));
				item.getAttr().getaPCTIndex()
						.setDisplay(String.valueOf(entities.size()));
				firstQuestion = false;
			} else {
				item.getAttr().getaNameIndex().setDisplay("none");
				item.getAttr().getaPCTIndex().setDisplay("none");
			}
			item.getAttr().getbNameIndex().setDisplay("1");
			item.getAttr().getbPCTIndex().setDisplay("1");

			if (earliestItem >= entity.getSequ()) {
				item.setcLink(link + entity.getSubQuestionId() + "/");
			}
			list.add(item);
		}

		if (list.size() > 0) {
			list.get(0).setaPCT(EvalProgressPCTFormatter.getStdPCT(totalPCT));
		}
		return list;
	}

	@Override
	public String listenerForSaveButton(String expertId, String questionId,
			Integer type, int alteredAnswerNumber) {
		Boolean isFinishedAfterUpdating = updateExpertEvalProgress(expertId,
				questionId, type, alteredAnswerNumber);
		if (isFinishedAfterUpdating) {
			return getNextQuestionRoute(expertId, questionId, type);
		} else {
			return null;
		}
	}

	private Boolean updateExpertEvalProgress(String expertId,
			String questionId, Integer type, int alteredAnswerNumber) {
		this.evalProgressDao.updateQuestionProgress(expertId, questionId,
				type, alteredAnswerNumber);
		return judgeAQuestionFinished(expertId, questionId, type);
	}

	private Boolean judgeAQuestionFinished(String expertId, String questionId,
			Integer questionType) {
		EvalProgress entity = getEvalProgressByQuestionIdOrQuestionType(
				expertId, questionId, questionType);

		// 重载的judgeAQuestionFinished方法，通过EvalProgress判断
		return judgeAQuestionFinished(entity);
	}

	private Boolean judgeAQuestionFinished(EvalProgress entity) {
		// 完成值等于总值，可能是正确的，也有可能是错误的（比如网络问题、多人同时登录一个账号等）
		// 但这里我们姑且返回“完成”，真正出问题了让用户在前台强制刷新
		if (entity.getFinishedNumber() == entity.getTotalNumber()) {
			return true;
		} else {
			return false;
		}

		// 完成值小于0肯定错误
		else if (entity.getFinishedNumber() < 0) {
			强制刷新，去结果页表取数据更新进度表
			System.out.println(">>>>>>>>>>>异常进度，强制刷新！！<<<<<<<<<<");
			forceFlushEvalProgressTable(entity.getExpertId(),
					entity.getSubQuestionId(), entity.getQuestionType(), discId);
		}

		// 完成值大于总值肯定错误
		else if (entity.getFinishedNumber() > entity.getTotalNumber()) {
			强制刷新，去结果页表取数据更新进度表
			System.out.println(">>>>>>>>>>>异常进度，强制刷新！！<<<<<<<<<<");
			forceFlushEvalProgressTable(entity.getExpertId(),
					entity.getSubQuestionId(), entity.getQuestionType(), discId);
		}
		// 完成值小于总值，可能是正确的，也有可能是错误的（比如网络问题、多人同时登录一个账号等）
		// 但这里我们姑且返回“没有完成”，真正出问题了让用户在前台强制刷新
		
		else if (entity.getFinishedNumber() < entity.getTotalNumber()) {
			return false;
		}
		

		// 刷新之后的新数据
		EvalProgress forceFlushedEntity = getEvalProgressByQuestionIdOrQuestionType(
				entity.getExpertId(), entity.getSubQuestionId(),
				entity.getQuestionType());

		// 强制从结果表中拿到数据更新进度表，我们就认为没有问题了
		if (forceFlushedEntity.getFinishedNumber() == forceFlushedEntity
				.getTotalNumber()) {
			return true;
		} else {
			return false;
		}
	}

	// 从结果表中
	private void forceFlushEvalProgressTable(String expertId, String questionId,
			Integer questionType, String discId) {
		Integer finishedNumber = 0;
		if (!(questionType == QType.INDIC_IDX.toInt())
				&& !(questionType == QType.INDIC_WT.toInt())) {
			finishedNumber = evalService.getSubjectiveQuestionFinishedNumber(expertId, questionId, questionType, discId);
			evalProgressDao.flushQuestoinProgress(expertId, questionId, questionType, finishedNumber);
		} else if (questionType == QType.INDIC_IDX.toInt()) {
			finishedNumber = evalService.getIndicatorIndexFinishedNumber(expertId, discId);
		} else if (questionType == QType.INDIC_WT.toInt()) {
			finishedNumber = evalService.getIndicatorWeightFinishedNumber(expertId, discId);
		}
		evalProgressDao.flushQuestoinProgress(expertId, questionId, questionType, finishedNumber);
	}

	@Override
	public String getNextQuestionRoute(String expertId, String questionId,
			Integer type) {
		EvalProgress entity = getEvalProgressByQuestionIdOrQuestionType(
				expertId, questionId, type);

		// 该题目还没有打完，不能拿到
		if (!judgeAQuestionFinished(entity)) {
			return null;
		} else {
			// step设为1表示拿后面一道题目的route
			return getRoute(entity, 1);
		}

	}

	private EvalProgress getEvalProgressByQuestionIdOrQuestionType(
			String expertId, String questionId, Integer questiontype) {
		EvalProgress entity = null;
		// 目前只有成果打分需要questionId区分
		// 因为很多成果打分是同一个questionType，但是仅questionType无法确定完成哪道题
		if (null != questionId) {
			entity = evalProgressDao.getEvalProgressByQuestionId(expertId,
					questionId);
		} else {
			entity = evalProgressDao.getEvalProgressByQuestionType(expertId,
					questiontype).get(0);
		}
		return entity;
	}

	@Override
	public String getPrevQuestionRoute(String expertId, String questionId,
			QType type) {
		EvalProgress entity = null;
		// 目前只有成果打分需要questionId区分
		// 因为很多成果打分是同一个questionType，但是仅questionType无法确定完成哪道题
		if (null != questionId) {
			entity = evalProgressDao.getEvalProgressByQuestionId(expertId,
					questionId);
		} else {
			entity = evalProgressDao.getEvalProgressByQuestionType(expertId,
					type.toInt()).get(0);
		}
		// step设为-1表示拿前面一道题目的route
		return getRoute(entity, -1);
	}

	private String getRoute(EvalProgress entity, Integer step) {
		// 因为存储的时候是有序的
		Integer anotherSequ = entity.getSequ() + step;
		EvalProgress anotherEntity = evalProgressDao.getBySequ(
				entity.getExpertId(), anotherSequ);

		if (null == anotherEntity) {
			return null;
		}

		if (anotherEntity.getQuestionType() == QType.INDIC_IDX.toInt()) {
			return indicatorRoute;
		} else if (anotherEntity.getQuestionType() == QType.INDIC_WT
				.toInt()) {
			return indicatorWeightRoute;
		} else if (anotherEntity.getQuestionType() == QType.ACHV
				.toInt()) {
			return achievementRoute + anotherEntity.getSubQuestionId();
		} else if (anotherEntity.getQuestionType() == QType.REPU
				.toInt()) {
			return raputationRoute;
		} else if (anotherEntity.getQuestionType() == QType.RANK
				.toInt()) {
			return rankingRoute;
		} else {
			return null;
		}
	}

	@Override
	public String getIndicatorIndexPCT(String expertId) {
		EvalProgress evalProgressEntity1 = evalProgressDao
				.getEvalProgressByQuestionType(expertId,
						QType.INDIC_IDX.toInt()).get(0);
		List<EvalProgress> list = new ArrayList<EvalProgress>();
		list.add(evalProgressEntity1);
		return getPCT(list);
	}

	@Override
	public String getIndicatorWeightPCT(String expertId) {
		EvalProgress evalProgressEntity1 = evalProgressDao
				.getEvalProgressByQuestionType(expertId,
						QType.INDIC_WT.toInt()).get(0);
		List<EvalProgress> list = new ArrayList<EvalProgress>();
		list.add(evalProgressEntity1);
		return getPCT(list);
	}

	@Override
	public String getAchievementPCT(String expertId) {
		List<EvalProgress> entities = evalProgressDao
				.getEvalProgressByQuestionType(expertId,
						QType.ACHV.toInt());
		return getPCT(entities);
	}

	@Override
	public String getReputationPCT(String expertId) {
		EvalProgress evalProgressEntity = evalProgressDao
				.getEvalProgressByQuestionType(expertId,
						QType.REPU.toInt()).get(0);
		List<EvalProgress> list = new ArrayList<EvalProgress>();
		list.add(evalProgressEntity);
		return getPCT(list);
	}

	@Override
	public String getRankingPCT(String expertId) {
		EvalProgress evalProgressEntity = evalProgressDao
				.getEvalProgressByQuestionType(expertId,
						QType.RANK.toInt()).get(0);

		List<EvalProgress> list = new ArrayList<EvalProgress>();
		list.add(evalProgressEntity);
		return getPCT(list);
	}

	private String getPCT(List<EvalProgress> list) {
		Double totalPCT = 0D;
		Boolean finishedNumberStillZero = true;
		for (EvalProgress entity : list) {
			Integer finishedNumber = entity.getFinishedNumber();
			if (0 != finishedNumber) {
				finishedNumberStillZero = false;
			}
			Integer totalNumber = entity.getTotalNumber();
			Double PCT = Double.valueOf(finishedNumber) / totalNumber;

			// 因为参评学校都是一样的
			// 所以设定各个打分子项各占进度的1/n(n表示打分子项的个数，entities.size())
			totalPCT += PCT * (1D / list.size());
		}
		if (finishedNumberStillZero) {
			return "0%";
		} else {
			return EvalProgressPCTFormatter.getStdPCT(totalPCT);
		}
	}

	// 与业务逻辑无关的getter和setter=============================================

	public EvalService getEvalService() {
		return evalService;
	}

	public EvalIndicIdxService getEvalIndicIdxService() {
		return evalIndicIdxService;
	}

	public void setEvalIndicIdxService(
			EvalIndicIdxService evalIndicIdxService) {
		this.evalIndicIdxService = evalIndicIdxService;
	}

	public void setEvalService(EvalService evalService) {
		this.evalService = evalService;
	}

	public EvalProgressDao getEvalProgressDao() {
		return evalProgressDao;
	}

	public void setEvalProgressDao(EvalProgressDao evalProgressDao) {
		this.evalProgressDao = evalProgressDao;
	}

	public EvalIndicWtService getEvalIndicWtService() {
		return evalIndicWtService;
	}

	public void setEvalIndicWtService(
			EvalIndicWtService evalIndicWtService) {
		this.evalIndicWtService = evalIndicWtService;
	}*/
}
