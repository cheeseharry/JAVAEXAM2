package ch15_money_and_calculations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class RoundingPlayground extends JFrame implements ActionListener {

	/**
	 * Creates a JTable filled with random restaurant subtotal bills (as big decimal),
	 * then calculates and add tax and tip amounts, to create a total bill.
	 * User can choose whether intermediate calculations for tax and tip are rounded or not
	 * and also whether the double calculations are rounded using default (half up) or
	 * banker's (half even) rounding.
	 */
	private static final long serialVersionUID = 1010L;
	JButton generateB = new JButton("Generate");
	JButton clearB = new JButton("Clear Table");

	JCheckBox intermediateCB = new JCheckBox("Round intermediate results (tax & tip calculations)", true);
	JCheckBox doubleBankersRoundingCB = new JCheckBox("Use banker's rounding on doubles", false);

	static boolean _roundIntermediate = true;
	static boolean _doubleBankersRounding = false;

	DefaultTableModel model;
	JTable table;

	NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.US);

	public static void main(String[] args) {
		RoundingPlayground app = new RoundingPlayground();
		app.setSize(530, 330);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setName("Rounding Playground");
		app.setTitle(app.getName());
		app.setVisible(true);
	}

	RoundingPlayground() {

		Object[] columnNames = {"Subtotal (Big D)", "Total (double)", "Total (Big D)", "Are totals the same?"};
		model = new DefaultTableModel(12,4) {
			public boolean isCellEditable(int row, int column) {
				return false; // makes table cells not-editable
			}
		}; // rows, columns
		model.setColumnIdentifiers(columnNames);

		table = new JTable(model);

		//show the grid lines:
		table.setGridColor(Color.LIGHT_GRAY);

		//align cells right (since these are monetary amounts):
		DefaultTableCellRenderer alignRight = new DefaultTableCellRenderer();
		alignRight.setHorizontalAlignment(SwingConstants.RIGHT);
		for (int c = 0; c < model.getColumnCount(); c++) table.getColumnModel().getColumn(c).setCellRenderer(alignRight);

		//put table on a scroll pane to make it scrollable:
		JScrollPane sp = new JScrollPane(table);
		add(sp, BorderLayout.CENTER);

		//buttons:
		generateB.setToolTipText("Generates 10 random subtotals to which\ntax of 7.5% and a tip of 20% are calculated and added");
		intermediateCB.setToolTipText("If you don't round these, they may appear off on displayed/printed results!");
		doubleBankersRoundingCB.setToolTipText("The default is HALF_UP");

		//setup our gui:
		JPanel southP = new JPanel();
		southP.setLayout(new BorderLayout());

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2,1));
		buttons.add(generateB);
		buttons.add(clearB);
		buttons.setPreferredSize(new Dimension(200, 76));

		southP.add(buttons, BorderLayout.EAST);

		JPanel choicesP = new JPanel();
		choicesP.setLayout(new GridLayout(3,1));
		JLabel settingsLB = new JLabel("   Settings:  ");
		Font f = new Font("Helvetica", Font.PLAIN, 12);
		settingsLB.setFont(f);
		intermediateCB.setFont(f);
		doubleBankersRoundingCB.setFont(f);
		choicesP.add(settingsLB);
		choicesP.add(intermediateCB);
		choicesP.add(doubleBankersRoundingCB);

		southP.add(choicesP, BorderLayout.WEST);

		add(southP, BorderLayout.SOUTH);

		//add listeners last:
		generateB.addActionListener(this);
		clearB.addActionListener(this);
		intermediateCB.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == generateB) {

			model.setRowCount(0);

			// generate random subtotals and calculated totals:
			for (int i = 0; i < 10; i++) {
				RestaurantRowData rd = new RestaurantRowData();
				rd.randomize();
				model.insertRow(i, rd.getRow());
			}

			// sums for each data type:
			double sumD = 0.0;
			BigDecimal sumBD = new BigDecimal("0.0");

			for (int j = 0; j < model.getRowCount(); j++) {
				sumD += Double.parseDouble((String) model.getValueAt(j, 1)); // is this the easiest way??
				BigDecimal addend = new BigDecimal((String) model.getValueAt(j, 2));
				sumBD = sumBD.add(addend);
			}

			// round the totals to dollars and cents:
			if (this._doubleBankersRounding) {
				sumD = Double.parseDouble(moneyFormat.format(sumD)); // round half-even
			} else {
				sumD = Math.round(sumD*100.0)/100.0; // round half-up
				String sumD$ = Double.toString(sumD);
				if (sumD$.substring(sumD$.indexOf("."), sumD$.length()).length() < 3) {
					sumD$ += "0";
					sumD = Double.parseDouble(sumD$);
				}
			}

			sumBD = sumBD.setScale(2, BigDecimal.ROUND_HALF_EVEN);

			Object[] line = {"", "----------------", "----------------", ""};
			model.insertRow(model.getRowCount(), line);
			Object[] totals = {"", moneyFormat.format(sumD), "$" + sumBD.toPlainString(), Boolean.toString(sumD == Double.parseDouble(sumBD.toPlainString()))};
			model.insertRow(model.getRowCount(), totals);

		} else if (e.getSource() == clearB) {
			model.setRowCount(0);
		} else if (e.getSource() == intermediateCB) {
			if (intermediateCB.isSelected()) _roundIntermediate = true;
			else _roundIntermediate = false;
		} else if (e.getSource() == doubleBankersRoundingCB) {
			if (doubleBankersRoundingCB.isSelected()) _doubleBankersRounding = true;
			else _doubleBankersRounding = false;
		}
	}
}

