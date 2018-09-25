<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a new adventure...</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<div id = "header">
Create a new adventure...</div>

<div id = "content">
How many adventures are in your party?
<form name="numCharactersForm" method="POST" action="AdventureCreator">
	<input type="text" name="numberOfcharacters" placeholder="4" required="required"/>
	<input type="hidden" name="username" value="<%= (String) request.getSession().getAttribute("username") %>"/>
	<input type="hidden" name="form_number" value="1"/>
	<button class="btn" type="submit">Next..</button>
</form>

</div>

<div id="footer"><a href="index.html">cancel</a></div>

</body>
</html>