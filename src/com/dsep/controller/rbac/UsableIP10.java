package com.dsep.controller.rbac;

import java.util.ArrayList;
/**
 * 
 * 
 * ```java
	{	"username":"zhangsan",
		"password":"password",
		"location":"changzhou",
     	"ipType":"dynamic",
     	"game":"menghuanxiyou"
		"ips":[
			"114.226.128.222:1080,aa,bb,changzhou",
			"114.226.135.46:1080,aa,bb,changzhou",
			"114.226.105.168:1080,aa,bb,changzhou",
			"222.185.160.121:1080,aa,bb,changzhou",
			"222.185.23.209:1080,aa,bb,changzhou",
			"222.185.137.210:1080,aa,bb,changzhou",
			"114.228.75.53:1080,aa,bb,changzhou",
			"114.226.65.242:1080,aa,bb,changzhou"],
		"isValidate":true,
		"userWinNum":10,  // 用户开卡窗口总数
		"avaliableWinNum":5, // 用户实时窗口数
		"port":1080,
		"info":"ok",
		"exptime":279624753
	}
```
 *
 */
public class UsableIP10 {
	public String username;
	public String password;
	public String location;
	public ArrayList<String> ips = new ArrayList<String> ();
	public Boolean isValidate;
	public Integer avaliableWinNum;
	public Integer userWinNum;
	public Integer port;
	public String info;
	public String game;
	public Long remainTime;
}
