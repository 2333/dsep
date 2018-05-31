package com.dsep.domain;

import java.util.List;
import java.util.Map;

public class UnitDiscCollect {
	private String unitId;
	private String discId;
	private List<String[]>entityIdandName;
	public UnitDiscCollect() {
		// TODO Auto-generated constructor stub
	}
	public UnitDiscCollect(String unitId,String discId,List<String[]> entityIdName)
	{
		this.unitId=unitId;
		this.discId=discId;
		this.entityIdandName=entityIdName;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getDiscId() {
		return discId;
	}
	public void setDiscId(String discId) {
		this.discId = discId;
	}
	public List<String[]> getEntityIdandName() {
		return entityIdandName;
	}
	public void setEntityIdandName(List<String[]> entityIdandName) {
		this.entityIdandName = entityIdandName;
	}
	
	
	
}
