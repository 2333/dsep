package com.dsep.entity.enumeration.feedback;

import com.dsep.entity.enumeration.DsepEnum;
import com.dsep.entity.enumeration.EnumModule;

/**
 * 反馈批次的状态
 * @author Pangeneral
 *
 */
public class FeedbackStatus extends EnumModule{
	
	public static DsepEnum UNBEGIN = new DsepEnum("0","反馈未开始");
	public static DsepEnum FEEDBACKOPEN = new DsepEnum("1","中心处理中");
	public static DsepEnum FEEDBACKBEGIN = new DsepEnum("2","答复进行中");
	public static DsepEnum STOP = new DsepEnum("3","反馈暂停");
	
	public FeedbackStatus(){
		setTypeMaterial();
	}
	
	@Override
	protected void setTypeMaterial() {
		// TODO Auto-generated method stub
		this.typeMaterial = new DsepEnum[]{UNBEGIN,FEEDBACKOPEN,FEEDBACKBEGIN,STOP};
	}
}
