package ch16_gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** NullLayoutDemo version 2
 * 
 * @author john_carlson@baylor.edu
 * 
 * This app demonstrates the ability to place, move, and resize a component
 * (the JButton b) using a null layout.
 *
 */
public class NullLayoutDemo extends JFrame implements ActionListener, ChangeListener {

	JPanel p;
	JButton b;
	JSlider xs, ys, ss;
	Rectangle r = new Rectangle(100, 134, 140, 40);
	JLabel l = new JLabel("");
	
	public static void main(String[] args) {
		NullLayoutDemo app = new NullLayoutDemo();
		app.setSize(400,400);
		app.setTitle("NullLayoutDemo");
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		app.setVisible(true);
	}
	
	NullLayoutDemo() {
		
		// this will create a panel "p" with a null layout:
		p = new JPanel();
		p.setLayout(null);
		
		// this will add a button "b" to the panel "p":
		b = new JButton("Move me anywhere!");
		// with a null layout panel, we must specify the size and location 
		// of each and every widget:
		b.setBounds(r);
		p.add(b);
		
		// panel p (containing button b) is now placed in the center of the app:
		add(p, BorderLayout.CENTER);
		
		// widgets are added around p to control b:
		// these will be added to a border layout, not the null layout
		// so we don't need to set their location (nor can we):
		xs = new JSlider(); // x location of b
		xs.setMaximum(400);
		xs.setMinimum(-200);
		xs.setValue(b.getX());
		add(xs, BorderLayout.SOUTH);
		
		ys = new JSlider(JSlider.VERTICAL); // -y location of b
		ys.setMaximum(100);
		ys.setMinimum(-400);
		ys.setValue(-b.getY());
		add(ys, BorderLayout.WEST);
		
		ss = new JSlider(JSlider.VERTICAL); // size of b
		ss.setMaximum(400);
		ss.setMinimum(0);
		ss.setValue(r.width);
		add(ss, BorderLayout.EAST);
		
		// label describing b:
		l.setText("x: " + xs.getValue() + " y: " + -ys.getValue() + " scale: " + ss.getValue()/4.0 + "%");
		l.setFont(new Font("Monospaced", Font.PLAIN, 13));
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setPreferredSize(new Dimension(400, 30));
		add(l, BorderLayout.NORTH);
		
		// add listeners:
		b.addActionListener(this);
		xs.addChangeListener(this);
		ys.addChangeListener(this);
		ss.addChangeListener(this);
		
	}

	// if the user presses the button b, reset the app:
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("resetting...");
		b.setBounds(r);
		xs.setValue(r.x);
		ys.setValue(-r.y);
		ss.setValue(r.width);
	}

	// handle the sliders:
	public void stateChanged(ChangeEvent arg0) {
		// use setBounds() to modify the size and location of b:
		b.setBounds(xs.getValue(), -ys.getValue(), ss.getValue(), 2*ss.getValue()/7);
		l.setText("x: " + xs.getValue() + " y: " + -ys.getValue() + " scale: " + ss.getValue()/4.0 + "%");
	}

}
