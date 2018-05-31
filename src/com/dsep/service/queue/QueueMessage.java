package com.dsep.service.queue;


import java.util.Map;

public class QueueMessage {
	//private variables
	private String identifier; //guid
	private String messageId; //guid不用管
	private String beanId;//用来处理消息的对象ID，
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	private Map<String, String> parameters;//需要传的参数。
	public QueueMessage(String _identifier, String _messageId, String _beanId,
			Map<String, String> parameters) {
		this.identifier = _identifier;
		this.messageId = _messageId;
		this.beanId = _beanId;
		this.parameters = parameters;
	}
	
	public QueueMessage(String _identifier, String _beanId, Map<String, String> parameters) {
		this.identifier = _identifier;
		this.beanId = _beanId;
		this.parameters = parameters;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public Map<String, String> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
}
