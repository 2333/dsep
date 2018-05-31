package com.dsep.service.flow;

import com.meta.entity.MetaDomain;

public interface DsepMainFlowService {
	/**
	 * * 获取当前主流程状态
	 * 0，本轮参评未开始进行
	 * 1，正在预参评
	 * 2，预参评结束
	 * 3，正在申报
	 * 4，申报结束
	 * 5，预公示
	 * 6，正在公示
	 * 7，公示结束
	 * 8，正在反馈
	 * 9，反馈结束
	 * 10，本轮参评结束
	 * @return
	 */
	public abstract String getCurrentState();
	
	/**
	 * 返回当前InnerState内部状态（用户权限控制）
	 * @return
	 */
	public abstract String getInnerState();
	
	/**
	 * 开启、关闭本轮参评（）
	 * @param state （0关闭，1开启）
	 * @return
	 */
	public abstract boolean updateState(String state);
	/**
	 * 更改主流程状态
	 * @param innerState
	 * 
	 * @return
	 */
	public abstract boolean updateInnerState(String innerState);
	
	public abstract MetaDomain getCurrentMetaDomain();
}
