package com.dsep.entity.dsepmeta;

public class CalResult {
	private String id;
	private String discId;
	private String discName;
	private String unitId;
	private String unitName;
	private double score;
	private int rank;
	private String cluClass;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDiscId() {
		return discId;
	}
	public void setDiscId(String discId) {
		this.discId = discId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getCluClass() {
		return cluClass;
	}
	public void setCluClass(String cluClass) {
		this.cluClass = cluClass;
	}
	public String getDiscName() {
		return discName;
	}
	public void setDiscName(String discName) {
		this.discName = discName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	
}
