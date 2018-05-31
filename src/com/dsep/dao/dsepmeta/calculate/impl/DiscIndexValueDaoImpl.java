package com.dsep.dao.dsepmeta.calculate.impl;

import java.util.List;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.calculate.DiscIndexValueDao;
import com.dsep.entity.dsepmeta.DiscLastIndexValue;

public class DiscIndexValueDaoImpl extends DsepMetaDaoImpl<DiscLastIndexValue, String> 
implements DiscIndexValueDao {
	
	private String getDiscIndexTableName(){
		return super.getTableName("O", "DISC_INDEX_VALUE");
	}
	@Override
	public List<DiscLastIndexValue> isExist(String indexId,String discId,String unitId){
		String sql = " select * from " + getDiscIndexTableName() + " t "
				+ " where t.INDEX_ID='" + indexId + "' and t.DISC_ID='"
				+ discId + "' and t.UNIT_ID='" + unitId + "'";
		return super.sqlFind(sql);
	}
	
	@Override
	public List<DiscLastIndexValue> getDiscLastIndexValues(String discId,String indexId){
		String sql = " select * from " + getDiscIndexTableName() + " t "
				+ " where t.DISC_ID='" + discId + "' and t.INDEX_ID='"
				+ indexId + "'";
		return super.sqlFind(sql);
	}
	
	@Override
	public List<DiscLastIndexValue> getDiscLastIndexValues(String discId){
		String sql = " select * from " + getDiscIndexTableName() + " t "
				+ " where t.DISC_ID='" + discId + "'";
		return super.sqlFind(sql);
	}
	@Override
	public List<String> getCaledUnitIDByDisc(String discId) {
		String sql="select distinct(UNIT_ID) from " +getDiscIndexTableName()
				+" where DISC_ID='"+discId+"'";
		return super.GetShadowResult(sql);
	}
}
