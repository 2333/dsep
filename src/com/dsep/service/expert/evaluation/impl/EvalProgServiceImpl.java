package com.dsep.service.expert.evaluation.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalAchvDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalPaperDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalQuestionDao;
import com.dsep.dao.dsepmeta.expert.evaluation.EvalResultDao;
import com.dsep.dao.dsepmeta.expert.selection.ExpertDao;
import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.entity.expert.EvalAchv;
import com.dsep.entity.expert.EvalPaper;
import com.dsep.entity.expert.EvalQuestion;
import com.dsep.entity.expert.Expert;
import com.dsep.service.expert.evaluation.EvalAchvService;
import com.dsep.service.expert.evaluation.EvalIndicIdxService;
import com.dsep.service.expert.evaluation.EvalIndicWtService;
import com.dsep.service.expert.evaluation.EvalProgService;
import com.dsep.service.expert.evaluation.EvalRepuService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.expert.EvalProgressVM;

/**
 * 看注释：DO NOT PRESS SHIFT+F!!
 * YOU'D BETTER SET THE CONFIG:
 * Window->Preferences->Java->Code Style
 * ->Formatter->Active Profile(press Edit)
 * ->Comments->all disable
 *
 */
public class EvalProgServiceImpl implements EvalProgService {
	private EvalPaperDao evalPaperDao;
	private EvalQuestionDao evalQuestionDao;
	private EvalAchvDao evalAchvDao;
	private ExpertDao expertDao;
	private DiscCategoryDao discCategoryDao;
	private EvalResultDao evalResultDao;

	private EvalAchvService evalAchvService;
	private EvalIndicIdxService evalIndicIdxService;
	private EvalIndicWtService evalIndicWtService;
	private EvalRepuService evalRepuService;

	// 最早可以打分的题目序号
	//private Integer earliestQ;

	private String indicIdxRoute = "/DSEP/evaluation/progress/indicIdx/";
	private String indicWtRoute = "/DSEP/evaluation/progress/indicWt/";
	private String achvRoute = "/DSEP/evaluation/progress/achv/";
	private String rapuRoute = "/DSEP/evaluation/progress/repu/";
	private String rankRoute = "/DSEP/evaluation/progress/rank/";

	@Override
	public String getQPreview(String batchId, String expertId) {
		List<EvalProgressVM> list = getMetaQuestions(batchId, expertId);
		return JsonConvertor.obj2JSON(list);
	}

	@Override
	public List<String> getRoutes(CurrentBatchExpertInfo info) {
		String batchId = info.getCurrentBatchId();
		String expertId = info.getExpertId();
		List<EvalProgressVM> list = getMetaQuestions(batchId, expertId);
		List<String> routes = new ArrayList<String>();
		for (EvalProgressVM vm : list) {
			routes.add(vm.getcLink());
		}
		return routes;
	}

	/** 格式：
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
	*/
	private List<EvalProgressVM> getMetaQuestions(String batchId,
			String expertId) {
		Expert expert = expertDao.get(expertId);

		String discId = (expert.getUseDiscId()) ? expert.getDiscId()
				: expert.getDiscId2();

		String discCatId = discCategoryDao.getCatByDiscId(discId);

		EvalPaper paper = evalPaperDao.getEvalPaper(expert.getExpertType(),
				discCatId, expert.getEvalBatch().getId());

		// 通过专家的对应Paper所规定的元Question，能够了解专家的任务
		List<EvalQuestion> metaQuestions = new LinkedList<EvalQuestion>();

		// 来自于底层order by Q_Type, sub_question_id asc以保证有序
		metaQuestions.addAll(evalQuestionDao.getAllMetaQuestionsByPaperId(paper
				.getId()));

		List<EvalProgressVM> list = new ArrayList<EvalProgressVM>();

		for (int i = 0; i < metaQuestions.size(); i++) {
			EvalQuestion metaQ = metaQuestions.get(i);
			Integer qType = metaQ.getQType();
			// 以一道题目为例写注释，其他都是类似
			if (qType == QType.INDIC_IDX.toInt()
					|| qType == QType.INDIC_WT.toInt()
					|| qType == QType.REPU.toInt()
					|| qType == QType.RANK.toInt()) {
				EvalProgressVM vm = null;
				if (qType == QType.INDIC_IDX.toInt())
					vm = createVM("指标体系打分", "指标体系打分", indicIdxRoute);
				else if (qType == QType.INDIC_WT.toInt())
					vm = createVM("指标权重打分", "指标权重打分", indicWtRoute);
				else if (qType == QType.REPU.toInt())
					vm = createVM("学科声誉打分", "学科声誉打分", rapuRoute);
				else if (qType == QType.RANK.toInt())
					vm = createVM("学科排名打分", "学科排名打分", rankRoute);

				// 根据results状态判断打分进度
				vm.setbPCT(checkSchedule(qType, batchId, expertId, null));
				// 加入最终要转换为json显示的list中
				list.add(vm);
			} else if (metaQ.getQType() == QType.ACHV.toInt()) {
				List<String> states = new ArrayList<String>();
				
				List<EvalQuestion> achvQs = new ArrayList<EvalQuestion>();
				List<EvalAchv> achvList = evalAchvDao.getByDiscCatId(discCatId);
				for (EvalAchv achv : achvList) {
					achvQs.add(metaQ);
					states.add(checkSchedule(QType.ACHV.toInt(), batchId,
							expertId, achv.getId()));
				}
				
				/*i++;
				while (i < metaQuestions.size()
						&& metaQuestions.get(i).getQType() == QType.ACHV
								.toInt()) {
					achvQs.add(metaQuestions.get(i));
					states.add(checkSchedule(QType.ACHV.toInt(), batchId,
						expertId, metaQuestions.get(i).getSubQuestionId()));
					// 不要写出 i = i++; 这样逗比的代码
					i++;
				}
				// 指针回退刚刚不满足while条件而+1的i
				i--;*/
				list.addAll(createAchievementItems(achvQs, achvRoute, states, discCatId));
			}
		}
		return list;
	}

