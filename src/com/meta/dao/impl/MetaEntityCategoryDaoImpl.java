package com.meta.dao.impl;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DaoImpl;
import com.meta.dao.MetaEntityCategoryDao;
import com.meta.entity.MetaEntityCategory;

public class MetaEntityCategoryDaoImpl extends DaoImpl<MetaEntityCategory, String> 
       implements MetaEntityCategoryDao{
	
	public List<MetaEntityCategory> getByOccassion(String occassion) {
		return getByOccassion(occassion, null);
	}

	public List<MetaEntityCategory> getByOccassion(String occassion,
			String parentId) {
		String hql = null;
		List<MetaEntityCategory> list = null;
		if(parentId==null){
			hql = "from MetaEntityCategory a where a.occassion = ?";
			list=super.hqlFind(hql, new Object[]{occassion});
		}else if(parentId==""){
			hql = "from MetaEntityCategory a where a.occassion = ? and a.parentId = ?";
			list=super.hqlFind(hql, new Object[]{occassion,parentId});
		}else{
			hql = "from MetaEntityCategory a where a.occassion = ? and (a.parentId is null or a.parentId='')";
			list=super.hqlFind(hql, new Object[]{occassion});			
		}
		return list;
	}

}
