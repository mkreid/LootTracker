package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents one session of game play. Items looted during the session are recorded here.
 * 
 * @author mkreid
 *
 */
public class Session implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date sessionDate= null;
	private Character[] charactersPresent = null;
	private ArrayList<Item> sessionLoot = null;
	
	
	public Session() {
		this.setSessionDate(new Date());
		this.sessionLoot = new ArrayList<Item>();
		
	}
	
	public ArrayList<Item> getSessionLoot() {
		return sessionLoot;
	}
	
	public void addSessionLootItem(Item anItem) {
		this.sessionLoot.add(anItem);
	}
	
	public void removeSessionLootItem(int anIndex) {
		this.sessionLoot.remove(anIndex);
	}

	public Date getSessionDate() {
		return sessionDate;
	}
	
	public String getSessionLootCount() {
		return String.valueOf(sessionLoot.size());
	}

	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}

	public Character[] getCharactersPresent() {
		return charactersPresent;
	}

	public void setCharactersPresent(Character[] charactersPresent) {
		this.charactersPresent = charactersPresent;
	}
	
	
	

}
