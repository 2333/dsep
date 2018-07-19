package com.dsep.dao.dsepmeta.process;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.NormConfig;

public interface NormConfigDao extends DsepMetaDao<NormConfig,String>{

	/**
	 * 更新配置状态
	 */
	public abstract void updateStatus(NormConfig normConfig);
	/**
	 * 获得页面配置
	 * @return
	 */
	public abstract List<NormConfig> getConfig();
	/**
	 * 获取某一个实体的配置信息
	 * @param entityId
	 * @return
	 */
	public abstract NormConfig getConfigByEntityId(String entityId);
	/**
	 * 批量保存
	 * @param list
	 */
	public abstract void saveNormConfigList(List<NormConfig> list);
	/**
	 * 获得某个实体的规范状态
	 * @param entityId
	 */
	public abstract int selectStatus(String entityId);
	
}
