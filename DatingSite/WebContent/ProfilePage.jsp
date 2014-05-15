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
<%String userID = "INVALID USER";
if(!Global.isSessionValid(request)) {
	response.sendRedirect("Home.jsp");
	Global.setError(session, "Invalid Session");
} else {
	userID = Global.getUserIDFromRequest(request);
} %>
</head>
<body>
	<p> Welcome to the profile page <p>
	Your session is valid!
	Your user ID is <%=userID %>
	Your email address is <%=Global.getEmailFromUserID(userID) %>

	<div id=personalInfoDiv>
		<h3>Personal Information</h3>
		<form id=personalInfoForm>
			<%for(Global.PersonalInfo pi : Global.PersonalInfo.values()) { %>
				<%=pi.getHTMLInputTag(userID) %>
			<%} %>
			<input type="submit" value="Submit">
		</form>
	</div>
	
	<!-- Is making GET requests to LogInHandler always a log out command a bad idea?  -->
	<!-- Yeah, probably. -->
<%=Global.getNavBar() %>
</body>
</html>
