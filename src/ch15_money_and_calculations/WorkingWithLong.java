package ch15_money_and_calculations;

import java.text.NumberFormat;
import java.util.Locale;


public class WorkingWithLong {

	public static void main(String[] args) {
		WorkingWithLong app = new WorkingWithLong();
	}
	
	WorkingWithLong() {
				
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		
		long billL =  501L * 5L;
		System.out.println("Subtotal: " + format.format(billL/100.0d));
		
		//long discountL = (long) (.1*billL); // this truncates, rather than rounds
		long discountL = Math.round(billL*.1d); // need to use Math.round() instead
		System.out.println("Discount: " + format.format(discountL/100.0d));
		
		billL = billL - discountL;
		System.out.println("Subtotal (after discount): " + format.format(billL/100.0d));
		
		long gratL = Math.round(billL*.2d);
		System.out.println("Gratuity: " + format.format(gratL/100.0d));
		
		billL = billL + gratL;
		System.out.println("Total: " + format.format(billL/100.0d));
		
		long threeWaysL = Math.round(billL/3.0d);
		System.out.println("Split 3 ways: " + format.format(threeWaysL/100.0d));
		
	}
		
}
