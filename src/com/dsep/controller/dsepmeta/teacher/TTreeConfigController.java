package com.dsep.controller.dsepmeta.teacher;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.logger.LoggerTool;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.util.JsonConvertor;

@Controller 
@RequestMapping("TCollect/toTCollect/TreeConfig")
public class TTreeConfigController {
	@Resource(name="loggerTool")
	private LoggerTool loggerTool;
	@Resource(name="collectService")
	private DMCollectService collectService;
	
	@RequestMapping("initTCollectTree")
	@ResponseBody
	public String initTCollectTree(){
		return JsonConvertor.obj2JSON(collectService.getTreesByOccasion("T"));
	} 

}
