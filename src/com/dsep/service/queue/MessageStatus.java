package com.dsep.service.queue;



public enum MessageStatus {
	ERROR("-1"),
	UNHANDLED("0"),
	STARTED("1"),
	COMPLETED("2");
	
	final private String status;
	private MessageStatus(String status){
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
	public static MessageStatus getMessageStatus(String _status) {
		for(MessageStatus status: MessageStatus.values()){
			if(status.getStatus().equals(_status)) return status;
		}
		return MessageStatus.ERROR;
	}

}
