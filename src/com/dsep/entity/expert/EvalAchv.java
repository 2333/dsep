package com.dsep.entity.expert;

public class EvalAchv {
	private String id;

	// 该数据对应的采集项
	private String collectId1;
	private String collectId2;
	private String collectId3;
	private String collectId1Name;
	private String collectId2Name;
	private String collectId3Name;
	private String questionName;
	private String discCatId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCollectId1() {
		return collectId1;
	}

	public void setCollectId1(String collectId1) {
		this.collectId1 = collectId1;
	}

	public String getCollectId2() {
		return collectId2;
	}

	public void setCollectId2(String collectId2) {
		this.collectId2 = collectId2;
	}

	public String getCollectId3() {
		return collectId3;
	}

	public void setCollectId3(String collectId3) {
		this.collectId3 = collectId3;
	}

	public String getCollectId1Name() {
		return collectId1Name;
	}

	public void setCollectId1Name(String collectId1Name) {
		this.collectId1Name = collectId1Name;
	}

	public String getCollectId2Name() {
		return collectId2Name;
	}

	public void setCollectId2Name(String collectId2Name) {
		this.collectId2Name = collectId2Name;
	}

	public String getCollectId3Name() {
		return collectId3Name;
	}

	public void setCollectId3Name(String collectId3Name) {
		this.collectId3Name = collectId3Name;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getDiscCatId() {
		return discCatId;
	}

	public void setDiscCatId(String discCatId) {
		this.discCatId = discCatId;
	}

}
