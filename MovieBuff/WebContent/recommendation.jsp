<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.util.*" %> 
<%@ page import="com.moviebuff.objects.*" %> 
<html>
<head>
<meta charset="UTF-8">
<title>Recommendation movie</title>
</head>
<body>
<p>Your personalized recommendation list!</p>
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
			<% List<Movie> movieList = (List<Movie>)request.getAttribute("recommendationList");%>
 
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