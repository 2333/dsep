package com.dsep.service.dsepmeta.dsepmetas;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;

@Transactional(propagation = Propagation.SUPPORTS)
public interface DMViewConfigService {
	/**
	 * 获得某采集表的视图配置信息
	 * 
	 * @param entityId 采集表Id
	 *         
	 * @return 视图配置信息实体
	 */
	
	public abstract ViewConfig getViewConfig(String entityId);
	
	/**
	 * 根据style获得对应采集表的视图配置信息
	 * @param entityId
	 * @param style
	 * @return
	 */
	public ViewConfig getViewConfig(String entityId, String style);
	
	public ViewConfig getViewConfigByCategory(String entityId, String style);
}
