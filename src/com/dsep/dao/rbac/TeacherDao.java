package com.dsep.dao.rbac;

import java.util.List;

import com.dsep.dao.common.Dao;
import com.dsep.entity.Teacher;
import com.dsep.entity.User;

public interface TeacherDao extends Dao<Teacher,String>{
	
	/**
	 * 保存一个教师用户
	 * @param teacher
	 * @return 返回主键
	 */
	public String saveTeacher(Teacher teacher);
	
	/**
	 * 通过loginId获取teacher
	 * @param loginId
	 * @return
	 */
	public Teacher getTeacherByLoginId(String loginId);
	
	/**
	 * 通过参数条件对教师用户进行筛选
	 * @param teachLoginId
	 * @param teachName
	 * @param unitId
	 * @param discId1
	 * @param discId2
	 * @param pageIndex
	 * @param pageSize
	 * @param orderProperName
	 * @param desc
	 * @return
	 */
	public List<Teacher> getUserByDisc12(String loginId,String name,String unitId, String discId1,
			String discId2,int pageIndex,int pageSize,String orderProperName,Boolean desc);
	
	/**
	 * 通过一级学科、二级学科获取总记录数
	 * @param loginId
	 * @param name
	 * @param unitId
	 * @param discId1
	 * @param discId2
	 * @return
	 */
	public int getCount12(String loginId,String name,String unitId,String discId1,String discId2);
	
	/**
	 * 通过专家编号查看专家是否存在
	 * @param zjbh
	 * @return
	 */
	public boolean isExistTeacher(String zjbh);
	
}
