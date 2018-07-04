package com.meta.service.sql.impl;

import java.util.Map;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.dsep.util.GUID;
import com.meta.dao.AdditionalOperation;
import com.meta.entity.MetaAttributeMap;
import com.meta.entity.MetaDataType;
import com.meta.entity.MetaEntity;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaEntityMap;
import com.meta.service.factory.SqlHelperFactory;
import com.meta.service.sql.SqlBuilder;
import com.meta.service.sql.SqlDDLHelper;
import com.meta.service.sql.SqlDMLHelper;

/**
 * SqlBuilder抽象类的Oracle实现
 * 
 * @author thbin
 * 
 */
public class SqlBuilderImpl extends SqlBuilder {

	@Override
	public void buildSingleSelectSql(MetaEntity entity,
			Map<String, Object> params, String orderPropName, boolean asc) {
		buildSingleSelectSql(entity, params, orderPropName, asc, "");
	}
	@Override
	public void buildSingleSelectSql(MetaEntity entity,
			Map<String, Object> params, String orderPropName, boolean asc, String versionId) {
		buildSingleSelectSql(entity, params, "", orderPropName, asc, versionId);
	}

	@Override
	public void buildSingleSelectSql(MetaEntity entity, List<String> attrNames,	
			Map<String, Object> params, String orderPropName, boolean asc){
		buildSingleSelectSql(entity, attrNames, params, orderPropName, asc, "");
	}
	@Override
	public void buildSingleSelectSql(MetaEntity entity, List<String> attrNames,	
			Map<String, Object> params, String orderPropName, boolean asc, String versionId) {
		buildSingleSelectSql(entity, attrNames, params, "", orderPropName, asc, versionId);
	}
	
