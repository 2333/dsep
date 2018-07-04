package com.dsep.domain.dsepmeta.expert;

import com.dsep.entity.expert.EvalIndicWt;

public class EvalIndicWtAndScore {
	private String questionId;
	private String resultId;
	private String id;
	// 有效的item的个数，有些指标权重给一项打分，有些给三项打分
	private String effectItemNum;
	// 题目所针对的学科
	private String discCatId;
	// 有的题目是针对重点实验室、基地、中心；有的是建筑设计获奖（仅对建筑学类学科）
	private String questionNamespace;
	// 有效项目的名字，比如一等奖
	private String item1;
	private String item2;
	private String item3;
	private String item4;
	private String item5;
	private String item6;
	private String item7;
	// 初始分数，建议值
	private String initVal1;
	private String initVal2;
	private String initVal3;
	private String initVal4;
	private String initVal5;
	private String initVal6;
	private String initVal7;
	
	// 该数据对应的采集项
	private String collectId;
	// 该数据对应的采集表中的字段
	private String columnId;
	// 其他的题干信息
	private String questionStem;
	public EvalIndicWtAndScore(EvalIndicWt w) {
		this.id = w.getId();
		this.effectItemNum = w.getEffectItemNum();
		this.discCatId = w.getDiscCatId();
		this.item1 = w.getItem1();
		this.item2 = w.getItem2();
		this.item3 = w.getItem3();
		this.item4 = w.getItem4();
		this.item5 = w.getItem5();
		this.item6 = w.getItem6();
		this.item7 = w.getItem7();
		this.initVal1 = w.getInitVal1();
		this.initVal2 = w.getInitVal2();
		this.initVal3 = w.getInitVal3();
		this.initVal4 = w.getInitVal4();
		this.initVal5 = w.getInitVal5();
		this.initVal6 = w.getInitVal6();
		this.initVal7 = w.getInitVal7();		
		this.questionStem = w.getQuestionStem();
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEffectItemNum() {
		return effectItemNum;
	}

	public void setEffectItemNum(String effectItemNum) {
		this.effectItemNum = effectItemNum;
	}

	public String getDiscCatId() {
		return discCatId;
	}

	public void setDiscCatId(String discCatId) {
		this.discCatId = discCatId;
	}

	public String getQuestionNamespace() {
		return questionNamespace;
	}

	public void setQuestionNamespace(String questionNamespace) {
		this.questionNamespace = questionNamespace;
	}

	public String getItem1() {
		return item1;
	}

	public void setItem1(String item1) {
		this.item1 = item1;
	}

	public String getItem2() {
		return item2;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public String getItem3() {
		return item3;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}

	public String getItem4() {
		return item4;
	}

	public void setItem4(String item4) {
		this.item4 = item4;
	}

	public String getItem5() {
		return item5;
	}

	public void setItem5(String item5) {
		this.item5 = item5;
	}
	
	public String getItem6() {
		return item6;
	}

	public void setItem6(String item6) {
		this.item6 = item6;
	}

	public String getItem7() {
		return item7;
	}

	public void setItem7(String item7) {
		this.item7 = item7;
	}

	public String getInitVal1() {
		return initVal1;
	}

	public void setInitVal1(String initVal1) {
		this.initVal1 = initVal1;
	}

	public String getInitVal2() {
		return initVal2;
	}

	public void setInitVal2(String initVal2) {
		this.initVal2 = initVal2;
	}

	public String getInitVal3() {
		return initVal3;
	}

	public void setInitVal3(String initVal3) {
		this.initVal3 = initVal3;
	}

	public String getInitVal4() {
		return initVal4;
	}

	public void setInitVal4(String initVal4) {
		this.initVal4 = initVal4;
	}

	public String getInitVal5() {
		return initVal5;
	}

	public void setInitVal5(String initVal5) {
		this.initVal5 = initVal5;
	}
	
	public String getInitVal6() {
		return initVal6;
	}

	public void setInitVal6(String initVal6) {
		this.initVal6 = initVal6;
	}

	public String getInitVal7() {
		return initVal7;
	}

	public void setInitVal7(String initVal7) {
		this.initVal7 = initVal7;
	}

	public String getCollectId() {
		return collectId;
	}

	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getQuestionStem() {
		return questionStem;
	}

	public void setQuestionStem(String questionStem) {
		this.questionStem = questionStem;
	}

}