	// 根据专家打分结果判断进度
	private String checkSchedule(Integer qType, String batchId,
			String expertId, String subQuestionId) {
		if (qType == QType.INDIC_IDX.toInt()) {
			return evalIndicIdxService.getIndicIdxProg(batchId, expertId);
		} else if (qType == QType.INDIC_WT.toInt()) {
			return evalIndicWtService.getIndicWtProg(batchId, expertId);
		} else if (qType == QType.ACHV.toInt()) {
			return evalAchvService.getAchvProg(batchId, expertId, subQuestionId);
		} else if (qType == QType.REPU.toInt()) {
			return evalRepuService.getRepuProg(batchId, expertId);
		} else {
			return "err";
		}
	}

	// 统一处理指标体系打分、指标权重打分、学科声誉打分、学科排名打分
	private EvalProgressVM createVM(String mainCellName, String subCellName,
			String link) {
		EvalProgressVM item = new EvalProgressVM();
		item.setaName(mainCellName);
		item.setbName(subCellName);
		item.setcLink(link);
		// 因为指标体系打分、指标权重打分、学科声誉打分、学科排名打分都是一行
		// 所以手动设定mainCellDisplay = subCellDisplay = 1
		item.getAttr().getaNameIndex().setDisplay("1");
		item.getAttr().getbNameIndex().setDisplay("1");
		item.getAttr().getbPCTIndex().setDisplay("1");
		return item;
	}

	// 因为成果打分有多项，所以要在for循环中处理，并解决格式问题
	private List<EvalProgressVM> createAchievementItems(
			List<EvalQuestion> questions, String link, List<String> states, String discCatId) {
		List<EvalProgressVM> list = new ArrayList<EvalProgressVM>();
		List<EvalAchv> evalAchvList = evalAchvDao.getByDiscCatId(discCatId);
		boolean firstQuestion = true;
		for (int i = 0; i < evalAchvList.size(); i++) {
			EvalAchv achv = evalAchvList.get(i);
			String state = states.get(i);
			EvalProgressVM item = new EvalProgressVM();
			//item.setId(id);
			item.setaName("学科成果打分");
			// 获得具体学科成果打分项名称
			item.setbName(achv.getQuestionName());

			item.setaPCT("0");
			item.setbPCT(state);
			item.setcLink(link + achv.getId() + "/");
			list.add(item);
			// 设置前端显示格式
			if (firstQuestion) {
				item.getAttr().getaNameIndex()
						.setDisplay(String.valueOf(questions.size()));
				firstQuestion = false;
			} else {
				item.getAttr().getaNameIndex().setDisplay("none");
				item.getAttr().getaPCTIndex().setDisplay("none");
			}
			item.getAttr().getbNameIndex().setDisplay("1");
			item.getAttr().getbPCTIndex().setDisplay("1");
		}
		return list;
	}

	// 与业务逻辑无关的getter和setter=============================================
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

	public EvalResultDao getEvalResultDao() {
		return evalResultDao;
	}

	public void setEvalResultDao(EvalResultDao evalResultDao) {
		this.evalResultDao = evalResultDao;
	}

	public EvalAchvService getEvalAchvService() {
		return evalAchvService;
	}

	public void setEvalAchvService(EvalAchvService evalAchvService) {
		this.evalAchvService = evalAchvService;
	}

	public EvalIndicIdxService getEvalIndicIdxService() {
		return evalIndicIdxService;
	}

	public void setEvalIndicIdxService(EvalIndicIdxService evalIndicIdxService) {
		this.evalIndicIdxService = evalIndicIdxService;
	}

	public EvalIndicWtService getEvalIndicWtService() {
		return evalIndicWtService;
	}

	public void setEvalIndicWtService(EvalIndicWtService evalIndicWtService) {
		this.evalIndicWtService = evalIndicWtService;
	}

	public EvalRepuService getEvalRepuService() {
		return evalRepuService;
	}

	public void setEvalRepuService(EvalRepuService evalRepuService) {
		this.evalRepuService = evalRepuService;
	}
}
