package com.dsep.service.databackup;

import java.util.List;

import com.meta.entity.PgshTest;


public interface TestService {
	public List<PgshTest> getPgshTest() throws InstantiationException, IllegalAccessException;
}
