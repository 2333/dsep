package com.dsep.controller.rbac;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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

	
	private Map<String,Integer> rosIPMap = UserIPMaps.getInstance().getMap();
	//private Map<String,Set<String>> userAndObtainedIPsMaps = UserAndObtainedIPsMaps.getInstance().getMap();
	
	
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
		User user = userService.getUser(Integer.valueOf(userId));
		model.addAttribute("user", user);
		Map<String,String> userTypes = Dictionaries.getUserType();
		model.addAttribute("userTypes", userTypes);
		return "rbac/editUserInfo";
	}

	@RequestMapping("userroletree")
	@ResponseBody
	public String userroletree(String userId) {
		String jsonString = JsonConvertor.obj2JSON(roleService.getroleTreeVMs(Integer.valueOf(userId)));
		return jsonString;
	}

	@RequestMapping("userallroletree")
	@ResponseBody
	public String userallroletree() {
		String jsonString = JsonConvertor.obj2JSON(roleService.getroleTreeVMs(-1));
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
			//String userId = UUID.randomUUID().toString().replaceAll("-", "");
			//System.out.println(userId);
			//user.setId(userId);
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
			userService.deleteUser(Integer.valueOf(userId));
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
	
	
	@RequestMapping(value = "/user_validate")
	@ResponseBody
	public String userValidate(@RequestBody List<UserValidator> list) {
		// 最开始验证一下前端数据的合法性
		//validateDataFromJSP(results);
		
		for (UserValidator ele : list) {
			// 把前台参数解码
			if (ele == null) {
				return "";
			}
			System.out.println(ele.getLoginId());
			System.out.println(ele.getPassword());
			//System.out.println(ele.getLocation());
			//System.out.println(ele.getRequestWinNum());
		}
		
		// 从前台来的值，即将要存储到数据库中
		ReturnInfo info = new ReturnInfo();
		info.setAvaliableWinNum(100 - 10);
		info.setIsValidate(true);
		String jsonString = JsonConvertor.obj2JSON(info);
		return jsonString;
	}
	
	@RequestMapping(value = "/online")
	@ResponseBody
	public String online(@RequestBody List<UserOnLine> list) {
		// 最开始验证一下前端数据的合法性
		//validateDataFromJSP(results);
		String currentIP = null;
		System.out.println(rosIPMap.size());
		for (UserOnLine ele : list) {
			// 把前台参数解码
			if (ele == null) {
				return "";
			}
			System.out.println(ele.getLocation());
			System.out.println(ele.getLoginId());
			System.out.println(ele.getUseIP());
			currentIP = ele.getUseIP();
			
		}
		UserOnLineReturnInfo info = new UserOnLineReturnInfo();
		info.setRet(true);
		Integer res = rosIPMap.get(currentIP);
		if (res == null) {
			rosIPMap.put(currentIP, 1);
			info.setInfo(currentIP + " now use num:" + 1);
		} else {
			rosIPMap.put(currentIP, res + 1);
			info.setInfo(currentIP + " now use num:" + (res + 1));
		}
		String jsonString = JsonConvertor.obj2JSON(info);
		return jsonString;
	}
	
	@RequestMapping(value = "/offline")
	@ResponseBody
	public String offline(@RequestBody List<UserOffLine> list) {
		// 最开始验证一下前端数据的合法性
		//validateDataFromJSP(results);
		String currentIP = null;
		System.out.println(rosIPMap.size());
		for (UserOffLine ele : list) {
			// 把前台参数解码
			if (ele == null) {
				return "";
			}
			System.out.println(ele.getLocation());
			System.out.println(ele.getLoginId());
			System.out.println(ele.getReleaseIP());
			currentIP = ele.getReleaseIP();
			
		}
		UserOffLineReturnInfo info = new UserOffLineReturnInfo();
		info.setRet(true);
		Integer res = rosIPMap.get(currentIP);
		if (res == null) {
			rosIPMap.put(currentIP, 0);
			info.setInfo(currentIP + " now use num:" + 0);
		} else {
			rosIPMap.put(currentIP, res - 1);
			info.setInfo(currentIP + " now use num:" + (res - 1));
		}
		String jsonString = JsonConvertor.obj2JSON(info);
		return jsonString;
	}
	
	/**
	 * 
	 * @return
	 * 从本地获得test.txt中的可用的一个ros的ip，利用Ros的API连上之后，获取10个，然后以json的形式传递到本地
	 */
	@RequestMapping(value = "/getRosIP")
	@ResponseBody
	public String getRosIP(HttpServletRequest req, @RequestBody List<UserValidator> list) {
		
		UsableIP10 usableIP10 = new UsableIP10();
		
		if (null == list || list.size() == 0) {
			return "";
		}
		for (UserValidator ele : list) {
			// 把前台参数解码
			if (ele == null) {
				usableIP10.info = "数据为空";
				usableIP10.avaliableWinNum = -1;
				return JsonConvertor.obj2JSON(usableIP10);
			}
			System.out.println(ele.getLoginId());
			System.out.println(ele.getPassword());
			//System.out.println(ele.getLocation());
			//System.out.println(ele.getRequestWinNum());
		}
		
		// 如果初始化没有可用的ROS IP
		if (RosConnectionUtil.usableRosConnectionIPs.size() == 0) {
			String ret = RosConnectionUtil.getCurrentRosConnectionIP();
			if (ret.equals("ERROR")) {
				usableIP10.info = "数据为空";
				usableIP10.avaliableWinNum = -1;
				return JsonConvertor.obj2JSON(usableIP10);
			}
		}
		ArrayList<String> activePPPoEIpAddresses = new ArrayList<String>();
		for (String r : RosConnectionUtil.usableRosConnectionIPs) {
			activePPPoEIpAddresses.add(r);
		}
		
        
        
        int requestNum = list.get(0).getRequestWinNum();
        
        int remainNum = UserAndObtainedIPsMaps.getInstance().guanliyuanIP;
        
        int allocateNum = 0;
        if (remainNum >= requestNum) {
        	allocateNum = requestNum;
        	UserAndObtainedIPsMaps.getInstance().guanliyuanIP -= requestNum;
        	usableIP10.info = "ok";
        } else {
        	allocateNum = remainNum;
        	UserAndObtainedIPsMaps.getInstance().guanliyuanIP = 0;
        	usableIP10.info = "request winnum more than remain winnum, allocate:" + allocateNum;
        }
        HashSet<Integer> set = new HashSet<Integer>();
        while (set.size() < allocateNum) {
        	int i = (int) ((Math.random())*RosConnectionUtil.usableRosConnectionIPs.size());
            if (!set.contains(i)) {
            	set.add(i);
            }
        }
        usableIP10.username = list.get(0).getLoginId();
        usableIP10.password = list.get(0).getPassword();
        usableIP10.remainTime = UserAndObtainedIPsMaps.getInstance().myDate1.getTime() - new Date().getTime();
        usableIP10.avaliableWinNum = UserAndObtainedIPsMaps.getInstance().guanliyuanIP;
        usableIP10.isValidate = true;
        usableIP10.location = "changzhou";
        usableIP10.port = 1080;
        for (Integer i : set) {
        	System.out.println(activePPPoEIpAddresses.get(i));
        	usableIP10.ips.add(activePPPoEIpAddresses.get(i)+ ":" + usableIP10.port + ",aa,bb" 
        	+ "," + usableIP10.location);
        }
        
        
		String jsonString = JsonConvertor.obj2JSON(usableIP10);
		return jsonString;
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
