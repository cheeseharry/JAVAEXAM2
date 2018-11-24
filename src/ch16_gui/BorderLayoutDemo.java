package ch16_gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * BorderLayoutDemo
 * @author john_carlson
 *
 * This app demonstrates the placement of single swing widgets into the named regions of a border layout.
 * JButton presses are "recorded" in the center text area describing which region sourced the event.
 * 
 */
public class BorderLayoutDemo extends JFrame {

	public static void main(String[] args) {
		BorderLayoutDemo app = new BorderLayoutDemo();
		app.setTitle("BorderLayout Demo");
		app.setSize(600, 400);
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		app.setVisible(true);
	}
	
	BorderLayoutDemo() {
		
		setLayout(new BorderLayout());
		
		// buttons for the compass regions:
		JButton northBtn = new JButton("North");
		JButton southBtn = new JButton("South");
		JButton westBtn = new JButton("West");
		JButton eastBtn = new JButton("East");

		// text area for center display of button presses:
		JTextArea centerJTA = new JTextArea();
		JScrollPane jsp = new JScrollPane(centerJTA);
		
		// add to UI:
		add(northBtn, BorderLayout.NORTH);
		add(southBtn, BorderLayout.SOUTH);
		add(westBtn, BorderLayout.WEST);
		add(eastBtn, BorderLayout.EAST);
		add(jsp, BorderLayout.CENTER);
		
		// register all buttons with same action event handler:
		for( Component c: this.getContentPane().getComponents()) {
			if (c.getClass() == JButton.class) {
				JButton b = (JButton) c;
				b.addActionListener(press -> {
					centerJTA.append(b.getText() + "\n");
				});
			}
		}
	}
}
