<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="datingSite.Global"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Roosevelt Dating Website</title>
<%if(!Global.isSessionValid(request)) { %>
	<%response.sendRedirect("Home.jsp"); %>
	<%Global.setError(session, "Invalid Session"); %>
<%} %>
</head>
<body>
	<p> Welcome to the profile page <p>
	Your session is valid!
	<!-- Is making GET requests to LogInHandler always a log out command a bad idea?  -->
</body>
</html>