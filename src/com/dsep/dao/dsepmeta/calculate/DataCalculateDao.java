package com.dsep.dao.dsepmeta.calculate;

import java.util.List;



import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.dsepmeta.DataCalculateConfig;



public interface DataCalculateDao extends DsepMetaDao<DataCalculateConfig, String>{

	public abstract List<DataCalculateConfig> getDataCalculateConfig(int pageIndex,
			int pageSize, Boolean desc, String orderProperName);

	public abstract List<DataCalculateConfig> getDataCaledConfig(int pageIndex, int pageSize,
			Boolean desc, String orderProperName);
	

}
