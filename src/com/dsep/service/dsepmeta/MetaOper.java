package com.dsep.service.dsepmeta;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.dsep.common.exception.BusinessException;
import com.dsep.dao.dsepmeta.base.CategoryCollectDao;
import com.dsep.dao.dsepmeta.base.CategoryDao;
import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.dao.dsepmeta.base.IndexDao;
import com.dsep.dao.dsepmeta.base.IndexMapDao;
import com.dsep.util.Configurations;
import com.dsep.util.UnitTest;
import com.dsep.vm.JqgridVM;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.entity.MetaDomain;
import com.meta.entity.MetaEntity;
import com.meta.entity.MetaEntityMap;
import com.meta.service.MetaDomainService;
import com.meta.service.MetaEntityCategoryService;
import com.meta.service.MetaEntityMapService;
import com.meta.service.MetaEntityService;
import com.meta.service.sql.SqlBuilder;
import com.meta.service.sql.SqlExecutor;

public class MetaOper  {

	protected MetaEntityCategoryService metaEntityCategoryService;
	protected MetaDomainService metaDomainService;
	protected MetaEntityService metaEntityService;
	protected SqlExecutor sqlExecutor;
	protected SqlBuilder sqlBuilder;
	protected CategoryCollectDao categoryCollectDao;
	protected DiscCategoryDao discCategoryDao;
	protected CategoryDao categoryDao;
	protected IndexDao indexDao;
	protected IndexMapDao indexMapDao;
	protected MetaEntityMapService metaEntityMapService;
	
	
	protected String getEntityId(String category, int seqNo){
		String domainId= Configurations.getCurrentDomainId();
		MetaDomain domain = metaDomainService.getById(domainId);
		String entityId = metaDomainService.getEntityId(domain, category, seqNo);
		return entityId;		
	}

	/**
	 * 取采集表前台数据,返回JqgridVM格式的数据
	 * 
	 * @param entityId	实体ID
	 * @param params 参数表，key为参数属性名，values为参数
	 * @param pageIndex	当前页序号，从1开始，如果0表明不分页
	 * @param pageSize 当前也记录数
	 * @param orderPropName 排序的属性名（目前只支持属性名和字段名完全一致的情况）
	 * @param asc 排序方式，true-升序， false-降序
	 * @return JqgridVM数据
	 */
	protected JqgridVM getJqGridData(String entityId, Map<String, Object> params,
			int pageIndex, int pageSize, String orderPropName, boolean asc) {
		MetaEntity entity = metaEntityService.getById(entityId);
		if (entity == null) {
			throw new BusinessException("无法找到当前实体的元信息：" + entityId);
		}
		sqlBuilder.buildSingleCountSql(entity, params, entity.getPkName());
		int count = sqlExecutor.execCountQuery(sqlBuilder);
		
		sqlBuilder.buildSingleSelectSql(entity, params, orderPropName, asc);
		List<Map<String, String>> results = sqlExecutor.execQuery(sqlBuilder,
				pageIndex, pageSize);

		return new JqgridVM(pageIndex, count, pageSize, results);
	}
	/**
	 * 根据sql语句，取采集表前台数据,返回JqgridVM格式的数据
	 * 
	 * @param entityId	实体ID
	 * @param params 参数表，key为参数属性名，values为参数
	 * @param pageIndex	当前页序号，从1开始，如果0表明不分页
	 * @param pageSize 当前也记录数
	 * @param orderPropName 排序的属性名（目前只支持属性名和字段名完全一致的情况）
	 * @param asc 排序方式，true-升序， false-降序
	 * @return JqgridVM数据
	 */
	protected JqgridVM getJqGridData(String entityId, Map<String, Object> params,
			String sqlCondition,int pageIndex, int pageSize, String orderPropName, boolean asc) {
		MetaEntity entity = metaEntityService.getById(entityId);
		if (entity == null) {
			throw new BusinessException("无法找到当前实体的元信息：" + entityId);
		}
		sqlBuilder.buildSingleCountSql(entity,sqlCondition,params,entity.getPkName());
		int count = sqlExecutor.execCountQuery(sqlBuilder);

		sqlBuilder.buildSingleSelectSql(entity, params,sqlCondition, orderPropName, asc);
		List<Map<String, String>> results = sqlExecutor.execQuery(sqlBuilder,
				pageIndex, pageSize);

		return new JqgridVM(pageIndex, count, pageSize, results);
	}
	
