package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Adventure implements Serializable {
	

	/** 
	 * Constructor for a new Adventure! Initialize the number of party members to 0, to start!
	 */
	public Adventure() {
		partySize = 0;
		sessions = new ArrayList<Session>();
	}
	
	
	
	/* Local Private Variables */
	private static final long serialVersionUID = 1L;
	private int partySize;
	private String username = null;
	private Character[] party = null;
	private ArrayList<Session> sessions = null;

	
	public Character[] getParty() {
		return party;
	}

	public void setParty(Character[] party) {
		this.party = party;
	}

	public int getPartySize() {
		return partySize;
	}

	public void setPartySize(int partySize) {
		this.partySize = partySize;
		this.party = new Character[partySize];
	}
	
	/**
	 * Implementation of the serializable interface. 
	 * 
	 * @param aInputStream
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
		aInputStream.defaultReadObject();
	}
	
	/**
	 * Implementation of the serializable interface. 
	 * @param aOutputStream
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
		aOutputStream.defaultWriteObject();
	}

	
	public int getNumSessions() {
		return sessions.size();
	}

	public void addSession(Session s) {
		this.sessions.add(s);
	}
	public Session getSession(int index) {
		return this.sessions.get(index);
	}
	public ArrayList<Session> getAllSessions() {
		return this.sessions;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

}
