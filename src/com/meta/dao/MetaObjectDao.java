package com.meta.dao;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.Dao;
import com.meta.entity.MetaObject;

public interface MetaObjectDao extends Dao<Object, String> {
	/**
	 * 直接执行Sql语句
	 * 
	 * @param sql
	 *            需要执行的Sql语句
	 * @param parmas
	 *            Sql语句对应的参数表
	 * @return 执行该Sql语句后影响的记录数
	 */
	public abstract int sqlBulkUpdate(String sql, Object[] params);

	/**
	 * 根据Sql语句获得结果列表
	 * 
	 * @param sql
	 *            带参数的sql语句
	 * @param columnNames
	 *            数据库真实字段名
	 * @param columnTypes
	 *            字段类型，缺省可以置null
	 * @param params
	 *            参数表
	 * @param addOps
	 *            需要对数据执行的附加操作
	 * @param pageIndex
	 *            当前页号，从1开始, 0表示不需要分页（返回所有记录）
	 * @param pageSize
	 *            每页的记录记录数
	 * @return 返回数据列表，列表中的成员以Map形式存储，key为字段名，value为字段值
	 */
	public abstract List<Map<String, Object>> sqlGetListMapObject(String sql,
			String[] columnNames, org.hibernate.type.Type[] columnTypes,
			Object[] params, List<AdditionalOperation> addOps, int pageIndex,
			int pageSize);

	/**
	 * 根据Sql语句获得结果列表(以String的形式返回结果)
	 * 
	 * @param sql
	 *            带参数的sql语句
	 * @param columnNames
	 *            数据库真实字段名
	 * @param columnTypes
	 *            字段类型，缺省可以置null
	 * @param params
	 *            参数表
	 * @param addOps
	 *            需要对数据执行的附加操作
	 * @param pageIndex
	 *            当前页号，从1开始, 0表示不需要分页（返回所有记录）
	 * @param pageSize
	 *            每页的记录记录数
	 * @return 返回数据列表，列表中的成员以Map形式存储，key为字段名，value为字段值
	 */
	public abstract List<Map<String, String>> sqlGetListMap(String sql,
			String[] columnNames, org.hibernate.type.Type[] columnTypes,
			Object[] params, List<AdditionalOperation> addOps, int pageIndex,
			int pageSize);
	
	/**
	 * 执行统计结果数量的SQL语句
	 * 
	 * @param sql
	 *            需要时带count的SQL语句
	 * @param params
	 *            该SQL语句对应的参数
	 * @return 记录总数
	 */
	public abstract int sqlCount(String sql, Object[] params);
}
