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
    <h1>Sign Up</h1>
    <p>Please fill in this form to create an account.</p>
    <hr>
    <% 
	String errorCode = (String)request.getSession().getAttribute("ErrorCode"); 
	if (errorCode != null) {
		out.println("<p style='color:red;'>" + errorCode + "</p>");
	}	
	%>
    <label for="username"><b>Username</b></label>
    <input type="text" placeholder="Enter Username" name="username" required/>

    <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="email" required>

    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="psw" required>

    <label for="psw-repeat"><b>Repeat Password</b></label>
    <input type="password" placeholder="Repeat Password" name="psw-repeat" required>

    <label>
      <input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Remember me
    </label>
    
    <input type="hidden" name="action_request" value="<%= LoginController.CREATE_ACCOUNT_REQUEST %>"/>

    <p>By creating an account you agree to our <a href="#" style="color:dodgerblue">Terms &amp; Privacy</a>.</p>

    <div class="clearfix">
      <button type="button" class="cancelbtn">Cancel</button>
      <button type="submit" class="signupbtn">Sign Up</button>
    </div>
  </div>
</form>
<br>

<%@ include file="parts/footer.jsp" %>
</body>
</html>