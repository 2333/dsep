package com.dsep.entity.project;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.Attachment;

@JsonAutoDetect
@JsonIgnoreProperties(value = {"teamMembers" })
public class ApplyItem {
	private String Id;
	private String unitId;
	private String discId;
	private String userId;
	private String itemName;
	private String contactInfo;
	private Double funds;
	private String itemTarget;
	private String itemContent;
	private String teamIntro;
	// see com.dsep.util.project.ApplyItemStatusType
	private Integer currentState;
	private Set<TeamMember> teamMembers = new HashSet<TeamMember>();

	private Attachment attachment;
	private SchoolProject schoolProject;
	private JudgeResult judgeResult;

	public JudgeResult getJudgeResult() {
		return judgeResult;
	}

	public void setJudgeResult(JudgeResult judgeResult) {
		this.judgeResult = judgeResult;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getDiscId() {
		return discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Double getFunds() {
		return funds;
	}

	public void setFunds(Double funds) {
		this.funds = funds;
	}

	public String getItemTarget() {
		return itemTarget;
	}

	public void setItemTarget(String itemTarget) {
		this.itemTarget = itemTarget;
	}

	public String getItemContent() {
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public String getTeamIntro() {
		return teamIntro;
	}

	public void setTeamIntro(String teamIntro) {
		this.teamIntro = teamIntro;
	}

	public Integer getCurrentState() {
		return currentState;
	}

	public void setCurrentState(Integer currentState) {
		this.currentState = currentState;
	}

	public Set<TeamMember> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(Set<TeamMember> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public SchoolProject getSchoolProject() {
		return schoolProject;
	}

	public void setSchoolProject(SchoolProject schoolProject) {
		this.schoolProject = schoolProject;
	}

}
