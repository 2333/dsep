package com.dsep.dao.dsepmeta.calculate;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.CalResult;
import com.dsep.entity.dsepmeta.DiscLastIndexValue;

public interface CalResultDao extends DsepMetaDao<CalResult, String> {
	
	/**
	 * 获取一条计算结果
	 * @param unitId
	 * @param discId
	 * @return
	 */
	public abstract List<CalResult> getResult(String unitId, String discId);
	
	/**
	 * 删除一条记录结果
	 * @param unitId
	 * @param discId
	 */
	public abstract void deleteResult(String unitId,String discId);
	
	/**
	 * 获取某学科的所有计算结果
	 * @param discId
	 * @param unitId
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderProperName
	 * @return
	 */
	public abstract List<CalResult> getResult(String discId,String unitId,int pageIndex,
			int pageSize, Boolean desc, String orderProperName);
}
