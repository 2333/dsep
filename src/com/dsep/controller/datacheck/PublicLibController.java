package com.dsep.controller.datacheck;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.PubEntry;
import com.dsep.entity.dsepmeta.PubResult;
import com.dsep.service.check.publiclib.PublicLibService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.PageVM;

@Controller
@RequestMapping("check")
public class PublicLibController {

	@Resource(name = "publicLibService")
	private PublicLibService publicLibService;
	
	//学位中心载入公共库比对页面
	@RequestMapping("pub")
	public String pub()
	{
		return "/DataCheck/public_lib_check";
	}
	
	//载入展示公共库页面
	@RequestMapping("pubview")
	public String pubview()
	{
		return "/DataCheck/public_lib_view";
	}
	
	//载入公共库比对的配置表
	@RequestMapping("pubGetPublicEntry")
	@ResponseBody
	public String getPublicEntry(HttpServletRequest request,HttpServletResponse response){

		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		PageVM<PubEntry> entries = publicLibService.getPubEntry(page, pageSize);
		String result = JsonConvertor.obj2JSON(entries.getGridData());
		return result;
	}
	
	//开始比对
	@RequestMapping("pubStartCompare")
	@ResponseBody
	public boolean startCompare(HttpSession session){
		
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		try{
			publicLibService.startPubCompare(user.getLoginId());
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	//载入比对结果表
	@RequestMapping("pubGetPublicResult")
	@ResponseBody
	public String getPublicResult(@RequestParam(value = "pubLibId")String pubLibId ,@RequestParam(value = "type")String type,
			HttpServletRequest request,HttpServletResponse response){
		
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		if(pubLibId == null || pubLibId.equals("")) return "";
		PageVM<PubResult> pubResults = publicLibService.getResultDataByType(pubLibId, type, page, pageSize, order_flag, sidx);
		String result = JsonConvertor.obj2JSON(pubResults.getGridData());
		return result;
	} 
	
	/**
	 * 获取本地数据配置信息
	 */
	@RequestMapping("pubLocalDataConfig")
	@ResponseBody
	public String pubLocalDataConfig(@RequestParam(value = "entityId") String entityId) {

		ViewConfig viewConfig = publicLibService.getLocalDataConfig(entityId);
		
		String configData = JsonConvertor.obj2JSON(viewConfig);
		return configData;
	}
	
	/**
	 * 获取本地数据
	 * @return
	 */
	@RequestMapping("pubDataDetail")
	@ResponseBody
	public String pubDataDetail(HttpServletRequest request,
			HttpServletResponse response,String entityId, String itemId){
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		
		JqgridVM jqgridVM = publicLibService.getLocalDataDetail(
				entityId, itemId ,sidx, order_flag, page, pageSize);
		
		String result = JsonConvertor.obj2JSON(jqgridVM);
		return result;
	}
	
	//导出比对结果
	@RequestMapping("pubExportPublicResult")
	@ResponseBody
	public String exportPublicResult(HttpServletRequest request,HttpSession session){
		
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String fileString=null;
		
		fileString = publicLibService.getExportCompareInfo(user, rootPath);
		
		return fileString;
	}
}
