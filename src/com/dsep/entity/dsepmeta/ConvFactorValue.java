package com.dsep.entity.dsepmeta;

import java.io.Serializable;

public class ConvFactorValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1741075207066092438L;

	private String id;
	private String indexId;
	private String discId;
	private String content;
	private double avgValue;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getAvgValue() {
		return avgValue;
	}
	public void setAvgValue(double avgValue) {
		this.avgValue = avgValue;
	}
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	
	
	

}
