package com.dsep.dao.admin.impl;

import java.util.List;
import java.util.Map;

import com.dsep.dao.admin.SqlHelperDao;
import com.dsep.dao.common.impl.DaoImpl;

public class SqlHelperDaoImpl extends DaoImpl<Object, String> implements SqlHelperDao{

	
	@Override
	public int excuteUpdateOrSave(String sql) {
		// TODO Auto-generated method stub
		return super.sqlBulkUpdate(sql);
	}

	@Override
	public List<Map<String, Object>> excuteSelect(String sql) {
		// TODO Auto-generated method stub
		return super.sqlQuery(sql);
	}
	
}
