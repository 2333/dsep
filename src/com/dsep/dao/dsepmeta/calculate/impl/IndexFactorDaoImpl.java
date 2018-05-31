package com.dsep.dao.dsepmeta.calculate.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.calculate.IndexFactorDao;
import com.dsep.entity.dsepmeta.ConvFactorValue;

public class IndexFactorDaoImpl extends DsepMetaDaoImpl<ConvFactorValue,String> 
	implements IndexFactorDao{
	
	private String tableName = super.getTableName("O", "CONV_FACTOR");
	@Override
	public Map<String, Double> getIndexFactor(String indexName, String catId) {
		String sqlString = String.format("select INDEX_CONT, AVG_VALUE from "+ tableName +" where INDEX_NAME = ? and CAT_ID is null or CAT_ID = ?");
		List<Object[]> value = super.sqlScalarResults(sqlString,
				new String[] { "INDEX_CONT", "AVG_VALUE" },new Object[]{indexName,catId});
		Map<String, Double> map = new LinkedHashMap<String, Double>();
		for(Object[] o : value){
			map.put((String) o[0], (double) o[1]);
		}
		return map;
	}
	
	@Override
	public List<ConvFactorValue> getCFValueByContentAndDisc(String discId,String content){
		String sql = " select * from " + tableName + " t where  t.DISC_ID='" 
	           + discId + "'";
		sql += (content==null||"".equals(content))?"":" and t.CONTENT='" + content + "'";
		return super.sqlFind(sql);
	}

}
