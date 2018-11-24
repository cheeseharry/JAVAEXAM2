package ch17_printing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
	 * Custom jpanel to keep the onscreen receipt from being overwritten
	 * by another window, etc.
	 * 
	 * To make this printable:
	 * 1. implement Printable
	 * 2. supply required print() method
	 * 
	 */
	public class ReceiptPanel extends JPanel implements Printable {

		Font f = new Font("Helvetica", Font.PLAIN, 10);
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);

		Image receiptIMG = Toolkit.getDefaultToolkit().getImage("ye-olde-receipt.png");
		ImageIcon receiptIMGICON = new ImageIcon(receiptIMG.getScaledInstance(240, 60, Image.SCALE_SMOOTH));
		BigDecimal total; 
		ArrayList<FoodItem> order;
		
		// constructor to share data:
		ReceiptPanel(BigDecimal b, ArrayList<FoodItem> a) {
			total = b;
			order = a;			
		}
		
		/**
		 * paintComponent() contains the code to draw the receipt:
		 */
		public void paintComponent(Graphics graphics) {
						
			this.removeAll();
			this.setLayout(null);

			// make it smooth:
			Graphics2D g2d = (Graphics2D) graphics;
			RenderingHints hints = new RenderingHints(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
			g2d.setRenderingHints(hints);

			// title graphic:
			JLabel title_LBL = new JLabel();
			title_LBL.setIcon(receiptIMGICON);
			title_LBL.setBounds(10, 0, 240, 60);
			add(title_LBL);
			
			
			// date/time stamp:
			Calendar cal = new GregorianCalendar();
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Helvetica", Font.PLAIN, 12));
			g2d.drawString("Capt. Jack Parrot\'s Pirate Pizza Palace", 24, 70);
			g2d.drawString((cal.get(Calendar.MONTH) + 1) 
					+ "/" + cal.get(Calendar.DAY_OF_MONTH) 
					+ "/" + cal.get(Calendar.YEAR)
					+ "  " + cal.get(Calendar.HOUR_OF_DAY) 
					+ ":" + cal.get(Calendar.MINUTE) 
					+ " " + (cal.get(Calendar.AM_PM) == 0 ? "AM" : "PM"), 66, 90);

			
			// list of ordered items:
			JLabel[] labels = new JLabel[order.size()];
			int y = 120;
			
			for (int j = 0; j < order.size(); j++) {
				
				// can use JLabel's to precisely size and place text (but a JTextArea would be easier)
				labels[j] = new JLabel(order.get(j).toReceiptString(j));
				labels[j].setFont(f);
				int h = (order.get(j).getClass() == Pizza.class?24:12);
				if (j>0) {
					// after first line:
					y = labels[j-1].getY() + labels[j-1].getHeight() + 5;
					
				}
				
				labels[j].setBounds(10, y, 240, h);
				add(labels[j]);
			}

			
			// total:
			g2d.setFont(new Font("Helvetica", Font.PLAIN, 18));
			g2d.drawString("Total: " + nf.format(total), 74, getHeight()-55);

			
			// slogan:
			g2d.setFont(f.deriveFont(Font.ITALIC, 15));
			g2d.drawString("Pay up or walk the plank!", 47, getHeight()-30);

		}

		/**
		 * print() is required when we implement printable:
		 */
		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
				throws PrinterException {
			if (pageIndex > 0) {
				return(NO_SUCH_PAGE);
			} else {
				Graphics2D g2d = (Graphics2D) graphics;
				g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
				paint(g2d);
				return(PAGE_EXISTS);
			}
		}
	}