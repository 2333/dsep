package com.dsep.domain.dsepmeta.expert;

/**
 * UNIT_ID | COUNT(ID)
 * 10001   | 31
 * 10003   | 40
 * 10335   | 21
 */
public class ExpertNumberGroupByUnitId {
	private String unitId;
	private Integer num;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
