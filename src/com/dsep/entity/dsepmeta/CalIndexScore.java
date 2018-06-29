package com.dsep.entity.dsepmeta;

public class CalIndexScore {
	
	private String id;
	private String unitId;
	private String discId;
	private String indexId;
	private double value;
	private double score;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
}
