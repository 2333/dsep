package com.dsep.dao.rbac.impl;

import java.util.List;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.dao.rbac.IpDao;
import com.dsep.entity.Ip;



public class IpDaoImpl extends DaoImpl<Ip,Integer> implements IpDao{
	@Override
	public List<Ip> getUserIps(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select * from dsep_rbac_ip where id in"
				+ "(select ip_id from dsep_rbac_user_ip where user_id=?)";
		List<Ip> list = super.sqlFind(sql, new Object[]{userId});
		return list;
	}

	@Override
	public Ip getIpByIpValue(String ipValue) {
		return null;
	}

	
}
