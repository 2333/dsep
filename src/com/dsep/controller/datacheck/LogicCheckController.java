package com.dsep.controller.datacheck;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.entity.User;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.check.logic.LogicCheckService;
import com.dsep.service.dsepmeta.dsepmetas.DMCollectService;
import com.dsep.service.dsepmeta.dsepmetas.DMViewConfigService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.LogicCheckVM;
import com.dsep.vm.PageVM;
import com.meta.domain.search.SearchGroup;
import com.meta.domain.search.SearchRule;
import com.meta.domain.search.SearchType;

@Controller
@RequestMapping("check")
public class LogicCheckController {

	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;

	@Resource(name = "logicCheckService")
	private LogicCheckService logicCheckService;

	@Resource(name = "dmViewConfigService")
	private DMViewConfigService viewConfigService;

	// 展示"团队"、"专家"、"论文"等数据的Service
	@Resource(name = "collectService")
	private DMCollectService collectService;

	/**
	 * 学科逻辑检查入口
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("disclogiccheck")
	public String disclogiccheck(Model model, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		model.addAttribute("user", user);
		return "/DataCheck/disc_logic_check";
	}

	/**
	 * 学校逻辑检查入口
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("unitLogicCheck")
	public String unitLogicCheck(Model model, HttpSession session) {
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		String unitId = user.getUnitId();
		Map<String, String> initDiscsMap = logicCheckService
				.getInitDiscIdMaps(unitId);
		model.addAttribute("discMap", initDiscsMap);
		model.addAttribute("user", user);
		return "/DataCheck/unit_logic_check";
	}

	/**
	 * 中心逻辑检查入口
	 * 
	 * @param model
	 * @param tableId
	 * @return
	 */
	@RequestMapping("centerLogicCheck")
	public String centerlogiccheck(Model model, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		Map<String, String> initUnitMap = logicCheckService.getInitUnitIdMaps();
		model.addAttribute("unitMap", initUnitMap);
		model.addAttribute("user", user);
		return "/DataCheck/center_logic_check";
	}

