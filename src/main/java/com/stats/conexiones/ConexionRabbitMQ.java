package com.stats.conexiones;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConexionRabbitMQ {
	public Channel CrearConexion() throws IOException, TimeoutException {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("llama.rmq.cloudamqp.com");	    
	    factory.setPassword("cVgfbjWvPcTGZ_VlfkMLCcfn6TQhtxAL");
	    factory.setUsername("wxlrebpd");
//	    factory.setPort(port);
	    factory.setVirtualHost("wxlrebpd");  
	    
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    return channel;
	}
}
