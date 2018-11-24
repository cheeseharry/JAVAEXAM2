package ch15_money_and_calculations;

import java.text.DecimalFormat;
import java.util.Random;


public class BankersRounding2 {

	public static void main(String[] args) {
		BankersRounding2 app = new BankersRounding2();
	}
	
	BankersRounding2() {
		
		Random r = new Random();
		DecimalFormat df = new DecimalFormat("###0.00");
		double sumd = 0.0, sumdfd = 0.0;
		long suml = 0, sumlrnd = 0;
		
		System.out.println("d\t\t\t(long) d\tMath.round(d)\tBanker\'s");
		System.out.println("----------------------------------------------------------------------"); 
		
		for (int i = 0; i < 10; i++) { // can plug in any number of repetitions

			double d = 10.0d * r.nextDouble();
			sumd += d;
			
			long l = (long) (d*100.0d);
			suml += l;
			
			long lrnd = Math.round(d*100d);
			sumlrnd += lrnd;
		
			//getting an actual double from decimalformat is slightly convoluted:
			double dfd = Double.parseDouble(df.format(d));
			sumdfd += dfd;
			System.out.println( d + "\t\t" + l + "\t\t" + lrnd/100.0 + "\t\t" + dfd/*df.format(d)*/);
				
		}
		
		System.out.println("======================================================================");
		// remember that (long) d and Math.round(d) are 100x actual dollar amount, so we scale them back for display as money
		System.out.println("$" + sumd + "\t\t$" + suml/100.0 + "\t\t$" + sumlrnd/100.0 + "\t\t$" + df.format(sumdfd));
		
		System.out.println("\nSummary:");
		System.out.println("(long) d: " + 1.0*suml/sumd);
		System.out.println("round(d): " + 1.0*sumlrnd/sumd);
		System.out.println("banker's: " + 100.0*sumdfd/sumd);
		
	}
		
}
