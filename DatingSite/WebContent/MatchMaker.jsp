<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="datingSite.Global"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get your Match</title>
</head>
<body>
<p> Search for other people on the datingsite</p>
<form id= searchForm method="get" action = "SearchHandler">
<% for(Global.PersonalInfo pi : Global.PersonalInfo.values()){%>
	<% if(pi == Global.PersonalInfo.Birthday) continue; %>
	<%= pi.getBlankHTMLInputTag() %>
<% } %>
</form>
</body>
</html>