package com.stats.conexiones;

import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

@Service
public class ConexionMongo {
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
}
