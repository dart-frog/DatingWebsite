<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="datingSite.Global, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<% Global.User currentUser = Global.getUserFromRequest(request);
Global.User otherUser = null;
Map<Global.PersonalInfo, String> otherUserInfoMap = null;
String currentUserID = "INVALID USER";
String otherUserID = request.getParameter("user");
if(currentUser == null) {
	response.sendRedirect("Home.jsp");
	Global.setError(session, "Invalid Session");
} else if(otherUserID == null) {
	response.sendRedirect("ProfilePage.jsp");
	Global.setError(session, "User not found.");
} else {
	currentUserID = currentUser.getUserID();
	otherUser = new Global.User(otherUserID);
} 

if(otherUser == null) {
	response.sendRedirect("ProfilePage.jsp");
	Global.setError(session, "User not found.");
} else {
	otherUserInfoMap = otherUser.getAllUserInfo();
}%>
</head>
<body>
<h1>Currently viewing the page of user <%=otherUserInfoMap.get(Global.PersonalInfo.FirstName) %>.</h1>
<h2>Personal Information:</h2>
<%for(Global.PersonalInfo pi : Global.PersonalInfo.values()) { %>
	<%=pi.getDisplayName() %>
	<%=": " %>
	<%=otherUserInfoMap.get(pi) %>
	<br>
<%} %>
<%@include file="NavBar.jsp" %>
</body>
</html>