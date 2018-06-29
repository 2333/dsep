package com.dsep.controller.publicDataManage;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.util.JsonConvertor;


@Controller
@RequestMapping("PublicDataManage/viewData/TreeConfig")
public class PubDataTreeConfigController {

	
	@Resource(name="collectService")
	private DMCollectService collectService;
	
	@RequestMapping("initTree")
	@ResponseBody
	public String initCollectTree(HttpSession session)
	{
		return JsonConvertor.obj2JSON(collectService.getTreesByOccasion("G"));
	}
}
