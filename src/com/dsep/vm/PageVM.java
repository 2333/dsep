package com.dsep.vm;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PageVM<T> implements Serializable{
	private int pageIndex;
	private int totalPage;
	private int totalCount;
	private List<T> list;
	
	public PageVM(int pageIndex,int totalCount,int pageSize,List<T> list){
		this.pageIndex=pageIndex;
		this.totalCount=totalCount;		
		if(pageSize != 0)
			this.totalPage=(int) Math.ceil((double)totalCount/(double)pageSize);
		this.list=list;
	}


	public int getTotalPage() {
		return totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}


	public int getPageIndex() {
		return pageIndex;
	}

	public Map<String,Object> getGridData(){
		Map<String,Object> m = new LinkedHashMap<String,Object>();
		m.put("pageIndex", this.pageIndex);
		m.put("totalPage", this.totalPage);
		m.put("totalCount", this.totalCount);
		m.put("rows", this.list);
		return m;		
	}


}
