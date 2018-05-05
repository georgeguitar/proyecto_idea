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
import com.mongodb.MongoClientURI;

@Service
public class ConexionMongo {
/*
	private final static String HOST = "localhost";
	private final static int PORT = 13700;
	
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

	MongoClient mongoClient;
	DB db;

	public DB CrearConexion() throws UnknownHostException {
        MongoClientURI uri  = new MongoClientURI("mongodb://super18:super18@ds113700.mlab.com:13700/notable");
//        MongoClientURI uri  = new MongoClientURI(System.getenv("MONGOHQ_URL")); 
        MongoClient client = new MongoClient(uri);
        db = client.getDB(uri.getDatabase());
		
		return db;
	}
	
/*	
	public DB CrearConexion() throws UnknownHostException {
		System.out.println(System.getenv("PATH"));

		
	    String webPort = System.getenv("PORT");
	    if(webPort == null || webPort.isEmpty()) {
	        webPort = "8080";
	    }
   
	    String url = "mongodb://super18:super18@ds113700.mlab.com";
	    
	    MongoClientURI uri = new MongoClientURI(System.getenv("MONGOHQ_URL"));
//	    MongoClient mongoClient = new MongoClient(uri.getURI(), Integer.valueOf(webPort));
	    MongoClient mongoClient = new MongoClient(url, Integer.valueOf(webPort));
	    
	    String dbname = uri.getDatabase();
	    DB db = mongoClient.getDB(dbname);
		
	    MongoCredential credential = MongoCredential.createCredential(uri.getUsername(),dbname,uri.getPassword());
	    MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
	    
		return db;
	}
	*/
}
