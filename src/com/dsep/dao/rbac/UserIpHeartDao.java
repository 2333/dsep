package com.dsep.dao.rbac;
import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.UserIpHeart;


public interface  UserIpHeartDao extends Dao<UserIpHeart,Integer> {	
	public abstract List<UserIpHeart> getNoHeartData();
	
}
