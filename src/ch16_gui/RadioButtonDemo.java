package ch16_gui;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class RadioButtonDemo extends JFrame implements ItemListener {

	JRadioButton rb1, rb2, rb3;
	
	public static void main(String[] args) {
		RadioButtonDemo app = new RadioButtonDemo();
		app.setSize(160, 120);
		app.setTitle("Demo");
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		app.setVisible(true);

	}
	
	RadioButtonDemo() {
		
		rb1 = new JRadioButton("No! Pick Me!");
		rb2 = new JRadioButton("No! Pick Me!");
		rb3 = new JRadioButton("She likes me best.", true);
		
		ButtonGroup g = new ButtonGroup();
		g.add(rb1);
		g.add(rb2);
		g.add(rb3);
		
		JPanel p = new JPanel();
		
		p.add(rb1);
		p.add(rb2);
		p.add(rb3);
		
		add(p);
		
		// add listeners last:
		rb1.addItemListener(this);
		rb2.addItemListener(this);
		rb3.addItemListener(this);
		
	}

	/**
	 *  this event handler will be called twice for each radio button selection:
	 *  once for the new selection and once for the prior selection (almost like
	 *  "pushing in" and "popping out" events).
	 */
	public void itemStateChanged(ItemEvent check) {
	
		/* quick way to change the text on both buttons 
		 * (since we are doing the same thing for each button):
		 */
		JRadioButton rb = (JRadioButton) check.getSource();
		String pronoun = Math.random() > .5? "She":"He";
		rb.setText(rb.isSelected()? pronoun + " likes me best" : "No! Pick Me!");
		
	}
}
