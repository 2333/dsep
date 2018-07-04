package com.dsep.entity.enumeration.rbac;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

public class UserType extends EnumModule{

	public static final DsepEnum CENTER = new DsepEnum("1", "中心");
	public static final DsepEnum UNIT = new DsepEnum("2", "学校");
	public static final DsepEnum DISC = new DsepEnum("3","学科");
	
	@Override
	protected void setTypeMaterial() {
		// TODO Auto-generated method stub
		this.typeMaterial = new DsepEnum[] { CENTER , UNIT, DISC };
	}

}