	/**
	 * 取采集表前台数据,返回JqgridVM格式的数据
	 * 
	 * @param entityId	实体ID
	 * @param versionId备份数据的版本号
	 * @param params 参数表，key为参数属性名，values为参数
	 * @param pageIndex	当前页序号，从1开始，如果0表明不分页
	 * @param pageSize 当前也记录数
	 * @param orderPropName 排序的属性名（目前只支持属性名和字段名完全一致的情况）
	 * @param asc 排序方式，true-升序， false-降序
	 * @return JqgridVM数据
	 */
	protected JqgridVM getJqGridData(String entityId, String versionId,Map<String, Object> params,
			String sqlCondition,int pageIndex, int pageSize, String orderPropName, boolean asc) {
		MetaEntity entity = metaEntityService.getById(entityId);
		if (entity == null) {
			throw new BusinessException("无法找到当前实体的元信息：" + entityId);
		}
		sqlBuilder.buildSingleCountSql(entity, sqlCondition, params, entity.getPkName(), versionId);
		int count = sqlExecutor.execCountQuery(sqlBuilder);
		sqlBuilder.buildSingleSelectSql(entity, params, sqlCondition, orderPropName, asc, versionId);
		List<Map<String, String>> results = sqlExecutor.execQuery(sqlBuilder,
				pageIndex, pageSize);
		return new JqgridVM(pageIndex, count, pageSize, results);
	}
	/**
	 * 通过sql条件语句查询数据
	 * @param entityId
	 * @param attrNames
	 * @param sqlCondition
	 * @return
	 */
	protected List<Map<String, String>> getRowData(String entityId,List<String> attrNames,
			String sqlCondition){
		return getRowData(entityId, attrNames, sqlCondition, 0, 0, null, true);
	}
	protected List<Map<String, String>> getRowData(String entityId,List<String> attrNames,
			String sqlCondition,int pageIndex,int pageSize,String orderPropName,boolean asc){
		MetaEntity entity = metaEntityService.getById(entityId);
		if (entity == null) {
			throw new BusinessException("无法找到当前实体的元信息：" + entityId);
		}
		sqlBuilder.buildSingleSelectSql(entity,attrNames,null,sqlCondition,orderPropName,asc);
		return sqlExecutor.execQuery(sqlBuilder,pageIndex,pageSize);
	}
	
	protected List<Map<String, String>> getRowData(String entityId,List<String> attrNames,
			Map<String, Object> params, String sqlCondition,int pageIndex,int pageSize,String orderPropName,boolean asc){
		MetaEntity entity = metaEntityService.getById(entityId);
		if (entity == null) {
			throw new BusinessException("无法找到当前实体的元信息：" + entityId);
		}
		sqlBuilder.buildSingleSelectSql(entity,attrNames,params,sqlCondition,orderPropName,asc);
		return sqlExecutor.execQuery(sqlBuilder,pageIndex,pageSize);
	}
	
