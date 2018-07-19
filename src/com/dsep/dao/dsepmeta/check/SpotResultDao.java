package com.dsep.dao.dsepmeta.check;

import java.util.List;

import com.dsep.entity.dsepmeta.SpotResult;

public interface SpotResultDao {

	/**
	 * 存储某一个学校的抽查结果
	 * @param discList
	 * @return
	 */
	public void saveSpotResultList(List<SpotResult> spotResultList);
	
	/**
	 * 存储一条抽查结果
	 * @param spotResult
	 * @return
	 */
	public void saveOneSpotResult(SpotResult spotResult);
	
	/**
	 * 取得所有的抽查结果
	 */
	public List<SpotResult> selectAllSpotResult(int pageIndex, int pageSize,
			boolean desc, String orderProperName);
    /**
     * 取得记录总数
     * @return
     */
	public int getCount();
	/**
	 * 取得某一学校下的所有抽到的学科
	 * @param unitID
	 * @return
	 */
	public List<SpotResult> selectSpotResultByUnitID(String unitID);
	/**
	 * 删除所有的抽查结果
	 */
	public void deleteAllSpotResult();
	
	/**
	 * 取得所有的抽查结果 不分页
	 * @return
	 */
	public List<SpotResult> getAllData();
}
