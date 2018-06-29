package com.dsep.entity.dsepmeta;

public class IndexAvgWt {
	private String id;
	private String indexItemId;
	private String discId;
	private double avgValue;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndexItemId() {
		return indexItemId;
	}
	public void setIndexItemId(String indexItemId) {
		this.indexItemId = indexItemId;
	}
	public String getDiscId() {
		return discId;
	}
	public void setDiscId(String discId) {
		this.discId = discId;
	}
	public double getAvgValue() {
		return avgValue;
	}
	public void setAvgValue(double avgValue) {
		this.avgValue = avgValue;
	}
}
