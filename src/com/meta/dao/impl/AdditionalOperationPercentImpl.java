package com.meta.dao.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dsep.common.exception.BusinessException;
import com.meta.dao.AdditionalOperation;
import com.meta.entity.MetaPercentData;
/**
 * 针对原有的百分号类型，将三个字段变成一个字段，但2014年7月7日之后，百分号类型变成1个，此接口不再使用
 * @author thbin
 *
 */
public class AdditionalOperationPercentImpl extends AdditionalOperation {
	
	public AdditionalOperationPercentImpl(String columnName) {
		super(columnName);
	}

	public void execObject(Map<String, Object> row) {
		/*Object obj = row.get(MetaPercentData.getTypeColumn(columnName)).toString();		
		String type = "";
		if(obj!=null) type = obj.toString();
		
		obj = row.get(MetaPercentData.getNumColumn(columnName));
		int num = 0;
		if(obj!=null) num = (int) obj;
		
		obj = row.get(MetaPercentData.getValueColumn(columnName));
		String value = "";
		if(obj!=null) value = obj.toString();
		
		MetaPercentData percentData = 
				new MetaPercentData(type, num, value);
		row.put(columnName, percentData);*/
		String percentData = row.get(columnName).toString();
		try{
			MetaPercentData value = new MetaPercentData(percentData);
			row.remove(columnName);
			row.put(columnName, value);
		}catch(BusinessException ex){
			MetaPercentData value = new MetaPercentData("0", 0, "0");
			row.remove(columnName);
			row.put(columnName, value);
		}
	}

	public void exec(Map<String, String> row){
		String type = row.get(MetaPercentData.getTypeColumn(columnName));	
		
		String numStr = row.get(MetaPercentData.getNumColumn(columnName));
		int num = 0;
		if(StringUtils.isNotBlank(numStr)){
			num = Integer.parseInt(numStr);
		}
		
		String value = row.get(MetaPercentData.getValueColumn(columnName));
		
		MetaPercentData percentData = 
				new MetaPercentData(type, num, value);
		row.put(columnName, percentData.toString());		
	}
}
