package com.dsep.dao.rbac.impl;

import java.util.List;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.dao.rbac.RightDao;
import com.dsep.entity.Right;

public class RightDaoImpl extends DaoImpl<Right,String> implements RightDao {

	public List<Right> getUserRights(String userLoginId) {				
		String sql=String.format("select * from DSEP_RBAC_RIGHT  where ID in "+
			 "(select distinct RIGHT_ID from DSEP_RBAC_ROLE_RIGHT left join DSEP_RBAC_USER_ROLE2 " +
			 "on  DSEP_RBAC_ROLE_RIGHT.ROLE_ID = DSEP_RBAC_USER_ROLE2.ROLE_ID "+
			 "where USER_ID in "+"(select distinct ID from DSEP_RBAC_USER2 where LOGIN_ID= '%s')) " +
			 "order by LEVEL_NO,SEQ_NO ASC",userLoginId);
		return super.sqlFind(sql);
	}
	
	//获取菜单权限
	public List<Right> getMenuRights(String userId) {
		String sql=String.format("select * from DSEP_RBAC_RIGHT  where ID in "+
				 "(select distinct RIGHT_ID from DSEP_RBAC_ROLE_RIGHT left join DSEP_RBAC_USER_ROLE2 " +
				 "on  DSEP_RBAC_ROLE_RIGHT.ROLE_ID = DSEP_RBAC_USER_ROLE2.ROLE_ID "+
				 "where USER_ID in "+"(select distinct ID from DSEP_RBAC_USER2 where LOGIN_ID= '%s')) " +
				 "and CATEGORY = 1 " +
				 "order by LEVEL_NO,SEQ_NO ASC",userId);
			return super.sqlFind(sql);
	}
    
	//删除角色权限信息
	public  void deleteRoleRight(String rightId){
	 	String sql=String.format("delete from DSEP_RBAC_ROLE_RIGHT where RIGHT_ID='%s'",rightId);
	 	super.sqlBulkUpdate(sql);
	}
	
	//删除权限信息
	public  void deleteRight(String rightId){
	 	String sql = String.format("delete from DSEP_RBAC_RIGHT where ID='%s'",rightId);
	 	super.sqlBulkUpdate(sql);
	}
	

	@Override
	public List<Right> getRoleRights(String roleId, String rightType) {
		String sql = "select * from dsep_rbac_right where id in"
				+ "(select right_id from dsep_rbac_role_right where role_id = ?) and category = ?";
		List<Right> list = super.sqlFind(sql, new Object[]{roleId, rightType});
		return list;
	}
}
