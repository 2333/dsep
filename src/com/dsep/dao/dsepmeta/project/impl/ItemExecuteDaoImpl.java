package com.dsep.dao.dsepmeta.project.impl;

import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.project.ItemExecuteDao;
import com.dsep.entity.project.ItemExecute;

public class ItemExecuteDaoImpl 
extends DsepMetaDaoImpl<ItemExecute,String> implements ItemExecuteDao{
	
	private String getTableName() {
		return super.getTableName("p", "itemExecute");
	}
	@Override
	public List<ItemExecute> getAll() {
		String sql = "select * from " + getTableName();
		return super.sqlFind(sql);
	}
	@Override
	public ItemExecute getResultByItemId(String itemId) {
		// TODO Auto-generated method stub
		String sql = "select * from "+getTableName()+" where item_id = '"+itemId+"'";
		return super.sqlFind(sql).get(0);
	}
	@Override
	public ItemExecute getResultById(String resultId) {
		// TODO Auto-generated method stub
		String sql = "select * from "+getTableName()+" where id = '"+resultId+"'";
		return super.sqlFind(sql).get(0);
	}
}
