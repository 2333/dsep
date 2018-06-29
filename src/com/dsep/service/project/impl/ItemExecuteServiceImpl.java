package com.dsep.service.project.impl;

import java.util.List;

import com.dsep.dao.dsepmeta.project.ItemExecuteDao;
import com.dsep.entity.project.ItemExecute;
import com.dsep.service.project.ItemExecuteService;
import com.dsep.vm.PageVM;

public class ItemExecuteServiceImpl implements ItemExecuteService {

	private ItemExecuteDao itemExecuteDao;
	public ItemExecuteDao getItemExecuteDao() {
		return itemExecuteDao;
	}

	public void setItemExecuteDao(ItemExecuteDao itemExecuteDao) {
		this.itemExecuteDao = itemExecuteDao;
	}

	@Override
	public void update(ItemExecute itemExecute) {
		itemExecuteDao.saveOrUpdate(itemExecute);
	}

	@Override
	public void delete(String id) {
		
		itemExecuteDao.deleteByKey(id);
	}

	@Override
	public PageVM<ItemExecute> page(String id, int pageIndex, int pageSize,
			Boolean desc, String orderProperName)
			throws InstantiationException, IllegalAccessException {
		
		 /*获取当前页的项目经费支出条目*/
		 List<ItemExecute> itemExecute = itemExecuteDao.page(pageIndex, pageSize, desc, orderProperName);
		 /*获取总的条数*/
		 int totalCount = itemExecuteDao.Count();
		PageVM<ItemExecute> result = new PageVM<ItemExecute>(pageIndex,
				totalCount, pageSize, itemExecute);
		return result;
	}

	@Override
	public ItemExecute getResultByItemId(String itemId) {
		// TODO Auto-generated method stub
		return itemExecuteDao.getResultByItemId(itemId);
	}

	@Override
	public String create(ItemExecute itemExecute) {
		// TODO Auto-generated method stub
		return itemExecuteDao.save(itemExecute);
	}

	@Override
	public ItemExecute getResultById(String resultId) {
		// TODO Auto-generated method stub
		return itemExecuteDao.getResultById(resultId);
	}

}
