package com.dsep.util.expert.eval;

public enum EvalBatchStatus {
	// 创建完成，未遴选任何专家
	NOT_SELECTED(0),
	// 已经遴选了专家
	SELECTED(1),
	// 已经发送邮件通知，专家处于可以打分状态
	MSG_SEND_AND_QS_GENERATED(2),
	// 已经超过了结束时间
	EXPIRED(3);

	private int status;

	EvalBatchStatus(int type) {
		this.status = type;
	}

	public int toInt() {
		return status;
	}

	@Override
	public String toString() {
		return String.valueOf(this.status);
	}
}
