package com.dsep.vm;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.UserIpHeart;
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})  
public class UserIpHeartVM {
	private UserIpHeart userIpHeart;

	public UserIpHeart getUserIpHeart() {
		return userIpHeart;
	}

	public void setUserIpHeart(UserIpHeart userIpHeart) {
		this.userIpHeart = userIpHeart;
	}
}
