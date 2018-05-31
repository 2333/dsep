package com.dsep.domain.dsepmeta.expert;

import com.dsep.entity.dsepmeta.Index;
import com.dsep.entity.dsepmeta.IndexMap;

public class IndexMapScore {
	private String id;
	private String itemId;
	private String name;
	private String isSubject;
	private String parentId;
	private String rule;
	private String weight;
	private String score;
	private String state;
	private String questionId;
	private String resultId;	
	private int indexLevel;
	private Index index;
	
	public IndexMapScore(IndexMap indexMap) {
		this.id = indexMap.getId();
		this.itemId = indexMap.getItemId();
		this.name = indexMap.getName();
		this.isSubject = indexMap.getIsSubject();
		this.parentId = indexMap.getParentId();
		this.rule = indexMap.getRule();
		this.weight = indexMap.getWeight();
		this.indexLevel = indexMap.getIndexLevel();
		this.index = indexMap.getIndex();
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public int getIndexLevel() {
		return indexLevel;
	}
	
	public void setIndexLevel(int indexLevel) {
		this.indexLevel = indexLevel;
	}
	
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
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
}
