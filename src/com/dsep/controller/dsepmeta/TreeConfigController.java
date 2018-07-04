package com.dsep.controller.dsepmeta;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.logger.LoggerTool;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.util.JsonConvertor;

@Controller
@RequestMapping("Collect/toCollect/TreeConfig")
public class TreeConfigController {
	@Resource(name="loggerTool")
	private LoggerTool loggerTool;
	@Resource(name="collectService")
	private DMCollectService collectService;
	/**
	 * 初始化tree的配置信息
	 * @return
	 */
	@RequestMapping("initCollectTree/{discId}")
	@ResponseBody
	public String initCollectTree(@PathVariable(value="discId")String discId,HttpSession session)
	{
		return JsonConvertor.obj2JSON(collectService.getDiscCollectCategoryTreeVMs(discId));
		//return JsonConvertor.obj2JSON(collectService.getDisciplineCollectTrees(discId));
	}
}
