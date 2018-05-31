package com.dsep.vm;

import com.dsep.entity.dsepmeta.DataCalculateConfig;


public class DataCalculateVM {

	public String calStatus;
	public DataCalculateConfig dataCalculateConfig;
	
	
	public DataCalculateVM(){
		
	}
	
	public DataCalculateVM(DataCalculateConfig dataCalculateConfig){
		this.calStatus = dataCalculateConfig.getCalStatus();
		this.dataCalculateConfig = dataCalculateConfig;
	}
	public String getCalStatus() {
		return calStatus;
	}
	public void setCalStatus(String calStatus) {
		
		if(dataCalculateConfig.getCalStatus().equals("1")){
			this.calStatus = "已计算";
		}
		else 
			this.calStatus = "未计算";
	}

	public DataCalculateConfig getDataCalculateConfig() {
		return dataCalculateConfig;
	}
	public void setDataCalculateConfig(DataCalculateConfig dataCalculateConfig) {
		this.dataCalculateConfig = dataCalculateConfig;
	}
	
	
	
	
}
