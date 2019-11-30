<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.moviebuff.objects.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<% ArrayList<Movie> movieList = (ArrayList<Movie>)request.getAttribute("movieList"); %>

<table border="1">
		<thead>
			<tr>
				<th>#</th>
				<th>Movie Name</th>
				<th>Average Rating</th>
			</tr>
		</thead>
		<tbody>
			<% int i=1; %> 
 
			<%for (Movie u : movieList) {%>
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
</head>
<body>
</body>
</html>