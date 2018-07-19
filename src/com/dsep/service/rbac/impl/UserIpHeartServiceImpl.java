package com.dsep.service.rbac.impl;
import java.util.List;

import com.dsep.dao.rbac.UserIpHeartDao;
import com.dsep.entity.Ip;
import com.dsep.entity.UserIpHeart;
/**
 * @author fanghongyu
 */
import com.dsep.service.rbac.UserIpHeartService;
import com.dsep.vm.PageVM;

public class UserIpHeartServiceImpl implements UserIpHeartService
{
	private UserIpHeartDao userIpHeartDao;

	public UserIpHeartDao getUserIpHeartDao() {
		return userIpHeartDao;
	}

	public void setUserIpHeartDao(UserIpHeartDao userIpHeartDao) {
		this.userIpHeartDao = userIpHeartDao;
	}

	@Override
	public void save(UserIpHeart userIpHeart) {
		userIpHeartDao.save(userIpHeart);
	}

	@Override
	public PageVM<UserIpHeart> userIpHeartQuery(int pageIndex, int pageSize,
			Boolean desc, String orderProperName) {
		List<UserIpHeart> list=userIpHeartDao.page(pageIndex, pageSize, desc, orderProperName);
		int totalCount=userIpHeartDao.Count();
		PageVM<UserIpHeart> result=new PageVM<UserIpHeart>(pageIndex,totalCount,pageSize,list);
		return result;
	}

	@Override
	public List<UserIpHeart> getNoHeartData() {
		List<UserIpHeart> list = userIpHeartDao.getNoHeartData();
		return list;
	}

	@Override
	public List<UserIpHeart> updateHeartDataMoreThan300SecondsNotAttendCalc() {
		userIpHeartDao.updateHeartDataMoreThan300SecondsNotAttendCalc();
		return null;
	}
}
