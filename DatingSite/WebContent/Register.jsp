<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="datingSite.Global"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<style>
 body {
 	background-image: url("http://farm9.staticflickr.com/8166/7353107414_f2eb14356a_b.jpg");
 	background-repeat: no-repeat;
 	background-attachment: fixed;
 	background-position: center;
 	
 }
 h1{
    text-align: left;
    
 }
</style>
	<title>Roosevelt Dating Website</title>
	</head>
	<body>
		<%= Global.getError(session)%>
		<h1>Please Register</h1>
		<div align="left">
			<form action = "RegistrationHandler" method = "post">
				<span align="right"> 
					Email: <input type="email" placeholder="you@example.com" name='email' required><br>
					Password: <input type="password" name="password" required><br>
					Retype Password: <input type="password" name="repassword" required><br>
					I am a Roosevelt Student: <input type="checkbox"name="studentstatus"><br> 
					<input type="submit" value="Submit">
				</span>
			</form>
		</div>
	</body>
</html>