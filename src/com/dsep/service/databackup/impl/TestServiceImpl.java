package com.dsep.service.databackup.impl;

import java.util.List;

import com.dsep.dao.dsepmeta.databackup.TestDao;
import com.dsep.service.databackup.TestService;
import com.meta.entity.PgshTest;

public class TestServiceImpl implements TestService{

	private TestDao testDao;
	
	public void setTestDao(TestDao testDao) {
		this.testDao = testDao;
	}

	@Override
	public List<PgshTest> getPgshTest() throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return testDao.getPgsh();
	}

}
