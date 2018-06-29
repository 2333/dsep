package com.dsep.controller.rbac;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

public class RosConnectionUtil {
	public static Set<String> usableRosConnectionIPs = new HashSet<String>();
	public static String currentRosConnectionIP = "";
	
	public static String getCurrentRosConnectionIP() {
		String ip = "114.226.65.242";
		String classPath = new Object() {
	        public String getPath() {
	            return this.getClass().getClassLoader().getResource("")  
	    		        .getPath();  
	        }
		}.getPath();
		
		System.out.println(classPath);
		String realPath = classPath.substring(0, classPath.indexOf("WEB-INF"))  
		        + "WEB-INF/classes/com/dsep/controller/rbac/";  
		
		//String realPath=req.getServletContext().getRealPath("/WEB-INF/classes/com/dsep/controller/rbac/");
		System.out.println(realPath);
		File f = new File(realPath + "/test.txt");
		try {
			InputStream is = new FileInputStream(f);
			byte[] buf = new byte[100];
			int length = -1;
			
			while (-1 != (length = is.read(buf, 0, 100))) {
				ip = new String(buf, 0, length);
			}
			System.out.println("ip is:" + ip);
			is.close();
			if (checkConnectionIsOK(ip)) {
				System.out.println("从文本中Load的IP：" + ip + "是有效的！");
				currentRosConnectionIP = ip;
			} else {
				System.out.println("从文本中Load的IP：" + ip + "是无效的！");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// init usableRosConnectionIP
		if (usableRosConnectionIPs.size() == 0) {
			ArrayList<String> allUsableRosIPs = getAllUsableRosIPs(currentRosConnectionIP);
			for (String ele : allUsableRosIPs) {
				System.out.println("获取所有ROS可用的IP，当前为" + ele);
				usableRosConnectionIPs.add(ele);
			}
		}
		
		if (checkConnectionIsOK(currentRosConnectionIP)) {
			usableRosConnectionIPs.clear();
			ArrayList<String> allUsableRosIPs = getAllUsableRosIPs(currentRosConnectionIP);
			for (String ele : allUsableRosIPs) {
				System.out.println("获取所有ROS可用的IP，当前为" + ele);
				usableRosConnectionIPs.add(ele);
			}
			return currentRosConnectionIP;
		} else {
			System.out.println("currentRosConnectionIP失效，需要从usableRosConnectionIPs中轮询获取可用IP");
			String newConnectionIP = null;
			for (String ele : usableRosConnectionIPs) {
				if (checkConnectionIsOK(ele)) {
					System.out.println("找到可用IP：" + ele);
					newConnectionIP = ele;
					break;
				}
			}
			if (newConnectionIP != null) {
				currentRosConnectionIP = newConnectionIP;
				usableRosConnectionIPs.clear();
				ArrayList<String> allUsableRosIPs = getAllUsableRosIPs(currentRosConnectionIP);
				for (String ele : allUsableRosIPs) {
					usableRosConnectionIPs.add(ele);
				}
			} else {
				System.out.println("彻底lost connection，需要管理员维护!");
				return "error";
			}
			for (String ele : usableRosConnectionIPs) {
				System.out.println(ele);
			}
			return currentRosConnectionIP;
		}
	}
	private static boolean checkConnectionIsOK(String connectionIP) {
		ApiConnection rosConn = new Ros().getConn(connectionIP, "admin", "1qaz_xsW@");
		try {
			Object o = rosConn.execute("/ip/address/print");
			if (o != null) {
				return true;
			} else {
				return false;
			}
		} catch (MikrotikApiException e) {
			e.printStackTrace();
			return false;
		} finally {
			Ros
		}
	}
	private static ArrayList<String> getAllUsableRosIPs(String connectionIP) {
		ApiConnection rosConn = new Ros().getConn(connectionIP, "admin", "1qaz_xsW@");
		ArrayList<String> activePPPoEIpAddresses = new ArrayList<String>();
		try {
			List<Map<String, String>> rs = rosConn.execute("/ip/address/print");
			for (Map<String,String> r : rs) {
				  System.out.println(r);
				  String addr = null;
				  if (r.get("actual-interface").contains("pppoe-out")) {
					  addr = r.get("address");
					  addr = addr.substring(0, addr.length() - 3);
					  activePPPoEIpAddresses.add(addr);
				  }
				}
		} catch (MikrotikApiException e) {
			e.printStackTrace();
		}
		return activePPPoEIpAddresses;
	}
}
