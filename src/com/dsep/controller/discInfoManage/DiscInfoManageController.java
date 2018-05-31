package com.dsep.controller.discInfoManage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.User;
import com.dsep.service.briefManage.BriefManageService;
import com.dsep.service.dsepmeta.dsepmetas.DMCheckLogicRuleService;
import com.dsep.util.TextConfiguration;
import com.dsep.util.UserSession;


@Controller
@RequestMapping("DiscInfoManage")
public class DiscInfoManageController {
	
	@Resource(name="briefManageService")
	private BriefManageService briefManageService;
	
	@Resource(name="checkLogicRule")
	DMCheckLogicRuleService checkLogicRule;
	
	@RequestMapping("viewBrief")
	public String viewBrief(Model model,HttpSession session){
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		model.addAttribute("brief",briefManageService.getDiscBriefVM(user.getUnitId(), user.getDiscId()));
		model.addAttribute("logicCheckInfo", checkLogicRule.getLogicCheckResultBeforeSubmit(user.getUnitId(), user.getDiscId(), user));
		//model.addAttribute("logicCheckTime", checkLogicRule.getLogicCheckResultBeforeSubmit(user.getUnitId(), user.getDiscId(), user));
		return "/BriefManage/discBriefManage";
	}
	
	@RequestMapping("viewBrief/produceBrief/{unitId}/{discId}")
	@ResponseBody
	public String produceBrief(@PathVariable(value="unitId")String unitId,
							  @PathVariable(value="discId")String discId
							)
	{
		return briefManageService.produceBrief(unitId, discId);
	}
	
	@RequestMapping("viewBrief/downLoadBrief/{briefId}")
	@ResponseBody
	public String downLoadBrief(@PathVariable(value="briefId")String briefId)
	{
		if(briefId==null) return "{\"result\":\""+"failure"+"\",\"data\":\" \"}";
		else 
		{
			String downLoadPath=briefManageService.downLoadBrief(briefId);
			return "{\"result\":\""+"success"+"\",\"data\":\""+downLoadPath+"\"}";
		}	
	}
}

