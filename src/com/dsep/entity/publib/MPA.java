package com.dsep.entity.publib;

import java.io.Serializable;
/*
 * 全国MPA 优秀专业硕士学位论文
 */
public class MPA implements Serializable{

	private String Jb;
	private String Zzmc;
	private String Dsmc;
	private String Lwtm;
	private String Xxmc;
	private String Xxdm;
	
	public String getJb() {
		return Jb;
	}
	public void setJb(String jb) {
		Jb = jb;
	}
	public String getZzmc() {
		return Zzmc;
	}
	public void setZzmc(String zzmc) {
		Zzmc = zzmc;
	}
	public String getDsmc() {
		return Dsmc;
	}
	public void setDsmc(String dsmc) {
		Dsmc = dsmc;
	}
	public String getLwtm() {
		return Lwtm;
	}
	public void setLwtm(String lwtm) {
		Lwtm = lwtm;
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