	/**
	 * 根据条件获得当前实体的数据信息（以对象的形式返回）
	 * @param entityId 实体ID
	 * @param params 参数表，键值对的方式表示key-为属性名，values为属性值
	 * @param attrNames 需要返回的属性列表（不需要返回所有的属性）
	 * @param orderPropName 排序字段，如果为""或null,则按照主键排序
	 * @param asc true-升序，false-降序
	 * @param pageIndex 当前页号，以1开始，0表示不分页
	 * @param pageSize 当前也大小
	 * @return 结果集
	 */
	protected List<Map<String, Object>> getDataObject(String entityId,List<String> attrNames,
			Map<String, Object> params, String orderPropName, boolean asc,
			int pageIndex, int pageSize) {
		MetaEntity entity = metaEntityService.getById(entityId);
		if (entity == null) {
			throw new BusinessException("无法找到当前实体的元信息：" + entityId);
		}
		sqlBuilder.buildSingleSelectSql(entity, attrNames, params, orderPropName, asc);
		return sqlExecutor.execQueryObject(sqlBuilder, pageIndex, pageSize);
	}
	/**
	 * 根据条件获得当前实体的数据信息（以对象的形式返回）
	 * @param entityId 实体ID
	 * @param params 参数表，键值对的方式表示key-为属性名，values为属性值
	 * @param orderPropName 排序字段，如果为""或null,则按照主键排序
	 * @param asc true-升序，false-降序
	 * @param pageIndex 当前页号，以1开始，0表示不分页
	 * @param pageSize 当前也大小
	 * @return 结果集
	 */
	protected List<Map<String, Object>> getDataObject(String entityId,
			Map<String, Object> params, String orderPropName, boolean asc,
			int pageIndex, int pageSize) {
		MetaEntity entity = metaEntityService.getById(entityId);
		if (entity == null) {
			throw new BusinessException("无法找到当前实体的元信息：" + entityId);
		}
		sqlBuilder.buildSingleSelectSql(entity, params,	orderPropName, asc);
		return sqlExecutor.execQueryObject(sqlBuilder, pageIndex, pageSize);
	}
	/**
	 * 根据条件获得当前实体的数据信息（以字符串的形式返回）
	 * @param entityId 实体ID
	 * @param params 参数表，键值对的方式表示key-为属性名，values为属性值
	 * @param orderPropName 排序字段，如果为""或null,则按照主键排序
	 * @param asc true-升序，false-降序
	 * @param pageIndex 当前页号，以1开始，0表示不分页
	 * @param pageSize 当前也大小
	 * @return 结果集
	 */
	protected List<Map<String, String>> getData(String entityId, Map<String, Object> params, String orderPropName, boolean asc, int pageIndex, int pageSize){

		MetaEntity entity = metaEntityService.getById(entityId);
		if (entity == null) {
			throw new BusinessException("无法找到当前实体的元信息：" + entityId);
		}
		sqlBuilder.buildSingleSelectSql(entity, params,	orderPropName, asc);
		return sqlExecutor.execQuery(sqlBuilder, pageIndex, pageSize);
	}
	
	/**
	 * 根据条件获得当前实体的备份数据信息（以字符串的形式返回）
	 * @param entityId 实体ID
	 * @param params 参数表，键值对的方式表示key-为属性名，values为属性值
	 * @param orderPropName 排序字段，如果为""或null,则按照主键排序
	 * @param asc true-升序，false-降序
	 * @param pageIndex 当前页号，以1开始，0表示不分页
	 * @param pageSize 当前也大小
	 * @return 结果集
	 */
	protected List<Map<String, String>> getBackupData(String entityId, String versionId, Map<String, Object> params, String orderPropName, boolean asc, int pageIndex, int pageSize){

		MetaEntity entity = metaEntityService.getById(entityId);
		if (entity == null) {
			throw new BusinessException("无法找到当前实体的元信息：" + entityId);
		}
		sqlBuilder.buildSingleSelectSql(entity, params,	orderPropName, asc,versionId);
		return sqlExecutor.execQuery(sqlBuilder, pageIndex, pageSize);
	}
	

