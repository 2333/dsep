package com.dsep.dao.rbac.impl;

import java.util.List;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.dao.rbac.UserIpHeartDao;
import com.dsep.entity.UserIpHeart;

 public class UserIpHeartDaoImpl extends DaoImpl<UserIpHeart,Integer> implements UserIpHeartDao {

	@Override
	public List<UserIpHeart> getNoHeartData() {
		
		// 选择出所有5分钟内未出现的，且需要检测的(flag=0)的machineid
		//String sql = "select * from dsep_rbac_useripheart where machineid not in "
		//		+ "(select machineid from dsep_rbac_useripheart where (time_to_sec(now()) - time_to_sec(date_format(lastrecordtimeStr, '%Y-%m-%d %H:%i:%s'))) <= 30) "
		//		+ "and flag = 0";
		
		String sql = "select * from dsep_rbac_useripheart where (time_to_sec(now()) - time_to_sec(date_format(lastrecordtimeStr, '%Y-%m-%d %H:%i:%s'))) > 30 and flag <> 1";
		List<UserIpHeart> list = super.sqlFind(sql);
		return list;
	}

	@Override
	public void updateHeartDataMoreThan300SecondsNotAttendCalc() {
		// 把过去5分钟的数据设置为不用检测(flag=1)
		String sql = "update dsep_rbac_useripheart set flag = 1 where (time_to_sec(now()) - time_to_sec(date_format(lastrecordtimeStr, '%Y-%m-%d %H:%i:%s'))) > 30";
		super.sqlBulkUpdate(sql);
		//return list;
	}
	
	
}
