package com.dsep.dao.dsepmeta.base.impl;

import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.base.DiscCategoryDao;
import com.dsep.entity.dsepmeta.DiscCategory;

public class DiscCategoryDaoImpl extends DsepMetaDaoImpl<DiscCategory,String> 
		implements DiscCategoryDao{

	private String getDiscCatTableName(){
		return super.getTableName("X", "CAT_DISC");
	}
	
	@Override
	public List<String> getDiscByCatId(String catId) {
		// TODO Auto-generated method stub
		String sql = String.format("select DISC_ID from %s where CAT_ID = ?",
				this.getDiscCatTableName());
		Object[] param = new Object[1];
		param[0] = catId;
		return super.GetShadowResult(sql, param);
	}

	@Override
	public String getCatByDiscId(String discId) {
		// TODO Auto-generated method stub
		String sql = String.format("select CAT_ID from %s where DISC_ID = ?",
				this.getDiscCatTableName());
		Object[] param = new Object[1];
		param[0] = discId;
		return (String)super.sqlUniqueResult(sql, param);
	}

}
