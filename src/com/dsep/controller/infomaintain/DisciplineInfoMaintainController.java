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

import com.dsep.entity.Discipline;
import com.dsep.entity.Role;
import com.dsep.entity.User;
import com.dsep.service.base.DisciplineService;
import com.dsep.service.rbac.UserService;
import com.dsep.util.Dictionaries;
import com.dsep.util.JsonConvertor;
import com.dsep.util.UserSession;
import com.dsep.vm.NewsVM;
import com.dsep.vm.PageVM;
import com.dsep.vm.UserVM;

@Controller
@RequestMapping("InfoMaintain")
public class DisciplineInfoMaintainController {
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "disciplineService")
	private DisciplineService disciplineService;
	/** by chongrui
	 * 跳转到本学校下得学科列表
	 * @return
	 */
	@RequestMapping("discipline")
	public String universityManage(){
		return "InfoMaintain/discipline_list";
	}
	
	/** by chongrui
	 * 跳转到本学科用户的自我维护界面
	 * @return
	 */
	//获取当前登录身份，并显示该身份信息。---by刘琪
	@RequestMapping("disciplineInfo")
	public String disciplineInfo(Model model,HttpSession session){
		UserSession us = new UserSession(session);
		User user = us.getCurrentUser();//session自动获取当前用户id--刘琪
		model.addAttribute("user", user);
		Map<String,String> userTypes = Dictionaries.getUserType();//userType映射
		model.addAttribute("userTypes", userTypes);
		return "InfoMaintain/discipline_maintain";
	}
	/**
	 * 返回学科查看页面，默认无法修改
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("disciplineshow")
	public String show_disciplineInfo(String id,Model model){
		User user =userService.getUser(id);
		model.addAttribute("user",user);
		return "InfoMaintain/show_discipline";
	}
	/** by chongrui
	 * 跳转到添加学科的界面
	 * @return
	 */
	@RequestMapping("disciplineadd")
	public String AddDiscipline(HttpSession session,Model m)
	{
		UserSession us = new UserSession(session);
        User user = us.getCurrentUser();//即可获得当前登录用户
        m.addAttribute("user",user);
        //获得当前学校用户下的所有学科
        List<Discipline> disclist = disciplineService.getDisciplinesByUnitId(user.getUnitId());
        m.addAttribute("disclist",disclist);
		return "InfoMaintain/add_discipline";
	}
	
	@RequestMapping("disciplineSerach")
	@ResponseBody
	public String searchDiscipline(HttpServletRequest request,
			HttpServletResponse response,String unitId,String discId,String name){
		
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		PageVM<UserVM> disc_userVM = userService.getSearchUsers(null, unitId,discId, name, "3",page, pageSize);
		Map<String, Object> m = disc_userVM.getGridData();
		String json = JsonConvertor.obj2JSON(m);
		return json;
	}
	/** by chongrui
	 *编辑所选的学科用户
	 *id为学科的唯一编号
	 */
	@RequestMapping("disciplineedit")
	public String EditDiscipline(String id, Model m)
	{
		User user = userService.getUser(id);
		m.addAttribute("user", user);
		return "InfoMaintain/edit_discipline";
	}
	
	/** by chongrui
	 * 删除学科
	 */
	@RequestMapping("disciplinedel")
	@ResponseBody
	public boolean DeleteDisc(String id)
	{
		//System.out.println("deleteid="+id);
		try{
			userService.deleteUser(id);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/** by chongrui
	 *显示当前学校的所有学科
	 */
	@RequestMapping("disciplinelist")
	@ResponseBody
	public void discsiplinelist(HttpServletRequest request,HttpServletResponse response,
			HttpSession session)
	{   
		UserSession us = new UserSession(session);
        User user1 = us.getCurrentUser();//即可获得当前登录用户
        String current_unitId = user1.getUnitId();
        
		String sord=request.getParameter("sord");//排序方式
		String sidx=request.getParameter("sidx");//排序字段
		int page= Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize=Integer.parseInt(request.getParameter("rows")); //每页多少数据
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
		
		PageVM<UserVM> disc_userVm = userService.getDisciplineUsers(page, pageSize, order_flag, sidx,current_unitId);   	
        //转换为json数据格式
		String disc_userlist=JsonConvertor.obj2JSON(disc_userVm.getGridData());
         //转换为UTF-8编码
		response.setCharacterEncoding("UTF-8");
        System.out.println(disc_userlist);
        
        try {
			response.getWriter().print(disc_userlist);
		} 
        catch (IOException e) {
			e.printStackTrace();
		}   

	}
	
	/** by chongrui
	 * 保存添加的学科
	 */
	@RequestMapping("disciplinesaveadd")
	@ResponseBody
	public boolean saveAddDisc(User user, HttpSession session)
	{
		UserSession us = new UserSession(session);
        User user1 = us.getCurrentUser();//即可获得当前登录用户
        user.setUnitId(user1.getUnitId());
		 try {
			 userService.newDisciplineUser(user);
			 return true;
			} 
	        catch (Exception e) {
				e.printStackTrace();
				return false;
			}   
	}
	
	/** by chongrui
	 * 保存修改的学科
	 */
	@RequestMapping("disciplinesaveedit")
	@ResponseBody
	public boolean saveEditDisc(User user,@RequestParam(value = "roleid", required = false) List<String> discroleid)
	{
		 try {
			 userService.UpdateUser(user,discroleid);
			 return true;
			} 
	        catch (Exception e) {
				e.printStackTrace();
				return false;
			}   
	}
	
	//保存当前登录身份的修改信息---by刘琪
	@RequestMapping("disciplineInfosaveEditSelf")
	@ResponseBody
	public boolean saveEditDisc(User user,HttpSession session)//修改参数
	{
		 try{
			 //自动获取当前用户的角色id
			 List<String> userRoles = new ArrayList<String>();
			 userRoles.add("t0002");
			//更新用户信息，但是密码不变，从原Session里边读取并设置
			 UserSession us = new UserSession(session);
			 user.setPassword(us.getCurrentUser().getPassword());
			 userService.UpdateUser(user, userRoles);
			 //保存当前用户信息的同时要更改当前session信息
			 us.setCurrentUser(user);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
}