package com.dsep.controller.teacherManage;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.domain.AttachmentHelper;
import com.dsep.entity.User;
import com.dsep.entity.enumeration.AttachmentType;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.briefManage.BriefManageService;
import com.dsep.service.file.BriefsheetService;
import com.dsep.service.rbac.TeacherService;
import com.dsep.util.FileOperate;
import com.dsep.util.UserSession;
import com.dsep.util.briefsheet.AbstractPDF;
import com.dsep.util.briefsheet.BriefTeacher;

@Controller
@RequestMapping("Teacher/briefManage/")
public class TeacherBriefManage {
	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;
	@Resource(name = "teacherService")
	private TeacherService teacherService;
	
	@RequestMapping("viewBrief")
	public String viewTeacherBrief(Model model,HttpSession session){
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		model.addAttribute("brief", briefManageService.getTeacherBriefVM(user.getId()));
		return "TeacherBriefManage/teacherBriefManage";
	}
	
	@Resource(name="briefManageService")
	private BriefManageService briefManageService;
	
	@Resource(name="briefsheetService")
	private BriefsheetService briefsheetService;
	
	@RequestMapping("createTeacherBrief")
	@ResponseBody
	public String createTeacherBrief(HttpSession session){
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		String teacherId = user.getId();
		//先删除之前的数据库信息以及文件
		String briefId = teacherService.geTeacherById(teacherId).getBriefId();
		if(briefId != null && !briefId.isEmpty()){
			attachmentService.deleteAttachment(briefId);
		}
		//FileOperate.delete(attachmentService.getAttachmentPath(briefId));
		//开始生成新的简况表
		String briefName = teacherService.geTeacherById(teacherId).getLoginId() 
				+ "_" + teacherService.geTeacherById(teacherId).getName();
		AbstractPDF briefTeacher = new BriefTeacher(teacherId, briefName, null);
		AttachmentHelper ah = 
			attachmentService.getBriefHelper(briefTeacher, user.getId(), AttachmentType.BRIEF, user.getUnitId());
		try {
			//先生成文件
			briefsheetService.generateBriefSheet(briefTeacher);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//再写入数据库
		String fileId = attachmentService.addAttachment(ah.getAttachment());
		String result = teacherService.updateTeacherBriefId(teacherId, fileId);
		return "{\"result\":\""+"success"+"\",\"data\":\""+result+"\"}";
	}
	
	@RequestMapping("reCreateTeacherBrief")
	@ResponseBody
	public String recreateTeacherBrief(HttpSession session){
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		return briefManageService.produceBrief(user.getId());
	}
	@RequestMapping("/downLoadBrief/{briefId}")
	@ResponseBody
	public String downLoadBrief(@PathVariable(value="briefId")String briefId)
	{
		if(briefId==null) return "{\"result\":\""+"failure"+"\",\"data\":\" \"}";
		else 
		{
			String downLoadPath=briefManageService.downLoadBrief(briefId);
			return "{\"result\":\""+"success"+"\",\"data\":\""+downLoadPath+"\"}";
		}	
	}
	
}
