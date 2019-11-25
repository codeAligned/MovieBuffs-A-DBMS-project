<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%String name = (String)request.getAttribute("Username"); %>

    <br />
   
<h1>Welcome to MovieBuffs</h1>
 <br />
  <br />
<h2>Hello <%= name%></h2>
<form action="HelloServlet1" method="post">
  
   <input type="submit" value="Check Movie history" />
   <input type="hidden" name="myhiddenvalue" value="<%= name%>" />
    <br />
    <br />
   </form>
</body>
</html>