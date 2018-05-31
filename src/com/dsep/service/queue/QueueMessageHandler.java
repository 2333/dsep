package com.dsep.service.queue;

public interface QueueMessageHandler {
	public Boolean handleMessage(QueueMessage message);
}
