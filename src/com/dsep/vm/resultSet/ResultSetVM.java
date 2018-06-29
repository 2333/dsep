package com.dsep.vm.resultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResultSetVM {
	private List<String> colNames;
	private List<List<Object>> rowDatas; 

	public ResultSetVM(){
		
	}
	public ResultSetVM(Set<String>colNames,List<Map<String, Object>>rowDatas){
		setColNames(colNames);
		setRowDatas(rowDatas);
	}
	public List<String> getColNames() {
		return colNames;
	}
	public void setColNames(Set<String> colNames) {
		this.colNames = new ArrayList<String>(0);
		this.colNames.addAll(colNames);
	}
	public List<List<Object>> getRowDatas() {
		return rowDatas;
	}
	public void setRowDatas(List<Map<String, Object>> rowDatas) {
		this.rowDatas = new ArrayList<List<Object>>(0);
		for(Map<String, Object> rowMap:rowDatas){
			List<Object> row = new ArrayList<Object>(0);
			for(String key : this.colNames){
				row.add(rowMap.get(key));
			}
			this.rowDatas.add(row);
		}
	}
	
}
