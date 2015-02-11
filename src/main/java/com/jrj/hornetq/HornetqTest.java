package com.jrj.hornetq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.TextMessage;

public class HornetqTest {

	/**
	 * @param args
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws JMSException, InterruptedException {
		try {
			new HornetQSend().send();
			new Thread().sleep(5000);
			Message message = new HornetQRecive().receive();
			System.out.println(message);
			if (message instanceof TextMessage) {
				System.out.println(((TextMessage) message).getText());
			} else if (message instanceof MapMessage) {
				MapMessage mmsg = (MapMessage) message;
				System.out.println(mmsg.getLong("acctId") + ", " + mmsg.getString("side") + ", "
						+ mmsg.getString("symbol") + ", " + mmsg.getDouble("shares"));
			} else {
				throw new IllegalStateException("Message Type Not Supported");

			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}