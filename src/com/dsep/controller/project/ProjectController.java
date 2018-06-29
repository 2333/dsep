package com.dsep.controller.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dsep.domain.AttachmentHelper;
import com.dsep.entity.Attachment;
import com.dsep.entity.User;
import com.dsep.entity.enumeration.AttachmentType;
import com.dsep.entity.project.ApplyItem;
import com.dsep.entity.project.ItemExecute;
import com.dsep.entity.project.ItemFunds;
import com.dsep.entity.project.JudgeResult;
import com.dsep.entity.project.PassItem;
import com.dsep.entity.project.SchoolProject;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.project.ApplyItemService;
import com.dsep.service.project.ItemExecuteService;
import com.dsep.service.project.ItemFundsService;
import com.dsep.service.project.JudgeResultService;
import com.dsep.service.project.PassItemService;
import com.dsep.service.project.SchoolProjectService;
import com.dsep.service.project.email.ProjectEmailService;
import com.dsep.util.Dictionaries;
import com.dsep.util.FileOperate;
import com.dsep.util.JsonConvertor;
import com.dsep.util.MySessionContext;
import com.dsep.util.UserSession;
import com.dsep.util.project.ApplyItemStatus;
import com.dsep.vm.PageVM;
import com.dsep.vm.project.ApplyItemVM;
import com.dsep.vm.project.PassItemVM;
import com.dsep.vm.project.SchoolProjectVM;

@Controller
@RequestMapping("project")
public class ProjectController {

	@Resource(name = "schoolProjectService")
	private SchoolProjectService schoolProjectService;

	@Resource(name = "projectEmailService")
	private ProjectEmailService projectEmailService;

	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;

	@Resource(name = "applyItemService")
	private ApplyItemService applyItemService;

	@Resource(name = "passItemService")
	private PassItemService passItemService;

	@Resource(name = "judgeResultService")
	private JudgeResultService judgeResultService;

	@Resource(name = "itemExecuteService")
	private ItemExecuteService itemExecuteService;

	@Resource(name="itemFundsService")
	private ItemFundsService itemFundsService;
	/**
	 * 跳转项目指南页面
	 */
	@RequestMapping("punit")
	public String punit() {
		return "ProjectManagement/Unit_Manage";
	}

	/**
	 * 获取项目指南列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("punit_viewGuides")
	@ResponseBody
	public String viewGuides(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		int pageIndex = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Boolean desc = request.getParameter("sord").equals("desc") ? true
				: false;
		String orderProperName = request.getParameter("sidx");
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		String unitId = user.getUnitId();
		try {
			PageVM<SchoolProjectVM> projects = schoolProjectService.pageVM(
					unitId, pageIndex, pageSize, desc, orderProperName);
			Map<String, Object> m = projects.getGridData();
			String json = JsonConvertor.obj2JSON(m);
			return json;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * 跳转发布指南页面
	 */
	@RequestMapping("punit_create")
	public String punitcreate() {
		return "ProjectManagement/Guide_Publish";
	}

