package com.dsep.service.project.impl;

import java.util.List;

import com.dsep.dao.dsepmeta.project.ItemProvideFundsDao;
import com.dsep.dao.dsepmeta.project.PassItemDao;
import com.dsep.entity.project.ItemProvideFunds;
import com.dsep.entity.project.PassItem;
import com.dsep.service.project.ItemProvideFundsService;
import com.dsep.vm.PageVM;

public class ItemProvideFundsServiceImpl implements ItemProvideFundsService{
	private PassItemDao passItemDao;
	private ItemProvideFundsDao itemProvideFundsDao;
	@Override
	public String create(ItemProvideFunds itemProvideFunds) {
		return itemProvideFundsDao.save(itemProvideFunds);
	}
	@Override
	public void update(ItemProvideFunds itemProvideFunds) {
		itemProvideFundsDao.saveOrUpdate(itemProvideFunds);
		
	}
	@Override
	public void delete(String id) {
		itemProvideFundsDao.deleteByKey(id);
		
	}
	@Override
	public ItemProvideFunds getItemProvideFunds(String provideFundsId) {
		// TODO Auto-generated method stub
		return itemProvideFundsDao.get(provideFundsId);
	}
	@Override
	public PageVM<ItemProvideFunds> getSearch(String startDate, String endDate,String item_id,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		List<ItemProvideFunds> itemProvideFunds = itemProvideFundsDao.getSearchData(startDate,endDate,item_id,page,pageSize);
		int totalCount=itemProvideFundsDao.Count(item_id);
		PageVM<ItemProvideFunds> result=new PageVM<ItemProvideFunds>(page ,totalCount, pageSize , itemProvideFunds);
		return result;
	}
	@Override
	public PassItem getPassItem(String itemId) {
		// TODO Auto-generated method stub
		return passItemDao.get(itemId);
	}
	@Override
	public PageVM<ItemProvideFunds> page(String item_id, int pageIndex,
			int pageSize, Boolean desc, String orderProperName)
			throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		List<ItemProvideFunds> itemProvideFunds = itemProvideFundsDao.pageFind(item_id,pageIndex, pageSize, desc, orderProperName);
		 /*获取总的条数*/
		 int totalCount = itemProvideFundsDao.Count(item_id);
		PageVM<ItemProvideFunds> result = new PageVM<ItemProvideFunds>(pageIndex,
				totalCount, pageSize, itemProvideFunds);
		return result;
	}
	public PassItemDao getPassItemDao() {
		return passItemDao;
	}
	public void setPassItemDao(PassItemDao passItemDao) {
		this.passItemDao = passItemDao;
	}
	public ItemProvideFundsDao getItemProvideFundsDao() {
		return itemProvideFundsDao;
	}
	public void setItemProvideFundsDao(ItemProvideFundsDao itemProvideFundsDao) {
		this.itemProvideFundsDao = itemProvideFundsDao;
	}
	
}
