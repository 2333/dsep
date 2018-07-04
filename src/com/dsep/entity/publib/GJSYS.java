package com.dsep.entity.publib;

import java.io.Serializable;
/*
 * 国家实验室
 */
public class GJSYS implements Serializable{

	private String Sysmc;
	private String Xxdm;
	private String Xxmc;
	private String Pzsj;
	
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
	public String getPzsj() {
		return Pzsj;
	}
	public void setPzsj(String pzsj) {
		Pzsj = pzsj;
	}
}