	@RequestMapping("punit_search")
	@ResponseBody
	public String psearch(HttpServletRequest request, HttpSession session) {
		String sord = request.getParameter("sord");//排序方式
		String orderProperName = request.getParameter("sidx");//排序字段
		int pageIndex = Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); //每一页的数据条数
		boolean desc = false;
		if ("desc".equals(sord)) {
			desc = true;
		}
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		String unitId = user.getUnitId();
		String projectName = request.getParameter("projectName");
		String projectType = request.getParameter("projectType");
		int currentState = Integer.parseInt(request
				.getParameter("currentState"));
		try {
			PageVM<SchoolProjectVM> projects = schoolProjectService.pageVM(
					unitId, pageIndex, pageSize, desc, orderProperName,
					projectName, projectType, currentState);
			String json = JsonConvertor.obj2JSON(projects.getGridData());
			return json;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 上传附件
	 * @param file
	 * @return
	 */
	@RequestMapping("punit_fileUpload")
	@ResponseBody
	public String uploadAttachment(HttpServletRequest request, String jsessionid) {

		MySessionContext myc = MySessionContext.getInstance();
		HttpSession session = myc.getSession(jsessionid);

		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();

		MultipartFile file = FileOperate.getFile(request);

		AttachmentHelper ah = attachmentService.getAttachmentHelper(file,
				user.getId(), AttachmentType.PROJECT, user.getUnitId());
		if (FileOperate.upload(file, ah.getPath(), ah.getStorageName())) {
			String attachmentId = attachmentService.addAttachment(ah
					.getAttachment());
			return attachmentId;
		} else {
			return null;
		}
	}

	/**
	 * 下载附件
	 * @param id
	 * @return
	 */
	@RequestMapping("punit_fileDownload")
	@ResponseBody
	public String downloadAttachment(String id) {

		String filePath = attachmentService.getAttachmentPath(id);
		String json = "false";
		try {
			if (FileOperate.ifFileExist(filePath)) {
				json = JsonConvertor.obj2JSON(filePath);
				return json;
			} else {
				return JsonConvertor.obj2JSON(json);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return JsonConvertor.obj2JSON(json);
		}
	}

	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	@RequestMapping("punit_fileDelete")
	@ResponseBody
	public boolean deleteAttachment(String id, String projectId) {

		try {
			String path = attachmentService.getAttachmentPath(id);
			//删除附件实体之前要先删除关联
			if (!StringUtils.isBlank(projectId)) {
				schoolProjectService.deleteAttachment(projectId);
			}
			attachmentService.deleteAttachment(id);
			FileOperate.delete(path);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/*
	 * 保存新建项目指南
	 */
	@RequestMapping("punit_saveAdd")
	@ResponseBody
	public Boolean newsSaveAdd(SchoolProject guide,
			@RequestParam(value = "attachmentId", required = false) String id,
			HttpSession session, HttpServletRequest request)
			throws ParseException {

		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		String unitId = user.getUnitId();
		String projectType = request.getParameter("guide_type");
		String end_date = request.getParameter("end_date");
		String projectName = request.getParameter("guide_name");
		String projectIntro = request.getParameter("guide_content");
		String projectRestrict = request.getParameter("apply_conditions");
		String projectDetail = request.getParameter("defense_matters");
		Date now = new Date();
		Date startTime = new Date(now.getYear(), now.getMonth(), now.getDate());

		guide.setStartDate(startTime);
		guide.setCurrentState(1);
		if (!StringUtils.isBlank(unitId))
			guide.setUnitId(unitId);
		if (!StringUtils.isBlank(projectType))
			guide.setProjectType(projectType);
		if (!StringUtils.isBlank(end_date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date endDate = sdf.parse(end_date);
			guide.setEndDate(endDate);
		}
		if (!StringUtils.isBlank(projectName))
			guide.setProjectName(projectName);
		if (!StringUtils.isBlank(projectIntro))
			guide.setProjectIntro(projectIntro);
		if (!StringUtils.isBlank(projectRestrict))
			guide.setProjectRestrict(projectRestrict);
		if (!StringUtils.isBlank(projectDetail))
			guide.setProjectDetail(projectDetail);
		if (!StringUtils.isBlank(id))//如果附件不空
			guide.setAttachment(attachmentService.getAttachment(id));

		try {
			schoolProjectService.create(guide);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@RequestMapping("punit_publish")
	@ResponseBody
	public Boolean ppublish(String projectId) {
		SchoolProject project = schoolProjectService.getProjectById(projectId);
		project.setCurrentState(2);
		project.setCommitState("是");
		try {
			schoolProjectService.update(project);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * 保存编辑项目指南
	 * @param news
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping("punit_saveEdit")
	@ResponseBody
	public Boolean newsSaveEdit(SchoolProject guide,
			@RequestParam(value = "attachmentId", required = false) String id,
			HttpSession session, HttpServletRequest request)
			throws ParseException {

		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		String unitId = user.getUnitId();
		String projectType = request.getParameter("guide_type");
		String end_date = request.getParameter("end_date");
		String projectName = request.getParameter("guide_name");
		String projectIntro = request.getParameter("guide_content");
		String projectRestrict = request.getParameter("apply_conditions");
		String projectDetail = request.getParameter("defense_matters");
		Date now = new Date();
		Date startTime = new Date(now.getYear(), now.getMonth(), now.getDate());

		guide.setStartDate(startTime);
		guide.setCurrentState(1);
		if (!StringUtils.isBlank(unitId))
			guide.setUnitId(unitId);
		if (!StringUtils.isBlank(projectType))
			guide.setProjectType(projectType);
		if (!StringUtils.isBlank(end_date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date endDate = sdf.parse(end_date);
			guide.setEndDate(endDate);
		}
		if (!StringUtils.isBlank(projectName))
			guide.setProjectName(projectName);
		if (!StringUtils.isBlank(projectIntro))
			guide.setProjectIntro(projectIntro);
		if (!StringUtils.isBlank(projectRestrict))
			guide.setProjectRestrict(projectRestrict);
		if (!StringUtils.isBlank(projectDetail))
			guide.setProjectDetail(projectDetail);
		if (!StringUtils.isBlank(id))//如果附件不空
			guide.setAttachment(attachmentService.getAttachment(id));

		try {
			schoolProjectService.update(guide);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/*
	 * 删除项目指南
	 */
	@RequestMapping("punit_delete")
	@ResponseBody
	public boolean guidedelete(String projectId) {
		try {
			//获取附件路径
			String attachmentId = schoolProjectService
					.getAttachmentId(projectId);
			if (StringUtils.isNotBlank(attachmentId)) {
				String path = attachmentService.getAttachmentPath(attachmentId);
				FileOperate.delete(path);//删除本地文件
			}
			schoolProjectService.delete(projectId);

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 查看项目指南
	 */
	@RequestMapping("punit_check")
	public String pcheck(String projectId, Model model) {
		SchoolProject project = schoolProjectService.getProjectById(projectId);
		SchoolProjectVM vm = new SchoolProjectVM(project);
		model.addAttribute("project", vm);
		return "ProjectManagement/Guide_Check";
	}

	//@RequestMapping("papply_check")
	public String pcheckinfo() {
		return "ProjectManagement/Guide_Check";
	}

	/**
	 * 编辑项目指南
	 */
	@RequestMapping("punit_edit")
	public String pedit(String projectId, Model model) {
		SchoolProject project = schoolProjectService.getProjectById(projectId);
		SchoolProjectVM vm = new SchoolProjectVM(project);
		model.addAttribute("project", vm);
		return "ProjectManagement/Guide_Edit";
	}

	/**
	 * 跳转执行管理页面
	 */
	@RequestMapping("punit_execute")
	public String pexecute(String projectId, Model model) {
		SchoolProject project = schoolProjectService.getProjectById(projectId);
		SchoolProjectVM vm = new SchoolProjectVM(project);
		model.addAttribute("project", vm);
		return "ProjectManagement/Execute_Manage";
	}

	/**
	 * 跳转申报管理页面
	 */
	@RequestMapping("punit_approval")
	public String papproval(String projectId, Model model) {
		SchoolProject project = schoolProjectService.getProjectById(projectId);
		SchoolProjectVM vm = new SchoolProjectVM(project);
		model.addAttribute("project", vm);
		return "ProjectManagement/Approval_Manage";
	}

	@RequestMapping("punit_viewApplyItems/{projectId}")
	@ResponseBody
	public String viewApplyItems(
			@PathVariable(value = "projectId") String projectId,
			HttpServletRequest request) {
		int pageIndex = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Boolean desc = request.getParameter("sord").equals("desc") ? true
				: false;
		String orderProperName = request.getParameter("sidx");
		//orderProperName = "ITEM_NAME";
		//projectId = "1CAD4660F47544CDB39AD63C6FD9452A";
		try {
			PageVM<ApplyItemVM> items = applyItemService
					.getApplyItemsByProjectId(projectId,
							ApplyItemStatus.COMMIT, pageIndex, pageSize, desc,
							orderProperName);
			Map<String, Object> m = items.getGridData();
			String json = JsonConvertor.obj2JSON(m);
			return json;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@RequestMapping("punit_viewPassItems/{projectId}")
	@ResponseBody
	public String viewPassItems(
			@PathVariable(value = "projectId") String projectId,
			HttpServletRequest request) {
		int pageIndex = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Boolean desc = request.getParameter("sord").equals("desc") ? true
				: false;
		String orderProperName = request.getParameter("sidx");
		//orderProperName = "ITEM_NAME";
		//projectId = "1CAD4660F47544CDB39AD63C6FD9452A";
		try {
			PageVM<PassItemVM> items = passItemService.retrive(
					projectId, null, null, null, null, pageIndex, pageSize, desc, orderProperName);
			Map<String, Object> m = items.getGridData();
			String json = JsonConvertor.obj2JSON(m);
			return json;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@RequestMapping("punit_closeProject")
	@ResponseBody
	public Boolean closeProject(String projectId) {
		Boolean result = schoolProjectService.closeProject(projectId);
		return result;
	}

	@RequestMapping("punit_restartProject")
	@ResponseBody
	public Boolean restartProject(String projectId) {
		Boolean result = schoolProjectService.restartProject(projectId);
		return result;
	}

	@RequestMapping("punit_inputResult")
	public String inputResult() {
		return "ProjectManagement/Result_Input";
	}

	@RequestMapping("punit_inputFinalResult")
	public String inputFinalResult() {
		return "ProjectManagement/FinalResult_Input";
	}

	@RequestMapping("punit_editResult")
	public String editResult(String itemId, Model model) {
		JudgeResult judgeResult = judgeResultService.getResultByItemId(itemId);
		model.addAttribute("judgeResult", judgeResult);
		return "ProjectManagement/Result_Edit";
	}

	@RequestMapping("punit_editFinalResult")
	public String editFinalResult(String itemId, Model model) {
		ItemExecute finalResult = itemExecuteService.getResultByItemId(itemId);
		model.addAttribute("finalResult", finalResult);
		return "ProjectManagement/FinalResult_Edit";
	}

	@RequestMapping("punit_resultPublish")
	@ResponseBody
	public Boolean resultPublish(String projectId) {
		return schoolProjectService.publishResult(projectId);
	}

	@RequestMapping("punit_finalResultPublish")
	@ResponseBody
	public Boolean finalResultPublish(String projectId) {
		return schoolProjectService.publishFinalResult(projectId);
	}

	@RequestMapping("punit_saveResult")
	@ResponseBody
	public Boolean saveResult(JudgeResult judgeResult,
			@RequestParam(value = "itemId", required = false) String itemId) {
		JudgeResult existResult = judgeResultService.getResultByItemId(itemId);
		if (existResult != null) {
			try {
				ApplyItem applyItem = applyItemService.getApplyItemById(itemId);
				applyItem.setCurrentState(3);
				applyItemService.update(applyItem);
				existResult.setApplyItem(applyItem);
				existResult.setIsAccept(judgeResult.getIsAccept());
				existResult.setOpinion(judgeResult.getOpinion());
				existResult.setScore(judgeResult.getScore());
				judgeResultService.update(existResult);
			} catch (Exception ex) {
				return false;
			}
			return true;
		} else {
			ApplyItem applyItem = applyItemService.getApplyItemById(itemId);
			applyItem.setCurrentState(3);
			applyItemService.update(applyItem);
			judgeResult.setApplyItem(applyItem);
			String result = judgeResultService.create(judgeResult);
			if (!StringUtils.isBlank(result))
				return true;
			return false;
		}
	}

	@RequestMapping("punit_saveFinalResult")
	@ResponseBody
	public Boolean saveResult(ItemExecute finalResult,
			@RequestParam(value = "itemId", required = false) String itemId) {
		PassItem passItem = passItemService.getPassItemById(itemId);
		passItem.setItemState(2);
		passItemService.update(passItem);
		finalResult.setPassItem(passItem);
		String result = itemExecuteService.create(finalResult);
		if (!StringUtils.isBlank(result))
			return true;
		return false;
	}

	@RequestMapping("punit_updateResult")
	@ResponseBody
	public Boolean updateResult(HttpServletRequest request,
			@RequestParam(value = "itemId", required = false) String itemId) {
		ApplyItem applyItem = applyItemService.getApplyItemById(itemId);
		JudgeResult judgeResult = judgeResultService.getResultById(request
				.getParameter("id"));
		judgeResult.setApplyItem(applyItem);
		judgeResult.setIsAccept(request.getParameter("isAccept"));
		judgeResult.setOpinion(request.getParameter("opinion"));
		judgeResult.setScore(request.getParameter("score"));
		judgeResultService.update(judgeResult);
		return true;
	}

	@RequestMapping("punit_updateFinalResult")
	@ResponseBody
	public Boolean updateFinalResult(HttpServletRequest request,
			@RequestParam(value = "itemId", required = false) String itemId) {
		PassItem passItem = passItemService.getPassItemById(itemId);
		String resultId = request.getParameter("id");
		ItemExecute finalResult = itemExecuteService.getResultById(resultId);
		finalResult.setScore(request.getParameter("score"));
		finalResult.setTxtRecord(request.getParameter("txtRecord"));
		finalResult.setPassItem(passItem);
		itemExecuteService.update(finalResult);
		return true;
	}

	@RequestMapping("punit_passItem_check/{passItemId}")
	public String ppassItemCheck(
			@PathVariable(value = "passItemId") String passItemId, Model model) {
		PassItem passItem = passItemService.getPassItemById(passItemId);
		model.addAttribute("passItem", passItem);
		return "ProjectManagement/PassItem_Check";
	}

	@RequestMapping("punit_closeMedium")
	@ResponseBody
	public Boolean pcloseMedium(String projectId) {
		return schoolProjectService.setProjectState(projectId, 6) > 0;
	}

	@RequestMapping("punit_closeFinal")
	@ResponseBody
	public Boolean pcloseFinal(String projectId) {
		return schoolProjectService.setProjectState(projectId, 8) > 0;
	}

	/**
	 * 跳转查看项目页面
	 */
	@RequestMapping("punit_approval_view")
	public String pview() {
		return "ProjectManagement/Project_Check";
	}

	/**
	 * 打开发送邮件配置页面
	 */
	@RequestMapping("punit_show_emailConfig/{projectId}")
	public String showEmailConfig(Model model,
			@PathVariable(value = "projectId") String projectId) {
		model.addAttribute("projectId", projectId);
		model.addAttribute("status", Dictionaries
				.getSchoolProjectState(schoolProjectService.getProjectById(
						projectId).getCurrentState() + 1));
		return "ProjectManagement/Dialog_SendEmail";
	}

	@RequestMapping("punit_sendEmail")
	@ResponseBody
	public Boolean sendEmail(@RequestBody EmailConfig emailConfigData,
			HttpServletRequest request) {

		@SuppressWarnings("deprecation")
		String basePath = request.getRealPath("/");

		List<PassItem> list = new ArrayList<PassItem>();
		list.addAll(schoolProjectService.getProjectById(
				emailConfigData.getProjectId()).getPassItems());

		Attachment attachment = attachmentService.getAttachment(emailConfigData
				.getAttachmentId());
		String attachmentPath = null;
		String attachmentName  = null;
		
		if (attachment != null) {
			attachmentPath = attachment.getPath();
			attachmentName = attachment.getName();
		}
		

		// 以下需要删除
		//String attachmentPath = File.separator + "home" + File.separator + "jc"
		//		+ File.separator + "nohup.out";
		
		emailConfigData.setAttachmentName(attachmentName);
		try {
			projectEmailService.sendEmail(basePath, list,
					emailConfigData.getProjectId(),
					emailConfigData.getEmailTitle(),
					emailConfigData.getContent(), attachmentPath,
					emailConfigData.getAttachmentName());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 上传附件
	 * @param file
	 * @return
	 */
	@RequestMapping("punit_emailFileUpload")
	@ResponseBody
	public String uploadEmailAttachment(HttpServletRequest request,
			String jsessionid) {

		MySessionContext myc = MySessionContext.getInstance();
		HttpSession session = myc.getSession(jsessionid);

		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();

		MultipartFile file = FileOperate.getFile(request);

		AttachmentHelper ah = attachmentService.getAttachmentHelper(file,
				user.getId(), AttachmentType.PROJECT, user.getUnitId());
		if (FileOperate.upload(file, ah.getPath(), ah.getStorageName())) {
			String attachmentId = attachmentService.addAttachment(ah
					.getAttachment());
			return attachmentId;
		} else {
			return null;
		}
	}

	/**
	 * 下载附件
	 * @param id
	 * @return
	 */
	@RequestMapping("punit_emailFileDownload")
	@ResponseBody
	public String downloadEmailAttachment(String id) {

		String filePath = attachmentService.getAttachmentPath(id);
		String json = "false";
		try {
			if (FileOperate.ifFileExist(filePath)) {
				json = JsonConvertor.obj2JSON(filePath);
				return json;
			} else {
				return JsonConvertor.obj2JSON(json);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return JsonConvertor.obj2JSON(json);
		}
	}

	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	@RequestMapping("punit_emailFileDelete")
	@ResponseBody
	public boolean deleteEmailAttachment(String id, String projectId) {

		try {
			String path = attachmentService.getAttachmentPath(id);
			//删除附件实体之前要先删除关联
			if (!StringUtils.isBlank(projectId)) {
				//applyItemService.deleteAttachment(projectId);
			}
			attachmentService.deleteAttachment(id);
			FileOperate.delete(path);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 跳转项目申报页面
	 */
	//@RequestMapping("papply")
	public String papply() {
		return "ProjectManagement/Project_Apply";
	}

	/**
	 * 跳转项目创建页面
	 */
	//@RequestMapping("papply_create")
	public String pcreate() {
		return "ProjectManagement/Project_Edit";
	}

	/**
	 * 跳转我的项目页面
	 */
	@RequestMapping("pproject")
	public String pproject(HttpSession session) {
		UserSession userSession = new UserSession(session);
		if(userSession!=null){
			return "ProjectManagement/My_Project";
		}
		else 
			return null;
	}

	/**
	 * 跳转项目编辑页面
	 */
	@RequestMapping("pproject_edit")
	public String pedit() {
		return "ProjectManagement/Project_Edit";
	}

	/**
	 * 跳转进度管理页面
	 */
	@RequestMapping("pproject_progress")
	public String pprogress(String item_id, Model m) {
		m.addAttribute("itemId", item_id);
		return "ProjectManagement/Progress_Manage";
	}

	/**
	 * 跳转进度查看页面
	 */
	@RequestMapping("pproject_checkProgress")
	public String pcheckProgress(String item_id, Model m) {
		m.addAttribute("itemId", item_id);
		m.addAttribute("itemState", "已结束");
		return "ProjectManagement/Progress_Manage";
	}

	/**
	 * 跳转进度查看页面
	 */
	@RequestMapping("punit_checkProgress")
	public String punitCheckProgress(String item_id, Model m) {
		m.addAttribute("itemId", item_id);
		m.addAttribute("itemState", "已结束");
		return "ProjectManagement/Progress_Manage";
	}

	@RequestMapping("pproject_checkResult")
	public String pcheckResult(String item_id, Model m) {
		ItemExecute finalResult = itemExecuteService.getResultByItemId(item_id);
		m.addAttribute("finalResult", finalResult);
		m.addAttribute("isCheck", "是");
		return "ProjectManagement/FinalResult_Edit";
	}
	@ResponseBody
	@RequestMapping("punit_itemFundsList")
	public String initFundsUsingGrid(String itemId,
			HttpServletRequest request,HttpServletResponse response){
		String sord = request.getParameter("sord");// 排序顺序
		String sidx = request.getParameter("sidx");
		//String searchString = request.getParameter("searchString");// 查询条件值
		//String filter = request.getParameter("filters");
		int pageIndex = Integer.parseInt(request.getParameter("page")); // 当前页
		int pageSize  = Integer.parseInt(request.getParameter("rows")); // 每页多少数据
		String item_id=itemId;
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		PageVM<ItemFunds> itemFunds = null;
		try {
			itemFunds =itemFundsService.page(item_id,pageIndex, pageSize, order_flag, sidx);
			Map<String, Object> map = itemFunds.getGridData();
			String json = JsonConvertor.obj2JSON(map);
			return json;
		} catch ( Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

class EmailConfig {
	private String projectId;
	private String emailTitle;
	private String startTime;
	private String attachmentName;
	private String content;
	private String attachmentId;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

}
