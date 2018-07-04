package com.dsep.controller.rbac;

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
import com.dsep.entity.Ip;
import com.dsep.entity.Right;
import com.dsep.entity.RosConnIpCache;
import com.dsep.entity.User;
import com.dsep.entity.UserIpHeart;
import com.dsep.entity.UserIpLog;
import com.dsep.service.base.NewsService;
import com.dsep.service.rbac.IpInUseService;
import com.dsep.service.rbac.IpService;
import com.dsep.service.rbac.RoleService;
import com.dsep.service.rbac.RosConnIpCacheService;
import com.dsep.service.rbac.UserIpHeartService;
import com.dsep.service.rbac.UserIpLogService;
import com.dsep.service.rbac.UserService;
import com.dsep.util.Configurations;
import com.dsep.util.Dictionaries;
import com.dsep.util.JsonConvertor;
import com.dsep.util.TextConfiguration;
import com.dsep.util.UserSession;
import com.dsep.util.VerificationCode;
import com.dsep.vm.NewsVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.UserIpOnLineVM;
import com.dsep.vm.UserVM;

@Controller
@RequestMapping("rbac")
public class UserController {

	
	//private Map<String,Integer> rosIPMap = UserIPMaps.getInstance().getMap();
	//private Map<String,Set<String>> userAndObtainedIPsMaps = UserAndObtainedIPsMaps.getInstance().getMap();
	
	
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "roleService")
	private RoleService roleService;
	
	@Resource(name = "rosConnIpCacheService")
	private RosConnIpCacheService rosConnIpCacheService;
	
	@Resource(name = "userIpLogService")
	private UserIpLogService userIpLogService;
	
	@Resource(name = "userIpHeartService")
	private UserIpHeartService userIpHeartService;
	
	
	@Resource(name = "ipService")
	private IpService ipService;
	
	@Resource(name = "ipInUseService")
	private IpInUseService ipInUseService;
	
	
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
		UserOnLineReturnInfo info = new UserOnLineReturnInfo();
		
		if (null == list || list.size() == 0) {
			String jsonString = JsonConvertor.obj2JSON(info);
			return jsonString;
		}
		
		UserOnLine userOnLine = list.get(0);
		String loginId = userOnLine.getLoginId();
		String location = userOnLine.getLocation();
		String useIp = userOnLine.getUseIP();
		String currentIP = null;
		User user = userService.getUserByLoginId(loginId);
		
		
		info.setCurrent(1);
		info.setAvaliableWinNum(user.getNownum() - 1);
		info.setUserIp(useIp);
		
		RosConnIpCache rosConnIpCache = rosConnIpCacheService.getRosConnIpCacheByIpValue(useIp);
		String pppoeName = rosConnIpCache.getIpPppoeName();
		String userdPppoeNumber = user.getUsedPppoeNumber();
		if (userdPppoeNumber == null || userdPppoeNumber.length() == 0) {
			userdPppoeNumber = pppoeName;
		} else {
			userdPppoeNumber = userdPppoeNumber + "," + pppoeName;
		}
		user.setNownum(user.getNownum() - 1);
		user.setUsedPppoeNumber(userdPppoeNumber);
		userService.UpdateUserAndIps(user);
		String jsonString = JsonConvertor.obj2JSON(info);
		return jsonString;
	}
	
	@RequestMapping(value = "/offline")
	@ResponseBody
	public String offline(@RequestBody List<UserOffLine> list) {
		UserOffLineReturnInfo info = new UserOffLineReturnInfo();
		
		if (null == list || list.size() == 0) {
			String jsonString = JsonConvertor.obj2JSON(info);
			return jsonString;
		}
		
		UserOffLine userOffLine = list.get(0);
		String loginId = userOffLine.getLoginId();
		String location = userOffLine.getLocation();
		String useIp = userOffLine.getUserIp();
		User user = userService.getUserByLoginId(loginId);
		
		info.setCurrent(0);
		info.setAvaliableWinNum(user.getNownum() + 1);
		info.setUserIp(useIp);
		RosConnIpCache rosConnIpCache = rosConnIpCacheService.getRosConnIpCacheByIpValue(useIp);
		String pppoeName = rosConnIpCache.getIpPppoeName();
		String userdPppoeNumber = user.getUsedPppoeNumber();
		String newNumbers = "";
		if (userdPppoeNumber == null || userdPppoeNumber.length() == 0) {
			
		} else {
			String ele = pppoeName;
			String[] numbers = userdPppoeNumber.split(",");
			
			for (String n_ele : numbers) {
				if (n_ele.equals(ele)) {
					
				} else {
					newNumbers = newNumbers + "," + n_ele;
				}
			}
		}
		user.setNownum(user.getNownum() + 1);
		user.setUsedPppoeNumber(newNumbers);
		userService.UpdateUserAndIps(user);
		String jsonString = JsonConvertor.obj2JSON(info);
		return jsonString;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * 用于手动初始化某一地区的所有pppoe ip列表
	 */
	@RequestMapping(value = "/addIps")
	@ResponseBody
	public String addIps(HttpServletRequest req) {
		for (int i = 1; i <= 52; i++) {
			Ip ip = new Ip();
			ip.setId(i);
			ip.setRosName("ros-changzhou");
			ip.setPppoeName("pppoe-" + i);
		
			ipService.saveIp(ip);
		}
		return "";
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * 用于手动绑定用户和某一地区的pppoe ip，这个功能其实应该在前端注册用户时绑定
	 */
	@RequestMapping(value = "/testBindUserAndIps")
	@ResponseBody
	public String testBindUserAndIps(HttpServletRequest req) {
		User zhangsan = userService.getUser(3);
		HashSet<Ip> set = new HashSet<Ip>();
		for (int i = 1; i <= 10; i++) {
			Ip ip = ipService.getIp(i);
			set.add(ip);
		}
		zhangsan.setIps(set);
		userService.UpdateUserAndIps(zhangsan);
		
		User lisi = userService.getUser(4);
		HashSet<Ip> set2 = new HashSet<Ip>();
		for (int i = 5; i <= 16; i++) {
			Ip ip = ipService.getIp(i);
			set2.add(ip);
		}
		lisi.setIps(set2);
		userService.UpdateUserAndIps(lisi);
		return "";
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
		User user = null;
		if (null == list || list.size() == 0) {
			usableIP10.info = "user invalid";
			usableIP10.avaliableWinNum = -1;
			String jsonString = JsonConvertor.obj2JSON(usableIP10);
			return jsonString;
		}
		for (UserValidator ele : list) {
			// 把前台参数解码
			if (ele == null) {
				usableIP10.info = "user invalid";
				usableIP10.avaliableWinNum = -1;
				return JsonConvertor.obj2JSON(usableIP10);
			}
			if (userService.validateUser(ele.getLoginId(), ele.getPassword())) {
				user = userService.getUserByLoginId(ele.getLoginId());
			} else {
				usableIP10.info = "user invalid";
				usableIP10.avaliableWinNum = -1;
				return JsonConvertor.obj2JSON(usableIP10);
			}
			System.out.println(user.getLoginId());
			System.out.println(user.getPassword());
		}
		
		// 如果初始化没有可用的ROS IP
		String ret = new RosConnectionUtil().getCurrentRosConnectionIP("changzhou", rosConnIpCacheService);
		if (ret.equals("ERROR")) {
			usableIP10.info = "数据为空";
			usableIP10.avaliableWinNum = -1;
			return JsonConvertor.obj2JSON(usableIP10);
		}
		
		ArrayList<String> activePPPoEIpAddresses = new ArrayList<String>();
		List<RosConnIpCache> ipCaches = rosConnIpCacheService.getAllIpsByRosLocation("changzhou");
		for (RosConnIpCache r : ipCaches) {
			activePPPoEIpAddresses.add(r.getIpValue());
		}
        
        int requestNum = user.getNownum();
        
        int remainNum = user.getNownum();
        
        int allocateNum = remainNum;
        if (allocateNum > 0) {
        	//UserAndObtainedIPsMaps.getInstance().guanliyuanIP -= requestNum;
        	usableIP10.info = "ok";
        } else {
        	//UserAndObtainedIPsMaps.getInstance().guanliyuanIP = 0;
        	usableIP10.info = "request winnum more than remain winnum, allocate:" + allocateNum;
        	usableIP10.avaliableWinNum = 0;
			String jsonString = JsonConvertor.obj2JSON(usableIP10);
			return jsonString;
        }
        HashSet<Integer> set = new HashSet<Integer>();
        Set<Ip> ips = user.getIps();
    	Set<Integer> intSet = new HashSet<Integer>();
    	for (Ip ip : ips) {
    		intSet.add(Integer.valueOf(ip.getPppoeName().substring(6)));
    	}
        while (set.size() < allocateNum) {
        	int i = (int) ((Math.random())*ipCaches.size());
        	
            if (!set.contains(i)) {
            	boolean iNotInIntSet = true;
            	for (Integer x : intSet) {
            		if (x == i) {
            			iNotInIntSet = false;
            			break;
            		}
            	}
            	if (iNotInIntSet) {
            		set.add(i);
            	}
            }
        }
        usableIP10.username = user.getLoginId();
        usableIP10.password = user.getPassword();
        usableIP10.remainTime = user.getLoginTime().getTime();
        usableIP10.avaliableWinNum = user.getNownum();
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
	
	@RequestMapping("userIp")
	public String userIp() {
		return "rbac/showUserIp";
	}
	
	/*
	 * 所有用户及其IP在线情况查询
	 */
	@RequestMapping("userIpQuery")
	@ResponseBody
	public String userIpQuery(HttpServletRequest request,
			HttpServletResponse response, String loginId, String name, String userType, String unitId, String discId){
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		PageVM<UserIpOnLineVM> users = userService.userIpQuery(page, pageSize, true, "id", "0");
		
		Map<String, Object> m = users.getGridData();
		
		String json = JsonConvertor.obj2JSON(m);
		System.out.println(json);
		return json;
	}
	
	
	@RequestMapping(value = "/logger")
	public void logger(@RequestBody List<UserLogger> list) {
		if (null == list || list.size() == 0) {
			return;
		}
		UserLogger userLogger = list.get(0);
		String loginId = userLogger.getLoginId();
		String log = userLogger.getLog();
		UserIpLog userIpLog = new UserIpLog();
		userIpLog.setLoginId(loginId);
		userIpLog.setMyLog(log);
		this.userIpLogService.save(userIpLog);
	}
	
	@RequestMapping(value = "/beat")
	public void beat(@RequestBody List<UserHeartBeat> list) {
		if (null == list || list.size() == 0) {
			return;
		}
		
		UserHeartBeat beat = list.get(0);
		String loginId = beat.getLoginId();
		String machineId = beat.getMachineId();
		List<String> ips = beat.getUseIp();
		String myIps = "";
		for (String ip : ips) {
			myIps += (ip + ",");
		}
		if (null == myIps || myIps.length() == 0) {
			
		} else {
			myIps = myIps.substring(0, myIps.length() - 2);
		}
		UserIpHeart userIpHeart = new UserIpHeart();
		userIpHeart.setLoginId(loginId);
		userIpHeart.setMachineId(machineId);
		userIpHeart.setUseIp(myIps);
		userIpHeart.setLastRecordTime(new Date());
		this.userIpHeartService.save(userIpHeart);
	}
	

	@RequestMapping("userIpLog")
	public String userIpLog() {
		return "rbac/showUserIpLog";
	}
	
	@RequestMapping("userIpLogQuery")
	@ResponseBody
	public String userIpLogQuery(HttpServletRequest request,
			HttpServletResponse response, String loginId, String name, String userType, String unitId, String discId){
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		PageVM<UserIpLog> userIpLog = userIpLogService.userIpLogQuery(page, pageSize, true, "id");
		
		Map<String, Object> m = userIpLog.getGridData();
		
		String json = JsonConvertor.obj2JSON(m);
		System.out.println(json);
		return json;
	}
	
	
	@RequestMapping("userIpHeart")
	public String userIpHeart() {
		return "rbac/showUserIpHeart";
	}
	
	@RequestMapping("userIpHeartQuery")
	@ResponseBody
	public String userIpHeartQuery(HttpServletRequest request,
			HttpServletResponse response, String loginId, String name, String userType, String unitId, String discId){
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		PageVM<UserIpHeart> users = userIpHeartService.userIpHeartQuery(page, pageSize, true, "id");
		
		Map<String, Object> m = users.getGridData();
		
		String json = JsonConvertor.obj2JSON(m);
		System.out.println(json);
		return json;
	}
	
}
