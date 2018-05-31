package com.dsep.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.Unit;

@Transactional(propagation=Propagation.SUPPORTS)
public interface UnitService {
	
	/**
	 * 获取所有单位名称
	 * @return
	 */
	@Cacheable(value="unitCache")
	abstract public Map<String,String> getAllUnitMaps();
	
	/**
	 * 获取所有单位的实体列表
	 * @return
	 */
	abstract public List<Unit> getAllUnits();
	
	/**
	 * 获取拥有学科号为discId学科的所有单位实体列表
	 * @param discId
	 * @return
	 */
	abstract public List<Unit> getUnitsByDiscId(String discId);
	
	
	/**
	 * 获取某一学科的所有参评学校
	 * @param discId 学科ID
	 * @return 参评学校集合
	 */
	abstract public List<String> getEvalUnitByDiscId(String discId);
	
	/**
	 * 获取某一学科的所有参评学校
	 * @param discId 学科ID
	 * @return 参评学校集合
	 */
	abstract public Map<String,String> getJoinUnitMapByDiscId(String discId);
	
	/**
	 * 根据学校ID获取学校名称
	 * @param unitId 学校ID
	 * @return 学校名称
	 */
	abstract public String getUnitNameById(String unitId);
	
	/**
	 * 获得所有参评学校
	 * @return
	 */
	abstract public List<String> getAllEvalUnitIds();
	
	public Map<String,String> getAllEvalUnitMap();
	
	public void testCache();
	
}
