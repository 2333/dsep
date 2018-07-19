package com.dsep.dao.dsepmeta.project;

import java.util.List;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.project.ItemFunds;

public interface ItemFundsDao  extends DsepMetaDao<ItemFunds, String>{

	public abstract List<ItemFunds> getAll();
	public abstract List<ItemFunds> pageFind(String item_id,int pageIndex,
			int pageSize, Boolean desc, String orderProperName);
	
	public abstract List<ItemFunds> getSearchData(String startDate,String endDate,String invoiceNumber,String item_id,int page,int pageSize);
	
	public int Count(String item_id);
}
