<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %> 
<%@ page import="com.moviebuff.objects.*" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Top Rated movies</title>
</head>
<body>
<p> This is view page </p>


<table border="1">
		<thead>
			<tr>
				<th>#</th>
				<th>Movie Name</th>
				<th>Average Rating</th>
				<th>Genre</th>
			</tr>
		</thead>
		<tbody>
			<% int i=1; %> 
			<% List<Movie> movieList = (List<Movie>)request.getAttribute("movieList");%>
 
			<%for (Movie movie : movieList) {%>
				<tr> 
					<td> <%= i++ %></td>
					<td><%=movie.getTitle() %></td>
					<td><%=movie.getAvgRating() %></td>
					<td><%=movie.getGenres() %></td>
				<tr/>	

<%} %>
		</tbody>
	</table>


</table>


</body>
</html>