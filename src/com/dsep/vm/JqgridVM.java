package com.dsep.vm;

import java.util.List;
import java.util.Map;


public class JqgridVM {
	private int pageIndex;
	private int totalPage;
	private int totalCount;
	private List<Map<String,String>> rows;
	public JqgridVM()
	{
		
	}
	public JqgridVM(int pageIndex,int totalCount,int pageSize,List<Map<String,String>>list){
		this.pageIndex=pageIndex;
		this.totalCount=totalCount;
		this.totalPage=this.getTotalPages(totalCount,pageSize);
		this.rows=list;
	}
	
	private int getTotalPages(int totalCount,int pageSize)
	{
		double tmp=(double)totalCount/(double)pageSize;
		 return ((int)Math.ceil((double)totalCount/(double)pageSize));
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<Map<String, String>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, String>> rows) {
		this.rows = rows;
	}

	
	

}
