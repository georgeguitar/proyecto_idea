package com.stats.model;

import org.bson.Document;

import com.mongodb.BasicDBObject;

public class Idea {
	String id;
	String idea;
	String proposerId;
	Integer votes;

	public Idea (Document doc) {
		this.id = doc.getObjectId("_id").toString();
		this.idea = doc.getString("idea");
		this.proposerId = doc.getString("proposerId");
		this.votes = doc.getInteger("votes");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public String getProposerId() {
		return proposerId;
	}

	public void setProposerId(String proposerId) {
		this.proposerId = proposerId;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}

//	public BasicDBObject toDBObject() {
//		BasicDBObject dBObjecDocumento = new BasicDBObject();
//
//		dBObjecDocumento.append("id", this.getId());
//		dBObjecDocumento.append("idea", this.getIdea());
//		dBObjecDocumento.append("user_id", this.getProposerId());
//		dBObjecDocumento.append("cantidad_votos", this.getVotes());
//		
//		return dBObjecDocumento;
//	}
	
	public Document toDocument() {
		Document documento = new Document();
		
		documento.append("id", this.getId());
		documento.append("idea", this.getIdea());
		documento.append("user_id", this.getProposerId());
		documento.append("cantidad_votos", this.getVotes());
		
		return documento;
	}
}
