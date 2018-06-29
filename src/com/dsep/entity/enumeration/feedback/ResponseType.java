package com.dsep.entity.enumeration.feedback;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

public class ResponseType extends EnumModule{

	public static DsepEnum DELETE= new DsepEnum("1","删除");
	public static DsepEnum KEEP = new DsepEnum("2","保留");
	public static DsepEnum CHANGE = new DsepEnum("3","修改");
	public static DsepEnum Write = new DsepEnum("4","已答复");
	
	
	public ResponseType(){
		setTypeMaterial();
	}
	
	@Override
	protected void setTypeMaterial() {
		// TODO Auto-generated method stub
		this.typeMaterial = new DsepEnum[]{DELETE,CHANGE,KEEP,Write};
	}

}
