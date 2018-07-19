package com.dsep.controller.rbac;

public class UserOnLineReturnInfo {
	private Integer current;
	private Integer avaliableWinNum;
	private String useIp;
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
	public String getUseIp() {
		return useIp;
	}
	public void setUseIp(String useIp) {
		this.useIp = useIp;
	}
}
