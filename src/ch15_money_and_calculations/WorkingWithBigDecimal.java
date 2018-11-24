package ch15_money_and_calculations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;


public class WorkingWithBigDecimal {

	public static void main(String[] args) {
		WorkingWithBigDecimal app = new WorkingWithBigDecimal();
	}

	WorkingWithBigDecimal() {
	
		// input values:
		String price$ = "5.01";
		BigDecimal price = new BigDecimal(price$);
		BigDecimal persons = new BigDecimal("5");
		
		// receipt calculations:
		BigDecimal subtotal = (persons.multiply(price));
		output("Subtotal", subtotal, true);
		
		BigDecimal discount = (new BigDecimal("0.10")).multiply(subtotal);;
		output("Discount (10%)", discount, true);
		
		subtotal = subtotal.subtract(discount);
		output("Subtotal", subtotal, true);
		
		BigDecimal gratuity = (new BigDecimal("0.20")).multiply(subtotal);
		output("Gratuity (20%)", gratuity, true);
		
		subtotal = subtotal.add(gratuity);
		output("Total", subtotal, true);
		
		BigDecimal split3 = new BigDecimal("3");
		split3 = subtotal.divide(split3, MathContext.DECIMAL128);
		output("Split (3 ways)", split3, true);
		
	}
	
	
	NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
	Currency c = Currency.getInstance(Locale.US);
	int decimals = c.getDefaultFractionDigits(); // needs to match the currency system
	
	void output(String s, BigDecimal value, boolean round) {
					
		// set scale to round prior to output/save:
		if (round) value = value.setScale(decimals, RoundingMode.HALF_EVEN);
		
		// display output line at console:
		System.out.println(s + ": \t" + (s.length() < 6?"\t":"") + value.toPlainString() + "\t(" + nf.format(value.doubleValue()) + ")");
	}

}

