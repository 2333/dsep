package com.dsep.service.dsepmeta.dsepmetas;


import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.User;
import com.dsep.vm.CollectionTreeVM;
import com.dsep.vm.JqgridVM;
import com.dsep.vm.collect.CollectCategoryTreeVM;
import com.meta.domain.search.SearchGroup;

/**
 * 数据采集服务
 * @author thbin
 *
 */
public interface DMCollectService  {
	 	
	/** 获得当前指标采集体系的采集树（学科采集）
	 * @param discId 学科Id
	 * @param occassion 为场合，分为采集和备份
	 * @return baseUrl 相对URL前导地址，可以为空
	 * @return List<采集树>
	 */
	public abstract List<CollectionTreeVM> getDisciplineCollectTrees(String discId);
	/**
	 * 通过学科id,获取采集树及学科门类ID
	 * @param discId
	 * @return
	 */
	public abstract CollectCategoryTreeVM getDiscCollectCategoryTreeVMs(String discId);
	
	/** 获得教师的采集树
	 * @return List<采集树>
	 */
	public abstract List<CollectionTreeVM> getTreesByOccasion(String occasion);
	/**
	 * 通过学科门类加载采集树
	 * @param catId
	 * @return
	 */
	public abstract List<CollectionTreeVM> getDisciplineCollectTreesByCatId(String catId);
	/**
	 * 新增采集项
	 * 
	 * @param entityId 需要插入的实体ID
	 * @param unitId 单位ID
	 * @param discId 学科ID
	 * @param userId 用户ID
	 * @param rowData 以key,value存储的数据
	 * @return 新插入记录的主键，如果返回null,则表明插入记录出错
	 */
	public abstract String newRow(String entityId, String unitId, String discId, String userId, Map<String,String> rowData);
	/**
	 * 更新采集条项
	 * @param entityId
	 * @param userId
	 * @param pkValue
	 * @param rowData
	 * @return
	 */
	public abstract int updateRow(String entityId, String userId, String pkValue,String unitId,String discId,Map<String, String> rowData);
	/**
	 * 采集成果重新排序
	 * @param entityId 实体ID
	 * @param userId 用户ID
	 * @param pkValues 主键列表
	 * @param rowDatas 更新的序号集合
	 * @return 更新序号的条目
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract int reOrder(String entityId,String userId,List<String> pkValues,List<Map<String,String>> rowDatas );
	/**
	 * 通过主键删除一行数据，并且把该序号一下的序号减一
	 * @param entity
	 * @param pkValue
	 * @param unitId
	 * @param discId
	 * @param userId TODO
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean deleteRow(String entity,String pkValue,String seqNo,String unitId,String discId, String userId);
	
	/**
	 * 取采集表前台数据,返回JqgridVM格式的数据
	 * 
	 * @param entityId	实体ID
	 * @param unitId 单位代码
	 * @param discId 学科代码
	 * @param params 参数表，key为参数属性名，values为参数（不需要单位代码和学科代码）
	 * @param pageIndex	当前页序号，从1开始，如果0表明不分页
	 * @param pageSize 当前也记录数
	 * @param orderPropName 排序的属性名（目前只支持属性名和字段名完全一致的情况）
	 * @param asc 排序方式，true-升序， false-降序
	 * @return JqgridVM数据
	 */
	public abstract JqgridVM getJqGridData(String entityId, String unitId, String discId , int pageIndex, int pageSize,
			String orderPropName, boolean asc);
	/**
	 * 取采集表前台数据,返回JqgridVM格式的数据
	 * 
	 * @param entityId	实体ID
	 * @param unitId 单位代码
	 * @param discId 学科代码
	 * @param params 参数表，key为参数属性名，values为参数（不需要单位代码和学科代码）
	 * @param pageIndex	当前页序号，从1开始，如果0表明不分页
	 * @param pageSize 当前也记录数
	 * @param orderPropName 排序的属性名（目前只支持属性名和字段名完全一致的情况）
	 * @param asc 排序方式，true-升序， false-降序
	 * @return JqgridVM数据
	 */
	public abstract JqgridVM getJqGridSearchData(String entityId, String unitId, String discId ,SearchGroup searchGroup, int pageIndex, int pageSize,
			String orderPropName, boolean asc);
	/**
	 * 获得学校学科的采集成果记录数
	 * @param entityId
	 * @param unitId
	 * @param discId
	 * @return
	 */
	public abstract int getCount(String entityId,String unitId,String discId);
	/**
	 * 获取教师成果
	 * @param entityId
	 * @param unitId
	 * @param dsicId
	 * @param userIds
	 * @param pageIndex
	 * @param pageSize
	 * @param orderPropName
	 * @param asc
	 * @return
	 */
	public abstract JqgridVM getJqGridTData(String entityId,String unitId,String dsicId,List<String>userIds,int pageIndex,
			int pageSize,String orderPropName,boolean asc);
	/**
	 * 根据SQL语句，获取教师成果
	 * @param entityId
	 * @param unitId
	 * @param dsicId
	 * @param userIds
	 * @param sqlCondition(查询条件)
	 * @param pageIndex
	 * @param pageSize
	 * @param orderPropName
	 * @param asc
	 * @return
	 */
	public abstract JqgridVM getJqGridTSearchData(String entityId,String unitId,String dsicId,List<String>userIds,SearchGroup searchGroup,int pageIndex,
			int pageSize,String orderPropName,boolean asc);
	/**
	 * 学科采集数据的时候查看教师信息（这里对已经选如的教师成果进行标记）
	 * @param entityId
	 * @param tarEntityId (将要导入的目标实体ID)
	 * @param unitId
	 * @param dsicId
	 * @param userIds
	 * @param searchGroup
	 * @param pageIndex
	 * @param pageSize
	 * @param orderPropName
	 * @param asc
	 * @return
	 */
	public abstract JqgridVM viewTSearchData(String entityId,String tarEntityId,String unitId,String dsicId,List<String>userIds,SearchGroup searchGroup,int pageIndex,
			int pageSize,String orderPropName,boolean asc);
	/**
	 * 更新教师成果
	 * @param entityId
	 * @param user
	 * @param rowData
	 * @return 返回更新的ID
	 */
	public abstract String newTRow(String entityId,User user, Map<String, String> rowData);
	/**
	 * 删除教师成果
	 * @param entityId
	 * @param userId
	 * @param rowData
	 * @return
	 */
	public abstract Boolean deleteTRow(String entityId,String pkValue,String userId,String seqNo);
	/**
	 * 通过目标实体Id获取源实体ID 和 Name
	 * @param tarEntityId
	 * @return
	 */
	public abstract Map<String, String> getOriginEntity(String tarEntityId,String discId);
	
