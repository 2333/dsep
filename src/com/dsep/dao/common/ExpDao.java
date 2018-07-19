package com.dsep.dao.common;

import java.util.List;

public interface ExpDao<T> {

	/**
	 * 获取某数据表的所有数据，以实体集合形式封装后返回
	 * 如：dsep_expert表对应expert类，此函数返回expert实体的集合
	 * @param tableName 表名
	 * @return 实体的集合
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public abstract List<T> getAll(String tableName) throws InstantiationException, IllegalAccessException;
	
	/**
	 * 根据sql查询语句获取某张表的数据，并以实体集合形式封装后返回
	 * @param sql sql查询语句
	 * @return 实体集合
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public abstract List<T> getAllBySql(String sql) throws InstantiationException, IllegalAccessException;
	
	/**
	 * 通过SQL获取记录数
	 * @return
	 */
	public abstract int getCountBySql(String sql);
}
