package com.dsep.controller.rbac;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

public class Ros {
	public ApiConnection getConn(String ip, String username, String password) {
		ApiConnection conn = null;
		
		try {
			//conn = ApiConnection.connect("192.168.1.29");
			//conn.login("admin",""); // log in to router
			conn = ApiConnection.connect(ip);
			conn.login(username, password); // log in to router
	        
		} catch (MikrotikApiException e) {
			e.printStackTrace();
		} 
		return conn;
	}
	
	public boolean closeConn(ApiConnection conn) {
		try {
			conn.close(); // disconnect from router
			return true;
		} catch (MikrotikApiException e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	
}
