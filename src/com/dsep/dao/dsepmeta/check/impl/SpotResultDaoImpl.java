package com.dsep.dao.dsepmeta.check.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.check.SpotResultDao;
import com.dsep.entity.dsepmeta.PubResult;
import com.dsep.entity.dsepmeta.SpotResult;

public class SpotResultDaoImpl extends DsepMetaDaoImpl<SpotResult, String>
implements SpotResultDao{

	

	@Override
	public List<SpotResult> selectAllSpotResult(int pageIndex, int pageSize,
			boolean desc, String orderProperName) {
		// TODO Auto-generated method stub
		List<SpotResult> result=new LinkedList<SpotResult>();
		
		
		result=super.page(pageIndex, pageSize, desc, orderProperName);	
		return result;
	}

	@Override
	public List<SpotResult> selectSpotResultByUnitID(String unitID) {
		// TODO Auto-generated method stub
		List<SpotResult> result = new LinkedList<SpotResult>();
		String tableName=super.getTableName("D", "spot_result");
		StringBuilder hql=new StringBuilder("Select * from "+tableName);
		
		List<Object> params = new LinkedList<Object>();
		
		List<String> conditionColumns = new LinkedList<String>();	
		
		if(StringUtils.isBlank(unitID)){
			params.add(unitID);
			conditionColumns.add("UNIT_ID");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		
		result=super.hqlFind(hql.toString(), params.toArray());
		return result;
	}

	@Override
	public void saveOneSpotResult(SpotResult spotResult) {
		// TODO Auto-generated method stub
		String pk = super.save(spotResult);
	//	super.saveOrUpdate(spotResult);
	}

	@Override
	public void saveSpotResultList(List<SpotResult> spotResultList) {
		// TODO Auto-generated method stub
		for(int i=0;i<spotResultList.size();i++){
			SpotResult spotRes= spotResultList.get(i);
			super.saveOrUpdate(spotRes);
		}
	}

	@Override
	public void deleteAllSpotResult() {
		// TODO Auto-generated method stub
		String tableName=super.getTableName("D", "spot_result");
		StringBuilder sql=new StringBuilder("delete from "+tableName);
		
		super.sqlBulkUpdate(sql.toString());
	}
	@Override
	public int getCount(){
		int result;
		String tableName=super.getTableName("D", "spot_result");
		String sql=new String("select count(*) from " +tableName);
		
		result=super.sqlCount(sql);
		
		return result;
	}
	@Override
	public List<SpotResult> getAllData() {
		// TODO Auto-generated method stub
		List<SpotResult> result=new LinkedList<SpotResult>();
		String tableName=super.getTableName("D", "spot_result");
		String sql=new String("select * from " +tableName);
		
		result=super.sqlFind(sql);  
		
		return result;
	}
	
}
