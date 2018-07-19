package com.dsep.entity.publib;

import java.io.Serializable;
/*
 * 体育界重点学科
 */
public class TYZJ implements Serializable{

	private String Xxdm;
	private String Xxmc;
	private String Zdxkmc;
	
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
	public String getZdxkmc() {
		return Zdxkmc;
	}
	public void setZdxkmc(String zdxkmc) {
		Zdxkmc = zdxkmc;
	}
	
}
