package com.dsep.controller.project;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.dsep.entity.Teacher;
import com.dsep.entity.Unit;
import com.dsep.entity.User;
import com.dsep.entity.enumeration.AttachmentType;
import com.dsep.entity.project.ApplyItem;
import com.dsep.entity.project.SchoolProject;
import com.dsep.entity.project.TeamMember;
import com.dsep.service.base.AttachmentService;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.base.UnitService;
import com.dsep.service.project.ApplyItemService;
import com.dsep.service.project.SchoolProjectService;
import com.dsep.service.project.TeamMemberService;
import com.dsep.service.rbac.TeacherService;
import com.dsep.util.FileOperate;
import com.dsep.util.JsonConvertor;
import com.dsep.util.MySessionContext;
import com.dsep.util.UserSession;
import com.dsep.util.project.ApplyItemStatus;
import com.dsep.vm.PageVM;
import com.dsep.vm.project.ApplyItemVM;
import com.dsep.vm.project.SchoolProjectVM;

@Controller
@RequestMapping("project")
public class ApplyItemController {
	/**
	 * 跳转项目申报页面
	 */
	@RequestMapping("papply")
	public String papply(HttpSession session, Model model) {
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		List<String> projectTypes = schoolProjectService
				.getUniqueProjectTypes(user.getUnitId());
		model.addAttribute("projectTypes", projectTypes);
		return "ProjectManagement/Project_Apply";
	}

	/**
	 * 跳转项目创建页面
	 */
	@RequestMapping("papply_create/{id}")
	public String pcreate(HttpSession session, Model model,
			@PathVariable(value = "id") String id) {
		SchoolProject project = schoolProjectService.getProjectById(id);
		model.addAttribute("projectId", id);
		model.addAttribute("projectName", project.getProjectName());

		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();

		List<Unit> units = unitService.getAllUnits();
		model.addAttribute("units", units);

		model.addAttribute("userUnitId", user.getUnitId());
		model.addAttribute("userDiscId", user.getDiscId());
		model.addAttribute("userId", user.getId());

		model.addAttribute("discs", disciplineService
				.getJoinDisciplineMapByUnitId(user.getUnitId()));

		List<Teacher> list = teacherService
				.getTeachersByDiscId12(null, null, user.getUnitId(),
						user.getDiscId(), null, 1, 1000, true, "zjbh");
		Map<String, Teacher> map = new HashMap<String, Teacher>();
		for (Teacher t : list) {
			map.put(t.getId(), t);
		}
		model.addAttribute("teachersMap", map);

		return "ProjectManagement/Project_Edit";
	}

	/**
	 * 跳转我的项目页面
	 */
	@RequestMapping("papply_toMyItemPage")
	public String toMyItemPage() {
		return "ProjectManagement/My_Project";
	}

	/**
	 * 跳转项目创建页面
	 */
	@RequestMapping("papply_edit_applyItem/{applyItemId}")
	public String editApplyItem(HttpSession session, Model model,
			@PathVariable(value = "applyItemId") String applyItemId) {
		ApplyItem item = applyItemService.getApplyItemById(applyItemId);

		model.addAttribute("applyItem", item);

		SchoolProject project = item.getSchoolProject();
		model.addAttribute("projectId", project.getId());
		model.addAttribute("projectName", project.getProjectName());

		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();

		List<Unit> units = unitService.getAllUnits();
		model.addAttribute("units", units);
		return "ProjectManagement/Project_Edit";
	}

	/**
	 * 获得所有的项目类别
	 */
	@RequestMapping("papply_getProjectTypes")
	public void getProjectTypes(HttpSession session, Model model) {

	}

	@RequestMapping("papply_addTeachersPage")
	public String addTeachersPage() {
		return "ProjectManagement/Add_Teachers";
	}

