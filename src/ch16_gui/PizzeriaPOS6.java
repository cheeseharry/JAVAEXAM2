package ch16_gui;


import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * PizzeriaPOS6
 * 
 * @author john_carlson@baylor.edu
 * 
 * 
 *
 */
@SuppressWarnings("serial")
public class PizzeriaPOS6 extends JFrame implements ActionListener, ListSelectionListener, KeyListener, ItemListener, MouseListener {

	// attributes
	BigDecimal totalPrice;
	NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
	DecimalFormat df = new DecimalFormat("####.00");

	ArrayList<FoodItem> order = new ArrayList<FoodItem>();
	FoodItem food;

	// GUI
	// north region:
	JLabel titleLBL = new JLabel();
	JPanel eastPanel = new JPanel();
	Image titleIMG = Toolkit.getDefaultToolkit().getImage("capt-jacks.png");
	ImageIcon titleIMGICON = new ImageIcon(titleIMG.getScaledInstance(1024, 72, Image.SCALE_SMOOTH));
	Image title_overIMG = Toolkit.getDefaultToolkit().getImage("parrot-cocktail.png");
	ImageIcon title_overIMGICON = new ImageIcon(title_overIMG.getScaledInstance(1024, 72, Image.SCALE_SMOOTH));
	Image captainJackParrotIMG = Toolkit.getDefaultToolkit().getImage("captain-jack-parrot.png");
	ImageIcon captainJackParrotIMGICON = new ImageIcon(captainJackParrotIMG.getScaledInstance(60, 80, Image.SCALE_SMOOTH));
	
	// west region:
	JButton addPizzaBtn = new JButton("");
	JButton addBevBtn = new JButton("");
	JButton addDessertBtn = new JButton("");

	// east region:
	JLabel detailsTitleAreaLbl = new JLabel("  Order Total  ");
	JLabel totalPriceLbl = new JLabel(" $0.00 ");
	JLabel itemDetailsTitleLbl = new JLabel("Item Special Request:");
	JTextField itemSpecialRequestTF = new JTextField();

	JButton chgQuantityBtn = new JButton("Change Quantity");
	JButton chgSizeBtn = new JButton("Change Size");
	JButton setSpecialRequest = new JButton("Make Special Request");

	ButtonGroup radioButtons = new ButtonGroup();
	JRadioButton dineInRB, takeAwayRB, deliverRB;
	JTextArea addressTA = new JTextArea();
	JCheckBox extraSauceCB = new JCheckBox("Extra parrot sauce", false);
	JCheckBox kidsBookCB = new JCheckBox("Little matey\'s coloring book", false);
	JCheckBox orderInstructionsCB = new JCheckBox("Special instructions:", false);
	JTextArea orderInstructionsTA = new JTextArea("Order Special Instructions");

	// south region:
	JButton removeItemBtn = new JButton("Remove Selected Item");
	JButton cancelOrderBtn = new JButton("Cancel Order"); // removes all data
	JButton placeOrderBtn = new JButton("Place Order");

	// center region:
	DefaultListModel<FoodItem> data = new DefaultListModel<FoodItem>();
	JList<FoodItem> orderList = new JList<FoodItem>(data);
	JScrollPane jsp = new JScrollPane(orderList);

	// sounds:
	AudioClip argh, ahoy, yoho, onTheWay;


