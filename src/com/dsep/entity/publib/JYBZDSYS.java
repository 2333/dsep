package com.dsep.entity.publib;

import java.io.Serializable;
/*
 * 教育部重点实验室
 */
public class JYBZDSYS implements Serializable{

	private String Sysmc;
	private String Xxxz;
	private String Xkxx;
	private String Xxdm;
	private String Xxmc;
	
	public String getSysmc() {
		return Sysmc;
	}
	public void setSysmc(String sysmc) {
		Sysmc = sysmc;
	}
	public String getXxdm() {
		return Xxdm;
	}
	public void setXxdm(String xxdm) {
		Xxdm = xxdm;
	}
	public String getXxmc() {
		return Xxmc;
	}
	public void setXxmc(String xxmc) {
		Xxmc = xxmc;
	}
	public String getXxxz() {
		return Xxxz;
	}
	public void setXxxz(String xxxz) {
		Xxxz = xxxz;
	}
	public String getXkxx() {
		return Xkxx;
	}
	public void setXkxx(String xkxx) {
		Xkxx = xkxx;
	}
}
