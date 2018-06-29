package com.dsep.service.project;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.project.ItemFunds;
import com.dsep.entity.project.PassItem;

import com.dsep.vm.PageVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface ItemFundsService {

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract String create(ItemFunds itemFunds);
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void update(ItemFunds itemFunds);
	
	
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void delete(String id);
	
	public abstract ItemFunds getItemFunds(String fundsId);
	public abstract PageVM<ItemFunds> getSearch(String startDate,String endDate,String invoiceNumber,String item_id,int page,int pageSize);
	public abstract PassItem getPassItem(String itemId);
	public abstract PageVM<ItemFunds> page(String item_id,int pageIndex,
			int pageSize, Boolean desc, String orderProperName)
			throws InstantiationException, IllegalAccessException;
}
