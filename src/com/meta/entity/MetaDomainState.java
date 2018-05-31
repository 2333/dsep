package com.meta.entity;

import java.io.Serializable;

public enum MetaDomainState  implements Serializable{
	NEW("0"), USING("1"), DISABLE("2");
	
	private final String state;
	private MetaDomainState(String state){
		this.state = state;
	}
	public String getState(){
		return state;
	}
	/**
	 * 根据状态字符串，返回状态ID
	 * @param state
	 * @return 状态ID
	 */
	public static MetaDomainState getState(String state)
	{
		if(state.equals("0")) return MetaDomainState.NEW;
		if(state.equals("1")) return MetaDomainState.USING;
		return MetaDomainState.DISABLE;
	}
}
