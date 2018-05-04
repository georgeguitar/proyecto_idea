package com.stats.servicio;

import java.net.UnknownHostException;
import java.sql.Connection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DB;
import com.stats.conexiones.ConexionMongo;
import com.stats.conexiones.ConexionMySql;
import com.stats.dao.DocumentDAOImpl;
import com.stats.dao.DocumentoDAO;
import com.stats.dao.DocumentoMySqlDAO;
import com.stats.dao.DocumentoMySqlDAOImpl;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages="com.stats")
public class ServicioStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioStatsApplication.class, args);
	}

	@Bean
	public DB getDataSourceMongoDB() throws UnknownHostException {
		ConexionMongo conexion = new ConexionMongo();
		DB db = conexion.CrearConexion();
		
		return db;
	}

	public DocumentoDAO getContactDAO() throws UnknownHostException {
		return new DocumentDAOImpl(getDataSourceMongoDB());
	}
	
	@Bean
	public Connection getDataSourceMySql() throws UnknownHostException {
		ConexionMySql conexion = new ConexionMySql();
		Connection db = conexion.CrearConexion();
		
		return db;
	}

	public DocumentoMySqlDAO getContact1DAO() throws UnknownHostException {
		return new DocumentoMySqlDAOImpl(getDataSourceMySql());
	}
}
