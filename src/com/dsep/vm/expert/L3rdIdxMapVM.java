package com.dsep.vm.expert;

public class L3rdIdxMapVM {
	private String id;
	private String pId;
	private String grandPId;

	private String name;
	private String pName;
	private String grandPName;

	private String weight;
	private String pWeight;
	private String grandPWeight;

	public L3rdIdxMapVM(String id, String pId, String grandPId, String name,
			String pName, String grandPName, String weight, String pWeight,
			String grandPWeight) {
		this.id = id;
		this.pId = pId;
		this.grandPId = grandPId;
		this.name = name;
		this.pName = pName;
		this.grandPName = grandPName;
		this.weight = weight;
		this.pWeight = pWeight;
		this.grandPWeight = grandPWeight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getGrandPId() {
		return grandPId;
	}

	public void setGrandPId(String grandPId) {
		this.grandPId = grandPId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getGrandPName() {
		return grandPName;
	}

	public void setGrandPName(String grandPName) {
		this.grandPName = grandPName;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getpWeight() {
		return pWeight;
	}

	public void setpWeight(String pWeight) {
		this.pWeight = pWeight;
	}

	public String getGrandPWeight() {
		return grandPWeight;
	}

	public void setGrandPWeight(String grandPWeight) {
		this.grandPWeight = grandPWeight;
	}

}