	/** setup app characteristics
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PizzeriaPOS6 app = new PizzeriaPOS6();
		app.setSize(1024, 768);
		app.setTitle("POS Version 6");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
		app.setFocus();
	}

	PizzeriaPOS6() {

		// --------------------------------------------------------- setup
		URL arghURL, yohoURL, ahoyURL, onTheWayURL;
		try {
			// This syntax is older but works well:
			arghURL = new URL("file:argh.wav");
			argh = Applet.newAudioClip(arghURL);

			yohoURL = new URL("file:yoho.au");
			yoho = Applet.newAudioClip(yohoURL);

			ahoyURL = new URL("file:ahoy-matey.wav");
			ahoy = Applet.newAudioClip(ahoyURL);

			onTheWayURL = new URL("file:on-the-way.wav");
			onTheWay = Applet.newAudioClip(onTheWayURL);

		} catch (MalformedURLException frack) {
			frack.printStackTrace();
		}

		Font smallF = new Font("Helvetica", Font.PLAIN, 11);
		Font smallPirateF = new Font("Black Sam's Gold", Font.PLAIN, 20);


		// ============================== setup gui:
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());

		// ------------------------------ NORTH:
		titleLBL.setIcon(titleIMGICON);
		p.add(titleLBL, BorderLayout.NORTH);


		// ------------------------------ CENTER:
		jsp.setBorder(BorderFactory.createTitledBorder("Order Items"));
		p.add(jsp, BorderLayout.CENTER);
		orderList.setFont(new Font("Courier", Font.PLAIN, 14));

		// ------------------------------ WEST:
		// load images for the buttons:
		Image temp = Toolkit.getDefaultToolkit().getImage("pepperoni-pizza.png");
		ImageIcon pizzaImg = new ImageIcon(temp.getScaledInstance(128, 64, Image.SCALE_SMOOTH));
		Image temp2 = Toolkit.getDefaultToolkit().getImage("single-beverage.png");
		ImageIcon bevImg = new ImageIcon(temp2.getScaledInstance(72, 110, Image.SCALE_SMOOTH));
		Image temp3 = Toolkit.getDefaultToolkit().getImage("parrot-pop.png");
		ImageIcon dessertImage = new ImageIcon(temp3.getScaledInstance(60, 110, Image.SCALE_SMOOTH));

		addPizzaBtn.setIcon(pizzaImg);
		addBevBtn.setIcon(bevImg);
		addDessertBtn.setIcon(dessertImage);

		JPanel westButtons = new JPanel();
		westButtons.add(addPizzaBtn);
		westButtons.add(addBevBtn);
		westButtons.add(addDessertBtn);
		westButtons.setLayout(new GridLayout(westButtons.getComponentCount(),1));
		westButtons.setBorder(BorderFactory.createTitledBorder("Add Items"));

		p.add(westButtons, BorderLayout.WEST);


		// ------------------------------- SOUTH:
		JPanel southP = new JPanel();
		southP.setLayout(new GridLayout(2,1));

		itemDetailsTitleLbl.setFont(smallPirateF);
		itemDetailsTitleLbl.setHorizontalAlignment(JLabel.LEFT);
		itemDetailsTitleLbl.setVerticalAlignment(JLabel.BOTTOM);

		itemSpecialRequestTF.setFont(smallF);
		itemSpecialRequestTF.setText("Item special request");
		itemSpecialRequestTF.setEnabled(false);
		itemSpecialRequestTF.setPreferredSize(new Dimension(400, 32));

		JPanel southButtons = new JPanel();
		southButtons.setLayout(new FlowLayout(FlowLayout.LEFT));

		southButtons.add(setSpecialRequest);
		southButtons.add(chgQuantityBtn);
		southButtons.add(chgSizeBtn);
		southButtons.add(removeItemBtn);
		southButtons.add(cancelOrderBtn);

		JPanel southDetails = new JPanel();
		southDetails.setLayout(new FlowLayout(FlowLayout.LEFT));
		southDetails.add(itemDetailsTitleLbl);
		southDetails.add(itemSpecialRequestTF);

		southP.add(southButtons);
		southP.add(southDetails);

		southP.setBorder(BorderFactory.createTitledBorder("Edit Selected Item"));

		p.add(southP, BorderLayout.SOUTH);


		// --------------------------------- EAST:
		// set the width to override default borderlayout width:
		eastPanel.setPreferredSize(new Dimension(200, getPreferredSize().height));

		eastPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));

		detailsTitleAreaLbl.setFont(smallPirateF);

		totalPriceLbl.setFont(new Font("Courier", Font.PLAIN, 36));
		totalPriceLbl.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		totalPriceLbl.setForeground(Color.GREEN);
		totalPriceLbl.setBackground(Color.BLACK);
		totalPriceLbl.setOpaque(true);
		totalPriceLbl.setPreferredSize(new Dimension(180, 40));
		totalPriceLbl.setHorizontalAlignment(JLabel.CENTER);


		// radio buttons:
		dineInRB = new JRadioButton("Dine in", true);
		dineInRB.setFont(smallF);

		takeAwayRB = new JRadioButton("Take away", false);
		takeAwayRB.setFont(smallF);

		deliverRB = new JRadioButton("Deliver:", false);
		deliverRB.setFont(smallF);

		radioButtons.add(dineInRB);
		radioButtons.add(takeAwayRB);
		radioButtons.add(deliverRB);

		// address text area:
		addressTA.setFont(smallF);
		addressTA.setPreferredSize(new Dimension(180, 48));
		addressTA.setText("Delivery Address");
		addressTA.setEnabled(false);

		// check boxes:
		extraSauceCB.setFont(smallF);
		kidsBookCB.setFont(smallF);
		orderInstructionsCB.setFont(smallF);

		// order instructions text area:
		orderInstructionsTA.setFont(smallF);
		orderInstructionsTA.setPreferredSize(new Dimension(180, 60));
		orderInstructionsTA.setEnabled(false);

		// panels to group ui components on east panel:
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(3,1));
		radioPanel.setPreferredSize(new Dimension(180, 66));
		radioPanel.setAlignmentY(RIGHT_ALIGNMENT);
		radioPanel.add(dineInRB);
		radioPanel.add(takeAwayRB);
		radioPanel.add(deliverRB);

		JPanel checkPanel = new JPanel();
		checkPanel.setLayout(new GridLayout(3,1));
		checkPanel.setPreferredSize(new Dimension(180, 66));
		checkPanel.add(extraSauceCB);
		checkPanel.add(kidsBookCB);
		checkPanel.add(orderInstructionsCB);

		// size the place order button to fill remaining space:
		placeOrderBtn.setPreferredSize(new Dimension(180, 200));

		// add components:
		eastPanel.add(detailsTitleAreaLbl);
		eastPanel.add(totalPriceLbl);
		eastPanel.add(radioPanel);
		eastPanel.add(addressTA);
		eastPanel.add(checkPanel);
		eastPanel.add(orderInstructionsTA);
		eastPanel.add(placeOrderBtn); // resize larger


		p.add(eastPanel, BorderLayout.EAST);

		add(p);

		// register default listeners last:
		registerListeners(p);

		// register other specific listeners:
		titleLBL.addMouseListener(this);

	}

	/** utility methods to attach default listeners to all
	 *  panel components (recursive)
	 *  
	 * @param p
	 */
	void registerListeners(JComponent p) {
		for (int j = 0; j < p.getComponentCount(); j++) {
			Object o = p.getComponent(j);
			if (o.getClass() == JButton.class) {
				((JButton) o).addActionListener(this);
				((JButton) o).addKeyListener(this);		

			} else if (o.getClass() == JTextArea.class) {
				((JTextArea) o).addKeyListener(this);

			} else if(o.getClass() == JTextField.class) {
				((JTextField) o).addKeyListener(this);
				((JTextField) o).addActionListener(this);

			} else if (o.getClass() == JCheckBox.class) {
				((JCheckBox) o).addKeyListener(this);
				((JCheckBox) o).addItemListener(this);

			} else if (o.getClass() == JRadioButton.class) {
				((JRadioButton) o).addKeyListener(this);
				((JRadioButton) o).addItemListener(this);

			} else if (o.getClass() == JList.class) {
				((JList) o).addKeyListener(this);
				((JList) o).addListSelectionListener(this);

			} else if (o.getClass() == JPanel.class || o.getClass() == JScrollPane.class || o.getClass() == JViewport.class) {
				registerListeners((JComponent) o);

			}
		}
	}