	@Override
	public void buildSingleSelectSql(MetaEntity entity, List<String> attrNames,Map<String, Object> params,
			String sqlCondition,String orderPropName,boolean asc) {
		buildSingleSelectSql(entity, attrNames, params, sqlCondition, orderPropName, asc,"");
	}
	@Override
	public void buildSingleSelectSql(MetaEntity entity, List<String> attrNames,Map<String, Object> params,
			String sqlCondition,String orderPropName,boolean asc, String versionId) {
		// TODO Auto-generated method stub
		clear();
		// 获得排序属性对应的字段名
		StringBuilder orderSql = new StringBuilder("");

		boolean bFirst = true,fseqno=false;
		StringBuilder columnSql = new StringBuilder("");
		for(String attrName: attrNames){
			MetaAttribute foundAttr = null;
			for (MetaAttribute attr : entity.getAttributes()){
				if(!fseqno)
				{
					SqlDMLHelper sqlHelper1 = SqlHelperFactory.newSqlDMLHelper(attr);
					if ((orderPropName != null) && orderPropName.equals(attr.getName())) {
						fseqno=true;
						if(isNumberColumn(entity, orderPropName)){
							orderSql.append(sqlHelper1.getColumnName());
						}else{
							orderSql.append(sqlHelper1.getColumnName());
							//orderSql.append("nlssort("+sqlHelper1.getColumnName()+",'NLS_SORT=SCHINESE_PINYIN_M')");
						}
					}
				}
				if(attrName.equals(attr.getName())){
					foundAttr = attr;
					break;
				}
				
			}
			if(foundAttr!=null){
				SqlDMLHelper sqlHelper = SqlHelperFactory.newSqlDMLHelper(foundAttr);
				
				/*if ((orderPropName != null) && orderPropName.equals(foundAttr.getName())) {
					if(isNumberColumn(entity, orderPropName)){
						orderSql.append(sqlHelper.getColumnName());
					}else{
						orderSql.append("nlssort("+sqlHelper.getColumnName()+",'NLS_SORT=SCHINESE_PINYIN_M')");
					}
				}*/

				List<String> aColumnNames = sqlHelper.getColumnNames();
				columnNames.addAll(aColumnNames);
				columnTypes.addAll(sqlHelper.getColumnTypes());
				List<AdditionalOperation> addOps = sqlHelper.getAddOps();
				if (addOps != null)
					this.addOps.addAll(addOps);
				for (String columnName : aColumnNames) {
					if (bFirst) {
						bFirst = false;
					} else {
						columnSql.append(", ");
					}
					columnSql.append(columnName);
				}
			}
		}	
		if (orderSql.length() > 0) {
			if (asc) {
				orderSql.append(" asc");
			} else {
				orderSql.append(" desc");
			}
		}
		innerBuildSingleSelectSql(entity, columnSql.toString(),params,sqlCondition,orderSql.toString(), versionId);
		
	}
	/**
	 * 获取数据表中的所有字段，通过条件查询
	 * @param entity
	 * @param params
	 * @param sqlCondition
	 * @param orderPropName
	 * @param asc
	 */
	@Override
	public void buildSingleSelectSql(MetaEntity entity,Map<String, Object> params,String sqlCondition,
			String orderPropName, boolean asc){
		buildSingleSelectSql(entity, params,sqlCondition, orderPropName, asc,"");
	}
	@Override
	public void buildSingleSelectSql(MetaEntity entity,Map<String, Object> params,String sqlCondition,
			String orderPropName,boolean asc, String versionId){
		clear();
		// 获得排序属性对应的字段名
		StringBuilder orderSql = new StringBuilder("");

		boolean bFirst = true;
		StringBuilder columnSql = new StringBuilder("");
		for (MetaAttribute attr : entity.getAttributes()) {
			SqlDMLHelper sqlHelper = SqlHelperFactory.newSqlDMLHelper(attr);

			if ((orderPropName != null) && orderPropName.equals(attr.getName())) {
				if(isNumberColumn(entity, orderPropName)){
					orderSql.append(sqlHelper.getColumnName());
				}else{
					//orderSql.append("nlssort("+sqlHelper.getColumnName()+",'NLS_SORT=SCHINESE_PINYIN_M')");
					orderSql.append(sqlHelper.getColumnName());
				}
				
			}

			List<String> aColumnNames = sqlHelper.getColumnNames();
			columnNames.addAll(aColumnNames);
			columnTypes.addAll(sqlHelper.getColumnTypes());
			List<AdditionalOperation> addOps = sqlHelper.getAddOps();
			if (addOps != null)
				this.addOps.addAll(addOps);
			for (String columnName : aColumnNames) {
				if (bFirst) {
					bFirst = false;
				} else {
					columnSql.append(", ");
				}
				columnSql.append(columnName);
			}
		}

		if (orderSql.length() > 0) {
			if (asc) {
				orderSql.append(" asc");
			} else {
				orderSql.append(" desc");
			}
		}
		innerBuildSingleSelectSql(entity, columnSql.toString(),params,sqlCondition,orderSql.toString(), versionId);
	}
	
	/*
	 * public void buildSingleSelectSql(MetaEntity entity, List<String>
	 * attrNames, Map<String, Object> params, String orderPropName, boolean asc)
	 * { clear(); //获得排序属性对应的字段名 StringBuilder orderSql = new StringBuilder("");
	 * 
	 * boolean bFirst = true; StringBuilder columnSql = new StringBuilder("");
	 * for(String attrName: attrNames){ Set<MetaAttribute> attrs=
	 * entity.getAttributes(); MetaAttribute attr = null;
	 * Iterator<MetaAttribute> iter = attrs.iterator(); boolean found= false;
	 * while(iter.hasNext()){ attr = iter.next();
	 * if(attr.getName().equals(attrName)){ found = true; break; } }
	 * if((found)&&(attr!=null)){ SqlDMLHelper sqlHelper =
	 * SqlHelperFactory.newSqlDMLHelper(attr); if ((orderPropName!=null) &&
	 * orderPropName.equals(attr.getName())) {
	 * orderSql.append(sqlHelper.getColumnName()); }
	 * 
	 * List<String> aColumnNames = sqlHelper.getColumnNames();
	 * columnNames.addAll(aColumnNames);
	 * columnTypes.addAll(sqlHelper.getColumnTypes()); List<AdditionalOperation>
	 * addOps = sqlHelper.getAddOps(); if (addOps != null)
	 * this.addOps.addAll(addOps); for (String columnName : aColumnNames) { if
	 * (bFirst) { bFirst = false; } else { columnSql.append(", "); }
	 * columnSql.append(columnName); }
	 * 
	 * } }
	 * 
	 * if (orderSql.length()>0) { if (asc) { orderSql.append(" asc"); } else {
	 * orderSql.append(" desc"); } } innerBuildSingleSelectSql(entity,
	 * columnSql.toString(), params, orderSql.toString());
	 * 
	 * }
	 */
	@Override
	public void buildSingleCountSql(MetaEntity entity,
			Map<String, Object> params, String countColumn){
		buildSingleCountSql(entity, params, countColumn,"");
	}
	@Override
	public void buildSingleCountSql(MetaEntity entity,
			Map<String, Object> params, String countColumn, String versionId) {
		buildSingleCountSql(entity,"",params,countColumn,"");
	}
	@Override
	public void buildSingleCountSql(MetaEntity entity, String sqlCondition,
			Map<String, Object> params, String countColumn) {
		// TODO Auto-generated method stub
		buildSingleCountSql(entity,sqlCondition,params,countColumn,"");
	}
	@Override
	public void buildSingleCountSql(MetaEntity entity, String sqlCondition,
			Map<String, Object> params, String countColumn, String versionId) {
		// TODO Auto-generated method stub
		clear();
		String countSql = " count(ID) ";
		if (StringUtils.isNotBlank(countColumn)) {
			countSql = " count( " + countColumn + ") ";
		}
		innerBuildSingleSelectSql(entity, countSql, params,sqlCondition, null, versionId);
	}
	
