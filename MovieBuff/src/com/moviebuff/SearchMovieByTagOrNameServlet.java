package com.moviebuff;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class SearchMovieByTagOrNameServlet
 */
@WebServlet("/searchmoviebytagorname")
public class SearchMovieByTagOrNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchMovieByTagOrNameServlet() {
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
		String radioButtonVal = request.getParameter("command");
		String searchText = request.getParameter("searchValue");
		//PrintWriter writer = response.getWriter();
		Connection conn = DatabaseConnector.getInstance().con;
		DatabaseUtlity dbUtility = new DatabaseUtlity(conn);
		
		String str = dbUtility.showMoviesByNameOrTag(radioButtonVal, searchText);
		//String str = "sfwsfew";
		request.setAttribute("movieSearch",str);
		request.getRequestDispatcher("MovieSearchResult.jsp").forward(request, response); 
	}

}
