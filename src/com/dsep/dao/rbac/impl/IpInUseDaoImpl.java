package com.dsep.dao.rbac.impl;

import java.util.List;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.dao.rbac.IpInUseDao;
import com.dsep.entity.IpInUse;



public class IpInUseDaoImpl extends DaoImpl<IpInUse,Integer> implements IpInUseDao{

	@Override
	public List<IpInUse> getIpInUse(Integer userId, Integer ipId) {
		return null;
	}

	@Override
	public List<IpInUse> getIpInUse(Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select * from dsep_rbac_ip where id in"
				+ "(select ip_id from dsep_rbac_user_ip where user_id=?)";
		List<IpInUse> list = super.sqlFind(sql, new Object[] {userId});
		return list;
	}

	
}
