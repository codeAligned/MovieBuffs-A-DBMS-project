package com.moviebuff;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moviebuff.objects.Movie;

/**
 * Servlet implementation class Top100RatedMovies
 */
//@WebServlet 
public class Top100RatedMovies extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Top100RatedMovies() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = DatabaseConnector.getInstance().con;
		DatabaseUtlity dbUtility = new DatabaseUtlity(conn);
		List<Movie> movieList = dbUtility.getTopMovies();
		System.out.println(movieList.size());
		System.out.println(movieList);
		request.setAttribute("movieList",movieList);
		request.getRequestDispatcher("getTop100.jsp").forward(request, response); 
	}

}
