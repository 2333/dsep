package com.dsep.util.survey;

/**
 *  该enum的对应的数值应该和前台
 *  var qType = {SCQ : 0, MCQ : 1, blankQ : 2, mixQ : 3,
	   			 matrixQ : 4, SAQ : 5};
	数值一致！
 */

public enum QType {
	// 单选题
	SCQ(0),
	// 多选题
	MCQ(1),
	// 填空题
	blankQ(2),
	// 选择填空题
	mixQ(3),
	// 矩阵题
	matrixQ(4),
	// 简答题
	SAQ(5),
	// 板块
	paneQ(6),
	// 提示语句
	hintQ(7),
	// 其他，封装问卷名称、简介和GUID的实体
	other(8);
	

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
