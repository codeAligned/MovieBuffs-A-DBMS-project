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

$(document).ready(function(){
    $('#movieGenre').click(function(){
        $('#movieGenreButton').show();
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
 
 <h1>Genre Based Top 10 Movies </h1>
<h2>Please select at most three genre names</h2>

<form name="genreList" action="moviegenre10list" method="post">
<input type="button" id="movieGenre" value="Get Movies By Genre" />
<br>
<div id="movieGenreButton" style="display: none;"> 
<p>
<input type="checkbox" name="genre_name" value="Adventure">Adventure<br>
<input type="checkbox" name="genre_name" value="Animation">Animation<br>
<input type="checkbox" name="genre_name" value="Children">Children<br>
<input type="checkbox" name="genre_name" value="Comedy">Comedy<br>
<input type="checkbox" name="genre_name" value="Fantasy">Fantasy<br>
<input type="checkbox" name="genre_name" value="Romance">Romance<br>
<input type="checkbox" name="genre_name" value="Drama">Drama<br>
<input type="checkbox" name="genre_name" value="Action">Action<br>
<input type="checkbox" name="genre_name" value="Crime">Crime<br>
<input type="checkbox" name="genre_name" value="Thriller">Thriller<br>
<input type="checkbox" name="genre_name" value="Horror">Horror<br>
<input type="checkbox" name="genre_name" value="Mystery">Mystery<br>
<input type="checkbox" name="genre_name" value="Sci-Fi">Sci-Fi<br>
<input type="checkbox" name="genre_name" value="War">War<br>
<input type="checkbox" name="genre_name" value="Musical">Musical<br>
<input type="checkbox" name="genre_name" value="Documentary">Documentary<br>
<input type="checkbox" name="genre_name" value="IMAX">IMAX<br>
<input type="checkbox" name="genre_name" value="Western">Western<br>
<input type="checkbox" name="genre_name" value="Film-Noir">Film-Noir</p>
<br><br>
<input type="submit" name= "submit" value="Submit"> 
</div>
</form>	
 
 <form action="RecommendationServlet" method="post">
  <INPUT TYPE="submit" VALUE="Get Recommendation" />
</form>
</body>
</html>
