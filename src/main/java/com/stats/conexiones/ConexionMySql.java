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
			    String url="jdbc:mysql://mfh5oxm25hprrpon:etk70aoilrc5y8nz@ocvwlym0zv3tcn68.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/g3gcrh40646vhxdn";
			    String pwd="etk70aoilrc5y8nz";
			    String usr="mfh5oxm25hprrpon";
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
