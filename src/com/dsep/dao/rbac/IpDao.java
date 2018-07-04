package com.dsep.dao.rbac;

import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.Ip;

public interface IpDao extends Dao<Ip,Integer>
{
	
	/**
	 * 获取用户的所有ip
	 * @param userId
	 * @return
	 */
	public abstract List<Ip> getUserIps(Integer userId);
	
	public abstract Ip getIpByIpValue(String ipValue);
}
