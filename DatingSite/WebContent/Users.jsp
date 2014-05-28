<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="datingSite.Global, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%
Global.User currentUser=null, otherUser=null;
String currentUserID, otherUserID;

try {
	currentUser = Global.getUserFromRequest(request);
	currentUserID = currentUser.getUserID();
} catch(IllegalArgumentException e) {
	response.sendRedirect("Home.jsp");
	Global.setError(session, "Invalid Session");
}

try {
	otherUserID = request.getParameter("user");
	otherUser = new Global.User(otherUserID);
} catch(IllegalArgumentException e) {
	response.sendRedirect("ProfilePage.jsp");
	Global.setError(session, "User not found.");
}

Map<Global.PersonalInfo, String> otherUserInfoMap = null;
%>
</head>
<body>
<%if(currentUser != null && otherUser != null) {%>
<h1>Currently viewing the page of user <%=otherUserInfoMap.get(Global.PersonalInfo.FirstName) %>.</h1>
<h2>Personal Information:</h2>
<%for(Global.PersonalInfo pi : Global.PersonalInfo.values()) { %>
	<%=pi.getDisplayName() %>
	<%=": " %>
	<%=otherUserInfoMap.get(pi) %>
	<br>
<%} %>
<%} %>
<%@include file="NavBar.jsp" %>
</body>
</html>