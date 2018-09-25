package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Adventure;

public class AdventureSaver extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AdventureSaver() {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Get Adventure object from session
		Adventure a = (Adventure) req.getSession().getAttribute("currentAdventure");
		String username = a.getUsername();
		
		// write adventure object to database
		DatabaseController.writeAdventure(a, username);	
		
		
		// notify the user
		// TODO: display save icon/flair
		resp.sendRedirect("home.jsp");		
	}
	
}
