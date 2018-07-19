package com.meta.domain.search;

public enum SearchType {
	INT("integer"),
	NUMBER("number"),
	YEAR("year"),
	YEARMONTH("yearmonth"),
	DATE("date"),
	DATETIME("datetime"),
	STRING("text"),
	ERROR("error");
	final private String type;
	private SearchType(String type){
		this.type = type;
	}
	@Override
	public String toString() {
		return type;
	}
	static public SearchType getSearchType(String type){
		for(SearchType searchType: SearchType.values()){
			if(searchType.toString().equals(type)) return searchType;
		}
		return SearchType.ERROR;
	}
}
