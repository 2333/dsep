package com.dsep.dao.dsepmeta.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.metamodel.domain.Superclass;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.base.TeachDiscDao;
import com.dsep.entity.dsepmeta.TeachDisc;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class TeachDiscDaoImpl extends DsepMetaDaoImpl<TeachDisc, String> implements TeachDiscDao{

	@Override
	public List<TeachDisc> getTeachDiscByPage(String teachLoginId,String teachName,
			String unitId, String discId,int pageIndex, 
			int pageSize, String orderProperName, Boolean desc) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder(" from TeachDisc t ");
		List<String> hqlConditions = new ArrayList<String>(0);
		List<Object> values= new ArrayList<Object>(0);
		if(StringUtils.isNotBlank(teachLoginId)){
			hqlConditions.add(" t.teachLoginId ");
			values.add(teachLoginId);
		}
		if(StringUtils.isNotBlank(teachName)){
			hqlConditions.add(" t.teachName ");
			values.add(teachName);
		}
		if(StringUtils.isNotBlank(unitId)){
			hqlConditions.add(" t.unitId ");
			values.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			hqlConditions.add(" t.discId ");
			values.add(discId);
		}
		hql.append(super.hqlAndCondtion(hqlConditions));
		return super.hqlPage(hql.toString(), pageIndex, pageSize, desc, orderProperName, values.toArray());
	}

	@Override
	public List<TeachDisc> getAllTeachDiscs(String unitId, String discId) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder(" from TeachDisc t ");
		List<Object> values= new ArrayList<Object>(0);
		boolean bFirst = true;
		if(StringUtils.isNotBlank(unitId)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" t.unitId = ? ");
			values.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" t.discId = ? ");
			values.add(discId);
		}
		return super.hqlFind(hql.toString(), values.toArray());
	}

	@Override
	public int getTeachDiscCount(String teachLoginId,String teachName,String unitId, String discId) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder(" select count(*) from TeachDisc t ");
		List<String> hqlConditions = new ArrayList<String>(0);
		List<Object> values= new ArrayList<Object>(0);
		/*boolean bFirst = true;*/
		if(StringUtils.isNotBlank(teachLoginId)){
			hqlConditions.add(" t.teachLoginId ");
			values.add(teachLoginId);
		}
		if(StringUtils.isNotBlank(teachName)){
			hqlConditions.add(" t.teachName ");
			values.add(teachName);
		}
		if(StringUtils.isNotBlank(unitId)){
			hqlConditions.add(" t.unitId");
			values.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			hqlConditions.add(" t.discId");
			values.add(discId);
		}
		hql.append(super.hqlAndCondtion(hqlConditions));
		return super.hqlCount(hql.toString(), values.toArray());
	}

	@Override
	public String import2FromUser(String pkValue,String seqNo,String unitId, String discId) {
		// TODO Auto-generated method stub
		String sql =" insert into DSEP_RBAC_TEACH_DISC (UNIT_ID,DISC_ID,SEQ_NO,TEACH_LOGIN_ID,TEACH_NAME," +
				"TEACH_ID) select '"+unitId+"','"+discId+"','"+seqNo+"',LOGIN_ID,NAME,ID from " +
						"DSEP_RBAC_USER WHERE ID = ?";
		if(super.sqlBulkUpdate(sql, new Object[]{pkValue})>0){
			return seqNo;
		}else{
			return null;
		}
	}

	@Override
	public String delTeachDisc(String pkValue) {
		// TODO Auto-generated method stub
		super.deleteByKey(pkValue);
		return pkValue;
	}

	@Override
	public Integer updateAfterSeqNo(String unitId, String discId, int seqNo) {
		// TODO Auto-generated method stub
		String hql = "update TeachDisc t set t.seqNo = t.seqNo - 1 where t.unitId = ? and t.discId = ? and t.seqNo > ?";
		super.hqlBulkUpdate(hql,new Object[]{unitId,discId,seqNo});
		return seqNo;
	}

	@Override
	public List<String> getTeachIds(String unitId, String discId) {
		// TODO Auto-generated method stub
		List<String> params = new ArrayList<String>(0);
		List<Object> values= new ArrayList<Object>(0);
		if(StringUtils.isNotBlank(unitId)){
			params.add("UNIT_ID");
			values.add(unitId);
			
		}
		if(StringUtils.isNotBlank(discId)){
			params.add("DISC_ID");
			values.add(discId);
		}
		String sql = "select TEACH_ID from DSEP_RBAC_TEACH_DISC ";
		sql += sqlAndConditon(params);
		return super.GetShadowResult(sql, values.toArray());
	}

	@Override
	public boolean isTeacherExist(String unitId, String discId, String teachId) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from TeachDisc ";
		List<String> params = new ArrayList<String>(0);
		List<Object> values = new ArrayList<Object>(0);
		if(StringUtils.isNotBlank(unitId)){
			params.add("unitId");
			values.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			params.add("discId");
			values.add(discId);
		}
		if(StringUtils.isNotBlank(teachId)){
			params.add("teachId");
			values.add(teachId);
		}
		hql += super.hqlAndCondtion(params);
		return super.hqlCount(hql, values.toArray())>0?true:false;
	}
	
}
