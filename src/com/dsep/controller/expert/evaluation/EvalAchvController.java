package com.dsep.controller.expert.evaluation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.domain.dsepmeta.expert.CurrentBatchExpertInfo;
import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.entity.expert.EvalResult;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.dsepmeta.dsepmetas.DMViewConfigService;
import com.dsep.service.expert.evaluation.EvalAchvService;
import com.dsep.service.expert.evaluation.EvalIndicIdxService;
import com.dsep.service.expert.evaluation.EvalProgService;
import com.dsep.service.expert.evaluation.EvalService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.expert.eval.GUIDConvertor;
import com.dsep.util.expert.eval.QType;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.EvalAchvVM;
import com.dsep.vm.expert.EvalQuestionVM;

/**
 * 学科成果打分
 * 
 * 路由1(/progress/achv/{subQuestionId}/{batchId}/{expertId}):
 * 		专家的任务列表中设定的路由，通过该路由，首先在achvCheckInfo方法中进行验证，
 * 		验证当前专家和任务批次是否发生了改变，改变则重置session信息，然后转交给achv方法执行
 * 
 * 路由2(/progress/achv/{subQuestionId}):
 * 		1.由专家的任务列表路由转交调用
 * 		2.通过点击“上一项打分”调用
 * 		2.通过点击“下一项打分”调用
 * 		通过subQuestionId找到某个成果评价的题目，并以achvQs+32位subQuestionId存入session，
 * 		此外，该路由对应的方法还set了一些路由，打分项ID等到model以传到前台，
 * 		最后返回Expert/eval_achv.jsp
 * 
 * 路由3(/progress/achvGetResults/{subQuestionId}):
 *		通过传来的subQuestionId展示该成果打分的题目和结果
 * 
 * 路由4:(/progress/achvSaveOrUpdateResults/{subQuestionId}):
 * 		保存或更新前台结果
 * 
 * 路由5:(/progress/achvShowUnitAchv/{discId}/{unitId}/{collectId}):
 * 		点击某个学校的打分成果，传递给后台采集项id，学校id和学科id，
 * 		会展示该参评学校的学科的采集成果，从而作为专家打分的依据
 * 
 * 路由6:(/progress/achvInitGrid/{entityId}):
 * 		根据不同的打分项信息(entityId)初始化显示表格
 * 
 * 路由7:(/progress/achvCollectionData/{entityId}/{unitId}/{discId}):
 * 		获得表格中的采集项内容
 */
@Controller
@RequestMapping("evaluation")
public class EvalAchvController {

