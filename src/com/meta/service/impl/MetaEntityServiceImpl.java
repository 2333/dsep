package com.meta.service.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.domain.dsepmeta.jqcol.dicrule.JqGridColDicRule;
import com.dsep.util.NumberFormatUtil;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.dao.MetaAttrStyleDao;
import com.meta.dao.MetaAttributeDao;
import com.meta.dao.MetaEntityDao;
import com.meta.dao.MetaEntityStyleDao;
import com.meta.domain.MetaAttrDomain;
import com.meta.domain.MetaEntityDomain;
import com.meta.entity.MetaAttrStyle;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaDataType;
import com.meta.entity.MetaEntity;
import com.meta.entity.MetaEntityStyle;
import com.meta.service.MetaCheckRuleService;
import com.meta.service.MetaDicService;
import com.meta.service.MetaEntityService;

public class MetaEntityServiceImpl implements MetaEntityService {
	private MetaEntityDao metaEntityDao;
	private MetaAttributeDao metaAttributeDao;
	private MetaEntityStyleDao metaEntityStyleDao;
	private MetaAttrStyleDao metaAttrStyleDao;
	private MetaDicService metaDicService;
	private MetaCheckRuleService metaCheckRuleService;

	@Cacheable(value ="metaEntityCache", key="#id")
	public MetaEntity getById(String id) {
		return metaEntityDao.get(id);
	}

	public MetaEntity getByName(String name) {
		return metaEntityDao.getByName(name);
	}

	public List<MetaEntity> getEntities(String domainId, String categoryId){
		return metaEntityDao.getEntities(domainId, categoryId);
	}
	
	@Cacheable(value ="metaEntityDomainCache", key="#entity.getId() + #styleOccassion")
	public MetaEntityDomain getEntityDomain(MetaEntity entity, String styleOccassion) {
		if(entity == null ) return null;
		MetaEntityStyle entityStyle = metaEntityStyleDao.getByOccassion(entity.getId(), styleOccassion);
		Set<MetaAttribute> attrs = entity.getAttributes();
		Set<MetaAttrStyle> attrStyles = entityStyle.getAttrStyles();
		
		//寻找当前实体对应的属性领域对象
		List<MetaAttrDomain> attrDomains = new LinkedList<MetaAttrDomain>();
		for(MetaAttribute attr : attrs){
			MetaAttrStyle foundAttrStyle = null; 
			for(MetaAttrStyle attrStyle : attrStyles){
				if(attrStyle.getAttrId().equals(attr.getId())) {
					foundAttrStyle = attrStyle;
					break;
				}
			}
			if(foundAttrStyle != null){
				MetaAttrDomain attrDomain = new MetaAttrDomain(attr, foundAttrStyle);
				attrDomains.add(attrDomain);
				//如果是字典类型，则加载字典
				if(attrDomain.getDataTypeObject()==MetaDataType.DIC){
					attrDomain.setDic(metaDicService.getById(attrDomain.getDicId()));
				}
				//如果校验规则不为空，则加载校验规则
				if(StringUtils.isNotBlank(attrDomain.getCheckRuleId())){
					attrDomain.setCheckRule(metaCheckRuleService.getById(attrDomain.getCheckRuleId()));
				}
			}
		}
		
		MetaEntityDomain entityDomain = new MetaEntityDomain(entity, entityStyle, attrDomains);
		//查询实体对应的规则
		if(StringUtils.isNotBlank(entityDomain.getCheckRuleId())){
			entityDomain.setCheckRule(metaCheckRuleService.getById(entityDomain.getCheckRuleId()));
		}
		return entityDomain;
	}
	
	public MetaEntityDomain getEntityDomain(String entityID, String styleOccassion) {
		MetaEntity entity = getById(entityID);
		return getEntityDomain(entity, styleOccassion);
	}

	@Override
	@Cacheable(value ="metaEntityDomainCache",key="#entityID+'C'")
	public MetaEntityDomain getEntityDomain(String entityID) {
		return getEntityDomain(entityID, "C");
	}

	@Override
	public MetaAttribute getEntityAttrbute(String entityID, String attrEngName){
		MetaEntity entity = getById(entityID);
		return getEntityAttribute(entity, attrEngName);
	}
	@Override
	public MetaAttribute getEntityAttribute(MetaEntity entity, String attrEngName)	{
		for(MetaAttribute attr:entity.getAttributes()){
			if(attr.getName().equals(attrEngName)){
				return attr;
			}
		}
		return null;
	}
	
	@Override
	public MetaEntityDomain getEntityDomain(MetaEntity entity) {
		return getEntityDomain(entity, "C");
	}
	
	public MetaEntityDao getMetaEntityDao() {
		return metaEntityDao;
	}

	public void setMetaEntityDao(MetaEntityDao metaEntityDao) {
		this.metaEntityDao = metaEntityDao;
	}

