package com.dsep.dao.rbac;
import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.UserIpHeart;


public interface  UserIpHeartDao extends Dao<UserIpHeart,Integer> {	
	public abstract List<UserIpHeart> getNoHeartData();
	
	// 300s，即5分钟以上的数据全部设置为标记位，不再参与运算
	public abstract void updateHeartDataMoreThan300SecondsNotAttendCalc();
	
}
