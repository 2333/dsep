package com.dsep.dao.dsepmeta.base.impl;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.base.CategoryDao;
import com.dsep.entity.dsepmeta.Category;

public class CategoryDaoImpl extends DsepMetaDaoImpl<Category, String> implements CategoryDao{
	
	private String getTableName()
	{
		return super.getTableName("X", "CATEGORY");
	}
	@Override
	public Category getCategoryById(String id) {
		// TODO Auto-generated method stub
		String sql=String.format("select * from  %s  where  ID=?",this.getTableName());
		return (Category)super.sqlUniqueResult(sql, new Object[]{id});
	}

	@Override
	public String getNameById(String id) {
		// TODO Auto-generated method stub
		String sql=String.format("select NAME from %s where ID=?",this.getTableName());
		return (String)super.sqlUniqueResult(sql, new Object[]{id});
	}

	@Override
	public String getIndexIdById(String id) {
		// TODO Auto-generated method stub
		String sql=String.format("select INDEX_ID from %s where ID=?",this.getTableName());
		return (String)super.sqlUniqueResult(sql, new Object[]{id});
	}

	@Override
	public String getCollectIdById(String id) {
		// TODO Auto-generated method stub
		String sql=String.format("select COLLECT_ID from %s where ID=?",this.getTableName());
		return (String)super.sqlUniqueResult(sql, new Object[]{id});
	}
	
}
