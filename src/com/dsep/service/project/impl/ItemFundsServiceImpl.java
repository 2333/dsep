package com.dsep.service.project.impl;

import java.util.List;

import com.dsep.dao.dsepmeta.project.ItemFundsDao;
import com.dsep.dao.dsepmeta.project.PassItemDao;
import com.dsep.entity.project.ItemFunds;
import com.dsep.entity.project.PassItem;
import com.dsep.service.project.ItemFundsService;
import com.dsep.vm.PageVM;

public class ItemFundsServiceImpl implements ItemFundsService{
	private ItemFundsDao itemFundsDao;
	private PassItemDao passItemDao;
	
	@Override
	public String create(ItemFunds itemFunds) {
		return itemFundsDao.save(itemFunds);
	}
	
	@Override
	public void update(ItemFunds itemFunds) {
		itemFundsDao.saveOrUpdate(itemFunds);
	}


	@Override
	public void delete(String id) {
		itemFundsDao.deleteByKey(id);
	}

	@Override
	public PageVM<ItemFunds> page(String item_id,int pageIndex, int pageSize,
			Boolean desc, String orderProperName)
			throws InstantiationException, IllegalAccessException {
		
		 /*获取当前页的项目经费支出条目*/
		 List<ItemFunds> itemFunds = itemFundsDao.pageFind(item_id,pageIndex, pageSize, desc, orderProperName);
		 /*获取总的条数*/
		 int totalCount = itemFundsDao.Count(item_id);
		PageVM<ItemFunds> result = new PageVM<ItemFunds>(pageIndex,
				totalCount, pageSize, itemFunds);
		return result;
	}

	public ItemFundsDao getItemFundsDao() {
		return itemFundsDao;
	}

	public void setItemFundsDao(ItemFundsDao itemFundsDao) {
		this.itemFundsDao = itemFundsDao;
	}

	public PassItemDao getPassItemDao() {
		return passItemDao;
	}


	public void setPassItemDao(PassItemDao passItemDao) {
		this.passItemDao = passItemDao;
	}


	@Override
	/**
	 * 根据id返回项目实体
	 */
	public PassItem getPassItem(String itemId) {
		return passItemDao.get(itemId);
		
	}
	
	@Override
	/**
	 * 根据id返回经费实体
	 */
	public ItemFunds getItemFunds(String fundsId) {
		return itemFundsDao.get(fundsId);
		
	}

	@Override
	public PageVM<ItemFunds> getSearch(String startDate, String endDate,String invoiceNumber,String item_id,
			int page, int pageSize) {
		List<ItemFunds> funds = itemFundsDao.getSearchData(startDate,endDate,invoiceNumber,item_id,page,pageSize);
		int totalCount=funds.size();
		PageVM<ItemFunds> result=new PageVM<ItemFunds>(page ,totalCount, pageSize , funds);
		return result;
	}

}
