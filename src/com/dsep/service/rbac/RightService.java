package com.dsep.service.rbac;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.Right;
import com.dsep.vm.PageVM;
import com.dsep.vm.RightVM;
import com.dsep.vm.CheckTreeVM;

public interface RightService 
{
	public abstract Right getRight(String rightId);
	
	/** 取得某角色的权限map
	 * @param roleId
	 * @return Map<Right,该角色是否有该权限（没有0，有1）>
	 */
	public abstract List<CheckTreeVM> getRoleRights(String roleId);
	
	public abstract List<CheckTreeVM> getMenuRoleRights(String roleId);
	
	public abstract List<CheckTreeVM> getActionRoleRights(String roleId);
	
	public abstract PageVM<RightVM> getRights(int pageIndex,int pageSize,Boolean desc,String orderProperName);
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void newRight(Right right);
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void updateRight(Right right);
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void deleteRight(String rightId);
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean checkRightId(String rightId);
}
