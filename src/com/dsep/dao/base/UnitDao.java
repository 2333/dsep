package com.dsep.dao.base;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.Dao;
import com.dsep.entity.Unit;

public interface UnitDao extends Dao<Unit , String>{
	/**
	 * 获取所有单位的名称
	 * @return
	 */
	public abstract Map<String, String> getUnitNames();
	
	/**
	 * 获取所有拥有学科号为discId的学科的单位列表
	 * @param discId
	 * @return
	 */
	public abstract List<Unit> getUnitsByDiscId(String discId);
}
