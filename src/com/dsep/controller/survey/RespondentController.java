package com.dsep.controller.survey;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("survey")
public class RespondentController {
	
	/**
	 * 跳转调查对象管理界面
	 */
	@RequestMapping("rmanage")
	public String rmanage(){
		return "survey/manage_respondent";
	}
	
	
}

