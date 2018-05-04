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

@Service
public class ConexionMongo {

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
}
