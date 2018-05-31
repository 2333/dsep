package com.dsep.util.survey;

public enum QNRStatus {
	// 创建未发布
	NOT_PUBLISHED(0),
	// 已经发布
	PUBLISHED(1),
	// 已经结束
	FINISHED(2);
	private int qNRStatus;

	QNRStatus(int type) {
		this.qNRStatus = type;
	}

	public int toInt() {
		return qNRStatus;
	}

	@Override
	public String toString() {
		return String.valueOf(this.qNRStatus);
	}

}
