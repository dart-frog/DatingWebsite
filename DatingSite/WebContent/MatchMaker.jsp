<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="datingSite.Global, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get your Match</title>
<%
Global.User currentUser=null;
String currentUserID;
try {
	currentUser = Global.getUserFromRequest(request);
	currentUserID = currentUser.getUserID();
} catch(IllegalArgumentException e) {
	response.sendRedirect("Home.jsp");
	Global.setError(session, "Invalid Session");
} %>
</head>
<body onload="init()">
<p> Search for other people on the dating site</p>
<form id=searchForm>
<% for(Global.PersonalInfo pi : Global.PersonalInfo.values()){%>
	<% if(pi == Global.PersonalInfo.Birthday) continue; %>
	<%= pi.getBlankHTMLInputTag().replace("required", "") %>
<% } %>
<input type = "hidden" name="myself" value="<%=currentUser.getUserID() %>"> 
<input id="submitButton" type="button" value="Submit">
</form>

<span id='listOfUsers'>
</span>

<script type='text/javascript' src=MatchMaker.js></script>
</body>
</html>
