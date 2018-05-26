package com.stats.servicio;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoDatabase;
import com.rabbitmq.client.Channel;
import com.stats.conexiones.ConexionMongo;
import com.stats.conexiones.ConexionMySql;
import com.stats.conexiones.ConexionRabbitMQ;
import com.stats.dao.IdeaDAO;
import com.stats.dao.IdeaDAOImpl;
import com.stats.dao.VoteDAO;
import com.stats.dao.VoteDAOImpl;
import com.stats.rabbitmq.Receive;
import com.stats.rabbitmq.ReceiveImpl;


@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages="com.stats")
public class ServicioStatsApplication {
	private static Logger logger = LogManager.getLogger(ServicioStatsApplication.class);
	
	public static void main(String[] args) throws IOException, TimeoutException {
		logger.info("- Logger: Iniciando Spring Boot..");
		SpringApplication.run(ServicioStatsApplication.class, args);
	}
	
	@Bean
	public MongoDatabase getDataSourceMongoDB() throws UnknownHostException {
		ConexionMongo conexion = new ConexionMongo();
		MongoDatabase db = conexion.CrearConexion();
		
		return db;
	}
	
	public IdeaDAO getIdeaDAO() throws UnknownHostException {
		return new IdeaDAOImpl(getDataSourceMongoDB());
	}
	
	@Bean
	public Connection getDataSourceMySql() throws UnknownHostException {
		ConexionMySql conexion = new ConexionMySql();
		Connection db = conexion.CrearConexion();
		
		return db;
	}

	public VoteDAO getVoteDAO() throws UnknownHostException {
		return new VoteDAOImpl(getDataSourceMySql());
	}
	
	
	@Bean	
	public Channel getConecionRabbitMQ() throws IOException, TimeoutException {
		ConexionRabbitMQ conexionRabbitMQ = new ConexionRabbitMQ();
		Channel channel = conexionRabbitMQ.CrearConexion();
		
		return channel;
	}
	

	public Receive getRabbitMQ() throws IOException, TimeoutException {
		return new ReceiveImpl(getConecionRabbitMQ());
	}
}
