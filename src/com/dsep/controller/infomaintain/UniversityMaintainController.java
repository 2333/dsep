package com.dsep.controller.infomaintain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsep.entity.User;
import com.dsep.service.base.UnitService;
import com.dsep.service.rbac.UserService;
import com.dsep.util.Dictionaries;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.PageVM;
import com.dsep.vm.UserVM;

@Controller
@RequestMapping("InfoMaintain")
public class UniversityMaintainController {
	

		@Resource(name = "unitService")
		private UnitService unitService ;
		
		
		@Resource(name="userService")
		private UserService userService ;
		
		
		/**
		 * 点击菜单按钮跳转学校管理页面
		 * @return
		 */
		@RequestMapping("university")
		public String universityManage(){
			return "InfoMaintain/university_list";
		}
		
		/**
		 * 点击菜单跳转到学校信息维护页面-for 学校用户
		 * @return
		 */
		//获取当前登录身份，并显示该身份信息。---by刘琪
		@RequestMapping("universityMaintain")
		public String universityMaintain(Model model,HttpSession session){
			UserSession us = new UserSession(session);
			User user = us.getCurrentUser();//session自动获取当前用户id--刘琪
			model.addAttribute("user", user);
			Map<String,String> userTypes = Dictionaries.getUserType();//userType映射
			model.addAttribute("userTypes", userTypes);
			return "InfoMaintain/university_maintain";
		}
		//保存当前登录身份的修改信息---by刘琪
		@RequestMapping("universityMaintainsaveEditSelf")
		@ResponseBody
		public boolean saveEditUni(User user,HttpSession session) {
			 try{
				 
				 UserSession us = new UserSession(session);
				 
				 //自动获取当前用户的角色id
				 List<String> userRoles = new ArrayList<String>();
				 userRoles.add("t0003");
				 
				 //更新用户信息，但是密码不变，从原Session里边读取并设置
				 user.setPassword(us.getCurrentUser().getPassword());
				 
				 //更新用户信息
				 userService.UpdateUser(user, userRoles);
				 
				 //将新的用户信息写入Session
				 us.setCurrentUser(user);
			}catch(Exception e){
				return false;
			}
			return true;
		}
		
		@RequestMapping("universityshow")
		public String show_universityInfo(String id,Model model){
			User user =userService.getUser(id);
			model.addAttribute("user",user);
			return "InfoMaintain/show_university";
		}
		/*
		 * 增加一个学校用户
		 * model universityList保存所有学校的<id,name>
		 * 返回前台下拉列表中显示
		 * @author lyx
		 */
		@RequestMapping("universityadd")
		public String AddUniversity(Model model){
			Map<String,String>universityList = unitService.getAllUnitMaps();
			model.addAttribute("universityList", universityList);
			return "InfoMaintain/add_university";
		}
		
		/*
		 * 编辑学校用户信息
		 */
		@RequestMapping("universityedit")
		public String editUniversity(String id , Model model){
			
			User user = userService.getUser(id);
			System.out.println(user);
			model.addAttribute("user", user);
			return "InfoMaintain/edit_university";
		}
		
		/*
		 * 删除学校用户
		 */
		@RequestMapping("universitydel")
		@ResponseBody
		public boolean delUniversity(String id){
			try{
				userService.deleteUser(id);
			}
			catch(Exception e){
				return false;
			}
			return true;
		}
		
		@RequestMapping("universitySerach")
		@ResponseBody
		public String searchUniversity(HttpServletRequest request,
				HttpServletResponse response,String unitId,String name){
			
			int page = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("rows"));
			PageVM<UserVM> unit_userVM = userService.getSearchUsers(null, unitId,"", name, "2",page, pageSize);
			Map<String, Object> m = unit_userVM.getGridData();
			String json = JsonConvertor.obj2JSON(m);
			return json;
		}
		//返回学校用户列表回界面
		@RequestMapping("universityList")
		@ResponseBody
		public void schoolUserList(HttpServletRequest request,HttpServletResponse response){
			String sord=request.getParameter("sord");//排序方式
			String sidx=request.getParameter("sidx");//排序字段
			int page= Integer.parseInt(request.getParameter("page")); //当前页
			int pageSize=Integer.parseInt(request.getParameter("rows")); //每页多少数据
			boolean order_flag = false;
			if ("desc".equals(sord)) {
				order_flag = true;
			}
		    PageVM<UserVM> schoolUserVm = userService.getSchoolUsers(page, pageSize, order_flag, sidx);   	
	        String schoolUserlist=JsonConvertor.obj2JSON(schoolUserVm.getGridData());
	       // String json = JsonConvertor.obj2JSON(m); 
	        response.setCharacterEncoding("UTF-8");
	        System.out.println(schoolUserlist);
	        try {
				response.getWriter().print(schoolUserlist);
			} 
	        catch (IOException e) {
				e.printStackTrace();
			}  
		}
		
		//保存新建学校用户
		@RequestMapping("universitysaveadd")
		@ResponseBody
		public boolean saveAddSchool(User user){
			try{
				userService.newSchoolUser(user);
			}
			catch(Exception e){
				return false;
			}
			return true;
		}
		
		@RequestMapping("universitysaveedit")
		@ResponseBody
		public boolean universitysaveedit(User user,@RequestParam(value = "roleid", required = false) List<String> uniroleid)
		{
			 try {
				 userService.UpdateUser(user,uniroleid);
				 return true;
				} 
		        catch (Exception e) {
					e.printStackTrace();
					return false;
				}   
		}
		
}
