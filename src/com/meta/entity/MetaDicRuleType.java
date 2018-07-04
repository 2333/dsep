package com.meta.entity;

import java.io.Serializable;

public enum MetaDicRuleType implements Serializable{
	CATEGORY("ML");//
	
	private final String dicRuleType;
	
	private MetaDicRuleType(String type) {
		// TODO Auto-generated constructor stub
		dicRuleType = type;
	}
	public static MetaDicRuleType getDicRuleType(String ruleType){
		String upperRuleType=ruleType.toUpperCase();
		if(upperRuleType.equals("ML")) return MetaDicRuleType.CATEGORY;
		return MetaDicRuleType.CATEGORY;
	}
	public String getDicRuleType() {
		return dicRuleType;
	}
	
}
