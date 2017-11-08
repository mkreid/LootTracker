<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Adventure"%>
<%@page import="model.Character"%>
<%@page import="model.Session"%>
<%@page import="model.Item"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Heroes!</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="http://www.openjs.com/scripts/events/keyboard_shortcuts/shortcut.js" type="text/javascript"></script>
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript">
function init() {
	shortcut.add("Shift+SPACE", function() {
		if (/[?&]sessionId=/.test(location.search)) {
			//alert("TODO: redirect to add loot screen?");
			var modal = document.getElementById('myModal');
			var span = document.getElementsByClassName("close")[0];
			span.onclick = function() {
			    modal.style.display = "none";
			}
			// When the user clicks anywhere outside of the modal, close it
			window.onclick = function(event) {
			    if (event.target == modal) {
			        modal.style.display = "none";
			    }
			}
			modal.style.display = "block";
		} else {
			alert("Select a valid session first!!");
		}
	});
	
}
window.onload=init;
</script>

</head>
<body>
<% Adventure a = (Adventure) request.getSession().getAttribute("currentAdventure");
String partySize = "0";
Character[] characters = null;
String selectedCharacterId = null;
String sessionCount = "";
String selectedSessionId = null;
if (a == null) {
	// redirect to create new or load!
	response.sendRedirect("index.html");
} else {
//	System.out.println("a is NOT null - yay!!!");
	partySize = String.valueOf(a.getPartySize());
	characters = a.getParty();
	sessionCount = String.valueOf(a.getNumSessions());
	selectedCharacterId = request.getParameter("characterId");
	selectedSessionId = request.getParameter("sessionId");
	
//	System.out.println("selectedCharacterId=" + selectedCharacterId);
}%>

<%
// check if we're adding a session
if (request.getParameter("addSession") != null) {
	System.out.println("New session request!!; addSession="+request.getParameter("addSession"));
	a.addSession(new Session());
	sessionCount = String.valueOf(a.getNumSessions());
}
%>


<!--  nav bar up top -->
<div id="header">
<a href="home.jsp">LootTracker!</a>
</div>


<!--  side character bar -->
<div id="menu">
Party Size: <%= partySize %>
<br>
<% if (characters != null) {
	int i = 0;
	for (Character c : characters) {
		// do stuff
		out.println("<a href =\"home.jsp?characterId=" + i++ + "\">"+c.getName() +", the "+ c.getCombatClass() + "</a>" + "<br>"); 
	}
}
%>
<hr>
<p class="sessionsRecorded">Sessions Recorded: <%= sessionCount %></p>
<form name="newSessionForm" method="POST">
<input type="hidden" name="addSession" value="1">
<button onclick="javascript:addSession()">[+]</button>
</form>
<% if (!sessionCount.equalsIgnoreCase("0")) {
	int i = 0;
	for (Session s : a.getAllSessions()) {
		// do stuff
		out.println("<a href =\"home.jsp?sessionId=" + i + "\">"+ "Session #"+(i+1) + "</a><br>");
		i++;
		//out.println("<a href =\"home.jsp?sessionId=" + i++ + "\">"+ "Session #"+(i+1) +", on "+ DateFormat.getDateInstance().format(s.getSessionDate()) + "</a>" + "<br>"); 
	}
}
%>
</div>

<!--  content frame -->
<div id="content">
<%
if (selectedCharacterId != null) {
	// someone is selected!
	out.println("TODO: display loot sheet for " + characters[Integer.parseInt(selectedCharacterId)].getName());
} else if (selectedSessionId != null) {
	// a session is selected!
	Session s = a.getSession(Integer.parseInt(selectedSessionId));
	ArrayList<Item> items = s.getSessionLoot();
	out.println("<h2> Loot Session " + (Integer.parseInt(selectedSessionId)+1) + " - " 
	+ s.getSessionDate().toString() + "</h2>");
	out.println("<h4>This session contains " + a.getSession(Integer.parseInt(selectedSessionId)).getSessionLootCount() + " items.</h4>");
	out.println("<hr>");
	//out.println("TODO: actually display items!");
	out.println("<table id=\"t01\">");
	out.println("<tbody>");
	out.println("<tr>");
	out.println("<th>Item</th>");
	out.println("<th>Value</th>");
	out.println("<th>Currency</th>");
	out.println("<th>Notes</th>");
	out.println("<th>Edit</th>");
	out.println("</tr>");
	
	int itemIndex = 0;
	for (Item i : items) {
		out.println("<tr>");
		//out.println(i.toString() + "<a href=\"home.jsp?itemId="+ itemIndex + "\">[&times;]</a>" + "<br>");
		out.println("<th>" + i.getName() + "</th>");
		out.println("<th>" + i.getValue() + "</th>");
		out.println("<th>" + i.getPrintableCurrency() + "</th>");
		out.println("<th>" + i.getDescription() + "</th>");
		out.println("<th>" + "<a href=\"home.jsp?itemId="+ itemIndex + "\">[&times;]</a>" + "</th>");
		itemIndex++; 
		out.println("</tr>");
	}
	
	out.println("</tbody>");
	out.println("</table>");
	out.println("<br>");
	
} else {
	// nothing is selected - display info
	out.println("Select someone from the left menu to check out their inventory, or create/select a session and press SHIFT+SPACE to add loot!");
}
%>
</div>

<!-- The add-item Modal -->
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">&times;</span>
    <form name="NewLootForm" method="POST" action="AdventureCreator">
    	<p>Item Name: <input type="text" name="itemName" placeholder="Stinky old shoe" required="required"/></p>
    	<p>Item Value: <input type="text" name="itemValue" placeholder="0" required="required"/><input type="radio" name="currencyValue" value="1" id="currencyCopper" checked /><label for="currencyCopper">CP</label><input type="radio" name="currencyValue" value="2" id="currencySilver" /><label for="currencySilver">SP</label><input type="radio" name="currencyValue" value="3" id="currencyGold" /><label for="currencyGold">GP</label><input type="radio" name="currencyValue" value="4" id="currencyElectrum" /><label for="currencyElectrum">EP</label><input type="radio" name="currencyValue" value="5" id="currencyPlatinum" /><label for="currencyPlatinum">PP</label></p>
    	<p>Item Notes: <input type="text" name="itemDescription" placeholder="This shoe smells funny..."/></p>
    	<input type="hidden" name="sessionId" value="<%= selectedSessionId %>"/>
		<input type="hidden" name="form_number" value="3"/>
		<button class="btn" type="submit">Add..</button>
	</form>
  	</div>
</div>

<!--  footer content -->
<div id="footer">
<a href="save">Download Progress</a> | <a href="index.html" onclick="return confirm('Are you sure? Unsaved progress will be lost!')">Exit</a>
</div>

</body>
</html>