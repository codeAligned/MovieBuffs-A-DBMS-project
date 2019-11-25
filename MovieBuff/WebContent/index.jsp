<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Welcome to MovieBuffs</h1>

<form action="loginservlet" method="post">
    Username: <input type="text" name="userName" size="50">
    <br />
    <br />
    Password:  <input type="password" name="userPassword" size="50">
    <br />
    <br />
    <input type="submit" value="Sign-In" />
</form>
</body>
</html>