	@Override
	public void existTable(String tableName) {
		super.clear();
		sql = "select count(1)  from user_tables where table_name = ?";
		parameters.add(tableName);
	}
	@Override
	public void buildCreateTableSql(MetaEntity entity) {
		buildCreateTableSql(entity, "");
	}
	@Override
	public void buildCreateTableSql(MetaEntity entity, String versionId) {
		StringBuilder newSql = new StringBuilder();
		//newSql.append("create table " + entity.getName().toUpperCase() + "(");
		newSql.append("create table " + getTableName(entity, versionId).toUpperCase() + "(");
		Set<MetaAttribute> attrs = entity.getAttributes();
		boolean needDot = false;
		for (MetaAttribute attr : attrs) {
			SqlDDLHelper sqlHelper = SqlHelperFactory.newSqlDDLHelper(attr, versionId);
			String[] colNames = sqlHelper.getColumnNames();
			String[] typeNames = sqlHelper.getColumnTypeNames();
			String[] defaultValues = sqlHelper.getDefaultValueNames();
			for (int i = 0; i < colNames.length; i++) {
				if (needDot)
					newSql.append(",");
				newSql.append(colNames[i] + " " + typeNames[i] + " "
						+ defaultValues[i]);
				if (!needDot)
					needDot = true;
			}
		}
		newSql.append(")");
		sql = newSql.toString();
	}
	@Override
	public void buildDropTableSql(MetaEntity entity) {
		// TODO Auto-generated method stub
		buildDropTableSql(entity,"");
	}
	@Override
	public void buildDropTableSql(MetaEntity entity, String versionId) {
		// TODO Auto-generated method stub
		StringBuilder newSql = new StringBuilder();
		newSql.append("drop table " + getTableName(entity, versionId).toUpperCase());
		sql = newSql.toString();
	}
	@Override
	public void buildCreateIndexSql(MetaEntity entity){
		buildCreateIndexSql(entity, "");
	}
	@Override
	public void buildCreateIndexSql(MetaEntity entity, String versionId) {
		StringBuilder allSql = new StringBuilder("");
		clear();
		//String tableName = entity.getName();
		String tableName = getTableName(entity, versionId);
		int idxNum = 0;
		// 求总共的索引个数
		Set<MetaAttribute> attrs = entity.getAttributes();
		for (MetaAttribute attr : attrs) {
			if ((attr.getIndexNo() < 100) && (attr.getIndexNo() > idxNum)) {
				idxNum = attr.getIndexNo();
			}
		}
		if (idxNum < 1)
			return;
		String preSql = "create index idx_" + tableName + "_";
		for (int i = 1; i <= idxNum; i++) {
			StringBuilder newSql = new StringBuilder();
			newSql.append(i + " on " + tableName + "(");
			boolean needDot = false;
			for (MetaAttribute attr : attrs) {
				if (attr.getIndexNo() == i) {
					if (needDot)
						newSql.append(",");
					newSql.append(attr.getName());
					needDot = true;
				}
			}
			newSql.append("); ");
			allSql.append(preSql + newSql);
		}
		sql = allSql.toString();
	}

