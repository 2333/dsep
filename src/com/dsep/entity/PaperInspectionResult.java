package com.dsep.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="DSEP_PAPER_INSPECTION_RESULT")
public class PaperInspectionResult {
	
	private String paperSpotResultID;
	private String collegeID;
	private String disciplineID;
	private Date lastCheckTime;
	private boolean isDelete;
	
	@Id
	@Column(name="PAPERSPOTRESULTID")
	public String getPaperSpotResultID() {
		return paperSpotResultID;
	}
	
	public void setPaperSpotResultID(String paperSpotResultID) {
		this.paperSpotResultID = paperSpotResultID;
	}
	
	@Column(name="COLLEGEID")
	public String getCollegeID() {
		return collegeID;
	}
	
	public void setCollegeID(String collegeID) {
		this.collegeID = collegeID;
	}
	
	@Column(name="DISCIPLINEID")
	public String getDisciplineID() {
		return disciplineID;
	}
	
	public void setDisciplineID(String disciplineID) {
		this.disciplineID = disciplineID;
	}
	
	
	@Column(name="LASTCHECKTIME")
	public Date getLastCheckTime() {
		return lastCheckTime;
	}
	
	public void setLastCheckTime(Date lastCheckTime) {
		this.lastCheckTime = lastCheckTime;
	}
	
	@Column(name="ISDELETE")
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
}
