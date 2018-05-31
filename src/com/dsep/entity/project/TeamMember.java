package com.dsep.entity.project;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(value = { "applyItems","passItems","principalApplyItems", "principalPassItems" })
public class TeamMember {
	private String id;
	private String name;
	private String email;
	private String info;
	private Boolean isPrincipal;

	private Set<ApplyItem> applyItems = new HashSet<ApplyItem>();
	private Set<PassItem> passItems = new HashSet<PassItem>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Set<ApplyItem> getApplyItems() {
		return applyItems;
	}

	public void setApplyItems(Set<ApplyItem> applyItems) {
		this.applyItems = applyItems;
	}

	public Set<PassItem> getPassItems() {
		return passItems;
	}

	public void setPassItems(Set<PassItem> passItems) {
		this.passItems = passItems;
	}

	public Boolean getIsPrincipal() {
		return isPrincipal;
	}

	public void setIsPrincipal(Boolean isPrincipal) {
		this.isPrincipal = isPrincipal;
	}
}
