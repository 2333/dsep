package com.dsep.controller.dataprocess;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.NormConfig;
import com.dsep.entity.dsepmeta.NormResult;
import com.dsep.service.dataprocess.normalization.NormalizationService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.PageVM;

@Controller
@RequestMapping("dataNormalization")
public class DataNormalizationController {
	
	@Resource(name="normalizationService")
	private NormalizationService normalizationService;
	
	@RequestMapping("norm")
	public String dataNormalization(Model model,String tableId)
	{
		return "/ReportsManage/data_normalize";
	}

	/**
	 * 获得检测表的配置
	 */
	@RequestMapping("normConfig")
	@ResponseBody
	public String getNormConfig(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		UserSession us = new UserSession(session);
		User user=us.getCurrentUser();
		PageVM<NormConfig>  configPage=normalizationService.initNormaTable(user);
		String result = JsonConvertor.obj2JSON(configPage.getGridData());
		return result;
	}
	/**
	 * 
	 */
	@RequestMapping("uodateNormConfig")
	@ResponseBody
	public String updateNormConfig(@RequestParam(value = "entityId") String entityId,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		UserSession us=new UserSession(session);
		User user=us.getCurrentUser();
		
		int status=normalizationService.updateNormTable(user, entityId);
		
		return null;
	}
	/**
	 * 获取不规范数据
	 * @return
	 */
	@RequestMapping("normdetail")
	@ResponseBody
	public String detialData(@RequestParam(value = "entityId") String entityId,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		UserSession us=new UserSession(session);
		User user=us.getCurrentUser();
		PageVM<NormResult> normresult=normalizationService.showNormaFieldData(entityId);
		String result=JsonConvertor.obj2JSON(normresult.getGridData());
		return result;
	}
	/**
	 * 获取某实体某字段规范数据集
	 * @return
	 */
	@RequestMapping("normdataset")
	@ResponseBody
	public String normDataSet(@RequestParam(value="entityId") String entityId,@RequestParam(value="fieldName") String fieldName,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		String result=normalizationService.showNormDataSet("E20130102", "TD_TYPE");
		
		return result;
	}
	/**
	 * 提交规范结果-一条映射关系
	 */
	@RequestMapping("normSaveOne")
	@ResponseBody
	public int commmitResult(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		String dataId=request.getParameter("dataId");         
		String normValue=request.getParameter("normValue"); 
		String normResult=dataId+":"+normValue;
		int result= normalizationService.saveOneNormaResult(normResult);
		
		return result;
	}
	/**
	 * 导出所有采集表的规范化结果
	 */
	@RequestMapping("normExport")
	@ResponseBody
	public String exportAllNormResult(HttpServletRequest request){
		String fileString=null;
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		
		fileString = normalizationService.exportNormaResult(rootPath);
		
		return fileString;
	}
}
