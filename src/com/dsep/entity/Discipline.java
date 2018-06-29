package com.dsep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="DSEP_BASE_DISCIPLINE")
public class Discipline implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -12958618071685252L;
	private String id;
	private DiscContent discContent;
	private String name; 
	
	@Id
	@Column(name="ID",length=6, nullable = false)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)  
	@Cascade(value=CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "CONT_ID")
	public DiscContent getDisContent() {
		return discContent;
	}
	
	public void setDisContent(DiscContent discContent) {
		this.discContent = discContent;
	}
	
	@Column(name="NAME",length=20, nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
