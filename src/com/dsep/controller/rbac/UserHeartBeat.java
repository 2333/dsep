package com.dsep.controller.rbac;

import java.util.List;

public class UserHeartBeat {
	private String loginId;
	private String machineId;
	private String location;
	private List<String> useIp;
	private int localWinNum;
	private String beattype;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public List<String> getUseIp() {
		return useIp;
	}
	public void setUseIp(List<String> useIp) {
		this.useIp = useIp;
	}
	public int getLocalWinNum() {
		return localWinNum;
	}
	public void setLocalWinNum(int localWinNum) {
		this.localWinNum = localWinNum;
	}
	public String getBeattype() {
		return beattype;
	}
	public void setBeattype(String beattype) {
		this.beattype = beattype;
	}
}