class RestaurantRowData {

	/* Generates a bill subtotal without tax or tip.
	 * This subtotal is a random big decimal and only has
	 * 2 decimal places (since menu items aren't going to
	 * be listed in the 1000th's). That is, it is a realistic
	 * value and it is stored precisely.
	 * 
	 * But, if we choose to round doubles using bankers rounding,
	 * how can the results differ from big decimal?
	 * e.g., what if subtotal is 18.50?
	 * 
	 */
	double subtotalD, totalD; // d

	BigDecimal subtotalBD, totalBD; // bd;

	String subtotalBD$, totalD$, totalBD$;

	private Random r = new Random();
	// using decimal format because the currency symbol attached by NumberFormat will
	// break our code when we try to parse it, for example, as a double
	private DecimalFormat noCurrencySymbol = new DecimalFormat("###0.00");


	public RestaurantRowData() {
		subtotalBD = new BigDecimal("0.00");
		subtotalBD = subtotalBD.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		subtotalD = 0.0;
	}

	public double roundDouble(double d, boolean bank) {
		if (bank) {
			return Double.parseDouble(noCurrencySymbol.format(d)); // round half-even
		} else {
			d = Math.round(d*100.0)/100.0; // round half-up
			return d;
		}
	}

	public void randomize() {

		// need to start with a random number as a string, to use to init the big decimal:
		String randNumber$ = "";
		randNumber$ += (r.nextInt(50)+1) + "." + r.nextInt(10) + r.nextInt(10);

		// column 0 random restaurant bills in big decimal (only 2 decimal places)
		subtotalBD = new BigDecimal(randNumber$);
		subtotalBD = subtotalBD.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		// column 1 (subtotal + tax + tip) in doubles
		subtotalD = Double.parseDouble(subtotalBD.toPlainString());

		double taxD = subtotalD*0.075;
		if (RoundingPlayground._roundIntermediate) taxD = roundDouble(taxD, RoundingPlayground._doubleBankersRounding);
		double tipD = (subtotalD + taxD) * 0.2;
		if (RoundingPlayground._roundIntermediate) tipD = roundDouble(tipD, RoundingPlayground._doubleBankersRounding);
		totalD = subtotalD + taxD + tipD;
		totalD = roundDouble(totalD, RoundingPlayground._doubleBankersRounding);

		// column 2 (subtotal + tax + tip) in big decimals
		BigDecimal taxBD = subtotalBD.multiply(new BigDecimal("0.075"));
		if (RoundingPlayground._roundIntermediate) {
			taxBD = taxBD.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		} else {
			taxBD = taxBD.setScale(16, BigDecimal.ROUND_HALF_EVEN);
		}

		BigDecimal tipBD = subtotalBD.add(taxBD);
		tipBD = tipBD.multiply(new BigDecimal("0.2"));
		if (RoundingPlayground._roundIntermediate) {
			tipBD = tipBD.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		} else {
			tipBD = tipBD.setScale(16, BigDecimal.ROUND_HALF_EVEN);
		}
		totalBD = subtotalBD.add(taxBD).add(tipBD);
		totalBD = totalBD.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		doString();
	}

	public void doString() {
		// these are formatted as US currency amounts:
		subtotalBD$ = subtotalBD.toPlainString();
		//totalD$ = Double.toString(totalD); // this approach will "drop" trailing zeros (e.g., 17.50 becomes 17.5)
		totalD$ = noCurrencySymbol.format(totalD);
		totalBD$ = totalBD.toPlainString();
	}

	public String[] getRow() {
		String row[] = {subtotalBD$, totalD$, totalBD$, Boolean.toString(totalD$.equals(totalBD$))};
		return row;
	}

	public String toString() {
		return subtotalBD$ + "\t" + totalD$ + "\t" + totalBD$ + "\t" + Boolean.toString(totalD$.equals(totalBD$));
	}
}

