package com.dsep.controller.rbac;

public class UserOnLineReturnInfo {
	private Integer current;
	private Integer avaliableWinNum;
	private String userIp;
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(Integer current) {
		this.current = current;
	}
	public Integer getAvaliableWinNum() {
		return avaliableWinNum;
	}
	public void setAvaliableWinNum(Integer avaliableWinNum) {
		this.avaliableWinNum = avaliableWinNum;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
}