	@Override
	public void buildSingleInsertSql(MetaEntity entity,
			Map<String, String> values) {
		boolean bFirst = true;
		this.clear();
		StringBuilder insertSql = new StringBuilder("insert into "
				//+ entity.getName() + "(");
				+ getTableName(entity) + "(");
		StringBuilder valueSql = new StringBuilder(" values (");
		String uid = null;
		// 判断是否有主键字段，如果没有主键字段意味着需要使用自增的主键
		if (!values.containsKey(entity.getPkName())) {
			uid = GUID.get();
			insertSql.append(entity.getPkName());
			valueSql.append("?");
			this.parameters.add(uid);
			bFirst = false;
		}
		for (MetaAttribute attr : entity.getAttributes()) {
			if (values.containsKey(attr.getName())                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   ) {// 在参数表里面
				SqlDMLHelper sqlHelper = SqlHelperFactory.newSqlDMLHelper(attr);
				List<String> columnNames = sqlHelper.getColumnNames();// 获得实际的数据库字段
				List<Object> columnValues = sqlHelper.getColumnValues(values
						.get(attr.getName()));
				for (String columnName : columnNames) {
					if (bFirst) {
						bFirst = false;
					} else {
						insertSql.append(",");
						valueSql.append(",");
					}
					insertSql.append(columnName);
					if ("DATETIME".equals(attr.getDataType())||"DATE".equals(attr.getDataType())) {
						valueSql.append(" to_date ( ? ,'yyyy-mm-dd hh24:mi:ss') ");
					}else{
						valueSql.append("?");
					}
					
				}
				for (Object columnValue : columnValues) {
					this.parameters.add(columnValue);
				}
			}
		}
		insertSql.append(")");
		valueSql.append(")");
		sql = insertSql.toString() + valueSql.toString();
		// 把ID写回Values
		if (!values.containsKey(entity.getPkName())) {
			values.put(entity.getPkName(), uid);
		}
	}
	@Override
	public void buildMultiInsertSql(MetaEntityMap metaEntityMap,
			String pkValue, Map<String, String> insertValues) {
		// TODO Auto-generated method stub
		this.clear();
		boolean bFirst = true;
		boolean bSecond= true;
		MetaEntity oriEntity = metaEntityMap.getOriginEntity();
		MetaEntity tarEntity = metaEntityMap.getTargetEntity();
		Set<MetaAttribute> tarAttributes = tarEntity.getAttributes();//目标属性 
		Set<MetaAttributeMap> attributeMaps = metaEntityMap.getAttributeMaps();//映射属性 
		StringBuilder insertSql = new StringBuilder(" insert into "
		+getTableName(tarEntity)+ " tarEntity  (");
		String uid= null;
		StringBuilder selectSql = new StringBuilder(" select ");
		if (!insertValues.containsKey(tarEntity.getPkName())) {
			uid = GUID.get();
			insertSql.append(" tarEntity."+tarEntity.getPkName());
			selectSql.append("'"+uid+"'");
			bFirst = false;
			bSecond=false;
		}
		Set<String> colNames = insertValues.keySet();//要插入的列名
		for(String colName : colNames){
			if(bFirst){
				bFirst=false;
			}else{
				insertSql.append(" , ");
				
			}
			if(bSecond){
				bSecond=false;
			}else{
				selectSql.append(" , ");
			}
			insertSql.append(" tarEntity."+colName);
				for(MetaAttribute tarAttr: tarAttributes){
					if(tarAttr.getName().equals(colName)){
						if(("DATE".equals(tarAttr.getDataType())||"DATETIME".equals(tarAttr.getDataType()))){
							selectSql.append(" to_date('"+insertValues.get(colName)+"','yyyy-mm-dd hh24:mi:ss') ");
						}else{
							selectSql.append("'"+insertValues.get(colName)+"'");
						}
					}
				}
		}
		for(MetaAttributeMap attributeMap:attributeMaps){
			if(bFirst){
				bFirst= false;
			}else{
				insertSql.append(" , ");
			}
			insertSql.append(" tarEntity."+attributeMap.getTargetAttr().getName());
			if(bSecond){
				bSecond=false;
			}else{
				selectSql.append(" , ");
			}
			switch(attributeMap.getTargetAttr().getDataType()){
				case "DATETIME":
					selectSql.append(" to_char(oriEntity."+attributeMap.getOriginAttr().getName()+",'YYYY-MM-DD HH24:MI:SS AM')");
					break;
				case "DATE":
					selectSql.append(" to_char(oriEntity."+attributeMap.getOriginAttr().getName()+",'YYYY-MM-DD')");
					break;
				case "YEAR":
					//selectSql.append(" to_char(oriEntity."+attributeMap.getOriginAttr().getName()+",'YYYY')");
					selectSql.append(" oriEntity."+attributeMap.getOriginAttr().getName());
					break;
				case "YEARMONTH":
					//selectSql.append(" to_char(oriEntity."+attributeMap.getOriginAttr().getName()+",'YYYYMM')");
					selectSql.append(" oriEntity."+attributeMap.getOriginAttr().getName());
					break;
				default:
					selectSql.append(" oriEntity."+attributeMap.getOriginAttr().getName());
					break;
				
			}
			
		}
		insertSql.append(" ) ");
		selectSql.append(" from "+getTableName(oriEntity)+" oriEntity where "
				+"oriEntity."
				+oriEntity.getPkName()+ " = ?");
		this.sql = insertSql.toString()+ " " + selectSql.toString();
		this.parameters.add(pkValue);
	}
	@Override
	public void buildSingleUpdateSql(MetaEntity entity, String pkValue,
			Map<String, String> values) {
		boolean bFirst = true;
		this.clear();
		StringBuilder updateSql = new StringBuilder("update "
				//+ entity.getName() + " set ");
				+ getTableName(entity) + " set ");
		for (MetaAttribute attr : entity.getAttributes()) {
			if (values.containsKey(attr.getName())) {// 在参数表里面
				SqlDMLHelper sqlHelper = SqlHelperFactory.newSqlDMLHelper(attr);
				List<String> columnNames = sqlHelper.getColumnNames();// 获得实际的数据库字段
				List<Object> columnValues = sqlHelper.getColumnValues(values
						.get(attr.getName()));
				for (String columnName : columnNames) {
					if (bFirst) {
						bFirst = false;
					} else {
						updateSql.append(",");
					}
					if ("DATETIME".equals(attr.getDataType())||"DATE".equals(attr.getDataType())) {
						updateSql.append(columnName
								+ " = to_date ( ? ,'yyyy-mm-dd hh24:mi:ss')");
					} else {
						updateSql.append(columnName + " = ?");
					}

				}
				for (Object columnValue : columnValues) {
					this.parameters.add(columnValue);
				}
			}
		}
		updateSql.append(" where " + entity.getPkName() + " = ?");
		this.parameters.add(pkValue);
		sql = updateSql.toString();
	}
	@Override
	public void buildSingleDeleteSql(MetaEntity entity, String pkValue){
		buildSingleDeleteSql(entity, pkValue, "");
	}
	@Override
	public void buildSingleDeleteSql(MetaEntity entity, String pkValue, String versionId) {
		this.clear();
		StringBuilder deleteSql = new StringBuilder("delete from "
				//+ entity.getName());
				+ getTableName(entity, versionId));
		deleteSql.append(" where " + entity.getPkName() + " = ?");
		this.parameters.add(pkValue);
		if(StringUtils.isNotBlank(versionId)){
			deleteSql.append(" and VERSION_ID = ?");
			this.parameters.add(versionId);
		}
		sql = deleteSql.toString();
	}
	
	
	
