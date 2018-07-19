package com.dsep.controller.teacherManage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.exception.TeachManageException;
import com.dsep.controller.base.JqGridBaseController;
import com.dsep.entity.User;
import com.dsep.service.base.TeachDiscService;
import com.dsep.service.rbac.TeacherService;
import com.dsep.util.JsonConvertor;
import com.dsep.util.Tools;
import com.dsep.util.UserSession;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Controller
@RequestMapping("/teachDiscManage")
public class DiscTeachManageController extends JqGridBaseController{
	
	@Resource(name="teachDiscService")
	private TeachDiscService teachDiscService;
	
	@Resource(name="teacherService")
	private TeacherService teacherService;
	
	@RequestMapping("/viewTeachList")
	public String viewTeachList(HttpSession session,Model model){
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		model.addAttribute("unitId", user.getUnitId());
		model.addAttribute("discId", user.getDiscId());
		return "TeachDiscManage/teachDiscList";
	}
	@RequestMapping("/viewTeachList/viewTeachData")
	@ResponseBody
	public String viewTeachData(HttpServletRequest request){
		
		String unitId = request.getParameter("unitId");//学校代码
		String discId1 = request.getParameter("discId1");//一级学科代码
		String discId2 = request.getParameter("discId2");//二级学科代码
		String teachLoginId = request.getParameter("teachLoginId");//教师用户名
		String teachName = Tools.urlDecoder(request.getParameter("teachName"), "UTF-8");//教师姓名
		setRequestParams(request);
		return JsonConvertor.obj2JSON(teachDiscService.getViewTeachDiscPageVM(teachLoginId,teachName,unitId, discId1, discId2,
				getPageIndex(), getPageSize(),getSidx(),!isAsc()).getGridData());
		
	}
	@RequestMapping("/viewTeachList/selectedTeachData")
	@ResponseBody
	public String selectedTeachData(HttpServletRequest request){
		setRequestParams(request);
		String unitId = request.getParameter("unitId");//学校代码
		String discId = request.getParameter("discId");//学科代码
		String teachLoginId = request.getParameter("teachLoginId");//教师用户名
		String teachName = Tools.urlDecoder(request.getParameter("teachName"), "UTF-8");//教师姓名
		String result = JsonConvertor.obj2JSON(teachDiscService.getTeachDiscPageVM(teachLoginId,teachName,
				unitId, discId, getPageIndex(),getPageSize(), getSidx(),
				!isAsc()).getGridData());
		return result;
		
	}
	@RequestMapping("/viewTeachList/import2TeachDisc/{unitId}/{discId}/{pkValues}")
	@ResponseBody
	public String import2TeachDisc(@PathVariable(value="unitId")String unitId,
			@PathVariable(value="discId")String discId,
			@PathVariable(value="pkValues")List<String> pkValues){
		for(String pkValue:pkValues){
			if(teachDiscService.isTeacherExist(unitId, discId, pkValue)){
				throw new TeachManageException("选择了重复的教师，请重新确认！");
			}
		}
		for(String pkValue: pkValues){
			if(teachDiscService.import2FromUser(unitId, discId, pkValue)==null){
				return "error";
			}
		}
		return "success";
	}
	@RequestMapping("/viewTeachList/delTeachDisc/{unitId}/{discId}/{pkAndSeqNos}")
	@ResponseBody
	public String delTeachDisc(@PathVariable(value="unitId")String unitId,
			@PathVariable(value="discId")String discId,
			@PathVariable(value="pkAndSeqNos")List<String> pkAndSeqNos){
		for(String pkSeqNo:pkAndSeqNos){
			String []pkAndSeqNo = pkSeqNo.split(":");
			String pkValue = pkAndSeqNo[0];
			String seqNo = pkAndSeqNo[1];
			if(teachDiscService.delTeachDisc(unitId, discId, pkValue, seqNo)==null){
				return "error";
			}
		}
		return "success";
	}
	
	@RequestMapping("/viewTeachList/teachDetail/{pkValue}")
	public String viewTeachDetail(@PathVariable(value="pkValue")String pkValue,
			Model model){
		model.addAttribute("teacher",teacherService.geTeacherById(pkValue));
		return "TeachDiscManage/teachDetialDialog";	
	}

}
