package com.dsep.controller.disciplineManage;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.exception.BusinessException;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.PreEval;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.file.ExportService;
import com.dsep.service.flow.EvalFlowService;
import com.dsep.service.flow.PreFlowService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.TextConfiguration;
import com.dsep.util.UserSession;
import com.dsep.vm.PageVM;
import com.dsep.vm.flow.PreEvalVM;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;


@Controller
@RequestMapping("SchoolPreApply")
public class SchoolPreApplyCollection {
	
	@Resource(name="preFlowService")
	private PreFlowService preFlowService;
	@Resource(name="evalFlowService")
	private EvalFlowService evalFlowService;
	@Resource(name="disciplineService")
	private DisciplineService disciplineService;
	@Resource(name="exportService")
	private ExportService exportService;
	
	@RequestMapping("PreGatherList")
	public String SchoolPreApplyCollectionView(Model model,HttpSession session)
	{
		UserSession userSession = new UserSession(session);
		User user= userSession.getCurrentUser();
		if("2".equals(user.getUserType()))
		{
			model.addAttribute("unitId",user.getUnitId());
			model.addAttribute("state", preFlowService.getPreState(user.getUnitId()));
			model.addAttribute("isExist",preFlowService.isHaveUnit(user.getUnitId()));
			model.addAttribute("isUnitReport", preFlowService.isReport(user.getUnitId()));
			return "/DisciplinesManage/school_pre_apply";
		}else {
			throw new BusinessException("用户身份不合法！");
		}
		
	}
	@RequestMapping(value = "PreGatherList_export/{unitId}",method=RequestMethod.POST)
	// 导出学科预参评信息
	@ResponseBody
	public String exportPreExcel(@PathVariable(value="unitId")String unitId,
			                     HttpServletRequest request,
			                     HttpServletResponse response)
	{
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String fileString = exportService.exportPreExcel(unitId,rootPath);
		return fileString;
	}
	
	@RequestMapping("PreGatherList_PreGatherData")
	@ResponseBody
	public String schoolCollectionData(HttpServletRequest request,HttpSession session)
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
		String isUnitReport= request.getParameter("isUnitReport");
		PageVM<PreEvalVM> pageVM=preFlowService.getCollectPreByPage(unitId, discId,isReport,isEval,isUnitReport,pageIndex, pageSize,asc, sidx);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	@RequestMapping("PreGatherList_PreGatherEdit")
	@ResponseBody
	public String unitPreGatherEdit(PreEvalVM preEvalVM,HttpServletRequest request,HttpSession session) throws NoSuchFieldException, SecurityException
	{
		String oper=request.getParameter("oper");
		UserSession userSession= new UserSession(session);
		User user = userSession.getCurrentUser();
		if("edit".equals(oper)){
			PreEval preEval = preEvalVM.getPreEval();
			if(preEval.getId()==null||"".equals(preEval.getId()))
			{
				preFlowService.savePreEval(preEval, user.getId());
			}else {
				preFlowService.updatePreEval(preEval, user.getId());
			}
			
		}
		return "success";
	}
	
	@RequestMapping("PreGatherList_importBaseDisc")
	@ResponseBody
	public String importBaseDisc(HttpServletRequest request,HttpSession session)
	{
		String unitId = request.getParameter("unitId");
		UserSession userSession = new UserSession(session);
		User user= userSession.getCurrentUser();
		if(preFlowService.importDiscsFromBase(unitId,user.getId()))
		{
			return "success";
		}else{
			return "error";
		}
			
	}
	@RequestMapping("PreGatherList_unitPre2Center")
	@ResponseBody
	public String unitPre2Center(HttpServletRequest request,HttpSession session)
	{
		String unitId = request.getParameter("unitId");
		if(StringUtils.isNotBlank(unitId))
		{
			String isUnitReport= request.getParameter("isUnitReport");
			UserSession userSession= new UserSession(session);
			User user = userSession.getCurrentUser();
			if("0".equals(isUnitReport)||"1".equals(isUnitReport))
			preFlowService.unit2Center(unitId, isUnitReport,user.getId());
			return "success";
		}else {
			return "error";
		}
		
	}
	@RequestMapping("PreGatherList_import2Eval")
	@ResponseBody
	public String import2Eval(HttpServletRequest request,HttpSession session)
	{
		String unitId = request.getParameter("unitId");
		UserSession userSession= new UserSession(session);
		User user = userSession.getCurrentUser();
		if(evalFlowService.importDiscsFromPre(unitId, user.getId()))
			return "success";
		else{
			return "error";
		}
		
	}
	
}
