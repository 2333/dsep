package com.dsep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DSEP_BASE_PROVINCE")
public class Province implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 464768606105231461L;
	private String id;
	private String name; 
	
	@Id
	@Column(name="ID",length=5, nullable = false)
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
