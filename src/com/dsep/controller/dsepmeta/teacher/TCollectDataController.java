package com.dsep.controller.dsepmeta.teacher;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.mortbay.jetty.security.UserRealm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.logger.LoggerTool;
import com.dsep.controller.base.JqGridBaseController;
import com.dsep.entity.User;
import com.dsep.service.base.TeachDiscService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.rbac.UserService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.JqgridVM;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.domain.search.SearchGroup;

@Controller
@RequestMapping("TCollect/toTCollect/CollectData")
public class TCollectDataController extends JqGridBaseController{
	@Resource(name="loggerTool")
	private LoggerTool loggerTool;
	@Resource(name="collectService")
	private DMCollectService collectService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="teachDiscService")
	private TeachDiscService teachDiscService;
	
	@RequestMapping(value="collectionTData/{entityId}")
	@ResponseBody
	public String collectionTData(@PathVariable(value="entityId")String entityId,HttpServletRequest request,HttpSession session)
	{
		
		String unitId = request.getParameter("unitId");//单位代码
		String discId = request.getParameter("discId");//学科代码
		String userId = request.getParameter("userId");//用户Id
		String loginId = request.getParameter("loginId");//用户名
		List<String> userIds=null;
		if(userId!=null){
			userIds = new ArrayList<String>(0);
			userIds.add(userId);
		}
		else if(loginId!=null){
			User user = userService.getUserByLoginId(loginId);
			if(user!=null){
				userIds = new ArrayList<String>(0);
				userIds.add(user.getId());
			}
			
		}
		setRequestParams(request);
		JqgridVM jqgridVM = collectService.getJqGridTSearchData(entityId, unitId, discId, userIds, getSearchGroup(),getPageIndex(),getPageSize(), getSidx(),isAsc());
		String result=JsonConvertor.obj2JSON(jqgridVM);
		return result;
	}
	@RequestMapping(value="viewCollectTData/{entityId}/{tarEntityId}")
	@ResponseBody
	public String viewCollectTData(@PathVariable(value="entityId")String entityId,
			@PathVariable(value="tarEntityId")String tarEntityId,
			HttpServletRequest request,HttpSession session)
	{
		
		String unitId = request.getParameter("unitId");//单位代码
		String discId = request.getParameter("discId");//学科代码
		String userId = request.getParameter("userId");//用户Id
		String loginId = request.getParameter("loginId");//用户名
		List<String> userIds=null;
		if(userId!=null){
			userIds = new ArrayList<String>(0);
			userIds.add(userId);
		}
		else if(loginId!=null){
			User user = userService.getUserByLoginId(loginId);
			if(user!=null){
				userIds = new ArrayList<String>(0);
				userIds.add(user.getId());
			}
			
		}
		setRequestParams(request);
		JqgridVM jqgridVM = collectService.viewTSearchData(entityId,tarEntityId,unitId, discId, userIds, getSearchGroup(),getPageIndex(),getPageSize(), getSidx(),isAsc());
		String result=JsonConvertor.obj2JSON(jqgridVM);
		return result;
	}
}
