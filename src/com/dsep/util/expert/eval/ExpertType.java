package com.dsep.util.expert.eval;

public enum ExpertType {
	// 学术专家
	ACADEMIC(0),
	// 行业专家
	INDUSTRY(1);
	
	private int expertType;

	ExpertType(int type) {
		this.expertType = type;
	}

	public int toInt() {
		return expertType;
	}
	
	 @Override
     public String toString() {
         return String.valueOf(this.expertType);
     } 
}
