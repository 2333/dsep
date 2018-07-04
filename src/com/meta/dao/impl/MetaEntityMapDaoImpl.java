package com.meta.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DaoImpl;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.dao.MetaEntityMapDao;
import com.meta.entity.MetaEntityMap;

public class MetaEntityMapDaoImpl extends DaoImpl<MetaEntityMap, String> implements MetaEntityMapDao{

	@Override
	public List<MetaEntityMap> getEntityMaps(String oriEntityId,
			String tarEntityId, String catId) {
		// TODO Auto-generated method stub
		List<Object> values= new ArrayList<Object>(0);
		StringBuilder hql = new StringBuilder("from MetaEntityMap em where 1=1 ");
		if(StringUtils.isNotBlank(oriEntityId)){
			hql.append(" and  em.originEntity.id = ? ");
			values.add(oriEntityId);
		}
		if(StringUtils.isNotBlank(tarEntityId)){
			hql.append(" and em.targetEntity.id = ? ");
			values.add(tarEntityId);
		}
		if(StringUtils.isNotBlank(catId)){
			hql.append(" and ( (em.catId=?) or (em.catId='') or (em.catId is null) ) ");
			values.add(catId);
		}
		return super.hqlFind(hql.toString(), values.toArray());
	}

	@Override
	public MetaEntityMap getEntityMap(String oriEntityId, String tarEntityId,
			String catId) {
		// TODO Auto-generated method stub
		List<MetaEntityMap> metaEntityMaps = getEntityMaps(oriEntityId, tarEntityId, catId);
		if(metaEntityMaps!=null&&metaEntityMaps.size()>0){
			return metaEntityMaps.get(0);
		}else{
			return null;
		}
		
	}

}
