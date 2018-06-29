package com.dsep.entity.enumeration;

import java.util.HashMap;
import java.util.Map;

public abstract class EnumModule {

	protected DsepEnum[] typeMaterial;
	
	protected abstract void setTypeMaterial();
	
	public Map<String,String> getEnumMap(){
		Map<String,String> enumMap = new HashMap<String,String>();
		for(int i=0;i < typeMaterial.length;i++){
			enumMap.put(typeMaterial[i].getStatus(), typeMaterial[i].getShowing());
		}
		return enumMap;
	}
	
	public String getShowingByStatus(String status){
		for(int i=0;i < typeMaterial.length;i++){
			if( status.equals(typeMaterial[i].getStatus()))
				return typeMaterial[i].getShowing();
		}
		return "暂无";
	}

}
