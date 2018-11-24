package ch15_money_and_calculations;

import java.text.DecimalFormat;


public class BankersRounding {

	public static void main(String[] args) {
		BankersRounding app = new BankersRounding();
	}
	
	BankersRounding() {
		
		DecimalFormat df = new DecimalFormat("####");
		double sumd = 0.0;
		long suml = 0, sumlrnd = 0, sumdfd = 0;
		
		System.out.println("d\t\t(long) d\tMath.round(d)\tBanker\'s");
		System.out.println("-------\t\t-------\t\t-------\t\t-------"); //tabs are fun!
		
		for (double d = 0.5; d < 10; d += 1.0) {
					
			sumd += d;
			
			long l = (long) d;
			suml += l;
			
			long lrnd = Math.round(d);
			sumlrnd += lrnd;
		
			//getting an actual double from decimalformat is slightly convoluted:
			double dfd = Double.parseDouble(df.format(d));
			sumdfd += dfd;
			System.out.println( d + "\t\t" + l + "\t\t" + lrnd + "\t\t" + df.format(d));
				
		}
		
		System.out.println("=======\t\t=======\t\t=======\t\t=======");
		System.out.println("$" + sumd + "\t\t$" + suml + "\t\t$" + sumlrnd + "\t\t$" + sumdfd);
		
	}
	
}
