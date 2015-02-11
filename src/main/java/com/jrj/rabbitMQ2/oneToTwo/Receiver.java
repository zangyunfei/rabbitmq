package com.jrj.rabbitMQ2.oneToTwo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Receiver {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws java.io.IOException,
			java.lang.InterruptedException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. ");
		QueueingConsumer consumer = new QueueingConsumer(channel);

		boolean autoAck = true; // ��Ϣȷ�� Ĭ���� false ��ȷ��ִ�����
		channel.basicConsume("hello", autoAck, consumer);

		while (true) {
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				System.out.println(" [x] Received '" + message + "'");
				doWork(message);
				System.out.println(" [x] Done");

			}
		}
	}

	private static void doWork(String task) throws InterruptedException {
		Thread.sleep(1000);
	}
}