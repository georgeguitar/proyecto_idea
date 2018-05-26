package com.stats.dao;

public interface VoteDAO {
	public Integer obtenerVotos(String idIdea);
	public Boolean borrarVotos(String idIdea);
	public Boolean insertarVoto(String idUsuario, String idIdea);
	public Boolean eliminarVoto(String idUsuario, String idIdea);
}
