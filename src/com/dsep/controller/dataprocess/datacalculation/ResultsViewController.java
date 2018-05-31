package com.dsep.controller.dataprocess.datacalculation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.CalResult;
import com.dsep.entity.dsepmeta.DataCalculateConfig;
import com.dsep.entity.dsepmeta.IndexScore;
import com.dsep.service.datacalculate.DataCalculateService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.PageVM;

@Controller
@RequestMapping("calculateview")
public class ResultsViewController {
	
	@Resource(name = "dataCalculateService")
	private DataCalculateService dataCalculateService;
	
	@RequestMapping("calview")
	public String resultsView()
	{
		return "DataCalculation/result_view";
	}
	
	@RequestMapping("calviewdisclist")
	@ResponseBody
	public String showDiscConfig(HttpServletRequest request,
			HttpServletResponse response){
		
		String sord=request.getParameter("sord");
		String sidx=request.getParameter("sidx");
		int page=Integer.parseInt(request.getParameter("page"));
		int pageSize=Integer.parseInt(request.getParameter("rows"));
		
		boolean order_flag=false;
		if(sord.equals("desc")){
			order_flag=true;
		}
		
		PageVM<DataCalculateConfig> list=dataCalculateService.showResultConfig(sidx, order_flag, page, pageSize);
		String result=JsonConvertor.obj2JSON(list.getGridData());
		
		return result;
	}
	
	@RequestMapping("calviewresult")
	@ResponseBody
	public String showResult(@RequestParam(value="discId") String discId,HttpServletRequest request,
			HttpServletResponse response, HttpSession session){
		UserSession us=new UserSession(session);
		User user=us.getCurrentUser();
		
		String sord=request.getParameter("sord");
		String sidx=request.getParameter("sidx");
		int page=Integer.parseInt(request.getParameter("page"));
		int pageSize=Integer.parseInt(request.getParameter("rows"));
		
		boolean ord_flag=false;
		if(sord.equals("desc")){
			ord_flag=true;
		}
		
		PageVM<CalResult> calResultList=dataCalculateService.showResult(discId, sidx, ord_flag, page, pageSize);
		String result=JsonConvertor.obj2JSON(calResultList.getGridData());

		return result;
	}
	
	@RequestMapping("calviewunit")
	@ResponseBody
	public String showOneUnit(@RequestParam(value="unit") String unit,HttpServletRequest request,
			HttpServletResponse response, HttpSession session){
		UserSession us=new UserSession(session);
		User user=us.getCurrentUser();
		
		String sord=request.getParameter("sord");
		String sidx=request.getParameter("sidx");
		int page=Integer.parseInt(request.getParameter("page"));
		int pageSize=Integer.parseInt(request.getParameter("rows"));
		
		boolean ord_flag=false;
		if(sord.equals("desc")){
			ord_flag=true;
		}
		
		PageVM<CalResult> calResultList=dataCalculateService.showUnitResult(unit, sidx, ord_flag, page, pageSize);
		String result=JsonConvertor.obj2JSON(calResultList.getGridData());
		
		return result;
	}
	@RequestMapping("calviewdetial")
	@ResponseBody
	public String showDetial(@RequestParam(value="discId") String discId,@RequestParam(value="unitId") String unitId,HttpServletRequest request,
			HttpServletResponse response, HttpSession session){
		//获取所有的一级指标和二级指标
		UserSession us=new UserSession(session);
		User user=us.getCurrentUser();
		
		String sord=request.getParameter("sord");
		String sidx=request.getParameter("sidx");
		int page=Integer.parseInt(request.getParameter("page"));
		int pageSize=Integer.parseInt(request.getParameter("rows"));
		
		boolean ord_flag=false;
		if(sord.equals("desc")){
			ord_flag=true;
		}
		PageVM<IndexScore> list=dataCalculateService.showDetial(discId, unitId, sidx, ord_flag, page, pageSize);
		String result=JsonConvertor.obj2JSON(list.getGridData());
		return result;
	}
	@RequestMapping("calviewexport")
	@ResponseBody
	public String export(HttpServletRequest request){
		String fileString=null;
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		
		fileString=dataCalculateService.getExportData(rootPath);
		return fileString;
	}
}
