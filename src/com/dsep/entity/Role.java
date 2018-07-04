package com.dsep.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@Entity
@Table(name="DSEP_RBAC_ROLE")
@JsonAutoDetect
@JsonIgnoreProperties(value = {"rights"})
public class Role implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2108191982727012922L;
	private String id;
	private String name;
	private String category;
	private Set<Right> rights= new HashSet<Right>(0);
	@Id
	@Column(name="ID",length=5)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="NAME",length=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name="CATEGORY",length=1)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@ManyToMany(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
	@JoinTable(name = "DSEP_RBAC_ROLE_RIGHT", 
	inverseJoinColumns = { @JoinColumn(name = "RIGHT_ID", referencedColumnName = "ID", nullable = false) },
	joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false) })
	public Set<Right> getRights() {
		return rights;
	}

	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}
	
}
