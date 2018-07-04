package com.dsep.dao.rbac.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DaoImpl;
import com.dsep.dao.rbac.UserDao;
import com.dsep.entity.News;
import com.dsep.entity.Teacher;
import com.dsep.entity.User;
import com.dsep.entity.expert.Expert;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

 public class UserDaoImpl extends DaoImpl<User,Integer> implements UserDao {
	
	public boolean validatorUser(String userLoginId,String password) {		
		String hql = "from User u where u.loginId=? and u.password=?";
	    List<User> list=super.hqlFind(hql, new Object[]{userLoginId,password});
		return (list.size()>0);
	}

	@Override
	public User getByLoginId(String loginId) {
		// TODO Auto-generated method stub
		String hql="from User u where u.loginId=?";
		List<User>list =super.hqlFind(hql, new Object[]{loginId});
		if(list.size()>0)
			return list.get(0);
		else {
			return null;
		}
	}

	/**
	 * 根据用户类型返回用户列表，返回的用户限定在pageIndex和pageSize范围内
	 */
	@Override
	public List<User> getPageUsersByUserType(int pageIndex, int pageSize,
			Boolean desc, String orderProperName, String userType) {
		// TODO Auto-generated method stub
		String hql="from User u where u.userType=?";
		List<User> list =super.hqlPage(hql, pageIndex, pageSize, desc, orderProperName, new Object[]{userType});
		return list;
	}

	/**
	 * 
	 */
	@Override
	public List<User> getUsersByUserType(String userType) {
		// TODO Auto-generated method stub
		String hql="from User u where u.userType=?";
		List<User> list = super.hqlFind(hql,new Object[]{userType});
		return list;
	}
	
	/**
	 * 根据学校ID返回本学校学科用户(分页)
	 */
	@Override
	public List<User> getDisciplineUsersByUnitId(int pageIndex, int pageSize,
			Boolean desc, String orderProperName, String unitId) {
		// TODO Auto-generated method stub
		String hql="from User u where u.userType=? and u.unitId = ?";
		List<User> list =super.hqlPage(hql, pageIndex, pageSize, desc, orderProperName, new Object[]{"3", unitId});
		return list;
	}

	/**
	 * 获得本学校的所有学科用户	
	 */
	@Override
	public List<User> getDisciplineUsersByUnitId(String unitId){
		String hql="from User u where u.userType=? and u.unitId = ?";
		List<User> list = super.hqlFind(hql, new Object[]{"3",unitId});
		return list;
	}

	@Override
	public void updateLoginInfo(String loginIp, Date loginTime, Integer id) {
		String sql = "update dsep_rbac_user2 set login_ip = ? where id = ?";//,login_time = ? where id = ?";
		super.sqlBulkUpdate(sql, new Object[]{loginIp,  id});
	}
	
	@Override
	public void updateUserPassword(String password, Integer id) {
		String sql = "update dsep_rbac_user2 set password = ? where id = ?";
		super.sqlBulkUpdate(sql, new Object[]{password , id});
	}

	@Override
	public int deleteUserBySource(int source) {
		// TODO Auto-generated method stub
		String sql = "delete from dsep_rbac_user2 where source = ?";
		Object[] valueParameter = new Object[1];
		valueParameter[0] = source;
		int result = super.sqlBulkUpdate(sql, valueParameter);
		return result;
	}

	@Override
	public List<String> getUserIdsByUnitIdAndDiscId(String unitId, String discId) {
		// TODO Auto-generated method stub
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select id from dsep_rbac_user2 u");
		List<Object> values=new ArrayList<Object>(0);
		boolean bFirst= true;
		if(StringUtils.isNotBlank(unitId)){
			if(bFirst){
				bFirst=false;
				sqlBuffer.append(" where ");
			}else{
				sqlBuffer.append(" and ");
			}
			sqlBuffer.append(" u.unit_id = ? ");
			values.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			if(bFirst){
				bFirst=false;
				sqlBuffer.append(" where ");
			}else{
				sqlBuffer.append(" and ");
			}
			sqlBuffer.append(" disc_id = ? ");
			values.add(discId);
		}
		return super.GetShadowResult(sqlBuffer.toString(), values.toArray());
	}

	@Override
	public List<User> getUserByDisc12(String loginId,String name,String unitId, String discId1,
			String discId2,int pageIndex,int pageSize,String orderProperName,Boolean desc) {
		// TODO Auto-generated method stub
		boolean bFirst = true;
		boolean bSecond = true;
		List<Object> values = new ArrayList<Object>(0);
		StringBuilder hql = new StringBuilder(" from User u  ");
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
			hql.append(" u.discId2 = ? )  ");
			values.add(discId2);
		}else{
			hql.append(" )  ");
		}
		List<User> users = super.hqlPage(hql.toString(), pageIndex, pageSize, desc,
				orderProperName, values.toArray());
		return users;
	}

	@Override
	public int getCount12(String loginId,String name,String unitId, String discId1, String discId2) {
		// TODO Auto-generated method stub
		boolean bFirst = true;
		boolean bSecond = true;
		List<Object> values = new ArrayList<Object>(0);
		StringBuilder hql = new StringBuilder(" select count(*) from User u  ");
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
			hql.append(" u.discId2 = ? )  ");
			values.add(discId2);
		}else{
			hql.append(" )  ");
		}
		return super.hqlCount(hql.toString(),values.toArray());
	}

	@Override
	public List<User> getSearchUsers(String loginId, String unitId, String discId, String name,String userType,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.id is not null";
		List<Object> params = new ArrayList<Object>(0);
		
		if(StringUtils.isNotBlank(loginId)){
			hql += " and u.loginId like ?";
			loginId = "%" + loginId + "%";
			params.add(loginId);
		}
		
		if(StringUtils.isNotBlank(unitId)){
			hql += " and u.unitId=?";
			params.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			hql += " and u.discId=?";
			params.add(discId);
		}
		if(!userType.equals("-")){
			hql += " and u.userType=?";
			params.add(userType);
		}
		if(StringUtils.isNotBlank(name)){
			hql += " and u.name like ?";
			name = "%" + name + "%";
			params.add(name);
		}
		
		
		List<User> list = super.hqlPage(hql, page , pageSize, params.toArray());
		
		return list;
	}

	@Override
	public int getSearchCount(String loginId, String unitId, String discId,
			String name, String userType) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.id is not null";
		List<Object> params = new ArrayList<Object>(0);
		
		if(StringUtils.isNotBlank(loginId)){
			hql += " and u.loginId like ?";
			loginId = "%" + loginId + "%";
			params.add(loginId);
		}
		
		if(StringUtils.isNotBlank(unitId)){
			hql += " and u.unitId=?";
			params.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			hql += " and u.discId=?";
			params.add(discId);
		}
		if(!userType.equals("-")){
			hql += " and u.userType=?";
			params.add(userType);
		}
		if(StringUtils.isNotBlank(name)){
			hql += " and u.name like ?";
			name = "%" + name + "%";
			params.add(name);
		}
		return super.hqlFind(hql, params.toArray()).size();
	}
}
