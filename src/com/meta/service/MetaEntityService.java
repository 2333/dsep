package com.meta.service;

import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.jqcol.dicrule.JqGridColDicRule;
import com.meta.domain.MetaEntityDomain;
import com.meta.entity.MetaAttrStyle;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaEntity;

@Transactional(propagation=Propagation.SUPPORTS)
public interface MetaEntityService {
	/**
	 * 根据实体ID获得当前实体
	 * @param id 实体ID
	 * @return 实体
	 */
	public abstract MetaEntity getById(String id);
	/**
	 * 根据实体表名称获得当前实体
	 * @param name 实体名称
	 * @return 实体
	 */
	public abstract MetaEntity getByName(String name);
	/**
	 * 根据领域ID和分类ID获得所有的实体类
	 * @param domainId 领域ID
	 * @param categoryId 分类ID
	 * @return 该领域该分类下的所有实体类
	 */
	public abstract List<MetaEntity> getEntities(String domainId, String categoryId);
	/** 
	 * 根据实体和实体样式使用场合获得获得该实体对应的显示模型
	 * @param entityID 实体类ID
	 * @param styleOccassion 实体样式使用风格
	 * @return 实体Domain对象
	 */
	public abstract MetaEntityDomain getEntityDomain(String entityID, String styleOccassion);
	/** 
	 * 根据实体和实体样式使用场合获得获得该实体对应的显示模型，不考虑使用场合（使用场合默认为"C"类型）
	 * @param entityID 实体类ID 
	 * @return 实体Domain对象
	 */
	public abstract MetaEntityDomain getEntityDomain(String entityID);
	/**
	 * 根据实体和实体样式使用场合获得获得该实体对应的显示模型
	 * @param entity 实体
	 * @param styleOccassion 实体样式使用风格
	 * @return 实体Domain对象
	 */
	public abstract MetaEntityDomain getEntityDomain(MetaEntity entity, String styleOccassion);
	
	public abstract MetaEntityDomain getEntityDomian(MetaEntity entity, String styleOccassion,List<JqGridColDicRule> dicRules);
	/**
	 * 根据实体和实体样式使用场合获得获得该实体对应的显示模型（使用场合默认为"C"类型）
	 * @param entity 实体
	 * @return 实体Domain对象
	 */
	public abstract MetaEntityDomain getEntityDomain(MetaEntity entity);
	/**
	 * 根据实体ID获得属性元数据
	 * @param entityID 实体ID
	 * @param attrEngName 属性英文名称
	 * @return 属性元数据
	 */
	public abstract MetaAttribute getEntityAttrbute(String entityID, String attrEngName);
	/**
	 * 根据实体获得属性元数据
	 * @param entity 实体
	 * @param attrEngName 属性英文名称
	 * @return 属性元数据
	 */
	public abstract MetaAttribute getEntityAttribute(MetaEntity entity, String attrEngName);
	/**
	 * 通过实体Id 删除实体
	 * @param entityId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean deleteEntityById(String entityId);
	
	/**
	 * 给实体添加一个属性
	 * @param entityId
	 * @param attribute
	 * @return 返回添加属性的ID(主键)
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract String addAttribute(String entityId,MetaAttribute attribute,MetaAttrStyle attrStyle);
	/**
	 * 获取带有附件的实体id和实体名
	 * @return
	 */
	public abstract Map<String, String> getEntitiesHaveFile();
}
