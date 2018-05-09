package com.stats.dao;

public interface VoteDAO {
	public Integer obtenerVotos(String idIdea);
	public Boolean borrarVotos(String idIdea);
}
