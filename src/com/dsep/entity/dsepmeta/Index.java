package com.dsep.entity.dsepmeta;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.dsepmeta.IndexMap;

@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
public class Index {
	private String id;
	private String name;
	private String state;
	private IndexMap indexMap;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public IndexMap getIndexMap() {
		return indexMap;
	}
	public void setIndexMap(IndexMap indexMap) {
		this.indexMap = indexMap;
	}
}
