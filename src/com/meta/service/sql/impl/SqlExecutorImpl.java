package com.meta.service.sql.impl;

import java.util.List;
import java.util.Map;

import com.meta.dao.MetaObjectDao;
import com.meta.service.sql.SqlBuilder;
import com.meta.service.sql.SqlExecutor;

public class SqlExecutorImpl implements	SqlExecutor {
	
	private MetaObjectDao metaObjectDao; 
	
	public int execUpdate(SqlBuilder sqlBuilder) {
		int result = -1;
		String[] trueSqls = sqlBuilder.getSql().split(";");
		for(int i=0; i<trueSqls.length; i++){
			if (trueSqls[i].trim().length() > 0) {
				result = metaObjectDao.sqlBulkUpdate(trueSqls[i],
						sqlBuilder.getParameters().toArray());
			}
		}
		return result;
	}

	public List<Map<String, Object>> execQueryObject(SqlBuilder sqlBuilder) {
		return this.execQueryObject(sqlBuilder, 0, 0);
	}

	public List<Map<String, Object>> execQueryObject(SqlBuilder sqlBuilder,int pageIndex, int pageSize) {
		String[] columnNames = new String[sqlBuilder.getColumnNames().size()];
		org.hibernate.type.Type[] columnTypes = new org.hibernate.type.Type[sqlBuilder.getColumnNames().size()];
		sqlBuilder.getColumnNames().toArray(columnNames);
		sqlBuilder.getHibernateColumnTypes().toArray(columnTypes);
		
		return metaObjectDao.sqlGetListMapObject(sqlBuilder.getSql(), columnNames, 
				columnTypes, sqlBuilder.getParameters().toArray(),
				sqlBuilder.getAddOps(),
				pageIndex, pageSize);
		
	}
	
	public List<Map<String, String>> execQuery(SqlBuilder sqlBuilder,int pageIndex, int pageSize) {
		String[] columnNames = new String[sqlBuilder.getColumnNames().size()];
		org.hibernate.type.Type[] columnTypes = new org.hibernate.type.Type[sqlBuilder.getColumnNames().size()];
		sqlBuilder.getColumnNames().toArray(columnNames);
		sqlBuilder.getHibernateColumnTypes().toArray(columnTypes);
		//先临时后面转换一下，以后有时间再添加相应的接口（效率较低）
		return metaObjectDao.sqlGetListMap(sqlBuilder.getSql(), columnNames, 
				columnTypes, sqlBuilder.getParameters().toArray(),
				sqlBuilder.getAddOps(),
				pageIndex, pageSize);
		/*List<Map<String, String>> destList = new LinkedList<Map<String, String>>();				
		for(Map<String, Object> srcRow : srcList){
			Map<String,String> destRow = new LinkedHashMap<String,String>();
			for(String key: srcRow.keySet()){
				Object srcValue = srcRow.get(key);
				String destValue = "";
				if(srcValue!=null){
					destValue = srcValue.toString();
				}
				destRow.put(key, destValue);
			}
			destList.add(destRow);
		}
		return destList;*/
	}
	
	public  List<Map<String, Object>> execSQLQuery(SqlBuilder sqlBuilder,int pageIndex, int pageSize){
		String[] columnNames = new String[sqlBuilder.getColumnNames().size()];
		sqlBuilder.getColumnNames().toArray(columnNames);
		return metaObjectDao.sqlGetListMapObject(sqlBuilder.getSql(), columnNames, 
				null, sqlBuilder.getParameters().toArray(),
				null,
				pageIndex, pageSize);
	}
	public  int execCountQuery(SqlBuilder sqlBuilder){
		return metaObjectDao.sqlCount(sqlBuilder.getSql(), sqlBuilder.getParameters().toArray());
	}

	public MetaObjectDao getMetaObjectDao() {
		return metaObjectDao;
	}

	public void setMetaObjectDao(MetaObjectDao metaObjectDao) {
		this.metaObjectDao = metaObjectDao;
	}
	
	
}
