package com.dsep.vm.collect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class BatchDataVM {
	private String entityId;
	private List<Map<String, String>>rowDatas;
	public BatchDataVM(){
		
	}
	public BatchDataVM(String params){
		List batchEditData=new ArrayList();
		JSONObject jsonObject = JSONObject.fromObject(params);
		String entityId=jsonObject.getJSONArray("tableId").get(0).toString();
		JSONArray titleValues= jsonObject.getJSONArray("titleValues");
		JSONArray rowsArray=jsonObject.getJSONArray("rows");
		for(Object o:rowsArray )
		{
			Map map= new HashMap();
			JSONObject row=(JSONObject)o;
			for(Object colName:titleValues)
			{	
				/*if("ATTACH_ID".equals(colName.toString())){
					if(!row.getString(colName.toString()).equals("请上传附件")){
						map.put(colName.toString(),row.getString(colName.toString()));
					}
				}else{
					map.put(colName.toString(),row.getString(colName.toString()));
				}*/
				map.put(colName.toString(),row.getString(colName.toString()));
			}
			batchEditData.add(map);
		}
		setEntityId(entityId);
		setRowDatas(batchEditData);
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public List<Map<String, String>> getRowDatas() {
		return rowDatas;
	}
	public void setRowDatas(List<Map<String, String>> rowDatas) {
		this.rowDatas = rowDatas;
	}
}
