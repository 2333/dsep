package com.dsep.service.project;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.project.TeamMember;
import com.dsep.vm.PageVM;

/**
 * 
 */
@Transactional(propagation = Propagation.SUPPORTS)
public interface TeamMemberService {
	public abstract PageVM<TeamMember> page(String applyItemId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName);

}