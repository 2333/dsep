package com.dsep.vm.flow;

import java.util.HashMap;
import java.util.Map;

import com.dsep.util.Dictionaries;

public class CollectFlowVM {
	private Map<String, String> rowData=new HashMap<String, String>();
	private String stateName;
	private String isEval;
	private String isReport;
	public CollectFlowVM(){
		
	}
	public CollectFlowVM(Map<String, String>map)
	{
		rowData.putAll(map);
		stateName=Dictionaries.getCollectFlowTypes(Integer.valueOf(map.get("STATE")));
		setIsEval(map.get("IS_EVAL"));
		setIsReport(map.get("IS_REPORT"));
	}
	public Map<String, String> getRowData() {
		return rowData;
	}
	public void setRowData(Map<String, String> rowDataMap) {
		this.rowData = rowDataMap;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getIsEval() {
		return isEval;
	}
	public void setIsEval(String isEval) {
		if("1".equals(isEval))
		{
			this.isEval = "是";
		}else  if("0".equals(isEval)){
			this.isEval="否";
		}
	}
	public String getIsReport() {
		return isReport;
	}
	public void setIsReport(String isReport) {
		if("1".equals(isReport))
		{
			this.isReport = "是";
		}else  if("0".equals(isReport)){
			this.isReport="否";
		}
		
	}
	

}
