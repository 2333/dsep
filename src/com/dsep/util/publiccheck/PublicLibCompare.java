package com.dsep.util.publiccheck;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class PublicLibCompare {

	public static List<Map<String, String>> check(
			List<Map<String, String>> pubDatas,
			List<Map<String, String>> entityDatas,
			List<Map<String, String>> fieldsMap,
			String entityChsName,
			String flagField
			){
		//结果表
		List<Map<String, String>> result = new LinkedList<Map<String, String>>();
		boolean compareResult;
		
		//取本地数据一条，确定关键字段
		for(Map<String, String> entityRow: entityDatas){
			
			//比对缓存表
			List<Map<String, String>> compareCache = new LinkedList<Map<String, String>>();
			
			//对公共库数据进行第一遍循环，先比对关键字段，将比对成功的记录在缓存表里边
			for(Map<String, String> pubRow: pubDatas){
				boolean flag = true;
				for(Map<String, String> fm: fieldsMap){
					if(fm.get("fieldType").toString().equals("1")){
						
						String pubField = fm.get("publibField").toString();
						String entityField = fm.get("entityField").toString();
						
						//应用规范化规则
						String pMethod = fm.get("normRule") == null?null:fm.get("normRule").toString();
						String pubValue = pretreat(pubRow.get(pubField).toString(),pMethod);
						
						String entityValue = entityRow.get(entityField).toString();
						//应用比对规则
						String cMethod = fm.get("compareRule") == null?null:fm.get("compareRule").toString();
						compareResult = compare(entityValue, pubValue, cMethod);
						if(!compareResult){
							flag = false;
							break;
						}
						
					}
				}
				//表示关键字段完全匹配，记录入缓存表里边
				if(flag) compareCache.add(pubRow);
			}
			//如果缓存表为空，则表示不在公共库，记录之
			int count = compareCache.size();
			if(count <= 0){
				for(Map<String, String> fm: fieldsMap){
					if(fm.get("fieldType").toString().equals("1")){
						Map<String, String> resultItem = new LinkedHashMap<String, String>();
						String entityField = fm.get("entityField").toString();
						String entityChsField = fm.get("entityChsField").toString();
						String entityValue = entityRow.get(entityField).toString();
						resultItem.put("unitId", entityRow.get("UNIT_ID").toString());
						resultItem.put("discId", entityRow.get("DISC_ID").toString());
						resultItem.put("entityChsName", entityChsName);
						resultItem.put("seqNo", entityRow.get("SEQ_NO").toString());
						resultItem.put("localItemId", entityRow.get("ID").toString());
						resultItem.put("localField", entityField);
						resultItem.put("localChsField", entityChsField);
						resultItem.put("localValue", entityValue);
						resultItem.put("compareResult", PublicLibResult.MISS.toString());
						resultItem.put("flagField", entityRow.get(flagField).toString());
						result.add(resultItem);//将结果记入结果集
					}
				}
			}
			else{
				//如果缓存表不为空，比对非关键字段，如果非关键字段不匹配，为异议记录，记录下来
				for(Map<String, String> cache:compareCache){
					for(Map<String, String> fm: fieldsMap){
						if(!fm.get("fieldType").toString().equals("1")){
							String pubField = fm.get("publibField").toString();
							String entityField = fm.get("entityField").toString();
							String entityChsField = fm.get("entityChsField").toString();
							//应用规范化规则
							String pMethod = fm.get("normRule") == null?null:fm.get("normRule").toString();
							String pubValue = pretreat(cache.get(pubField).toString(), pMethod);
							
							String entityValue = entityRow.get(entityField).toString();
							if(fm.get("fieldType").toString().equals("2")) entityValue = "*"+entityValue;
							if(fm.get("fieldType").toString().equals("3")) entityValue = "#"+entityValue;
							
							//应用比对规则
							String cMethod = fm.get("compareRule") == null?null:fm.get("compareRule").toString();
							compareResult = compare(entityValue, pubValue, cMethod);
							if(!compareResult){
								//记录异议数据
								Map<String, String> resultItem = new LinkedHashMap<String, String>();
								resultItem.put("unitId", entityRow.get("UNIT_ID").toString());
								resultItem.put("discId", entityRow.get("DISC_ID").toString());
								resultItem.put("entityChsName", entityChsName);
								resultItem.put("seqNo", entityRow.get("SEQ_NO").toString());
								resultItem.put("localItemId", entityRow.get("ID").toString());
								resultItem.put("localField", entityField);
								resultItem.put("localChsField", entityChsField);
								resultItem.put("localValue", entityRow.get(entityField).toString());
								resultItem.put("pubValue", cache.get(pubField).toString());
								resultItem.put("compareResult", PublicLibResult.OBJECTIONS.toString());
								resultItem.put("flagField", entityRow.get(flagField).toString());
								result.add(resultItem);//将结果记入结果集
							}
						}
					}
				}
				//如果完全匹配说明在公共库，不做记录
			}
		}
		return result;
	}
	
	//比对前规范化数据
	@SuppressWarnings("rawtypes")
	private static String pretreat(String origin,String normRule){
		String object = "";
		
		try {
			if(!StringUtils.isNotBlank(normRule)) 
			{
				return origin;
			}
			Class<?> c = Class.forName("com.dsep.util.publiccheck.normalization." + normRule);
			Class[] args = new Class[1];
			args[0] = String.class;
			Method m = c.getDeclaredMethod("pretreat",args);
			object = (String)m.invoke(null, origin);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
		return object;
	}
	
	//应用比对规则进行比对
	@SuppressWarnings("rawtypes")
	private static boolean compare(String localValue, String pubValue, String copmareRule){
		boolean result = false;
		
		try {
			if(!StringUtils.isNotBlank(copmareRule)) 
			{
				return pubValue.equals(localValue);
			}
			Class<?> c = Class.forName("com.dsep.util.publiccheck.normalization." + copmareRule);
			Class[] args = new Class[2];
			args[0] = String.class;
			args[1] = String.class;
			Method m = c.getDeclaredMethod("compare",args);
			result = (boolean) m.invoke(null, localValue, pubValue);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
		return result;
	}
}
