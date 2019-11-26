package com.moviebuff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moviebuff.objects.Movie;

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

	public List<Movie> showMoviesByNameOrTag(String radioButtonVal, String searchText) {
		 
		 ResultSet resultSet;
		 StringBuilder sb = new StringBuilder();
		 String sqlQuery = "";
		 List<Movie> movieList = new ArrayList<Movie>();
		 try {
			Statement statement = connection.createStatement();
			 PreparedStatement ps = null;
			 if(radioButtonVal.equals(TAG)) {
				 sqlQuery = "select M.title, Avg(R.rating) \r\n" + 
				 		"from public.\"User\" U, public.\"Movies\" M, public.\"Ratings\" R, public.\"Tags\" T \r\n" + 
				 		"where R.userid=U.\"userId\" and R.movieid=M.movieid and T.movieid=M.movieid\r\n" + 
				 		"and T.\"tag\"= ? \r\n" + 
				 		"Group by M.movieid";
			 	ps = connection.prepareStatement(sqlQuery);
			 	ps.setString(1, searchText);
			 	resultSet = ps.executeQuery();
			 	addToMovieList(resultSet, movieList);
			 	 
			 } else if (radioButtonVal.equals(NAME)) {
			 	sqlQuery = "select  M.title, M.movieid\r\n" + 
			 			"from public.\"Movies\" M\r\n" + 
			 			"where M.title= ?";
			 	
			 	sqlQuery = "select M.title, Avg(R.rating)\r\n" + 
			 			"from  public.\"Movies\" M, public.\"Ratings\" R\r\n" + 
			 			"where   R.movieid=M.movieid and  M.\"title\"= ?\r\n" + 
			 			"Group by M.movieid";
			 	ps = connection.prepareStatement(sqlQuery);
			 	ps.setString(1, searchText);
			 	resultSet= ps.executeQuery();
			 	addToMovieList(resultSet, movieList);
			 	
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
         return movieList;
	}

	private void addToMovieList(ResultSet resultSet, List<Movie> movieList) {
		try {
			while (resultSet.next()) {
				 Movie m = new Movie();
				 m.setTitle(resultSet.getString("title"));
				 m.setAvgRating(Double.parseDouble(resultSet.getString("avg")));

				 movieList.add(m);
				 
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
