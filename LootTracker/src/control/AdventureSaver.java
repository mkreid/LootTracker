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
		
		
//		System.out.println("AdventureSaver.debug: a.getPartySize() = " + a.getPartySize());
		
		
		// write object to file
	//	FileOutputStream fos = new FileOutputStream(getServletContext().getRealPath("/files/"+ fileName));
		FileOutputStream fos = new FileOutputStream(fileName);
		
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// write object
		oos.writeObject(a);
		System.out.println("object written to file on the server");
		// close streams
		oos.close();
		fos.close();
		
		
		// send file to user
		//File f = new File(this.getServletContext().getRealPath("/files/" + fileName));
		File f = new File(fileName);
		
		//resp.setContentType("application/lttrk");

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
		
//		
//		String filename = "myAdventure.lttrk";
//		resp.setContentType("application/octet-stream");
//		resp.setHeader("Content-Disposition", "attachment;filename="+filename);
//		
//		File f = new File(filename);
////		FileInputStream fileIn = new FileInputStream(f);
//		FileInputStream fileIn = new FileInputStream(new File(getServletContext().getRealPath("/files/"+filename)));
//		ServletOutputStream out = resp.getOutputStream();
//		
//		byte[] outputeByte = new byte[(int)f.length()];
//		while (fileIn.read(outputeByte, 0, (int)f.length()) != -1 ) {
//			out.write(outputeByte, 0, (int)f.length());
//		}
//		fileIn.close();
		
		//TODO: redirect back to load page!
		
//		resp.setContentType("text/hmtl");
//		resp.sendRedirect("home.jsp");
	}
	
}
