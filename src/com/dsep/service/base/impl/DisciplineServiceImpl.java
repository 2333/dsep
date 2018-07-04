package com.dsep.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.cache.annotation.Cacheable;

import com.dsep.dao.base.DisciplineDao;
import com.dsep.dao.base.UnitDao;
import com.dsep.dao.dsepmeta.flow.EvalFlowDao;
import com.dsep.entity.Discipline;
import com.dsep.entity.Unit;
import com.dsep.service.base.DisciplineService;
import com.dsep.util.Dictionaries;

public class DisciplineServiceImpl implements DisciplineService{

	private UnitDao unitDao;
	
	private DisciplineDao disciplineDao;
	
	private EvalFlowDao evalFlowDao;
	
	
	public EvalFlowDao getEvalFlowDao() {
		return evalFlowDao;
	}

	public void setEvalFlowDao(EvalFlowDao evalFlowDao) {
		this.evalFlowDao = evalFlowDao;
	}

	public UnitDao getUnitDao(){
		return unitDao;
	}
	
	public void setUnitDao(UnitDao unitDao){
		this.unitDao = unitDao;
	}
	
	public DisciplineDao getDisciplineDao(){
		return disciplineDao;
	}
	
	public void setDisciplineDao(DisciplineDao disciplineDao){
		this.disciplineDao = disciplineDao;
	}

	
	@Override
	public Map<String, String> getDisciplinesNamesByUnitId(String unitId) {
		Unit unit=unitDao.get(unitId);
		Map<String, String> map = new HashMap<String,String>();
		for(Discipline discipline:unit.getDisciplines())
		{
			map.put(discipline.getId(), discipline.getName());
		}
		
		return map;
	}

	@Override
	public List<Discipline> getAllDisciplines() {
		return disciplineDao.getAll();
	}

	@Override
	public List<Discipline> getDisciplinesByUnitId(String unitId) {
		// TODO Auto-generated method stub
		return disciplineDao.getDisciplinesByUnitId(unitId);
	}

	@Override
	public List<String> getJoinDisciplineByUnitId(String unitId) {
		// TODO Auto-generated method stub
		return evalFlowDao.getEvalDiscByUnitId(unitId);
	}

	@Override
	public String getDisciplineNameById(String discId) {
		// TODO Auto-generated method stub
		return disciplineDao.get(discId).getName();
	}

	@Override
	@Cacheable(value="discCache")
	public Map<String, String> getAllDiscMap() {
		// TODO Auto-generated method stub
		return disciplineDao.getDisciplineNames();
	}

	@Override
	public Map<String, String> getJoinDisciplineMapByUnitId(String unitId) {
		// TODO Auto-generated method stub
		List<String> discList = this.getJoinDisciplineByUnitId(unitId);
		return this.getDisciplineMapByList(discList);
	}

	@Override
	public Map<String, String> getAllEvalDiscMap() {
		// TODO Auto-generated method stub
		return this.getDisciplineMapByList(this.getAllEvalDisciplineIds());
	}

	@Override
	public Map<String, String> getBothDisciplineMap(String formerUnitId,
			String checkedUnitId) {
		// TODO Auto-generated method stub
		List<String> discList = evalFlowDao.getBothEvalDiscList(formerUnitId, checkedUnitId);
		return this.getDisciplineMapByList(discList);
	}

	@Override
	public List<String> getAllEvalDisciplineIds() {
		// TODO Auto-generated method stub
		return evalFlowDao.getAllEvalDiscIds();
	}
	
	private Map<String,String> getDisciplineMapByList(List<String> discList){
		@SuppressWarnings("unchecked")
		Map<String,String> discMap = new LinkedMap();
		for(String discId:discList){
			discMap.put(discId, Dictionaries.getDisciplineName(discId));
		}
		return discMap;
	}

}
