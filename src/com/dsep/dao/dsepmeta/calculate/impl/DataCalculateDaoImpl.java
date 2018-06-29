package com.dsep.dao.dsepmeta.calculate.impl;



import java.util.List;


import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.calculate.DataCalculateDao;
import com.dsep.entity.dsepmeta.DataCalculateConfig;


public class DataCalculateDaoImpl extends 
	DsepMetaDaoImpl<DataCalculateConfig, String> implements DataCalculateDao {

	private String configTableName = super.getTableName("O", "DATA_CAL_CONFIG");
	@Override
	public List<DataCalculateConfig> getDataCalculateConfig(int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		String sql = "select * from " + configTableName + " order by " + orderProperName;
		List<DataCalculateConfig> result = super.sqlPage(sql, pageIndex, pageSize);
		return result;
	}
	@Override
	public List<DataCalculateConfig> getDataCaledConfig(int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		String sql = "select * from " + configTableName +" where CAL_STATUS='1' "+ " order by " + orderProperName;
		List<DataCalculateConfig> result = super.sqlPage(sql, pageIndex, pageSize);
		return result;
	}
	

}
