package com.meta.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.util.NumberFormatUtil;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.dao.MetaEntityDao;
import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaEntity;

public class MetaEntityDaoImpl extends DaoImpl<MetaEntity, String> implements
		MetaEntityDao {

	public MetaEntity getByName(String name) {
		String hql = "from MetaEntity e where e.name = ? ";
		List<MetaEntity> entities = hqlFind(hql, new Object[] { name });
		if ((entities != null) && entities.size() > 0) {
			return entities.get(0);
		} else {
			return null;
		}
	}

	public List<MetaEntity> getEntities(String domainId, String categoryId) {
		if (StringUtils.isNotBlank(categoryId)) {
			String hql = "from MetaEntity e where e.domainId = ?  and e.categoryId = ?";
			List<MetaEntity> entities = hqlFind(hql, new Object[] { domainId,
					categoryId });
			return entities;
		} else {
			String hql = "from MetaEntity e where e.domainId = ? ";
			List<MetaEntity> entities = hqlFind(hql, new Object[] { domainId });
			return entities;
		}
	}

	@Override
	public boolean deleteEntityById(String entityId) {
		// TODO Auto-generated method stub
		super.deleteByKey(entityId);
		return true;
	}

	@Override
	public Map<String, String> getEntitiesHaveFile() {
		// TODO Auto-generated method stub
		String hql = " from MetaEntity where isExistFile = ? order by id asc";
		List<MetaEntity> entities = super.hqlFind(hql, new Object[]{"1"});
		Map<String, String> result = new LinkedHashMap<String,String>(0); 
		for(MetaEntity entity:entities){
			result.put(entity.getId(), entity.getChsName());
		}
		return result;
	}

}
