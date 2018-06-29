package com.dsep.controller.survey;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dsep.domain.AttachmentHelper;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.SurveyUser;
import com.dsep.entity.enumeration.AttachmentType;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.survey.SurveyUserService;
import com.dsep.util.Dictionaries;
import com.dsep.util.FileOperate;
import com.dsep.util.JsonConvertor;
import com.dsep.util.MySessionContext;
import com.dsep.util.UserSession;
import com.dsep.vm.PageVM;

@Controller
@RequestMapping("InfoMaintain")
public class DiscSurveyUserController {
	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;
	
	@Resource(name = "surveyUserService")
	private SurveyUserService surveyUserService;
	
	@RequestMapping("discQNRInfo")
	public String qview() {
		return "survey/disc_QNR_user_info";
	}
	
	@RequestMapping("discQNRInfo_user_list")
	@ResponseBody
	public String list(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		String unitId = user.getUnitId();
		String discId = user.getDiscId();
		
		
		String sord = request.getParameter("sord");// 排序方式
		String sidx = request.getParameter("sidx");// 排序字段
		int page = Integer.parseInt(request.getParameter("page")); // 当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); // 每页多少数据

		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		PageVM<SurveyUser> quesVM = surveyUserService.retriveUsers(unitId, discId, page,
				pageSize, order_flag, sidx);

		@SuppressWarnings("rawtypes")
		Map m = quesVM.getGridData();
		String json = JsonConvertor.obj2JSON(m);
		return json;
	}
	
	/**
	 * 打开添加用户的对话框
	 * @return
	 */
	@RequestMapping("discQNRInfo_surveyUserAdd")
	public String surveyUserAdd() {
		return "survey/add_survey_user";
	}
	
	
	@RequestMapping("discQNRInfo_surveyUserSave")
	@ResponseBody
	public boolean surveyUserSave(SurveyUser surveyUser) {	
		
		try{
			surveyUserService.saveOrUpdate(surveyUser);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/**
	 * 打开编辑用户的对话框
	 * @return
	 */
	@RequestMapping("discQNRInfo_surveyUserEdit")
	public String surveyUserEdit(String userId, Model model) {
		
		Map<String,String> userTypes = Dictionaries.getSurveyType();
	    model.addAttribute("userTypes", userTypes);
	    
	    Map<String,String> genders = Dictionaries.getGenderType();
	    model.addAttribute("genders", genders);
		
		SurveyUser surveyUser = surveyUserService.getSurveyUser(userId.trim());
		model.addAttribute("surveyUser", surveyUser);
		
		return "survey/edit_survey_user";
	}
	
	@RequestMapping("discQNRInfo_deleteUser")
	@ResponseBody
	public boolean deleteUser(String userId) {
		try{
			surveyUserService.deleteSurveyUser(userId);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	//弹出导出框
	@RequestMapping("discQNRInfo_importUser")
	public String importUser() {
		return "survey/disc_import_survey_user";
	}
	
	/**
	 * 上传附件
	 * @param file
	 * @return
	 */
	@RequestMapping("discQNRInfo_upload")
	@ResponseBody
	public String uploadAttachment(HttpServletRequest request, String jsessionid){
		
		MySessionContext myc= MySessionContext.getInstance();
		HttpSession session = myc.getSession(jsessionid); 
		
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		
		MultipartFile file = FileOperate.getFile(request);
		
		AttachmentHelper ah = attachmentService.getAttachmentHelper(file, user.getId(), AttachmentType.SURVEY, user.getUnitId());
		if(FileOperate.upload(file, ah.getPath(), ah.getStorageName())){
			String attachId = attachmentService.addAttachment(ah.getAttachment());
			String attachPath = attachmentService.getAttachmentPath(attachId);
			surveyUserService.extractSurveyUsersFromExcel(attachPath, user.getUnitId(), user.getDiscId());
			return attachId;
		}	
		else{
			return null;
		}

	}
	
	/**
	 * 下载文件
	 * @param attachId
	 * @return
	 */
	@RequestMapping("discQNRInfo_download/{attachId}")
	@ResponseBody
	public String downLoadAttach(@PathVariable(value="attachId")String attachId){
		String path = attachmentService.getAttachmentPath(attachId);
		return JsonConvertor.obj2JSON(path);
	}
	
	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	@RequestMapping("discQNRInfo_deleteFile")
	@ResponseBody
	public boolean deleteFile(String id, String newsId){
		
		try{
			String path =  attachmentService.getAttachmentPath(id);
			attachmentService.deleteAttachment(id);
			FileOperate.delete(path);
		}catch(Exception e){
			return false;
		}
		return true;
	}
}

