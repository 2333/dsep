package com.dsep.service.dsepmeta;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.meta.dao.MetaObjectDao;
/**
 * 直接通过指定表名、字段名和查询条件等基本SQL语句进行数据库操作
 * @author thbin
 *
 */
public class BaseDBOper {
	private MetaObjectDao metaObjectDao;

	/**
	 * 通过指定表名、字段名和查询条件等查询数据
	 * @param tableName 表名
	 * @param fieldNames 字段列表
	 * @param sqlCondition 查询条件
	 * @return
	 */
	public List<Map<String, String>> getRowData(String tableName, String[] fieldNames,
			String sqlCondition){
		return getRowData(tableName, fieldNames,sqlCondition, "", true, 0, 0);
	}
	
	/**
	 * 通过指定表名、字段名和查询条件等查询数据
	 * @param tableName 表名
	 * @param fieldNames 字段列表
	 * @param sqlCondition 查询条件
	 * @param orderField 排序字段
	 * @param asc 是否升序
	 * @param pageIndex 返回页代码，为0则不分页
	 * @param pageSize 返回数据页大小
	 * @return
	 */
	public List<Map<String, String>> getRowData(String tableName, String[] fieldNames,
			String sqlCondition, String orderField, boolean asc, int pageIndex, int pageSize){
		StringBuilder sql = new StringBuilder("select ");
		boolean isFirst = true;
		for(String fieldName: fieldNames){
			if(!isFirst) sql.append(",");
			sql.append(fieldName);
			isFirst = false;
		}
		sql.append(" from ").append(tableName);
		if(StringUtils.isNotBlank(sqlCondition)){
			sql.append(" where ").append(sqlCondition);
		}
		if(StringUtils.isNotBlank(orderField)){
			sql.append(" order by ").append(orderField);
			if(asc) sql.append(" asc ");
			else sql.append(" desc ");
		}
		return metaObjectDao.sqlGetListMap(sql.toString(), fieldNames, null, null, null, pageIndex, pageSize);		
	}
	public MetaObjectDao getMetaObjectDao() {
		return metaObjectDao;
	}

	public void setMetaObjectDao(MetaObjectDao metaObjectDao) {
		this.metaObjectDao = metaObjectDao;
	}
}
