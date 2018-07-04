package com.dsep.service.rbac.impl;
/**
 * @author fanghongyu
 */
import java.util.List;

import com.dsep.dao.rbac.UserIpLogDao;
import com.dsep.entity.UserIpHeart;
import com.dsep.entity.UserIpLog;
import com.dsep.service.rbac.UserIpLogService;
import com.dsep.vm.PageVM;

public class UserIpLogServiceImpl implements UserIpLogService
{
	private UserIpLogDao userIpLogDao;

	public UserIpLogDao getUserIpLogDao() {
		return userIpLogDao;
	}

	public void setUserIpLogDao(UserIpLogDao userIpLogDao) {
		this.userIpLogDao = userIpLogDao;
	}

	@Override
	public void save(UserIpLog userIpLog) {
		userIpLogDao.save(userIpLog);
	}

	@Override
	public PageVM<UserIpLog> userIpLogQuery(int pageIndex, int pageSize,
			Boolean desc, String orderProperName) {
		List<UserIpLog> list=userIpLogDao.page(pageIndex, pageSize, desc, orderProperName);
		int totalCount=userIpLogDao.Count();
		PageVM<UserIpLog> result=new PageVM<UserIpLog>(pageIndex,totalCount,pageSize,list);
		return result;
	}
	
}
