package com.dsep.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.common.exception.TeachManageException;
import com.dsep.dao.dsepmeta.base.TeachDiscDao;
import com.dsep.entity.Teacher;
import com.dsep.entity.User;
import com.dsep.entity.dsepmeta.TeachDisc;
import com.dsep.service.base.TeachDiscService;
import com.dsep.service.rbac.TeacherService;
import com.dsep.service.rbac.UserService;
import com.dsep.vm.PageVM;
import com.dsep.vm.teacher.SelectedTeachVM;
import com.dsep.vm.teacher.ViewTeachVM;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class TeachDiscServiceImpl implements TeachDiscService{

	private UserService userService;
	private TeacherService teacherService;
	private TeachDiscDao teachDiscDao;
	@Override
	public PageVM<ViewTeachVM> getViewTeachDiscPageVM(String teachLoginId,String teachName,String unitId,
			String discId1,String discId2, int pageIndex, int pageSize, String orderProperName,
			Boolean desc) {
		// TODO Auto-generated method stub
		//List<User> users = userService.getTeachDiscByDisc12(teachLoginId,teachName,unitId, discId1, discId2, pageIndex, pageSize, desc, orderProperName);
		List<Teacher> teachers = teacherService.getTeachersByDiscId12(teachLoginId, teachName, unitId, discId1, discId2, pageIndex, pageSize, desc, orderProperName);
		String discId = null;
		if(StringUtils.isNotBlank(discId1)){
			discId = discId1;
		}else if(StringUtils.isNotBlank(discId2)){ 
			discId = discId2;
		}
		List<ViewTeachVM> viewTeachVMs = new ArrayList<ViewTeachVM>(0);
		List<TeachDisc> teachDiscs = teachDiscDao.getAllTeachDiscs(unitId, discId);
		for(Teacher teacher: teachers){
			 
			boolean isSelected = false;
			for(TeachDisc teachDisc : teachDiscs){
				if(teacher.getId().equals(teachDisc.getTeachId())){
					isSelected = true;
					break;
				}
			}
			ViewTeachVM viewTeachVM = new ViewTeachVM(teacher,isSelected);
			viewTeachVMs.add(viewTeachVM);
		}		
		int totalCount = teacherService.getCountByDisc12(teachLoginId,teachName,unitId, discId1, discId2);
		PageVM<ViewTeachVM> viewTeachPageVM = new PageVM<ViewTeachVM>(pageIndex, totalCount, pageSize, viewTeachVMs);
		return viewTeachPageVM;
	}
	@Override
	public PageVM<SelectedTeachVM> getTeachDiscPageVM(String teachLoginId,String teachName,
			String unitId, String discId,
			int pageIndex, int pageSize, String orderProperName, Boolean desc) {
		// TODO Auto-generated method stub
		List<TeachDisc> teachDiscs = teachDiscDao.getTeachDiscByPage(teachLoginId,teachName,unitId, discId, pageIndex, pageSize, orderProperName, desc);
		int totalCount = teachDiscDao.getTeachDiscCount(teachLoginId,teachName,unitId, discId);
		List<SelectedTeachVM> selectedTeachVMs = new ArrayList<SelectedTeachVM>(0);
		for(TeachDisc teachDisc:teachDiscs){
			SelectedTeachVM selectedTeachVM = new SelectedTeachVM(teachDisc);
			selectedTeachVMs.add(selectedTeachVM);
		}
		PageVM<SelectedTeachVM> selectedTeachPageVM = new PageVM<SelectedTeachVM>(pageIndex, totalCount, pageSize, selectedTeachVMs);
		return selectedTeachPageVM;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public TeachDiscDao getTeachDiscDao() {
		return teachDiscDao;
	}
	public void setTeachDiscDao(TeachDiscDao teachDiscDao) {
		this.teachDiscDao = teachDiscDao;
	}
	@Override
	public String import2FromUser(String unitId, String discId, String pkValue) {
		// TODO Auto-generated method stub
		if(teachDiscDao.isTeacherExist(unitId, discId, pkValue)){
			throw new TeachManageException("选择了重复的教师，请重新确认！");
		}else{
			int seqNo = teachDiscDao.getTeachDiscCount(null, null, unitId, discId)+1;
			return teachDiscDao.import2FromUser(pkValue, String.valueOf(seqNo), unitId, discId);
		}
		
	}
	@Override
	public String delTeachDisc(String unitId, String discId, String pkValue,
			String seqNo) {
		// TODO Auto-generated method stub
		if(teachDiscDao.updateAfterSeqNo(unitId, discId, Integer.valueOf(seqNo))!=null){
			if(teachDiscDao.delTeachDisc(pkValue)!=null){
				return pkValue;
			}
		}
		return null;
	}
	@Override
	public List<String> getTeachIds(String unitId, String discId) {
		// TODO Auto-generated method stub
		return teachDiscDao.getTeachIds(unitId, discId);
	}
	public TeacherService getTeacherService() {
		return teacherService;
	}
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	@Override
	public boolean isTeacherExist(String unitId,String discId,String teacherId) {
		// TODO Auto-generated method stub
		return teachDiscDao.isTeacherExist(unitId, discId, teacherId);
	}
	
}
