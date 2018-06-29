package com.dsep.controller.publicDataManage;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.service.dsepmeta.dsepmetas.DMViewConfigService;
import com.dsep.util.JsonConvertor;

@Controller
@RequestMapping("PublicDataManage/viewData/JqConfig")

public class PubDataJqConfigController {
	
	@Resource(name="dmViewConfigService")
	private DMViewConfigService viewConfigService;	

	@RequestMapping("/initCollectJqgrid/{entityId}")
	@ResponseBody
	public String initCollectJqgrid(@PathVariable(value="entityId")String entityId)
	{
		ViewConfig viewConfig = viewConfigService.getViewConfig(entityId,"G");
		String configData=JsonConvertor.obj2JSON(viewConfig);
		return configData;
		
	}
}
