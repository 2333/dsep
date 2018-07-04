package com.dsep.dao.rbac;

import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.Right;

public interface RightDao extends Dao<Right,String>
{
	/** 取得某用户的所有权限，按照rightlevel升序排序
	 * @param userId
	 * @return List<权限>
	 */
	public abstract List<Right> getUserRights(String userId);
	
	public abstract List<Right> getMenuRights(String userId);
	
	public abstract void deleteRoleRight(String rightId);
	
	public abstract void deleteRight(String rightId);
	
	/**
	 * 通过权限类型获取role的权限
	 * @param roleId
	 * @param rightType
	 * @return
	 */
	public abstract List<Right> getRoleRights(String roleId, String rightType);

}
