package com.dsep.controller.dsepmeta;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.cfg.ExtendsQueueEntry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.logger.LoggerTool;
import com.dsep.controller.base.JqGridBaseController;
import com.dsep.entity.User;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.JqgridVM;
import com.meta.domain.search.SearchGroup;

@Controller
@RequestMapping("Collect/toCollect/CollectData")
public class CollectDataController extends JqGridBaseController{
	@Resource(name="loggerTool")
	private LoggerTool loggerTool;
	@Resource(name="collectService")
	private DMCollectService collectService;
	/**
	 * 加载jqgrid的数据
	 * @param tableId
	 * @param titleValues
	 * @param collegeId
	 * @param disciplineId
	 * @param request
	 * @return 
	 */
	@RequestMapping(value="collectionData/{entityId}/" +
			"{unitId}/{discId}")
	@ResponseBody
	public String collectionData(@PathVariable(value="entityId")String entityId,
							@PathVariable(value="unitId")String unitId,
							@PathVariable(value="discId")String disciplineId,
							HttpServletRequest request,
							HttpSession session)
	{
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		setRequestParams(request);
		collectService.initOneCollectData(entityId, unitId, disciplineId,user.getId());
		JqgridVM jqgridVM=collectService.getJqGridSearchData(entityId,unitId,disciplineId,getSearchGroup(),getPageIndex(), getPageSize(),getSidx(), isAsc());
		String result=JsonConvertor.obj2JSON(jqgridVM);
		return result;
		
	}
}
