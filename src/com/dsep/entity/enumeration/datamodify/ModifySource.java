package com.dsep.entity.enumeration.datamodify;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

public class ModifySource extends EnumModule{

	public static final DsepEnum FEEDBACK = new DsepEnum("1","反馈");
	public static final DsepEnum DATACHANGE = new DsepEnum("2","数据修改");
	
	public ModifySource(){
		setTypeMaterial();
	}
	
	@Override
	protected void setTypeMaterial() {
		// TODO Auto-generated method stub
		this.typeMaterial = new DsepEnum[]{FEEDBACK,DATACHANGE};
	}

}
