package com.dsep.entity.project;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.Attachment;

@JsonAutoDetect
@JsonIgnoreProperties(value = { "itemFunds", "itemExecute", "teamMembers" })
public class PassItem {

	private String Id;
	private String unitId;
	private String discId;
	private String itemName;
	private String contactInfo;
	private Double funds;
	private String itemTarget;
	private String itemContent;
	private String teamIntro;
	private String userId;

	private Set<TeamMember> teamMembers;
	private Set<ItemFunds> itemFunds;
	private Set<ItemExecute> itemExecute;
	private Set<ItemProvideFunds> itemProvideFunds;

	public Set<ItemProvideFunds> getItemProvideFunds() {
		return itemProvideFunds;
	}

	public void setItemProvideFunds(Set<ItemProvideFunds> itemProvideFunds) {
		this.itemProvideFunds = itemProvideFunds;
	}

	private int itemState;

	private Attachment attachment;
	private Attachment mediumReport;
	private Attachment finalReport;

	private SchoolProject schoolProject;

	public PassItem() {

	}

	public PassItem(ApplyItem applyItem) {
		// TODO Auto-generated constructor stub
		this.unitId = applyItem.getUnitId();
		this.discId = applyItem.getDiscId();
		this.itemName = applyItem.getItemName();
		this.contactInfo = applyItem.getContactInfo();
		this.funds = applyItem.getFunds();
		this.itemTarget = applyItem.getItemTarget();
		this.itemContent = applyItem.getItemContent();
		this.teamIntro = applyItem.getTeamIntro();

		//Found shared references to a collection
		Set<TeamMember> members = new HashSet<TeamMember>();
		members.addAll(applyItem.getTeamMembers());
		this.teamMembers = members;

		this.itemState = 1;
		this.attachment = applyItem.getAttachment();
		this.schoolProject = applyItem.getSchoolProject();
		this.userId = applyItem.getUserId();
	}

	public Attachment getMediumReport() {
		return mediumReport;
	}

	public void setMediumReport(Attachment mediumReport) {
		this.mediumReport = mediumReport;
	}

	public Attachment getFinalReport() {
		return finalReport;
	}

	public void setFinalReport(Attachment finalReport) {
		this.finalReport = finalReport;
	}

	public Set<ItemFunds> getItemFunds() {
		return itemFunds;
	}

	public void setItemFunds(Set<ItemFunds> itemFunds) {
		this.itemFunds = itemFunds;
	}

	public Set<ItemExecute> getItemExecute() {
		return itemExecute;
	}

	public void setItemExecute(Set<ItemExecute> itemExecute) {
		this.itemExecute = itemExecute;
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

	public Set<TeamMember> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(Set<TeamMember> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public int getItemState() {
		return itemState;
	}

	public void setItemState(int itemState) {
		this.itemState = itemState;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
