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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stats.model.User;

@Service
public class DocumentoMySqlDAOImpl implements DocumentoMySqlDAO {
	
	Connection con;
	
	public DocumentoMySqlDAOImpl(Connection dbConexion) {
		con = dbConexion;
	}
	
	@Override
	public List<User> listar() {
		List<User> listaDocumentos = new ArrayList<User>();
		/*
		PreparedStatement st;
		try {
			st = con.prepareStatement("select * from person");
		    ResultSet rs=st.executeQuery();

		    while(rs.next()){
			    Person person = new Person();
		    	person.setId(rs.getInt("id"));
		    	person.setFirstName(rs.getString("firstName"));
		    	person.setLastName(rs.getString("lastName"));
		    	person.setAge(rs.getInt("age"));
		    	
		    	listaDocumentos.add(person);
//		    	System.out.println(rs.getInt("id")+" "+rs.getString(2));  
		    }  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return listaDocumentos;
	}
}
