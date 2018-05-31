package com.dsep.controller.expert.select;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.service.expert.batch.EvalBatchService;
import com.dsep.service.expert.rule.RuleService;
import com.dsep.service.expert.select.ExpertCRUDService;
import com.dsep.service.expert.select.ExpertUtilService;
import com.dsep.service.expert.select.SelectReConstructService;
import com.dsep.service.expert.select.SelectService;
import com.dsep.util.JsonConvertor;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.ExpertSelectedVM;

@Controller
@RequestMapping("expert")
public class ExpertSelectController {
	// 该Service用于处理遴选规则
	@Resource(name = "ruleService")
	private RuleService ruleService;

	// 该Service用于通过规则选择专家
	@Resource(name = "selectService")
	private SelectService selectService;

	@Resource(name = "selectReConstructService")
	private SelectReConstructService selectReConstructService;

	@Resource(name = "expertCRUDService")
	private ExpertCRUDService expertCRUDService;

	@Resource(name = "expertUtilService")
	private ExpertUtilService expertUtilService;

	// 该Service用于处理遴选批次
	@Resource(name = "evalBatchService")
	private EvalBatchService evalBatchService;

	@RequestMapping("selectExpert")
	public String selectExpert() {
		return "Expert/expert_select";
	}

	// 显示所有的已选专家
	@RequestMapping("selectExpertData/{currentBatchId}")
	public void expertSelectedData(
			@PathVariable(value = "currentBatchId") String currentBatchId,
			HttpServletRequest request, HttpServletResponse response) {

		String sord = request.getParameter("sord");// 排序顺序
		String sidx = request.getParameter("sidx");// 排序字段
		int page = Integer.parseInt(request.getParameter("page")); // 当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); // 每页多少数据
		boolean order_flag = ("desc".equals(sord)) ? true : false;

		PageVM<ExpertSelectedVM> data = null;
		try {
			data = expertCRUDService.getExperts(currentBatchId, page, pageSize,
					order_flag, sidx);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		String listExperts = JsonConvertor.obj2JSON(data.getGridData());
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(listExperts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 点击界面"开始遴选"按钮，就会ajax执行该方法
	// selectExpertService内部会获得所有学校、学科的信息，规则明细，外部专家信息
	@RequestMapping("selectExpertSelectByRule")
	@ResponseBody
	public String selectByRule(String ruleId) {
		try {
			// 第二个参数是batchId，正常遴选是传递null
			// 第三个参数传递false表明不是补选
			selectService.select(ruleId, null, false);
			//selectReConstructService.select(ruleId);
			return "success";
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return "failure";
		}
	}

	// 点击界面"开始遴选"按钮，就会ajax执行该方法
	// selectExpertService内部会获得所有学校、学科的信息，规则明细，外部专家信息
	@RequestMapping("selectExpertReSelectByRule")
	@ResponseBody
	public String reSelectByRule(String currentBatchId) {
		try {
			// 第一个参数传递null是因为ruleId会在Service中获得
			// 第二个参数将会用于获得最近一次使用的rule
			// 第三个参数传递true表明是补选
			selectService.select(null, currentBatchId, true);
			//selectReConstructService.select(ruleId);
			return "success";
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return "failure";
		}
	}

	// 点击“替换专家”，添加专家弹出框
	// 仅仅是弹框，不涉及选择专家的逻辑，具体的批量选择在BatchAddExpertController里面
	@RequestMapping("selectExpertAddExpert")
	public String addExpert() {
		return "Expert/dialog_addExpert";
	}

	// 替换专家弹出框
	@RequestMapping("selectExpertReplaceExpert")
	public String replaceExpert(String needReplaceExpertId, Model model) {
		System.out.println(needReplaceExpertId);
		ExpertSelectedVM expertSelectedVM = this.expertCRUDService
				.getExpert(needReplaceExpertId);
		model.addAttribute("expertSelectedVM", expertSelectedVM);
		return "Expert/dialog_replaceExpert";
	}

	// 在遴选专家界面，要通过下拉框展示规则，从而遴选专家
	// 此处就是要给下拉框填充规则的ID和名称
	// 把规则和ID拼接起来，到前端再拆分一下
	@RequestMapping("selectExpertSelectEveryRuleId/{currentBatchId}")
	public void selectEveryRuleId(HttpServletResponse response,
			@PathVariable(value = "currentBatchId") String currentBatchId) {
		List<ExpertSelectionRule> rules = ruleService.getRules(currentBatchId);
		List<String> rulesIdAndName = new LinkedList<String>();

		for (ExpertSelectionRule r : rules) {
			// 拼接规则ID和名称
			// idAndName可能式样如下是
			// 12345678123456781234567812345678遴选规则名称XXX
			String idAndName = r.getId() + r.getRuleName();
			rulesIdAndName.add(idAndName);
		}
		String rulesIdAndNameJSON = JsonConvertor.obj2JSON(rulesIdAndName);
		System.out.println(rulesIdAndNameJSON);
		System.out.println("test");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(rulesIdAndNameJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 在遴选专家界面，要通过下拉框展示规则，从而遴选专家
	// 此处就是要给下拉框填充规则的ID和名称
	// 把规则和ID拼接起来，到前端再拆分一下
	@RequestMapping("selectExpertSelectEveryBatchId")
	public void selectEveryBatchId(HttpServletResponse response,
			HttpSession session) {
		List<EvalBatch> batches = evalBatchService.getAll();
		session.setAttribute("batches", batches);
		List<String> batchIdAndName = new LinkedList<String>();

		for (EvalBatch b : batches) {
			// 拼接规则ID和名称
			// idAndName可能式样如下是
			// 12345678123456781234567812345678遴选规则名称XXX
			String idAndName = b.getId() + b.getBatchChName();
			batchIdAndName.add(idAndName);
		}
		String batchIdAndNameJSON = JsonConvertor.obj2JSON(batchIdAndName);
		System.out.println(batchIdAndNameJSON);
		System.out.println("hehererhehehew");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(batchIdAndNameJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("selectExpertGetQueryUnitsAndDiscs")
	@ResponseBody
	public String getQueryUnitsAndDiscs(HttpServletResponse response) {
		List<String> unitIds = null;
		/*
		 * try { //unitIds = expertUtilService.getAttendExpertsUnits(); } catch
		 * (InstantiationException e) { e.printStackTrace(); } catch
		 * (IllegalAccessException e) { e.printStackTrace(); }
		 */
		Map<String, String> unitIdsAndNames = new HashMap<String, String>();
		for (String unitId : unitIds) {
			unitIdsAndNames.put(unitId, unitId);
		}
		String selectValues = JsonConvertor.obj2JSON(unitIdsAndNames);
		System.out.println(selectValues);
		return selectValues;
	}

	@RequestMapping("selectExpertGetReviewPage")
	public String getReviewPage() {
		return "Expert/selectionReview";
	}

	@RequestMapping("selectExpertReview/{currentBatchId}")
	@ResponseBody
	public String review(
			@PathVariable(value = "currentBatchId") String currentBatchId) {
		ExpertSelectionRule rule = ruleService
				.getLastUsedRuleInABatch(currentBatchId);
		String json = expertUtilService.showExpertStatistics(rule,
				currentBatchId);
		return json;
	}

	@RequestMapping("selectExpertAddExperts")
	public String addExperts(String experts) {
		System.out.println(experts);
		return null;
	}

	// 查看专家拒绝原因弹出框
	@RequestMapping("selectExpertRemark")
	public String remark() {
		return "Expert/dialog_remark";
	}

	// 查看专家评价进度弹出框
	@RequestMapping("progress")
	public String progress() {
		return "Expert/dialog_progress";
	}

	@RequestMapping("manageExpert")
	public String manageExpert(Model model) {
		return "Expert/expert_manage";
	}

	// 专家筛选条件
	@RequestMapping("filterRule")
	public String filterRule(Model model) {
		return "Expert/dialog_filterRule";
	}

	// 根据查询条件来筛选专家
	@RequestMapping("filterExpert")
	public String filterExpert(Model model) {
		return "Expert/expert_filter";
	}

}