	/**
	 * 根据用户指定需要返回的字段和条件获得当前实体的数据信息（以list字符串数组的形式返回）
	 * @param entityId 实体ID
	 * @param attrNames 需要返回的属性名称，返回的结果按照该属性名操作 
	 * @param params 参数表，键值对的方式表示key-为属性名，values为属性值，为null则所有数据
	 * @param orderPropName 排序字段，如果为""或null,则按照主键排序
	 * @param asc true-升序，false-降序
	 * @param pageIndex 当前页号，以1开始，0表示不分页
	 * @param pageSize 当前页号
	 * @return 结果集
	 */
	protected List<String[]> getData(String entityId, List<String> attrNames, Map<String, Object> params, String orderPropName, boolean asc, int pageIndex, int pageSize) {
		MetaEntity entity = metaEntityService.getById(entityId);
		if (entity == null) {
			throw new BusinessException("无法找到当前实体的元信息：" + entityId);
		}
		sqlBuilder.buildSingleSelectSql(entity, attrNames, params, orderPropName, asc);
		List<Map<String, String>> listMap = sqlExecutor.execQuery(sqlBuilder, 0, 0);
		List<String[]> list = new LinkedList<String[]>();
		for(Map<String,String> value: listMap){
			String[] result = new String[attrNames.size()];
			int i=0;
			for(String attrName: attrNames){
				result[i++] = value.get(attrName);
			}
			list.add(result);
		}
		return list;
	}
	/**
	 * 根据用户指定需要返回的字段和条件获得当前实体的数据信息（以list字符串数组的形式返回）
	 * @param entityId 实体ID
	 * @param attrNames 需要返回的属性名称，返回的结果按照该属性名操作 
	 * @param params 参数表，键值对的方式表示key-为属性名，values为属性值，为null则所有数据
	 * @param sqlCondition 以SQL语句表示的查询条件，与params对应
	 * @param orderPropName 排序字段，如果为""或null,则按照主键排序
	 * @param asc true-升序，false-降序
	 * @param pageIndex 当前页号，以1开始，0表示不分页
	 * @param pageSize 当前页号
	 * @return 结果集
	 */
	protected List<String[]> getData(String entityId, List<String> attrNames, Map<String, Object> params,String sqlCondition,String orderPropName, boolean asc, int pageIndex, int pageSize) {
		MetaEntity entity = metaEntityService.getById(entityId);
		if (entity == null) {
			throw new BusinessException("无法找到当前实体的元信息：" + entityId);
		}
		sqlBuilder.buildSingleSelectSql(entity, attrNames, params, sqlCondition, orderPropName, asc);
		List<Map<String, String>> listMap = sqlExecutor.execQuery(sqlBuilder, 0, 0);
		List<String[]> list = new LinkedList<String[]>();
		for(Map<String,String> value: listMap){
			String[] result = new String[attrNames.size()];
			int i=0;
			for(String attrName: attrNames){
				result[i++] = value.get(attrName);
			}
			list.add(result);
		}
		return list;
	}
	/**
	 * 新增采集项
	 * 
	 * @param entityId 需要插入的实体ID
	 * @param rowData 以key,value存储的数据
	 * @return 新插入记录的主键，如果返回null,则表明插入记录出错
	 */
	protected String newRow(String entityId, Map<String, String> rowData) {
		MetaEntity entity = metaEntityService.getById(entityId);
		sqlBuilder.buildSingleInsertSql(entity, rowData);
		if (sqlExecutor.execUpdate(sqlBuilder) == 1) {// 成功的插入一条记录
			return rowData.get(entity.getPkName());
		} else {
			return null;
		}
	}
	
	protected String importNewRow(String oriEntityId,String tarEntityId,
			String catId,String pkValue,Map<String, String> rowData){
		MetaEntityMap metaEntityMap = metaEntityMapService.getEntityMap(oriEntityId, tarEntityId, catId);
		
		sqlBuilder.buildMultiInsertSql(metaEntityMap, pkValue, rowData);
			if(sqlExecutor.execUpdate(sqlBuilder)==1){//插入一条记录失败
				return pkValue;
			}else{
				return null;
			}
	}

	/**
	 * 更新采集项
	 * 
	 * @param entityId 需要更新的实体ID
	 * @param pkValue 数据主键id
	 * @param rowData 以key,value存储的数据
	 * @return 更新的记录数，应该为1
	 */
	protected int updateRow(String entityId, String pkValue,
			Map<String, String> rowData) {
		MetaEntity entity = metaEntityService.getById(entityId);
		sqlBuilder.buildSingleUpdateSql(entity, pkValue, rowData);
		return sqlExecutor.execUpdate(sqlBuilder);
	}

	/** 删除采集项
	 * @param entityId 需要更新的实体ID
	 * @param pkValue 数据主键id
	 * @return
	 */
	protected boolean deleteRow(String entityId, String pkValue) {
		MetaEntity entity = metaEntityService.getById(entityId);
		sqlBuilder.buildSingleDeleteSql(entity, pkValue);
		return (sqlExecutor.execUpdate(sqlBuilder) == 1);
	}
	
	protected int deleteRow(String entityId, Map<String, Object> params)
	{
		MetaEntity entity = metaEntityService.getById(entityId);
		sqlBuilder.buildDeleteSql(entity, params);
		return sqlExecutor.execUpdate(sqlBuilder);
	}
	/**
	 * 删除备份表中的采集项
	 * @param entityId 实体ID
	 * @param pkValue 主键
	 * @param versionId 版本号
	 * @return
	 */
	protected boolean deleteBackupRow(String entityId,String pkValue,String versionId){
		MetaEntity entity = metaEntityService.getById(entityId);
		sqlBuilder.buildSingleDeleteSql(entity, pkValue, versionId);
		return (sqlExecutor.execUpdate(sqlBuilder) == 1);
	}
	
