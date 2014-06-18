<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="datingSite.Global"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
 body {
 	background-image: url('http://images.fineartamerica.com/images-medium-large/seattle-inverted-black-and-white-larry-keahey.jpg');
 	background-repeat: no-repeat;
 	background-attachment: fixed;
 	background-position: center;
 	color: black;
 }
 h1{
    text-align: center;
    
 }
 p{
 	text-align: center;
 }
 form{
 	align: center;
 }
 div.LogIn{
 	margin-left: auto;
    margin-right: auto;
    width: 300px;
 
    align: right;
 }
</style>
<title>Roosevelt Dating Website</title>
<%if(Global.isSessionValid(request)){ %>
<%response.sendRedirect("ProfilePage.jsp"); %>
<%} %>
</head>
<body>
	<%if(session.getAttribute("error") != null) { %>
		<span style="color:red;"><%=session.getAttribute("error") %></span>
		<%session.setAttribute("error", null); %>
	<%} %>
	<H1>Welcome to Roosevelts Dating Website</H1>
	<p>If you are a new user, please <a href= "Register.jsp"> Register</a></p>
	
	<p>Log in</p>
	<div class="LogIn">
	<form action="LogInHandler" method="post">
		Email: <input type="text" name="email" required><br>
		Password: <input type="password" name="password" required><br>
		<input type="submit" value="Submit"><br>
	</form>
	</div>
</body>
</html>
