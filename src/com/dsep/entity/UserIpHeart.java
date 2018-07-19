package com.dsep.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;


@Entity
@Table(name="DSEP_RBAC_USERIPHEART")
@JsonAutoDetect
public class UserIpHeart implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2108191982727012922L;
	private Integer id;
	private String loginId;
	private String machineId;
	private String useIp;
	private String lastRecordTimeStr;
	private Integer flag;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="LOGINID", length=100)
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	@Column(name="MACHINEID", length=100)
	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	@Column(name="USEIP", length=2000)
	public String getUseIp() {
		return useIp;
	}

	public void setUseIp(String useIp) {
		this.useIp = useIp;
	}


	@Column(name="LASTRECORDTIMESTR", length=50)
	public String getLastRecordTimeStr() {
		return lastRecordTimeStr;
	}

	public void setLastRecordTimeStr(String lastRecordTimeStr) {
		this.lastRecordTimeStr = lastRecordTimeStr;
	}

	
	@Column(name="FLAG")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
