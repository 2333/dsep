package com.dsep.controller.publicDataManage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("PublicDataManage")
public class PublicDataManageController {
	
	@RequestMapping("viewData")
	public String viewPublicData(){
		return "/PublicDataManage/publicDataManage";
	}
}
