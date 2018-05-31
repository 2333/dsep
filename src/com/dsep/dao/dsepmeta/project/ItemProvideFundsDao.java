package com.dsep.dao.dsepmeta.project;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.project.ItemProvideFunds;

public interface ItemProvideFundsDao extends DsepMetaDao<ItemProvideFunds, String>{

	public abstract List<ItemProvideFunds> getAll();
	public abstract List<ItemProvideFunds> pageFind(String item_id,int pageIndex,
			int pageSize, Boolean desc, String orderProperName);
	
	public abstract int Count(String item_id);
	public abstract List<ItemProvideFunds> getSearchData(String startDate,String endDate,String item_id,int page,int pageSize);
}
