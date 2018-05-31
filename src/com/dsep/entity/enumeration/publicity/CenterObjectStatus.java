package com.dsep.entity.enumeration.publicity;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

public class CenterObjectStatus extends EnumModule{
	public static final DsepEnum NOTSUBMIT = new DsepEnum("0", "未提交");//学校暂未提交异议
	public static final DsepEnum UNITSUBMIT = new DsepEnum("1", "未处理");//中心尚未处理异议
	public static final DsepEnum PROCESSED = new DsepEnum("2","已处理");//中心处理完异议后
	public static final DsepEnum NOTPASS = new DsepEnum("3","已删除");//中心删除异议
	
	public CenterObjectStatus() {
		setTypeMaterial();
	}

	@Override
	protected void setTypeMaterial() {
		this.typeMaterial = new DsepEnum[] { NOTSUBMIT, UNITSUBMIT,PROCESSED,NOTPASS};
	}
}
