package com.dsep.controller.rbac;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Administrator
 * 在内存中记录，一个用户占用了多少IP
 */
public class UserAndObtainedIPsMaps {
	public int guanliyuanIP = 10;
	private HashMap<String, Set<String>> map = new HashMap<String, Set<String>>();
	public DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	public Date myDate1;

	private UserAndObtainedIPsMaps() {
		try {
			myDate1 = dateFormat1.parse("2018-07-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}  
    private static UserAndObtainedIPsMaps single=null;  
    //静态工厂方法   
    public static UserAndObtainedIPsMaps getInstance() {  
         if (single == null) {    
             single = new UserAndObtainedIPsMaps();  
         }    
        return single;  
    }
	public HashMap<String, Set<String>> getMap() {
		return map;
	}
	public void setMap(HashMap<String, Set<String>> map) {
		this.map = map;
	}
	public static UserAndObtainedIPsMaps getSingle() {
		return single;
	}
	public static void setSingle(UserAndObtainedIPsMaps single) {
		UserAndObtainedIPsMaps.single = single;
	}  
    
    
}