	/**
	 * note that focus can't be set in the constructor or before the gui is visible
	 */
	private void setFocus() {
		addPizzaBtn.requestFocus();
	}

	/** 
	 * A convenience method to prompt for a new size and update the FoodItem object:
	 * 
	 * @param tmp
	 * @param list
	 * @param defaultSize
	 * @return
	 */
	FoodItem getNewSize(FoodItem tmp, String[] list, String defaultSize) {

		// show dialog with sizeList for this Beverage object:
		String ns$ = (String) JOptionPane.showInputDialog(null, "Select new size:", "Change Size", 
				JOptionPane.QUESTION_MESSAGE, tmp.productImage, list, defaultSize);

		// have to get index associate with the selected size string:
		int idx = 0;
		for (String s: list) {
			if (s.equals(ns$)) {
				tmp.size = idx;
				break;
			} else {
				idx++;
			}
		}

		// update the price:
		tmp.price = tmp.getPrice();

		// return the result:
		return tmp;

	}

	// for a forthcoming "lowest priced pizza is free" promotion:
	void updateLowPrice() {

		BigDecimal min = new BigDecimal(Double.MAX_VALUE);
		int minAt = -1;

		// scans through the order to find the lowest price
		for (int i = 0; i < order.size(); i++) {
			Object o = order.get(i);
			if (o.getClass() == Pizza.class) {
				Pizza p = (Pizza) o;

				if (p.price.compareTo(min) == -1) {
					min = p.price;
					p.isLowPrice = true;

					// undo previous low price:
					if ( minAt > -1 ) {
						Pizza undo = (Pizza) order.get(minAt);
						undo.isLowPrice = false;
					}
					minAt = i;
				}

				//order.set(i, p); // TODO: need on windows??

			}
		}
	}

