package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Adventure;
import model.Character;
import model.Item;

public class AdventureCreator extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AdventureCreator() {
		super();
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String formDelimiter = req.getParameter("form_number");
		
		if (formDelimiter.equalsIgnoreCase("1")) {
			// create a new adventure
			Adventure a = new Adventure();
			// How many adventurers are there?
			a.setPartySize(Integer.parseInt(req.getParameter("numberOfcharacters")));
			
			// done 1st setup, redirect to newAdventure2.jsp page, binding  adventure to session
			HttpSession session = req.getSession();
			session.setAttribute("currentAdventure", a);
			resp.sendRedirect("newAdventure2.jsp");
		} else if (formDelimiter.equalsIgnoreCase("2")) {
			// time to setup the characters:
			Adventure a = (Adventure) req.getSession().getAttribute("currentAdventure");
			// TODO: error checking on a - is it null?
			System.out.println("setting up adventure of size " + a.getPartySize());
			
			
			
			Character[] p = new Character[a.getPartySize()];
			
			for (int i = 0; i< a.getPartySize(); i++) {
				String name = req.getParameter("character"+i+"Name");
				String combatClass = req.getParameter("character"+i+"Class");
				//TODO: accept characters of different level
				
				p[i] = new Character(name, combatClass, 1);
				System.out.print("Character "+(i+1)+" name: "+ req.getParameter("character"+i+"Name"));
				System.out.print("\t");
				System.out.println("class: "+ req.getParameter("character"+i+"Class"));
			}
			
			a.setParty(p);
			
			
			System.out.println("a.getPartySize() = " + a.getPartySize());
			req.getSession().setAttribute("currentAdventure", a);
			resp.sendRedirect("home.jsp");
			
		} else if (formDelimiter.equalsIgnoreCase("3")) {
			// We're adding some loot!
			
			Adventure a = (Adventure) req.getSession().getAttribute("currentAdventure");// get current adventure
			
			String sessionId = (String) req.getParameter("sessionId");						// get selected sessionId
			String newItemName = (String) req.getParameter("itemName");						// get item name
			int newItemValue = Integer.parseInt(req.getParameter("itemValue"));				// get item value
			int newItemCurrency = Integer.parseInt(req.getParameter("currencyValue"));	// get value currency
			String newItemDescription = (String) req.getParameter("itemDescription");		// get description
			
			Item i = new Item(newItemName, newItemValue, newItemCurrency, newItemDescription);
			
			System.out.println("Adding loot to session #"+sessionId);
			System.out.println("newItemName="+i.getName());
			System.out.println("newItemValue="+i.getValue());
			System.out.println("newItemCurrency="+i.getValueCurrency());
			System.out.println("newItemName="+i.getDescription());
			
			a.getSession(Integer.parseInt(sessionId)).addSessionLootItem(i);
			
			
			
			req.getSession().setAttribute("currentAdventure", a);
			resp.sendRedirect("home.jsp?sessionId="+sessionId);
			
		} else if (formDelimiter.equalsIgnoreCase("4")) {
			
			// We're modifying an item!
			Adventure a = (Adventure) req.getSession().getAttribute("currentAdventure");	// get current adventure
			String sessionId = (String) req.getParameter("sessionId");						// get selected sessionId
			
			System.out.println("Going to modify loot in session #"+sessionId);
			
			//TODO: get new parameters and update the item
			
			req.getSession().setAttribute("currentAdventure", a);							// store changes in session
			resp.sendRedirect("home.jsp?sessionId="+sessionId);
		}
		
		
						
		
	}
	

}
