
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.moviebuff.objects.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>

<table border="1">
		<thead>
			<tr>
				<th>#</th>
				<th>Movie Name</th>
				<th>Your Rating</th>
			</tr>
		</thead>
		<tbody>
			<% int i=1; %> 
			<% List<Movie> movieNames = (List<Movie>)request.getAttribute("ListOfMovies");%>
 
			<%for (Movie u : movieNames) {%>
			<tr>
				<td><%=i++%></td>
				<td><%=u.getTitle()%></td>
				<td><%=u.getAvgRating()%></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>


</body>
</html>