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

import java.util.List;

import com.stats.model.Documento;

public interface DocumentoDAO {
	public List<Documento> listar();
	public void guardarActualizar(Documento documento);
	public Documento get(String idDocumento);
	public void borrar(String idDocumento);
	public List<Documento> buscar(String llave, String valor);
}
