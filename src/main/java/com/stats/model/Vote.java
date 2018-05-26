/**
 * Demo realizado por:
 * Claudia Salazar Gonzales
 * Juan Dirceu Navarro Arias
 * Luis Fernando Numa Navarro Arias
 * 
 * Diplomado Software Libre versión Sucre
 * noviembre 2016
 */


package com.stats.model;

import com.mongodb.BasicDBObject;

public class Vote {
	private String id;
	private String userId;
	private String ideaId;
	
	public Vote (BasicDBObject dBObjectDocumento) {
		this.id = dBObjectDocumento.getString("id");
		this.userId = dBObjectDocumento.getString("user_id");
		this.ideaId = dBObjectDocumento.getString("idea_id");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(String ideaId) {
		this.ideaId = ideaId;
	}

}