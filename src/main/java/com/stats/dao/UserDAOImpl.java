package com.stats.dao;

import java.sql.Connection;

public class UserDAOImpl implements UserDAO {
	
	Connection con;
	
	public UserDAOImpl(Connection dbConexion) {
		con = dbConexion;
	}
}
