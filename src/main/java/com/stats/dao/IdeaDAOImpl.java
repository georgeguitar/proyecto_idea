package com.stats.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.stats.model.Idea;

@Service
public class IdeaDAOImpl implements IdeaDAO {

//	DBCollection collection;
	MongoCollection<Document> collection;
	
	public IdeaDAOImpl(MongoDatabase db) {
		  collection = db.getCollection("ideas");
		  System.out.println("collection: " + collection);
	}
	
	@Override
	public void actualizarIdeas() {

	}
	
	@Override
	public void guardarActualizar(Idea idea) {
		if (idea.getId().isEmpty()) {
			Document object = idea.toDocument();
	        collection.insertOne(object);
		} else {
//			DBObject find = new BasicDBObject("_id", new ObjectId(idea.getId())); 
//			DBObject updated = new BasicDBObject(idea.toDBObject());
//			collection.update(find, updated);
			
			Document document = collection.find(eq("_id", new ObjectId(idea.getId()))).first();
			
			Bson filter = document;
			Bson newValue = new Document("votes", idea.getVotes());
			Bson updateOperationDocument = new Document("$set", newValue);
			collection.updateOne(filter, updateOperationDocument);
			
//			collection.updateOne(document, idea.toDocument());
		}
	}
	
	@Override
	public void borrar(String idIdea) {
//		ObjectId id= new ObjectId(idIdea);
//		BasicDBObject obj = new BasicDBObject();
//		obj.append("_id", id);
//		BasicDBObject query = new BasicDBObject(obj);
//		query.putAll((BSONObject)query);
//		
//		collection.remove(query);
		
		
		collection.deleteOne(new Document("_id", new ObjectId(idIdea)));
	}
	
//	@Override
//	public List<Idea> buscar(String llave, String valor) {
//		BasicDBObject obj = new BasicDBObject();        
//		obj.append(llave, valor);
//		BasicDBObject query = new BasicDBObject(obj);
//		query.putAll((BSONObject)query);
//
//		List<Idea> lista = new ArrayList<Idea>();
//		DBCursor cursor = collection.find(query);
//		try {
//			while (cursor.hasNext()) {
//				Idea idea = new Idea((BasicDBObject) cursor.next());
//				lista.add(idea);
//			}
//		} finally {
//			cursor.close();
//		}		
//		
//		return lista;
//	}
	
	@Override
	public List<Idea> listar() {
		List<Idea> listaIdeas = new ArrayList<Idea>();
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
			while (cursor.hasNext()) {
				Document tempDocument = cursor.next();
				Idea idea = new Idea(tempDocument);
				listaIdeas.add(idea);
			}
		} finally {
			cursor.close();
		}
		return listaIdeas;
	}
	
	@Override
	public Idea get(String idIdea) {
//		ObjectId id= new ObjectId(idIdea);
//		BasicDBObject obj = new BasicDBObject();        
//		obj.append("id", id);
//		BasicDBObject query = new BasicDBObject(obj);
//		query.putAll((BSONObject)query);
//		
//		DBObject dbObj = collection.findOne(query);
//		Idea idea = new Idea((BasicDBObject) dbObj);
//		
//		return idea; 
		
		Idea idea = null;
		Document document = collection.find(eq("_id", new ObjectId(idIdea))).first();
		if (document != null) {
			idea = new Idea(document); 
		}
		return idea;
	}
}
