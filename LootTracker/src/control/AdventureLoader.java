package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.Adventure;

@MultipartConfig

public class AdventureLoader extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.setLevel(Level.SEVERE);
		
		LOGGER.log(Level.INFO, "Loading a file!");
		
		Adventure a = null;
		
		// get save file:
		Part filePart =  req.getPart("saveFile");
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		
		
		LOGGER.log(Level.INFO, "Received file: " + fileName);
		
		FileInputStream is = (FileInputStream) filePart.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		try {
			a = (Adventure) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ois.close();
		is.close();
		
		LOGGER.log(Level.INFO, "adventure count = " + a.getPartySize());
		
		req.getSession().setAttribute("currentAdventure", a);
		
		
		
		resp.sendRedirect("home.jsp");
	}	
	

}
