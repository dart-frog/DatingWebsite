<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Roosevelt Dating Website</title>
<%response.sendRedirect("Register.html"); %>
</head>
<body>
	<H1>Welcome to Roosevelts Dating Website</H1>
	<p>If you are a new user please register</p>
	<a href="Register.jsp"> Register</a>
	<p>Log in</p>
	<form>
		<form action="SignIn" method="Post">
			UserName: <input type="text" name="UserName" required><br>
			Password: <input type="password" name="Password" required><br>
			<input type="submit" value="Submit">
		</form>
</body>
</html>