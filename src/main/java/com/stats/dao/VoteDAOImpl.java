package com.stats.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

@Service
public class VoteDAOImpl implements VoteDAO {
	
	Connection con;
	
	public VoteDAOImpl(Connection dbConexion) {
		con = dbConexion;
	}
	
	@Override
	public Integer obtenerVotos(String idIdea) {
		String sql = "select count(user_id) as votos from votes where idea_id = '" + idIdea + "'";
		Integer votos = 0;
		PreparedStatement st;
		
		try {
			st = con.prepareStatement(sql);
		    ResultSet rs=st.executeQuery();

		    while(rs.next()){
		    	votos = rs.getInt("votos");
		    }  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return votos;
	}
	
	@Override
	public Boolean borrarVotos(String idUsuario) {
		String sql = "DELETE FROM votes WHERE user_id = '" + idUsuario + "'";
		Boolean respuesta = true;
		PreparedStatement st;
		
		try {
			st = con.prepareStatement(sql);
			st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respuesta = false;
		}
		return respuesta;
	}

	@Override
	public Boolean eliminarVoto(String idUsuario, String idIdea) {
		String sql = "DELETE FROM votes WHERE idea_id = '" + idIdea + "' and user_id = '" + idUsuario + "'";
		Boolean respuesta = true;
		PreparedStatement st;
		
		try {
			st = con.prepareStatement(sql);
			st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respuesta = false;
		}
		return respuesta;
	}
	
	@Override
	public Boolean insertarVoto(String idUsuario, String idIdea) {
		String sql = "INSERT INTO votes (user_id, idea_id) VALUES ('" + idUsuario + "', '"+ idIdea + "')";
		Boolean respuesta = true;
		PreparedStatement st;
		
		try {
			st = con.prepareStatement(sql);
			st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respuesta = false;
		}
		return respuesta;
	}
	
}
