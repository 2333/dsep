package com.dsep.common.exception;

public class CollectBusinessException extends BusinessException{
	/**
	 * 信息类型,text为普通文本；json为json类型
	 */
	private String messageType;
	public CollectBusinessException() {
		// TODO Auto-generated constructor stub
		super();
	}
	public CollectBusinessException(String message)
	{
		super(message);
	}
	public CollectBusinessException(String message,String type)
	{
		super(message);
		setMessageType(type);
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
}
