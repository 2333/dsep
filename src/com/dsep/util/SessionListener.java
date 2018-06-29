package com.dsep.util;

import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	
	public static HashMap<Object, Object> userMap = new HashMap<Object, Object>();
	private MySessionContext myc = MySessionContext.getInstance();

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		myc.AddSession(httpSessionEvent.getSession());
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		myc.DelSession(session);
	}
}