	/**
	 * processed the orders in the list and recalculates totalPrice
	 */
	public void updateOrder() {

		updateLowPrice();

		// clear item details
		itemSpecialRequestTF.setText("");

		// update the order and total
		if (data.size() > 0) data.clear();
		totalPrice = BigDecimal.ZERO;

		for (int i = 0; i < order.size(); i++) {
			data.addElement(order.get(i));
			totalPrice = totalPrice.add(new BigDecimal(df.format(order.get(i).price)));
		}

		totalPrice = totalPrice.setScale(2, RoundingMode.HALF_EVEN);
		totalPriceLbl.setText(nf.format(totalPrice));

	}

	/**
	 * clears the order and resets the GUI
	 */
	public void clearOrder() {
		
		removeAllItems();

		// reset ui:
		this.addressTA.setText("");
		this.dineInRB.setSelected(true);
		this.extraSauceCB.setSelected(false);
		this.kidsBookCB.setSelected(false);
		this.orderInstructionsCB.setSelected(false);
		this.orderInstructionsTA.setText("");
		this.orderInstructionsTA.setEnabled(false);

	}
	
	/** 
	 * removes all items in the cart and updates total
	 */
	public void removeAllItems() {
		data.clear();
		order.clear();
		updateOrder();
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == addPizzaBtn) {

			food = new Pizza();
			order.add(food);

		} else if (e.getSource() == addBevBtn) {

			food = new Beverage();
			order.add(food);

		} else if (e.getSource() == addDessertBtn) {

			food = new Dessert();
			order.add(food);

		} else if (e.getSource() == chgQuantityBtn) {

			int i = orderList.getSelectedIndex();

			if (i > -1) {

				String nq$ = JOptionPane.showInputDialog("New Quantity:");
				int nq = -1;
				try {
					nq = Integer.parseInt(nq$);
					if (nq > 0) {
						FoodItem tmp = order.get(i);
						tmp.quantity = nq;
						tmp.price = tmp.getPrice();
						order.set(i, tmp);

					} else if (nq == 0) {
						order.remove(i);

					} else {
						JOptionPane.showMessageDialog(this, "That is not a valid quantity!", "Error", 0); 

					}

				} catch (NumberFormatException x_x) {
					JOptionPane.showMessageDialog(this, "That is not a valid quantity!", "Error", 0); 
				}

			} else {
				JOptionPane.showMessageDialog(this, "Please select an item to change quantity!", "Attention", 0); 
			}


		} else if (e.getSource() == chgSizeBtn) {

			int i = orderList.getSelectedIndex();

			if (i > -1) {

				FoodItem tmp = order.get(i); // tmp could be a beverage, pizza, or dessert... need to know...

				if (tmp.getClass() == Beverage.class) {

					// show input dialog for this Beverage object:
					tmp = this.getNewSize(tmp, ((Beverage) tmp).sizeList, "Large");
					order.set(i, tmp);

				} else if (tmp.getClass() == Pizza.class) {

					// show input dialog for this Pizza object:
					tmp = this.getNewSize(tmp, ((Pizza) tmp).sizeList, "Large");
					order.set(i, tmp);

				} else if (tmp.getClass() == Dessert.class) {

					tmp = this.getNewSize(tmp, ((Dessert) tmp).sizeList, "Regular");
					order.set(i, tmp);

				}

			} else {
				JOptionPane.showMessageDialog(this, "Please select an item to change size!", "Attention", 0); 
			}

		} else if (e.getSource() == removeItemBtn) {

			if (removeItemBtn.getText().equals("Remove ALL Items")) {
				//clearOrder();
				removeAllItems();
				
			} else {
				int i = orderList.getSelectedIndex();

				if (i > -1) {
					itemDetailsTitleLbl.setText("Item Special Request:");
					order.remove(i);
				} else {
					argh.play();
					JOptionPane.showMessageDialog(this, "Please select an item to remove!", "Attention", 0); 
				}
			}

		} else if (e.getSource() == setSpecialRequest) {

			int i = orderList.getSelectedIndex();

			if (i > -1) {

				// get current request, place in tf, and enable for editing
				FoodItem food = order.get(i);
				String request$ = food.specialRequest;

				itemSpecialRequestTF.setText(request$);
				itemSpecialRequestTF.setEnabled(true);
				if (request$.equals("--")) {
					itemSpecialRequestTF.setText("");
				}

				itemSpecialRequestTF.requestFocusInWindow();
				// when the user hits "enter", the key listener will get the text they entered.

			} else {
				argh.play();
				JOptionPane.showMessageDialog(this, "Please select an item to attach this request to!", "Attention", 0);
			}

		} else if (e.getSource() == placeOrderBtn) {

			if (order.size() > 0) {
				onTheWay.play();
				totalPrice = totalPrice.setScale(2, RoundingMode.HALF_EVEN);
				JOptionPane.showMessageDialog(this, "Payment of " + nf.format(totalPrice) + " received. Your pizzas are on the way!");
				clearOrder();

			} else {
				argh.play();
				JOptionPane.showMessageDialog(this, "Add items to your order prior to checking out, Matey.", "Attention", 0);

			}

		} else if (e.getSource() == cancelOrderBtn) {

			// this will clear any items on the order and also reset the UI for a new order:
			ahoy.play();
			
			// confirm action:
			int reply = JOptionPane.showConfirmDialog(
					this, 
					"Are you sure you want to cancel this order?", 
					"Cancel Order?",  
					JOptionPane.YES_NO_OPTION,
					JOptionPane.NO_OPTION,
					captainJackParrotIMGICON);
			if (reply == JOptionPane.YES_OPTION) {
				clearOrder();
			}
		}


