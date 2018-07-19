package com.meta.dao;

import com.dsep.dao.common.Dao;
import com.meta.entity.MetaAttribute;

public interface MetaAttributeDao extends Dao<MetaAttribute, String>{
	
	public abstract String saveBySql(MetaAttribute attribute);
}
