package com.dsep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DSEP_BASE_DISC_CONTENT")
public class DiscContent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4908331690757007636L;
	private String id;
	private String name; 
	
	@Id
	@Column(name="ID",length=2, nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="NAME",length=20, nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}