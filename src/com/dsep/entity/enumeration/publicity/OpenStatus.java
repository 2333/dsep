package com.dsep.entity.enumeration.publicity;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

public class OpenStatus extends EnumModule{
	public static DsepEnum OPEN = new DsepEnum("1","进行中");
	public static DsepEnum CLOSE = new DsepEnum("0","已结束");
	
	public OpenStatus(){
		setTypeMaterial();
	}
	
	
	@Override
	protected void setTypeMaterial() {
		// TODO Auto-generated method stub
		this.typeMaterial = new DsepEnum[]{OPEN,CLOSE};
	}
	
	
}
