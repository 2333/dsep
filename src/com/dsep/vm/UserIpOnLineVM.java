package com.dsep.vm;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.Ip;
import com.dsep.entity.RosConnIpCache;
import com.dsep.entity.User;
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})  
public class UserIpOnLineVM {
	private User user;
	private RosConnIpCache cache;
	private Ip ip;
	private String onlineIp;
	private String status;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public RosConnIpCache getCache() {
		return cache;
	}
	public void setCache(RosConnIpCache cache) {
		this.cache = cache;
	}
	public Ip getIp() {
		return ip;
	}
	public void setIp(Ip ip) {
		this.ip = ip;
	}
	public String getOnlineIp() {
		return onlineIp;
	}
	public void setOnlineIp(String onlineIp) {
		this.onlineIp = onlineIp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
