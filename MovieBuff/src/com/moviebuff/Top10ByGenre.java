package com.moviebuff;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moviebuff.objects.Movie;

/**
 * Servlet implementation class Top10ByGenre
 */
@WebServlet("/moviegenre10list")
public class Top10ByGenre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Top10ByGenre() {
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
		PrintWriter writer = response.getWriter();
		Connection cn = DatabaseConnector.getInstance().con;
		DatabaseUtlity pg = new DatabaseUtlity(cn);
		if(cn==null)
		{
			writer.println("Connection is null");
			writer.close();
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		List<Movie> top10bygenre = new ArrayList<Movie>();
		String outResponse = new String(); 
		//String[] genreList = {"Adventure", "Animation", "Children", "Comedy", "Fantasy", "Romance", "Drama", "Action", "Crime", "Thriller", "Horror", "Mystery", "Sci-Fi", "War", "Musical", "Documentary", "IMAX", "Western", "Film-Noir"};
	
		String[] genreName = request.getParameterValues("genre_name");
		boolean submitButtonPressed = request.getParameter("submit") != null;
		if(submitButtonPressed != false)
		{
			if(genreName == null )
			{
				outResponse = "Please check at most two genres";
				writer.println(outResponse);
				writer.close();
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			else if (genreName.length >3 )
			{
				outResponse = "Please check the number of genre selected";
				writer.println(outResponse);
				writer.close();
				request.getRequestDispatcher("top10ByGenre.jsp").forward(request, response);
			}
			else
			{
				System.out.println(genreName);
				top10bygenre = pg.getTop10MoviesByGenre(genreName);
				request.setAttribute("movieList",top10bygenre);
				request.getRequestDispatcher("showTop10ByGenre.jsp").forward(request, response); 
			}
			try {
				cn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}