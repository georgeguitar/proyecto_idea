package com.stats.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Service
public class ReceiveImpl implements Receive {
	private final String QUEUE_NAME = "hello";
	
	 Channel channel;
	
	public ReceiveImpl(Channel _channel) {
		channel = _channel;
	}
	
	public void escuchar() throws IOException, TimeoutException {
/*
//		private final static String QUEUE_NAME = "hello";
	final String QUEUE_NAME = "hello";
	
    ConnectionFactory factory = new ConnectionFactory();
//    factory.setHost("localhost");
//    factory.setHost("amqp://wxlrebpd:cVgfbjWvPcTGZ_VlfkMLCcfn6TQhtxAL@llama.rmq.cloudamqp.com/wxlrebpd");
    factory.setHost("llama.rmq.cloudamqp.com");	    
    factory.setPassword("cVgfbjWvPcTGZ_VlfkMLCcfn6TQhtxAL");
    factory.setUsername("wxlrebpd");
//    factory.setPort(port);
    factory.setVirtualHost("wxlrebpd");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
*/
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
