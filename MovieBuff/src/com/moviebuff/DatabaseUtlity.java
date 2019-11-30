package com.moviebuff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
				 		"where R.userid=U.\"userid\" and R.movieid=M.movieid and T.movieid=M.movieid\r\n" + 
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
            getAllGenreForAMovie(movieList);
            
            
            return movieList;
        }  catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
            return null;
        }
		
	}
	
	public void getAllGenreForAMovie(List<Movie> movieList) {
		try {
		Statement statement = connection.createStatement();
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
		
		}  catch (SQLException e) {
	        System.out.println("Connection failure.");
	        e.printStackTrace();
	        return ;
	    }
	}
	
	
	public void fetchUserId(String userName) {
		Integer userId = 0;
		String sqlQuery = "";
		ResultSet resultSet;
		try {
			Statement statement = connection.createStatement();
			PreparedStatement ps = null;
			sqlQuery = "select  U.\"userid\" from public.\"User\" U\r\n" + 
					"where U.email_id = ?";
			ps = connection.prepareStatement(sqlQuery);
			ps.setString(1, userName);
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				 userId =  resultSet.getInt("userid");
				DatabaseConnector.getInstance().setUserId(userId);
				 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public List<Movie> showMovieHistory() {
			StringBuilder sb = new StringBuilder();
			String sqlQuery = "";
			ResultSet resultSet;
			List<Movie> movieList = new ArrayList<Movie>();
			try {
				Statement statement = connection.createStatement();
				PreparedStatement ps = null;
				sqlQuery = "select  M.title, R.rating as \"avg\" \r\n" + 
			 		"from public.\"Ratings\" R, public.\"Movies\" M, public.\"User\" U\r\n" + 
			 		"where R.movieid=M.movieid and U.\"userid\"=R.userid and U.\"userid\"= ?";
				ps = connection.prepareStatement(sqlQuery);
				ps.setInt(1, DatabaseConnector.getInstance().getUserId());
				resultSet = ps.executeQuery();
				addToMovieList(resultSet, movieList);
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return movieList;
		}
	public List<Movie> getRecommendation() {
		
		try {
			 
            Statement statement = connection.createStatement();
            int userId = DatabaseConnector.getInstance().getUserId();
	        String recent20MovieQuery = "select R.movieid,R.timestamp from public.\"Ratings\" R where R.userid = " + userId + " order by timestamp desc limit 20";
	            
            ResultSet recent20MovieResultSet = statement.executeQuery(recent20MovieQuery);
             
            List<Integer> recent20Movies = new ArrayList<>();
            
            while (recent20MovieResultSet.next()) {
            	int movieId = recent20MovieResultSet.getInt("movieId");
            	recent20Movies.add(movieId);
            }
            
            //Initialize weight
            int weight = 20;
            int totalWeight = 0;
            HashMap<String, Integer> genreMap = new HashMap<String, Integer>();
            
            for(int movieId : recent20Movies) {
            	String movieGenre = "select G.genre_name from public.\"Genre\" G, public.\"MovieGenre\" MG where MG.movieid = " + movieId + " AND MG.genreid = G.genreid";
            	ResultSet genreSet = statement.executeQuery(movieGenre);
            	
            	while(genreSet.next()){
            		String genre = genreSet.getString("genre_name");
            		genreMap.put(genre, genreMap.getOrDefault(genre, 0) + weight);
            		
            	}
            	totalWeight += weight;
            	weight--;
            }
            
            int boredomFactor = (int)(0.6*totalWeight);
            
            List<String> top3Genres = new ArrayList<String>();
            
            for(Map.Entry<String, Integer> e  : genreMap.entrySet()) {
            	int weightCurr = e.getValue();
            	if(weightCurr < boredomFactor) {
            		top3Genres.add(e.getKey());
            	}
            }
            
            Collections.sort(top3Genres, new Comparator<String>() {
            	public int compare(String g1, String g2) {
            		return genreMap.get(g2) - genreMap.get(g1);
            	}
            });
            
            top3Genres = top3Genres.subList(0, Math.min(top3Genres.size(), 3));
            
            
            //get movie list by genre
            Map<String, Movie> movieRatingMap = new HashMap<String, Movie>();
            

            String genre1 = top3Genres.size() > 0 ? top3Genres.get(0) : "", 
            		genre2 = top3Genres.size() > 1 ? top3Genres.get(1) : "", 
            		genre3 = top3Genres.size() > 2 ? top3Genres.get(2) : "";
            
            List<Movie> tempMovies ;
            tempMovies = getTop10MoviesByGenre(new String[] {genre1, genre2, genre3}); addInMovieRatingMap(movieRatingMap,tempMovies);
            tempMovies = getTop10MoviesByGenre(new String[] {genre1, genre2}); addInMovieRatingMap(movieRatingMap,tempMovies);
            tempMovies = getTop10MoviesByGenre(new String[] {genre1, genre3}); addInMovieRatingMap(movieRatingMap,tempMovies);
            tempMovies = getTop10MoviesByGenre(new String[] {genre2, genre3}); addInMovieRatingMap(movieRatingMap,tempMovies);
            tempMovies = getTop10MoviesByGenre(new String[] {genre1}); addInMovieRatingMap(movieRatingMap,tempMovies);
            tempMovies = getTop10MoviesByGenre(new String[] {genre2}); addInMovieRatingMap(movieRatingMap,tempMovies);
            tempMovies = getTop10MoviesByGenre(new String[] {genre3}); addInMovieRatingMap(movieRatingMap,tempMovies);
            
            List<Movie> movieList = new ArrayList<>(movieRatingMap.values());
            
            Collections.sort(movieList, new Comparator<Movie>() {
            	public int compare(Movie m1, Movie m2) {
            		if(m2.getAvgRating() < m1.getAvgRating())
            			return -1;
            		else
            			return 1;
            	}
            });
            
            Set<Movie> alreadyRated = new HashSet<Movie>(showMovieHistory());
            List<Movie> recMovieList = new ArrayList<Movie>();
            for (Movie movie : movieList) {
				if(!alreadyRated.contains(movie)) {
					recMovieList.add(movie);
				}
				if(recMovieList.size() == 5) {
					break;
				}
			}
            
            getAllGenreForAMovie(recMovieList);
            
            return recMovieList;
        }  catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
            return null;
        }
		
	}
	
	private void addInMovieRatingMap(Map<String, Movie> movieRatingMap, List<Movie> tempMovies) {
		for(Movie movie: tempMovies) {
			String movieName = movie.getTitle();
			if(!movieRatingMap.containsKey(movieName)) {
				movieRatingMap.put(movieName, movie);
			}
		}
	}

	public List<Movie> getTop10MoviesByGenre(String[] genreNames) {
		
		try {
			String query;
			if(genreNames.length ==1)
				query = "select avg(R.rating) AS Avg, M.title, M.movieid, G.genre_name "
						+ "FROM public.\"Ratings\" R, public.\"Movies\" M, public.\"Genre\" G, public.\"MovieGenre\" T "
						+ "WHERE R.movieid=M.movieid AND T.movieid=M.movieid AND T.genreid=G.genreid AND "
						+ "G.genre_name= \'" + genreNames[0] + "\' GROUP BY G.genreid,M.movieid ORDER BY Avg DESC LIMIT 10";
				
			else if (genreNames.length ==2)
				query = "select avg(R.rating) AS Avg,M.movieid, M.title "
						+ "FROM public.\"Ratings\" R, public.\"Movies\" M, public.\"Genre\" G1,  "
						+ "public.\"Genre\" G2, public.\"MovieGenre\" T1, public.\"MovieGenre\" T2 "
						+ "WHERE R.movieid=M.movieid and T1.movieid=M.movieid and T1.movieid = T2.movieid "
						+ "and T1.genreid=G1.genreid and T2.genreid=G2.genreid and G1.genre_name= \'" + genreNames[0] + 
						"\' and G2.genre_name= \'"+ genreNames[1] + "\' GROUP BY M.movieid ORDER BY Avg DESC LIMIT 10";
			
			else //for 3 genres
				query = "select avg(R.rating) AS Avg,M.movieid, M.title "
						+ "FROM public.\"Ratings\" R, public.\"Movies\" M, public.\"Genre\" G1,  "
						+ " public.\"Genre\" G2, public.\"Genre\" G3, public.\"MovieGenre\" T1, "
						+ " public.\"MovieGenre\" T2 , public.\"MovieGenre\" T3"
						+ " WHERE R.movieid=M.movieid and T1.movieid=M.movieid and T1.movieid = T2.movieid and "
						+" T3.movieid=M.movieid and T1.genreid=G1.genreid and  T2.genreid=G2.genreid and"
						+ " T3.genreid = G3.genreid and G1.genre_name= \'" + genreNames[0] + "\' and G2.genre_name= "
						+ "\'"+ genreNames[1] + "\' and G3.genre_name= \'" + genreNames[2] + "\' GROUP BY M.movieid ORDER BY Avg DESC LIMIT 10";
            
			Statement statement = connection.createStatement();
             
            ResultSet resultSet = statement.executeQuery(query);
            List<Movie> movieGenreList = new ArrayList<Movie>();
            while (resultSet.next()) {
            	Movie movie = new Movie();
            	movie.setAvgRating(Double.parseDouble(resultSet.getString("Avg")));
            	movie.setTitle(resultSet.getString("title"));
            	movie.setMovieid(resultSet.getInt("movieid"));
            	movieGenreList.add(movie);
            //	System.out.println(movie);
            }
            
            return movieGenreList;
        }  catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
            return null;
        }
		
	}
	
	
	
}
