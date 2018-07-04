package com.dsep.controller.rbac;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dsep.entity.RosConnIpCache;
import com.dsep.service.rbac.RosConnIpCacheService;

@Controller
public class RosConnectionUtil {
	

	//public static Set<String> usableRosConnectionIPs = new HashSet<String>();
	public String currentRosConnectionIP = "";
	
	public String getCurrentRosConnectionIP(String rosLocation, RosConnIpCacheService rosConnIpCacheService) {
		// 用于承载有效的ROS_API连接IP
		String ip= "";
		String classPath = new Object() {
	        public String getPath() {
	            return this.getClass().getClassLoader().getResource("").getPath();  
	        }
		}.getPath();
		if (classPath.startsWith("//")) {
			classPath = classPath.substring(1);
		}
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
		
		if (checkConnectionIsOK(currentRosConnectionIP)) {
			//usableRosConnectionIPs.clear();
			List<RosConnIpCache> cacheIps = rosConnIpCacheService.getAllIpsByRosLocation(rosLocation);
			ArrayList<RosConnIpCache> allUsableRosIPs = getAllUsableRosIPs(currentRosConnectionIP, rosLocation);
			for (RosConnIpCache ele : allUsableRosIPs) {
				System.out.println("获取所有ROS可用的IP，当前为" + ele.getIpValue());
			}
			for (RosConnIpCache cacheFromDB : cacheIps) {
				for (RosConnIpCache cacheFromRos : allUsableRosIPs) {
					if (cacheFromDB.getIpPppoeName().equals(cacheFromRos.getIpPppoeName()) && 
							cacheFromDB.getRosLocation().equals(cacheFromRos.getRosLocation())) {
						cacheFromDB.setIpValue(cacheFromRos.getIpValue());
						System.out.println("cacheFromDB.getIpValue=" + cacheFromDB.getIpValue());
					}
				}
			}
			rosConnIpCacheService.updateAllConnIpByRosLocation(rosLocation, cacheIps);
			System.out.println("rosConnIpCacheService.updateAllConnIpByRosLocation finished!");
			return currentRosConnectionIP;
		} else {
			System.out.println("currentRosConnectionIP失效，需要从usableRosConnectionIPs中轮询获取可用IP");
			String newConnectionIP = null;
			List<RosConnIpCache> cacheIps = rosConnIpCacheService.getAllIpsByRosLocation(rosLocation);
			for (RosConnIpCache ele : cacheIps) {
				if (checkConnectionIsOK(ele.getIpValue())) {
					System.out.println("找到可用IP：" + ele.getIpValue());
					newConnectionIP = ele.getIpPppoeName();
					break;
				}
			}
			if (newConnectionIP != null) {
				currentRosConnectionIP = newConnectionIP;
				ArrayList<RosConnIpCache> allUsableRosIPs = getAllUsableRosIPs(currentRosConnectionIP, rosLocation);
				for (RosConnIpCache cacheFromDB : cacheIps) {
					for (RosConnIpCache cacheFromRos : allUsableRosIPs) {
						if (cacheFromDB.getIpPppoeName().equals(cacheFromRos.getIpPppoeName()) && 
								cacheFromDB.getRosLocation().equals(cacheFromRos.getRosLocation()))
							cacheFromDB.setIpValue(cacheFromRos.getIpValue());
					}
				}
				rosConnIpCacheService.updateAllConnIpByRosLocation(rosLocation, cacheIps);
			} else {
				System.out.println("彻底lost connection，需要管理员维护!");
				return "error";
			}
			return currentRosConnectionIP;
		}
	}
	private boolean checkConnectionIsOK(String connectionIP) {
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
			Ros.closeConn(rosConn);
		}
	}
	
	/***
	 * 
	 * @param rosLocation
	 * @param connectionIP
	 * @return
	 * private Integer id;
	private Integer rosId;
	private String rosLocation;
	private String ipPppoeName;
	private String ipValue;
	 */
	private ArrayList<RosConnIpCache> getAllUsableRosIPs(String connectionIP,
			String rosLocation) {
		// get username and password by querying rosLocation, need modify
		ApiConnection rosConn = new Ros().getConn(connectionIP, "admin", "1qaz_xsW@");
		
		ArrayList<RosConnIpCache> rosConnIpCacheList = new ArrayList<RosConnIpCache>();
		ArrayList<String> activePPPoEIpAddresses = new ArrayList<String>();
		try {
			List<Map<String, String>> rs = rosConn.execute("/ip/address/print");
			
			for (Map<String,String> r : rs) {
				  System.out.println(r);
				  String addr = null;
				  String pppoename = null;
				  if (r.get("actual-interface").contains("pppoe-out")) {
					  RosConnIpCache cache = new RosConnIpCache();
					  addr = r.get("address");
					  pppoename = r.get("interface");
					  addr = addr.substring(0, addr.length() - 3);
					  pppoename = pppoename.substring(pppoename.indexOf("pppoe-out") + 9);
					  cache.setRosLocation(rosLocation);
					  cache.setIpPppoeName(pppoename);
					  cache.setIpValue(addr);
					  rosConnIpCacheList.add(cache);
					  activePPPoEIpAddresses.add(addr);
				  }
				}
		} catch (MikrotikApiException e) {
			e.printStackTrace();
		} finally {
			Ros.closeConn(rosConn);
		}
		return rosConnIpCacheList;
	}
}