		// need to check if user is editing the itemSpecialRequestTA
		// if NOT, then reset and update:
		if (!itemSpecialRequestTF.isEnabled()) {
			updateOrder();
		}

	}

	public void keyPressed(KeyEvent kev) {
		/**
		 * this is hooked up to the itemSpecialRequestTA
		 * need to see when enter or return is pressed
		 * to store the request
		 */
		if (kev.getSource() == this.itemSpecialRequestTF) {

			int i = orderList.getSelectedIndex();

			if (i > -1) {
				if (kev.getKeyCode() == KeyEvent.VK_ENTER ) {
					order.get(i).specialRequest = this.itemSpecialRequestTF.getText();
					itemSpecialRequestTF.setEnabled(false);
					ahoy.play();
					updateOrder();

				}
			}

		} else if (kev.getModifiers() == 8) {
			this.removeItemBtn.setText("Remove ALL Items");
		}
	}

	public void keyReleased(KeyEvent kev) {
		this.removeItemBtn.setText("Remove Selected Item");
	}

	public void keyTyped(KeyEvent kev) {

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		/**
		 * this code handles the user making selections in the JList
		 */
		if (!e.getValueIsAdjusting()) {

			itemSpecialRequestTF.setEnabled(false);
			int s = orderList.getSelectedIndex();

			if (s > -1 && s < order.size()) {

				FoodItem selected = order.get(s);
				if (!selected.specialRequest.equals("Item Special Request")) {
					itemSpecialRequestTF.setText(selected.specialRequest);
				} else {
					itemSpecialRequestTF.setText("Item Special Request");
				}

			} else {
				itemSpecialRequestTF.setText("Item Special Request");

			}

		}

	}

	/**
	 * itemStateChanged() handles all of the checkboxes and radio buttons
	 */
	public void itemStateChanged(ItemEvent iev) {

		if (iev.getSource() == extraSauceCB) {
			if (extraSauceCB.isSelected()) {
				ahoy.play();
			} 

		} else if (iev.getSource() == this.kidsBookCB) {
			if (this.kidsBookCB.isSelected()) {
				ahoy.play();
			}

		} else if (iev.getSource() == this.deliverRB) {
			addressTA.setEnabled(true);
			if (addressTA.getText().equals("Delivery Address")) {
				addressTA.setText("");
			}
			addressTA.requestFocusInWindow();

		} else if (iev.getSource() == this.dineInRB || iev.getSource() == this.takeAwayRB) {
			addressTA.setEnabled(false);

		} else if (iev.getSource() == this.orderInstructionsCB) {

			/**
			 * when the order special instructions cb is checked,
			 * the ta is enabled.
			 * if the ta says "Order Special Instructions", then that is erased
			 * and focus is requested
			 * 
			 * when the cb is unchecked, the ta is reset
			 */
			if (this.orderInstructionsCB.isSelected()) {
				ahoy.play();
				this.orderInstructionsTA.setEnabled(true);
				if (this.orderInstructionsTA.getText().equals("Order Special Instructions")) {
					this.orderInstructionsTA.setText("");
				}
				this.orderInstructionsTA.requestFocusInWindow();

			} else {
				this.orderInstructionsTA.setText("Order Special Instructions");
				this.orderInstructionsTA.setEnabled(false);

			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		titleLBL.setIcon(title_overIMGICON);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		titleLBL.setIcon(titleIMGICON);

	}
}



