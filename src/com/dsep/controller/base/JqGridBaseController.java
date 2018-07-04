package com.dsep.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.PRIVATE_MEMBER;

import com.dsep.util.JsonConvertor;
import com.dsep.util.Tools;
import com.meta.domain.search.SearchGroup;

public class JqGridBaseController {
	private String sidx;
	private boolean search;
	private SearchGroup searchGroup = new SearchGroup();
	private int pageIndex = 0;
	private int pageSize = 0;
	boolean asc;
	
	public void setRequestParams(HttpServletRequest request){
		String sord=request.getParameter("sord");//排序方式（升序、降序）
		String sidx=request.getParameter("sidx");//排序字段
		String page = request.getParameter("page"); //当前页
		String rows= request.getParameter("rows");//获取记录数
		String search = request.getParameter("_search");//是否排序
		setSidx(sidx);
		setSearch(search);
		setPageIndex(page);
		setPageSize(rows);
		setAsc(sord);
		if(this.search){
			String filters = request.getParameter("filters");
			setSearchGroup(filters);
		}else{
			setSearchGroup("");
		}
	}
	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = Boolean.parseBoolean(search);
	}


	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String page) {
		this.pageIndex = 0;
		if(page!=null){
			this.pageIndex= Integer.valueOf(page);
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(String rows) {
		this.pageSize = 0;
		if(rows!=null&&Tools.isNumeric(rows)){
			this.pageSize=Integer.valueOf(rows);
		}
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(String sord) {
		this.asc= true;
		if("desc".equals(sord)){
			this.asc = false;
		}
		
	}
	public SearchGroup getSearchGroup() {
		return searchGroup;
	}
	public void setSearchGroup(String filters) {
		if(StringUtils.isNotBlank(filters)){
			this.searchGroup = JsonConvertor.string2SearchObject(filters);
		}else{
			this.searchGroup = new SearchGroup();
		}
		
	}
}
