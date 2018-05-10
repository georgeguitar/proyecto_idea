package com.stats.dao;

import java.util.List;

import com.stats.model.Idea;

public interface IdeaDAO {
	public void guardarActualizar(Idea idea);
	public void actualizarIdeas(VoteDAOImpl voteDAOImpl);
	public void borrar(String idIdea);
	public List<Idea> listar();
	public Idea get(String idIdea);
}
