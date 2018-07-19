package com.dsep.dao.dsepmeta.databackup;

import java.util.List;

import com.dsep.dao.common.ExpDao;
import com.meta.entity.PgshTest;

public interface TestDao extends ExpDao<PgshTest>{
	public List<PgshTest> getPgsh() throws InstantiationException, IllegalAccessException;
}
