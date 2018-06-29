package com.dsep.service.rbac.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.dao.dsepmeta.expert.selection.OuterExpertDao;
import com.dsep.dao.rbac.RoleDao;
import com.dsep.dao.rbac.TeacherDao;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.Role;
import com.dsep.entity.Teacher;
import com.dsep.service.rbac.RoleService;
import com.dsep.service.rbac.TeacherService;
import com.dsep.service.rbac.UserService;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class TeacherServiceImpl implements TeacherService{

	private TeacherDao teacherDao;
	private UserService userService;
	private RoleService roleService;
	private OuterExpertDao outerExpertDao;
	@Override
	public void import2TeacherFromOtherExpertDB() {
		// TODO Auto-generated method stub
		List<String> dzxxList = new ArrayList<String>(0);
		try {
			List<OuterExpert> experts=outerExpertDao.getAll();
			for(OuterExpert expert: experts){
				//String xbmString= expert.getXBM();
				//String zjfl=expert.getZJFL();
				if(expert.getDZXX()!=null&&
					!dzxxList.contains(expert.getDZXX())&&
					expert.getYJXKM()!=null
					//!"0504".equals(expert.getYJXKM())
					){
					Teacher teacher = new Teacher(expert);
					if(teacherDao.getTeacherByLoginId(expert.getDZXX())==null){
						teacherDao.saveTeacher(teacher);
						dzxxList.add(expert.getDZXX());
					}
				}
			}
			teacherDao.flush();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public TeacherDao getTeacherDao() {
		return teacherDao;
	}
	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public OuterExpertDao getOuterExpertDao() {
		return outerExpertDao;
	}

	public void setOuterExpertDao(OuterExpertDao outerExpertDao) {
		this.outerExpertDao = outerExpertDao;
	}
	@Override
	public Teacher geTeacherById(String id) {
		// TODO Auto-generated method stub
		return teacherDao.get(id);
	}
	@Override
	public List<Teacher> getTeachersByDiscId12(String teachLoginId,
			String teachName, String unitId, String discId1, String discId2,
			int pageIndex, int pageSize, Boolean desc, String orderProperName) {
		// TODO Auto-generated method stub
		return teacherDao.getUserByDisc12(teachLoginId, teachName, unitId, discId1, discId2, pageIndex, pageSize, orderProperName, desc);
	}
	@Override
	public int getCountByDisc12(String teachLoginId, String teachName,
			String unitId, String discId1, String discId2) {
		// TODO Auto-generated method stub
		return teacherDao.getCount12(teachLoginId, teachName, unitId, discId1, discId2);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void import2TeacherFromOtherExpertDBByPage(int pageSize) {
		// TODO Auto-generated method stub
		int totalCount = outerExpertDao.getExpertInfoCount();
		int pageCount = totalCount/pageSize;
		for(int i=1;i<=pageCount;i++){
			try {
				List<OuterExpert> experts=outerExpertDao.getPageFromOtherDBs(i, pageSize, "zjbh", true);
				if(experts!=null&&experts.size()>0){
					for(OuterExpert expert: experts){
						String xbmString= expert.getXBM();
						String zjfl=expert.getZJFL();
						String dzxx = expert.getDZXX();
						if(teacherDao.getTeacherByLoginId(expert.getDZXX())==null){
							Teacher teacher = new Teacher(expert);
							teacherDao.saveTeacher(teacher);
						}
					}
				}
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Override
	public void setTeacherRole() {
		// TODO Auto-generated method stub
		List<Teacher> teachers = teacherDao.getAll();
		for(Teacher t: teachers){
			Set<Role> roles = new HashSet<Role>(0);
			roles.add(roleService.getRole("t0001"));
			t.setRoles(roles);
			teacherDao.saveOrUpdate(t);
		}
		teacherDao.flush();
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	@Override
	public Teacher getTeacherByLoginId(String loginId) {
		// TODO Auto-generated method stub
		return teacherDao.getTeacherByLoginId(loginId);
	}
	@Override
	public String updateTeacherBriefId(String teacherId, String briefId) {
		// TODO Auto-generated method stub
		Teacher teacher = teacherDao.get(teacherId);
		if(teacher!=null){
			teacher.setBriefId(briefId);
			teacher.setCreateBriefTime(new Date());
			teacherDao.saveOrUpdate(teacher);
			return briefId;
		}else{
			return null;
		}
		
	}
}
