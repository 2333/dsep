package com.dsep.entity.enumeration.feedback;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

/**
 * 反馈答复项的状态
 * @author Pangeneral
 *
 */
public class FeedbackResponseStatus extends EnumModule{

	public static DsepEnum UNIT = new DsepEnum("1","未提交");
	public static DsepEnum SUMBIT = new DsepEnum("2","已提交");
	public static DsepEnum FINISH = new DsepEnum("3","已完成");
	public static DsepEnum DELETE = new DsepEnum("4","已删除");
	
	public FeedbackResponseStatus(){
		setTypeMaterial();
	}
	
	@Override
	protected void setTypeMaterial() {
		// TODO Auto-generated method stub
		this.typeMaterial = new DsepEnum[]{UNIT,SUMBIT,FINISH,DELETE};
	}
	
	
	
}
