package com.dsep.util.survey;

public enum SurveyUserType {
	UNDER_GRADUATE(0), 
	GRADUATE(1),
	UNIT(2);
	
	private int userType;

	SurveyUserType(int type) {
		this.userType = type;
	}

	public int toInt() {
		return userType;
	}
	
	 @Override
     public String toString() {
         return String.valueOf(this.userType);
     } 
}
