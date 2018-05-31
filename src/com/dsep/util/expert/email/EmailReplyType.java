package com.dsep.util.expert.email;

public enum EmailReplyType {
	OK(0), 
	ValidateException(1),
	AlreadyRegisted(2),
	AlreadyRefused(3),
	OutOfData(4);
	
	private int emailReplyType;

	EmailReplyType(int type) {
		this.emailReplyType = type;
	}

	public int toInt() {
		return emailReplyType;
	}
	
	 @Override
     public String toString() {
         return String.valueOf(this.emailReplyType);
     } 
}
