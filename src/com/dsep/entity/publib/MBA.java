package com.dsep.entity.publib;

import java.io.Serializable;
/*
 * 教学教材-MBA
 */
public class MBA implements Serializable{

	private String Almc;
	private String Xkxx;
	private String Pzsj;
	private String Xxmc;
	private String Xxdm;
	
	public String getAlmc() {
		return Almc;
	}
	public void setAlmc(String almc) {
		Almc = almc;
	}
	public String getXkxx() {
		return Xkxx;
	}
	public void setXkxx(String xkxx) {
		Xkxx = xkxx;
	}
	public String getPzsj() {
		return Pzsj;
	}
	public void setPzsj(String pzsj) {
		Pzsj = pzsj;
	}
	public String getXxmc() {
		return Xxmc;
	}
	public void setXxmc(String xxmc) {
		Xxmc = xxmc;
	}
	public String getXxdm() {
		return Xxdm;
	}
	public void setXxdm(String xxdm) {
		Xxdm = xxdm;
	}
}