	@Override
	public void buildDeleteSql(MetaEntity entity, Map<String, Object> params){
		buildDeleteSql(entity, params,"");
	}
	@Override
	public void buildDeleteSql(MetaEntity entity, Map<String, Object> params, String versionId) {
		this.clear();
		int count = 1;
		StringBuilder deleteSql = new StringBuilder("delete from "
				//+ entity.getName());
				+ getTableName(entity, versionId));
		if (params != null) {
			deleteSql.append(" where ");
			for (MetaAttribute attr : entity.getAttributes()) {
				if (params.containsKey(attr.getName())) {// 在参数表里面
					SqlDMLHelper sqlHelper = SqlHelperFactory
							.newSqlDMLHelper(attr);
					List<String> columnNames = sqlHelper.getColumnNames();// 获得实际的数据库字段
					List<Object> columnValues = sqlHelper
							.getColumnValues(params.get(attr.getName()));
					for (String columnName : columnNames) {
						if (count < params.size()) {
							deleteSql.append(columnName + " = ? ");
							deleteSql.append(" and ");
							count++;
						} else
							deleteSql.append(columnName + " = ?");

					}
					for (Object columnValue : columnValues) {
						this.parameters.add(columnValue);
					}
				}
			}
		}
		sql = deleteSql.toString();
	}
	@Override
	public void buildUpdateSeqNoSql(MetaEntity entity, String seqNo,
			Map<String, Object> params,String versionId) {
		// TODO Auto-generated method stub
		this.clear();
		int count = 1;
		StringBuilder updateSeqNoSql = new StringBuilder("update "
				//+ entity.getName());
				+ getTableName(entity, versionId));
		updateSeqNoSql.append(" set SEQ_NO = SEQ_NO - 1 ");
		if (params != null && params.size()>0) {
			updateSeqNoSql.append(" where ");
			for (MetaAttribute attr : entity.getAttributes()) {
				if (params.containsKey(attr.getName())) {// 在参数表里面
					SqlDMLHelper sqlHelper = SqlHelperFactory
							.newSqlDMLHelper(attr);
					List<String> columnNames = sqlHelper.getColumnNames();// 获得实际的数据库字段
					List<Object> columnValues = sqlHelper
							.getColumnValues(params.get(attr.getName()));
					for (String columnName : columnNames) {
						if (count < params.size()) {
							updateSeqNoSql.append(columnName + " = ? ");
							updateSeqNoSql.append(" and ");
							count++;
						} else
							updateSeqNoSql.append(columnName + " = ?");

					}
					for (Object columnValue : columnValues) {
						this.parameters.add(columnValue);
					}
				}
			}
			updateSeqNoSql.append(" and ");
		} else {
			updateSeqNoSql.append(" where ");
		}
		updateSeqNoSql.append(" SEQ_NO > ?");
		this.addParameter(seqNo);
		sql = updateSeqNoSql.toString();
	}
	
