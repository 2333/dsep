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
@Table(name="DSEP_RBAC_IP")
@JsonAutoDetect
public class Ip implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2108191982727012922L;
	private Integer id;
	private String rosName;
	private String pppoeName;
	
	private String name;
	private String category;
	
	@Id
	@Column(name="ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="PPPOENAME",length=20)
	public String getPppoeName() {
		return pppoeName;
	}

	public void setPppoeName(String pppoeName) {
		this.pppoeName = pppoeName;
	}

	@Column(name="NAME",length=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="ROSNAME",length=20)
	public String getRosName() {
		return rosName;
	}

	public void setRosName(String rosName) {
		this.rosName = rosName;
	}
	@Column(name="CATEGORY",length=1)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	
}
