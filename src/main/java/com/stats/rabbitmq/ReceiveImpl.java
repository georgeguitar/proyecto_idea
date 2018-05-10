package com.stats.rabbitmq;

import java.io.IOException;
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
import com.stats.model.MensajeJson;

@Service
public class ReceiveImpl implements Receive {
	private final String QUEUE_NAME = "ESTADISTICA";
	
	@Autowired
	private VoteDAOImpl voteDAOImpl;

	@Autowired
	private IdeaDAOImpl ideaDAOImpl;
	
	Channel channel;
	
	public ReceiveImpl(Channel _channel) {
		channel = _channel;
	}
	
	public void escuchar() throws IOException, TimeoutException {
	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    System.out.println(" [*] Esperando por mensajes.");
	
	    Consumer consumer = new DefaultConsumer(channel) {
	      @Override
	      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
	          throws IOException {
	        String message = new String(body, "UTF-8");

		    Gson gson = new Gson();
		    MensajeJson mensajeJson = gson.fromJson(message, MensajeJson.class);
		    
	        /** 
	         * Formato:
	         * "{ 'accion': 'OBTENER_VOTOS', 'idIdea': '5aefc8f596d58b0004f18cc9'}"
	         * **/
	        if ("OBTENER_VOTOS".equals(mensajeJson.getAccion())) {
		        String idIdea = mensajeJson.getIdIdea();
			    System.out.println(" [x] Mensaje recibido: '" + mensajeJson.getAccion() + "'");
		        System.out.println(" [-] Cantidad de votos: '" + voteDAOImpl.obtenerVotos(idIdea));
	        }
	        /** 
	         * Formato:
	         * "{ 'accion': 'ACTUALIZAR_IDEAS'}"
	         * **/
	        if ("ACTUALIZAR_IDEAS".equals(mensajeJson.getAccion())) {
			    System.out.println(" [x] Mensaje recibido: '" + mensajeJson.getAccion() + "'");
			    ideaDAOImpl.actualizarIdeas(voteDAOImpl);
	        }
	        /** 
	         * Formato:
	         * "{ 'accion': 'BORRAR_VOTOS', 'idUsuario': '8' }"
	         * **/
	        if ("BORRAR_VOTOS".equals(mensajeJson.getAccion())) {
			    System.out.println(" [x] Mensaje recibido: '" + mensajeJson.getAccion() + "'");
			    String idUsuario = mensajeJson.getIdUsuario();
			    voteDAOImpl.borrarVotos(idUsuario);
			    ideaDAOImpl.actualizarIdeas(voteDAOImpl);
		        System.out.println(" [-] BORRAR_VOTOS: " + "ejecutado con éxito.");
	        }
	        /** 
	         * Formato:
	         * "{ 'accion': 'VOTAR_IDEA', 'idUsuario': '8', 'idIdea': '5aefc8f596d58b0004f18cc9'}"
	         * **/
	        if ("VOTAR_IDEA".equals(mensajeJson.getAccion())) {
		        String idIdea = mensajeJson.getIdIdea();
		        String idUsuario = mensajeJson.getIdUsuario();
			    System.out.println(" [x] Mensaje recibido: '" + mensajeJson.getAccion() + "'");
		        voteDAOImpl.insertarVoto(idUsuario, idIdea);
			    ideaDAOImpl.actualizarIdeas(voteDAOImpl);
		        System.out.println(" [-] VOTAR_IDEA: " + "ejecutado con éxito.");
	        }
	        /** 
	         * Formato:
	         * "{ 'accion': 'QUITAR_IDEA', 'idUsuario': '8', 'idIdea': '5aefc8f596d58b0004f18cc9'}"
	         * **/
	        if ("QUITAR_IDEA".equals(mensajeJson.getAccion())) {
		        String idIdea = mensajeJson.getIdIdea();
		        String idUsuario = mensajeJson.getIdUsuario();    
			    System.out.println(" [x] Mensaje recibido: '" + mensajeJson.getAccion() + "'");
		        voteDAOImpl.eliminarVoto(idUsuario, idIdea);
			    ideaDAOImpl.actualizarIdeas(voteDAOImpl);
		        System.out.println(" [-] QUITAR_IDEA: " + "ejecutado con éxito.");
	        }
	      }
	    };
	    channel.basicConsume(QUEUE_NAME, true, consumer);
	}

}
