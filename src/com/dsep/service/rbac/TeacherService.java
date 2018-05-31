package com.dsep.service.rbac;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.entity.Teacher;

public interface TeacherService {
	
	/**
	 * 从专家库导入教师
	 */
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void import2TeacherFromOtherExpertDB();
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void import2TeacherFromOtherExpertDBByPage(int pageSize);
	/**
	 * 通过Id 获取教师信息
	 * @return
	 */
	public abstract Teacher geTeacherById(String id);
	
	/**
	 * 通过一级学科，二级学科对教师进行筛选
	 * @param teachLoginId
	 * @param teachName
	 * @param unitId
	 * @param discId1
	 * @param discId2
	 * @param pageIndex
	 * @param pageSize
	 * @param desc
	 * @param orderProperName
	 * @return
	 */
	public abstract List<Teacher> getTeachersByDiscId12(String teachLoginId,
			String teachName,String unitId,String discId1,
			String discId2,int pageIndex,int pageSize,
			Boolean desc,String orderProperName);
	
	public abstract int getCountByDisc12(String teachLoginId,
			String teachName,String unitId,String discId1,
			String discId2);
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract void setTeacherRole();
	/**
	 * 通过教师loginId获取教师实体
	 */
	public abstract Teacher getTeacherByLoginId(String loginId);
	
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract String updateTeacherBriefId(String teacherId,String briefId);
}
