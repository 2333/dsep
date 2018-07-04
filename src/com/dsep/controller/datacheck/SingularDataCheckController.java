package com.dsep.controller.datacheck;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("singularDataCheck")
public class SingularDataCheckController {
	@RequestMapping("toSingularDataCheck")
	public String singulardatacheck()
	{
		return "/DataCheck/singulardata_check";
	}
}
