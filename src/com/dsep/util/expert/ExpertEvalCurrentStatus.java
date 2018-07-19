package com.dsep.util.expert;

public enum ExpertEvalCurrentStatus {
	NotMailed(0), 
	Mailed(1),     
	Remailed(2),    
	Confirmed(3), 
	Refused(4),   
	Evaluating(5), 
	Finished(6);

	private int currentStatus;

	ExpertEvalCurrentStatus(int status) {
		this.currentStatus = status;
	}

	public int toInt() {
		return currentStatus;
	}

	@Override
	public String toString() {
		return String.valueOf(this.currentStatus);
	}
}
