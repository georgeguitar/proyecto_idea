/**
 * Demo realizado por:
 * Claudia Salazar Gonzales
 * Juan Dirceu Navarro Arias
 * Luis Fernando Numa Navarro Arias
 * 
 * Diplomado Software Libre versión Sucre
 * noviembre 2016
 */

package com.stats.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.stats.dao.IdeaDAOImpl;
import com.stats.dao.VoteDAOImpl;
import com.stats.model.Idea;
import com.stats.rabbitmq.ReceiveImpl;

@RestController
@RequestMapping("/stats")
public class ServiciosRest {
	
	@PostConstruct
	public void initialize() throws IOException, TimeoutException {
		receiveImpl.escuchar();
	}	
	
	@Autowired
	private IdeaDAOImpl ideaDAOImpl;
	
	@Autowired
	private VoteDAOImpl voteDAOImpl;
	
	@Autowired
	private ReceiveImpl receiveImpl;	
	
	@GET
	@RequestMapping(value = "/listarIdeas")
	@Produces(MediaType.TEXT_PLAIN)
	public String listarIdeas(){
		List<Idea> listaIdeas = new ArrayList<Idea>();
		listaIdeas = ideaDAOImpl.listar();
		
		String json = new Gson().toJson(listaIdeas);
		
		return json;
	}
	
//	@POST
//	@RequestMapping(value = "/nuevoDocumento")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public void nuevo(Prueba prueba) {
//		System.out.println(prueba.toString());
//	}

	
//	@GET
//	@RequestMapping(value = "/buscarIdea/{idea}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String buscarDisponibilidad(@PathVariable String disponibilidad){
//		List<Idea> listaIdeas = new ArrayList<Idea>();
//		listaIdeas = ideaDAOImpl.buscar("idea", disponibilidad);
//		
//		String json = new Gson().toJson(listaIdeas);
//		
//		return json;
//	}
	
	@GET
	@RequestMapping(value = "/obtenerVotos/{idIdea}")
	@Produces(MediaType.TEXT_PLAIN)
	public String obtenerVotos(@PathVariable String idIdea){
		Integer votos = voteDAOImpl.obtenerVotos(idIdea);
		
		String json = new Gson().toJson(votos);
		
		return json;
	}
	
}
