package com.dsep.util.project;

public enum ApplyItemStatus {
	// 教师保存没有提交
	NOT_COMMIT(0),
	//已提交
	COMMIT(1),
	// 教师已经提交，学校未审批
	NOT_REVIEW(2),
	// 教师已经提交，学校已经审批
	REVIEW(3),
	// 审批未通过，退回教师
	REJECT(4),
	// 审批通过，立项
	APPROVAL(5);
	
	private int status;

	ApplyItemStatus(int type) {
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