	@RequestMapping("/progress/achv/{subQuestionId}/{batchId}/{expertId}")
	public String achvCheckInfo(HttpSession session, Model model,
			@PathVariable(value = "subQuestionId") String subQuestionId,
			@PathVariable(value = "batchId") String batchId,
			@PathVariable(value = "expertId") String expertId) {
		session = UtilCommon.setCurrentBatchExpertInfoIntoSession(session,
				evalProgService, batchId, expertId);
		return achv(session, model, subQuestionId);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/progress/achv/{subQuestionId}")
	public String achv(HttpSession session, Model model,
			@PathVariable(value = "subQuestionId") String subQuestionId) {
		// 和@RequestMapping一致，注意加上/DSEP/evaluation/
		String currentRoute = UtilDeploymentDescriptor.descriptor
				+ "progress/achv/" + subQuestionId + "/";

		CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
				.getAttribute("info");

		List<EvalQuestionVM> questions = null;
		String qNameInSession = "achvQs" + subQuestionId;
		if (null == session.getAttribute(qNameInSession)) {
			questions = evalService.getQs(info, QType.ACHV.toInt(),
					subQuestionId);
			session.setAttribute(qNameInSession, questions);
		} else {
			questions = (List<EvalQuestionVM>) session
					.getAttribute(qNameInSession);
		}

		EvalQuestionVM currentQ = null;

		// 都是同一类成果打分的题目
		if (questions.size() > 0) {
			currentQ = questions.get(0);
		}

		String convertedSubQuestionId = GUIDConvertor
				.expertEvalGUIDEncode(currentQ.getSubQuestionId());
		// 部署的时候要改一下！
		String homeRoute = UtilDeploymentDescriptor.homeRoute;

		List<String> routes = info.getRoutes();
		String[] prevAndNextRoutes = UtilCommon.getPrevAndNextRoutes(routes,
				currentRoute);
		String prevQuestionRoute = prevAndNextRoutes[0];
		String nextQuestionRoute = prevAndNextRoutes[1];

		model.addAttribute("convertedSubQuestionId", convertedSubQuestionId);
		model.addAttribute("collectId1", currentQ.getCollectId1());
		model.addAttribute("collectId2", currentQ.getCollectId2());
		model.addAttribute("collectId3", currentQ.getCollectId3());
		model.addAttribute("collectId1Name", currentQ.getCollectId1Name());
		model.addAttribute("collectId2Name", currentQ.getCollectId2Name());
		model.addAttribute("collectId3Name", currentQ.getCollectId3Name());
		model.addAttribute("collectName", currentQ.getEvalQuestionName());
		model.addAttribute("itemName", currentQ.getEvalQuestionName());
		model.addAttribute("prevQuestionRoute", prevQuestionRoute);
		model.addAttribute("nextQuestionRoute", nextQuestionRoute);
		model.addAttribute("homeRoute", homeRoute);

		return "Expert/eval_achv";
	}

	/**
	 * 路由2
	 * 承接achievement路由
	 * 在加载完成专家要打分的题目tabs，比如"专家","团队","论文"后
	 * 需要向专家显示有哪些学校参评,呈现学校列表,并显示原来打过的分数
	 * 
	 * 注意：需要些custom paging方法，否则ajax会出问题，要废弃jqgrid的paging方法，返回"stop"，自己模拟page！！
	 */
	@RequestMapping(value = "/progress/achvGetResults/{subQuestionId}")
	@ResponseBody
	public String achvGetResults(
			@PathVariable(value = "subQuestionId") String subQuestionId,
			HttpServletRequest request, HttpSession session) {
		CurrentBatchExpertInfo info = (CurrentBatchExpertInfo) session
				.getAttribute("info");
		// 把前台参数解码
		subQuestionId = GUIDConvertor.expertEvalGUIDDecode(subQuestionId);

		int page = Integer.parseInt((String) request.getParameter("page")); // 当前页
		int pageSize = Integer.parseInt((String) request.getParameter("rows")); // 每页多少数据

		// 查询打分的学科、学校以及分数
		PageVM<EvalAchvVM> vmList = evalAchvService.getAchvResults(info,
				subQuestionId, page, pageSize);

		return JsonConvertor.obj2JSON(vmList.getGridData());
	}

	@RequestMapping(value = "/progress/achvSaveOrUpdateResults/{subQuestionId}")
	@ResponseBody
	public Boolean achvSaveOrUpdateResults(HttpSession session,
			@PathVariable(value = "subQuestionId") String subQuestionId,
			@RequestBody List<UtilEvalResultFromJSP> results) {
		// 最开始验证一下前端数据的合法性
		//validateDataFromJSP(results);
		// 把前台参数解码
		subQuestionId = GUIDConvertor.expertEvalGUIDDecode(subQuestionId);

		@SuppressWarnings("unchecked")
		List<EvalQuestionVM> questions = (List<EvalQuestionVM>) session
				.getAttribute("achvQs" + subQuestionId);

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

	@RequestMapping(value = "/progress/achvShowUnitAchv/{discId}/{unitId}/{collectId}")
	public String showUnitAchievement(Model model,
			@PathVariable(value = "discId") String discId,
			@PathVariable(value = "unitId") String unitId,
			@PathVariable(value = "collectId") String collectId) {
		model.addAttribute("discId", discId);
		model.addAttribute("unitId", unitId);
		model.addAttribute("collectId", collectId);
		return "Expert/dialog_achv";
	}

	@RequestMapping("/progress/achvInitGrid/{entityId}")
	@ResponseBody
	public String achvInitGrid(@PathVariable(value = "entityId") String entityId) {
		ViewConfig viewConfig = viewConfigService.getViewConfig(entityId);
		String configData = JsonConvertor.obj2JSON(viewConfig);
		System.out.println(configData);
		return configData;
	}

	@RequestMapping("/progress/achvCollectionData/{entityId}/{unitId}/{discId}")
	@ResponseBody
	public String achvCollectionData(
			@PathVariable(value = "entityId") String entityId,
			@PathVariable(value = "unitId") String unitId,
			@PathVariable(value = "discId") String disciplineId,
			HttpServletRequest request) {
		//传过来列名
		String sord = request.getParameter("sord");//排序名称
		String sidx = request.getParameter("sidx");//排序方式
		String page = request.getParameter("page"); //当前页
		String rows = request.getParameter("rows");
		int pageIndex = 0;
		int pageSize = 0;
		if (page != null) {
			pageIndex = Integer.parseInt(page);
		}
		if (rows == null || "all".equals(rows)) {
			pageIndex = 0;
			pageSize = 0;
		} else {
			pageSize = Integer.parseInt(rows); // 每页多少数据
		}
		boolean order_flag = true;
		if ("desc".equals(sord)) {
			order_flag = false;
		}

		JqgridVM jqgridVM = collectService.getJqGridData(entityId, unitId,
				disciplineId, pageIndex, pageSize, sidx, order_flag);
		String result = JsonConvertor.obj2JSON(jqgridVM);
		System.out.println(result);
		return result;
	}

	// 与业务逻辑无关的Service==============================
	// 前台"专家"、"论文"、"团队"表单元配置Service
	@Resource(name = "dmViewConfigService")
	private DMViewConfigService viewConfigService;

	// 展示"团队"、"专家"、"论文"等数据的Service
	@Resource(name = "collectService")
	private DMCollectService collectService;

	@Resource(name = "evalService")
	private EvalService evalService;

	@Resource(name = "evalIndicIdxService")
	private EvalIndicIdxService evalIndicIdxService;

	@Resource(name = "evalProgService")
	private EvalProgService evalProgService;

	@Resource(name = "evalAchvService")
	private EvalAchvService evalAchvService;
}