	/**
	 * 获得学校项目list
	 */
	@RequestMapping("papply_getProjects")
	@ResponseBody
	public String getProjects(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		String sord = request.getParameter("sord");// 排序顺序
		String sidx = request.getParameter("sidx");// 排序字段
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		String unitId = user.getUnitId();
		int pageIndex = Integer.parseInt(request.getParameter("page")); // 当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); // 每页多少数据
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		try {
			PageVM<SchoolProjectVM> projects = schoolProjectService.pageVM(
					unitId, pageIndex, pageSize, order_flag, sidx);
			Map<String, Object> m = projects.getGridData();
			String json = JsonConvertor.obj2JSON(m);
			return json;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@RequestMapping("papply_showTeamMembers/{applyItemId}")
	@ResponseBody
	public String showTeamMembers(HttpSession session, Model model,
			@PathVariable(value = "applyItemId") String applyItemId,
			HttpServletRequest request) {
		String sord = request.getParameter("sord");// 排序顺序
		String sidx = request.getParameter("sidx");// 排序字段
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		String unitId = user.getUnitId();
		int pageIndex = Integer.parseInt(request.getParameter("page")); // 当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); // 每页多少数据
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}

		try {
			PageVM<TeamMember> members = teamMemberService.page(
					applyItemId, pageIndex, pageSize, order_flag, sidx);
			Map<String, Object> m = members.getGridData();
			String json = JsonConvertor.obj2JSON(m);
			return json;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/*
	 * 保存新建项目指南
	 */
	@RequestMapping("papply_saveApply")
	@ResponseBody
	public Boolean saveApply(@RequestBody ApplyItemInfoFromJSP info,
			@RequestParam(value = "attachmentId", required = false) String id,
			HttpSession session, HttpServletRequest request)
			throws ParseException {

		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		String unitId = user.getUnitId();
		String discId = user.getDiscId();

		Date now = new Date();
		Date startTime = new Date(now.getYear(), now.getMonth(), now.getDate());
		ApplyItem item = null;
		String applyItemId = info.getApplyItemId();
		if (applyItemId != null)
			item = applyItemService.getApplyItemById(info.getApplyItemId());
		else
			item = new ApplyItem();
		if (!StringUtils.isBlank(unitId))
			item.setUnitId(unitId);
		if (!StringUtils.isBlank(discId))
			item.setDiscId(discId);

		// 设置UserId
		item.setItemName(info.getItemName());
		item.setItemTarget(info.getItemTarget());
		item.setItemContent(info.getItemContent());
		item.setAttachment(attachmentService.getAttachment(info
				.getAttachmentId()));

		SchoolProject project = schoolProjectService.getProjectById(info
				.getProjectId());
		Set<TeamMember> members = new HashSet<TeamMember>();
		for (int i = 0; i < info.getTeamNames().size(); i++) {
			TeamMember member = new TeamMember();
			//member.setId(GUID.get());
			member.setName(info.getTeamNames().get(i));
			member.setEmail(info.getTeamEmails().get(i));
			member.setInfo(info.getTeamInfos().get(i));
			member.getApplyItems().add(item);
			if (info.getTeamEmails().get(i).equals(info.getPrincipalEmail())) {
				member.setIsPrincipal(true);
			} else {
				member.setIsPrincipal(false);
			}
			item.getTeamMembers().add(member);
			members.add(member);
		}
		// 设置关联
		item.setSchoolProject(project);
		item.getSchoolProject().getApplyItems().add(item);

		item.setUserId(user.getId());
		item.setDiscId(discId);
		item.setContactInfo(info.getPrincipalEmail());
		item.setFunds(info.getFunds());

		// 状态为保存，未提交 
		if (applyItemId == null)
			item.setCurrentState(ApplyItemStatus.NOT_COMMIT.toInt());

		applyItemService.create(item, members);

		return true;
	}

	/**
	 * 学校账户：查看申请项目
	 */
	@RequestMapping("punit_applyItem_check/{applyItemId}")
	public String pcheck(
			@PathVariable(value = "applyItemId") String applyItemId, Model model) {
		ApplyItem applyItem = applyItemService.getApplyItemById(applyItemId);
		model.addAttribute("applyItem", applyItem);
		return "ProjectManagement/ApplyItem_Check";
	}

	/**
	 * 教师账户：查看申请项目
	 */
	@RequestMapping("pproject_applyItem_check/{applyItemId}")
	public String projectCheck(
			@PathVariable(value = "applyItemId") String applyItemId, Model model) {
		ApplyItem applyItem = applyItemService.getApplyItemById(applyItemId);
		model.addAttribute("applyItem", applyItem);
		return "ProjectManagement/ApplyItem_Check";
	}

	/**
	 * 查看项目指南
	 */
	@RequestMapping("papply_showProjects")
	public String showProject(String projectId, Model model) {
		SchoolProject project = schoolProjectService.getProjectById(projectId);
		SchoolProjectVM vm = new SchoolProjectVM(project);
		model.addAttribute("project", vm);
		return "ProjectManagement/Guide_Check";
	}

	/**
	 * 查看未提交的申报项目
	 */
	@RequestMapping("papply_viewUnpublishApplyItems/{projectId}")
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
							ApplyItemStatus.NOT_COMMIT, pageIndex, pageSize,
							desc, orderProperName);
			Map<String, Object> m = items.getGridData();
			String json = JsonConvertor.obj2JSON(m);
			return json;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * 查看未提交的申报项目
	 */
	@RequestMapping("pproject_viewUnpublishApplyItems")
	@ResponseBody
	public String viewApplyItems2(HttpSession session,
			HttpServletRequest request) {
		int pageIndex = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Boolean desc = request.getParameter("sord").equals("desc") ? true
				: false;
		String orderProperName = request.getParameter("sidx");
		//orderProperName = "ITEM_NAME";
		//projectId = "1CAD4660F47544CDB39AD63C6FD9452A";
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		try {
			PageVM<ApplyItemVM> items = applyItemService.retrive(null,
					ApplyItemStatus.NOT_COMMIT, null, null, user.getId(),
					pageIndex, pageSize, desc, orderProperName);
			Map<String, Object> m = items.getGridData();
			String json = JsonConvertor.obj2JSON(m);
			return json;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * 查看未提交的申报项目
	 */
	@RequestMapping("pproject_showRejectApplyItems")
	@ResponseBody
	public String viewRejectApplyItems(HttpSession session,
			HttpServletRequest request) {
		int pageIndex = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Boolean desc = request.getParameter("sord").equals("desc") ? true
				: false;
		String orderProperName = request.getParameter("sidx");
		//orderProperName = "ITEM_NAME";
		//projectId = "1CAD4660F47544CDB39AD63C6FD9452A";
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();
		try {
			PageVM<ApplyItemVM> items = applyItemService.retrive(null,
					ApplyItemStatus.REJECT, null, null, user.getId(),
					pageIndex, pageSize, desc, orderProperName);
			Map<String, Object> m = items.getGridData();
			String json = JsonConvertor.obj2JSON(m);
			return json;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * 教师提交applyItem至学校
	 */
	@RequestMapping("pproject_commitApplyItemToSchool/{applyItemId}")
	@ResponseBody
	public String commitApplyItemToSchool(
			@PathVariable(value = "applyItemId") String applyItemId,
			HttpSession session, HttpServletRequest request) {
		ApplyItem applyItem = applyItemService.getApplyItemById(applyItemId);
		SchoolProject project = applyItem.getSchoolProject();
		String state = project.getCommitState();
		if (state.equals("否"))
			return "该项目已经关闭，拒绝提交";
		Integer val = applyItemService.commitApplyItemToSchool(applyItemId);
		if (1 == val) {
			return "success";
		} else {
			return "failure";
		}
	}

	@RequestMapping("pproject_deleteApplyItem/{itemId}")
	@ResponseBody
	public Boolean deleteApplyItem(@PathVariable(value = "itemId") String itemId) {
		try {
			applyItemService.deleteById(itemId);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 查看未提交的申报项目
	 */
	@RequestMapping("papply_getProjectByQuery/{projectType}")
	@ResponseBody
	public String getProjectByQuery(
			@PathVariable(value = "projectType") String projectType,
			HttpSession session, HttpServletRequest request) {
		String sord = request.getParameter("sord");// 排序顺序
		String sidx = request.getParameter("sidx");// 排序字段
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		String unitId = user.getUnitId();
		int pageIndex = Integer.parseInt(request.getParameter("page")); // 当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); // 每页多少数据
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		if ("全选".equals(projectType)) {
			projectType = null;
		}
		try {
			PageVM<SchoolProjectVM> projects = schoolProjectService.pageVM(
					unitId, projectType, pageIndex, pageSize, order_flag, sidx);
			Map<String, Object> m = projects.getGridData();
			String json = JsonConvertor.obj2JSON(m);
			return json;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * 查看未提交的申报项目
	 */
	@RequestMapping("papply_getDiscByUnit/{unitId}")
	@ResponseBody
	public Map<String, String> getDiscByUnit(
			@PathVariable(value = "unitId") String unitId) {
		return disciplineService.getJoinDisciplineMapByUnitId(unitId);
	}

	/**
	 * 查看未提交的申报项目
	 */
	@RequestMapping("papply_getTeachersByUnitIdAndDiscId/{unitId}/{discId}")
	@ResponseBody
	public Map<String, Teacher> getTeachersByDisc(
			@PathVariable(value = "discId") String discId,
			@PathVariable(value = "unitId") String unitId) {
		List<Teacher> list = teacherService.getTeachersByDiscId12(null, null,
				unitId, discId, null, 1, 1000, true, "zjbh");
		Map<String, Teacher> map = new HashMap<String, Teacher>();
		for (Teacher t : list) {
			map.put(t.getId(), t);
		}
		return map;
	}

	/**
	 * 上传附件
	 * @param file
	 * @return
	 */
	@RequestMapping("papply_fileUpload")
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
	@RequestMapping("papply_fileDownload")
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
	@RequestMapping("papply_fileDelete")
	@ResponseBody
	public boolean deleteAttachment(String id, String projectId) {

		try {
			String path = attachmentService.getAttachmentPath(id);
			//删除附件实体之前要先删除关联
			if (!StringUtils.isBlank(projectId)) {
				applyItemService.deleteAttachment(projectId);
			}
			attachmentService.deleteAttachment(id);
			FileOperate.delete(path);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Resource(name = "schoolProjectService")
	private SchoolProjectService schoolProjectService;

	@Resource(name = "teacherService")
	private TeacherService teacherService;

	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;

	@Resource(name = "applyItemService")
	private ApplyItemService applyItemService;

	@Resource(name = "disciplineService")
	private DisciplineService disciplineService;

	@Resource(name = "unitService")
	private UnitService unitService;
	
	@Resource(name = "teamMemberService")
	private TeamMemberService teamMemberService;
}

class ApplyItemInfoFromJSP {
	private String applyItemId;
	private String projectId;
	private String itemName;
	private Double funds;
	private String principalName;
	private String principalEmail;
	private List<String> teamNames;
	private List<String> teamEmails;
	private List<String> teamInfos;
	private String itemTarget;
	private String itemContent;
	private String attachmentId;

	public String getApplyItemId() {
		return applyItemId;
	}

	public void setApplyItemId(String applyItemId) {
		this.applyItemId = applyItemId;
	}

	public String getPrincipalEmail() {
		return principalEmail;
	}

	public void setPrincipalEmail(String principalEmail) {
		this.principalEmail = principalEmail;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getFunds() {
		return funds;
	}

	public void setFunds(Double funds) {
		this.funds = funds;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public List<String> getTeamNames() {
		return teamNames;
	}

	public void setTeamNames(List<String> teamNames) {
		this.teamNames = teamNames;
	}

	public List<String> getTeamEmails() {
		return teamEmails;
	}

	public void setTeamEmails(List<String> teamEmails) {
		this.teamEmails = teamEmails;
	}

	public List<String> getTeamInfos() {
		return teamInfos;
	}

	public void setTeamInfos(List<String> teamInfos) {
		this.teamInfos = teamInfos;
	}

	public String getItemTarget() {
		return itemTarget;
	}

	public void setItemTarget(String itemTarget) {
		this.itemTarget = itemTarget;
	}

	public String getItemContent() {
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

}