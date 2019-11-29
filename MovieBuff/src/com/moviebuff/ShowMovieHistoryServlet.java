package com.moviebuff;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moviebuff.objects.Movie;

/**
 * Servlet implementation class ShowMovieHistoryServlet
 */
@WebServlet("/showmoviehistory")
public class ShowMovieHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowMovieHistoryServlet() {
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
		// TODO Auto-generated method stub
		Connection conn = DatabaseConnector.getInstance().con;
		DatabaseUtlity dbUtility = new DatabaseUtlity(conn);
		
		List<Movie> listOfMovies = dbUtility.showMovieHistory();
		request.setAttribute("ListOfMovies",listOfMovies);
		request.getRequestDispatcher("MovieSearchResult.jsp").forward(request, response); 
		
	}

}
