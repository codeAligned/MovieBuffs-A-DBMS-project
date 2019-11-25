package com.moviebuff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtlity {
	
	Connection connection;
	
	public DatabaseUtlity(Connection con) {
		// TODO Auto-generated constructor stub
		this.connection = con;
	}

	String verifyPassword(String userName, String userPassword) {
		String resp = "0";;
		try {
			Statement statement = connection.createStatement();
			String t="SELECT password FROM public.\"User\" WHERE email_id=\'"+userName+"\'";
			
			ResultSet resultSet = statement.executeQuery(t);
			String s="";
			while (resultSet.next()) {
			 s =  resultSet.getString("password");
			}
			
			if(userPassword.equals(s)){
				System.out.println("Password Matched");
				resp="1";
	   }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        return resp;
	}
}
