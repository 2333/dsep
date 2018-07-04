package com.dsep.service.rbac.impl;
/**
 * @author fanghongyu
 */
import java.util.Set;

import com.dsep.dao.rbac.IpDao;
import com.dsep.dao.rbac.IpInUseDao;
import com.dsep.dao.rbac.RightDao;
import com.dsep.dao.rbac.RoleDao;
import com.dsep.dao.rbac.UserDao;
import com.dsep.entity.IpInUse;
import com.dsep.service.rbac.IpInUseService;

public class IpInUseServiceImpl implements IpInUseService
{
	private RoleDao roleDao;
	private UserDao userDao;
	private RightDao rightDao;
	private IpDao ipDao;
	private IpInUseDao ipInUseDao;
	
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
	
	public IpDao getIpDao() {
		return ipDao;
	}

	public void setIpDao(IpDao ipDao) {
		this.ipDao = ipDao;
	}
	
	public IpInUseDao getIpInUseDao() {
		return ipInUseDao;
	}

	public void setIpInUseDao(IpInUseDao ipInUseDao) {
		this.ipInUseDao = ipInUseDao;
	}

	@Override
	public Set<IpInUse> getIpInUse(Integer userId, Integer ipId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer setIpInUse(Integer userId, Integer ipId) {
		IpInUse ipInUse = new IpInUse();
		ipInUse.setUserId(userId);
		ipInUse.setIpId(ipId);
		return ipInUseDao.save(ipInUse);
	}


	

}
