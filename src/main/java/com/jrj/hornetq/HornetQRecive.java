package com.jrj.hornetq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;

import org.hornetq.api.jms.HornetQJMSClient;

public class HornetQRecive extends HornetQBase {
	public HornetQRecive() throws JMSException {
		super();
	}

	public Message receive() throws JMSException {
		MessageConsumer consumer;
		try {
			Queue orderQueue = HornetQJMSClient.createQueue("ExpiryQueue");
			consumer = session.createConsumer(orderQueue);
			connection.start();
			return consumer.receive();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return null;

	}
}
