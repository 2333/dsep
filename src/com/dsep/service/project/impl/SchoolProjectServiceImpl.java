package com.dsep.service.project.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.dao.dsepmeta.project.ApplyItemDao;
import com.dsep.dao.dsepmeta.project.JudgeResultDao;
import com.dsep.dao.dsepmeta.project.PassItemDao;
import com.dsep.dao.dsepmeta.project.SchoolProjectDao;
import com.dsep.entity.project.ApplyItem;
import com.dsep.entity.project.JudgeResult;
import com.dsep.entity.project.PassItem;
import com.dsep.entity.project.SchoolProject;
import com.dsep.entity.project.TeamMember;
import com.dsep.service.project.SchoolProjectService;
import com.dsep.vm.PageVM;
import com.dsep.vm.project.SchoolProjectVM;

public class SchoolProjectServiceImpl implements SchoolProjectService {
	
	private SchoolProjectDao schoolProjectDao;
	
	private ApplyItemDao applyItemDao;
	
	private PassItemDao passItemDao;
	
	private JudgeResultDao judgeResultDao;

	public JudgeResultDao getJudgeResultDao() {
		return judgeResultDao;
	}

	public void setJudgeResultDao(JudgeResultDao judgeResultDao) {
		this.judgeResultDao = judgeResultDao;
	}

	@Override
	public String create(SchoolProject proj) {
		return schoolProjectDao.save(proj);
	}

	@Override
	public void update(SchoolProject proj) {
		schoolProjectDao.saveOrUpdate(proj);
	}

	@Override
	public void delete(String id) {
		schoolProjectDao.deleteByKey(id);
	}

	@Override
	public PageVM<SchoolProject> page(int pageIndex, int pageSize,
			Boolean desc, String orderProperName, String unitId)
			throws InstantiationException, IllegalAccessException {
		// 获得所有projects
		List<SchoolProject> projects = schoolProjectDao.pageFind(unitId,
				pageIndex, pageSize, desc, orderProperName);
		// 总数
		int totalCount = schoolProjectDao.Count();

		PageVM<SchoolProject> result = new PageVM<SchoolProject>(pageIndex,
				totalCount, pageSize, projects);

		return result;
	}

	@Override
	public List<SchoolProject> getAll() {
		return schoolProjectDao.getAll();
	}

	// getter and setter 
	public SchoolProjectDao getSchoolProjectDao() {
		return schoolProjectDao;
	}

	public void setSchoolProjectDao(SchoolProjectDao schoolProjectDao) {
		this.schoolProjectDao = schoolProjectDao;
	}

	
	public ApplyItemDao getApplyItemDao() {
		return applyItemDao;
	}

	public void setApplyItemDao(ApplyItemDao applyItemDao) {
		this.applyItemDao = applyItemDao;
	}

	public PassItemDao getPassItemDao() {
		return passItemDao;
	}

	public void setPassItemDao(PassItemDao passItemDao) {
		this.passItemDao = passItemDao;
	}

	@Override
	public void deleteAttachment(String projectId) {
		// TODO Auto-generated method stub
		schoolProjectDao.deleteAttachment(projectId);
	}

	@Override
	public String getAttachmentId(String projectId) {
		// TODO Auto-generated method stub
		return schoolProjectDao.getAttachmentId(projectId);
	}

	@Override
	public List<String> getUniqueProjectTypes(String unitId) {
		return schoolProjectDao.getUniqueProjectTypes(unitId);
	}

	@Override
	public SchoolProject getProjectById(String projectId) {
		// TODO Auto-generated method stub
		return schoolProjectDao.get(projectId);
	}

	@Override
	public PageVM<SchoolProjectVM> pageVM(String unitId, int pageIndex,
			int pageSize, Boolean desc, String orderProperName) {
		// TODO Auto-generated method stub
		// 获得所有projects
		List<SchoolProject> projects = schoolProjectDao.pageFind(unitId,
				pageIndex, pageSize, desc, orderProperName);
		List<SchoolProjectVM> projectsVM = new ArrayList<SchoolProjectVM>();
		for (SchoolProject project : projects) {
			SchoolProjectVM projectVM = new SchoolProjectVM(project);
			projectsVM.add(projectVM);
		}
		// 总数
		int totalCount = schoolProjectDao.Count();

		PageVM<SchoolProjectVM> result = new PageVM<SchoolProjectVM>(pageIndex,
				totalCount, pageSize, projectsVM);

		return result;
	}

