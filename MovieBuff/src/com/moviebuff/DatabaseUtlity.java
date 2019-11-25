package com.moviebuff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtlity {
	
	Connection connection;
	public static final String NAME = "0";
	public static final String TAG = "1";
	
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

	public String showMoviesByNameOrTag(String radioButtonVal, String searchText) {
		 
		 ResultSet resultSet;
		 StringBuilder sb = new StringBuilder();
		 String sqlQuery = "";
		 try {
			Statement statement = connection.createStatement();
			 PreparedStatement ps = null;
			 
			 if(radioButtonVal.equals(TAG)) {
			 	sqlQuery = "select  M.title\r\n" + 
			 			"from public.\"Movies\" M, public.\"Tags\" T\r\n" + 
			 			"where T.movieid=M.movieid and T.tag= ? ";
			 	ps = connection.prepareStatement(sqlQuery);
			 	ps.setString(1, searchText);
			 	//resultSet= statement.executeQuery("SELECT * FROM public.\"Genre\"");
			 	resultSet = ps.executeQuery();
			 	 while (resultSet.next()) {
			       	//String s =  resultSet.getString("genreId") + resultSet.getString("genre_name");
			       	String s =  resultSet.getString("title");
			       	s = s + "\n";
			           sb = sb.append(s);
			       }
			 } else if (radioButtonVal.equals(NAME)) {
			 	sqlQuery = "select  M.title, M.movieid\r\n" + 
			 			"from public.\"Movies\" M\r\n" + 
			 			"where M.title= ?";
			 	ps = connection.prepareStatement(sqlQuery);
			 	ps.setString(1, searchText);
			 	//resultSet= statement.executeQuery("SELECT * FROM public.\"Genre\"");
			 	resultSet= ps.executeQuery();
			 	 while (resultSet.next()) {
			      	//String s =  resultSet.getString("genreId") + resultSet.getString("genre_name");
			      	String s =  resultSet.getString("title") + resultSet.getString("movieid");
			      	s = s + "\n";
			          sb = sb.append(s);
			      }
			 	
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return sb.toString();
	}
}
