package com.dsep.dao.dsepmeta.calculate;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.DiscLastIndexValue;

public interface DiscIndexValueDao extends DsepMetaDao<DiscLastIndexValue, String> {
	/**
	 * 返回指定学校、学科、末级指标项的分数
	 * @param indexId
	 * @param discId
	 * @param unitId
	 * @return
	 */
	public abstract List<DiscLastIndexValue> isExist(String indexId,String discId,String unitId);
	
	/**
	 * 获得某参评学科下指定末级指标项的所有分数
	 * @param discId
	 * @return
	 */
	public abstract List<DiscLastIndexValue> getDiscLastIndexValues(String discId,String indexId);
	
	/**
	 * 获得某参评学科的所有末级指标项分数
	 * @param discId
	 * @return
	 */
	public abstract List<DiscLastIndexValue> getDiscLastIndexValues(String discId);
	/**
	 * 获得某个计算学科下的所有学校
	 * @param disc
	 * @return
	 */
	public abstract List<String> getCaledUnitIDByDisc(String discId);
}
