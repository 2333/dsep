package com.dsep.service.project;

import java.util.Set;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.project.ApplyItem;
import com.dsep.entity.project.TeamMember;
import com.dsep.util.project.ApplyItemStatus;
import com.dsep.vm.PageVM;
import com.dsep.vm.project.ApplyItemVM;

/**
 * 
 */
@Transactional(propagation = Propagation.SUPPORTS)
public interface ApplyItemService {
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public abstract String create(ApplyItem item, Set<TeamMember> members);

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public abstract void update(ApplyItem item);

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public abstract void delete(String id);
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public abstract int commitApplyItemToSchool(String id);

	public abstract PageVM<ApplyItem> page(String projectId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName);

	public abstract ApplyItem getApplyItemById(String applyItemId);

	public abstract PageVM<ApplyItemVM> getApplyItemsByProjectId(
			String projectId, ApplyItemStatus status, int pageIndex,
			int pageSize, Boolean desc, String orderProperName);

	/**
	 * 多条件翻页查询
	 * 不需要某些限定条件的话传null即可
	 */
	public abstract PageVM<ApplyItemVM> retrive(String projectId,
			ApplyItemStatus status, String unitId, String discId,
			String userId, int pageIndex, int pageSize, Boolean desc,
			String orderProperName);

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public abstract void deleteAttachment(String projectId);
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public abstract void deleteById(String itemId);
}