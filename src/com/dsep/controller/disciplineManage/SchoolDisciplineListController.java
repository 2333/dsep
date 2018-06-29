package com.dsep.controller.disciplineManage;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.exception.BusinessException;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.Eval;
import com.dsep.entity.dsepmeta.PreEval;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.briefManage.BriefManageService;
import com.dsep.service.file.ExportService;
import com.dsep.service.flow.EvalFlowService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.TextConfiguration;
import com.dsep.util.UserSession;
import com.dsep.vm.PageVM;
import com.dsep.vm.flow.CollectFlowVM;
import com.dsep.vm.flow.EvalVM;
import com.dsep.vm.flow.PreEvalVM;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;


@Controller
@RequestMapping("SchoolDisciplineList")
public class SchoolDisciplineListController {
	
	@Resource(name="evalFlowService")
	private EvalFlowService evalFlowService;
	@Resource(name="exportService")
	private ExportService exportService;
	@Resource(name="disciplineService")
	private DisciplineService disciplineService;
	@Resource(name="briefManageService")
	private BriefManageService briefManageService;
	@RequestMapping("GatherList")
	public String schoolCollectionView(Model model,HttpSession session)
	{
		UserSession userSession= new UserSession(session);
		User user = userSession.getCurrentUser();
		if("2".equals(user.getUserType()))
		{
			model.addAttribute("unitId", user.getUnitId());
			model.addAttribute("evalState", evalFlowService.getUnitState(user.getUnitId()));
			String unitVersionNo = evalFlowService.getVersionNo(user.getUnitId(), null);
			if(unitVersionNo==null)
				unitVersionNo="未生成";
			model.addAttribute("unitVersionNo",unitVersionNo);
			return "/DisciplinesManage/school_disciplines_list";
		}else{
			throw new BusinessException("用户不合法！");
		}
		
	}
	@RequestMapping(value = "GatherList_export/{unitId}",method=RequestMethod.POST)
	@ResponseBody
	public String exportPreExcel(@PathVariable(value="unitId")String unitId,
			                     HttpServletRequest request,
			                     HttpServletResponse response)
	{
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String fileString = exportService.exportEvalExcel(unitId,rootPath);
		return fileString;
	}
	@RequestMapping("GatherList_GatherData")
	@ResponseBody
	public String schoolCollectionData(Model model,HttpServletRequest request,HttpSession session)
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
		String discId = request.getParameter("discId");
		String state= request.getParameter("state");
		String is_Eval = request.getParameter("isEval");
		String is_Report= request.getParameter("isReport");
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
		PageVM<EvalVM> collectFlowPage=evalFlowService.getCollectEvalByPage(unitId, discId, state,isEval,isReport, pageIndex, pageSize, asc, sidx);
		String result=JsonConvertor.obj2JSON(collectFlowPage.getGridData());
		return result;
	}
	
	@RequestMapping("GatherList_editData")
	@ResponseBody
	public String unitCollectEditData(EvalVM evalVM,HttpServletRequest request,HttpSession session) throws NoSuchFieldException, SecurityException
	{
		
		String oper=request.getParameter("oper");
		UserSession userSession= new UserSession(session);
		User user = userSession.getCurrentUser();
		if("edit".equals(oper)){
			Eval eval=evalVM.getEval();
			evalFlowService.updateEval(eval, user.getId());	
		}
		return "success";
		
	}
	@RequestMapping("GatherList_importFromPre")
	@ResponseBody
	public String importDiscsFromPre(HttpServletRequest request,HttpSession session)
	{
		UserSession userSession = new UserSession(session);
		User user= userSession.getCurrentUser();
		String unitId= request.getParameter("unitId");
		evalFlowService.importDiscsFromPre(unitId, user.getId());
		return "success";
	}
	@RequestMapping("GatherList_isEditableEval")
	@ResponseBody
	public String isEditableEval(HttpServletRequest request){
		String unitId = request.getParameter("unitId");
		if(evalFlowService.isEditableEval(unitId)){
			return "1";
		}else{
			return "0";
		}
	}
	@RequestMapping("GatherList_download/{briefId}")
	@ResponseBody
	public String downLoad(@PathVariable(value="briefId")String briefId,
			HttpServletRequest request){
		if(briefId==null) return "{\"result\":\""+"failure"+"\",\"data\":\" \"}";
		else 
		{
			String downLoadPath=briefManageService.downLoadBrief(briefId);
			return "{\"result\":\""+"success"+"\",\"data\":\""+downLoadPath+"\"}";
		}	
		
	}
	
}
