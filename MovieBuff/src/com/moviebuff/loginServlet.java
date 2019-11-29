package com.moviebuff;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginservlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOGIN_ERROR = "We do not recognize your Email-id and/or password. Please try again." + "</h1>";
    private static final String LOGIN_SUCCESS = "1";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
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
		//doGet(request, response);
		
		String userName = request.getParameter("userName");
		String userPassword= request.getParameter("userPassword");
		System.out.println("Username "+userName);
		PrintWriter writer = response.getWriter();
		
		String res = authorizeUser(userName, userPassword);
		
		if(res.equals(LOGIN_SUCCESS)) {
			request.setAttribute("Username",userName);
			request.getRequestDispatcher("home.jsp").forward(request, response);
		} else {
			writer.println(LOGIN_ERROR);
			writer.close();
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

	private String authorizeUser(String userName, String userPassword) {
		Connection con = DatabaseConnector.getInstance().con;
		DatabaseUtlity utility = new DatabaseUtlity(con);
		String result = utility.verifyPassword(userName, userPassword);
		if(result.equals(LOGIN_SUCCESS)) {
			utility.fetchUserId(userName);
		}
		return result;
		
	}

}
