package model;

import java.io.Serializable;

public class Item implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int value;
	private int valueCurrency;
	private String description;
	private Character owner;
	
	public static final int COPPER_PIECES = 1;
	public static final int SILVER_PIECES = 2;
	public static final int GOLD_PIECES = 3;
	public static final int ELECTRUM_PIECES = 4;
	public static final int PLATINUM_PIECES = 5;
	
	/**
	 * empty constructor
	 */
	public Item() {
		name = "";
		value = 0;
		setValueCurrency(0);
		description = "";
		owner = null;
	}
	
	/**
	 * Constructor with name, value, currency, and description.
	 * TODO: set owner?
	 * @param aName
	 * @param aValue
	 * @param aCurrency
	 * @param aDescription
	 */
	public Item(String aName, int aValue, int aCurrency, String aDescription) {
		setName(aName);
		setValue(aValue);
		setValueCurrency(aCurrency);
		setDescription(aDescription);
		owner = null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Character getOwner() {
		return owner;
	}
	public void setOwner(Character owner) {
		this.owner = owner;
	}

	public int getValueCurrency() {
		return valueCurrency;
	}
	
	public String getPrintableCurrency() {
		String currency;
		if (this.getValueCurrency() == Item.COPPER_PIECES) {
			currency = "CP";
		} else if (this.getValueCurrency() == Item.SILVER_PIECES) {
			currency = "SP";
		} else if (this.getValueCurrency() == Item.GOLD_PIECES) {
			currency = "GP";
		} else if (this.getValueCurrency() == Item.ELECTRUM_PIECES) {
			currency = "EP";
		} else if (this.getValueCurrency() == Item.PLATINUM_PIECES) {
			currency = "PP";
		} else {
			currency = "";
		}
		return currency;
	}

	public void setValueCurrency(int valueCurrency) {
		this.valueCurrency = valueCurrency;
	}

	@Override
	public String toString() {
		String output = "";
		String currency;
		if (this.getValueCurrency() == Item.COPPER_PIECES) {
			currency = "CP";
		} else if (this.getValueCurrency() == Item.SILVER_PIECES) {
			currency = "SP";
		} else if (this.getValueCurrency() == Item.GOLD_PIECES) {
			currency = "GP";
		} else if (this.getValueCurrency() == Item.ELECTRUM_PIECES) {
			currency = "EP";
		} else if (this.getValueCurrency() == Item.PLATINUM_PIECES) {
			currency = "PP";
		} else {
			currency = "";
		}
		
		output += this.getName() + "\t" + String.valueOf(this.getValue()) + "\t" + currency + "\t" + this.getDescription();
		
		return output;
	}
	
	
	

}
