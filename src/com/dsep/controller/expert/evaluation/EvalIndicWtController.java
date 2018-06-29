package com.dsep.controller.expert.evaluation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.entity.expert.EvalResult;
import com.dsep.service.expert.evaluation.EvalIndicWtService;
import com.dsep.service.expert.evaluation.EvalProgService;
import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalIndicWtVM;
import com.dsep.vm.expert.EvalQuestionVM;

/**
 * 指标权重打分
 * 
 * 路由1(/progress/indicWt):打开Expert/eval_indicWt.jsp
 * 
 * 路由2(/progress/indicWtGetResults/):指标权重打分页面展现所有要打分的项目
 * 
 * 路由3(/progress/indicWtSaveOrUpdateResults):保存或更新所打分数
 * 
 */
//@Scope("prototype")
@Controller
@RequestMapping("evaluation")
public class EvalIndicWtController {

	@Resource(name = "evalIndicWtService")
	private EvalIndicWtService evalIndicWtService;

	@Resource(name = "evalService")
	private EvalService evalService;

	@Resource(name = "evalProgService")
	private EvalProgService evalProgService;

	@RequestMapping("progress/indicWt/{batchId}/{expertId}")
	public String indicWtCheckInfo(HttpSession session, Model model,
			@PathVariable(value = "batchId") String batchId,
			@PathVariable(value = "expertId") String expertId) {
		session = UtilCommon.setCurrentBatchExpertInfoIntoSession(session,
				evalProgService, batchId, expertId);
		return indicWt(session, model);
	}

	/**
	 * 路由1
	 * 保存或更新多条打分结果
	 */
	@RequestMapping("/progress/indicWt/")
	public String indicWt(HttpSession session, Model model) {
		CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
				.getAttribute("info");
		// 和@RequestMapping一致，注意加上/DSEP/evaluation/
		String currentRoute = UtilDeploymentDescriptor.descriptor
				+ "progress/indicWt/";

		List<EvalQuestionVM> questions = null;
		if (null == session.getAttribute("indicWtQs")) {
			questions = evalService.getQs(info, QType.INDIC_WT.toInt(), null);
			session.setAttribute("indicWtQs", questions);
		}
		// 部署的时候要改一下！
		String homeRoute = UtilDeploymentDescriptor.homeRoute;
		List<String> routes = info.getRoutes();
		String[] prevAndNextRoutes = UtilCommon.getPrevAndNextRoutes(routes,
				currentRoute);
		String prevQuestionRoute = prevAndNextRoutes[0];
		String nextQuestionRoute = prevAndNextRoutes[1];
		model.addAttribute("prevQuestionRoute", prevQuestionRoute);
		model.addAttribute("nextQuestionRoute", nextQuestionRoute);
		model.addAttribute("homeRoute", homeRoute);
		return "Expert/eval_indic_wt";
	}

	/**
	 * 路由2
	 */
	@RequestMapping(value = "/progress/indicWtGetResults/")
	@ResponseBody
	public String getIndicatorWeightItems(HttpSession session) {
		CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
				.getAttribute("info");
		PageVM<EvalIndicWtVM> vmList = evalIndicWtService.showIndicWtQAndResultsTable(info);
		String unitsJSON = JsonConvertor.obj2JSON(vmList.getGridData());
		return unitsJSON;
	}

	/**
	 * 路由3
	 * 保存或更新多条打分结果
	 */
	@RequestMapping(value = "/progress/indicWtSaveOrUpdateResults")
	@ResponseBody
	public Boolean saveResult(@RequestBody List<UtilEvalResultFromJSP> results,
			HttpSession session) {

		// 最开始验证一下前端数据的合法性
		//validateDataFromJSP(results);

		@SuppressWarnings("unchecked")
		List<EvalQuestionVM> questions = (List<EvalQuestionVM>) session
				.getAttribute("indicWtQs");

		CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
				.getAttribute("info");
		String expertId = info.getExpertId();

		// 从前台来的值，即将要存储到数据库中
		List<EvalResult> list = new ArrayList<EvalResult>();

		Date date = new Date();
		// 此for循环是在上一个for循环保证了前段数据的合法性后进行的保存
		for (UtilEvalResultFromJSP resultFromJSP : results) {
			EvalResult result = UtilCommon.extractEvalResult(resultFromJSP,
					questions, date);
			result.setExpertId(expertId);
			list.add(result);
		}

		evalService.saveResults(list);
		return true;
	}
}