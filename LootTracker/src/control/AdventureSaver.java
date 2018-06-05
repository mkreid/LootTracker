package control;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Get Adventure object from session
		Adventure a = (Adventure) req.getSession().getAttribute("currentAdventure");
		
		// TODO: prompt for a filename
		String fileName = "./MyAdventure.lttrk";
				
		
		// write object to file
		FileOutputStream fos = new FileOutputStream(fileName);
		
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// write object
		oos.writeObject(a);
		System.out.println("object written to file on the server");
		// close streams
		oos.close();
		fos.close();
		
		
		// send file to user
		File f = new File(fileName);
		

		resp.setHeader("Content-Length", String.valueOf(f.length()));
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		InputStream input = new BufferedInputStream(new FileInputStream(f), 1024 * 10);
		OutputStream output = new BufferedOutputStream(resp.getOutputStream(), 1024 * 10);
		
		byte[] buffer = new byte[1024 * 10];
		int length;
		while ((length = input.read(buffer)) > 0) {
		    output.write(buffer, 0, length);
		}
		
		output.flush();
		output.close();
		input.close();
		
	}
	
}
