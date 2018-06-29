package com.dsep.service.dsepmeta.dsepmetas;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.User;
import com.meta.entity.MetaEntity;

@Transactional(propagation=Propagation.SUPPORTS)
public interface DMSimilarityCheckService{
	
	/*
	 * 开始查重
	 */
	/*@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void startCheck(String userId, String unitId, String discId, List<MetaEntity> entities);*/
	
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void startCheck(String userId, String unitId, String discId, MetaEntity me);

	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void startCheck(String userId, String unitId, String discId, String entityId);

	/*
	 * 获取查重分组数据
	 */
	abstract public List<Map<String, String>> getSimilarityGroupDetail(
			String entityId, String dataId, String simIds, String sidx,
			boolean order_flag, int page, int pageSize, String unitId);
	
	/*
	 * 返回要导出查重数据文件的表头
	 */
	abstract public List<List<String>> getExcelTitles(User user,List<String> entityIDs);

	/*
	 * 根据entityID返回这个实体在前端界面要隐藏的的属性的字段名
	 */
	abstract public List<String> getShowAttrList(String entityId);
	
}
