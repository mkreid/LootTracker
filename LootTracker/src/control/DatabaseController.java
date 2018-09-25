package control;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import model.Adventure;



public class DatabaseController {
	
	private final static String JDBC_URL = "jdbc:mysql://localhost:3306/loottracker?autoReconnect=true&useSSL=false";
	private final static String JDBC_USER = "root";
	private final static String JDBC_PASS = "malcolm0";
	private final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	public static boolean authenticateUser(String username, String password) {
		
		try {
			
			Class.forName(JDBC_DRIVER);
			
			Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
				
			String stmt = "select count(*) from SEC_USERS where username = \'" + username + "\' and password = \'" + obfuscatepw(password) + "\'";

			System.out.println("DEBUG: SQL_STMT=" + stmt);
			
			PreparedStatement ps = conn.prepareStatement(stmt);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				// found a match
				if (rs.getInt(1) == 1) {
					rs.close();
					ps.close();
					conn.close();
					return true; 
				}
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean registerNewUser(String username, String password, String emailAddress) {
		
		try {
		
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
			
			String stmt = "insert into sec_users(USERNAME, FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, PASSWORD) values (?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(stmt);
			ps.setString(1, username);
			ps.setString(2, "First");
			ps.setString(3, "Last");
			ps.setString(4, emailAddress);
			ps.setString(5, obfuscatepw(password));
			
			int results = ps.executeUpdate();
			
			if (results == 1) {
				ps.close();
				conn.close();
				return true; 
			} else {
				ps.close();
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;	
	}
	
	public static String byteArrayToHexString(byte[] b) {
		  String result = "";
		  for (int i=0; i < b.length; i++) {
		    result +=
		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
		}
	
	
	private static String obfuscatepw(String pw) throws DigestException {
		//obfuscate password:
		MessageDigest md = null;
		String obfpw = "";
		String payload = (pw+"ILUV2PARTY!");
		 try {
			 md = MessageDigest.getInstance("SHA-1");
		     md.update(payload.getBytes());
		     MessageDigest tc1 = (MessageDigest) md.clone();
		     byte[] toChapter1Digest = tc1.digest();
		     obfpw = byteArrayToHexString(toChapter1Digest);
		     
		 } catch (CloneNotSupportedException | NoSuchAlgorithmException cnse) {
		     throw new DigestException("couldn't make digest of partial content");
		 }
		 
		return obfpw;
	}

	public static boolean adventureExists(String username) {
		try {
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
			String stmt = "select count(*) from adventures where username = ?";
			PreparedStatement ps = conn.prepareStatement(stmt);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// found a match
				if (rs.getInt(1) == 1) {
					rs.close();
					ps.close();
					conn.close();
					return true; 
				}
			}
			rs.close();
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public static Adventure loadAdventure(String username) {
		Adventure a = null;
		
		try {
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
			String stmt = "select adventure_object from adventures where username = ?";
			PreparedStatement ps = conn.prepareStatement(stmt);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				// found something!
				byte[] st = (byte[]) rs.getObject(1);
				ByteArrayInputStream baip = new ByteArrayInputStream(st);
				ObjectInputStream ois = new ObjectInputStream(baip);
				a = (Adventure) ois.readObject();
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return a;
	}

	public static void writeAdventure(Adventure a, String username) {
		try {
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
			String stmt = "insert into adventures (username, adventure_object) values (?, ?)";
			PreparedStatement ps = conn.prepareStatement(stmt);
			ps.setString(1, username);
			ps.setObject(2, a);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
			
		} catch (SQLIntegrityConstraintViolationException e) {
			try {
				Class.forName(JDBC_DRIVER);
				Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
				String stmt = "update adventures set adventure_object = ? where username = ?";
				PreparedStatement ps = conn.prepareStatement(stmt);
				ps.setObject(1, a);
				ps.setString(2, username);
				ps.executeUpdate();
				
				ps.close();
				conn.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	

}
