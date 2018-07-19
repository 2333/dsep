package com.dsep.entity.dsepmeta;

import java.io.Serializable;
import java.util.Date;

public class DataCalculateConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6996128321149122620L;
	private String id;
	private String discId;
	private String discName;
	private String calStatus;
	private Date calTime;
	private String userId;
	private String distance;
	
	
	public String getId() {
		return id;
	}
	
	public String getDiscName() {
		return discName;
	}
	public void setDiscName(String discName) {
		this.discName = discName;
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
	public String getCalStatus() {
		return calStatus;
	}
	public void setCalStatus(String calStatus) {
		this.calStatus = calStatus;
	}
	public Date getCalTime() {
		return calTime;
	}
	public void setCalTime(Date calTime) {
		this.calTime = calTime;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}


}
