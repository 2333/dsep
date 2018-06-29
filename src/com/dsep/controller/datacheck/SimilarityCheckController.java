package com.dsep.controller.datacheck;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.SimilarityEntry;
import com.dsep.entity.dsepmeta.SimilarityResult;
import com.dsep.service.check.similarity.SimilarityCheckService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.PageVM;

@Controller
@RequestMapping("check")
public class SimilarityCheckController {

	@Resource(name = "similarityCheckService")
	private SimilarityCheckService similarityCheckService;
	
	@RequestMapping("similarity")
	public String similarity(Model model) {
		//model.addAttribute("textConfiguration",textConfiguration);
		return "/DataCheck/similarity";
	}
	
	/**
	 * 获取查重入口表
	 */
	@RequestMapping("similarityEntry")
	@ResponseBody
	public String similarityEntry(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		
		//初始化查重入口
		similarityCheckService.initSimilarityEntry(user);
		
		PageVM<SimilarityEntry> entries = similarityCheckService.getSimilarityEntry(user, sidx, order_flag, page, pageSize);
		String result = JsonConvertor.obj2JSON(entries.getGridData());
		
		return result;
	}
	
	/**
	 * 开始查重
	 */
	@RequestMapping("similarityStart")
	@ResponseBody
	public boolean similarityStart(@RequestParam(value = "entityIds")List<String> entityIds , HttpSession session) { 
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		
		try{
			similarityCheckService.startCheck(entityIds, user.getId(), user.getUnitId(), user.getDiscId());
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 更新查重入口表
	 */
	@RequestMapping("similarityUpdate")
	@ResponseBody
	public boolean similarityUpdate(@RequestParam(value = "entityIds")List<String> entityIds ,HttpSession session) {
		
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		
		try{
			similarityCheckService.updateSimilarityEntry(entityIds, user.getId());
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 加载查重结果
	 */
	@RequestMapping("similarityResult")
	@ResponseBody
	public String similarityResult(HttpServletRequest request,HttpServletResponse response,HttpSession session, String entityId, String unitId, String discId) {
		
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		
		PageVM<SimilarityResult> results = similarityCheckService.getSimilarityResults(entityId, user.getId(), unitId, discId, sidx, order_flag, page, pageSize);
		String result = JsonConvertor.obj2JSON(results.getGridData());
		
		return result;
	}
	
	/**
	 * 获取查重实体配置信息
	 */
	@RequestMapping("similarityGroupConfig")
	@ResponseBody
	public String similarityGroupConfig(String entityId) {

		ViewConfig viewConfig = similarityCheckService.getLocalDataConfig(entityId);
		
		String configData = JsonConvertor.obj2JSON(viewConfig);
		return configData;
	}
	
	/**
	 * 获取查重数据组数据
	 */
	@RequestMapping("similarityDetails")
	@ResponseBody
	public String similarityDetails(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,String entityId, String dataId, String simIds){
		
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		
		JqgridVM jqgridVM = similarityCheckService.getSimilarityGroupDetail(
				entityId, dataId, simIds ,sidx, order_flag, page, pageSize, user.getUnitId());
		
		String result = JsonConvertor.obj2JSON(jqgridVM);
		return result;
	}
	
	/**
	 * excel导出
	 */
	@RequestMapping("similarityExport")
	@ResponseBody
	public String similarityExport(HttpServletRequest request,HttpSession session) {
		
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String fileString=null;
		
		fileString=similarityCheckService.getSimilarityExport(user, rootPath);
		return fileString;
	}

}