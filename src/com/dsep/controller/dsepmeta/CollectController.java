package com.dsep.controller.dsepmeta;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.exception.BusinessException;
import com.dsep.entity.User;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.dsepmeta.dsepmetas.DMDiscIndexService;
import com.dsep.service.flow.EvalFlowService;
import com.dsep.util.Configurations;
import com.dsep.util.JsonConvertor;
import com.dsep.util.TextConfiguration;
import com.dsep.util.UserSession;


@Controller
@RequestMapping("/Collect")
public class CollectController {
	@Resource(name="evalFlowService")
	private EvalFlowService evalFlowService;
	
	@Resource(name="collectService")
	private DMCollectService collectService;
	
	@Resource(name="discIndexService")
	private DMDiscIndexService discIndexService;
	
	/**
	 * 进入学科数据采集页面
	 * @return 页面路径
	 */
	@RequestMapping("toCollect")
	public String toDisciplineCollect(Model model,HttpServletRequest request,HttpSession session){	
		
		String requestDiscId = StringUtils.isBlank(request.getParameter("discId"))?"":request.getParameter("discId");
		String requestUnitId = StringUtils.isBlank(request.getParameter("unitId"))?"":request.getParameter("unitId");
		UserSession userSession=new UserSession(session);
		User user = userSession.getCurrentUser();
		String unitId=StringUtils.isBlank(user.getUnitId())?"":user.getUnitId();
		String discId=StringUtils.isBlank(user.getDiscId())?"":user.getDiscId();
		//String categoryId = discIndexService.getCategotyByDiscId(discId);
		String userType = user.getUserType();
		switch(userType){
		case "4"://教师
			model.addAttribute("loginId", user.getLoginId());
			return "TDataCollect/teacher_collect";
		case "3": //学科
			model.addAttribute("unitId", unitId);
			model.addAttribute("discId", discId);
			model.addAttribute("categoryId", discIndexService.getCategotyByDiscId(discId));
			return "/DataCollect/discipline_collect";
		case "2"://学校
			model.addAttribute("unitId", unitId);
			model.addAttribute("discId", requestDiscId);
			model.addAttribute("categoryId", discIndexService.getCategotyByDiscId(requestDiscId));
			return "/DataCollect/unit_collect";
		case "1"://中心
			model.addAttribute("unitId", requestUnitId);
			model.addAttribute("discId", requestDiscId);
			model.addAttribute("categoryId", discIndexService.getCategotyByDiscId(requestDiscId));
			return "/DataCollect/center_collect";
		default:
			throw new BusinessException("用户非法访问");
		}
	}
	/**
	 * 学校请求编辑学科成果
	 * @return
	 */
	@RequestMapping("toCollect/unitEditData/{unitId}/{discId}")
	@ResponseBody
	public String unitEditData(@PathVariable(value="unitId")String unitId,
			@PathVariable(value="discId")String discId){
		if(evalFlowService.eidtDiscData(unitId, discId)){
			return "success";
		}
		return "error";
	}
	/**
	 * 学校请求确认修改学科成果
	 * @return
	 */
	@RequestMapping("toCollect/unitConfirmData/{unitId}/{discId}")
	@ResponseBody
	public String unitConfirmData(@PathVariable(value="unitId")String unitId,
			@PathVariable(value="discId")String discId){
		if(evalFlowService.confirmDiscData(unitId, discId)){
			return "success";
		}
		return "error";
	}
	/**
	 * 获取采集状态
	 * @param unitId
	 * @param discId
	 * @return
	 */
	@RequestMapping("toCollect/collectState/{unitId}/{discId}")
	@ResponseBody
	public String collectState(@PathVariable(value="unitId")String unitId,
			@PathVariable(value="discId")String discId)
	{
		return evalFlowService.getEvalStatus(unitId, discId).toString();
	}
	/**
	 * 是否有编辑权限
	 * @param unitId
	 * @param discId
	 * @param session
	 * @return 1 有编辑权限，0没有编辑权限
	 */
	@RequestMapping("toCollect/isEditable/{unitId}/{discId}")
	@ResponseBody
	public String isEditable(@PathVariable(value="unitId")String unitId,
			@PathVariable(value="discId")String discId,HttpSession session)
	{
		UserSession userSession= new UserSession(session);
		if(evalFlowService.isEditable(unitId, discId, userSession.getCurrentUser().getUserType()))
		{
			return "1";
		}else{
			return "0";
		}
	}
	/**
	 * 获取学校和学科的版本号
	 * @param request
	 * @return
	 */
	@RequestMapping("toCollect/collectVersion")
	@ResponseBody
	public String collectVersion(HttpServletRequest request)
	{
		String unitId=request.getParameter("unitId");
		String discId=request.getParameter("discId");
		return evalFlowService.getVersionNo(unitId, discId);
	}
	/**
	 * 对比例和名次控件的请求处理
	 * @return
	 */
	@RequestMapping("toCollect/percentdialog")
	public String percentdialog()
	{
	    return "/CollectMeta/editpercent";
	}
	
	@RequestMapping("toCollect/toOriginCollect/{tarEntityId}/{discId}")
	@ResponseBody
	public String toOriginCollect(@PathVariable(value="tarEntityId")String tarEntityId,
			@PathVariable(value="discId")String discId){
		Map<String, String> origEntity=collectService.getOriginEntity(tarEntityId, discId);
		if(origEntity!=null){
			return JsonConvertor.obj2JSON(origEntity);
		}
		return "{\"\":\"\"}";
	}
}
