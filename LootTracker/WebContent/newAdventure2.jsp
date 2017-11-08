<%@page import="model.Adventure"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add adventurers...</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<% Adventure a = (Adventure) request.getSession().getAttribute("currentAdventure");%>

<div id = "header">
Let's get to know your heroes better...</div>

<br>

<form name="CharactersForm" method="POST" action="AdventureCreator">
<%
	for (int i = 0; i < a.getPartySize(); i++) {
		%>Character #<%=i+1%>:<br> Name: <input type="text" name="character<%=i%>Name" placeholder="John Smith" required="required"/> Class: <input type="text" name="character<%=i%>Class" placeholder="Barbarian" required="required"/>
		<br><hr>
	<%}
%>
	<input type="hidden" name="form_number" value="2"/>
	<button class="btn" type="submit">Next..</button>
</form>

	

<br>
<div id="footer"><a href="index.html">cancel</a></div>

</body>
</html>