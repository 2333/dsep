package com.dsep.util;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.dsep.common.exception.SessionNullException;
import com.dsep.entity.Right;
import com.dsep.entity.User;
import com.dsep.service.publicity.process.factory.DsepProcessFactory;

public class UserSession {
	
	private HttpSession session;

	public UserSession(HttpSession session){
		this.session = session;
	}
	
	
	public void setCurrentUser(User user){
		session.setAttribute("userSession", user);
		session.setAttribute("processFactory",DsepProcessFactory.getDsepFactory(user));
		session.setMaxInactiveInterval(60*40);
	}
	
	public void setMenuRights(List<Right> menuRights){
		session.setAttribute("menuRightsSession", menuRights);
		session.setMaxInactiveInterval(60*40);
	}
	
	public void setForbidRights(List<Right> forbidRights){
		session.setAttribute("forbidRightsSession", forbidRights);
		session.setMaxInactiveInterval(60*40);
	}
	
	public DsepProcessFactory getDsepProcessFactory(){
		DsepProcessFactory processFactory = (DsepProcessFactory)session.getAttribute("processFactory");
		return processFactory;
	}
	
	public User getCurrentUser(){
		User user = (User)session.getAttribute("userSession");
		if(user == null){
			throw new SessionNullException();
		}
		return user;
	}
	
	public List<Right> getMenuRights(){
		List<Right> menuRights = (List<Right>)session.getAttribute("menuRightsSession");
		if(menuRights == null){
			throw new SessionNullException();
		}
		return menuRights;
	}
	
	public List<Right> getForbidRights(){
		List<Right> forbidRights = (List<Right>)session.getAttribute("forbidRightsSession");
		if(forbidRights == null){
			throw new SessionNullException();
		}
		return forbidRights;
	}
	
}