	/**
	 * 不公示某条采集项
	 * @param entityId 实体ID
	 * @param versionId 版本号
	 * @param pkValue 主键
	 * @return
	 */
	protected boolean notPubBackData(String entityId,String versionId,String pkValue){
		MetaEntity entity = metaEntityService.getById(entityId);
		sqlBuilder.buildNotPublicEntitySql(entity, versionId, pkValue);
		return (sqlExecutor.execUpdate(sqlBuilder) >= 1 );
	}
	
	/**
	 * 公示某条采集项
	 * @param entityId
	 * @param versionId
	 * @param pkValue
	 * @return
	 */
	protected boolean pubBackData(String entityId,String versionId,String pkValue){
		MetaEntity entity = metaEntityService.getById(entityId);
		sqlBuilder.buildPublicEntitySql(entity, versionId, pkValue);
		return (sqlExecutor.execUpdate(sqlBuilder) >= 1 );
	}
	
	protected void updateSeqNo(String entityId, String seqNo,
			Map<String, Object> rowData) {
		MetaEntity entity = metaEntityService.getById(entityId);
		sqlBuilder.buildUpdateSeqNoSql(entity, seqNo, rowData,null);
		sqlExecutor.execUpdate(sqlBuilder);
	}
	
	/**
	 * 删除备份数据时更新备份数据的序号
	 * @param entityId 实体ID
	 * @param seqNo 序号
	 * @param versionId 版本号
	 * @param rowData 
	 */
	protected void updateBackSeqNo(String entityId,String seqNo,
			String versionId,Map<String,Object> rowData){
		MetaEntity entity = metaEntityService.getById(entityId);
		sqlBuilder.buildUpdateSeqNoSql(entity, seqNo, rowData,versionId);
		UnitTest.testPrint();
		sqlExecutor.execUpdate(sqlBuilder);
	}
	
	/**
	 * 根据条件获取记录数
	 * @param entityId
	 * @param params
	 * @return
	 */
	protected int getCount(String entityId,Map<String, Object> params)
	{
		MetaEntity entity = metaEntityService.getById(entityId);
		if (entity == null) {
			throw new BusinessException("无法找到当前实体的元信息：" + entityId);
		}
		sqlBuilder.buildSingleCountSql(entity, params, entity.getPkName());
		return sqlExecutor.execCountQuery(sqlBuilder);
	}
	// 以下为Getters && Setters
	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}

	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	public SqlBuilder getSqlBuilder() {
		return sqlBuilder;
	}

	public void setSqlBuilder(SqlBuilder sqlBuilder) {
		this.sqlBuilder = sqlBuilder;
	}

	public MetaDomainService getMetaDomainService() {
		return metaDomainService;
	}

	public void setMetaDomainService(MetaDomainService metaDomainService) {
		this.metaDomainService = metaDomainService;
	}

	public MetaEntityService getMetaEntityService() {
		return metaEntityService;
	}

	public void setMetaEntityService(MetaEntityService metaEntityService) {
		this.metaEntityService = metaEntityService;
	}

	public MetaEntityCategoryService getMetaEntityCategoryService() {
		return metaEntityCategoryService;
	}

	public void setMetaEntityCategoryService(
			MetaEntityCategoryService metaEntityCategoryService) {
		this.metaEntityCategoryService = metaEntityCategoryService;
	}
	public DiscCategoryDao getDiscCategoryDao() {
		return discCategoryDao;
	}

	public void setDiscCategoryDao(DiscCategoryDao discCategoryDao) {
		this.discCategoryDao = discCategoryDao;
	}

	public CategoryCollectDao getCategoryCollectDao() {
		return categoryCollectDao;
	}

	public void setCategoryCollectDao(CategoryCollectDao categoryCollectDao) {
		this.categoryCollectDao = categoryCollectDao;
	}
	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public IndexDao getIndexDao() {
		return indexDao;
	}

	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	}

	public IndexMapDao getIndexMapDao() {
		return indexMapDao;
	}

	public void setIndexMapDao(IndexMapDao indexMapDao) {
		this.indexMapDao = indexMapDao;
	}

	public MetaEntityMapService getMetaEntityMapService() {
		return metaEntityMapService;
	}

	public void setMetaEntityMapService(MetaEntityMapService metaEntityMapService) {
		this.metaEntityMapService = metaEntityMapService;
	}
	
}
