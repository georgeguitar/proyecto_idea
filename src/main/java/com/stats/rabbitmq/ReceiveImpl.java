package com.stats.rabbitmq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.stats.dao.IdeaDAOImpl;
import com.stats.dao.VoteDAOImpl;
import com.stats.model.Idea;
import com.stats.model.MensajeJson;

@Service
public class ReceiveImpl implements Receive {
	
	@Autowired
	private VoteDAOImpl voteDAOImpl;

	@Autowired
	private IdeaDAOImpl ideaDAOImpl;
	
	private final String QUEUE_NAME = "ESTADISTICA";
	
	Channel channel;
	
	public ReceiveImpl(Channel _channel) {
		channel = _channel;
	}
	
	
	
	public void escuchar() throws IOException, TimeoutException {
	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	
	    Consumer consumer = new DefaultConsumer(channel) {
	      @Override
	      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
	          throws IOException {
	        String message = new String(body, "UTF-8");
		    System.out.println("Mensaje recibido: " + message);
		    
		    //{ "operation": "BORRAR_VOTOS", "idIdea": "5aeccf443ff59c74b0446108" }
		    Gson gson = new Gson(); // Or use new GsonBuilder().create();
		    MensajeJson mensajeJson = gson.fromJson(message, MensajeJson.class);
		    
//	        String[] parts = message.split(",");
//	        String mensaje = parts[0];
	        
	        if ("OBTENER_VOTOS".equals(mensajeJson.getOperation())) {
		        String idIdea = mensajeJson.getIdIdea();
		        System.out.println(" [x] Received '" + message + "'");
		        System.out.println(voteDAOImpl.obtenerVotos(idIdea));
	        }
	        if ("ACTUALIZAR_IDEAS".equals(mensajeJson.getOperation())) {
		        System.out.println(" [x] Received '" + message + "'");
	        	List<Idea> listaIdeas = new ArrayList<Idea>();
	        	listaIdeas = ideaDAOImpl.listar();
	        	for (Idea idea : listaIdeas) {
	        		Integer cantVotosPorIdea = voteDAOImpl.obtenerVotos(idea.getId());
	        		if (cantVotosPorIdea != idea.getVotes()) {
		        		idea.setVotes(cantVotosPorIdea);
		        		ideaDAOImpl.guardarActualizar(idea);
				        System.out.println("votos actualizados, idIdea: " + idea.getId());

	        		}
	        	}
	        }
	        if ("BORRAR_VOTOS".equals(mensajeJson.getOperation())) {
		        String idIdea = mensajeJson.getIdIdea();
		        System.out.println(" [x] Received '" + message + "'");
		        if (voteDAOImpl.borrarVotos(idIdea)) {
			        System.out.println(idIdea + " borrado con éxito de la tabla votes.");		        	
		        } else {
			        System.out.println("¡No se pudo borrar!");
		        }
	        }
	      }
	    };
	    channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
