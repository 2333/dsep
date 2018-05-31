package com.dsep.dao.dsepmeta.databackup.impl;

import java.util.List;

import com.dsep.dao.common.impl.ExpDaoImpl;
import com.dsep.dao.dsepmeta.databackup.TestDao;
import com.meta.entity.PgshTest;

public class TestDaoImpl extends ExpDaoImpl<PgshTest> implements TestDao{

	@Override
	public List<PgshTest> getPgsh() throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		String tableName = "pgsh_test";
		return super.getAll(tableName);
	}
	
}
