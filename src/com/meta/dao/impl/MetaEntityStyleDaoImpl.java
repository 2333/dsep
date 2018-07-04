package com.meta.dao.impl;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DaoImpl;
import com.meta.dao.MetaEntityStyleDao;
import com.meta.entity.MetaEntityStyle;

public class MetaEntityStyleDaoImpl extends DaoImpl<MetaEntityStyle, String>
       implements MetaEntityStyleDao{

	public MetaEntityStyle getByOccassion(String entityId, String occassion) {
		String sql = null;
		List<MetaEntityStyle> list = null;
		if(StringUtils.isBlank(occassion)){
			sql = "select * from META_ENTITY_STYLE t where t.ENTITYID = ? and (t.OCCASSION is null or t.OCCASSION = '')";
			list = sqlFind(sql, new Object[]{entityId});
		}else{
			sql = "select * from META_ENTITY_STYLE t where t.ENTITYID = ? and t.OCCASSION = ?";
			list = sqlFind(sql, new Object[]{entityId, occassion});
		}
		if(list!=null && list.size()> 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}
