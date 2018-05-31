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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.entity.expert.EvalResult;
import com.dsep.entity.expert.Expert;
import com.dsep.service.expert.evaluation.EvalProgService;
import com.dsep.service.expert.evaluation.EvalRepuService;
import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.service.file.ExportService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalQuestionVM;
import com.dsep.vm.expert.EvalRepuVM;

/**
 * 路由1(/progress/reputation/):打开Expert/evaluate_reputation.jsp的学科声誉评价页面， 初始化expertId,
 * discId, questionId等
 * 
 * 路由2(/progress/achievement_getUnits/):学科成果评价页面有展现参评学校的jqGrid， 这个路由会为jqGrid加载参评的学校
 * 
 * 路由3(/progress/achievement_initGrid/{entityId}):通过配置信息，获得某个成果的表格展现形式，
 * 如点击论文tab，每一个学校的子jqGrid展示的是论文的表格
 * 
 * 路由4(/progress/achievement_collectionData/{entityId}/{unitId}/{discId}):点击某个
 * 学校的打分成果，传递给后台采集项id，学校id和学科id，会展示该参评学校的学科的采集成果， 从而作为专家打分的依据
 * 
 * 路由5:(/progress/achievement_saveSingleResult/):保存或更新单条打分结果
 * 
 * 路由6:(/progress/achievement_saveMultiResults/):保存或更新多条打分结果
 * 
 * 路由7:
 * 
 * 路由8:(/progress/reputation_briefsheet/):下载简况表
 */

@Controller
@RequestMapping("evaluation")
public class EvalRepuController {
	@Resource(name = "evalRepuService")
	private EvalRepuService evalRepuService;

	@Resource(name = "evalService")
	private EvalService evalService;

	@Resource(name = "exportService")
	private ExportService exportService;

	@Resource(name = "evalProgService")
	private EvalProgService evalProgService;

	@RequestMapping("/progress/repu/{batchId}/{expertId}")
	public String repuCheckInfo(HttpSession session, Model model,
			@PathVariable(value = "batchId") String batchId,
			@PathVariable(value = "expertId") String expertId) {
		session = UtilCommon.setCurrentBatchExpertInfoIntoSession(session,
				evalProgService, batchId, expertId);
		return repu(session, model);
	}

	@RequestMapping("/progress/repu/")
	public String repu(HttpSession session, Model model) {
		// 和@RequestMapping一致，注意加上/DSEP/evaluation/
		String currentRoute = UtilDeploymentDescriptor.descriptor
				+ "progress/repu/";

		CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
				.getAttribute("info");

		List<EvalQuestionVM> questions = null;
		if (null == session.getAttribute("repuQs")) {
			questions = evalService.getQs(info, QType.REPU.toInt(), null);
			session.setAttribute("repuQs", questions);
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

		return "Expert/eval_repu";
	}

	/**
	 * 路由2 承接reputation路由 在打开evaluation_reputation后
	 * 需要向专家显示有哪些学校参评,呈现学校列表,并显示原来打过的分数
	 */
	@RequestMapping(value = "/progress/repuGetResults/")
	@ResponseBody
	public String getUnits(HttpServletRequest request, HttpSession session) {
		// 把前台参数解码
		//questionId = GUIDConvertor.expertEvalGUIDDecode(questionId);
		/*UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String userId = user.getId();
		// 专家用户的的用户实体Id就是expertId
		String expertId = userId;*/

		CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
				.getAttribute("info");
		Expert expert = null;//info.getExpert();
		String sord = (String) request.getParameter("sord");// 排序顺序
		String sidx = (String) request.getParameter("sidx");// 排序字段
		int page = Integer.parseInt((String) request.getParameter("page")); // 当前页
		int pageSize = Integer.parseInt((String) request.getParameter("rows")); // 每页多少数据
		boolean order_flag = ("desc".equals(sord)) ? true : false;

		// 查询打分的学科、学校以及分数
		PageVM<EvalRepuVM> vmList = evalRepuService.getRepuResults(info);
		//.getResult(
		//expert.getId(), expert.g, discId, page, pageSize, order_flag, sidx);

		String unitsJSON = JsonConvertor.obj2JSON(vmList.getGridData());
		System.out.println(unitsJSON);
		return unitsJSON;
	}

	// 下载简况表
	@RequestMapping(value = "/progress/reputation_briefsheet/{unitId}/{discId}", method = RequestMethod.POST)
	@ResponseBody
	// 相关调用函数全部在com.dsep.service.export.briefsheet包中
	public String exportBreifSheet(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "unitId") String unitId,
			@PathVariable(value = "discId") String discId) throws Exception {
		// test
		unitId = "10006";
		discId = "0835";
		long start = 0, end = 0;
		System.out.print(start = System.currentTimeMillis());
		System.out.print("\n");
		String downLoadUrl = exportService.exportBriefSheet(unitId, discId,
				request.getSession().getServletContext().getRealPath("/"));
		System.out.print(end = System.currentTimeMillis());
		System.out.print("\nusing time: " + (end - start) / 1000);
		return downLoadUrl;
	}

	/**
	 * 路由6 保存或更新多条打分结果
	 */
	@RequestMapping(value = "/progress/repuSaveOrUpdateResults")
	@ResponseBody
	public Boolean repuSaveOrUpdateResults(
			@RequestBody List<UtilEvalResultFromJSP> results,
			HttpSession session) {
		@SuppressWarnings("unchecked")
		List<EvalQuestionVM> questions = (List<EvalQuestionVM>) session
				.getAttribute("repuQs");

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