package com.dsep.vm.project;

import java.util.Set;

import com.dsep.entity.project.ApplyItem;
import com.dsep.entity.project.ItemProvideFunds;
import com.dsep.entity.project.PassItem;
import com.dsep.entity.project.TeamMember;
import com.dsep.util.DateProcess;
import com.dsep.util.Dictionaries;

public class ApplyItemVM {

	private ApplyItem applyItem;
	private String id;
	private String projectId;
	private String itemName;
	private String funds;
	private String principalName;
	private String projectName;
	private String projectEndDate;
	private String discId;
	private Integer currentState;

	public ApplyItemVM(ApplyItem applyItem) {
		this.applyItem = applyItem;

		this.projectId = applyItem.getSchoolProject().getId();
		this.projectName = applyItem.getSchoolProject().getProjectName();
		this.setProjectEndDate(DateProcess.getShowingDate(applyItem
				.getSchoolProject().getEndDate()));
		this.setFunds(String.valueOf(applyItem.getFunds()));
		this.setItemName(applyItem.getItemName());
		this.setId(applyItem.getId());
		this.setDiscId(Dictionaries.getDisciplineName(applyItem.getDiscId()));
		this.setCurrentState(applyItem.getCurrentState());
		Set<TeamMember> members = applyItem.getTeamMembers();
		TeamMember principal = null;
		for (TeamMember m : members) {
			if (m.getIsPrincipal()) {
				principal = m;
				break;
			}
		}
		this.setPrincipalName(principal.getName());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDiscId() {
		return discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public ApplyItem getApplyItem() {
		return applyItem;
	}

	public void setApplyItem(ApplyItem applyItem) {
		this.applyItem = applyItem;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getFunds() {
		return funds;
	}

	public void setFunds(String funds) {
		this.funds = funds;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public Integer getCurrentState() {
		return currentState;
	}

	public void setCurrentState(Integer currentState) {
		this.currentState = currentState;
	}

}
