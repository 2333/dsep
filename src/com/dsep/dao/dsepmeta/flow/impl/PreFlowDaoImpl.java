package com.dsep.dao.dsepmeta.flow.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.metamodel.domain.Superclass;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.flow.PreFlowDao;
import com.dsep.entity.dsepmeta.PreEval;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class PreFlowDaoImpl extends DsepMetaDaoImpl<PreEval, String> implements PreFlowDao{

	private String evalTableName(){
		return super.getTableName("M", "PRE");
	}
	@Override
	public List<PreEval> getPreDataByPage(String unitId, String disciplineId,Boolean isReport,Boolean isEval,Boolean isUnitReport, 
			String orderPropName, boolean asc, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		StringBuilder hql= new  StringBuilder(" from PreEval p ");
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		if(StringUtils.isNotBlank(unitId)){
			params.add(unitId);
			conditionColumns.add("p.unitId");
		}
		if(StringUtils.isNotBlank(disciplineId))
		{
			params.add(disciplineId);
			conditionColumns.add("p.discId");
		}
		if(isEval!=null){
			params.add(isEval);
			conditionColumns.add("p.isEval");
		}
		if(isReport!=null){
			params.add(isReport);
			conditionColumns.add("p.isReport");
		}
		if(isUnitReport!=null){
			params.add(isUnitReport);
			conditionColumns.add("p.isUnitReport");
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		return super.hqlPage(hql.toString(), pageIndex, pageSize,!asc, orderPropName, params.toArray());
	}

	@Override
	public int preEvalCount(String unitId, String discId) {
		// TODO Auto-generated method stub
		StringBuilder hql= new StringBuilder(" select count(*) from PreEval p ");
		List<String> conditionColumns= new ArrayList<String>(0);
		List<Object> params= new ArrayList<Object>(0);
		if(StringUtils.isNotBlank(unitId))
		{
			conditionColumns.add("p.unitId");
			params.add(unitId);
		}
		if(StringUtils.isNotBlank(discId))
		{
			conditionColumns.add("p.discId");
			params.add(discId);
		}
		hql.append(super.hqlAndCondtion(conditionColumns));
		return super.hqlCount(hql.toString(), params.toArray());
	}

	@Override
	public boolean updatePreEval(PreEval preEval) throws NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		Map<String, Object> params= new HashMap<String, Object>(0);
		if(preEval.getIsEval()){
			params.put("isEval", 1);
		}else{
			params.put("isEval", 0);
		}
		if(preEval.getIsReport()){
			params.put("isReport", 1);
		}else{
			params.put("isReport", 0);
		}
		params.put("modifyTime", preEval.getModifyTime());
		params.put("modifyUserId", preEval.getModifyUserId());
		return (super.updateColumn(preEval.getId(), params)>0);
	}

	@Override
	public void savePreEval(PreEval preEval) {
		// TODO Auto-generated method stub
		super.save(preEval);
	}

	@Override
	public boolean importDiscsFromBaseDisc(String unitId,String userId) {
		// TODO Auto-generated method stub
		String sql = "insert into "+evalTableName()+" p (UNIT_ID,DISC_ID,INSERT_USER_ID,MODIFY_USER_ID) SELECT UNIT_ID,DISC_ID,'"+userId+"','"+userId+"' FROM DSEP_BASE_UNIT_DISC b where b.UNIT_ID=? ";
		super.sqlBulkUpdate(sql, new Object[]{unitId});
		return true;
	}
	@Override
	public boolean isHaveUnit(String unitId) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from " +evalTableName()+" where UNIT_ID=?";
		return super.sqlCount(sql, new Object[]{unitId})>0?true:false;
		
		/*return ((BigDecimal)super.sqlUniqueResult(sql, new Object[]{unitId})).intValue()>0;
		*/
	}
	@Override
	public boolean updateState(String unitId, String state,String isUnitReport) {
		// TODO Auto-generated method stub
		String sql =" update "+evalTableName()+" SET STATE=?,IS_UNIT_REPORT=?  WHERE UNIT_ID=?";
		return super.sqlBulkUpdate(sql, new Object []{state,isUnitReport,unitId})>0;
	}
	@Override
	public int getUnitPreState(String unitId) {
		// TODO Auto-generated method stub
		String sql = "select  DISTINCT STATE FROM "+evalTableName()+" WHERE UNIT_ID=?";
		Object state = super.sqlUniqueResult(sql, new Object[]{unitId});
		if(state!=null){
			return ((BigDecimal)state).intValue();
		}else{
			return 0;
		}
	}
	@Override
	public String isUnitReport(String unitId) {
		// TODO Auto-generated method stub
		String sql = "select  DISTINCT IS_UNIT_REPORT  FROM "+evalTableName()+" WHERE UNIT_ID=?";
		Object isReport = super.sqlUniqueResult(sql, new Object[]{unitId});
		if(isReport!=null){
			return isReport.toString();
		}else{
			return "0";
		}
	}
	@Override
	public List<PreEval> getPreEvalsByUnitId(String unitId) {
		// TODO Auto-generated method stub
		String sql ="select * from "+evalTableName()
				+" where UNIT_ID=? order by IS_EVAL desc , DISC_ID asc ";
		return super.sqlFind(sql, new Object[]{unitId});
	}
	
	
}
