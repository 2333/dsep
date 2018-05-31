package com.dsep.unitTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dsep.dao.dsepmeta.base.MessageDao;
import com.dsep.service.queue.QueueService;
import com.dsep.service.queue.QueueMessage;
import com.dsep.service.queue.QueueMessageHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/config/spring-common.xml",
		"file:WebContent/WEB-INF/config/spring-dao.xml",
		"file:WebContent/WEB-INF/config/spring-myservice.xml",
		"file:WebContent/WEB-INF/config/spring-transaction-annotation.xml",
		"file:WebContent/WEB-INF/config/spring-util.xml",
		"file:WebContent/WEB-INF/config/spring-jms.xml"})
public class JmsTest implements QueueMessageHandler{

    @Autowired
    @Qualifier("queueService")
    private QueueService queue;
    
    @Autowired
    @Qualifier("messageDao")
    private MessageDao messageDao;
      
    @Test  
    public void testSend() throws InterruptedException {  
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("unitId", "123");
    	map.put("discId", "1233");
    	map.put("userId", "2333");
    	QueueMessage message = new QueueMessage("123", "testQueueHandler", map);
        queue.sendMessage(message);    
        Thread.sleep(10000);
    }
    
    @Test
    public void testGetStatusByIdentifier() {
    	System.out.println("In testGetStatusByIdentifier");
    	String status = null;
    	try {
    		status = messageDao.getStatusByIdentifier("123");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println("In testGetStatusByIdentifier, status: " + status);
    }

	@Override
	public Boolean handleMessage(QueueMessage message) {
		// TODO Auto-generated method stub
		System.out.println("In testjms:    " + message.getBeanId());
		return null;
	}
    
	
}