	/**
	 * 导入一条记录到学科
	 * @param oriEntityId
	 * @param tarEntityId
	 * @param pkValue
	 * @param unitId
	 * @param discId
	 * @param userId
	 * @return
	 */
	public abstract String importNewRow(String oriEntityId,String tarEntityId,String pkValue,
			String importType,String unitId,String discId,String userId);
	
	/**
	 * 获取采集数据的某一条记录
	 * @param entityId 实体ID
	 * @param itemId 采集项Id
	 * @param sidx
	 * @param order_flag
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public abstract JqgridVM getCollectDataDetail(String entityId, String itemId,
			String sidx, boolean order_flag, int page, int pageSize);
	
	/**
	 * 获取采集数据的某几条记录
	 * @param entityId 实体ID
	 * @param itemId 采集项Id
	 * @param sidx
	 * @param order_flag
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public abstract JqgridVM getCollectDataDetail(String entityId, List<String> itemIds,
			String sidx, boolean order_flag, int page, int pageSize);
	
	/**
	 * 检查是否已经导入该记录
	 * @param entityId
	 * @param pkValue
	 * @return
	 */
	public abstract boolean isExistItem(String entityId,String pkValue);
	
	/**
	 * 上传学科简介
	 * @param entityId
	 * @param unitId
	 * @param discId
	 * @param userId
	 * @param rowData
	 * @param attachId
	 * @return
	 */
	public abstract String uploadDiscIntroduceFile(String entityId,String unitId,
			String discId, String userId, Map<String, String> rowData,String attachId);
	/**
	 * 更新学科简介
	 * @param entityId
	 * @param unitId
	 * @param discId
	 * @param userId
	 * @param rowData
	 * @param attachId
	 * @return
	 */
	public abstract String updateDiscIntroduceFile(String entityId,String unitId,
			String discId, String userId, Map<String, String> rowData,String pkValue,String attachId);
	/**
	 * 初始化一个学科采集项的数据
	 * @param entity
	 * @param unitId
	 * @param discId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean initOneCollectData(String entity,String unitId,String discId,String userId);
	
	/**
	 * 初始化一个公共库的数据
	 * @param entity
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean initOnePublicData(String entity,String userId);
	
	/**
	 * 清空一条数据
	 * @param entity
	 * @param unitId
	 * @param discId
	 * @param pkValue
	 * @param userId
	 * @param seqNo
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean clearOneCollectItem(String entity,String unitId,
			String discId,String pkValue,String userId,String seqNo);
	/**
	 * 导入公共库数据
	 * @param entityId 实体ID
	 * @param unitId 单位ID
	 * @param discId 学科ID
	 * @param userId 用户ID
	 * @return 导入数据的个数
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract int importPubData(String entityId, String unitId, String discId, String userId);
	
}
