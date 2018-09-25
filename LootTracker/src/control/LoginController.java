package control;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Adventure;

public class LoginController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int LOGIN_REQUEST = 1;
	public static final int LOGOUT_REQUEST = 2;
	public static final int CREATE_ACCOUNT_REQUEST = 3;
	
	private final static Logger LOGGER = Logger.getLogger(LoginController.class.getName());
	
	
	public LoginController() {
		LOGGER.setLevel(Level.INFO);
	}
	
	

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		// calls are made using an action request integer
		String actionDelimiter = req.getParameter("action_request");
		
		if (Integer.parseInt(actionDelimiter) == LOGIN_REQUEST) {
			// we're handling a login
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			
			boolean loginAttempt = DatabaseController.authenticateUser(username, password);
			
			if (loginAttempt) {
				// successful!
				LOGGER.info("LoginController: found corrosponding username and password in the database");
				
				// check if an adventure exists?
				if (DatabaseController.adventureExists(username)) {
					// load existing adventure
					Adventure a = DatabaseController.loadAdventure(username);
					a.setUsername(username);
					req.getSession().setAttribute("currentAdventure", a);
					resp.sendRedirect("home.jsp");
				} else {
					// prompt a new adventure
					req.getSession().setAttribute("username", username);
					resp.sendRedirect("newAdventure.jsp");
				}
				
			} else {
				// login failure
				LOGGER.info("LoginController: WRN! Did not find the corrosponding username and password in the database.");
				// send a message saying something was wrong with credentials
				req.getSession().setAttribute("ErrorCode","Bad username or password!");
				
				resp.sendRedirect("login.jsp");
			}
			LOGGER.info("LoginController: End of login_request");
			
		} else if (Integer.parseInt(actionDelimiter) == LOGOUT_REQUEST) {
			// we're handling a logout
			req.getSession().setAttribute("currentAdventure", null);
			req.getSession().invalidate();
			
			resp.sendRedirect("login.jsp");
			
			LOGGER.info("LoginController: End of logout_reqeust");
		} else if (Integer.parseInt(actionDelimiter) == CREATE_ACCOUNT_REQUEST) {
			// we're handling a new account request
			
			String username = req.getParameter("username");
			String password = req.getParameter("psw");
			String emailAddress = req.getParameter("email");
			
			// try to register in the DB
			Boolean registerNewUser = DatabaseController.registerNewUser(username, password, emailAddress);
			
			if (!registerNewUser) {
				req.getSession().setAttribute("ErrorCode","Unable to register user!");
				resp.sendRedirect("signup.jsp");
			} else {
			
				req.getSession().setAttribute("currentAdventure", new Adventure());
				resp.sendRedirect("login.jsp");
			}
			
			LOGGER.info("LoginController: End of CREATE_ACCOUNT_REQUEST");
		} else {
			// Unknown request!
			LOGGER.severe("LoginController: Warning! Unknown LoginController action request encountered.");
		}
		
	}
	
	
	

}
