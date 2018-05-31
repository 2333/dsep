package com.dsep.dao.common.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


















import org.hibernate.Query;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.util.Configurations;
import com.dsep.util.DateProcess;
import com.dsep.util.StringProcess;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class DsepMetaDaoImpl <T ,PK extends Serializable>  extends DaoImpl <T,PK>  implements DsepMetaDao<T,PK>{

	
	protected String getTableName(String category, String name){
		String prefix = Configurations.getTablePrefix();
		System.out.println(prefix);
		return Configurations.getTablePrefix()+"_"+category.toUpperCase()
				+ "_"+ name.toUpperCase() + "_" + Configurations.getTablePostfix(); 
	}
	
	/**
	 * 更新表的某几列数据,如果是Date类型会进行特殊处理
	 * @param id 表的主键值
	 * @param editRow key为类的属性名，value为类的属性值
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	@Override
	public int updateColumn(String pk,Map<String, Object> editRow) throws NoSuchFieldException, SecurityException{
		if( editRow.isEmpty() )
			return 0;
		else if(pk == null || pk == "")
			return 0;
		else
		{
			String hql = "update " + super.getEntityClass().getSimpleName() + " set ";
			for(Map.Entry<String, Object> entry: editRow.entrySet()){
				String type = super.getEntityClass().getDeclaredField(entry.getKey()).getGenericType().toString();
				if( type.equals("class java.util.Date")){//如果字段类型为Date型需进行特殊处理
					String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entry.getValue());
					hql += entry.getKey() + " = to_date('" + dateString + "','yyyy-MM-dd HH24:mi:ss') , "; 
				}
				else {
					hql += entry.getKey() + " = '" + entry.getValue() + "' , ";
				}
			}
			hql = hql.substring(0, hql.length()-2);
			hql += " where id = '"+pk+"'";
			System.out.println(hql);
			org.hibernate.Query query = getSession().createQuery(hql);
			return query.executeUpdate();
		}
	}
	
	@Override
	public int updateColumn(String pk,T t) throws Exception{
		if(pk == null || pk == "")
			return 0;
		else
		{
			String hql = "update " + super.getEntityClass().getSimpleName() + " set  ";
			String conditionStr = "";
			Field[] field = super.getEntityClass().getDeclaredFields();
			for(int i=0;i < field.length;i++){
				field[i].setAccessible(true);
				String type =  field[i].getGenericType().toString();
				if( isTypeNumber(type)){ //数字型
					if( (Integer)(field[i].get(t)) != -1){
						conditionStr += field[i].getName() + " = " + field[i].get(t) + " ,";
					}
				}
				else if(type.equals("class java.util.Date")){ //日期型
					if( field[i].get(t) != null){
						conditionStr += field[i].getName() + " = " + DateProcess.getDataBaseDate(field[i].get(t)) + " ,";
					}
				}
				else if( field[i].get(t) != null ){
					conditionStr += field[i].getName() + " = '" + field[i].get(t).toString() + "' ,";
				}
			}
			if( StringProcess.isNull(conditionStr)){
				throw new Exception("业务异常");
			}
			else{
				conditionStr = conditionStr.substring(0, conditionStr.length()-2);
				hql += conditionStr;
				hql += " where id = '"+pk+"'";
				org.hibernate.Query query = getSession().createQuery(hql);
				return query.executeUpdate();
			}
		}
	}
	
	
	protected String sqlAndConditon(List<String> columns){
		String sql = "";
		if(columns == null || columns.size() == 0) return "";
		int currentKeyIndex = 1;
		for(String key : columns){
			if(currentKeyIndex > 1) sql += String.format(" and %s = ? ",key);
			if(currentKeyIndex == 1) sql +=String.format( " where %s = ? ",key);
			currentKeyIndex++;
		}
		return sql;
	}
	
	protected String hqlAndCondtion(List<String> columns) {
		StringBuilder hql= new StringBuilder("");
		if(columns == null || columns.size()==0) return "";
		int currentKeyIndex=1;
		for(String key: columns)
		{
			if(currentKeyIndex>1) hql.append(String.format(" and %s = ?", key));
			if(currentKeyIndex==1) hql.append(String.format(" where %s = ?", key));
			currentKeyIndex++;
		}
		return hql.toString();
	}
	
	/**
	 * 判断类型是否是数字型
	 * @param type
	 * @return
	 */
	private boolean isTypeNumber(String type){
		switch(type){
			case "class java.lang.Integer":
			case "int":
			case "double":
			case "float":
				return true;
		}
		return false;
	}
	
	/**
	 * 获取查询字符串
	 * @param t
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String getHqlStr(T t) throws IllegalArgumentException, IllegalAccessException{
		StringBuilder hqlStr = new StringBuilder("from ");
		hqlStr.append(super.getEntityClass().getSimpleName());
		StringBuilder conditionStr = new StringBuilder("");
		Field[] field = super.getEntityClass().getDeclaredFields();
		for(int i=0;i < field.length;i++){
			field[i].setAccessible(true);
			String type =  field[i].getGenericType().toString();
			if( isTypeNumber(type)){
				if( (Integer)(field[i].get(t)) != -1 && (Integer)(field[i].get(t)) != 0){
					conditionStr.append(field[i].getName());
					conditionStr.append(" = ");
					conditionStr.append(field[i].get(t));
					conditionStr.append(" and ");
				}
			}
			else if(type.equals("class java.util.Date")){ //日期型
				if( field[i].get(t) != null){
					conditionStr.append(field[i].getName());
					conditionStr.append(" = ");
					conditionStr.append(DateProcess.getDataBaseDate(field[i].get(t)));
					conditionStr.append(" and ");
				}
			}
			else if( field[i].get(t) != null ){
				conditionStr.append(field[i].getName());
				conditionStr.append(" = '");
				conditionStr.append(field[i].get(t).toString());
				conditionStr.append("' and ");
			}
		}
		if( !conditionStr.toString().equals("")){
			hqlStr.append(" where ");
			hqlStr.append(conditionStr.substring(0, conditionStr.length()-4));
		}
		return hqlStr.toString();
	}
	
	private String getSortString(boolean desc){
		if( desc )
			return " desc";
		else
			return " asc";
	}

	@Override
	public List<T> queryByCondition(T t) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		return super.hqlFind(getHqlStr(t));
	}
	

	@SuppressWarnings("unchecked")
	public List<T> queryByCondition(T t,int pageIndex, int pageSize,
			Boolean desc, String orderProperName) throws IllegalArgumentException, IllegalAccessException{
		String hqlStr = getHqlStr(t);
		if(orderProperName != null && orderProperName != "" ){
			hqlStr += " order by  " + orderProperName;
			hqlStr += getSortString(desc);
		}
		Query query = getSession().createQuery(hqlStr);
		int firstResultIndex = (pageIndex - 1) * pageSize;
		query = query.setFirstResult(firstResultIndex);
		query = query.setMaxResults(pageSize);
		List<T> result = query.list();
		return result;
	}

	@Override
	public T querySingleDataByCondition(T t) throws IllegalArgumentException,
			IllegalAccessException {
		// TODO Auto-generated method stub
		List<T> dataList = this.queryByCondition(t);
		if( dataList != null && dataList.size() > 0)
			return dataList.get(0);
		else
			return null;
	}

	@Override
	public int getCountByCondition(T t) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		String hqlStr = this.getHqlStr(t);
		Query query = getSession().createQuery(hqlStr);
		List<T> theList = query.list();
		if( theList != null ){
			return theList.size();
		}
		else{
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryByCondition(T t, String orderProperty, boolean desc) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		String hqlStr = this.getHqlStr(t);
		if( desc ){
			hqlStr += " order by " + orderProperty + " desc";
		}
		else{
			hqlStr += " order by " + orderProperty + " asc";
		}
		Query query = getSession().createQuery(hqlStr);
		return query.list();
	}

	@Override
	public int deleteByCondition(T t) throws IllegalArgumentException,
			IllegalAccessException {
		// TODO Auto-generated method stub
		String hqlStr = "delete from " + super.getEntityClass().getSimpleName()+"";
		String conditionStr = "";
		Field[] field = super.getEntityClass().getDeclaredFields();
		for(int i=0;i < field.length;i++){
			field[i].setAccessible(true);
			String type =  field[i].getGenericType().toString();
			if( isTypeNumber(type)){
				if( (Integer)(field[i].get(t)) != 0){
					conditionStr += field[i].getName() + " = " + field[i].get(t) + " and ";
				}
			}
			else if( field[i].get(t) != null ){
				conditionStr += field[i].getName() + " = '" + field[i].get(t).toString() + "' and ";
			}
		}
		if( conditionStr != ""){
			hqlStr += " where " + conditionStr.substring(0, conditionStr.length()-4);
		}
		return getSession().createQuery(hqlStr).executeUpdate();
	}
}
