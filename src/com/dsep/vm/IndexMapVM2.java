package com.dsep.vm;

import com.dsep.domain.dsepmeta.expert.IndexMapScore;

public class IndexMapVM2 {
	private String id;
	private String level1Name;	
	private String level1Id;
	private String level2Name;
	private String level2Id;
	private String level2ParentId;
	private IndexMapScore indexMapScore;
	private String state;
	
	public IndexMapVM2(IndexMapScore indexMapScore,
			String level1Name,String level1Id,String level2Name,
			String level2Id,String level2ParentId){
		this.indexMapScore = indexMapScore;
		this.id = indexMapScore.getId();
		this.level1Name = level1Name;
		this.level1Id = level1Id;
		this.level2Name = level2Name;
		this.level2Id = level2Id;
		this.level2ParentId = level2ParentId;
		if(indexMapScore.getState()==null)
			this.state = "未提交";
		else if(indexMapScore.getState().equals("0"))
			this.state = "未提交";
		else this.state = "已提交";
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLevel1Id() {
		return level1Id;
	}

	public void setLevel1Id(String level1Id) {
		this.level1Id = level1Id;
	}

	public String getLevel2Id() {
		return level2Id;
	}

	public void setLevel2Id(String level2Id) {
		this.level2Id = level2Id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevel1Name() {
		return level1Name;
	}

	public void setLevel1Name(String level1Name) {
		this.level1Name = level1Name;
	}

	public String getLevel2Name() {
		return level2Name;
	}

	public void setLevel2Name(String level2Name) {
		this.level2Name = level2Name;
	}
	
	public String getLevel2ParentId() {
		return level2ParentId;
	}

	public void setLevel2ParentId(String level2ParentId) {
		this.level2ParentId = level2ParentId;
	}

	public IndexMapScore getIndexMapScore() {
		return indexMapScore;
	}

	public void setIndexMapScore(IndexMapScore indexMapScore) {
		this.indexMapScore = indexMapScore;
	}
	
	
}
