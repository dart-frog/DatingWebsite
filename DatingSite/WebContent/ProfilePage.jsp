<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="datingSite.Global"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Roosevelt Dating Website</title>
<form action = "LogOutHandler" method = "post">
	<button type = "submit"> Log out</button>
</form>
<%if(!Global.isSessionValid(request)) { %>
	<%response.sendRedirect("Home.jsp"); %>
	<%Global.setError(session, "Invalid Session"); %>
<%} %>
</head>
<body>
	<p> Welcome to the profile page <p>
	Your session is valid!
	Today's date is <%=new Date() %>.
	
	<form id=personalInfo>
		<%
		%>
		<%for(int i = 0; i < Global.personalInfoLabels.length; i++) {%>
			<%=String.format("%s: <input type=\"%s\" name=\"%s\" value=\"%s\" required><br>", Global.personalInfoLabels[i], "text", Global.personalInfoVarNames[i], ) %>
		<%} %>
	</form>
	
	<!-- Is making GET requests to LogInHandler always a log out command a bad idea?  -->
	<!-- Yeah, probably. -->
<%=Global.getNavBar() %>
</body>
</html>
