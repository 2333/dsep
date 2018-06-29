package com.dsep.entity.dsepmeta;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dsep.entity.dsepmeta.Index;

@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
public class IndexMap {
	private String id;
	private String itemId;
	private String name;
	private String isSubject;
	private String parentId;
	private String rule;
	private String weight;
	private int indexLevel;
	
	public int getIndexLevel() {
		return indexLevel;
	}
	public void setIndexLevel(int indexLevel) {
		this.indexLevel = indexLevel;
	}
	private Index index;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsSubject() {
		return isSubject;
	}
	public void setIsSubject(String isSubject) {
		this.isSubject = isSubject;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public Index getIndex() {
		return index;
	}
	public void setIndex(Index index) {
		this.index = index;
	}
}
