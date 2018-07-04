package com.dsep.controller.rbac;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dsep.entity.RosConnIpCache;

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
	
	public static boolean closeConn(ApiConnection conn) {
		try {
			conn.close(); // disconnect from router
			return true;
		} catch (MikrotikApiException e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	public static void main(String[] args) {
		ApiConnection rosConn = new Ros().getConn("222.185.137.222", "admin", "1qaz_xsW@");
		
		try {
			List<Map<String, String>> rs = rosConn.execute("/ip/address/print");
			
			for (Map<String,String> r : rs) {
				  //System.out.println(r);
				  String addr = null;
				  String pppoe = null;
				  if (r.get("actual-interface").contains("pppoe-out")) {
					  RosConnIpCache cache = new RosConnIpCache();
					  addr = r.get("address");
					  pppoe = r.get("interface");
					  //System.out.println(addr);
					  addr = addr.substring(0, addr.length() - 3);
					  pppoe = pppoe.substring(pppoe.indexOf("pppoe-out") + 9);
					  System.out.println(pppoe + "\t" + addr);
				  }
				}
		} catch (MikrotikApiException e) {
			e.printStackTrace();
		} finally {
			Ros.closeConn(rosConn);
		}
	}
	
	
	
	
}
