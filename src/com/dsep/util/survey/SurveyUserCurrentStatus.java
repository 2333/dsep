package com.dsep.util.survey;

public enum SurveyUserCurrentStatus {
	NotMailed(0), 
	Mailed(1),     
	Remailed(2),    
	Confirmed(3), 
	Refused(4),   
	Evaluating(5), 
	Finished(6);

	private int currentStatus;

	SurveyUserCurrentStatus(int status) {
		this.currentStatus = status;
	}

	public int getIndex() {
		return currentStatus;
	}

	@Override
	public String toString() {
		return String.valueOf(this.currentStatus);
	}
}
