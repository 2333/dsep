package com.dsep.controller.expert.evaluation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.entity.expert.EvalResult;
import com.dsep.service.expert.evaluation.EvalIndicIdxService;
import com.dsep.service.expert.evaluation.EvalProgService;
import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.expert.EvalQuestionVM;

/**
 * 指标体系打分
 * indicIdx is short for 'indicator index'
 * 路由1(/progress/indicIdx/{batchId}/{expertId}):
 * 		专家的任务列表中设定的路由，通过该路由，首先在indicIdxCheckInfo方法中进行验证，
 * 		验证当前专家和任务批次是否发生了改变，改变则重置session信息，然后转交给indicIdx方法执行
 * 
 * 路由2(/progress/indicIdx):
 * 		1.由专家的任务列表路由转交调用
 * 		2.通过点击“上一项打分”调用
 * 		3.通过点击“下一项打分”调用
 * 		找到采集项打分的题目，并命名为indicIdxQs存入session，
 * 		此外，该路由对应的方法还set了一些路由等到model以传到前台，
 * 		最后返回Expert/eval_indic_idx.jsp
 * 
 * 路由3(/progress/indicIdxGetResults):
 *		展示指标体系的题目和结果
 * 
 * 路由4:(/progress/indicIdxSaveOrUpdateResults):
 * 		保存或更新前台结果
 */
@Controller
@RequestMapping("evaluation")
public class EvalIndicIdxController {

	// 从任务表格的链接中点击进入，会验证当前的已选专家和任务
	@RequestMapping("progress/indicIdx/{batchId}/{expertId}")
	public String indicIdxCheckInfo(HttpSession session, Model model,
			@PathVariable(value = "batchId") String batchId,
			@PathVariable(value = "expertId") String expertId) {
		// 验证任务是否改变，如果改变，重置session
		session = UtilCommon.setCurrentBatchExpertInfoIntoSession(session,
				evalProgService, batchId, expertId);
		return indicIdx(session, model);
	}

	@RequestMapping("progress/indicIdx")
	public String indicIdx(HttpSession session, Model model) {
		CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
				.getAttribute("info");
		// 和@RequestMapping一致，注意加上/DSEP/evaluation/
		String currentRoute = UtilDeploymentDescriptor.descriptor
				+ "progress/indicIdx/";

		List<EvalQuestionVM> questions = null;
		if (null == session.getAttribute("indicIdxQs")) {
			questions = evalService.getQs(info, QType.INDIC_IDX.toInt(), null);
			session.setAttribute("indicIdxQs", questions);
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
		return "Expert/eval_indic_idx";
	}

	@RequestMapping("progress/indicIdxGetResults")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
				.getAttribute("info");
		return evalIndicIdxService.showIndicIdxQAndResultsTable(info);
	}

	@RequestMapping("progress/indicIdxSaveOrUpdateResults")
	@ResponseBody
	public Boolean indicIdxSaveOrUpdateResults(
			@RequestBody List<UtilEvalResultFromJSP> results,
			HttpSession session) {
		@SuppressWarnings("unchecked")
		List<EvalQuestionVM> questions = (List<EvalQuestionVM>) session
				.getAttribute("indicIdxQs");

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
	
	// 与业务逻辑无关的Service==============================
	@Resource(name = "evalService")
	private EvalService evalService;

	@Resource(name = "evalIndicIdxService")
	private EvalIndicIdxService evalIndicIdxService;

	@Resource(name = "evalProgService")
	private EvalProgService evalProgService;
}