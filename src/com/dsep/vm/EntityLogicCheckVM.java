package com.dsep.vm;

import java.util.ArrayList;
import java.util.List;

public class EntityLogicCheckVM {
	private boolean passed;
	private List<Detail> data;
	
	public EntityLogicCheckVM(){
		this.setPassed(true);
		this.data = new ArrayList<Detail>();
	}
	
	public void addResult(String entityName,String type,String unitId,String disciplineId,String conclusion){
		this.data.add(new Detail(entityName,type,unitId,disciplineId,conclusion));
	}
	
	public List<Detail> getData() {
		return data;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}
}

 class Detail{
	private String entityName;
	private String type;
	private String typeName;
	private String conclusion;
	private String unitId;
	private String disciplineId;
	public Detail(String entityName,String type,String unitId,String disciplineId,
					String conclusion){
		this.entityName = entityName;
		this.type = type;
		this.unitId = unitId;
		this.disciplineId = disciplineId;
		this.conclusion = conclusion;
	}
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeName() {
		switch(this.type){
			case "0":
				this.typeName = "错误";
				break;
			case "1":
				this.typeName = "警告";
				break;
			default: 
				this.typeName= "警告";
		}
		return this.typeName;
	}

	public String getConclusion() {
		return conclusion;
	}

}