package model;

import java.io.Serializable;

public class Character implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Character(String name, String combatClass, int level) {
		this.name = name;
		this.combatClass = combatClass;
		this.level = level;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCombatClass() {
		return combatClass;
	}
	public void setCombatClass(String combatClass) {
		this.combatClass = combatClass;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	private String name;
	private String combatClass;
	private int level;

}
