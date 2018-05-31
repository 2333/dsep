package com.dsep.controller.datacheck;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.SpotResult;
import com.dsep.service.check.paperInspect.PaperInspectionService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.PageVM;

@Controller
@RequestMapping("check")
public class DataSpotCheckController {
	
	@Resource(name="paperInspectionService")
	private PaperInspectionService paperInspectionService;
	
	@RequestMapping("spot")
	public String spot()
	{
		return "/DataCheck/spot_check";
	}
	/**
	 * 生成抽查清单
	 * @return
	 */
	@RequestMapping("spotGenerateList")
	@ResponseBody
	public boolean generateSpotList(){
		try{
			paperInspectionService.startSpot();
			return true;
		}catch(Exception e){
			return false;
		}
		
	}
	
	/**
	 * 返回抽查清单结果
	 * @return
	 */
	@RequestMapping("spotList")
	@ResponseBody
	public String getSpotList(HttpServletRequest request,HttpServletResponse response){
		String sord=request.getParameter("sord");
		String sidx=request.getParameter("sidx");
		int page=Integer.parseInt(request.getParameter("page"));
		int pageSize=Integer.parseInt(request.getParameter("rows"));
		boolean order_flag=false;
		if("desc".equals(sord)){
			order_flag=false;
		}
		PageVM<SpotResult> inspectList=paperInspectionService.getSpotList(page, pageSize, order_flag, sidx);
		String result=JsonConvertor.obj2JSON(inspectList.getGridData());
		return result;
		
	}
	/**
	 * 获取抽查规则
	 * @return
	 */
	@RequestMapping("spotRule")
	@ResponseBody
	public String getRule(){
		return null;
	}
	/**
	 * 更改抽查规则
	 * @return
	 */
	@RequestMapping("spoteditRule")
	@ResponseBody
	public boolean setRule(){
		return false;
	}
	/**
	 * 导出抽查清单
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("spotExport")
	@ResponseBody
	public String spotExport(HttpServletRequest request,HttpSession session){
		UserSession us=new UserSession(session);
		User user=us.getCurrentUser();
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String fileString=null;
		
		fileString=paperInspectionService.getExportSpotList(rootPath);
		return fileString;
		
	}
}
