package com.dsep.service.project;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.project.ItemProvideFunds;
import com.dsep.entity.project.PassItem;
import com.dsep.vm.PageVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface ItemProvideFundsService {

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract String create(ItemProvideFunds itemProvideFunds);
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void update(ItemProvideFunds itemProvideFunds);
	
	
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void delete(String id);
	
	public abstract ItemProvideFunds getItemProvideFunds(String provideFundsId);
	public abstract PageVM<ItemProvideFunds> getSearch(String startDate,String endDate,String item_id,int page,int pageSize);
	public abstract PassItem getPassItem(String itemId);
	public abstract PageVM<ItemProvideFunds> page(String item_id,int pageIndex,
			int pageSize, Boolean desc, String orderProperName)
			throws InstantiationException, IllegalAccessException;
}
