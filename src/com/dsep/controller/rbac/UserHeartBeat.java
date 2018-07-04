package com.dsep.controller.rbac;

import java.util.List;

public class UserHeartBeat {
	private String loginId;
	private String machineId;
	private List<String> useIp;
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
}
