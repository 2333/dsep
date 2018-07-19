package com.dsep.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;


@Entity
@Table(name="DSEP_RBAC_ROSCONNIPCACHE")
@JsonAutoDetect
public class RosConnIpCache implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2108191982727012922L;
	private Integer id;
	private Integer rosId;
	private String rosLocation;
	private String ipPppoeName;
	private String ipValue;
	private Date lastUpdateTime;
	private String lastUpdateStr;
	
	
	
	@Id
	@Column(name="ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="ROSID")
	public Integer getRosId() {
		return rosId;
	}

	public void setRosId(Integer rosId) {
		this.rosId = rosId;
	}

	@Column(name="ROSLOCATION",length=20)
	public String getRosLocation() {
		return rosLocation;
	}

	public void setRosLocation(String rosLocation) {
		this.rosLocation = rosLocation;
	}

	@Column(name="IPPPPOENAME",length=20)
	public String getIpPppoeName() {
		return ipPppoeName;
	}

	public void setIpPppoeName(String ipPppoeName) {
		this.ipPppoeName = ipPppoeName;
	}

	@Column(name="IPVALUE",length=20)
	public String getIpValue() {
		return ipValue;
	}

	public void setIpValue(String ipValue) {
		this.ipValue = ipValue;
	}
	
	@Column(name="LASTUPDATETIME")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name="LASTUPDATSTR",length=50)
	public String getLastUpdateStr() {
		return lastUpdateStr;
	}

	public void setLastUpdateStr(String lastUpdateStr) {
		this.lastUpdateStr = lastUpdateStr;
	}

	
	
}
