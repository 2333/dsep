package com.meta.dao;
import com.dsep.dao.common.Dao;
import com.meta.entity.MetaAttrStyle;

public interface MetaAttrStyleDao extends Dao<MetaAttrStyle, String>{
	
	public abstract String saveBySql(MetaAttrStyle attrStyle);

}
