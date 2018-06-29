package com.dsep.controller.expert.rule;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.dsep.entity.expert.EvalBatch;
import com.dsep.entity.expert.ExpertSelectionRule;
import com.dsep.entity.expert.ExpertSelectionRuleDetail;
import com.dsep.service.expert.batch.EvalBatchService;
import com.dsep.service.expert.rule.RuleService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.expert.rule.GetRuleAndRuleDetailUtil;
import com.dsep.vm.PageVM;
import com.dsep.vm.expert.ExpertSelectionRuleVM;

@Controller
@RequestMapping("rule")
public class RuleController {
	@Resource(name = "ruleService")
	private RuleService ruleService;

	@Resource(name = "evalBatchService")
	private EvalBatchService evalBatchService;

	// 以下4个方法是对应遴选规则的增删改查
	// 增删改查的路由分别对应(以makeRule为前缀的，为了权限拦截的需要)：
	// 增->makeRuleAddRule
	// 改->makeRuleModifyRule
	// 删->makeRuleDeleteRule
	// 查->makeRuleGetRuleList
	@RequestMapping("makeRuleAddRule")
	@ResponseBody
	public Boolean saveRule(@RequestBody RuleInfoFromJSP info,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		ExpertSelectionRule rule = GetRuleAndRuleDetailUtil.packageRule(
				request, null, info.getRuleName(), info.getRuleComment());

		int detailCounter = Integer.valueOf(request
				.getParameter("detailCounter"));

		String evalBatchId = request.getParameter("currentBatchId");
		System.out.println(evalBatchId);

		List<ExpertSelectionRuleDetail> details = new ArrayList<ExpertSelectionRuleDetail>();
		for (int i = 1; i <= detailCounter; i++) {
			ExpertSelectionRuleDetail detail = GetRuleAndRuleDetailUtil
					.packageRuleDetail(request, i);
			details.add(detail);
		}
		try {
			ruleService.saveRuleAndDetails(rule, details, evalBatchId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@RequestMapping("makeRuleDeleteRule")
	@ResponseBody
	public boolean deleteRule(String id) {
		return ruleService.deleteRuleById(id);
	}

	@RequestMapping("makeRuleModifyRule")
	@ResponseBody
	public Boolean updateRule(@RequestBody RuleInfoFromJSP info,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		String currentBatchId = request.getParameter("currentBatchId");
		String ruleId = request.getParameter("rule.id");
		List<EvalBatch> batches = (List<EvalBatch>) session
				.getAttribute("batches");

		ExpertSelectionRule r = ruleService.getRuleById(ruleId);
		/*System.out.println("-----------------------------------------");
		EvalBatch batch = evalBatchService.getEvalBatchById(evalBatchId);
		System.out.println("-----------------------------------------");
		EvalBatch evalBatch = evalBatchService.getEvalBatchById("ECB64BDC9B284F16852D024CA4EBD19B");
		EvalBatch evalBatch2 = evalBatchService.getEvalBatchById("ECB64BDC9B284F16852D024CA4EBD19C");*/
		Timestamp createDate = r.getCreateDate();
		ExpertSelectionRule rule = GetRuleAndRuleDetailUtil.packageRule(
				request, createDate, info.getRuleName(), info.getRuleComment());

		int detailCounter = Integer.valueOf(request
				.getParameter("detailCounter"));

		List<ExpertSelectionRuleDetail> details = new ArrayList<ExpertSelectionRuleDetail>();
		for (int i = 1; i <= detailCounter; i++) {
			ExpertSelectionRuleDetail detail = GetRuleAndRuleDetailUtil
					.packageRuleDetail(request, i);
			details.add(detail);
		}
		for (EvalBatch e : batches) {
			if (e.getId().equals(currentBatchId))
				rule.setEvalBatch(e);
		}
		try {
			ruleService.updateRuleAndDetails(ruleId, rule, details);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 显示所有的规则
	@RequestMapping("makeRuleGetRuleList/{currentBatchId}")
	public void expertSelectionRuleData(
			@PathVariable(value = "currentBatchId") String currentBatchId,
			HttpServletRequest request, HttpServletResponse response) {
		String sord = request.getParameter("sord");// 排序顺序
		// String sidx = request.getParameter("sidx");// 排序字段
		String sidx = "id";

		//String searchString = request.getParameter("searchString");// 查询条件值
		//String filter = request.getParameter("filters");
		int page = Integer.parseInt(request.getParameter("page")); // 当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); // 每页多少数据
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}

		PageVM<ExpertSelectionRuleVM> results = ruleService
				.getExpertSelectionRules(page, pageSize, order_flag, sidx,
						currentBatchId);
		Map<String, Object> obj = results.getGridData();
		String listRules = JsonConvertor.obj2JSON(obj);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(listRules);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("makeRuleGetRuleInfo/{currentBatchId}")
	public void getRuleInfo(
			@PathVariable(value = "currentBatchId") String currentBatchId,
			HttpServletRequest request, HttpServletResponse response) {
		List<ExpertSelectionRule> rules = ruleService.getRules(currentBatchId);
		List<String> ruleIdAndName = new ArrayList<String>();
		for (ExpertSelectionRule e : rules) {
			String idAndName = e.getId() + e.getRuleName();
			ruleIdAndName.add(idAndName);
		}
		String ruleIdAndNameJson = JsonConvertor.obj2JSON(ruleIdAndName);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(ruleIdAndNameJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*@RequestMapping("makeBatch")
	public String makeBatch(){
		return "Expert/rule/expert_makeBatch";
	}*/

	@RequestMapping("makeRuleGetBatchData")
	@ResponseBody
	public void getBatchData(HttpServletRequest request,
			HttpServletResponse response) {
		String sord = request.getParameter("sord");// 排序顺序
		// String sidx = request.getParameter("sidx");// 排序字段
		String sidx = "id";

		//String searchString = request.getParameter("searchString");// 查询条件值
		//String filter = request.getParameter("filters");
		int page = Integer.parseInt(request.getParameter("page")); // 当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); // 每页多少数据
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}

		PageVM<EvalBatch> results = evalBatchService.getBatchesByPageForCenter(
				page, pageSize, order_flag, sidx);
		Map<String, Object> obj = results.getGridData();
		String listRules = JsonConvertor.obj2JSON(obj);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(listRules);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("makeRule")
	public String makeRule(Model model) {
		return "Expert/rule/expert_makeRule";
	}

	@RequestMapping("makeBatch")
	public String makeBatch() {
		return "Expert/rule/expert_makeBatch";
	}

	@RequestMapping("makeRuleAddBatchDialog")
	public String addBatchDialog() {
		return "Expert/rule/dialog_addBatch";
	}

	@RequestMapping("makeRuleAddBatch")
	@ResponseBody
	public String addBatch(@RequestBody BatchDataFromJSP data) {
		evalBatchService.createBatchAndPapers(data.getBatchName(),
				data.getIndustrialItems(), data.getAcademicItems(),
				data.getBeginDate(), data.getEndDate());
		String str = "success";
		String str0 = JsonConvertor.obj2JSON(str);
		return str0;
	}

	@RequestMapping("makeRuleEditBatch")
	public String editBatch(String id, Model model) {
		EvalBatch batch = evalBatchService.getEvalBatchById(id);
		model.addAttribute("batch", batch);
		return "Expert/rule/dialog_editBatch";
	}

	@RequestMapping("makeRuleSaveEditBatch")
	@ResponseBody
	public String saveEditBatch(String id, @RequestBody BatchDataFromJSP items) {
		evalBatchService.saveOrUpdateEvalBatch(id, items.getNum(),
				items.getBatchName(), items.getIndustrialItems(),
				items.getAcademicItems());
		String str = "success";
		String str0 = JsonConvertor.obj2JSON(str);
		return str0;
	}

	@RequestMapping("makeRuleDelBatch")
	@ResponseBody
	public Boolean delBatch(String id) {
		evalBatchService.delEvalBatch(id);
		return true;
	}

	@RequestMapping("makeRuleGetAddRuleDialog")
	public String edituser(Model model) {
		return "Expert/rule/dialog_addRule";
	}

	@RequestMapping("makeRuleGetModifyRuleDialog")
	public String editRule(String id, Model model) {
		ExpertSelectionRule rule = ruleService.getRuleById(id);
		/*List<ExpertSelectionRuleDetail> details = (List<ExpertSelectionRuleDetail>) ruleService.getDetailsByRuleId(id);*/
		List<ExpertSelectionRuleDetail> details = ruleService
				.getDetailsByRuleId(id);

		model.addAttribute("rule", rule);

		for (int i = 1; i <= details.size(); i++) {

			// 因为get是从0开始取得，而i是从1开始的，所以get里面的参数是i-1
			model.addAttribute("detail" + i,
					getSpecificSequRuleDetail(i, details));
		}
		return "Expert/rule/dialog_editRule";
	}

	@RequestMapping("makeRuleGetViewRuleDialog")
	public String viewRule(String id, Model model) {
		ExpertSelectionRule rule = ruleService.getRuleById(id);
		/*List<ExpertSelectionRuleDetail> details = (List<ExpertSelectionRuleDetail>) ruleService.getDetailsByRuleId(id);*/
		List<ExpertSelectionRuleDetail> details = ruleService
				.getDetailsByRuleId(id);

		model.addAttribute("rule", rule);

		for (int i = 1; i <= details.size(); i++) {

			// 因为get是从0开始取得，而i是从1开始的，所以get里面的参数是i-1
			model.addAttribute("detail" + i,
					getSpecificSequRuleDetail(i, details));
		}
		return "Expert/rule/dialog_viewRule";
	}

	@RequestMapping("makeRuleGetImportRuleDetailDialog")
	public String importRule(String id, Model model) {
		ExpertSelectionRule rule = ruleService.getRuleById(id);
		/*List<ExpertSelectionRuleDetail> details = (List<ExpertSelectionRuleDetail>) ruleService.getDetailsByRuleId(id);*/
		List<ExpertSelectionRuleDetail> details = ruleService
				.getDetailsByRuleId(id);

		model.addAttribute("rule", rule);

		for (int i = 1; i <= details.size(); i++) {

			// 因为get是从0开始取得，而i是从1开始的，所以get里面的参数是i-1
			model.addAttribute("detail" + i,
					getSpecificSequRuleDetail(i, details));
		}
		return "Expert/rule/dialog_importRuleDetail";
	}

	@RequestMapping("makeRuleGetImportRuleDialog")
	public String getImportRuleDialog() {
		return "Expert/rule/dialog_importRule";
	}

	private ExpertSelectionRuleDetail getSpecificSequRuleDetail(int sequ,
			List<ExpertSelectionRuleDetail> details) {
		for (ExpertSelectionRuleDetail detail : details) {
			if (detail.getSequ().equals(String.valueOf(sequ))) {
				return detail;
			}
		}
		return null;
	}

}

class BatchDataFromJSP {
	private String id;

	private String num;

	private String batchName;
	private String beginDate;
	private String endDate;
	private List<String> industrialItems;
	private List<String> academicItems;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<String> getIndustrialItems() {
		return industrialItems;
	}

	public void setIndustrialItems(List<String> industrialItems) {
		this.industrialItems = industrialItems;
	}

	public List<String> getAcademicItems() {
		return academicItems;
	}

	public void setAcademicItems(List<String> academicItems) {
		this.academicItems = academicItems;
	}
}

class RuleInfoFromJSP {
	private String ruleName;
	private String ruleComment;

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleComment() {
		return ruleComment;
	}

	public void setRuleComment(String ruleComment) {
		this.ruleComment = ruleComment;
	}

}
