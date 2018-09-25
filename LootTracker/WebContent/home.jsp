<%@page import="control.LoginController"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Adventure"%>
<%@page import="model.Character"%>
<%@page import="model.Session"%>
<%@page import="model.Item"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Heroes!</title>
<script src="js/jquery_1_7_1.js" type="text/javascript"></script>
<script src="js/shortcut.js" type="text/javascript"></script>
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript">
function init() {
	shortcut.add("Shift+SPACE", function() {
		if (/[?&]sessionId=/.test(location.search)) {
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

function showEditWindow(editItemId, editItemSessionId, editItemName, editValue, editNote, currencyType) {
	console.log("showEditWindow() called.");
	var modal = document.getElementById('editModal');
	var span = document.getElementsByClassName("close")[1];
	var form = document.getElementById('modifyLootForm');
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
	
	// set the fields with data
	document.getElementById('editModalItemId').value = editItemId;
	document.getElementById('editModalSessionId').value = editItemSessionId;
	document.getElementById('editModalNameInput').value = editItemName;
	document.getElementById('editModalValueInput').value = editValue;
	document.getElementById('editModalNotesInput').value = editNote;
	document.getElementById('editCurrencyValue').value = currencyType;
	
<<<<<<< HEAD
=======
	console.log("currencyType=" + currencyType);
	
	if (currencyType == <%= Item.COPPER_PIECES %>) {
		console.log("--> cur = CP");
		document.getElementById("editCurrencyCopper").checked = true;
	} else if (currencyType == <%= Item.SILVER_PIECES %>) {
		console.log("--> cur = SP");
		document.getElementById('editCurrencySilver').checked = true;
	} else if (currencyType == <%= Item.GOLD_PIECES %>) {
		console.log("--> cur = GP");
		document.getElementById('editCurrencyGold').checked = true;
	} else if (currencyType == <%= Item.ELECTRUM_PIECES %>) {
		console.log("--> cur = EP");
		document.getElementById('editCurrencyElectrum').checked = true;
	} else if (currencyType == <%= Item.PLATINUM_PIECES %>) {
		console.log("--> cur = PP");
		document.getElementById('editCurrencyPlatinum').checked = true;
	}
>>>>>>> refs/remotes/origin/master

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
String deleteItemId = null;
String itemSessionId = null;
String editItemId = null;
if (a == null) {
	// redirect to create new or load!
	response.sendRedirect("index.jsp");
} else {
//	System.out.println("a is NOT null - yay!!!");
	partySize = String.valueOf(a.getPartySize());
	characters = a.getParty();
	sessionCount = String.valueOf(a.getNumSessions());
	selectedCharacterId = request.getParameter("characterId");
	selectedSessionId = request.getParameter("sessionId");
	deleteItemId = request.getParameter("deleteItemId");
	itemSessionId = request.getParameter("itemSessionId");
	editItemId = request.getParameter("editItemId");
			
	
//	System.out.println("selectedCharacterId=" + selectedCharacterId);

}

%>

<%

// check if we're adding a session

if (request.getParameter("addSession") != null) {
	System.out.println("New session request!!; addSession="+request.getParameter("addSession"));
	a.addSession(new Session());
	sessionCount = String.valueOf(a.getNumSessions());
}

%>

<%
// check if we're adding a session
if (request.getParameter("modifyItem") != null) {
	System.out.println("modify item request!!; modifyItem="+request.getParameter("modifyItem"));
	// do something!
	%>
	<script>
	showEditWindow();
	</script>
	<%
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
<% if (!sessionCount.equalsIgnoreCase("0") && a!=null) {
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
	out.println("<table id=\"t01\">");
	out.println("<tbody>");
	out.println("<tr>");
	out.println("<th>Item</th>");
	out.println("<th>Value</th>");
	out.println("<th class=\"currencyColumn\">Cur.</th>");
	out.println("<th>Notes</th>");
	out.println("<th class=\"modifyColumn\">Modify</th>");
	out.println("</tr>");
	
	int itemIndex = 0;
	for (Item i : items) {
		out.println("<tr>");
		//out.println(i.toString() + "<a href=\"home.jsp?itemId="+ itemIndex + "\">[&times;]</a>" + "<br>");
		out.println("<th>" + i.getName() + "</th>");
		out.println("<th>" + i.getValue() + "</th>");
		out.println("<th class=\"currencyColumn\">" + i.getPrintableCurrency() + "</th>");
		out.println("<th>" + i.getDescription() + "</th>");
		out.println("<th class=\"modifyColumn\">" + "<a href=\"javascript:showEditWindow(" + itemIndex + "," + selectedSessionId + 
				", '" + i.getSanitizedName() + "', " + i.getValue() + ", '" + i.getSanitizedDescription() + "', " + 
				i.getValueCurrency() + ")\"><img class=\"editImg\" src=\"img/edit_trans.png\"/></a>" +
				"<a href=\"home.jsp?deleteItemId="+ itemIndex + "&itemSessionId=" + selectedSessionId + 
				"\" onclick=\"return confirm('Are you sure?')\"><img class=\"deleteImg\" src=\"img/delete.png\"/></a>" + "</th>");
		itemIndex++; 
		out.println("</tr>");
	}
	
	out.println("</tbody>");
	out.println("</table>");
	out.println("<br>");
	
} else if ((deleteItemId != null && itemSessionId != null) ) {
	// delete an item request!
	Session s = a.getSession(Integer.parseInt(itemSessionId));
	s.getSessionLoot().remove(Integer.parseInt(deleteItemId));
	
	response.sendRedirect("home.jsp?sessionId=" + itemSessionId);
	
} else if ((editItemId != null && itemSessionId != null) ) {	
	// modify an item request!
	
	
	
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
    	<p>Item Value: <input type="text" name="itemValue" placeholder="0" required="required"/>
    	<select name="currencyValue">
    		<option value="1" id="currencyCopper">CP</option>
    		<option value="2" id="currencySilver">SP</option>
    		<option value="3" id="currencyGold">GP</option>
    		<option value="4" id="currencyElectrum">EP</option>
    		<option value="5" id="currencyPlatinum">PP</option>
    	</select>
    	<p>Item Notes: <input type="text" name="itemDescription" placeholder="This shoe smells funny..."/></p>
    	<input type="hidden" name="sessionId" value="<%= selectedSessionId %>"/>
		<input type="hidden" name="form_number" value="3"/>
		<button class="btn" type="submit">Add..</button>
	</form>
  	</div>
</div>

<!-- The edit-item Modal -->
<div id="editModal" class="modal">
  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">&times;</span>
    <form id="modifyLootForm" name="ModifyLootForm" method="POST" action="AdventureCreator">
    	<p>Item Name: <input id="editModalNameInput" type="text" name="itemName" value="ERR" required="required"/></p>
		<p>Item Value: <input id="editModalValueInput" type="text" name="itemValue" value="ERR" required="required"/>
		<select name="currencyValue" id="editCurrencyValue">
    		<option value="1" id="editCurrencyCopper">CP</option>
    		<option value="2" id="editCurrencySilver">SP</option>
    		<option value="3" id="editCurrencyGold">GP</option>
    		<option value="4" id="editCurrencyElectrum">EP</option>
    		<option value="5" id="editCurrencyPlatinum">PP</option>
    	</select>
    	<p>Item Notes: <input id="editModalNotesInput" type="text" name="itemDescription" value="ERR"/></p>
    	<input id="editModalSessionId" type="hidden" name="sessionId" value="<%= itemSessionId %>"/>
    	<input id="editModalItemId" type="hidden" name="editItemId" value="<%= editItemId %>"/>
		<input type="hidden" name="form_number" value="4"/>
		<button class="btn" type="submit">Save</button>
	</form>
  	</div>
</div>

<!--  footer content -->
<div id="footer">
<form action="login" method="POST"><input type="hidden" name="action_request" value="<%= LoginController.LOGOUT_REQUEST %>"/><button type="submit" name="logout" value="logout" onclick="return confirm('Are you sure? Unsaved progress will be lost!')">Logout</button></form>
<form action="save" method="POST"><button type="submit" name="save" value="save">Save</button></form>
</div>
<%@ include file="parts/footer.jsp" %>
</body>
</html>