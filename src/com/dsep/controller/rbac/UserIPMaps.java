package com.dsep.controller.rbac;

import java.util.HashMap;

public class UserIPMaps {
	private HashMap<String, Integer> map = new HashMap<String, Integer>();
	private UserIPMaps() {}  
    private static UserIPMaps single=null;  
    //静态工厂方法   
    public static UserIPMaps getInstance() {  
         if (single == null) {    
             single = new UserIPMaps();  
         }    
        return single;  
    }
	public HashMap<String, Integer> getMap() {
		return map;
	}
	public void setMap(HashMap<String, Integer> map) {
		this.map = map;
	}
	public static UserIPMaps getSingle() {
		return single;
	}
	public static void setSingle(UserIPMaps single) {
		UserIPMaps.single = single;
	}  
    
    
}
