package com.dsep.entity.dsepmeta;

public class IndexScore {

	private String id;
	private String discId;
	private String unitId;
	private String indexId;
	private String indexName;
	private int indexLevel;
	private double score;
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
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getIndexLevel() {
		return indexLevel;
	}
	public void setIndexLevel(int indexLevel) {
		this.indexLevel = indexLevel;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	
}
