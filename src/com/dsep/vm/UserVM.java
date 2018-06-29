package com.dsep.vm;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.User;
import com.dsep.util.Dictionaries;
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})  
public class UserVM {
	
	private User user;
	private String userTypeName;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUserTypeName() {
		String userType=this.user.getUserType();
		this.userTypeName = Dictionaries.getUserTypeName(userType);
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	

}
