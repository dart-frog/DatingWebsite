<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="datingSite.Global"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
div.sidebar{
	margin-left: auto;
    margin-right: auto;
    width: 400px;
}
li{
	display:inline;
	margin: 0;
    padding: 0;
    
}
ul li{
margin: 10px;
}

ul {
    list-style-type: none;
    
}
a {font-size:125%}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div class = "sidebar">
	<ul> 
		<li> <a href= 'ProfilePage.jsp'> Account Page</a> </li>
		<li>   <a href= 'MessageHub.jsp'> Message Hub</a>  </li> 
		<li>   <a href= 'MatchMaker.jsp'> Search</a></li> 
		<li>   <form action = "LogOutHandler" method = "post">
		<button type = "submit"> Log out</button>
		</form> </li>
	</ul>
	</div>

</body>
</html>