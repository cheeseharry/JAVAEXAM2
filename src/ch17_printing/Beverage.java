package ch17_printing;


import java.awt.Image;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

public class Beverage extends FoodItem {

	int type;
	boolean adult;
	String[] sizeList = {"Little Matey\'s", "Medium", "Large", "Black Beard\'s Bucket"};
	
	@SuppressWarnings("serial")
	static Map<String, Double> baseDrinkPrices = new HashMap<String, Double>() {
		{
			put("Water", .5);
			put("Soda", 1.99);
			put("Beer", 2.99);
			put("Wine", 3.79);
			put("Parrot Cocktail", .25);
			put("Pirate Punch", 5.99);
		}
	};
	
	Beverage() {
		Random r = new Random();
		size = r.nextInt(4);
		type = r.nextInt(6);
		name = getTypeAsString();
		quantity = 1;
		
		price = getPrice();

		//specialRequest = "--";
		Image temp = Toolkit.getDefaultToolkit().getImage("single-beverage.png");
		productImage = new ImageIcon(temp.getScaledInstance(48, 64, Image.SCALE_SMOOTH));
	}
	
	String getTypeAsString() {
		String n$ = "";
		switch (type) {
		case 0: n$ = "Water"; break;
		case 1: n$ = "Soda"; break;
		case 2: n$ = "Beer"; break;
		case 3: n$ = "Wine"; break;
		case 4: n$ = "Parrot Cocktail"; break;
		case 5: n$ = "Pirate Punch"; break;
		default: n$ = "Error parsing type!";
		}

		return n$;
	}
	
	@Override
	public BigDecimal getPrice() {

		BigDecimal p = new BigDecimal(size);
		p = p.multiply(new BigDecimal(".79"));
		p = p.add(new BigDecimal(baseDrinkPrices.get(name).toString()));
		p = p.multiply(new BigDecimal(quantity));
		p = p.setScale(2, RoundingMode.HALF_EVEN);

		return p;
	}


	@Override
	String getSizeAsString() {
		String size$ = "";
		switch (size) {
		case 0: size$ = sizeList[0]; break; //"Little Matey\'s"; break;
		case 1: size$ = sizeList[1]; break; //"Medium"; break;
		case 2: size$ = sizeList[2]; break; //"Large"; break;
		case 3: size$ = sizeList[3]; break; //"Black Beard\'s Bucket of"; break;
		default: size$ = "Error parsing size!";
		}

		return size$;
	}

	@Override
	public String toString() {
		return "[" + quantity + "] " + getSizeAsString() + " " + name + ": " + nf.format(price) 
				+ (!specialRequest.equals("Item Special Request")? " [SEE NOTE]": "");
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
				"", 
				"", 
				nf.format(price), 
				rq$}; 
		return row;
	}
	
	/**
	 * returns receipt data for pizza food items
	 * "Quantity", "Size", "Type", "Price"
	 */
	@Override
	public String toReceiptString(int i) {
		String rq$ = "";
		if (specialRequest.length() > 0 && !specialRequest.equals("Item Special Request")) rq$ = "See Request";

		String s = "<html>" + (i+1) + ". " 
				+ quantity 
				+ " " + getSizeAsString() 
				+ " " + getTypeAsString() 
				+ "..... " 
				+ " " + nf.format(price)
				+ (rq$.equals("See Request")?("<br>"+specialRequest):"")
				+ "</html>";
		return s;
	}

}
