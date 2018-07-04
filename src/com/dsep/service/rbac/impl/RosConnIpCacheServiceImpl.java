package com.dsep.service.rbac.impl;
/**
 * @author fanghongyu
 */
import java.util.List;
import java.util.Set;

import com.dsep.dao.rbac.IpDao;
import com.dsep.dao.rbac.IpInUseDao;
import com.dsep.dao.rbac.RightDao;
import com.dsep.dao.rbac.RoleDao;
import com.dsep.dao.rbac.RosConnIpCacheDao;
import com.dsep.dao.rbac.UserDao;
import com.dsep.entity.RosConnIpCache;
import com.dsep.service.rbac.RosConnIpCacheService;

public class RosConnIpCacheServiceImpl implements RosConnIpCacheService
{
	private RoleDao roleDao;
	private UserDao userDao;
	private RightDao rightDao;
	private IpDao ipDao;
	private IpInUseDao ipInUseDao;
	private RosConnIpCacheDao rosConnIpCacheDao;
	
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
	
	

	public RosConnIpCacheDao getRosConnIpCacheDao() {
		return rosConnIpCacheDao;
	}

	public void setRosConnIpCacheDao(RosConnIpCacheDao rosConnIpCacheDao) {
		this.rosConnIpCacheDao = rosConnIpCacheDao;
	}

	@Override
	public List<RosConnIpCache> getAllIpsByRosLocation(String rosLocation) {
		return rosConnIpCacheDao.getAllIpsByLocation(rosLocation);
	}

	@Override
	public void updateAllConnIpByRosLocation(String rosLocation, List<RosConnIpCache> list) {
		for (RosConnIpCache cache : list) {
			cache.setRosLocation(rosLocation);
			rosConnIpCacheDao.saveOrUpdate(cache);
		}
	}

	@Override
	public RosConnIpCache getRosConnIpCacheByIpValue(String ipValue) {
		return this.rosConnIpCacheDao.getRosConnIpCacheByIpValue(ipValue);
	}

	@Override
	public RosConnIpCache getRosConnIpCacheByPppoeAndLocation(String pppoe,
			String location) {
		return this.rosConnIpCacheDao.getRosConnIpCacheByPppoeAndLocation(pppoe, location);
	}

}
