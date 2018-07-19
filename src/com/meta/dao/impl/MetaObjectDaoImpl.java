package com.meta.dao.impl;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.type.DateType;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.util.Configurations;
import com.meta.dao.AdditionalOperation;
import com.meta.dao.MetaObjectDao;
import com.meta.entity.MetaObject;

public class MetaObjectDaoImpl extends DaoImpl<Object, String> implements
		MetaObjectDao {

	public int sqlBulkUpdate(String sql, Object[] params) {
		if ((params == null) || (params.length == 0)) {
			return super.sqlBulkUpdate(sql);
		} else {
			return super.sqlBulkUpdate(sql, params);
		}
	}

	public int sqlCount(String sql, Object[] params) {
		if ((params == null) || (params.length == 0)) {
			return super.sqlCount(sql);
		} else {
			return super.sqlCount(sql, params);
		}
	}
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
	public List<Map<String, Object>> sqlGetListMapObject(String sql,
			String[] columnNames, org.hibernate.type.Type[] columnTypes,
			Object[] params, List<AdditionalOperation> addOps, int pageIndex,
			int pageSize) {
		SQLQuery q = super.getSession().createSQLQuery(sql);
		for (int i = 0; i < params.length; i++)
			q.setParameter(i, params[i]);
		if(columnNames!=null){
		for (int i = 0; i < columnNames.length; i++) {
			if (columnTypes == null) {
				q.addScalar(columnNames[i]);
			} else {
				q.addScalar(columnNames[i], columnTypes[i]);
			}
		}
		}
		if (pageIndex > 0) {
			// 计算第一个结果index
			int firstResultIndex = (pageIndex - 1) * pageSize;
			q.setFirstResult(firstResultIndex).setMaxResults(pageSize);
		}
		List<Object[]> data = q.list();
		// 转换数据
		List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
		for (Object[] r : data) {
			Map<String, Object> row = new LinkedHashMap<String, Object>();
			for (int i = 0; i < r.length; i++) {
				// if(r[i] == null)
				// r[i] = "";
				// row.put(columnNames[i],r[i].toString());
				row.put(columnNames[i], r[i]);
			}
			if (addOps != null) {
				for (AdditionalOperation op : addOps) {
					op.execObject(row);
				}
			}
			result.add(row);
		}
		return result;
	}
	/**
	 * 执行SQL语句，返回数据对象，按照Select语句的顺序返回
	 * @param sql SQL语句
	 * @param params 参数
	 * @param pageIndex 页号
	 * @param pageSize 页大小
	 * @return
	 */
	public List<Object[]> sqlGetListObject(String sql, Object[] params,  int pageIndex, int pageSize) {
		SQLQuery q = super.getSession().createSQLQuery(sql);
		for (int i = 0; i < params.length; i++)
			q.setParameter(i, params[i]);
		if (pageIndex > 0) {
			// 计算第一个结果index
			int firstResultIndex = (pageIndex - 1) * pageSize;
			q.setFirstResult(firstResultIndex).setMaxResults(pageSize);
		}
		List<Object[]> data = q.list();
		return data;
	}
	protected  List<MetaObject> sqlGetEntity(String sql,
			Object[] params, Class cls, int pageIndex, int pageSize){
		SQLQuery q = super.getSession().createSQLQuery(sql);
		for (int i = 0; i < params.length; i++)
			q.setParameter(i, params[i]);
		if (pageIndex > 0) {
			// 计算第一个结果index
			int firstResultIndex = (pageIndex - 1) * pageSize;
			q.setFirstResult(firstResultIndex).setMaxResults(pageSize);
		}
		q.addEntity(cls);
		return q.list();
	}
	protected SQLQuery getSQLQuery(String sql,
			Object[] params, Class<?> cls, int pageIndex, int pageSize){
		SQLQuery q = super.getSession().createSQLQuery(sql);
		for (int i = 0; i < params.length; i++)
			q.setParameter(i, params[i]);
		if (pageIndex > 0) {
			// 计算第一个结果index
			int firstResultIndex = (pageIndex - 1) * pageSize;
			q.setFirstResult(firstResultIndex).setMaxResults(pageSize);
		}
		q.addEntity(cls);
		return q;
	}
			
	// 元数据模型添加的功能函数
	/**
	 * 根据Sql语句获得结果列表（返回String形式的结果）
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
	public List<Map<String, String>> sqlGetListMap(String sql,
			String[] columnNames, org.hibernate.type.Type[] columnTypes,
			Object[] params, List<AdditionalOperation> addOps, int pageIndex,
			int pageSize) {
		SQLQuery q = super.getSession().createSQLQuery(sql);
		if(params!=null){
			for (int i = 0; i < params.length; i++)
				q.setParameter(i, params[i]);
		}
		if(params!=null){
			for (int i = 0; i < columnNames.length; i++) {
				if (columnTypes == null) {
					q.addScalar(columnNames[i]);
				} else {
					q.addScalar(columnNames[i], columnTypes[i]);
				}
			}
		}
		if (pageIndex > 0) {
			// 计算第一个结果index
			int firstResultIndex = (pageIndex - 1) * pageSize;
			q.setFirstResult(firstResultIndex).setMaxResults(pageSize);
		}
		List<Object[]> data = q.list();
		// 转换数据
		List<Map<String, String>> result = new LinkedList<Map<String, String>>();
		if(columnNames.length==1){
			for (Object r : data) {
				Map<String, String> row = new LinkedHashMap<String, String>();
				row.put(columnNames[0], r.toString());
				if (addOps != null) {
					for (AdditionalOperation op : addOps) {
						op.exec(row);
					}
				}
				result.add(row);
			}
		}else{
			for (Object[] r : data) {
				Map<String, String> row = new LinkedHashMap<String, String>();
				for (int i = 0; i < r.length; i++) {
					if (r[i] == null) {
						r[i] = "";
					}
					row.put(columnNames[i], r[i].toString());
				}
				if (addOps != null) {
					for (AdditionalOperation op : addOps) {
						op.exec(row);
					}
				}
				result.add(row);
			}
		}
		
		return result;
	}
}
