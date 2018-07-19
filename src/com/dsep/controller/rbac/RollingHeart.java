package com.dsep.controller.rbac;

import java.util.List;

import com.dsep.entity.RosConnIpCache;
import com.dsep.entity.User;
import com.dsep.entity.UserIpHeart;
import com.dsep.service.rbac.RosConnIpCacheService;
import com.dsep.service.rbac.UserIpHeartService;
import com.dsep.service.rbac.UserService;

public class RollingHeart {
	public void rolling(RosConnIpCacheService rosConnIpCacheService, UserIpHeartService userIpHeartService, UserService userService) {
		List<UserIpHeart> list = userIpHeartService.getNoHeartData();
		
		for (UserIpHeart heart : list) {
			String loginId = heart.getLoginId();
			String ips = heart.getUseIp();
			if (ips == null || ips.length() == 0) {
				
			} else {
				User user = userService.getUserByLoginId(loginId);
				
				for (String useIp : ips.split(",")) {
					RosConnIpCache rosConnIpCache = rosConnIpCacheService.getRosConnIpCacheByIpValue(useIp);
					if (rosConnIpCache == null) continue;
					String pppoeName = rosConnIpCache.getIpPppoeName();
					String userdPppoeNumber = user.getUsedPppoeNumber();
					String newNumbers = "";
					if (userdPppoeNumber == null || userdPppoeNumber.length() == 0) {
						
					} else {
						String ele = pppoeName;
						String[] numbers = userdPppoeNumber.split(",");
						
						for (String n_ele : numbers) {
							// 需要下线的ip命中在线ips列表
							if (n_ele.equals(ele)) {
								user.setNownum(user.getNownum() + 1);
							} else {
								if (newNumbers.length() == 0) {
									newNumbers = n_ele;
								} else {
									newNumbers = newNumbers + "," + n_ele;
								}
							}
						}
					}
					
					user.setUsedPppoeNumber(newNumbers);
				}
				
				userService.UpdateUserAndIpsByMerge(user);
			}
			
		}
		userIpHeartService.updateHeartDataMoreThan300SecondsNotAttendCalc();
		
		
		
	}
}
