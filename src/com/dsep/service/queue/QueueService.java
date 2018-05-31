package com.dsep.service.queue;
import javax.jms.*;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * queue base interface
 * @author sanpili
 * @since 2014-06-16
 *
 */
@Transactional(propagation=Propagation.SUPPORTS)
public interface QueueService {
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void sendMessage(final QueueMessage message);
	public MessageStatus getStatus(String _identifier);
}
