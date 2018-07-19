package com.dsep.entity.enumeration;

public class DsepEnum {
	
	private String status;
	private String showing;


	public String getStatus(){
		return status;
	}
	
	public String getShowing(){
		return showing;
	}
	
	public DsepEnum(String status,String showing){
		this.status = status;
		this.showing = showing;
	}
}
