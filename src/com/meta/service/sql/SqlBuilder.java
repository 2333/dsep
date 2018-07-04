package com.meta.service.sql;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.type.StringType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;

import com.dsep.util.Configurations;
import com.meta.dao.AdditionalOperation;
import com.meta.entity.MetaDataType;
import com.meta.entity.MetaEntity;
import com.meta.entity.MetaEntityMap;

/**
 * 动态数据表的Sql语句生成器
 * 
 * @author thbin
 * 
 */
public abstract class SqlBuilder {
	/**
	 * 当前的Sql语句,sql语句可以通过分号分割，但只允许第一条SQL有参数
	 */
	protected String sql;
	/**
	 * Sql语句对应的参数表，如果sql语句包含多条，则只对应第一条sql语句的参数
	 */
	protected List<Object> parameters;
	/**
	 * 结果列对应的真实字段名
	 */
	protected List<String> columnNames;
	/**
	 * 结果列对应的真实字段名的真实字段类型
	 */
	protected List<MetaDataType> columnTypes;
	/**
	 * 处理数据时需要执行的附加操作
	 */
	protected List<AdditionalOperation> addOps;
	/**
	 * 构造函数
	 */
	public SqlBuilder() {
		parameters = new LinkedList<Object>();
		columnNames = new LinkedList<String>();
		columnTypes = new LinkedList<MetaDataType>();
		addOps = new LinkedList<AdditionalOperation>();
		sql = "";
	}
	/**
	 * 根据版本号，获得当前实体对应的表名
	 * @return
	 */
	public String getTableName(MetaEntity entity){
		return getTableName(entity, "");
	}
	public String getTableName(MetaEntity entity, String versionId)
	{
		if(StringUtils.isBlank(versionId)){
			return entity.getName();
		}else{
			return entity.getName()+Configurations.getBackupTablePostfix(versionId);
		}
	}
	/**
	 * 处理版本号的参数，在参数中添加版本号信息
	 * @return
	 */
	protected boolean dealVersionNoParameters(Map<String, Object> params, String versionId)
	{
		if(params==null){
			params = new HashMap<String, Object>(0);
		}
		if ( StringUtils.isNotBlank(versionId) &&(!params.containsKey("VERSION_ID")) ){
			params.put("VERSION_ID", versionId);
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 清除当前Sql和参数表
	 */
	public void clear() {
		parameters.clear();
		columnNames.clear();
		columnTypes.clear();
		addOps.clear();
		sql = "";
	}	
	/**
	 * 构造单个实体类的Select语句，查询数据库
	 * 
	 * @param entity
	 *            需要查询的实体类对象
	 * @param param
	 *            查询条件参数，key-真实字段名，value-参数值，多个条件为and操作
	 * @param orderPropName
	 *            排序条件，如果是null或""，则按照主键排序
	 * @param asc true-升序排列，false-降序排列
	 */
	abstract public void buildSingleSelectSql(MetaEntity entity,
			Map<String, Object> params, String orderPropName, boolean asc);	
	abstract public void buildSingleSelectSql(MetaEntity entity,
			Map<String, Object> params, String orderPropName, boolean asc, String versionId);
	/**
	 * 构造单个实体类的Select语句，查询数据库
	 * 
	 * @param entity
	 *            需要查询的实体类对象
	 * @param attrNames
	 * 				需要返回的属性信息
	 * @param param
	 *            查询条件参数，key-真实字段名，value-参数值，多个条件为and操作
	 * @param orderPropName
	 *            排序条件，如果是null或""，则按照主键排序
	 * @param asc true-升序排列，false-降序排列
	 */
	abstract public void buildSingleSelectSql(MetaEntity entity, List<String> attrNames, Map<String, Object> params, String orderPropName, boolean asc);
	abstract public void buildSingleSelectSql(MetaEntity entity, List<String> attrNames, Map<String, Object> params, String orderPropName, boolean asc, String versionId);
	
	/**
	 *  通过查询条件获取数据
	 * @param entity
	 * @param params
	 * @param sqlCondition
	 * @param orderPropName
	 * @param asc
	 */
	abstract public void buildSingleSelectSql(MetaEntity entity,Map<String, Object> params,String sqlCondition,
			String orderPropName,boolean asc);
	abstract public void buildSingleSelectSql(MetaEntity entity,Map<String, Object> params,String sqlCondition,
			String orderPropName,boolean asc, String versionId);
	/**
	 * 通过查询条件获取数据
	 * @param entity
	 * @param attrNames
	 * @param sqlCondition
	 */
	abstract public void buildSingleSelectSql(MetaEntity entity,List<String> attrNames,Map<String, Object>params, 
			String sqlCondition,String orderPropName,boolean asc);
	abstract public void buildSingleSelectSql(MetaEntity entity,List<String> attrNames,Map<String, Object>params, 
			String sqlCondition,String orderPropName,boolean asc, String versionId);
	
	/**
	 * 构造单个实体类的Select语句需要的统计记录数Sql
	 * 
	 * @param entity
	 *            需要查询的实体类对象
	 * @param param
	 *            查询条件参数，key-字段名，value-参数值，直接为and操作
	 * @param countColumn
	 *            count字段名，默认为ID
	 */
	abstract public void buildSingleCountSql(MetaEntity entity,
			Map<String, Object> params, String countColumn);
	abstract public void buildSingleCountSql(MetaEntity entity,
			Map<String, Object> params, String countColumn, String versionId);
	abstract public void buildSingleCountSql(MetaEntity entity,String sqlCondition,
			Map<String, Object>params,String countColumn);
	abstract public void buildSingleCountSql(MetaEntity entity,String sqlCondition,
			Map<String, Object>params,String countColumn,String versionId);
	
	/**
	 * 构造插入单个实体类的Insert语句
	 * 
	 * @param entity
	 *            需要插入的实体类信息
	 * @param values
	 *            需要插入的数据，以键值对的方式存储，key为属性名，value为String格式的数据
	 */
	abstract public void buildSingleInsertSql(MetaEntity entity,
			Map<String, String> values);
	abstract public void buildMultiInsertSql(MetaEntityMap metaEntityMap,String pkValue,Map<String, String> insertValues);
	/**
	 * 构造单个实体的更新语句，按照主键更新
	 * 
	 * @param entity
	 *            需要更新的实体信息
	 * @param pkValue
	 *            需要更新数据的主键值
	 * @param values
	 *            需要更新的数据，以键值对的方式存储，key为属性名，value为String格式的数据
	 */
	abstract public void buildSingleUpdateSql(MetaEntity entity, String pkValue,
			Map<String, String> values);

	/**
	 * 构造单个实体的删除语句，按照主键删除
	 * 
	 * @param entity
	 *            需要更新的实体信息	 
	 * @param pkValue
	 *            需要更新数据的主键值
	 * @param values
	 *            需要更新的数据，以键值对的方式存储，key为属性名，value为String格式的数据
	 */
	abstract public void buildSingleDeleteSql(MetaEntity entity, String pkValue);
	abstract public void buildSingleDeleteSql(MetaEntity entity, String pkValue, String versionId);
	
	/**
	 * 构造不公示的更新语句，如果不公示整个采集项，那么传入pkValue为空即可
	 * @param entity
	 * @param versionId
	 * @param pkValue
	 */
	public abstract void buildNotPublicEntitySql(MetaEntity entity,String versionId,String pkValue);
	
	/**
	 * 构造公示的更新语句，如果公示整个采集项，那么传入pkValue为空即可
	 * @param entity
	 * @param versionId
	 * @param pkValue
	 */
	public abstract void buildPublicEntitySql(MetaEntity entity,String versionId,String pkValue);
	
	/**
	 * 判断是否存在当前的表
	 * 
	 * @param table
	 *            表名
	 * @return 是否存在该表
	 */
	abstract public void existTable(String tableName);

	/** 根据参数删除数据
	 * @param entity
	 * @param map
	 */
	abstract public void buildDeleteSql(MetaEntity entity, Map<String,Object> params);
	abstract public void buildDeleteSql(MetaEntity entity, Map<String,Object> params, String versionId);
	

	/**
	 * 构造更新序号的SQL,使得在这个序号之后的序号自动减一
	 * @param entity
	 * @param seqNo
	 * @param params
	 * @param versionId
	 */
	public abstract void buildUpdateSeqNoSql(MetaEntity entity, String seqNo,
			Map<String, Object> params,String versionId);
	
	/**
	 * 生成实体类的建表脚本
	 * 
	 * @param entity
	 *            需要新建的实体类，要求在当前用户下不存在该实体类
	 */
	abstract public void buildCreateTableSql(MetaEntity entity);
	abstract public void buildCreateTableSql(MetaEntity entity, String versionId);
	/**
	 * 生成实体类的建索引脚本
	 * 
	 * @param entity
	 */
	abstract public void buildCreateIndexSql(MetaEntity entity);
	abstract public void buildCreateIndexSql(MetaEntity entity, String versionId);
	
	/**
	 * 生成实体类的删除表脚本
	 * @param entity
	 */
	abstract public void buildDropTableSql(MetaEntity entity);
	abstract public void buildDropTableSql(MetaEntity entity,String versionId);
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<Object> getParameters() {
		return parameters;
	}

	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}

	/**
	 * 给当前SQL语句增加一个参数
	 * 
	 * @param parameter
	 *            参数值
	 */
	public void addParameter(Object parameter) {
		parameters.add(parameter);
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public List<MetaDataType> getColumnTypes() {
		return columnTypes;
	}

	public void setColumnTypes(List<MetaDataType> columnTypes) {
		this.columnTypes = columnTypes;
	}

	public List<AdditionalOperation> getAddOps() {
		return addOps;
	}

	public void setAddOps(List<AdditionalOperation> addOps) {
		this.addOps = addOps;
	}

	/**
	 * 将列类型转换为Hibernate数据类型
	 * 
	 * @return 元模型类型对应的Hibernate数据类型
	 */
	public List<Type> getHibernateColumnTypes() {
		List<Type> hibernateTypes = new LinkedList<Type>();
		for (MetaDataType dataType : columnTypes) {
			switch (dataType) {
			case CHAR:
				hibernateTypes.add(StringType.INSTANCE);
				break;
			case VARCHAR2:
				hibernateTypes.add(StringType.INSTANCE);
				break;
			case NVARCHAR2:
				hibernateTypes.add(StringType.INSTANCE);
				break;
			case INT:
				hibernateTypes.add(IntegerType.INSTANCE);
				break;
			case DOUBLE:
				hibernateTypes.add(DoubleType.INSTANCE);
				break;
			case DATE:
				hibernateTypes.add(DateType.INSTANCE);
				break;
			case DATETIME:
				hibernateTypes.add(TimestampType.INSTANCE);
				break;
			case YEAR:
				hibernateTypes.add(StringType.INSTANCE);
				break;
			case YEARMONTH:
				hibernateTypes.add(StringType.INSTANCE);
				break;
			case DIC:
				hibernateTypes.add(StringType.INSTANCE);
				break;
			case PERCENT:
				hibernateTypes.add(StringType.INSTANCE);
				break;
			}
		}
		return hibernateTypes;
	}
}
