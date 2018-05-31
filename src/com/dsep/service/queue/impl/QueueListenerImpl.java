package com.dsep.service.queue.impl;

import com.dsep.util.SpringContext;
import com.dsep.dao.dsepmeta.base.MessageDao;
import com.dsep.service.queue.MessageStatus;
import com.dsep.service.queue.QueueMessageHandler;

import java.util.HashMap;
import java.util.Map;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dsep.service.queue.QueueMessage;

@Transactional(propagation = Propagation.SUPPORTS)
public class QueueListenerImpl implements MessageListener {

	@Autowired
	@Qualifier("messageDao")
	private MessageDao messageDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void onMessage(Message message) {
		messageDao.flush();
		MapMessage mapMessage = (MapMessage) message;
		System.out.println("In MessageListener@onMessage.....");
		try {
			String _identifier = mapMessage.getString("_identifier");
			String _messageId = mapMessage.getString("_messageId");
			String _beanId = mapMessage.getString("_beanId");
			System.out.println(_beanId);

			QueueMessageHandler handler = (QueueMessageHandler) SpringContext
					.getBean(_beanId);
			@SuppressWarnings("unchecked")
			Map<String, String> parameters = (Map<String, String>) mapMessage
					.getObject("parameters");
			QueueMessage queueMessage = new QueueMessage(_identifier,
					_messageId, _beanId, parameters);
			// begin handle message
			HashMap<String, Object> columns = new HashMap<String, Object>();
			columns.put("status", "1");
			messageDao.updateColumnBySql(_messageId, columns);
			messageDao.flush();
			handler.handleMessage(queueMessage);

			// end handle message
			columns.remove("status");
			columns.put("status", "2");
			messageDao.updateColumnBySql(_messageId, columns);
			messageDao.flush();
			System.err.println("消息内容是：" + _identifier + " ," + _messageId + ","
					+ _beanId);
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
