package com.dsep.vm;

import com.dsep.domain.dsepmeta.expert.IndexMapScore;

public class IndexMapVM {
	private String id;
	private Boolean isLeaf;
	private Boolean expanded;
	private Boolean loaded;
	private Integer level;
	private String parent;
	private IndexMapScore indexMapScore;
	
	public IndexMapVM(IndexMapScore indexMapScore) {
		this.id = indexMapScore.getId();
		this.isLeaf = (indexMapScore.getIndexLevel()==3);
		// true means pre expanded and loaded!
		this.expanded = true;
		this.loaded = true;
		this.level = indexMapScore.getIndexLevel()-1;
		this.parent = indexMapScore.getParentId();
		this.indexMapScore = indexMapScore;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public Boolean getExpanded() {
		return expanded;
	}
	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
	public Boolean getLoaded() {
		return loaded;
	}
	public void setLoaded(Boolean loaded) {
		this.loaded = loaded;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public IndexMapScore getIndexMapScore() {
		return indexMapScore;
	}
	public void setIndexMapScore(IndexMapScore indexMapScore) {
		this.indexMapScore = indexMapScore;
	}
	
	
}
