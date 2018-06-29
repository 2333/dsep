package com.dsep.entity.enumeration.publicity;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

public class MaterialType extends EnumModule {

	public static final DsepEnum OBJECTION = new DsepEnum("1", "异议材料");
	public static final DsepEnum FEEDBACK_RESPONSE = new DsepEnum("2", "反馈答复材料");

	public MaterialType() {
		setTypeMaterial();
	}

	@Override
	protected void setTypeMaterial() {
		// TODO Auto-generated method stub
		this.typeMaterial = new DsepEnum[] { OBJECTION, FEEDBACK_RESPONSE };
	}
}
