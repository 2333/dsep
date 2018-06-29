package com.dsep.entity.enumeration.datamodify;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

public class ModifyType extends EnumModule{
	public static final DsepEnum CHANGE = new DsepEnum("1","修改");
	public static final DsepEnum DELETE = new DsepEnum("2","删除");
	
	public ModifyType(){
		setTypeMaterial();
	}
	
	@Override
	protected void setTypeMaterial() {
		// TODO Auto-generated method stub
		this.typeMaterial = new DsepEnum[]{CHANGE,DELETE};
	}
}
