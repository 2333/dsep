package com.meta.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.meta.entity.MetaDomain;

@Transactional(propagation=Propagation.SUPPORTS)
public interface MetaDomainService {	
	/**
	 * 
	 * @param category 实体对应的数据分类
	 * @param seqNo  该实体在该类别中的序号
	 * @return
	 */
	abstract public String getEntityId(MetaDomain domain, String category, int seqNo);
	/**
	 * 根据ID
	 * @param domainId
	 * @return
	 */
	abstract public MetaDomain getById(String domainId);
	/**
	 * 获得当前可用的领域对象，如果有多个，则返回第一个
	 * @param occasion 使用场合
	 * @return 第一个可用的领域对象
	 */
	public abstract MetaDomain getAvailDomain(String occasion); 
	/**
	 * 获得当前所有可用的领域对象
	 * @param occasion	领域对象场合
	 * @return	所有可用的领域对象列表
	 */
	public abstract List<MetaDomain> getAllAvailDomain(String occasion);
	/**
	 * 新建一个Domain的所有实体，要求该Domain的状态必须是NEW，新建完成后，状态变成USING	
	 * @param domain 需要新建实体的Domain
	 * @return 是否成功构造该Domain的所有实体
	 */
	public abstract boolean buildDomainEntities(MetaDomain domain);
}
