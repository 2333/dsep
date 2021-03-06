package com.dsep.controller.publicity.prepublicity;

import java.text.ParseException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.dsepmeta.PublicityManagement;
import com.dsep.service.base.DiscCategoryService;
import com.dsep.service.publicity.objection.PublicityService;
import com.dsep.service.publicity.prepublic.PrePublicityService;
import com.dsep.vm.publicity.PrePublicityManagementVM;
import com.dsep.vm.publicity.PublicityManagementVM;


@Controller
@RequestMapping("publicity")
public class CenterPrePublicityController {
	
	@Resource(name="discCategoryService")
	private DiscCategoryService discCategoryService;
	
	@Resource(name="publicityService")
	private PublicityService publicityService;
	
	@Resource(name="prePublicityService")
	private PrePublicityService prePublicityService;
	
	@RequestMapping("prepub")
	public String getIntoCenterPrePublicity(Model model) throws IllegalArgumentException, IllegalAccessException{
		Map<String,String> categoryMap = discCategoryService.getAllCategoryMap();
	    PublicityManagement currentRound = prePublicityService.getCurrentPublicityRound();
	    model.addAttribute("currentRound", currentRound);
	    model.addAttribute("discCategoryMap", categoryMap);
		/*model.addAttribute("versionId", currentRound.getBackupVersionId());
		model.addAttribute("roundStatus", currentRound.getStatus());*/
		return "PublicAndFeedback/PrePublicity/center_config";
	}
	
	@RequestMapping("prepub_beginPublicityDialog")
	public String publicityDialog(Model model) throws IllegalArgumentException, IllegalAccessException{
		PrePublicityManagementVM currentRound = new 
			PrePublicityManagementVM(prePublicityService.getCurrentPublicityRound());
	    model.addAttribute("currentRound", currentRound);
		return "PublicAndFeedback/PrePublicity/center_begin_publicity_dialog";
	}
	
	@RequestMapping("prepub_openPublicityRound")
	@ResponseBody
	public boolean openPublicityRound() throws IllegalArgumentException, IllegalAccessException{
		return prePublicityService.openNewPublicityRound();
	}
	
	@RequestMapping("prepub_setPublicity")
	@ResponseBody
	public boolean setPublicity(HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, ParseException{
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String publicityName = request.getParameter("publicityName");
		String remark = request.getParameter("remark");
		boolean result = prePublicityService.setPublicity(publicityName,beginTime, endTime,remark);
		return result;
	}
	
	
	@RequestMapping("prepub_beginPublicity")
	@ResponseBody
	public String beginPublicity(HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException{
		return prePublicityService.beginPublicity();
	}
	
}
