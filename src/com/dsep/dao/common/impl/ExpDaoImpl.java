package com.dsep.dao.common.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ctc.wstx.util.StringUtil;
import com.dsep.dao.common.ExpDao;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class ExpDaoImpl<T> implements ExpDao<T>{

	/*private static String noSessionException = "No Session found for current thread";
	private static Session session = null;*/
	/*private Class<T> entityClass;*/
	
	private JdbcTemplate jdbcTemplate;//jdbc采用注入的方式赋值
	private Class<T> entityClass;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	public ExpDaoImpl(){
		this.entityClass=null;
		Class c=getClass();
		Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
	}

	/**
	 * 设置类属性的值
	 * @param theField 属性字段
	 * @param type 属性类型
	 * @param fieldValue 从数据库中取出的属性值
	 * @param obj 属性所属的实体
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void setFieldValue(Field theField,String type,Object fieldValue,Object obj) throws IllegalArgumentException, IllegalAccessException{
		theField.setAccessible(true);
		switch(type){
			case "class java.lang.String":
				theField.set(obj, fieldValue.toString());
				break;
			case "int":
				theField.setInt(obj, Integer.parseInt(fieldValue.toString()));
				break;
			case "short":
			case "class java.lang.Short":
				theField.set(obj, ((BigDecimal)fieldValue).shortValue());
				break;
			case "double":
			case "class java.lang.Double":
				theField.set(obj, ((BigDecimal)fieldValue).doubleValue());
				break;
			case "float":
			case "class java.lang.Float":
				theField.set(obj, ((BigDecimal)fieldValue).floatValue());
				break;
			case "long":
			case "class java.lang.Long":
				theField.set(obj, ((BigDecimal)fieldValue).longValue());
				break;
			case "byte":
			case "class java.lang.Byte":
				theField.set(obj, ((BigDecimal)fieldValue).byteValue());
				break;
			case "char":
				theField.setChar(obj, fieldValue.toString().charAt(0));
				break;
			case "class java.sql.Time":
				break;
			default:
				theField.set(obj, fieldValue);
		}
	}
	
	@Override
	public List<T> getAllBySql(String sql) throws InstantiationException, IllegalAccessException{
		List<Map<String,Object>> listMap = jdbcTemplate.queryForList(sql);//获取查询结果,每行数据为一个Map
		List<T> objectList = new ArrayList<T>();
		Field[] field = this.entityClass.getDeclaredFields();//获取实体类的所有属性
		for(int i=0;i < listMap.size();i++){
			T newObject = this.entityClass.newInstance();//获取一个新的实体，与某一行数据对应
			Map<String,Object> rowMap = listMap.get(i);//获取数据集的第i行记录
			for(int j=0;j < field.length;j++){//给属性赋值
				field[j].setAccessible(true);
				Object fieldValue = rowMap.get(field[j].getName());//获取属性的值，可能为空
				String type = field[j].getGenericType().toString();//获取属性的类型
				if( fieldValue != null )//不为空才给属性赋值
					setFieldValue(field[j],type,fieldValue,newObject);
			}
			objectList.add(newObject);
		}
		return objectList;
	}
	
	public List<T> getAll(String tableName) throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		String sql = "select * from "+tableName+" ";
		return getAllBySql(sql);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int getCountBySql(String sql) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForInt(sql);
	}


}
