package com.dsep.service.admin.impl;

import java.util.List;
import java.util.Map;

import com.dsep.dao.admin.SqlHelperDao;
import com.dsep.service.admin.SqlHelperService;
import com.dsep.vm.resultSet.ResultSetVM;

public class SqlHelperServiceImpl implements SqlHelperService{

	private SqlHelperDao sqlHelperDao;
	

	public SqlHelperDao getSqlHelperDao() {
		return sqlHelperDao;
	}

	public void setSqlHelperDao(SqlHelperDao sqlHelperDao) {
		this.sqlHelperDao = sqlHelperDao;
	}

	@Override
	public int executeUpdateOrSave(String sql) {
		// TODO Auto-generated method stub
		return sqlHelperDao.excuteUpdateOrSave(sql);
	}

	@Override
	public List<Map<String, Object>> executeSelect(String sql) {
		// TODO Auto-generated method stub
		return sqlHelperDao.excuteSelect(sql);
	}

	@Override
	public ResultSetVM getResultSetVM(String sql) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> rowsMap = executeSelect(sql);
		ResultSetVM resultSetVM = null;
		if(rowsMap!=null&&rowsMap.size()>0){
			resultSetVM = new ResultSetVM(rowsMap.get(0).keySet(),rowsMap);
		}
		return resultSetVM;
	}

}
