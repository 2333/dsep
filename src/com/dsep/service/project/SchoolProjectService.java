package com.dsep.service.project;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.project.SchoolProject;
import com.dsep.vm.PageVM;
import com.dsep.vm.project.SchoolProjectVM;

/**
 * 
 */
@Transactional(propagation = Propagation.SUPPORTS)
public interface SchoolProjectService {
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract String create(SchoolProject proj);

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void update(SchoolProject proj);

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void delete(String id);

	public abstract PageVM<SchoolProject> page( int pageIndex,
			int pageSize, Boolean desc, String orderProperName,String unitId)
			throws InstantiationException, IllegalAccessException;

	public abstract List<SchoolProject> getAll();

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void deleteAttachment(String projectId);

	public abstract String getAttachmentId(String projectId);

	public abstract SchoolProject getProjectById(String projectId);
	
	public abstract List<String> getUniqueProjectTypes(String unitId);

	public abstract PageVM<SchoolProjectVM> pageVM(String unitId, int pageIndex, int pageSize,
			Boolean desc, String orderProperName);
	
	// 通过项目类型查找某个学校的项目
	public abstract PageVM<SchoolProjectVM> pageVM(String unitId, String projectType, int pageIndex, int pageSize,
			Boolean desc, String orderProperName);

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract Boolean closeProject(String projectId);

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract Boolean restartProject(String projectId);

	public abstract PageVM<SchoolProjectVM> pageVM(String unitId,
			int pageIndex, int pageSize, boolean desc, String orderProperName,
			String projectName, String projectType, int currentState);
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract Boolean publishResult(String projectId);

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract int setProjectState(String projectId, int i);
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract Boolean publishFinalResult(String projectId);
}