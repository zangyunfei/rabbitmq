package com.jrj.hornetq;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;




public abstract class HornetQBase {
	TransportConfiguration transportConfiguration;
	ConnectionFactory connectionFactory;
	static Connection connection;
	static Session session;

	public HornetQBase() throws JMSException {
		/*
		 * transportConfiguration = new
		 * TransportConfiguration(NettyConnectorFactory.class.getName()); cf =
		 * (ConnectionFactory)
		 * HornetQJMSClient.createConnectionFactoryWithoutHA(
		 * JMSFactoryType.CF,transportConfiguration);
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(org.hornetq.core.remoting.impl.netty.TransportConstants.HOST_PROP_NAME, "172.16.6.236");
		map.put(org.hornetq.core.remoting.impl.netty.TransportConstants.PORT_PROP_NAME, 5445);
		transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName(), map);
		connectionFactory = (ConnectionFactory) HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF,
				transportConfiguration);
		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

}