package com.meta.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.jqcol.dicrule.JqGridColDicRule;
import com.meta.domain.MetaDicDomain;

/**
 * 元数据中字典服务接口
 * @author thbin
 *
 */
@Transactional(propagation=Propagation.SUPPORTS)
public interface MetaDicService {
	/**
	 * 根据字典类型的ID获得当前字典类型的领域实体
	 * @param id 字典ID
	 * @return
	 */	
	public abstract MetaDicDomain getById(String id);  
	/**
	 * 获取字典根据规则进行筛选
	 * @param id
	 * @param dicRules
	 * @return
	 */
	public abstract MetaDicDomain getMetaDicDomain(String id,List<JqGridColDicRule> dicRules);
}
