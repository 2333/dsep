package com.dsep.service.base;

import java.util.List;
import java.util.Map;

import com.dsep.entity.Discipline;

public interface DisciplineService {
	
	/**
	 * 获取所有学科的Map，key为ID，value为中文名
	 * @return
	 */
	public Map<String,String> getAllDiscMap();
	
	/**
	 * 获取所有一级学科列表
	 * @return
	 */
	public abstract List<Discipline> getAllDisciplines();
	
	/**
	 * 获取单位号是unitId的单位的所有学科
	 * @param unitId
	 * @return
	 */
	public abstract List<Discipline> getDisciplinesByUnitId(String unitId);
	
	/**
	 * 根据学科ID获取学科名
	 * @param discId 学科Id
	 * @return 学科名
	 */
	public String getDisciplineNameById(String discId);
	
	/**
	 * 根据学校ID所有学科信息
	 * @param collegeId
	 * @return
	 */
	public abstract Map<String, String> getDisciplinesNamesByUnitId(String unitId);

	/**
	 * 根据学校ID获取所有参评学科ID
	 * @param unitId
	 * @return
	 */
	public abstract List<String> getJoinDisciplineByUnitId(String unitId);
	
	public abstract List<String> getAllEvalDisciplineIds();
	
	/**
	 * 根据学校ID获取所有参评学科的Map
	 * @param unitId 学校ID
	 * @return
	 */
	public Map<String,String> getJoinDisciplineMapByUnitId(String unitId);
	
	/**
	 * 获取两个学校共同拥有的学科Map
	 * @param formerUnitId 学校用户的ID
	 * @param checkedUnitId 被选中的学校ID
	 * @return
	 */
	public Map<String,String> getBothDisciplineMap(String formerUnitId,String checkedUnitId);
	
	/**
	 * 获取所有参评学科的id和name
	 * @return
	 */
	public Map<String, String> getAllEvalDiscMap();
}
