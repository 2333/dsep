package com.dsep.service.rbac;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.Right;
import com.dsep.entity.Role;
import com.dsep.vm.PageVM;
import com.dsep.vm.CheckTreeVM;

@Transactional(propagation=Propagation.SUPPORTS)
public interface RoleService {
	
	public abstract Set<Role> getUserRoles(String userId);
	
	/** 取得某用户的角色键值对
	 * @param userId
	 * @return Map<Role,该User是否有该角色（没有0，有1）>
	 */
	public abstract List<CheckTreeVM> getroleTreeVMs(String userId);
	public abstract List<CheckTreeVM> getUserRolesTree(String userId);
	
	public abstract PageVM<Role> getRoles(int pageIndex,int pageSize,Boolean desc,String orderProperName);
	
	public abstract Role getRole(String roleId);
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract Boolean newRole(Role role,List<String> rightIds) throws Exception;
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract Boolean updateRole(Role role,List<String> rightIds);
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract Boolean deleteRole(String roleId);
	
	
}
