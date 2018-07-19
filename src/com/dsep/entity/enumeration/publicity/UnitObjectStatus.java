package com.dsep.entity.enumeration.publicity;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

public class UnitObjectStatus extends EnumModule{
	
	public static final DsepEnum NOTSUBMIT = new DsepEnum("0", "未提交");//学校尚未提交异议
	public static final DsepEnum SUBMIT = new DsepEnum("1", "已提交");//学校已提交异议
	public static final DsepEnum UNITNOTPASS = new DsepEnum("2","单位删除异议");
	
	public UnitObjectStatus() {
		setTypeMaterial();
	}

	@Override
	protected void setTypeMaterial() {
		this.typeMaterial = new DsepEnum[] { NOTSUBMIT, SUBMIT};
	}
}
