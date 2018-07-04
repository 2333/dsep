package com.dsep.dao.rbac;

import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.RosConnIpCache;

public interface RosConnIpCacheDao extends Dao<RosConnIpCache,Integer>
{
	
	/**
	 * 获取用户的所有ip
	 * @param userId
	 * @return
	 */
	public abstract List<RosConnIpCache> getAllIpsByLocation(String rosLocation);
	
	
	public abstract void updateCacheByLocation(RosConnIpCache cache, String rosLocation);
	
	public abstract RosConnIpCache getRosConnIpCacheByIpValue(String ipValue);
	
	public abstract RosConnIpCache getRosConnIpCacheByPppoeAndLocation(String pppoe, String location);
}
