package com.dsep.entity.dsepmeta;

import java.io.Serializable;

public class SpotResult implements Serializable{

	private static final long serialVersionUID = 4822559567229232233L;
	private String id;
	private String unitID;
	private String unitName;
	private String discID;
	private String discName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnitID() {
		return unitID;
	}
	public void setUnitID(String unitID) {
		this.unitID = unitID;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getDiscID() {
		return discID;
	}
	public void setDiscID(String discID) {
		this.discID = discID;
	}
	public String getDiscName() {
		return discName;
	}
	public void setDiscName(String discName) {
		this.discName = discName;
	}
	
}
