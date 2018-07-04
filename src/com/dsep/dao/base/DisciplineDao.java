package com.dsep.dao.base;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.Dao;
import com.dsep.entity.Discipline;

public interface DisciplineDao extends Dao<Discipline , String>{
	
	/**
	 * 获取单位号是unitId的单位的所有学科
	 * @param unitId
	 * @return
	 */
	public abstract List<Discipline> getDisciplinesByUnitId(String unitId);
	
	/**
	 * 获取所有一级学科的学科名
	 * @return
	 */
	public abstract Map<String, String> getDisciplineNames();
	
}
