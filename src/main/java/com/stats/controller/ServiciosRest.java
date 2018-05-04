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

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.stats.dao.DocumentoDAO;
import com.stats.dao.DocumentoMySqlDAO;
import com.stats.model.Documento;
import com.stats.model.Person;

@RestController
@RequestMapping("/stats")
public class ServiciosRest {
	
	@Autowired
	private DocumentoDAO documentoDAO;
	
	@Autowired
	private DocumentoMySqlDAO documentoMySqlDAO;
	
	@GET
	@RequestMapping(value = "/listarDocumentos")
	@Produces(MediaType.TEXT_PLAIN)
	public String listaLibros(){
		List<Documento> listaDocumentos = new ArrayList<Documento>();
		listaDocumentos = documentoDAO.listar();
		
		String json = new Gson().toJson(listaDocumentos);
		
		return json;
	}
	
	@GET
	@RequestMapping(value = "/buscarDocsDisponibles/{disponibilidad}")
	@Produces(MediaType.TEXT_PLAIN)
	public String buscarDisponibilidad(@PathVariable String disponibilidad){
		List<Documento> listaDocumentos = new ArrayList<Documento>();
		listaDocumentos = documentoDAO.buscar("disponibilidad", disponibilidad);
		
		String json = new Gson().toJson(listaDocumentos);
		
		return json;
	}
	
	@GET
	@RequestMapping(value = "/buscarTitulos/{titulo}")
	@Produces(MediaType.TEXT_PLAIN)
	public String buscarPorTitulo(@PathVariable String titulo){
		List<Documento> listaDocumentos = new ArrayList<Documento>();
		listaDocumentos = documentoDAO.buscar("titulo", titulo);
		
		String json = new Gson().toJson(listaDocumentos);
		
		return json;
	}
	
	@GET
	@RequestMapping(value = "/listarPersona")
	@Produces(MediaType.TEXT_PLAIN)
	public String listaPerson(){
		List<Person> listaPerson = new ArrayList<Person>();
		listaPerson = documentoMySqlDAO.listar();
		
		String json = new Gson().toJson(listaPerson);
		
		return json;
	}
	
}
