package com.dsep.dao.dsepmeta.flow.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.flow.EvalFlowDao;
import com.dsep.entity.dsepmeta.Eval;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class EvalFlowDaoImpl extends DsepMetaDaoImpl<Eval, String> implements EvalFlowDao{
	
	private String evalTableName(){
		return super.getTableName("M", "EVAL");
	}
	
	@Override
	public int evalDataCount(String unitId, String disciplineId, String status,Boolean isEval) {
		String sql = "select count(*) from " + evalTableName();
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		
		if(!StringUtils.isBlank(unitId)) {
			params.add(unitId); 				//参数
			conditionColumns.add("UNIT_ID");// 查询条件
		}
		if(!StringUtils.isBlank(disciplineId)){
			 params.add(disciplineId);					//参数
			 conditionColumns.add("DISC_ID");//查询条件
		}
		if(StringUtils.isNotBlank(status)&&!"-1".equals(status)){
			 params.add(status);					//参数
			 conditionColumns.add("STATE");//查询条件
		}
		if(isEval!=null){
			conditionColumns.add("IS_EVAL");
			if(isEval){
				params.add(1);
			}else{
				params.add(0);
			}
			
		}
		sql += super.sqlAndConditon(conditionColumns);
		return super.sqlCount(sql, params.toArray());
	}
	@Override
	public boolean haveStatus(String unitId, String disciplineId, String status) {
		
		return !(this.evalDataCount(unitId, disciplineId, status,true) == 0) ;
	}
	
	@Override
	public Integer getStatus(String unitId, String disciplineId) {
		String sql = "select state from " + evalTableName() +" where  UNIT_ID = ? and DISC_ID = ? ";
		Object state = super.sqlUniqueResult(sql, new Object[]{unitId,disciplineId});
		if(state!=null){
			return ((BigDecimal)state).intValue();
		}else{
			return -1;
		}
		/*Integer result = ((BigDecimal)state).intValue();*/
		/*Integer result =((BigDecimal)super.sqlUniqueResult(sql, new Object[]{unitId,disciplineId})).intValue() ;*/		
		/*return result;*/
	}
	
	@Override
	public void updateStatus(String unitId, String disciplineId,
			Integer newtatus) {
		String sql = String.format("update %s set STATE = ? ", this.evalTableName());		
		
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		
		params.add(newtatus);
		conditionColumns.add("IS_EVAL");//更新参评学科的状态
		params.add(1);
		if(!StringUtils.isBlank(unitId)) {
			params.add(unitId); 				//参数
			conditionColumns.add("UNIT_ID");// 查询条件
		}
		if(!StringUtils.isBlank(disciplineId)){
			 params.add(disciplineId);					//参数
			 conditionColumns.add("DISC_ID");//查询条件
		}
		sql+=super.sqlAndConditon(conditionColumns);
		super.sqlBulkUpdate(sql,params.toArray() );		
	}

	@Override
	public Integer getStreamNo(String unitId, String discId) {
		// TODO Auto-generated method stub
		String sql= "";
		List<String> conditionColumn = new ArrayList<String>(0);
		List<Object> params = new ArrayList<Object>(0);
		conditionColumn.add("IS_EVAL");
		params.add(1);
		if(StringUtils.isNotBlank(unitId)){
			conditionColumn.add("UNIT_ID");
			params.add(unitId);
			sql=String.format("select DISTINCT UNIT_STREAM_NO from %s ",evalTableName());
		}
		if(StringUtils.isNotBlank(discId)){
			conditionColumn.add("DISC_ID");
			params.add(discId);
			sql = String.format("select DISTINCT DISC_STREAM_NO from %s ",evalTableName());
		}
		sql += super.sqlAndConditon(conditionColumn);
		Object streamNo = super.sqlUniqueResult(sql,params.toArray());
		if(streamNo!=null){
			return ((BigDecimal)streamNo).intValue();
		}else{
			return 0;
		}
		/*Integer result =((BigDecimal)super.sqlUniqueResult(sql,params.toArray())).intValue() ;		
		return result;*/
		
	}

	@Override
	public boolean updateDiscVersionNo(String unitId, String discId,
			String newVersionNo) {
		// TODO Auto-generated method stub
		String sql= String.format("update %s set DISC_VERSION_NO = ?",this.evalTableName());
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		
		params.add(newVersionNo);
		params.add(1);
		conditionColumns.add("IS_EVAL");
		if(!StringUtils.isBlank(unitId)) {
			params.add(unitId); 				//参数
			conditionColumns.add("UNIT_ID");// 查询条件
		}
		if(!StringUtils.isBlank(discId)){
			 params.add(discId);					//参数
			 conditionColumns.add("DISC_ID");//查询条件
		}
		sql+=super.sqlAndConditon(conditionColumns);
		return (super.sqlBulkUpdate(sql,params.toArray())>0);	
		
	}

	@Override
	public boolean updateUnitVersionNo(String unitId, String newVersionNo) {
		// TODO Auto-generated method stub
		String sql= String.format("update %s set UNIT_VERSION_NO = ?",this.evalTableName());
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		
		params.add(newVersionNo);
		conditionColumns.add("IS_EVAL");
		params.add(1);
		if(!StringUtils.isBlank(unitId)) {
			params.add(unitId); 				//参数
			conditionColumns.add("UNIT_ID");// 查询条件
		}
		sql+=super.sqlAndConditon(conditionColumns);
		return (super.sqlBulkUpdate(sql,params.toArray())>0);	
	}

	@Override
	public boolean updateUnitStreamNo(String unitId,Integer newStreamNo) {
		String sql= String.format("update %s set UNIT_STREAM_NO = ?",this.evalTableName());
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		
		params.add(newStreamNo);
		conditionColumns.add("IS_EVAL");
		params.add(1);
		if(!StringUtils.isBlank(unitId)) {
			params.add(unitId); 				//参数
			conditionColumns.add("UNIT_ID");// 查询条件
		}
		sql+=super.sqlAndConditon(conditionColumns);
		return (super.sqlBulkUpdate(sql,params.toArray())>0);	
		
	}

	@Override
	public boolean updateDiscStreamNo(String unitId, String discId,Integer newStreamNo) {
		// TODO Auto-generated method stub
		String sql= String.format("update %s set DISC_STREAM_NO = ?",this.evalTableName());
		List<Object> params = new LinkedList<Object>();
		List<String> conditionColumns = new LinkedList<String>();
		
		params.add(newStreamNo);
		conditionColumns.add("IS_EVAL");
		params.add(1);
		if(!StringUtils.isBlank(unitId)) {
			params.add(unitId); 				//参数
			conditionColumns.add("UNIT_ID");// 查询条件
		}
		if(!StringUtils.isBlank(discId)){
			 params.add(discId);					//参数
			 conditionColumns.add("DISC_ID");//查询条件
		}
		sql+=super.sqlAndConditon(conditionColumns);
		return (super.sqlBulkUpdate(sql,params.toArray())>0);
	
	}

	@Override
	public String getVersionNo(String unitId, String discId) {
		// TODO Auto-generated method stub
		String sql = "";
		List<String> conditionColumns = new ArrayList<String>(0);
		List<Object> params= new ArrayList<Object>(0);
		conditionColumns.add("IS_EVAL");
		params.add(1);
		if(StringUtils.isNotBlank(unitId)){
			conditionColumns.add("UNIT_ID");
			params.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			conditionColumns.add("DISC_ID");
			params.add(discId);
			sql = String.format("select DISTINCT DISC_VERSION_NO from %s ", evalTableName());
		}else{
			sql = String.format("select DISTINCT UNIT_VERSION_NO from %s ", evalTableName());
		}
		sql += super.sqlAndConditon(conditionColumns);
		Object versionNo = super.sqlUniqueResult(sql,params.toArray());
		if(versionNo!=null){
			return (String)versionNo;
		}else{
			return "";
		}
		/*return (String)super.sqlUniqueResult(sql,params.toArray());	*/	
		
	}

	@Override
	public List<Eval> getEvalsData(String unitId, String discId,
			String status,Boolean isEval,Boolean isReport, String orderPropName, boolean asc, int pageIndex,
			int pageSize) {
		// TODO Auto-generated method stub
		String hql = " from Eval";
		List<String> conditionColumns = new ArrayList<String>(0);
		List<Object> params = new ArrayList<Object>(0);
		if(StringUtils.isNotBlank(unitId)){
			conditionColumns.add("unitId");
			params.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			conditionColumns.add("discId");
			params.add(discId);
		}
		if(StringUtils.isNotBlank(status)&&!"-1".equals(status)){
			conditionColumns.add("state");
			params.add(Integer.valueOf(status));
		}
		if(isEval!=null){
			conditionColumns.add("isEval");
			params.add(isEval);
		}
		if(isReport!=null){
			conditionColumns.add("isReport");
			params.add(isReport);
		}
		hql += super.hqlAndCondtion(conditionColumns);
		return super.hqlPage(hql, pageIndex, pageSize, !asc, orderPropName, params.toArray());
	}

	@Override
	public List<String> getEvalUnitByDiscId(String disciplineId) {
		// TODO Auto-generated method stub
		String sql = String.format("select UNIT_ID from %s where IS_EVAL = '1' "
			+ "and DISC_ID = ? order by UNIT_ID ASC",this.evalTableName());
		Object[] param = new Object[1];
		param[0] = disciplineId;
		List<String> unitList = super.GetShadowResult(sql, param);
		return unitList;
	}

	@Override
	public List<String> getEvalDiscByUnitId(String unitId) {
		// TODO Auto-generated method stub
		String sql = String.format("select DISC_ID from %s where IS_EVAL = 1 "
			+ "and UNIT_ID = ? order by DISC_ID", this.evalTableName());
		Object[] param = new Object[1];
		param[0] = unitId;
		List<String> discList = super.GetShadowResult(sql,param);
		return discList;
	}

	@Override
	public boolean importDiscsFromPreDisc(String unitId, String userId) {
		// TODO Auto-generated method stub
		String sql = "insert into "+getTableName("M", "EVAL")+" e (UNIT_ID,DISC_ID,INSERT_USER_ID,MODIFY_USER_ID," +
				"IS_EVAL,IS_REPORT,IS_UNIT_REPORT) SELECT UNIT_ID,DISC_ID,'"+userId+"','"+userId+"',IS_EVAL,IS_REPORT,IS_UNIT_REPORT FROM "+getTableName("M", "PRE")+" b where b.UNIT_ID=? and b.DISC_ID NOT IN (SELECT DISC_ID FROM "+getTableName("M", "EVAL")+" e1 WHERE e1.UNIT_ID=?)";
		super.sqlBulkUpdate(sql, new Object[]{unitId,unitId});
		return true;
	}

	@Override
	public boolean updateEval(Eval eval) throws NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		Map<String, Object> params= new HashMap<String, Object>(0);
		if(eval.getIsEval()){
			params.put("isEval",1);
			params.put("unitStreamNo", eval.getUnitStreamNo());
			params.put("unitVersionNo", eval.getUnitVersionNo());
		}else{
			params.put("isEval",0);
		}
		if(eval.getIsReport()){
			params.put("isReport", 1);
		}else{
			params.put("isReport", 0);
		}
		params.put("modifyTime", eval.getModifyTime());
		params.put("modifyUserId", eval.getModifyUserId());
		return (super.updateColumn(eval.getId(), params)>0);
 		
	}

	@Override
	public boolean haveImportPre(String unitId) {
		// TODO Auto-generated method stub
		String sql= "select count(*) from "+getTableName("M", "EVAL")+" WHERE UNIT_ID=?";
		return super.sqlCount(sql, new Object[]{unitId})>0?true:false;
		/*return ((BigDecimal)super.sqlUniqueResult(sql, new Object[]{unitId})).intValue()>0;*/
	}

	@Override
	public boolean isInEval(String unitId, String discId) {
		// TODO Auto-generated method stub
		String sql =" select count(*) from "+getTableName("M", "EVAL") ;//+" WHERE UNIT_ID=? AND DISC_ID=?";
		List<String> conditions= new ArrayList<String>(0);
		List<String> params = new ArrayList<String>(0);
		conditions.add("IS_EVAL");
		params.add("1");
		if(StringUtils.isNotBlank(unitId)){
			conditions.add("UNIT_ID");
			params.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			conditions.add("DISC_ID");
			params.add(discId);
		}
		sql+=super.sqlAndConditon(conditions);
		return super.sqlCount(sql, params.toArray())>0?true:false;
		/*return (((BigDecimal)super.sqlUniqueResult(sql, params.toArray())).intValue()>0);*/
	}

	@Override
	public List<String> getAllEvalUnitIds() {
		String sql = String.format("select DISTINCT UNIT_ID FROM %s order by UNIT_ID ASC",evalTableName());
		return super.GetShadowResult(sql);	
	}

	@Override
	public List<String[]> getUnitIdDiscIdAndCollectId(String unitId,String discId) {
		// TODO Auto-generated method stub
		List<String> conditionParam= new ArrayList<String>(0);
		List<String> params = new ArrayList<String>(0);
		if(StringUtils.isNotBlank(unitId)){
			conditionParam.add("unit_id");
			params.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			conditionParam.add("disc_id");
			params.add(discId);
		}
		String sql = buildUnitIdDiscIdAndCollectIdSql(conditionParam);
		List<Object[]> rowsObject = super.sqlScalarResults(sql, params.toArray());
		List<String[]> rowsList= new ArrayList<String[]>(0);
		for(Object[] object : rowsObject){
			String[] strings= new String[]{String.valueOf(object[0]),String.valueOf(object[1]),String.valueOf(object[2])};
			rowsList.add(strings);
		}
		return rowsList;
	}
	@Override
	public Boolean hasUnitOrDisc(String unitId, String discId) {
		// TODO Auto-generated method stub
		String sql = String.format("select count(*) from %s ", evalTableName());
		List<String> params = new ArrayList<String>(0);
		List<Object> values = new ArrayList<Object>(0);
		if(StringUtils.isNotBlank(unitId)){
			params.add("UNIT_ID");
			values.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			params.add("DISC_ID");
			values.add(discId);
		}
		sql+= sqlAndConditon(params);
		return super.sqlCount(sql, values.toArray())>0?true:false;
		/*return (((BigDecimal)super.sqlUniqueResult(sql, params.toArray())).intValue()>0);*/
	}
	private String buildUnitIdDiscIdAndCollectIdSql(List<String>conditionParams){
		StringBuilder sql= new StringBuilder("select e.unit_id,e.disc_id,c.collect_id from ");
		sql.append(getTableName("M", "EVAL"));
		sql.append(" e left join ");
		sql.append(getTableName("X", "CAT_DISC"));
		sql.append(" cd on e.disc_id = cd.disc_id left join ");
		sql.append(getTableName("X", "CATEGORY"));
		sql.append(" c on c.id = cd.cat_id WHERE e.is_eval='1' ");
		for(int i=0;i<conditionParams.size();i++){
				sql.append(" and e.");
				sql.append(conditionParams.get(i));
				sql.append(" = ? ");
		}
		return sql.toString();
	}

	@Override
	public List<String[]> getAllDiscIdAndName() {
		// TODO Auto-generated method stub
		StringBuilder sql = new  StringBuilder("select ID,NAME from ");
		sql.append("DSEP_BASE_DISCIPLINE ");
		sql.append(" where DSEP_BASE_DISCIPLINE.ID in ( select DISTINCT DISC_ID FROM ");
		sql.append(evalTableName());
		sql.append("  ) ");
		List<Object[]> listObjects=super.sqlScalarResults(sql.toString(),new String[]{"ID","NAME"});
		List<String[]> result = new ArrayList<String[]>(0);
		for(Object[] objects: listObjects){
			result.add(new String[]{objects[0].toString(),objects[1].toString()});
		}
		return result;
	}

	@Override
	public List<String> getBothEvalDiscList(String formerUnitId,
			String choosenUnitId) {
		// TODO Auto-generated method stub
		/*String sql = "select a.DISC_ID from "+ this.evalTableName()+" a,"
			+ this.evalTableName() + " b where a.UNIT_ID = '"+formerUnitId
			+ "' and b.UNIT_ID = '" + choosenUnitId + "' and "
			+ " a.DISC_ID = b.DISC_ID order by a.DISC_ID ASC";*/
		StringBuilder sql = new StringBuilder("select a.DISC_ID from ");
		sql.append(this.evalTableName());
		sql.append(" a, ");
		sql.append(this.evalTableName());
		sql.append(" b where a.UNIT_ID = ? and b.UNIT_ID = ? and "
			+ "a.DISC_ID = b.DISC_ID and a.IS_EVAL = '1' order by a.DISC_ID ASC");
		Object[] params = new Object[2];
		params[0] = formerUnitId;
		params[1] = choosenUnitId;
		return super.GetShadowResult(sql.toString(),params);
	}

	@Override
	public List<String> getAllEvalDiscIds() {
		// TODO Auto-generated method stub
		String sql = String.format("select DISTINCT DISC_ID FROM %s "
				+ " order by DISC_ID ASC",evalTableName());
		return super.GetShadowResult(sql);
	}

	@Override
	public Eval getEvalByUnitIdAndDiscId(String unitId, String discId) {
		// TODO Auto-generated method stub
		String hql ="from Eval where unitId = ? and discId = ?";
		List<Eval> evals = super.hqlFind(hql, new Object[]{unitId,discId});
		if(evals!=null&&evals.size()>0){
			return evals.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public List<Eval> getEvalByUnitId(String unitId) {
		// TODO Auto-generated method stub
		String sql="select * from "+evalTableName()+" where UNIT_ID=? order by IS_EVAL desc , DISC_ID asc ";
		return super.sqlFind(sql,new Object[]{unitId});
	}

	@Override
	public int updateEvalByUnitIdAndDiscId(String unitId, String discId,String attachId) {
		// TODO Auto-generated method stub
	    String sql="update "+evalTableName()+" set DISC_INTRODUCE_ID=? where UNIT_ID= ? and DISC_ID= ?";
	    return super.sqlBulkUpdate(sql,new Object[]{attachId,unitId,discId});
	}	
}
