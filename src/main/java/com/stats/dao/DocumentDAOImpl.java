/**
 * Demo realizado por:
 * Claudia Salazar Gonzales
 * Juan Dirceu Navarro Arias
 * Luis Fernando Numa Navarro Arias
 * 
 * Diplomado Software Libre versión Sucre
 * noviembre 2016
 */



package com.stats.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.stats.model.Documento;

@Service
public class DocumentDAOImpl implements DocumentoDAO {
	
	DBCollection collection;
	
	public DocumentDAOImpl(DB db) {
		this.collection = db.getCollection("Documentos");
	}
	
	@Override
	public Documento get(String idDocumento) {
		ObjectId id= new ObjectId(idDocumento);
		BasicDBObject obj = new BasicDBObject();        
		obj.append("_id", id);        
		BasicDBObject query = new BasicDBObject(obj);
		query.putAll((BSONObject)query);
		
		DBObject dbObj = collection.findOne(query);
		Documento documento = new Documento((BasicDBObject) dbObj);
		
		return documento; 
	}

	@Override
	public List<Documento> listar() {
		List<Documento> listaDocumentos = new ArrayList<Documento>();
		
		DBCursor cursor = collection.find();
		try {
			while (cursor.hasNext()) {
				Documento documento = new Documento((BasicDBObject) cursor.next());
				listaDocumentos.add(documento);
			}
		} finally {
			cursor.close();
		}
		
		return listaDocumentos;
	}
	
	@Override
	public void guardarActualizar(Documento documento) {
		if (documento.getId().isEmpty()) {
			collection.insert(documento.toDBObjectDocumento());
		} else {
			DBObject find = new BasicDBObject("_id", new ObjectId(documento.getId())); 
			DBObject updated = new BasicDBObject(documento.toDBObjectDocumento());
			collection.update(find, updated);
		}
	}
	
	@Override
	public void borrar(String idDocumento) {
		ObjectId id= new ObjectId(idDocumento);
		BasicDBObject obj = new BasicDBObject();
		obj.append("_id", id);
		BasicDBObject query = new BasicDBObject(obj);
		query.putAll((BSONObject)query);
		
		collection.remove(query);
	}

	@Override
	public List<Documento> buscar(String llave, String valor) {
		BasicDBObject obj = new BasicDBObject();        
		obj.append(llave, valor);
		BasicDBObject query = new BasicDBObject(obj);
		query.putAll((BSONObject)query);

		List<Documento> listaDocumentos = new ArrayList<Documento>();
		DBCursor cursor = collection.find(query);
		try {
			while (cursor.hasNext()) {
				Documento documento = new Documento((BasicDBObject) cursor.next());
				listaDocumentos.add(documento);
			}
		} finally {
			cursor.close();
		}		
		
		return listaDocumentos;
	}
	
}
