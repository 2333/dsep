package com.dsep.dao.dsepmeta.base.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.base.CategoryCollectDao;
import com.dsep.entity.dsepmeta.CategoryCollect;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.entity.MetaEntity;

public class CategoryCollectDaoImpl extends DsepMetaDaoImpl<CategoryCollect, String> implements CategoryCollectDao{

	private String getTableName()
	{
		return super.getTableName("X", "CAT_COLLECT");
	}
	@Override
	public List<CategoryCollect> getCategoryTreeById(String id) {
		// TODO Auto-generated method stub
		String sql=String.format("select * from  %s  START WITH ID=? CONNECT BY PRIOR ID=PARENT_ID order by id ",this.getTableName());
		return super.sqlFind(sql, new Object[]{id});
	}

	@Override
	public List<CategoryCollect> getCategorysByParentId(String parentId) {
		// TODO Auto-generated method stub
		String sql=String.format("select * from %s where PARENT_ID=?", this.getTableName());
		return super.sqlFind(sql, new Object[]{parentId});
	}

	@Override
	public CategoryCollect getCatCollectById(String id) {
		// TODO Auto-generated method stub
		String sql= String.format("select * from %s where ID=?", this.getTableName());
		return (CategoryCollect)super.sqlUniqueResult(sql, new Object[]{id});
	}
	@Override
	public void importToLogic(String unitId,String discId,String collectId) {
		// TODO Auto-generated method stub
		String sql = "insert into "+getTableName("D", "LOGIC_CHECK")+ 
				"L (UNIT_ID,DISC_ID,ENTITY_ID,ENTITY_CHS_NAME) SELECT '"+unitId+"','"+discId+"',META_ID,META_CHSNAME from "
				+getTableName("X", "CAT_COLLECT")+" cc"
				+" where cc.META_CAT = 'E'"
				+" START WITH cc.ID=? CONNECT BY PRIOR cc.ID=cc.PARENT_ID";
				
		super.sqlBulkUpdate(sql,new Object[]{collectId});
	}
	@Override
	public List<String> getEntiyIdsById(String catId) {
		// TODO Auto-generated method stub
		//如果取出的数据为一列Object[] 为Object
		String sql=String.format("select * from  %s where META_CAT='E'  START WITH ID=? CONNECT BY PRIOR ID=PARENT_ID ",this.getTableName());
		List<Object[]> rows= super.sqlScalarResults(sql, new String[]{"META_ID"}, new String[]{catId});
		//return super.sql
		List<String> entityIds= new ArrayList<String>(0);
		for(Object object:rows){
			entityIds.add(String.valueOf(object));
		}
		return entityIds;
	}
	@Override
	public Map<String, String> getEnIdAndEnBkIdsById(String catCollectId) {
		// TODO Auto-generated method stub
		String sql=String.format("select * from  %s where META_CAT='E'  START WITH ID=? CONNECT BY PRIOR ID=PARENT_ID ",this.getTableName());
		Map<String, String> enAndBkIds=new HashMap<String,String>(0);
		List<Object[]> enIdAndBkIds=super.sqlScalarResults(sql,new String[]{"META_ID","META_BACKUP_ID"},new String[]{catCollectId});
		for(Object[]o:enIdAndBkIds){
			enAndBkIds.put(String.valueOf(o[0]), String.valueOf(o[1]));
		}
		return enAndBkIds;
	}
	@Override
	public Map<String, String> getEntityIdAndName(String unitId, String discId) {
		// TODO Auto-generated method stub
		Map<String, String> entityIdAndName= new HashMap<String,String>(0);
		List<String> conditionCols=  new ArrayList<String>(0);
		List<String> param =  new ArrayList<String>(0); 
		if(StringUtils.isNotBlank(unitId)){
			conditionCols.add("UNIT_ID");
			param.add(unitId);
		}
		if(StringUtils.isNotBlank(discId))
		{
			conditionCols.add("DISC_ID");
			param.add(discId);
		}
		String sql = buildSql(conditionCols);
		List<Object[]> rows= super.sqlScalarResults(sql,new String[]{"META_ID","META_CHSNAME"}, param.toArray());
		for(Object[] r:rows){
			entityIdAndName.put(String.valueOf(r[0]), String.valueOf(r[1]));
		}
		return entityIdAndName;
	}
	private String buildSql(List<String>colnames)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select * from  dsep_x_cat_collect_2013 where META_CAT='E' START WITH ID in ");
		sql.append("( select cat.collect_id from dsep_x_category_2013 cat,dsep_x_cat_disc_2013 cat_disc where cat.id = cat_disc.cat_id ");
		sql.append(" and cat_disc.disc_id in (select eval.disc_id from dsep_m_eval_2013 eval ");
		if(colnames.size()>0)
		{
			int index=1;
			for(int i=0;i<colnames.size();i++){
				if(index==1){
					sql.append(" where "+ colnames.get(i)+" = ? ");
				}
				else {
					sql.append(" and  "+colnames.get(i)+" = ? ");
				}
				index++;
			}
		}
		sql.append(")) CONNECT BY PRIOR ID=PARENT_ID ");
		return sql.toString();
	}
	@Override
	public Map<String, String> getAllEntityAndBackupId() {
			String sql= String.format("select META_ID,META_CHSNAME FROM  %s WHERE META_CAT='E' group by META_ID,META_CHSNAME",getTableName());
			Map<String, String> enAndBkIds=new HashMap<String,String>(0);
			List<Object[]> enIdAndBkIds=super.sqlScalarResults(sql,new String[]{"META_ID","META_CHSNAME"});
			for(Object[]o:enIdAndBkIds){
				enAndBkIds.put(String.valueOf(o[0]), String.valueOf(o[1]));
			}
			return enAndBkIds;
		
	}
	@Override
	public List<String> getAllRootCollectId() {
		// TODO Auto-generated method stub
		String sql = String.format("select ID FROM %s where META_CAT = 'R'",getTableName());
		return  super.GetShadowResult(sql);
		
	}
	@Override
	public List<String[]> getEntityIdAndName(String collectId) {
		// TODO Auto-generated method stub
		String sql= String.format("select * from  %s where META_CAT='E' START WITH ID=? CONNECT BY PRIOR ID=PARENT_ID ",getTableName());
		List<Object[]> objects= super.sqlScalarResults(sql,new String[]{"META_ID","META_CHSNAME"},new Object[]{collectId});
		List<String[]> entityIdAndNames = new ArrayList<String[]>(0);
		for(Object[]o:objects)
		{
			String [] strings= new String[]{String.valueOf(o[0]),String.valueOf(o[1])};
			entityIdAndNames.add(strings);
		}
		return entityIdAndNames;
		
	}
	@Override
	public List<String> getAllEntityIds() {
		// TODO Auto-generated method stub
		String tableName = this.getTableName();
		String sql = String.format("select DISTINCT META_ID from %s where META_CAT='E' ",tableName);
		return super.GetShadowResult(sql);
	}
	
}