	public MetaEntityStyleDao getMetaEntityStyleDao() {
		return metaEntityStyleDao;
	}

	public void setMetaEntityStyleDao(MetaEntityStyleDao metaEntityStyleDao) {
		this.metaEntityStyleDao = metaEntityStyleDao;
	}

	public MetaDicService getMetaDicService() {
		return metaDicService;
	}

	public void setMetaDicService(MetaDicService metaDicService) {
		this.metaDicService = metaDicService;
	}

	public MetaCheckRuleService getMetaCheckRuleService() {
		return metaCheckRuleService;
	}

	public void setMetaCheckRuleService(MetaCheckRuleService metaCheckRuleService) {
		this.metaCheckRuleService = metaCheckRuleService;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean deleteEntityById(String entityId) {
		// TODO Auto-generated method stub
		if(metaEntityDao.deleteEntityById(entityId)){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public String addAttribute(String entityId, MetaAttribute attribute,MetaAttrStyle attrStyle) {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
		 	MetaEntity entity = metaEntityDao.get(entityId);
			Set<MetaAttribute> attributies = entity.getAttributes();
			Set<MetaEntityStyle> entityStyles = entity.getEntityStyles();
			Iterator<MetaEntityStyle> iterator = entityStyles.iterator();
			MetaEntityStyle entityStyle = null;
			if(iterator.hasNext()){
				entityStyle = iterator.next();
			}
			int seqNo = attributies.size()+1;
			attribute.setId("A"+entity.getId().substring(1)+NumberFormatUtil.getNumberFormat(seqNo, 2));
			attribute.setSeqNo(seqNo);
			attribute.setEntityId(entity.getId());
			//metaAttributeDao.saveBySql(attribute);
			if(metaAttributeDao.saveBySql(attribute)!=null){
				attrStyle.setId("S01"+attribute.getId());
				attrStyle.setAttrId(attribute.getId());
				attrStyle.setEntityStyleId(entityStyle.getId());
				metaAttrStyleDao.saveBySql(attrStyle);
			}
			return attribute.getId();
	}

	public MetaAttributeDao getMetaAttributeDao() {
		return metaAttributeDao;
	}

	public void setMetaAttributeDao(MetaAttributeDao metaAttributeDao) {
		this.metaAttributeDao = metaAttributeDao;
	}

	public MetaAttrStyleDao getMetaAttrStyleDao() {
		return metaAttrStyleDao;
	}

	public void setMetaAttrStyleDao(MetaAttrStyleDao metaAttrStyleDao) {
		this.metaAttrStyleDao = metaAttrStyleDao;
	}

	@Override
	public MetaEntityDomain getEntityDomian(MetaEntity entity,
			String styleOccassion, List<JqGridColDicRule> dicRules) {
		// TODO Auto-generated method stub
		if(entity == null ) return null;
		MetaEntityStyle entityStyle = metaEntityStyleDao.getByOccassion(entity.getId(), styleOccassion);
		Set<MetaAttribute> attrs = entity.getAttributes();
		Set<MetaAttrStyle> attrStyles = entityStyle.getAttrStyles();
		
		//寻找当前实体对应的属性领域对象
		List<MetaAttrDomain> attrDomains = new LinkedList<MetaAttrDomain>();
		for(MetaAttribute attr : attrs){
			MetaAttrStyle foundAttrStyle = null; 
			for(MetaAttrStyle attrStyle : attrStyles){
				if(attrStyle.getAttrId().equals(attr.getId())) {
					foundAttrStyle = attrStyle;
					break;
				}
			}
			if(foundAttrStyle != null){
				MetaAttrDomain attrDomain = new MetaAttrDomain(attr, foundAttrStyle);
				attrDomains.add(attrDomain);
				//如果是字典类型，则加载字典
				if(attrDomain.getDataTypeObject()==MetaDataType.DIC){
					attrDomain.setDic(metaDicService.getById(attrDomain.getDicId()));
				}
				//如果校验规则不为空，则加载校验规则
				if(StringUtils.isNotBlank(attrDomain.getCheckRuleId())){
					attrDomain.setCheckRule(metaCheckRuleService.getById(attrDomain.getCheckRuleId()));
				}
			}
		}
		
		MetaEntityDomain entityDomain = new MetaEntityDomain(entity, entityStyle, attrDomains);
		//查询实体对应的规则
		if(StringUtils.isNotBlank(entityDomain.getCheckRuleId())){
			entityDomain.setCheckRule(metaCheckRuleService.getById(entityDomain.getCheckRuleId()));
		}
		return entityDomain;
	}

	@Override
	public Map<String, String> getEntitiesHaveFile() {
		// TODO Auto-generated method stub
		return metaEntityDao.getEntitiesHaveFile();
	
	}

}
