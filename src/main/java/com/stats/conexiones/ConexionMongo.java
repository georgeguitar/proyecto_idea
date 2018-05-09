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

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

@Service
public class ConexionMongo {
/*
	private final static String HOST = "localhost";
	private final static int PORT = 13700;
	
	MongoClient mongoClient;
	DB db;

	public DB CrearConexion() throws UnknownHostException {
		mongoClient = new MongoClient(HOST, PORT);

		db = mongoClient.getDB("prueba");
		
		return db;
	}
	
	public void CerrarConexion() {
		mongoClient.close();
	}
*/
	
	public MongoDatabase CrearConexion() throws UnknownHostException {
		MongoClient mongoClient = null;
		MongoDatabase db = null;
		
		try {
			MongoClientURI uri  = new MongoClientURI("mongodb://super2018:super2018@ds117250.mlab.com:17250/ideas");
		  mongoClient = new MongoClient(uri);
		  
		  // New way to get database
		  db = mongoClient.getDatabase("ideas");
		  
		} catch (Exception e) {
		  e.printStackTrace();
		}
		
		return db;
	}
	

/*	
	MongoClient mongoClient;
	DB db;

	public DB CrearConexion() throws UnknownHostException {
//        MongoClientURI uri  = new MongoClientURI("mongodb://super18:super18@ds113700.mlab.com:13700/notable");
        MongoClientURI uri  = new MongoClientURI("mongodb://super18:super18@ds117250.mlab.com:17250/ideas");
//        MongoClientURI uri  = new MongoClientURI(System.getenv("MONGOHQ_URL")); 
        MongoClient client = new MongoClient(uri);
        db = client.getDB(uri.getDatabase());
		
	    MongoCredential credential = MongoCredential.createCredential(uri.getUsername(),uri.getDatabase(),uri.getPassword());
	    MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
        
		return db;
	}
	*/
}
