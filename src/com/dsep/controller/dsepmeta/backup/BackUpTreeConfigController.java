package com.dsep.controller.dsepmeta.backup;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.logger.LoggerTool;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.util.JsonConvertor;

@Controller
@RequestMapping("databackup/disciplinebackup/BackupData")
public class BackUpTreeConfigController {

	@Resource(name="loggerTool")
	private LoggerTool loggerTool;
	@Resource(name="collectService")
	private DMCollectService collectService;
	
	@RequestMapping("initBKTreeConfig/{discId}")
	@ResponseBody
	public String initBKCollectTree(@PathVariable(value="discId")String discId){
		return JsonConvertor.obj2JSON(collectService.getDisciplineCollectTrees(discId));
	} 
	
}
