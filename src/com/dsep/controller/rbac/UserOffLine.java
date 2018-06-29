package com.dsep.controller.rbac;

public class UserOffLine {
	private String loginId;
	private String location;
	private String releaseIP;
	private int sumSeconds;
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getReleaseIP() {
		return releaseIP;
	}
	public void setReleaseIP(String releaseIP) {
		this.releaseIP = releaseIP;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getSumSeconds() {
		return sumSeconds;
	}
	public void setSumSeconds(int sumSeconds) {
		this.sumSeconds = sumSeconds;
	}
}
