package com.dsep.entity.enumeration.publicity;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

public class PublicityStatus extends EnumModule{
	
	public static DsepEnum UNBEGIN = new DsepEnum("0","公示未开始");
	public static DsepEnum PREPUBBEGIN = new DsepEnum("1","预公示进行中");
	public static DsepEnum PUBBEGIN = new DsepEnum("2","公示进行中");
	public static DsepEnum STOP = new DsepEnum("3","公示暂停");
	
	public PublicityStatus(){
		setTypeMaterial();
	}

	@Override
	protected void setTypeMaterial() {
		// TODO Auto-generated method stub
		super.typeMaterial = new DsepEnum[]{UNBEGIN,PREPUBBEGIN,PUBBEGIN,STOP};
	}
	
}
