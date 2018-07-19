package com.dsep.entity.enumeration.feedback;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

public class CenterAdvice extends EnumModule{
	public static DsepEnum YES = new DsepEnum("1","同意");
	public static DsepEnum NO = new DsepEnum("0","不同意");

	
	public CenterAdvice(){
		setTypeMaterial();
	}
	
	@Override
	protected void setTypeMaterial() {
		// TODO Auto-generated method stub
		this.typeMaterial = new DsepEnum[]{YES,NO};
	}
}
