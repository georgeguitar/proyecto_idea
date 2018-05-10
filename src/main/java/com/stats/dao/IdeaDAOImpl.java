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

	MongoCollection<Document> collection;
	
	public IdeaDAOImpl(MongoDatabase db) {
		  collection = db.getCollection("ideas");
		  System.out.println("collection: " + collection);
	}
	
	@Override
	public void actualizarIdeas(VoteDAOImpl voteDAOImpl) {
    	List<Idea> listaIdeas = new ArrayList<Idea>();
    	listaIdeas = listar();
    	for (Idea idea : listaIdeas) {
    		Integer cantVotosPorIdea = voteDAOImpl.obtenerVotos(idea.getId());
    		if (cantVotosPorIdea != idea.getVotes()) {
        		idea.setVotes(cantVotosPorIdea);
        		guardarActualizar(idea);
    		}
    	}
	}
	
	@Override
	public void guardarActualizar(Idea idea) {
		if (idea.getId().isEmpty()) {
			Document object = idea.toDocument();
	        collection.insertOne(object);
		} else {
			Document document = collection.find(eq("_id", new ObjectId(idea.getId()))).first();
			
			Bson filter = document;
			Bson newValue = new Document("votes", idea.getVotes());
			Bson updateOperationDocument = new Document("$set", newValue);
			collection.updateOne(filter, updateOperationDocument);
		}
	}
	
	@Override
	public void borrar(String idIdea) {
		collection.deleteOne(new Document("_id", new ObjectId(idIdea)));
	}
	
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
		Idea idea = null;
		Document document = collection.find(eq("_id", new ObjectId(idIdea))).first();
		if (document != null) {
			idea = new Idea(document); 
		}
		return idea;
	}
}
