package com.dsep.entity.publib;

import java.io.Serializable;
/*
 * 教育部高校青年教师奖获得者
 */
public class QNJS implements Serializable{

	private String Zjxm;
	private String Xkxx;
	private String Pzsj;
	private String Xxmc;
	private String Xxdm;
	
	public String getZjxm() {
		return Zjxm;
	}
	public void setZjxm(String zjxm) {
		Zjxm = zjxm;
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
