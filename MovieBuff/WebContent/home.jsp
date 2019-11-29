<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
    $('#showSeachMovieArea').click(function(){
        $('#showSeachMovieButton').show();
    });
    $('#nameRadio, #tagRadio').click( function() {
  	  $("#showSearchArea").show();
  	});
});


</script>
</head>
<body>
<%String name = (String)request.getAttribute("Username"); %>

    <br />
   
<h1>Welcome to MovieBuffs</h1>
 <br />
  <br />
<h2>Hello <%= name%></h2>
<form action="searchmoviebytagorname" method="post">
  
   <input type="button" id="showSeachMovieArea" value="Search Movie" />
   <input type="hidden" name="myhiddenvalue" value="<%= name%>" />
    <br />
    <br />
    
<div id="showSeachMovieButton" style="display: none;"> 
	<INPUT TYPE="radio" name="command" id="nameRadio" value="0"/>Name
<INPUT TYPE="radio" NAME="command" id = "tagRadio" VALUE="1"/>Tag
<br/>
<div id = "showSearchArea" style="display: none;">
Enter search string here: <input type="text" name="searchValue" size="20" >
<INPUT TYPE="submit" VALUE="submit" />
</div>
 
</div>




   </form>
	
<form action="showmoviehistory" method="post">
   <input type="submit" id="showMovieHistoryButton" value="Show History" />

</form>	
   
<form action="Top100RatedMovies" method="post">
  <INPUT TYPE="submit" VALUE="Get top 100 movies" />
 </form>	
</body>
</html>
