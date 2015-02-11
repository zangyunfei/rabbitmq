package com.jrj.rabbitMQ;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class Test {

	public Test() throws Exception {
		QueueConsumer consumer = new QueueConsumer("queue");
		Thread consumerThread = new Thread(consumer);
		consumerThread.start(); // ������
		Producer producer = new Producer("queue"); // ������
		for (int i = 0; i < 10000; i++) {
			HashMap message = new HashMap();
			message.put("message number", i);
			Thread.sleep(1000);
			producer.sendMessage(message);
			System.out.println("Message Number " + i + " sent.");
		}
	}

	/**
	 * @param args
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		String path = "C:/Users/a.zip/test.pk";
		int mark = path.indexOf(".zip");
		String zipPath = path.substring(0, mark + 4);
		String filename = path.substring(mark + 5);
		System.out.println(zipPath);
		System.out.println(filename);
	}
}