	/**
	 * 内部函数 构造sql
	 * @param entity
	 * @param columnNames
	 * @param params
	 * @param orderSql
	 */
	private void innerBuildSingleSelectSql(MetaEntity entity,
			String columnNames, Map<String, Object> params, String orderSql, String versionId) {
		dealVersionNoParameters(params, versionId);
		StringBuilder innerSql = new StringBuilder("select ");
		innerSql.append(columnNames);
		//innerSql.append(" from " + entity.getName());
		innerSql.append(" from " + getTableName(entity, versionId));
		if (params != null) {
			Set<String> paramFields = params.keySet();
			boolean bFirst = true;
			// while (paramFields.hasMoreElements()){
			for (String paramField : paramFields) {
				if (bFirst) {
					bFirst = false;
					innerSql.append(" where ");
				} else {
					innerSql.append(" and ");// 布尔表达式
				}
				String key = paramField;
				innerSql.append(String.format(" %s = ? ", key));
				parameters.add(params.get(key));
			}
		}		
		if (StringUtils.isNotBlank(orderSql)) {
			innerSql.append(" order by " + orderSql);
		} else {
			innerSql.append(" order by " + entity.getPkName());// 按主键排序，必须要有排序字段，否则无法处理分页
		}
		this.sql = innerSql.toString();
	}
	/**
	 * 通过SQL条件进行查询
	 * @param entity
	 * @param columnNames
	 * @param sqlCondition
	 */
	private void innerBuildSingleSelectSql(MetaEntity entity,String columnNames,Map<String, Object>params,
			String sqlCondition,String orderSql, String versionId){
		dealVersionNoParameters(params, versionId);
		StringBuilder innerSql = new StringBuilder("select ");
		innerSql.append(columnNames);
		//innerSql.append(" from " + entity.getName());
		innerSql.append(" from " + getTableName(entity, versionId));
		boolean bFirst = true;
		if (params != null) {
			Set<String> paramFields = params.keySet();
			for (String paramField : paramFields) {
				if (bFirst) {
					bFirst = false;
					innerSql.append(" where ");
				} else {
					innerSql.append(" and ");// 布尔表达式
				}
				String key = paramField;
				innerSql.append(String.format(" %s = ? ", key));
				parameters.add(params.get(key));
			}
		}
		if(StringUtils.isNotBlank(sqlCondition)){
			if(bFirst){
				bFirst=false;
				innerSql.append(" where ");
			}else{
				innerSql.append(" and ");
			}
			innerSql.append(sqlCondition);
		}
		if (StringUtils.isNotBlank(orderSql)) {
			innerSql.append(" order by " + orderSql);
		} else {
			innerSql.append(" order by " + entity.getPkName());// 按主键排序，必须要有排序字段，否则无法处理分页
		}
		this.sql = innerSql.toString();
	}
	
