package com.dsep.common.exception;

public class TeachManageException extends BusinessException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messageType;//请求类型json\text
	public TeachManageException(){
		super();
	}
	public TeachManageException(String message){
		super(message);
	}
	public TeachManageException(String message,String messageType){
		super(message);
		setMessageType(messageType);
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
}
