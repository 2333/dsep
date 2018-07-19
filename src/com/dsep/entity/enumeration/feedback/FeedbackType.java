package com.dsep.entity.enumeration.feedback;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

public class FeedbackType extends EnumModule{
	public static DsepEnum NOPROFMATERIAL = new DsepEnum("1","证明材料不正确");
	public static DsepEnum DATAUNFORMAL = new DsepEnum("2","数据填写不规范");
	public static DsepEnum REPEATSUBMIT = new DsepEnum("3","重复填报");
	public static DsepEnum PUBNOTRIGHT = new DsepEnum("4","公共库不一致");
	public static DsepEnum THESISQUOTE = new DsepEnum("5","论文引用"); 
	public static DsepEnum OBJECTION = new DsepEnum("6","被异议数据");
	
	public FeedbackType(){
		setTypeMaterial();
	}
	
	@Override
	protected void setTypeMaterial() {
		// TODO Auto-generated method stub
		this.typeMaterial = new DsepEnum[]{NOPROFMATERIAL,DATAUNFORMAL,REPEATSUBMIT,PUBNOTRIGHT,THESISQUOTE,OBJECTION};
	}
	
}
