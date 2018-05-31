package com.dsep.vm.publicity;

import java.util.Date;

import com.dsep.entity.dsepmeta.PublicityManagement;
import com.dsep.entity.enumeration.EnumModule;
import com.dsep.entity.enumeration.publicity.OpenStatus;
import com.dsep.util.DateProcess;

public class PublicityManagementVM {

	private String roundId;
	private String publicRoundName;
	private String beginTime;
	private String endTime;
	private String actualEndTime;
	private String remark;
	private String openStatus;
	private String backupVersionId;
	
	public PublicityManagementVM(){
		
	}
	
	public PublicityManagementVM(PublicityManagement management){
		setPublicRoundName(management.getPublicRoundName());
		setRoundId(management.getId());
		setBeginTime(management.getBeginTime());
		setEndTime(management.getEndTime());
		setActualEndTime(management.getActualEndTime());
		setRemark(management.getRemark());
		setOpenStatus(management.getOpenStatus());
		setBackupVersionId(management.getBackupVersionId());
	}
	
	
	
	public String getRoundId() {
		return roundId;
	}
	public void setRoundId(String id) {
		this.roundId = id;
	}
	public String getPublicRoundName() {
		return publicRoundName;
	}
	public void setPublicRoundName(String publicRoundName) {
		this.publicRoundName = DateProcess.getShowingString(publicRoundName);
	}

	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = DateProcess.getShowingDate(beginTime);
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = DateProcess.getShowingDate(endTime);
	}
	public String getActualEndTime() {
		return actualEndTime;
	}
	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = DateProcess.getShowingDate(actualEndTime);
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = DateProcess.getShowingString(remark);
	}
	public String getOpenStatus() {
		return openStatus;
	}
	public void setOpenStatus(String openStatus) {
		EnumModule module = new OpenStatus();
		this.openStatus = module.getShowingByStatus(openStatus);
	}

	public String getBackupVersionId() {
		return backupVersionId;
	}

	public void setBackupVersionId(String backupVersionId) {
		this.backupVersionId = backupVersionId;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setActualEndTime(String actualEndTime) {
		this.actualEndTime = actualEndTime;
	}
	
	

}
