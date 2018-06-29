package com.dsep.controller.flow;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.exception.BusinessException;
import com.dsep.service.flow.DsepMainFlowService;
import com.dsep.util.Configurations;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Controller
@RequestMapping("MainFlow")
public class MainFlowController {
	
	@Resource(name="dsepMainFlowService")
	DsepMainFlowService dsepMainFlowService;
	@RequestMapping("getCurrentState")
	@ResponseBody
	public String getCurrentState()
	{
		return dsepMainFlowService.getCurrentState();
	}
	
	@RequestMapping("centerFlowManage")
	public String centerFlowManage(Model model){
		model.addAttribute("domain",dsepMainFlowService.getCurrentMetaDomain());
		return "centerFlowManage/centerFlowManage";
	}
	
	@RequestMapping("centerFlowManage/saveInnerState")
	@ResponseBody
	public String saveInnerState(HttpServletRequest request){
		String domainId = request.getParameter("domainId");
		String innerState =request.getParameter("innerState");
		if(Configurations.getCurrentDomainId().equals(domainId)){
			if(dsepMainFlowService.updateInnerState(innerState)){
				return "success";
			}
		}
		return "error";
	}
}
