package com.dsep.entity.publib;

import java.io.Serializable;
/*
 * 国家工程实验室
 */
public class GCSYS implements Serializable{

	private String Sysmc;
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
}
