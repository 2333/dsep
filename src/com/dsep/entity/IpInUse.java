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
@Table(name="DSEP_RBAC_IPINUSE")
@JsonAutoDetect
public class IpInUse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2108191982727012922L;
	private Integer id;
	private Integer userId;
	private Integer ipId;
	
	
	@Id
	@Column(name="ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="USERID")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name="IPID")
	public Integer getIpId() {
		return ipId;
	}

	public void setIpId(Integer ipId) {
		this.ipId = ipId;
	}
}
