<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Roosevelt Dating Website</title>
</head>
<body>
	<h1>Please Register</h1>
	<div align="left">
		<form action = "RegistrationHandler.java" method = "post">
			<span align="right"> 
				Email: <input type="email" placeholder="you@example.com" name='email' required><br>
				Password: <input type="password" name="password" required><br>
				Retype Password: <input type="password" name="repassword" required><br>
				I am a Roosevelt Student: <input type="checkbox"name="studentstatus"><br> 
				<input type="submit" value="Submit;">
				
			</span>
		</form>
	</div>
</body>
</html>