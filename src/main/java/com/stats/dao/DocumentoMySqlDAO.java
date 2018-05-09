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

import com.stats.model.User;

public interface DocumentoMySqlDAO {
	public List<User> listar();
}
