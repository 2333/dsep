package com.dsep.service.dsepmeta.dsepmetas;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.vm.CollectionTreeVM;
import com.dsep.vm.JqgridVM;
import com.meta.domain.search.SearchGroup;

public interface DMBackupService {
	
	/**
	 * 学位中心备份采集数据
	 * @param versionId
	 * @return
	 */
	public boolean backupCenter(String versionId);
	

	
	/**
	 * 备份学科数据
	 * @param unitId 学校ID
	 * @param discId 学科ID
	 * @param versionId 备份的版本号
	 * @return
	 */
	abstract public boolean backupDiscipline(String unitId,String discId,String versionId);
	
	/**
	 * 恢复学科数据
	 * @param unitId 学校ID
	 * @param discId 学科ID
	 * @param versionId 版本号
	 * @return
	 */
	abstract public boolean restoreDiscipline(String unitId,String discId,String versionId);
	
	/**
	 * 删除某版本的学科备份
	 * @param versionId 版本号
	 * @param discId 学科Id
	 * @return
	 */
	public boolean deleteDiscipline(String versionId,String discId);
	
	/**
	 * 通过学科ID获取学科的备份tree
	 * @param discId
	 * @return
	 */
	abstract public List<CollectionTreeVM> getDiscBackupTree(String discId);
	/**
	 * 通过学校Id,学科id 获取备份数据
	 * @param entityId
	 * @param unitId
	 * @param discId
	 * @param versionId
	 * @param sqlCondition 
	 * 			SQL查询语句
	 * @param pageIndex
	 * @param pageSize
	 * @param orderPropName
	 * @param asc
	 * @return
	 */
	abstract public JqgridVM getJqGridData(String entityId, String unitId, String discId ,String versionId,SearchGroup searchGroup, int pageIndex, int pageSize,
			String orderPropName, boolean asc);
	/**
	 * 通过学科门类获取数据
	 * @param entityId
	 * @param unitId
	 * @param discId
	 * @param version
	 * @param pageIndex
	 * @param pageSize
	 * @param orderPropName
	 * @param asc
	 * @return
	 */
	abstract public JqgridVM getJqGridDataByCatId(String entityId, String catId ,String versionId,SearchGroup searchGroup, int pageIndex, int pageSize,
			String orderPropName, boolean asc);
	
	/**
	 * 删除某一版本的所有备份数据
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean deleteBackupData(String versionId);
	
	/**
	 * 创建所有的备份表
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean createAllBackupTable();
	
	/**
	 * 获取采集数据的某多条记录
	 * @param entityId 实体ID
	 * @param itemId 采集项Id
	 * @param sidx
	 * @param order_flag
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public abstract JqgridVM getCollectDataDetail(String entityId, List<String> itemIds,String versionId,
			String sidx, boolean order_flag, int page, int pageSize);
	
}
