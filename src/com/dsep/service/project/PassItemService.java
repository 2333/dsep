package com.dsep.service.project;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.project.ApplyItem;
import com.dsep.entity.project.PassItem;
import com.dsep.vm.PageVM;
import com.dsep.vm.project.PassItemVM;

@Transactional(propagation = Propagation.SUPPORTS)
public interface PassItemService {

	public abstract String create(PassItem item);

	public abstract void update(PassItem item);

	public abstract void delete(String id);

	/**
	 * 多条件翻页查询
	 * 不需要某些限定条件的话传null即可
	 */
	public abstract PageVM<PassItem> page(String projectId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName);

	public abstract PageVM<PassItemVM> retrive(String projectId, Integer status,
			String unitId, String discId, String principalId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName);

	public abstract PageVM<PassItem> getPassItemsByProjectId(String projectId,
			int pageIndex, int pageSize, Boolean desc, String orderProperName);

	public abstract PassItem getPassItemById(String passItemId);
}
