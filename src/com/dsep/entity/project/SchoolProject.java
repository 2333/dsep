package com.dsep.entity.project;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.Attachment;

@JsonAutoDetect
@JsonIgnoreProperties(value = { "applyItems","passItems" })
public class SchoolProject {
	private String id;
	private String unitId;
	private Date startDate;
	private Date endDate;
	private Integer currentState;
	private String projectType;
	private String projectName;
	private String projectIntro;
	private String projectRestrict;
	private String projectDetail;
	private String commitState;

	

	private Attachment attachment;

	private Set<ApplyItem> applyItems = new HashSet<ApplyItem>();
	private Set<PassItem> passItems = new HashSet<PassItem>();

	public String getId() {
		return id;
	}

	public Set<PassItem> getPassItems() {
		return passItems;
	}

	public void setPassItems(Set<PassItem> passItems) {
		this.passItems = passItems;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getCommitState() {
		return commitState;
	}

	public void setCommitState(String commitState) {
		this.commitState = commitState;
	}
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Integer getCurrentState() {
		return currentState;
	}

	public void setCurrentState(Integer currentState) {
		this.currentState = currentState;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectIntro() {
		return projectIntro;
	}

	public void setProjectIntro(String projectIntro) {
		this.projectIntro = projectIntro;
	}

	public String getProjectRestrict() {
		return projectRestrict;
	}

	public void setProjectRestrict(String projectRestrict) {
		this.projectRestrict = projectRestrict;
	}

	public String getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(String projectDetail) {
		this.projectDetail = projectDetail;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public Set<ApplyItem> getApplyItems() {
		return applyItems;
	}

	public void setApplyItems(Set<ApplyItem> applyItems) {
		this.applyItems = applyItems;
	}

}
