package com.meta.dao;

import java.util.List;
import java.util.Map;

import com.dsep.dao.common.Dao;
import com.meta.entity.MetaInnerStateDetail;
public interface MetaInnerStateDetailDao extends Dao<MetaInnerStateDetail, String>{
	
	/**
	 * 通过domianid获取本轮内部状态的详细信息
	 * @param domainId
	 * @return
	 */
	public abstract List<MetaInnerStateDetail> getInnerStateDetailByDomainId(String domainId,int pageIndex,int pageSize,Boolean desc,String orderpropName);
	
	/**
	 * 根据领域ID，获取本轮内部状态记录数
	 * @param domainId
	 * @return
	 */
	public abstract int getInnerDetailCount(String domainId);
	
}
