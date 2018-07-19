package com.dsep.vm.project;

import java.util.Set;

import com.dsep.entity.project.ItemFunds;
import com.dsep.entity.project.ItemProvideFunds;
import com.dsep.entity.project.PassItem;
import com.dsep.entity.project.TeamMember;
import com.dsep.util.Dictionaries;

public class PassItemVM {

	private PassItem passItem;
	private String projectId;
	private String itemName;
	private String projectName;
	private String principalName;
	private Double applyFunds;//申请经费总额
	private Double providedFunds;//已到账经费
	private Double restFunds;//账户余额
	private String projectStatus;

	public PassItemVM(PassItem passItem) {
		this.setPassItem(passItem);
		this.setProjectId(passItem.getSchoolProject().getId());
		this.setItemName(passItem.getItemName());
		this.setProjectName(passItem.getSchoolProject().getProjectName());
		this.setProjectStatus(Dictionaries.getSchoolProjectState(passItem
				.getSchoolProject().getCurrentState()));
		//负责人姓名
		Set<TeamMember> members = passItem.getTeamMembers();
		TeamMember principal = null;
		for (TeamMember m : members) {
			if (m.getIsPrincipal()) {
				principal = m;
				break;
			}
		}
		this.setPrincipalName(principal.getName());
		//申请经费
		this.setApplyFunds(passItem.getFunds());

		//计算已到账经费
		Set<ItemProvideFunds> providedFunds = passItem.getItemProvideFunds();
		Double provided = 0.0;
		for (ItemProvideFunds funds : providedFunds) {
			provided += funds.getProvideAmount();
		}
		this.setProvidedFunds(provided);

		//计算账户余额
		Set<ItemFunds> itemFunds = passItem.getItemFunds();
		Double consume = 0.0;
		for (ItemFunds funds : itemFunds) {
			consume += funds.getConsumption();
		}
		Double balance = this.getProvidedFunds() - consume;
		this.setRestFunds(balance);
	}

	public PassItem getPassItem() {
		return passItem;
	}

	public void setPassItem(PassItem passItem) {
		this.passItem = passItem;
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

	public String getProjectName() {
		return projectName;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public Double getApplyFunds() {
		return applyFunds;
	}

	public void setApplyFunds(Double applyFunds) {
		this.applyFunds = applyFunds;
	}

	public Double getProvidedFunds() {
		return providedFunds;
	}

	public void setProvidedFunds(Double providedFunds) {
		this.providedFunds = providedFunds;
	}

	public Double getRestFunds() {
		return restFunds;
	}

	public void setRestFunds(Double restFunds) {
		this.restFunds = restFunds;
	}

}
