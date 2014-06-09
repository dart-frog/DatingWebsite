<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="datingSite.Global, datingSite.Global.User, datingSite.Global.Message, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
if(!Global.isSessionValid(request)) {
	Global.setError(session, "Invalid session.");
	response.sendRedirect("Home.jsp");
} else { %>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<%
		User currentUser = Global.getUserFromRequest(request);
	%>
	<title> Message Hub</title>
	
	</head>
	<body>
	<%@include file="NavBar.jsp" %>
	<h1>Your Messages</h1><br>
	<h2>Received Messages</h2><br>
	<%List<Message> receivedMessages = currentUser.getRecievedMessages(); %>
	<%for(Message m : receivedMessages) { %>
		<h3><%=m.getSender().getAllUserInfo().get(Global.PersonalInfo.FirstName) %></h3><br>
		<%=m.getText() %><br><br>
	<%} %>
	
	
	</body>
<%} %>
</html>