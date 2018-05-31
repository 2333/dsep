package com.dsep.entity.publib;

import java.io.Serializable;

/*
 * 中国科学院、工程院院士
 */
public class YS implements Serializable{

	private String Zjxm;
	private String Lb;
	private String Pzsj;
	private String Xkxx;
	private String Xxdm;
	private String Bz;
	
	public String getZjxm() {
		return Zjxm;
	}
	public void setZjxm(String zjxm) {
		Zjxm = zjxm;
	}
	public String getLb() {
		return Lb;
	}
	public void setLb(String lb) {
		Lb = lb;
	}
	public String getPzsj() {
		return Pzsj;
	}
	public void setPzsj(String pzsj) {
		Pzsj = pzsj;
	}
	public String getXkxx() {
		return Xkxx;
	}
	public void setXkxx(String xkxx) {
		Xkxx = xkxx;
	}
	public String getXxdm() {
		return Xxdm;
	}
	public void setXxdm(String xxdm) {
		Xxdm = xxdm;
	}
	public String getBz() {
		return Bz;
	}
	public void setBz(String bz) {
		Bz = bz;
	}
}
