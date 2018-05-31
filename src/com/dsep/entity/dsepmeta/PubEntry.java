package com.dsep.entity.dsepmeta;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共库比对入口表实体
 * @author Monar
 *
 */
public class PubEntry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7454446259725044242L;
	
	private String publibId;
	private String publibChsName;
	private String checkUser;
	private Date checkDate;
	private char isChecked;
	

	public String getPublibId() {
		return publibId;
	}
	public void setPublibId(String publibId) {
		this.publibId = publibId;
	}
	
	public String getPublibChsName() {
		return publibChsName;
	}
	public void setPublibChsName(String publibChsName) {
		this.publibChsName = publibChsName;
	}
	
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public char getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(char isChecked) {
		this.isChecked = isChecked;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
