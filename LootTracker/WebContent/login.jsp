<!DOCTYPE html>
<%@page import="control.LoginController"%>
<html>
<head>
<title>Welcome to LootTracker</title>
<%@ include file="parts/header.jsp" %>
<link rel="stylesheet" href="css/signup.css">
</head>
<body>
<%@ include file="parts/page_header.jsp" %>
<br>
<form action="login" method = "POST" style="border:1px solid #ccc">
  <div class="container">
    <h1>Sign In</h1>
    <p>Please fill in your credentials to continue adventuring.</p>
    <hr>
    <label for="username"><b>Username</b></label>
    <input type="text" placeholder="Enter Username" name="username" required/>

    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="password" required/>

    <label>
      <input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Remember me
    </label>
    <% 
	String errorCode = (String)request.getSession().getAttribute("ErrorCode"); 
	if (errorCode != null) {
		out.println("<p style='color:red;'>" + errorCode + "</p>");
		request.getSession().setAttribute("ErrorCode", null);
	}	
	%>
    <input type="hidden" name="action_request" value="<%= LoginController.LOGIN_REQUEST %>"/>

    <div class="clearfix">
      <button type="submit" class="signupbtn">Sign In</button>
    </div>
  </div>
</form>
<br>
<%@ include file="parts/footer.jsp" %>
</body>
</html>