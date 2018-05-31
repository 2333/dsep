package com.meta.service.sql;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 动态表的Sql语句执行器
 * 
 * @author thbin
 * 
 */
@Transactional(propagation = Propagation.SUPPORTS)
public interface SqlExecutor {
	/**
	 * 执行SqlBuilder生成的Sql语句 直接执行，不返回结构，一般执行数据库DDL和DML中的更新、插入和删除语句
	 * 
	 * @param sqlBuilder
	 *            需要执行的SQL语句和参数
	 * @return 执行操作后影响的记录数
	 */
	public abstract int execUpdate(SqlBuilder sqlBuilder);

	/**
	 * 执行SqlBuilder生成的Sql查询语句（返回对象类型），不分页
	 * 
	 * @param sqlBuilder
	 *            需要执行的SQL语句、参数和其它信息
	 * @return 执行查询后返回的结果集，以List<Map>的形式返回
	 */
	public abstract List<Map<String, Object>> execQueryObject(
			SqlBuilder sqlBuilder);

	/**
	 * 执行SqlBuilder生成的Sql查询语句（返回对象类型），分页
	 * 
	 * @param sqlBuilder
	 *            需要执行的SQL语句和参数
	 * @param pageIndex
	 *            当前页号（从1开始，0表示不分页）
	 * @param pageSize
	 *            页大小
	 * @return 执行查询结果后，返回的结果集，以List<Map>的形式返回，key-为字段名，map-为数据值
	 */
	public abstract List<Map<String, Object>> execQueryObject(
			SqlBuilder sqlBuilder, int pageIndex, int pageSize);

	/**
	 * 执行SqlBuilder生成的Sql查询语句(返回字符串类型)，不分页
	 * 
	 * @param sqlBuilder
	 *            需要执行的SQL语句和参数
	 * @param pageIndex
	 *            当前页号
	 * @param pageSize
	 *            页大小
	 * @return 执行查询结果后，返回的结果集，以List<Map>的形式返回，key-为字段名，map-为数据值
	 */
	public abstract List<Map<String, String>> execQuery(SqlBuilder sqlBuilder,
			int pageIndex, int pageSize);

	/**
	 * 直接执行SqlBuilder中的SQL语句（注意需要设置返回的结果的字段名），并返回结果集
	 * 
	 * @param sqlBuilder
	 *            需要执行的SQL语句和参数
	 * @return
	 */
	public abstract List<Map<String, Object>> execSQLQuery(
			SqlBuilder sqlBuilder, int pageIndex, int pageSize);

	/**
	 * 执行Count语句，返回记录数
	 * 
	 * @param sqlBuilder
	 * @return
	 */
	public abstract int execCountQuery(SqlBuilder sqlBuilder);
}
