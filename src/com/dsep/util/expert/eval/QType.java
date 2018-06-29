package com.dsep.util.expert.eval;

// 该enum的对应的数值应该和数据库*_X_EVAL_QUEST_*中的打分题目数值一致！
public enum QType {
	// 指标体系打分
	INDIC_IDX(0),
	// 指标权重打分
	INDIC_WT(1),
	// 学科成果打分
	ACHV(2),
	// 学科声誉打分
	REPU(3),
	// 学科排名打分
	RANK(4);
	
	private int questionType;

	QType(int type) {
		this.questionType = type;
	}

	public int toInt() {
		return questionType;
	}
	
	 @Override
     public String toString() {
         return String.valueOf(this.questionType);
     } 
}
