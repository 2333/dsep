package com.dsep.dao.rbac.impl;

import java.util.List;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.dao.rbac.RoleDao;
import com.dsep.entity.Role;



public class RoleDaoImpl extends DaoImpl<Role,String> implements RoleDao{

	public void updateRoleRights(String roleId, List<String> rightsIds) {
		super.sqlBulkUpdate("delete from dsep_role_right where roleid = ? ",new Object[]{roleId});
		for(String rId : rightsIds)
			super.sqlBulkUpdate(String.format("insert into dsep_role_right values('%s','%s')",roleId,rId));
	}
	
	@Override
	public List<Role> getUserRole(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select * from dsep_rbac_role where id in"
				+ "(select role_id from dsep_rbac_user_role2 where user_id=?)";
		List<Role> list = super.sqlFind(sql, new Object[]{userId});
		return list;
	}

}
