package com.dsep.controller.rbac;

public class BeatReturnInfo {
	private int userWinNum;
	private int avaliableWinNum;
	private boolean isexpired;
	public int getUserWinNum() {
		return userWinNum;
	}
	public void setUserWinNum(int userWinNum) {
		this.userWinNum = userWinNum;
	}
	public int getAvaliableWinNum() {
		return avaliableWinNum;
	}
	public void setAvaliableWinNum(int avaliableWinNum) {
		this.avaliableWinNum = avaliableWinNum;
	}
	public boolean isIsexpired() {
		return isexpired;
	}
	public void setIsexpired(boolean isexpired) {
		this.isexpired = isexpired;
	}
}
