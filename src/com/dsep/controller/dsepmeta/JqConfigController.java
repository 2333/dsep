package com.dsep.controller.dsepmeta;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.logger.LoggerTool;
import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.service.dsepmeta.dsepmetas.DMViewConfigService;
import com.dsep.util.JsonConvertor;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Controller
@RequestMapping("Collect/toCollect/JqConfig")
public class JqConfigController {
	@Resource(name="loggerTool")
	private LoggerTool loggerTool;	
	@Resource(name="dmViewConfigService")
	private DMViewConfigService viewConfigService;	
	/**
	 * 初始化jqgrid的配置信息
	 * @param tableId
	 * @return 配置信息
	 */
	@RequestMapping("/initCollectJqgrid/{entityId}")
	@ResponseBody
	public String initCollectJqgrid(@PathVariable(value="entityId")String entityId)
	{
		loggerTool.warn("初始化Jqgrid!");
		ViewConfig viewConfig = viewConfigService.getViewConfig(entityId);
		String configData=JsonConvertor.obj2JSON(viewConfig);
		return configData;
		
	}
}
