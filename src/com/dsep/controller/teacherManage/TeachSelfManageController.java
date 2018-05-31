package com.dsep.controller.teacherManage;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dsep.entity.User;
import com.dsep.service.rbac.TeacherService;
import com.dsep.util.UserSession;

@Controller
@RequestMapping("/TeacherSelfManage")
public class TeachSelfManageController {

	@Resource(name="teacherService")
	private TeacherService teacherService;
	
	@RequestMapping("/teacherInfo")
	public String teacherInfoManage(Model model,HttpSession session){
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		model.addAttribute("teacher", teacherService.geTeacherById(user.getId()));
		return "InfoMaintain/teacher_info";
	}
}
