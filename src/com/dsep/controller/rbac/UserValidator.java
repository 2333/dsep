package com.dsep.controller.rbac;

public class UserValidator {
	private String loginId;
	private String password;
	private String location;
	private Integer requestWinNum;
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getRequestWinNum() {
		return requestWinNum;
	}
	public void setRequestWinNum(Integer requestWinNum) {
		this.requestWinNum = requestWinNum;
	}
	
}
