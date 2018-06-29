package com.dsep.service.project.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.dao.dsepmeta.project.ApplyItemDao;
import com.dsep.dao.dsepmeta.project.TeamMemberDao;
import com.dsep.entity.project.ApplyItem;
import com.dsep.entity.project.TeamMember;
import com.dsep.service.project.ApplyItemService;
import com.dsep.util.project.ApplyItemStatus;
import com.dsep.vm.PageVM;
import com.dsep.vm.project.ApplyItemVM;

public class ApplyItemServiceImpl implements ApplyItemService {
	private ApplyItemDao applyItemDao;
	private TeamMemberDao teamMemberDao;

	@Override
	public String create(ApplyItem item, Set<TeamMember> members) {
		// 保存团队人员
		for (TeamMember m : members) {
			m.getApplyItems().add(item);
			item.getTeamMembers().add(m);
			//m.setId(GUID.get());
		}
		if (item.getId() != null) {
			applyItemDao.saveOrUpdate(item);
			return item.getId();
		} else {

			// 对teamMember的级联：all
			return applyItemDao.save(item);
		}
	}

	@Override
	public void update(ApplyItem item) {
		applyItemDao.saveOrUpdate(item);
	}

	@Override
	public void delete(String id) {
		applyItemDao.deleteByKey(id);
	}

	@Override
	public ApplyItem getApplyItemById(String applyItemId) {
		return applyItemDao.get(applyItemId);
	}

	@Override
	public PageVM<ApplyItem> page(String projectId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		// 获得所有projects
		List<ApplyItem> projects = applyItemDao.page(pageIndex, pageSize, desc,
				orderProperName);
		// 总数
		int totalCount = applyItemDao.Count();

		PageVM<ApplyItem> result = new PageVM<ApplyItem>(pageIndex, totalCount,
				pageSize, projects);

		return result;
	}

	@Override
	public PageVM<ApplyItemVM> getApplyItemsByProjectId(String projectId,
			ApplyItemStatus status, int pageIndex, int pageSize, Boolean desc,
			String orderProperName) {
		List<ApplyItem> items = new ArrayList<ApplyItem>();
		if (status.equals(ApplyItemStatus.COMMIT)) {
			items = applyItemDao.retrive2Status(projectId,
					ApplyItemStatus.NOT_REVIEW, ApplyItemStatus.REVIEW, null,
					null, null, pageIndex, pageSize, desc, orderProperName);
		} else {
			items = applyItemDao.retrive(projectId, status, null, null, null,
					pageIndex, pageSize, desc, orderProperName);
		}
		int totalCount = applyItemDao
				.count(projectId, status, null, null, null);

		List<ApplyItemVM> itemVMs = new ArrayList<ApplyItemVM>();
		for (ApplyItem item : items) {
			itemVMs.add(new ApplyItemVM(item));
		}
		
		PageVM<ApplyItemVM> result = new PageVM<ApplyItemVM>(pageIndex, totalCount,
				pageSize, itemVMs);

		return result;
	}

	@Override
	public PageVM<ApplyItemVM> retrive(String projectId,
			ApplyItemStatus status, String unitId, String discId,
			String userId, int pageIndex, int pageSize, Boolean desc,
			String orderProperName) {
		List<ApplyItem> items = applyItemDao.retrive(projectId, status, unitId,
				discId, userId, pageIndex, pageSize, desc, orderProperName);
		int totalCount = applyItemDao.count(projectId, status, unitId, discId,
				userId);

		List<ApplyItemVM> itemVMs = new ArrayList<ApplyItemVM>();
		for (ApplyItem item : items) {
			itemVMs.add(new ApplyItemVM(item));
		}
		PageVM<ApplyItemVM> result = new PageVM<ApplyItemVM>(pageIndex,
				totalCount, pageSize, itemVMs);

		return result;
	}

	@Override
	public int commitApplyItemToSchool(String id) {
		return applyItemDao.updateApplyItemStatus(id,
				ApplyItemStatus.NOT_REVIEW);
	}

	// getter and setter 
	public ApplyItemDao getApplyItemDao() {
		return applyItemDao;
	}

	public void setApplyItemDao(ApplyItemDao applyItemDao) {
		this.applyItemDao = applyItemDao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void deleteAttachment(String projectId) {
		// TODO Auto-generated method stub
		applyItemDao.deleteAttachment(projectId);
	}

	@Override
	public void deleteById(String itemId) {
		// TODO Auto-generated method stub
		applyItemDao.deleteById(itemId);
	}

	public TeamMemberDao getTeamMemberDao() {
		return teamMemberDao;
	}

	public void setTeamMemberDao(TeamMemberDao teamMemberDao) {
		this.teamMemberDao = teamMemberDao;
	}

}