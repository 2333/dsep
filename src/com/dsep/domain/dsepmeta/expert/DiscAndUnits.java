package com.dsep.domain.dsepmeta.expert;

import java.util.List;

public class DiscAndUnits {
	String disc;
	List<String> units;
	public DiscAndUnits(String disc, List<String> units) {
		this.disc = disc;
		this.units = units;
	}
	public String getDisc() {
		return disc;
	}
	public void setDisc(String disc) {
		this.disc = disc;
	}
	public List<String> getUnits() {
		return units;
	}
	public void setUnits(List<String> units) {
		this.units = units;
	}
	public DiscAndUnits getObjByDisc(String disc) {
		if (this.disc.equals(disc)) return this;
		else return null;
	}
}
