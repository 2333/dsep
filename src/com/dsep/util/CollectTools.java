package com.dsep.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

public class CollectTools {
	
	public static List<Map<String, String>> initRulesUtil(String ruleStr){
		Map<String, List<String>> tempRules = new HashMap<String,List<String>>(0);
		String []rules = ruleStr.split(";");
		int length = -1;
		for(String rule:rules){
			String colName = rule.split(":")[0];
			String[] values = StringDealUtil.getSubStrFromMidBrackets(
					rule.split(":")[1]).split(",");
			tempRules.put(colName, Arrays.asList(values));
			if(length == -1){
				length = values.length;
			}
		}
		List<Map<String, String>> rowDatas = new ArrayList<Map<String,String>>(0);
		for(int i=0;i<length;i++){
			Map<String, String> rowData = new HashMap<String,String>(0);
			for(String key : tempRules.keySet()){
				rowData.put(key, tempRules.get(key).get(i));
			}
			rowDatas.add(rowData);
		}
		return rowDatas;
	}

}
