package ch16_gui;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.ImageIcon;


public abstract class FoodItem {

	/**
	 * attributes (instance variables visible throughout package)
	 */
	String name;
	int size;
	String sizeList[];
	int quantity;
	int type;
	
	//double price;
	BigDecimal price;
	String specialRequest = "Item Special Request";
	ImageIcon productImage;
	
	NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);

	
	/**
	 * calculated the price of this food item based on its attributes
	 * @return double
	 */
	abstract BigDecimal getPrice();
	
	/**
	 * returns an appropriate String name for this size foot item
	 * @return String
	 */
	abstract String getSizeAsString();
	
	/**
	 * returns a descriptive String for this type of food item
	 * @return String
	 */
	abstract String getTypeAsString();
	
	/**
	 * provides a text description of this object for use in
	 * containers like JList
	 * @return String
	 */
	public abstract String toString(); 
	
	/**
	 * provides row data for the order table (POS 7)
	 * @return String[]
	 */
	public abstract String[] getRow();
	
	
}
