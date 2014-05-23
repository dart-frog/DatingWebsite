<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="datingSite.Global"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Roosevelt Dating Website</title>
<%Global.User user = Global.getUserFromRequest(request); %>
<%String userID = "INVALID USER";
if(user == null) {
	response.sendRedirect("Home.jsp");
	Global.setError(session, "Invalid Session");
} else {
	userID = user.getUserID();
} %>
</head>
<body>
	<%if(session.getAttribute("error") != null) { %>
		<span style="color:red;"><%=session.getAttribute("error") %></span>
		<%session.setAttribute("error", null); %>
	<%} %>
	<form action = "LogOutHandler" method = "post">
		<button type = "submit"> Log out</button>
	</form>
	<p> Welcome to the profile page! <p>
	Your session is valid.<br>
	Your user ID is <%=userID %>.<br>
	Your email address is <%=Global.getEmailFromUserID(userID) %>.<br>
	

	<div id=personalInfoDiv>
		<h3>Personal Information</h3>
		<form id=personalInfoForm method="post" action="PersonalInfoHandler">
			<%for(Global.PersonalInfo pi : Global.PersonalInfo.values()) { %>
				<%=pi.getHTMLInputTag(user) %>
			<%} %>
			<input type="submit" value="Submit">
		</form>
	</div>
<%@include file="NavBar.jsp" %>
</body>
</html>
