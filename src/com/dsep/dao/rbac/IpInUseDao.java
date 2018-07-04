package com.dsep.dao.rbac;

import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.IpInUse;

public interface IpInUseDao extends Dao<IpInUse,Integer>
{
	
	/**
	 * 获取用户的所有ip
	 * @param userId
	 * @return
	 */
	public abstract List<IpInUse> getIpInUse(Integer userId, Integer ipId);
	
	public abstract List<IpInUse> getIpInUse(Integer userId);
}
