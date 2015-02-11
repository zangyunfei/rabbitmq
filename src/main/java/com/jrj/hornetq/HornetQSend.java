package com.jrj.hornetq;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.hornetq.api.jms.HornetQJMSClient;

public class HornetQSend extends HornetQBase {

	public HornetQSend() throws JMSException {
		super();
	}

	public void send() throws JMSException {
		try {
			Queue orderQueue = HornetQJMSClient.createQueue("ExpiryQueue");
			MessageProducer producer = session.createProducer(orderQueue);
			connection.start();
			TextMessage message = session.createTextMessage("This is an order");
			producer.send(message);
			System.out.println("send message" + message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

	}

}
