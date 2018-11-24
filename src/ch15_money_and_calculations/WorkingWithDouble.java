package ch15_money_and_calculations;

import java.text.NumberFormat;
import java.util.Locale;


public class WorkingWithDouble {

	public static void main(String[] args) {
		WorkingWithDouble app = new WorkingWithDouble();
	}

	WorkingWithDouble() {

		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		// jack parrot's pizza palace
		// add a parrot for a penny special
		// everything in double
		double bill = 5.01*5.0; 

		System.out.println("Subtotal: " + bill + " (formatted = " + format.format(bill) + ")"); 
		double discount = bill * .1;  //10% discount
		System.out.println("discount:" + discount + " (formatted = " + format.format(discount) + ")");
		bill = bill - discount;
		System.out.println("Subtotal (with discount): " + bill + " (formatted = " + format.format(bill) + ")"); 
		double gratuity = .2*bill; // 20$ grat
		System.out.println("Gratuity: " + gratuity + " (formatted = " + format.format(gratuity) + ")");
		bill = bill + gratuity; 
		System.out.println("Total (with gratuity): " + bill + " (formatted = " + format.format(bill) + ")"); 
		bill = bill/3.0; 
		System.out.println("Split (3 payers): " + bill + " (formatted = " + format.format(bill) + ")"); 

	}
}
