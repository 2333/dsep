package com.dsep.controller.project;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.User;
import com.dsep.entity.project.PassItem;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.project.PassItemService;
import com.dsep.service.project.SchoolProjectService;
import com.dsep.service.rbac.TeacherService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.PageVM;
import com.dsep.vm.project.PassItemVM;

@Controller
@RequestMapping("project")
public class PassItemController {

	
	@RequestMapping("pproject_showPassItems")
	@ResponseBody
	public String showPassItems(HttpSession session, HttpServletRequest request) {
		int pageIndex = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Boolean desc = request.getParameter("sord").equals("desc") ? true
				: false;
		String orderProperName = request.getParameter("sidx");
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		try {
			PageVM<PassItemVM> items = passItemService.retrive(null, 1, null,
					null, user.getId(), pageIndex, pageSize, desc,
					orderProperName);
			Map<String, Object> m = items.getGridData();
			String json = JsonConvertor.obj2JSON(m);
			return json;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
	
	@RequestMapping("pproject_showFinishedItems")
	@ResponseBody
	public String showFinishedItems(HttpSession session, HttpServletRequest request) {
		int pageIndex = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Boolean desc = request.getParameter("sord").equals("desc") ? true
				: false;
		String orderProperName = request.getParameter("sidx");
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		try {
			PageVM<PassItemVM> items = passItemService.retrive(null, 0, null,
					null, user.getId(), pageIndex, pageSize, desc,
					orderProperName);
			Map<String, Object> m = items.getGridData();
			String json = JsonConvertor.obj2JSON(m);
			return json;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
	
	@Resource(name = "schoolProjectService")
	private SchoolProjectService schoolProjectService;

	@Resource(name = "teacherService")
	private TeacherService teacherService;

	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;

	@Resource(name = "passItemService")
	private PassItemService passItemService;

	@Resource(name = "disciplineService")
	private DisciplineService disciplineService;

	@Resource(name = "unitService")
	private UnitService unitService;
}
