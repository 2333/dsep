package com.dsep.service.queue.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.dsep.service.queue.MessageStatus;
import com.dsep.service.queue.QueueMessage;
import com.dsep.service.queue.QueueService;
import com.dsep.util.GUID;
import com.dsep.entity.MessageEntity;
import com.dsep.dao.dsepmeta.base.MessageDao;

public class QueueServiceImpl implements QueueService {
	
	private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("queueDestination")
    private Destination destination;
    
    @Autowired
    @Qualifier("messageDao")
    private MessageDao messageDao;

	@Override
	public void sendMessage(QueueMessage message) {
		String _messageId = GUID.get();
		message.setMessageId(_messageId);
		
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setId(_messageId);
		messageEntity.setCreatedAt(new Date());
		messageEntity.setCompletedAt(null);
		messageEntity.setRelatedId(message.getIdentifier());
		messageEntity.setStatus("0");
		messageEntity.setType("0");
		messageDao.save(messageEntity);
		messageDao.flush();

		jmsTemplate.send(this.getDestination(), this.getMessageCreator(message));
	}
	
	//need override
	public Destination getDestination() {
		return this.destination;
	}
	
	//need override
	//abstract public MessageCreator getMessageCreator();
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	
	@Resource
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	// implements get message creator
	/**
	 * message creator for Hashmap<string, string>
	 */
	protected MessageCreator getMessageCreator(final QueueMessage queueMessage) {
		return new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
				
				/*Map<String, String> map = queueMessage.getParameters();
				Iterator<Entry<String, String>> iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					message.setString(entry.getKey(), entry.getValue());
					//System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
				}*/
				message.setObject("parameters", queueMessage.getParameters());
				message.setString("_identifier", queueMessage.getIdentifier());
				message.setString("_messageId", queueMessage.getMessageId());
				message.setString("_beanId", queueMessage.getBeanId());
				return message;
			}
		};
	}
	
	@Override
	public MessageStatus getStatus(String _identifier) {
		return MessageStatus.getMessageStatus(messageDao.getStatusByIdentifier(_identifier));
	}

}
