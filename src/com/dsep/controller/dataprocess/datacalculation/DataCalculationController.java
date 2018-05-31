package com.dsep.controller.dataprocess.datacalculation;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.DataCalculateConfig;
import com.dsep.service.datacalculate.DataCalculateService;
import com.dsep.service.datacalculate.FactorAndWeight.IndexFactorService;
import com.dsep.service.datacalculate.FactorAndWeight.IndexWeightService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;

import com.dsep.util.datacalculate.DiscLastIndexValueVM;
import com.dsep.vm.PageVM;

@Controller
@RequestMapping("calculate")
public class DataCalculationController {

	
	@Resource(name = "dataCalculateService")
	private DataCalculateService dataCalculateService;
	
	@Resource(name = "indexFactorService")
	private IndexFactorService indexFactorService;
	
	@Resource(name = "indexWeightService")
	private IndexWeightService indexWeightService;
	
	@RequestMapping("cal")
	public String resultsCalculation(){

		return "/DataCalculation/data_calculation";
	}
	
	/**
	 * 获取计算配置信息表
	 */
	@RequestMapping("calConfigList")
	@ResponseBody 
	public String dataCalConfigList(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = 1;//1
		int pageSize = 99999;// 1 和 无限大，表示不分页 
		
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		PageVM<DataCalculateConfig> configs = dataCalculateService.showDataCalculateConfig(user,sidx, order_flag, page, pageSize);
		String result = JsonConvertor.obj2JSON(configs.getGridData());
		return result;
	}
	
	@RequestMapping("calShowResultsDialog")
	public String showResultsDialog(String discId,Model model){
		model.addAttribute("discId", discId);
		return "/DataCalculation/showResults";
	}
	
	@RequestMapping("calResultList")
	@ResponseBody
	public String showResults(HttpServletRequest request,HttpServletResponse response,
			String discId){
		PageVM<DiscLastIndexValueVM> vm = dataCalculateService.showDataCalculateResults(discId);
		Map m = vm.getGridData();
		return JsonConvertor.obj2JSON(m);
	}
	
	/**
	 * 开始计算
	 */
	@RequestMapping("calStartCalculate")
	@ResponseBody
	public String calStartCalculate(@RequestParam(value = "discIds") List<String> discIds , HttpSession session) {
		
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String result ="";
		try{
			int t1= (int) System.currentTimeMillis();
			dataCalculateService.dataCalculate(user, discIds);
			int t2= (int) System.currentTimeMillis();
			int t3=t2-t1;
			System.out.println("运行时间："+t3);
		}catch(Exception e){
			System.out.println(e.getMessage());
			result += e.getMessage();
		}
		
		return result;
//		return JsonConvertor.obj2JSON(result);
	}
	
	/**
	 * 
	 * @param limit 阈值
	 * @param discList 要计算的学科
	 * @return
	 */
	@RequestMapping("calCluste")
	@ResponseBody
	public String cluster(@RequestParam(value="discIds") List<String> discList,@RequestParam(value="limit") String limitStr){
		String result="";
		double limit=Double.parseDouble(limitStr);
		try{
			dataCalculateService.dataCluster(discList, limit);
		}catch(Exception e){
			System.out.println(e.getMessage());
			result += e.getMessage();
		}
		return result;
	}
	/**
	 * excel导出折算系数
	 */
	@RequestMapping(value="calExportIndex/{discId}",method=RequestMethod.POST)
	@ResponseBody
	public String calExportIndex(@PathVariable(value="discId")String discId,
			HttpServletRequest request) {
	
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String file;
		try {
			file = indexFactorService.exportFactorExcel(discId, rootPath);
			return file;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonConvertor.obj2JSON(e.getMessage());
		}
		
	}
	
	/**
	 * excel导出指标权重
	 */
	@RequestMapping(value="calExportWeight/{discId}",method=RequestMethod.POST)
	@ResponseBody
	public String calExportWeight(@PathVariable(value="discId")String discId,
			HttpServletRequest request) {
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String file;
		try {
			file = indexWeightService.exportWeightExcel(discId, rootPath);
			return file;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonConvertor.obj2JSON(e.getMessage());
		}
	}
	
	/**
	 * 计算指标权重和折算系数
	 * @param request
	 * @return
	 */
	@RequestMapping("calWeightFactor")
	@ResponseBody
	public String calWeightFactor(@RequestParam(value = "calType") String calType,
			@RequestBody List<String> discIds) {
		String result = JsonConvertor.obj2JSON("计算完成，可以选择导出查看计算结果");; 
		for(String discId:discIds){
			try {
				indexFactorService.calculateAwardWtByDisc(discId);
				indexFactorService.calculateIndicWtByDisc(discId); 
				indexWeightService.calculateAllIndexByDisc(discId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = JsonConvertor.obj2JSON(e.getMessage());
			}
		}
		return result;
	}
	
}
