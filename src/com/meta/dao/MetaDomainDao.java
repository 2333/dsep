package com.meta.dao;
import java.util.List;

import com.dsep.dao.common.Dao;
import com.meta.entity.MetaDomain;
import com.meta.entity.MetaDomainState;

public interface MetaDomainDao extends Dao<MetaDomain, String>{
	/**
	 * 获得当前所有可用的领域对象
	 * @param occasion	领域对象场合
	 * @param state 当前状态
	 * @return	所有可用的领域对象列表
	 */
	public abstract List<MetaDomain> getAllAvailDomain(String occasion, MetaDomainState state);
	
	/**
	 * 对内部状态进行更新
	 * @param metaDomainId
	 * @param innerState 新状态
	 * @return
	 */
	public abstract boolean updateInnerState(String metaDomainId,String innerState);
	
	/**
	 * 对本轮状态进行更新（本轮参评是否可用）
	 * @param metaDomainId
	 * @param state （0不可用，1可用）
	 * @return
	 */
	public abstract boolean updateState(String metaDomainId,String state);

}