	@Override
	public PageVM<SchoolProjectVM> pageVM(String unitId, String projectType,
			int pageIndex, int pageSize, Boolean desc, String orderProperName) {
		// TODO Auto-generated method stub
		// 获得所有projects
		List<SchoolProject> projects = schoolProjectDao.pageFind(unitId,
				projectType, pageIndex, pageSize, desc, orderProperName);
		List<SchoolProjectVM> projectsVM = new ArrayList<SchoolProjectVM>();
		for (SchoolProject project : projects) {
			SchoolProjectVM projectVM = new SchoolProjectVM(project);
			projectsVM.add(projectVM);
		}
		// 总数
		int totalCount = schoolProjectDao.count(unitId, projectType);

		PageVM<SchoolProjectVM> result = new PageVM<SchoolProjectVM>(pageIndex,
				totalCount, pageSize, projectsVM);

		return result;
	}

	@Override
	public Boolean closeProject(String projectId) {
		// TODO Auto-generated method stub
		return schoolProjectDao.closeProject(projectId);
	}

	@Override
	public Boolean restartProject(String projectId) {
		// TODO Auto-generated method stub
		return schoolProjectDao.restartProject(projectId);
	}

	@Override
	public PageVM<SchoolProjectVM> pageVM(String unitId, int pageIndex,
			int pageSize, boolean desc, String orderProperName,
			String projectName, String projectType, int currentState) {
		// TODO Auto-generated method stub
		List<SchoolProject> projects = schoolProjectDao.pageFind(unitId, pageIndex, pageSize, desc, orderProperName,projectName,projectType,currentState);
		List<SchoolProjectVM> projectsVM = new ArrayList<SchoolProjectVM>();
		for (SchoolProject project : projects) {
			SchoolProjectVM projectVM = new SchoolProjectVM(project);
			projectsVM.add(projectVM);
		}
		// 总数
		int totalCount = schoolProjectDao.count(unitId, projectType);

		PageVM<SchoolProjectVM> result = new PageVM<SchoolProjectVM>(pageIndex,
				totalCount, pageSize, projectsVM);

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public Boolean publishResult(String projectId) {
		// TODO Auto-generated method stub
		// 把所有通过的项目插入立项表，不通过的修改其状态为不通过
		List<PassItem> passItems = new ArrayList<PassItem>();
		List<ApplyItem> applyItems = applyItemDao.getApplyItemsByProjectId(projectId);
		try
		{
		for( ApplyItem applyItem : applyItems )
		{
			if( applyItem.getCurrentState()==3 ){
			String itemId = applyItem.getId();
			JudgeResult result = judgeResultDao.getResultByItemId(itemId);
			if( result.getIsAccept().equals("是"))
			{
				PassItem passItem = new PassItem(applyItem);
				Set<TeamMember> members = applyItem.getTeamMembers();
				for (TeamMember m : members) {
					passItem.getTeamMembers().add(m);
					m.getPassItems().add(passItem);
				}
				passItemDao.save(passItem);
				applyItem.setCurrentState(5);
			}
			else
			{
				applyItem.setCurrentState(4);
			}
			applyItemDao.saveOrUpdate(applyItem);
			}
		}
		if(!applyItems.isEmpty()){
			SchoolProject project = this.getProjectById(projectId);
			project.setCurrentState(4);
			this.update(project);
		}
		}catch(Exception ex)
		{
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public int setProjectState(String projectId, int i) {
		// TODO Auto-generated method stub
		return schoolProjectDao.setProjectState(projectId,i);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public Boolean publishFinalResult(String projectId) {
		// TODO Auto-generated method stub
		List<PassItem> items = passItemDao.getPassItemByProjectId(projectId);
		try{
			for(PassItem item : items)
			{
				item.setItemState(0);
				passItemDao.saveOrUpdate(item);
			}
			SchoolProject project = schoolProjectDao.get(projectId);
			project.setCurrentState(0);
			project.setCommitState("否");
			this.update(project);
		}catch(Exception ex)
		{
			return false;
		}	
		return true;
	}
}