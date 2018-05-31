package com.dsep.controller.disciplineManage;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.briefManage.BriefManageService;
import com.dsep.service.file.ExportService;
import com.dsep.service.flow.EvalFlowService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.TextConfiguration;
import com.dsep.vm.PageVM;
import com.dsep.vm.flow.EvalVM;

@Controller
@RequestMapping("CenterDisciplineList")
public class CenterDisciplineListController {
	
	@Resource(name="evalFlowService")
	private EvalFlowService evalFlowService;
	@Resource(name="exportService")
	private ExportService exportService;
	@Resource(name="unitService")
	private UnitService unitService;
	@Resource(name="disciplineService")
	private DisciplineService disciplineService;
	@Resource(name="briefManageService")
	private BriefManageService briefManageService;
	
	@RequestMapping("gatherList")
	public String centerCollectionView(Model model)
	{   
		model.addAttribute("evalState", evalFlowService.getUnitState(null));
		return "/DisciplinesManage/center_disciplines_list";
	}
	@RequestMapping("gatherList_gatherData")
	@ResponseBody
	public String  centerCollectionData(HttpServletRequest request,HttpSession session)
	{   
		String unitId=request.getParameter("unitId");
		String discId=request.getParameter("discId");
		String state=request.getParameter("state");
		String is_Eval= request.getParameter("isEval");
		String is_Report = request.getParameter("isReport");
		if(state==null||"all".equals(state))
			state=String.valueOf(-1);
		Boolean isEval= null;
		if("1".equals(is_Eval)){
			isEval=true;
		}else if("0".equals(is_Eval)){
			isEval= false;
		}
		Boolean isReport = null;
		if("1".equals(is_Report)){
			isReport=true;
		}else if("0".equals(is_Report)){
			isReport=false;
		}
		String sord = request.getParameter("sord");//排序方式
		String sidx = request.getParameter("sidx");//排序字段
		int pageIndex = Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); //每一页的数据条数
		boolean asc = false;
		if ("asc".equals(sord)) {
			asc = true;
		}
		PageVM<EvalVM> pageVM=evalFlowService.getCollectEvalByPage(unitId, discId, state, isEval,isReport,pageIndex, pageSize, asc, sidx);
		return JsonConvertor.obj2JSON(pageVM.getGridData());
	}
	@RequestMapping("gatherList_download/{briefId}")
	@ResponseBody
	public String downLoad(@PathVariable(value="briefId")String briefId){
		if(briefId==null) return "{\"result\":\""+"failure"+"\",\"data\":\" \"}";
		else 
		{
			String downLoadPath=briefManageService.downLoadBrief(briefId);
			return "{\"result\":\""+"success"+"\",\"data\":\""+downLoadPath+"\"}";
		}	
	}
	
	
}
