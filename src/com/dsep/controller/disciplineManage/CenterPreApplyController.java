package com.dsep.controller.disciplineManage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.flow.PreFlowService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.TextConfiguration;
import com.dsep.vm.PageVM;
import com.dsep.vm.flow.PreEvalVM;


@Controller
@RequestMapping("CenterPreList")
public class CenterPreApplyController {
	@Resource(name="preFlowService")
	private PreFlowService preFlowService;
	@Resource(name="disciplineService")
	private DisciplineService disciplineService;
	@Resource(name="unitService")
	private UnitService unitService;
	@RequestMapping("gatherList")
	public String CenterPreApplyView(Model model)
	{
		model.addAttribute("unitMap", unitService.getAllUnitMaps());
		model.addAttribute("discMap",disciplineService.getAllDiscMap());
		return "/DisciplinesManage/center_pre_apply";
	}
	@RequestMapping("/gatherList_gatherData")
	@ResponseBody
	public String gatherData(HttpServletRequest request)
	{
		String sord = request.getParameter("sord");//排序方式
		String sidx = request.getParameter("sidx");//排序字段
		int pageIndex = Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); //每一页的数据条数
		boolean asc = false;
		if ("asc".equals(sord)) {
			asc = true;
		}
		String unitId=request.getParameter("unitId");
		String discId=request.getParameter("discId");
		String isReport = request.getParameter("isReport");
		String isEval = request.getParameter("isEval");
		String isUnitReport = request.getParameter("isUnitReport");
		PageVM<PreEvalVM> pageVM=preFlowService.getCollectPreByPage(unitId, discId,isReport,isEval,isUnitReport,pageIndex, pageSize,asc, sidx);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	@RequestMapping("gatherList_gatherData/discChange")
	@ResponseBody
	public String discChange(HttpServletRequest request){
		String discId = request.getParameter("discId");
		return JsonConvertor.obj2JSON(unitService.getJoinUnitMapByDiscId(discId));
	}
	@RequestMapping("gatherList_gatherData/unitChange")
	@ResponseBody
	public String unitChange(HttpServletRequest request){
		String unitId = request.getParameter("unitId");
		return JsonConvertor.obj2JSON(disciplineService.getDisciplinesNamesByUnitId(unitId));
	}
}