	/**
	 * 内部函数，根据entity中记录的属性信息，生成Sql语句需要的字段名字符串，类似于ID,
	 * UNIT_ID...这样的语句,并设置结果列和结果类型数据
	 * 
	 * @param entity
	 * @return
	 */
	private String getResultColumns(MetaEntity entity) {
		boolean bFirst = true;
		StringBuilder columnSql = new StringBuilder("");
		for (MetaAttribute attr : entity.getAttributes()) {
			SqlDMLHelper sqlHelper = SqlHelperFactory.newSqlDMLHelper(attr);
			List<String> aColumnNames = sqlHelper.getColumnNames();
			columnNames.addAll(aColumnNames);
			columnTypes.addAll(sqlHelper.getColumnTypes());
			List<AdditionalOperation> addOps = sqlHelper.getAddOps();
			if (addOps != null)
				this.addOps.addAll(addOps);
			for (String columnName : aColumnNames) {
				if (bFirst) {
					bFirst = false;
				} else {
					columnSql.append(", ");
				}
				columnSql.append(columnName);
			}
		}
		return columnSql.toString();
	}
	@Override
	public void buildNotPublicEntitySql(MetaEntity entity, String versionId,
			String pkValue) {
		// TODO Auto-generated method stub
		this.clear();
		StringBuilder publicSql = new StringBuilder("update "
				+ getTableName(entity, versionId));
		publicSql.append(" set VERSION_ID = ? ");
		this.parameters.add("N"+versionId);
		publicSql.append(" where VERSION_ID = ?");
		this.parameters.add(versionId);
		if(StringUtils.isNotBlank(pkValue)){
			publicSql.append(" and "+ entity.getPkName() + " = ? ");
			this.parameters.add(pkValue);
		}
		sql = publicSql.toString();
	}
	@Override
	public void buildPublicEntitySql(MetaEntity entity, String versionId,
			String pkValue) {
		// TODO Auto-generated method stub
		this.clear();
		StringBuilder publicSql = new StringBuilder("update "
				+ getTableName(entity, versionId));
		publicSql.append(" set VERSION_ID = ? ");
		this.parameters.add(versionId.substring(1, versionId.length()));
		publicSql.append(" where VERSION_ID = ?");
		this.parameters.add(versionId);
		if(StringUtils.isNotBlank(pkValue)){
			publicSql.append(" and "+ entity.getPkName() + " = ? ");
			this.parameters.add(pkValue);
		}
		sql = publicSql.toString();
	}	
	
	/**
	 * 判断排序列是否为数字，如果不是按拼音排序
	 * @param entity
	 * @param column
	 * @return
	 */
	private boolean isNumberColumn(MetaEntity entity,String column){
		Set<MetaAttribute> attributeSet = entity.getAttributes();
		for(MetaAttribute attr: attributeSet){
			if(attr.getName().equals(column)){
				MetaDataType dataType= MetaDataType.getDataType(attr.getDataType());
				switch(dataType){
				 	case INT: 
				 	case DOUBLE:
				 	case DATE:
				 	case DATETIME:
				 	case PERCENT:
				 		return true;
				 	default:
				 		return false;
				}
			}
		}
		return false;
	}
	
}
