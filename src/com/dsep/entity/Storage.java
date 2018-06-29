package com.dsep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DSEP_BASE_STORAGE")
public class Storage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String path;
	private String active;
	private String webUrl;
	private String protocol;
	private String userId;
	private String password;
	
	@Id
	@Column(name="ID" ,length=4)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="NAME",length=100,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="PATH",length=100,nullable=false)
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(name="ACTIVE",length=1,nullable=false)
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	@Column(name="WEB_URL",length=500)
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	
	@Column(name="PROTOCOL",length=10)
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	@Column(name="USER_ID",length=10)
	public String getUser() {
		return userId;
	}
	public void setUser(String userId) {
		this.userId = userId;
	}
	
	@Column(name="PASSWORD",length=10)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
