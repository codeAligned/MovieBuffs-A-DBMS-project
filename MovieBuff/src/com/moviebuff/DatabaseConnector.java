package com.moviebuff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	
	private static DatabaseConnector databaseConnectionInstance = null;
	
	public Connection con;
	private DatabaseConnector() {
		try {
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static DatabaseConnector getInstance() {
		
		if(databaseConnectionInstance == null) {
			databaseConnectionInstance = new DatabaseConnector();
			
		}
		return databaseConnectionInstance;
	}
	

}
