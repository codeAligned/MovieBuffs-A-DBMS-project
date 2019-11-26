package com.moviebuff;

import java.sql.Connection;
import java.sql.DriverManager;
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
	
	public List<Movie> getTopMovies() {
		
		try {
			 
            Statement statement = connection.createStatement();
            String query = "select avg(R.rating) Avg, M.title, M.movieid,M.tmdbid, M.imdbid from public.\"Ratings\" R, public.\"Movies\" M where R.movieid=M.movieid group by M.movieid order by Avg desc limit 100";
            
            ResultSet resultSet = statement.executeQuery(query);
            List<Movie> movieList = new ArrayList<>();
            System.out.println("xxx");
            while (resultSet.next()) {
            	Movie movie = new Movie();
            	movie.setTitle(resultSet.getString("title"));
            	movie.setAvgRating(Double.parseDouble(resultSet.getString("Avg")));
            	movie.setMovieid(Integer.parseInt(resultSet.getString("movieid")));
            	if(resultSet.getString("tmdbid") != null)
            		movie.setTmdbid(Long.parseLong(resultSet.getString("tmdbid")));
            	if(resultSet.getString("imdbid") != null)
            		movie.setImdbid(Long.parseLong(resultSet.getString("imdbid")));
            	movieList.add(movie);
            }
            //append genres
            for (Movie movie : movieList) {
            	int movieId = movie.getMovieid();
            	String genrequery = "select G.genre_name from public.\"Genre\" G, public.\"MovieGenre\" MG where MG.movieid = " + movieId + " AND MG.genreid = G.genreid";
            	ResultSet genreSet = statement.executeQuery(genrequery);
            	
            	StringBuilder genreSB = new StringBuilder();
            	while(genreSet.next()){
            		genreSB.append(genreSet.getString("genre_name"));
            		if(!genreSet.isLast())
            			genreSB.append(", ");
            	}
            	movie.setGenres(genreSB.toString());
			}
            
            System.out.println("Movie size " + movieList.size());
            
            return movieList;
        }  catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
            return null;
        }
		
	}
	
}
