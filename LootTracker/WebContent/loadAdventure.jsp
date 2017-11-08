<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Continue your Journey</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<div id="header">Continue your quest...</div>
 
<form name="numCharactersForm" method="POST" action="load" enctype = "multipart/form-data">
	Select the adventure to load: <input type="file" name="saveFile" required="required"/>
	<button class="btn" type="submit">Load!</button>
</form>

<div id="footer"><a href="index.html">cancel</a></div>


</body>
</html>