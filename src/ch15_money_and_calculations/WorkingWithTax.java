package ch15_money_and_calculations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;


public class WorkingWithTax {

	public static void main(String[] args) {
		WorkingWithTax app = new WorkingWithTax();
	}

	WorkingWithTax() {

		Currency c = Currency.getInstance(Locale.US);
		int decimals = c.getDefaultFractionDigits(); // needs to match the currency system
		BigDecimal orderTotal = BigDecimal.ZERO;

		// header:
		System.out.println("Item\t\tAmount\t\tAs Money\tTotal");
		
		
		// price each:
		BigDecimal priceEach = new BigDecimal("10.01"); // famous parrot poppers appetizer plate

		
		// subtotal for 5 orders:
		BigDecimal subtotal = (priceEach.multiply(new BigDecimal("5")));
		System.out.print("Subtotal:\t" + subtotal.toPlainString() + "\t\t");
		subtotal = subtotal.setScale(decimals, BigDecimal.ROUND_HALF_EVEN);
		orderTotal = orderTotal.add(subtotal);
		System.out.println("$" + subtotal.toPlainString() + "\t\t" + orderTotal.toPlainString());
		
		
		// 10% discount:
		BigDecimal discount = (new BigDecimal("0.10")).multiply(subtotal);
		System.out.print("Discount (10%):\t" + discount.toPlainString() + "\t\t");
		// round as money:
		discount =	discount.setScale(decimals, BigDecimal.ROUND_HALF_EVEN);
		orderTotal = orderTotal.subtract(discount);
		System.out.println("$" + discount.toPlainString() + "\t\t" + orderTotal.toPlainString());
		
		
		// add tax (round half up):
		BigDecimal tax = new BigDecimal("0.0825");
		tax = subtotal.multiply(tax);
		System.out.print("Tax (8.25%):\t" + tax.toPlainString() + "\t");
		// round as tax:
		tax = tax.setScale(decimals, BigDecimal.ROUND_HALF_UP);
		orderTotal = orderTotal.add(tax);
		System.out.println("$" + tax.toPlainString() + "\t\t" + orderTotal.toPlainString());
		
		
		// tip:
		BigDecimal tip = (new BigDecimal("0.20")).multiply(subtotal);
		System.out.print("Tip (20%):\t" + tip.toPlainString() + "\t\t");
		// round as money:
		tip = tip.setScale(decimals, BigDecimal.ROUND_HALF_EVEN);
		orderTotal = orderTotal.add(tip);
		System.out.println("$" + tip.toPlainString() + "\t\t" + orderTotal.toPlainString());
		
		
		// split:
		BigDecimal split = orderTotal.divide(new BigDecimal("7"), MathContext.DECIMAL32);
		System.out.print("Split (7 way):\t" + split.toPlainString() + "\t");
		// round as money:
		split = split.setScale(decimals, BigDecimal.ROUND_HALF_EVEN);
		System.out.println("$" + split.toPlainString());

	}

}

