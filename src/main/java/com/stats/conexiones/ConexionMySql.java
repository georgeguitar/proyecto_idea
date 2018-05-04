package com.stats.conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

@Service
public class ConexionMySql {
	private static Connection con = null;
	
	
	public Connection CrearConexion() {
		try{
			if( con == null ){
			    String driver="com.mysql.jdbc.Driver"; //el driver varia segun la DB que usemos
			    String url="jdbc:mysql://localhost/borrar?autoReconnect=true";
			    String pwd="beatles";
			    String usr="juan";
			    Class.forName(driver);
			    con = DriverManager.getConnection(url,usr,pwd);
			    System.out.println("Conectionesfull");
			}
		}
		catch(ClassNotFoundException | SQLException ex){
			ex.printStackTrace();
		}
		
		return con;
   }
	
	public void CerrarConexion() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
