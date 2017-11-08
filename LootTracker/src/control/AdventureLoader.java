package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;

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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Hello, world! Loading a file!");
		
		Adventure a = null;
		
		// get save file:
//		File f = (File) req.getSession().getAttribute("saveFile");
		Part filePart =  req.getPart("saveFile");
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		
		
		System.out.println("Received file: " + fileName);
		
//		FileInputStream is = new FileInputStream(fileName);
//		FileInputStream is = new FileInputStream(Paths.get(filePart.getSubmittedFileName()).toFile());
		FileInputStream is = (FileInputStream) filePart.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		try {
			a = (Adventure) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ois.close();
		is.close();
		System.out.println("adventure count = " + a.getPartySize());
		
		req.getSession().setAttribute("currentAdventure", a);
		
		
		
		resp.sendRedirect("home.jsp");
	}
	
	//TODO: load in a previously saved adventure.
	
	

}
