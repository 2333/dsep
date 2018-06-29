package com.dsep.service.rbac.impl;
/**
 * @author fanghongyu
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.dao.rbac.RightDao;
import com.dsep.dao.rbac.RoleDao;
import com.dsep.dao.rbac.UserDao;
import com.dsep.entity.Right;
import com.dsep.entity.Role;
import com.dsep.entity.User;
import com.dsep.service.rbac.RoleService;
import com.dsep.vm.PageVM;
import com.dsep.vm.CheckTreeVM;

public class RoleServiceImpl implements RoleService
{
	private RoleDao roleDao;
	private UserDao userDao;
	private RightDao rightDao;
	
	public RightDao getRightDao() {
		return rightDao;
	}

	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public Set<Role> getUserRoles(Integer userId) {
		return  userDao.get(userId).getRoles();
	}
    
	public List<CheckTreeVM> getroleTreeVMs(Integer userId) {
		List<CheckTreeVM> allRight= new ArrayList<>();//瑶返回的TreeVM类型的list
		if(userId!=-1)
		{
		   User user=userDao.get(userId);
		   Set<Role> userRoles=user.getRoles();//得到该用户的角色
		   List<Role> alluserList=roleDao.getAll();//得到所有的角色
		   for(Role r:alluserList)
		   {
	          if(SetContainRole(userRoles, r.getId()))
	          {
                 allRight.add(roleToNode(r, true));    	   
	          }
	         else {
			     allRight.add(roleToNode(r, false));
		   }
		   }
		}
		else {
			List<Role> alluserList=roleDao.getAll();//得到所有的角色
			for(Role r:alluserList)
			{
				allRight.add(roleToNode(r, false));
			}
		}
		
		return allRight;
	}
	
	@Override
	public PageVM<Role> getRoles(int pageIndex, int pageSize, Boolean desc,
			String orderProperName) {
		
		List<Role> list=roleDao.page(pageIndex, pageSize, desc, orderProperName);
		int totalCount=roleDao.Count();
		PageVM<Role> result=new PageVM<Role>(pageIndex,totalCount,pageSize,list);
		return result;
	}

	public Boolean newRole(Role role,List<String> rightIds) throws Exception {
		if(roleDao.get(role.getId()) != null){
			throw new Exception("该角色ID已经存在！");
			}
		try
		{
			role.setRights(GetRightList(rightIds));
			roleDao.save(role);
			//roleDao.updateRoleRights(role.getRoleId(), rightIds);
		}catch(Exception ex)
		{
			return false;
		}		
		return true;
	}
	
	/*
	 *根据权限ID获取权限对象 
	 */
	private Set<Right> GetRightList(List<String> rightIds)
	{
		HashSet<Right> right = new HashSet<Right>();
		for(String rightId:rightIds)
		{
			Right newRight = rightDao.get(rightId);
			right.add(newRight);
		}
		return right;
	}
	
	public Boolean updateRole(Role role,List<String> rightIds) {
		try
		{
			role.setRights(GetRightList(rightIds));
			roleDao.saveOrUpdate(role);
			//roleDao.updateRoleRights(role.getRoleId(), rightIds);
		}catch(Exception ex)
		{			
			return false;
		}
		return true;
	}

	@Override
	public Boolean deleteRole(String roleId) {
		try
		{
			roleDao.deleteByKey(roleId);
		}
		catch(Exception ex)
		{
			return false;
		}
		return true;
	}

	@Override
	public Role getRole(String roleId) {
		return roleDao.get(roleId);
	}

	@Override
	public List<CheckTreeVM> getUserRolesTree(Integer userId) {
		List<Role> allRoles = roleDao.getAll();
		Set<Role> userRoles = null;
		User user = userDao.get(userId);
		if(user != null)
			userRoles=user.getRoles();
		List<CheckTreeVM> tree = new ArrayList<CheckTreeVM>();
		for(Role r : allRoles){
			CheckTreeVM node = new CheckTreeVM();
			if(this.SetContainRole(userRoles,r.getId()))
			{
				tree.add(roleToNode(r, true));
			}
			else{
				tree.add(roleToNode(r, false));
			}
		}
		return tree;
	}
	private Boolean SetContainRole(Set<Role> roles, String roleId)
	{
		if(roles == null)
			return false;
		for(Role r : roles)
			if(r.getId() == roleId)
				return true;
		return false;
	}
	
	private CheckTreeVM roleToNode(Role r,boolean checked)
	{
		CheckTreeVM node = new CheckTreeVM();
		node.setChecked(checked);
		node.setId(r.getId());
		node.setName(r.getName());
		node.setOpen(checked);
		node.setpId("root");
		return node;
	}


}
