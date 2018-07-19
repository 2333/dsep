package com.dsep.dao.rbac;

import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.Role;

public interface RoleDao extends Dao<Role,String>
{
	public abstract void updateRoleRights(String roleId,List<String> rightsIds);
	
	/**
	 * 获取用户的所有角色
	 * @param userId
	 * @return
	 */
	public abstract List<Role> getUserRole(Integer userId);
}
