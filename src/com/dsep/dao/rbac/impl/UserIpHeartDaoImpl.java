package com.dsep.dao.rbac.impl;

import java.util.List;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.dao.rbac.UserIpHeartDao;
import com.dsep.entity.UserIpHeart;

 public class UserIpHeartDaoImpl extends DaoImpl<UserIpHeart,Integer> implements UserIpHeartDao {

	@Override
	public List<UserIpHeart> getNoHeartData() {
		String sql = "select * from dsep_rbac_useripheart where (time_to_sec(now()) - time_to_sec(lastrecordtime)) > 300";
		List<UserIpHeart> list = super.sqlFind(sql);
		return list;
	}
	
	
}
