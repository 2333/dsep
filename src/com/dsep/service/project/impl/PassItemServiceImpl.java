package com.dsep.service.project.impl;

import java.util.ArrayList;
import java.util.List;

import com.dsep.dao.dsepmeta.project.PassItemDao;
import com.dsep.entity.project.ApplyItem;
import com.dsep.entity.project.PassItem;
import com.dsep.service.project.PassItemService;
import com.dsep.util.project.ApplyItemStatus;
import com.dsep.vm.PageVM;
import com.dsep.vm.project.ApplyItemVM;
import com.dsep.vm.project.PassItemVM;

public class PassItemServiceImpl implements PassItemService {

	private PassItemDao passItemDao;

	@Override
	public String create(PassItem item) {
		return passItemDao.save(item);
	}

	@Override
	public void update(PassItem item) {
		passItemDao.saveOrUpdate(item);
	}

	@Override
	public void delete(String id) {
		passItemDao.deleteByKey(id);
	}

	@Override
	public PageVM<PassItem> page(String projectId, int pageIndex, int pageSize,
			Boolean desc, String orderProperName) {
		// TODO Auto-generated method stub
		List<PassItem> items = passItemDao.page(pageIndex, pageSize, desc,
				orderProperName);
		int totalCount = passItemDao.Count();

		PageVM<PassItem> result = new PageVM<PassItem>(pageIndex, totalCount,
				pageSize, items);
		return result;
	}

	@Override
	public PageVM<PassItemVM> retrive(String projectId, Integer status,
			String unitId, String discId, String principalId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		List<PassItem> items = passItemDao
				.retrive(projectId, status, unitId, discId, principalId,
						pageIndex, pageSize, desc, orderProperName);

		int totalCount = passItemDao.count(projectId, status, unitId, discId,
				principalId);
		
		List<PassItemVM> passItemVM = new ArrayList<PassItemVM>();
		for(PassItem item: items){
			passItemVM.add(new PassItemVM(item));
		}

		PageVM<PassItemVM> result = new PageVM<PassItemVM>(pageIndex, totalCount,
				pageSize, passItemVM);
		return result;
	}

	public PassItemDao getPassItemDao() {
		return passItemDao;
	}

	public void setPassItemDao(PassItemDao passItemDao) {
		this.passItemDao = passItemDao;
	}

	@Override
	public PageVM<PassItem> getPassItemsByProjectId(String projectId,
			int pageIndex, int pageSize, Boolean desc, String orderProperName) {
		// TODO Auto-generated method stub
		List<PassItem> items = new ArrayList<PassItem>();
		items = passItemDao.retrive(projectId, null, null, null, null, pageIndex, pageSize, desc, orderProperName);
		int totalCount = passItemDao
				.count(projectId, 1, null, null, null);

		PageVM<PassItem> result = new PageVM<PassItem>(pageIndex, totalCount,
				pageSize, items);

		return result;
	}

	@Override
	public PassItem getPassItemById(String passItemId) {
		// TODO Auto-generated method stub
		return passItemDao.getPassItemById(passItemId);
	}

}
