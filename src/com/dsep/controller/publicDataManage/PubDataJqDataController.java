package com.dsep.controller.publicDataManage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.controller.base.JqGridBaseController;
import com.dsep.entity.User;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.JqgridVM;

@Controller
@RequestMapping("PublicDataManage/viewData/CollectData")

public class PubDataJqDataController extends JqGridBaseController{
	
	@Resource(name="collectService")
	private DMCollectService collectService;
	
	@RequestMapping(value="collectionData/{entityId}")
	@ResponseBody
	public String collectionData(@PathVariable(value="entityId")String entityId,
							HttpServletRequest request,
							HttpSession session)
	{
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		setRequestParams(request);
		collectService.initOnePublicData(entityId,user.getId());
		JqgridVM jqgridVM=collectService.getJqGridSearchData(entityId,null,null,getSearchGroup(),getPageIndex(), getPageSize(),getSidx(), isAsc());
		String result=JsonConvertor.obj2JSON(jqgridVM);
		return result;
	}
}
