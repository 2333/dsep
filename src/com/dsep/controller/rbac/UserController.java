package com.dsep.controller.rbac;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.common.exception.BusinessException;
import com.dsep.common.exception.SessionNullException;
import com.dsep.common.logger.LoggerTool;
import com.dsep.domain.MenuTreeNode;
import com.dsep.entity.Right;
import com.dsep.entity.User;
import com.dsep.service.base.NewsService;
import com.dsep.service.rbac.RoleService;
import com.dsep.service.rbac.UserService;
import com.dsep.util.Configurations;
import com.dsep.util.Dictionaries;
import com.dsep.util.JsonConvertor;
import com.dsep.util.TextConfiguration;
import com.dsep.util.UserSession;
import com.dsep.util.VerificationCode;
import com.dsep.vm.NewsVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.UserVM;

@Controller
@RequestMapping("rbac")
public class UserController {

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "roleService")
	private RoleService roleService;
	
	@Resource(name = "newsService")
	private NewsService newsService;

	//private static Logger logger= Logger.getLogger(UserController.class); 
	@Resource(name="loggerTool")
	private LoggerTool logger;
	
	@Resource(name="configuration")
	private Configurations configurations;
	@Resource(name="textconfiguration")
	private TextConfiguration textConfiguration;
	@RequestMapping("tologin")
	public String login(HttpServletRequest request,HttpSession session, Model model){
		UserSession userSession = new UserSession(session);
		if(userSession.getCurrentUser()!=null){
			List<NewsVM> list = newsService.getLatestNews(6);
			model.addAttribute("configurations", configurations);
			model.addAttribute("textConfiguration", textConfiguration);
			model.addAttribute("list", list);
			return "index";
		}else{
			throw new SessionNullException();
		}
		
	}
	
	@RequestMapping("login")
	@ResponseBody
	public String toLogin(Model model,String loginId,String password, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		User user= userService.getUserByLoginId(loginId);
		if(user!=null)
		{
			if(password!=null&&password.equals(user.getPassword()))
			{
				//更新用户登录信息-ip,time
				userService.updateLoginInfo(request.getRemoteAddr(), new Date(), user.getId());
				
				//添加用户Session信息
				UserSession us = new UserSession(session);
				us.setCurrentUser(user);
				
				//添加用户菜单权限和禁用权限到Session
				List<Right> menuRights = userService.getUserRightsByType(user.getId(), "1");
				List<Right> forbidRights = userService.getUserRightsByType(user.getId(), "2");
				us.setMenuRights(menuRights);
				us.setForbidRights(forbidRights);
				
				//添加菜单树到Session
				List<MenuTreeNode> tree = userService.getUserRightTree(user.getLoginId());
				session.setAttribute("tree",tree);
				
				return "success";
			}
			else{
				return "error";
			}
		}
		throw new BusinessException("错误操作！");
	}
	
	//验证码验证
	@RequestMapping("checkcode")
	@ResponseBody
	public String checkcode(HttpServletRequest request,HttpServletResponse response){
		String inputCode = request.getParameter("user_checknum");	//用户输入的验证码
		HttpSession session = request.getSession();
		String sRand = (String) session.getAttribute("randCheckCode");	//正确的验证码
		boolean isRight = inputCode.equalsIgnoreCase(sRand);
		String json = JsonConvertor.obj2JSON(isRight);
		return json;
	}

	@RequestMapping("user")
	public String user() {
		return "rbac/user";
	}

	@RequestMapping("userlist")
	@ResponseBody
	public String userlist(HttpServletRequest request,
			HttpServletResponse response) {
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		PageVM<UserVM> userVm = userService.getUsers(page, pageSize, order_flag, sidx);
		Map m = userVm.getGridData();
		String json = JsonConvertor.obj2JSON(m);
		return json;
	}

	@RequestMapping("useradd")
	public String useradd(Model model) {
		Map<String,String> userTypes = Dictionaries.getUserType();
	    model.addAttribute("userTypes", userTypes);
		return "rbac/addUserInfo";
	}

	@RequestMapping("useredit")
	public String useredit(String userId, Model model) {
		User user = userService.getUser(userId);
		model.addAttribute("user", user);
		Map<String,String> userTypes = Dictionaries.getUserType();
		model.addAttribute("userTypes", userTypes);
		return "rbac/editUserInfo";
	}

	@RequestMapping("userroletree")
	@ResponseBody
	public String userroletree(String userId) {
		String jsonString = JsonConvertor.obj2JSON(roleService.getroleTreeVMs(userId));
		return jsonString;
	}

	@RequestMapping("userallroletree")
	@ResponseBody
	public String userallroletree() {
		String jsonString = JsonConvertor.obj2JSON(roleService.getroleTreeVMs(""));
		return jsonString;
	}

	@RequestMapping("usersaveedit")
	@ResponseBody
	public boolean usersaveedit(User user,@RequestParam(value = "userRoles", required = false) List<String> userRoles) {
		 try{
			 userService.UpdateUser(user, userRoles);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@RequestMapping("usersaveadd")
	@ResponseBody
	public boolean usersaveadd(User user,@RequestParam(value = "userRoles", required = false) List<String> userRoles) {	
		try{
			userService.newUser(user, userRoles);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@RequestMapping("userdelete")
	@ResponseBody
	public boolean userdelete(String userId) {
		try{
			userService.deleteUser(userId);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@RequestMapping("generatecode")
	public void generateCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		VerificationCode.verify(request, response);
	}

	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
	
		if("D201301".equals(Configurations.getCurrentDomainId()))
			return "/rbac/logout";
		else{
			return "/rbac/logoutZ";
		}
	}
	
	//跳转主页
	@RequestMapping("home")
	public String homePage(Model model){
		
		List<NewsVM> list = newsService.getLatestNews(6);
		
		model.addAttribute("list", list);
		return "/HomePages/home";
	}
	
	//弹出修改密码界面
	@RequestMapping("modify_password")
	public String modifyPassword() {
		return "password";
	}
	
	//确认原密码是否正确
	@RequestMapping("check_password")
	@ResponseBody
	public boolean checkPassword(HttpSession session, String old_password) {
		
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		
		return userService.validateUser(user.getLoginId(), old_password);
	}
	
	//保存新密码
	@RequestMapping("save_password")
	@ResponseBody
	public boolean savePassword(HttpSession session, HttpServletRequest request) {
		
		UserSession userSession = new UserSession(session);
		User user = userSession.getCurrentUser();
		
		String new_password = request.getParameter("new_password");
		
		try{
			userService.updateUserPassword(new_password, user.getId());
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/*
	 * 用户查询
	 */
	@RequestMapping("userSearch")
	@ResponseBody
	public String newssearch(HttpServletRequest request,
			HttpServletResponse response, String loginId, String name, String userType, String unitId, String discId){
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		
		PageVM<UserVM> users = userService.getSearchUsers(loginId, unitId, discId, name, userType,  page, pageSize);
		
		Map<String, Object> m = users.getGridData();
		
		String json = JsonConvertor.obj2JSON(m);
		
		return json;
	}

}
