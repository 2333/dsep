package com.dsep.dao.rbac.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.dao.rbac.TeacherDao;
import com.dsep.entity.Teacher;
import com.dsep.entity.User;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class TeacherDaoImpl extends DaoImpl<Teacher,String> implements TeacherDao{

	@Override
	public String saveTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		return super.save(teacher);
	}

	@Override
	public Teacher getTeacherByLoginId(String loginId) {
		// TODO Auto-generated method stub
		String hql =" from Teacher t where t.loginId = ? ";
		List<Teacher> teachers = super.hqlFind(hql, new Object[]{loginId});
		if(teachers!=null&&teachers.size()>0){
			return teachers.get(0);
		}
		return null;
	}

	@Override
	public List<Teacher> getUserByDisc12(String loginId, String name,
			String unitId, String discId1, String discId2, int pageIndex,
			int pageSize, String orderProperName, Boolean desc) {
		// TODO Auto-generated method stub
		boolean bFirst = true;
		boolean bSecond = true;
		List<Object> values = new ArrayList<Object>(0);
		StringBuilder hql = new StringBuilder(" from Teacher u  ");
		if(StringUtils.isNotBlank(loginId)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" u.loginId = ? ");
			values.add(loginId);
		}
		if(StringUtils.isNotBlank(name)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" u.name = ? ");
			values.add(name);
		}
		if(StringUtils.isNotBlank(unitId)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" u.unitId = ? ");
			values.add(unitId);
		}
		if(StringUtils.isNotBlank(discId1)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			if(bSecond){
				bSecond= false;
				hql.append(" ( ");
			}else{
				hql.append(" or ");
			}
			hql.append(" u.discId = ? ");
			values.add(discId1);
		}
		if(StringUtils.isNotBlank(discId2)){
			
			if(bSecond){
				bSecond= false;
				if(bFirst){
					bFirst=false;
					hql.append(" where ");
				}else{
					hql.append(" and ");
				}
				hql.append(" ( ");
			}else{
				hql.append(" or ");
			}
			hql.append(" u.yjxkm2 = ? )  ");
			values.add(discId2);
		}else{
			hql.append(" )  ");
		}
		List<Teacher> teachers = super.hqlPage(hql.toString(), pageIndex, pageSize, desc,
				orderProperName, values.toArray());
		return teachers;
	}

	@Override
	public int getCount12(String loginId, String name, String unitId,
			String discId1, String discId2) {
		// TODO Auto-generated method stub
		boolean bFirst = true;
		boolean bSecond = true;
		List<Object> values = new ArrayList<Object>(0);
		StringBuilder hql = new StringBuilder(" select count(*) from Teacher u  ");
		if(StringUtils.isNotBlank(loginId)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" u.loginId = ? ");
			values.add(loginId);
		}
		if(StringUtils.isNotBlank(name)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" u.name = ? ");
			values.add(name);
		}
		if(StringUtils.isNotBlank(unitId)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" u.unitId = ? ");
			values.add(unitId);
		}
		if(StringUtils.isNotBlank(discId1)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			if(bSecond){
				bSecond= false;
				hql.append(" ( ");
			}else{
				hql.append(" or ");
			}
			hql.append(" u.discId = ? ");
			values.add(discId1);
		}
		if(StringUtils.isNotBlank(discId2)){
			
			if(bSecond){
				bSecond= false;
				if(bFirst){
					bFirst=false;
					hql.append(" where ");
				}else{
					hql.append(" and ");
				}
				hql.append(" ( ");
			}else{
				hql.append(" or ");
			}
			hql.append(" u.yjxkm2 = ? )  ");
			values.add(discId2);
		}else{
			hql.append(" )  ");
		}
		return super.hqlCount(hql.toString(),values.toArray());
	}

	@Override
	public boolean isExistTeacher(String zjbh) {
		// TODO Auto-generated method stub
		String hql = " from Teacher where zjbh = ?";
		List<Teacher> teachers = super.hqlFind(hql,new Object[]{zjbh});
		if(teachers!=null&&teachers.size()>0){
			return true;
		}
		return false;
	}
	
	
}