	/**
	 * （学科）根据实体ID 获取该实体逻辑错误信息列表
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("disclogiccheck_getCheckResultList")
	@ResponseBody
	public String disclogiccheck_getCheckResultList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		String entityId = request.getParameter("entityId");
		PageVM<?> pageVM = logicCheckService.showLogicResultData( 
				user.getUnitId(), user.getDiscId(), entityId, user.getId(),
				sidx, order_flag, page, pageSize);
		return JsonConvertor.obj2JSON(pageVM.getGridData());
	}

	/**
	 * （学科）根据实体ID 获取该实体逻辑错误信息列表
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("disclogiccheck_exportDiscWrongAndWarnData")
	@ResponseBody
	public String disclogiccheck_exportDiscWrongAndWarnData(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String rootPath = request.getSession().getServletContext()
				.getRealPath("/");
		return logicCheckService.exportErrorAndWarnData(user.getUnitId(),
				user.getDiscId(), user, rootPath);
	}

	/**
	 * 学科开始逻辑检查入口
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("disclogiccheck_startLogicCheck")
	@ResponseBody
	public boolean disclogiccheck_startLogicCheck(HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		boolean result = logicCheckService.startLogicCheck(user.getUnitId(),
				user.getDiscId(), user);
		return result;
	}

	/**
	 * （学科）获取警告信息
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("disclogiccheck_getCheckWarnList")
	@ResponseBody
	public String disclogiccheck_getCheckWarnList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();// 即可获得当前登录用户
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		String entityId = request.getParameter("entityId");
		boolean onlyGetWarn = false;
		if (null == request.getParameter("onlyGetWarn")
				|| !request.getParameter("onlyGetWarn").equals("yes")) {
			onlyGetWarn = false;
		} else {
			onlyGetWarn = true;
		}

		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}

		PageVM<?> pageVM = logicCheckService.showLogicWarnData(
				user.getUnitId(), user.getDiscId(), entityId, user.getId(),
				onlyGetWarn, sidx, order_flag, page, pageSize);
		System.out.println(JsonConvertor.obj2JSON(pageVM.getGridData()));
		return JsonConvertor.obj2JSON(pageVM.getGridData());
	}

	/**
	 * 获取学科逻辑检查配置信息 即左边配置表
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("disclogiccheck_getEntityList")
	@ResponseBody
	public String disclogiccheck_getEntityList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		PageVM<LogicCheckVM> pageVM = logicCheckService.showResultForDisc(
				user.getUnitId(), user.getDiscId(), user.getId(), sidx,
				order_flag, page, pageSize);
		String result = JsonConvertor.obj2JSON(pageVM.getGridData());

		return result;
	}

	/**
	 * （学校）显示当前学校所有申报的学科列表
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * 
	 * 
	 * {"pageIndex":1,"totalPage":1,"totalCount":6,"rows":[
	 * {"disciplineChsName":"125100","unitChsName":null,"entityChsName":null,"unitId":null,
	 * "passInfo":"错误、警告","entityId":null,"discId":"125100"},
	 * 
	 * {"disciplineChsName":"085213","unitChsName":null,"entityChsName":null,"unitId":null,
	 * "passInfo":"警告","entityId":null,"discId":"085213"},
	 * 
	 * {"disciplineChsName":"125200","unitChsName":null,"entityChsName":null,"unitId":null,
	 * "passInfo":"错误、警告","entityId":null,"discId":"125200"},
	 * 
	 * {"disciplineChsName":"135100","unitChsName":null,"entityChsName":null,"unitId":null,
	 * "passInfo":"警告","entityId":null,"discId":"135100"},
	 * 
	 * {"disciplineChsName":"035100","unitChsName":null,"entityChsName":null,"unitId":null,"passInfo":"警告","entityId":null,"discId":"035100"},
	 * {"disciplineChsName":"125300","unitChsName":null,"entityChsName":null,"unitId":null,"passInfo":"警告","entityId":null,"discId":"125300"}]}
	 */
	@RequestMapping("unitLogicCheck_getDiscsList")
	@ResponseBody
	public String unitgetDiscsList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		String inputDiscId = request.getParameter("inputDiscId");
		PageVM<LogicCheckVM> pageVM = logicCheckService.showResultForUnit(
				inputDiscId, user, sidx, order_flag, page, pageSize);
		String result = JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}

	@RequestMapping("unitLogicCheck_exportUnitWrongAndWarnData")
	@ResponseBody
	public String unitLogicCheck_exportUnitWrongAndWarnData(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String rootPath = request.getSession().getServletContext()
				.getRealPath("/");
		return logicCheckService.exportErrorAndWarnData(user.getUnitId(),
				user.getDiscId(), user, rootPath);
	}

	@RequestMapping("unitLogicCheck_getCheckResultList")
	@ResponseBody
	public String unitLogicCheck_getCheckResultList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		String discId = request.getParameter("discId");
		PageVM<?> pageVM = logicCheckService.showLogicResultData(
				user.getUnitId(), discId, null, user.getId(), sidx, order_flag,
				page, pageSize);
		String test = JsonConvertor.obj2JSON(pageVM.getGridData());
		return test;
	}

	/**
	 * @author YangJunLin
	 * @param session
	 * @return
	 */
	@RequestMapping("unitLogicCheck_startLogicCheck")
	@ResponseBody
	public boolean unitLogicCheck_startLogicCheck(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String inputUnitId = null;
		String inputDiscId = request.getParameter("inputDiscId");
		boolean result = logicCheckService.startLogicCheck(inputUnitId,
				inputDiscId, user);
		return result;
	}

	@RequestMapping("unitLogicCheck_getCheckWarnList")
	@ResponseBody
	public String unitLogicCheck_getCheckWarnList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();// 即可获得当前登录用户
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		String discId = request.getParameter("discId");
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		boolean onlyGetWarn = false;
		if (null == request.getParameter("onlyGetWarn")
				|| !request.getParameter("onlyGetWarn").equals("yes")) {
			onlyGetWarn = false;
		} else {
			onlyGetWarn = true;
		}

		PageVM<?> pageVM = logicCheckService.showLogicWarnData(
				user.getUnitId(), discId, null, user.getId(), onlyGetWarn,
				sidx, order_flag, page, pageSize);
		return JsonConvertor.obj2JSON(pageVM.getGridData());
	}

	@RequestMapping("unitLogicCheck_whetherCheckComplete")
	@ResponseBody
	public String schoolCheckComplete(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		return logicCheckService.haveCheckCompleted(user.getUnitId(),
				user.getDiscId(), user.getId());
	}

	@RequestMapping("centerLogicCheck_getCheckResultList")
	@ResponseBody
	public String centerLogicCheck_getCheckResultList(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		String unitId = request.getParameter("unitId");
		String discId = null;
		if (!request.getParameter("inputDiscId").equals("undefined")) {
			discId = request.getParameter("inputDiscId");
		}

		PageVM<?> pageVM = logicCheckService.showLogicResultData(unitId,
				discId, null, user.getId(), sidx, order_flag, page, pageSize);
		return JsonConvertor.obj2JSON(pageVM.getGridData());
	}

	@RequestMapping("centerLogicCheck_exportCenterWrongAndWarnData")
	@ResponseBody
	public String centerLogicCheck_exportCenterWrongAndWarnData(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String rootPath = request.getSession().getServletContext()
				.getRealPath("/");
		return logicCheckService.exportErrorAndWarnData(user.getUnitId(),
				user.getDiscId(), user, rootPath);
	}

	@RequestMapping("centerLogicCheck_getCheckWarnList")
	@ResponseBody
	public String centerLogicCheck_getCheckWarnList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		String unitId = request.getParameter("unitId");
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}

		boolean onlyGetWarn = false;
		if (null == request.getParameter("onlyGetWarn")
				|| !request.getParameter("onlyGetWarn").equals("yes")) {
			onlyGetWarn = false;
		} else {
			onlyGetWarn = true;
		}
		String discId = null;
		if ((null != request.getParameter("inputDiscId"))
				&& (!request.getParameter("inputDiscId").equals("undefined"))) {
			discId = request.getParameter("inputDiscId");
		}

		PageVM<?> pageVM = logicCheckService.showLogicWarnData(unitId, discId,
				null, user.getId(), onlyGetWarn, sidx, order_flag, page,
				pageSize);
		return JsonConvertor.obj2JSON(pageVM.getGridData());
	}

	@RequestMapping("centerLogicCheck_startLogicCheck")
	@ResponseBody
	public boolean centerLogicCheck_startLogicCheck(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String inputUnitId = request.getParameter("inputUnitId");
		String inputDiscId = request.getParameter("inputDiscId");
		boolean result = logicCheckService.startLogicCheck(inputUnitId,
				inputDiscId, user);
		return result;
	}

	@RequestMapping("centerLogicCheck_getEntityList")
	@ResponseBody
	public String CenterEntityList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		String inputUnitId = request.getParameter("inputUnitId");
		PageVM<LogicCheckVM> pageVM = logicCheckService.showResultForCenter(
				inputUnitId, user, sidx, order_flag, page, pageSize);
		String result = JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}

	@RequestMapping("centerLogicCheck_whetherCheckComplete")
	@ResponseBody
	public String centerCheckComplete(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		return logicCheckService.haveCheckCompleted(user.getUnitId(),
				user.getDiscId(), user.getId());
	}

	@RequestMapping("disclogiccheck_whetherCheckComplete")
	@ResponseBody
	public String discCheckComplete(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		return logicCheckService.haveCheckCompleted(user.getUnitId(),
				user.getDiscId(), user.getId());
	}

	@RequestMapping("centerLogicCheck_getDiscIdBySelectUnitId")
	@ResponseBody
	public Map<String, String> centerLogicCheck_getDiscIdBySelectUnitId(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String unitId = request.getParameter("unitId");
		Map<String, String> initDiscsMap = logicCheckService
				.getInitDiscIdMaps(unitId);
		return initDiscsMap;
	}

	/**
	 * 获取本地数据配置信息
	 */
	@RequestMapping("unitLogicCheck_DataConfig")
	@ResponseBody
	public String unitLogicCheckDataConfig(
			@RequestParam(value = "entityId") String entityId) {

		ViewConfig viewConfig = viewConfigService.getViewConfig(entityId);

		String configData = JsonConvertor.obj2JSON(viewConfig);
		return configData;
	}

	@RequestMapping("unitLogicCheck_getSubData")
	@ResponseBody
	public String unitLogicCheckGetSubData(HttpServletRequest request,
			HttpServletResponse response, String unitId, String discId,
			String entityId, String seqNo) {
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));

		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		SearchGroup searchGroup = new SearchGroup("and", new SearchRule(
				"SEQ_NO", new Object[] { seqNo }, SearchType.INT));
		JqgridVM jqgridVM = collectService.getJqGridSearchData(entityId,
				unitId, discId, searchGroup, page, pageSize, "", order_flag);
		String result = JsonConvertor.obj2JSON(jqgridVM);
		System.out.println(result);
		return result;
	}

	/**
	 * 获取本地数据配置信息
	 */
	@RequestMapping("centerLogicCheck_DataConfig")
	@ResponseBody
	public String centerLogicCheckDataConfig(
			@RequestParam(value = "entityId") String entityId) {

		ViewConfig viewConfig = viewConfigService.getViewConfig(entityId);

		String configData = JsonConvertor.obj2JSON(viewConfig);
		return configData;
	}

	@RequestMapping("centerLogicCheck_getSubData")
	@ResponseBody
	public String centerLogicCheckGetSubData(HttpServletRequest request,
			HttpServletResponse response, String unitId, String discId,
			String entityId, String seqNo) {
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));

		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		SearchGroup searchGroup = new SearchGroup("and", new SearchRule(
				"SEQ_NO", new Object[] { seqNo }, SearchType.INT));
		JqgridVM jqgridVM = collectService.getJqGridSearchData(entityId,
				unitId, discId, searchGroup, page, pageSize, "", order_flag);
		String result = JsonConvertor.obj2JSON(jqgridVM);
		System.out.println(result);
		return result;
	}

	/**
	 * 获取本地数据配置信息
	 */
	@RequestMapping("disclogiccheck_DataConfig")
	@ResponseBody
	public String pubLocalDataConfig(
			@RequestParam(value = "entityId") String entityId) {

		ViewConfig viewConfig = viewConfigService.getViewConfig(entityId);

		String configData = JsonConvertor.obj2JSON(viewConfig);
		return configData;
	}

	@RequestMapping("disclogiccheck_getSubData")
	@ResponseBody
	public String disclogiccheck_getSubData(HttpServletRequest request,
			HttpServletResponse response, String unitId, String discId,
			String entityId, String seqNo) {
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));

		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		SearchGroup searchGroup = new SearchGroup("and", new SearchRule(
				"SEQ_NO", new Object[] { seqNo }, SearchType.INT));
		JqgridVM jqgridVM = collectService.getJqGridSearchData(entityId,
				unitId, discId, searchGroup, page, pageSize, "", order_flag);
		String result = JsonConvertor.obj2JSON(jqgridVM);
		System.out.println(result);
		return result;
	}

	@RequestMapping("centerLogicCheck_downLoadTemplate/{templateId}")
	@ResponseBody
	public String downLoadTemplate(
			@PathVariable(value = "templateId") String templateId) {
		return JsonConvertor.obj2JSON(attachmentService
				.getAttachmentPath("F2CB69460EDB49AB8CDCBD981F371C87"));
	}

	@RequestMapping("unitLogicCheck_downLoadTemplate/{templateId}")
	@ResponseBody
	public String downLoadTemplate2(
			@PathVariable(value = "templateId") String templateId) {
		return JsonConvertor.obj2JSON(attachmentService
				.getAttachmentPath("F2CB69460EDB49AB8CDCBD981F371C87"));
	}

	@RequestMapping("disclogiccheck_downLoadTemplate/{templateId}")
	@ResponseBody
	public String downLoadTemplate3(
			@PathVariable(value = "templateId") String templateId) {
		return JsonConvertor.obj2JSON(attachmentService
				.getAttachmentPath("F2CB69460EDB49AB8CDCBD981F371C87"));
	}
}
