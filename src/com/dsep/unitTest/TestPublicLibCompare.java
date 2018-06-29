package com.dsep.unitTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.dsep.util.publiccheck.PublicLibCompare;

public class TestPublicLibCompare {

	@Test
	public void testCompare(){
		
		List<Map<String, String>> pubDatas = new LinkedList<Map<String, String>>();
		List<Map<String, String>> entityDatas = new LinkedList<Map<String, String>>();
		List<Map<String, String>> fieldsMap = new LinkedList<Map<String, String>>();
		
		//构造公共库数据
		Map<String, String> pubRow = new LinkedHashMap<String, String>();
		pubRow.put("A1", "高等数学");
		pubRow.put("B1", "王老师");
		pubRow.put("C1", "主楼349");
		pubRow.put("D1", "必修");
		
		pubDatas.add(pubRow);
		
		pubRow = new LinkedHashMap<String, String>();
		pubRow.put("A1", "大学物理");
		pubRow.put("B1", "崔老师");
		pubRow.put("C1", "主楼213");
		pubRow.put("D1", "必修");
		
		pubDatas.add(pubRow);
		
		pubRow = new LinkedHashMap<String, String>();
		pubRow.put("A1", "线性代数");
		pubRow.put("B1", "李老师");
		pubRow.put("C1", "一号楼115");
		pubRow.put("D1", "必修");
		
		pubDatas.add(pubRow);
		
		pubRow = new LinkedHashMap<String, String>();
		pubRow.put("A1", "矩阵理论");
		pubRow.put("B1", "张老师");
		pubRow.put("C1", "主M102");
		pubRow.put("D1", "必修");
		
		pubDatas.add(pubRow);
		
		//构造本地数据
		Map<String, String> entityRow = new LinkedHashMap<String, String>();
		entityRow.put("ID", "1");
		entityRow.put("UNIT_ID", "10006");
		entityRow.put("DISC_ID", "0835");
		entityRow.put("A2", "高等数学");
		entityRow.put("B2", "王老师");
		entityRow.put("C2", "主楼349");
		entityRow.put("D2", "必修");
		
		entityDatas.add(entityRow);
		
		entityRow = new LinkedHashMap<String, String>();
		entityRow.put("ID", "2");
		entityRow.put("UNIT_ID", "10006");
		entityRow.put("DISC_ID", "0835");
		entityRow.put("A2", "高等数学");
		entityRow.put("B2", "李老师");
		entityRow.put("C2", "主楼349");
		entityRow.put("D2", "必修");
		
		entityDatas.add(entityRow);
		
		entityRow = new LinkedHashMap<String, String>();
		entityRow.put("ID", "3");
		entityRow.put("UNIT_ID", "10006");
		entityRow.put("DISC_ID", "0835");
		entityRow.put("A2", "高等数学");
		entityRow.put("B2", "林老师");
		entityRow.put("C2", "主楼350");
		entityRow.put("D2", "必修");
		
		entityDatas.add(entityRow);
		
		entityRow = new LinkedHashMap<String, String>();
		entityRow.put("ID", "4");
		entityRow.put("UNIT_ID", "10006");
		entityRow.put("DISC_ID", "0835");
		entityRow.put("A2", "高等化学");
		entityRow.put("B2", "王老师");
		entityRow.put("C2", "主楼349");
		entityRow.put("D2", "必修");
		
		entityDatas.add(entityRow);
		
		//构造字段映射表数据
		Map<String, String> mapRow = new LinkedHashMap<String, String>();
		mapRow.put("publibField", "A1");
		mapRow.put("entityField", "A2");
		mapRow.put("fieldType", "1");
		
		fieldsMap.add(mapRow);
		
		mapRow = new LinkedHashMap<String, String>();
		mapRow.put("publibField", "B1");
		mapRow.put("entityField", "B2");
		mapRow.put("fieldType", "0");
		
		fieldsMap.add(mapRow);
		
		mapRow = new LinkedHashMap<String, String>();
		mapRow.put("publibField", "C1");
		mapRow.put("entityField", "C2");
		mapRow.put("fieldType", "0");
		
		fieldsMap.add(mapRow);
		
		mapRow = new LinkedHashMap<String, String>();
		mapRow.put("publibField", "D1");
		mapRow.put("entityField", "D2");
		mapRow.put("fieldType", "0");
		
		fieldsMap.add(mapRow);
		
		
		//比对结果
		List<Map<String, String>> results;
		
		/*results = PublicLibCompare.check(pubDatas, entityDatas, fieldsMap);*/
		
		/*Assert.assertNotNull(results);*/
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testReflect(){
		
		try {
			Class<?> c = Class.forName("com.dsep.util.publiccheck.normalization.PubPretreatDate");
			Class[] args1 = new Class[1];
			args1[0] = String.class;
			Method m = c.getDeclaredMethod("pretreat",args1);
			
			String argments = "10-03-02";
			String result = (String)m.invoke(null, argments);
			Assert.assertEquals("20100302", result);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
