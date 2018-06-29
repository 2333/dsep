package com.dsep.service.admin;

import java.util.List;
import java.util.Map;

import com.dsep.vm.resultSet.ResultSetVM;

public interface SqlHelperService {
	
	/**
	 * 执行更新或者保存sql语句
	 * @param sql
	 * @return
	 */
	abstract public int executeUpdateOrSave(String sql);
	
	/**
	 * 执行查询sql
	 * @param sql
	 * @return
	 */
	abstract public List<Map<String,Object>> executeSelect(String sql);
	
	/**
	 * 获取一张表
	 * @param sql
	 * @return
	 */
	abstract public ResultSetVM getResultSetVM(String sql);
	
}
