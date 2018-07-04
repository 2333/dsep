package com.dsep.vm;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.UserIpLog;
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})  
public class UserIpLogVM {
	private UserIpLog userIpLog;

	public UserIpLog getUserIpLog() {
		return userIpLog;
	}

	public void setUserIpLog(UserIpLog userIpLog) {
		this.userIpLog = userIpLog;
	}
}
