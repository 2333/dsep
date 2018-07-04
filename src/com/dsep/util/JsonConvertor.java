package com.dsep.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;


import com.meta.domain.search.SearchGroup;
import com.meta.domain.search.SearchRule;


public class JsonConvertor {	
	private static  ObjectMapper mapper = null;
	@SuppressWarnings("deprecation")
	private static ObjectMapper getMapper(){
		if(mapper == null){
			mapper= new ObjectMapper();
		}
		mapper.getSerializationConfig().disable(Feature.FAIL_ON_EMPTY_BEANS);
		mapper.configure(Feature.WRITE_NULL_PROPERTIES,false);
		return mapper; 		
	}
	
	public static final String mapJSON(Map<String,String> jsonMap){
		String result = "";
		if( jsonMap == null || jsonMap.size() == 0){
			result += "{}";
			return result;
		}
		result += "{root:[";
		for(Map.Entry<String, String> entry:jsonMap.entrySet()){
			String row = "{name:'"+entry.getKey()+"',value:'"+entry.getValue()+"'}";
			result += row+",";
		}
		result = result.substring(0, result.length()-1);
		result += "]}";
		return result;
	}
	
	public static final String obj2JSON(Object obj){
		String json = null;
		try {
			json = getMapper().writeValueAsString(obj);
		} catch ( IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static final SearchGroup string2SearchObject(String filters){
		JSONObject filter = JSONObject.fromObject(filters);//获取根group
		if(filter!=null){
			List<JSONObject> jsonObjects= new ArrayList<JSONObject>(0);
			jsonObjects.add(filter);//将root group放入数组
			List<SearchGroup> groups= new ArrayList<SearchGroup>(0);//group LIST
			List<Integer> parents= new ArrayList<Integer>(0); //记录父节点
			for(int i=0;i<jsonObjects.size();i++){
				JSONObject jsonObject = jsonObjects.get(i);
				SearchGroup searchGroup = new SearchGroup();
				if(jsonObject.has("groupOp")){
					String groupOp = jsonObject.getString("groupOp");
					searchGroup.setGroupOp(groupOp);
				}
				if(jsonObject.has("rules")){
					JSONArray jsonArray = jsonObject.getJSONArray("rules");
					List<SearchRule> rules= new ArrayList<SearchRule>(0);
					for(Object object: jsonArray){
						JSONObject jObject = (JSONObject)object;
						SearchRule rule = new SearchRule();
						rule.setField(jObject.getString("field"));
						rule.setData(jObject.getString("data"));
						rule.setOp(jObject.getString("op"));	
						rule.setType(jObject.getString("type"));
						rules.add(rule);
					}
					searchGroup.setRules(rules);
				}
				if(groups.size()==0){
					parents.add(-1);
				}else{
					SearchGroup indexGroup= groups.get(parents.get(i));
					indexGroup.getGroups().add(searchGroup);
				}
				groups.add(searchGroup);
				if(jsonObject.has("groups")){
					JSONArray jsonArrayGroups= jsonObject.getJSONArray("groups");
					for(Object object:jsonArrayGroups){
						jsonObjects.add((JSONObject)object);
						parents.add(i);
					}
				}
			}
			return groups.get(0);
		}
		return null;
	}
}
