package com.dsep.controller.rbac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dsep.entity.Right;
import com.dsep.entity.Role;
import com.dsep.service.rbac.RightService;
import com.dsep.service.rbac.RoleService;
import com.dsep.util.JsonConvertor;
import com.dsep.vm.PageVM;
import com.dsep.vm.CheckTreeVM;

@Controller
@RequestMapping("rbac")
public class RoleController {
	
	@Resource(name="roleService")
	private RoleService roleService;
	
	@Resource(name="rightService")
	private RightService rightService;
	
	//跳转角色管理页面
	@RequestMapping("role")
	public String role()
	{
		return "rbac/role";
	}
	
	//返回角色列表信息至前端
	@RequestMapping("rolelist")
	@ResponseBody
	public void roleList(HttpServletRequest request,HttpServletResponse response)
	{   
		String sord=request.getParameter("sord");//排序方式
		String sidx=request.getParameter("sidx");//排序字段
		int page= Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize=Integer.parseInt(request.getParameter("rows")); //每页多少数据
		boolean order_flag = false;
		if ("desc".equals(sord)) {
			order_flag = true;
		}
	    PageVM<Role> roleVm = roleService.getRoles(page, pageSize, order_flag, sidx);   	
        String rolelist=JsonConvertor.obj2JSON(roleVm.getGridData());
       // String json = JsonConvertor.obj2JSON(m); 
        response.setCharacterEncoding("UTF-8");
        System.out.println(rolelist);
        try {
			response.getWriter().print(rolelist);
		} 
        catch (IOException e) {
			e.printStackTrace();
		}   

	}
	
	//打开修改对话框
	@RequestMapping("roleedit")
	public String roleedit(String roleId,Model m)
	{
		if(roleId!=null||"".equals(roleId)){
			Role role=roleService.getRole(roleId);
			m.addAttribute("role", role);
		}
		return "rbac/roleInfo";
	}
	
	//保存修改角色
	@RequestMapping("rolesaveedit")
	@ResponseBody
	public boolean rolesaveedit(Role role,@RequestParam(value="rightIds",required=false)List<String>rightIds){
	  
	  role.setCategory("0");//category字段暂时无用
	  if(roleService.updateRole(role, rightIds))
	  {
		  return true;
	  }
	  else 
	  {
		  return false;
	  }
	}
	
	//打开新建对话框
	@RequestMapping("roleadd")
	public String roleadd(Model m)
	{   
		return "rbac/addRole";
	}
	
	//保存新建角色
	@RequestMapping("rolesaveadd")
	@ResponseBody
	public boolean rolesaveadd(Role role,@RequestParam(value="rightIds",required=false)List<String> rightIds)
	{
		role.setCategory("0");//category字段暂时无用
		try {
			if(roleService.newRole(role, rightIds))
			{
				return true;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	//删除角色
	@RequestMapping("roledelete")
	@ResponseBody
	public boolean roledelete(String roleId)
	{
		try {
			if(roleService.deleteRole(roleId))
			{
				return true;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//获取所有权限
	@RequestMapping("rolerighttree")
	public void rolerighttree(HttpServletRequest request,HttpServletResponse response,String roleId)
	{
		//System.out.println(roleId);
		String rightsTreeString=JsonConvertor.obj2JSON(rightService.getRoleRights(roleId));
		//System.out.println(rightsTreeString);
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(rightsTreeString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//获取菜单权限树
	@RequestMapping("rolemenutree")
	public void rolemenutree(HttpServletRequest request,HttpServletResponse response,String roleId)
	{
		String menuTreeString=JsonConvertor.obj2JSON(rightService.getMenuRoleRights(roleId));
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(menuTreeString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//获取动作权限树
	@RequestMapping("roleactiontree")
	public void roleactiontree(HttpServletRequest request,HttpServletResponse response,String roleId)
	{
		String actionTreeString=JsonConvertor.obj2JSON(rightService.getActionRoleRights(roleId));
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(actionTreeString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
