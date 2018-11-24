package ch17_printing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class WebAd extends FoodItem {

    /**
     * calculates the price based on the pizza's attributes
     * @return BigDecimal
     */
    @Override
    public BigDecimal getPrice() {
        BigDecimal calcOne = new BigDecimal(1);
        BigDecimal divideBy100 = new BigDecimal(100);

        BigDecimal unitPrice = new BigDecimal(Math.ceil(Math.random()*100));

        BigDecimal discountRate = new BigDecimal(Math.ceil(Math.random()*50));
        BigDecimal discountRateDivide = discountRate.divide(divideBy100);
        BigDecimal discountRateCalc = calcOne.subtract(discountRateDivide); // 1-0.25=0.75

        BigDecimal taxRate = new BigDecimal(8.00);
        BigDecimal taxRateDivide = taxRate.divide(divideBy100); //0.08
        BigDecimal taxRateCalc = taxRateDivide.add(calcOne); // *1.08


        BigDecimal adjustedPrice = new BigDecimal(1);

        adjustedPrice= adjustedPrice.multiply(unitPrice);
        adjustedPrice= adjustedPrice.multiply(discountRateCalc);
        adjustedPrice= adjustedPrice.multiply(taxRateCalc);


        // base price
        //double p = (3.0*(size+1) + 3.0*(crust+1));
        BigDecimal p = new BigDecimal("3");
        p = p.multiply(new BigDecimal(new Long(size+2L).toString()));


        p = p.multiply(new BigDecimal(quantity));
        p = p.setScale(2, RoundingMode.HALF_EVEN);

        return p;

    }

    String getTypeAsString() {
        String n$ = "";
        switch (type) {
            case 0: n$ = "Parrot Pop"; break;
            case 1: n$ = "Dessert Pizza"; break;
            case 2: n$ = "Chocolate Pirate Bar"; break;
            default: n$ = "Error parsing type!";
        }

        return n$;
    }



    @Override
    String getSizeAsString() {
        String size$ = "";

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
