/**
 * Realizado por:
 * Claudia Salazar Gonzales
 * Juan Dirceu Navarro Arias
 * Luis Fernando Numa Navarro Arias
 * 
 */


package com.stats.conexiones;

import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;

@Service
public class ConexionMongo {
/*
	private final static String HOST = "localhost";
	private final static int PORT = 27017;
	
	MongoClient mongoClient;
	DB db;

	public DB CrearConexion() throws UnknownHostException {
		mongoClient = new MongoClient(HOST, PORT);

		db = mongoClient.getDB("DocumentosDB");
		
		return db;
	}
	
	public void CerrarConexion() {
		mongoClient.close();
	}
	*/
	
	public DB CrearConexion() throws UnknownHostException {
	    String webPort = System.getenv("PORT");
	    if(webPort == null || webPort.isEmpty()) {
	        webPort = "8080";
	    }
   
	    MongoClientURI uri = new MongoClientURI(System.getenv("MONGOHQ_URL"));
	    MongoClient mongoClient = new MongoClient(uri.getURI(), Integer.valueOf(webPort));
	    
	    String dbname = uri.getDatabase();
	    DB db = mongoClient.getDB(dbname);
		
	    MongoCredential credential = MongoCredential.createCredential(uri.getUsername(),dbname,uri.getPassword());
	    MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
	    
		return db;
	}
}
