package ch16_gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;

import javax.swing.ImageIcon;

public class Pizza extends FoodItem {

	/**
	 * attributes (instance variables visible throughout package)
	 */
	int crust;
	int status;
	boolean isLowPrice = false;
	String[] sizeList = {"Small", "Medium", "Large", "Extra Large"};
	
	ArrayList<Topping>toppings = new ArrayList<Topping>();

	/**
	 * random constructor
	 * note that most customers will prefer to specify their own
	 */
	Pizza() {

		Random r = new Random();

		size = r.nextInt(4);
		crust = r.nextInt(4);
		quantity = 1;

		int ntop = 1 + r.nextInt(2); // no such thing as "zero" toppings
		for (int i = 0; i < ntop; i++) {
			int randomTop = r.nextInt(5);
			switch(randomTop) {
			case 0: toppings.add(new Topping("pepperoni", 1.5)); break;
			case 1: toppings.add(new Topping("onions", 0.99)); break;
			case 2: toppings.add(new Topping("mushrooms", 1.25)); break;
			case 3: toppings.add(new Topping("olives", .79)); break;
			case 4: toppings.add(new Topping("parrot sauce", -.25)); break;
			}

		}

		Topping.sortAlphabetically = false; // sort by price
		Collections.sort(toppings);
		
		price = getPrice();

		//specialRequest = "--";
		status = 0;
		
		Image temp = Toolkit.getDefaultToolkit().getImage("pepperoni-pizza.png");
		productImage = new ImageIcon(temp.getScaledInstance(64, 48, Image.SCALE_SMOOTH));
		
	}

	/**
	 * calculates the price based on the pizza's attributes
	 * @return BigDecimal
	 */
	@Override
	public BigDecimal getPrice() {
		
		// base price
		//double p = (3.0*(size+1) + 3.0*(crust+1)); 
		BigDecimal p = new BigDecimal("3");
		p = p.multiply(new BigDecimal(new Long(size+crust+2L).toString()));
						
		// add toppings prices
		for (int i = 0; i < toppings.size(); i++) {
			//p += toppings.get(i).price;
			double topd = toppings.get(i).price;
			DecimalFormat df = new DecimalFormat("####.00");
			BigDecimal topping = new BigDecimal(df.format(topd));
			p = p.add(topping);
		}

		p = p.multiply(new BigDecimal(quantity));
		p = p.setScale(2, RoundingMode.HALF_EVEN);
		
		return p;
		
	}

	/**
	 * converts the integer size into text
	 * @return String
	 */
	public String getSizeAsString() {

		String size$ = "";
		switch (size) {
		case 0: size$ = sizeList[0]; break; //"Small"
		case 1: size$ = sizeList[1]; break; //"Medium"
		case 2: size$ = sizeList[2]; break; //"Large"
		case 3: size$ = sizeList[3]; break; //"Extra Large"
		default: size$ = "Error parsing size!";
		}

		return size$;
	}

	/**
	 * converts the integer crust style into text
	 * @return String
	 */
	public String getTypeAsString() {

		String crust$ = "";
		switch (crust) {
		case 0: crust$ = "Regular"; break;
		case 1: crust$ = "Thin"; break;
		case 2: crust$ = "Thick"; break;
		case 3: crust$ = "Deep Dish"; break;
		default: crust$ = "Error parsing crust style!";
		}

		return crust$;
	}

	/**
	 * status messages for pizzas in an order
	 * other statuses related to the order (such as "order being taken", "payment being accepted", etc.
	 * will be part of the Order class)
	 * @return String
	 */
	public String getStatusAsText() {

		switch(status) {
		case 0: return "Part of order being taken";
		case 1: return "In kitchen queue";
		case 2: return "Being made";
		case 3: return "In oven";
		case 4: return "Ready";
		}
		return "-1";

	}

	/**
	 * builds a list of the toppings on this pizza, separated by commas and enclosed in parentheses
	 * @return String
	 */
	public String getToppingsList() {

		String s = "(";
		
		for (int i = 0; i < toppings.size(); i++) {
			s += toppings.get(i).name;
			if (i == toppings.size() - 1) {
				s += ")";
			} else {
				s += ", ";
			}
		}
		return s;
	}


	/**
	 * provides a text description of this object for use in
	 * containers like JList
	 * @return String
	 */	
	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		String return$ = "[" + quantity + "] " + getSizeAsString() + ", " + getTypeAsString() + ", " 
						+ toppings.size() + " toppings " + getToppingsList() 
						+ ": " + nf.format(price)
						+ (!specialRequest.equals("Item Special Request")? " [SEE NOTE]": "")
						+ (isLowPrice? " [LOW PRICE PIZZA]":"");
				
		return return$;
	}

	/**
	 * returns row data for pizza food items
	 * "Quantity", "Size", "Type", "Toppings", "List", "Price", "Note"
	 */
	@Override
	public String[] getRow() {
		String rq$ = "";
		if (specialRequest.length() > 0 && !specialRequest.equals("Item Special Request")) rq$ = "See Request";
		String[] row = {""+quantity, 
				getSizeAsString(), 
				getTypeAsString(), 
				""+toppings.size(), 
				getToppingsList(), 
				nf.format(price), 
				rq$}; 
		return row;
	}
	
}

class Topping implements Comparable<Topping> {
	String name;
	double price;
	static boolean sortAlphabetically = true;
	Topping(String n, double p) {
		name = n;
		price = p;
	}
	/**
	 * we have to supply this method to define how we want Topping objects compared
	 * i.e., based on their name or their price?
	 */
	@Override
	public int compareTo(Topping o) {
		
		if (sortAlphabetically) {
			return name.compareTo(o.name);
		} else {
			// sort by the price:
			if (price < o.price) return -1;
			else if (price == o.price) return 0;
			else return 1;
		}
	}
}
