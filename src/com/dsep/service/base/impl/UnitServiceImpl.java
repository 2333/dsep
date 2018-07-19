package com.dsep.service.base.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.cache.annotation.Cacheable;

import com.dsep.dao.base.UnitDao;
import com.dsep.dao.dsepmeta.flow.EvalFlowDao;
import com.dsep.entity.Unit;
import com.dsep.service.base.UnitService;
import com.dsep.util.Dictionaries;

public class UnitServiceImpl implements UnitService{

	private UnitDao unitDao;
	
	private EvalFlowDao evalFlowDao;
	
	private Map<String, String> getUnitMapByListId(List<String> idList) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		Map<String,String> unitMap = new LinkedMap();
		for(String unitId:idList){
			unitMap.put(unitId, Dictionaries.getUnitName(unitId));
		}
		return unitMap;
	}
	
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

	
	/*@Override
	@Cacheable(value="unitCache")*/
	public Map<String, String> getAllUnitMaps() {
		return unitDao.getUnitNames();
	}

	@Override
	public List<Unit> getAllUnits() {
		return unitDao.getAll();
	}

	@Override
	public List<Unit> getUnitsByDiscId(String discId) {
		return unitDao.getUnitsByDiscId(discId);
	}

	@Override
	public List<String> getEvalUnitByDiscId(String discId) {
		// TODO Auto-generated method stub
		return evalFlowDao.getEvalUnitByDiscId(discId);
	}

	@Override
	public String getUnitNameById(String unitId) {
		// TODO Auto-generated method stub
		return unitDao.get(unitId).getName();
	}

	@Override
	public List<String> getAllEvalUnitIds() {
		// TODO Auto-generated method stub
		return evalFlowDao.getAllEvalUnitIds();
	}

	@Override
	public Map<String, String> getJoinUnitMapByDiscId(String discId) {
		// TODO Auto-generated method stub
		return getUnitMapByListId(this.getEvalUnitByDiscId(discId));

	}

	@Override
	public Map<String, String> getAllEvalUnitMap() {
		// TODO Auto-generated method stub
		return getUnitMapByListId(this.getAllEvalUnitIds());
	}

	@Override
	public void testCache() {
		// TODO Auto-generated method stub
		this.getAllUnitMaps();
	}





}
