package ch16_gui;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class RadioButtonDemoFix extends JFrame implements ItemListener {

	JRadioButton rb1, rb2, rb3;

	public static void main(String[] args) {
		RadioButtonDemoFix app = new RadioButtonDemoFix();
		app.setSize(160, 120);
		app.setTitle("Demo Fix");
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		app.setVisible(true);

	}

	RadioButtonDemoFix() {

		rb1 = new JRadioButton("No! Pick Me!");
		rb2 = new JRadioButton("No! Pick Me!");
		rb3 = new JRadioButton("She likes me best.", true);

		ButtonGroup g = new ButtonGroup();
		g.add(rb1);
		g.add(rb2);
		g.add(rb3);

		JPanel p = new JPanel();
		/* two ways to left-align our radio buttons:
		 * 1. set the flow layout to left align
		 * 2. stack the radio buttons in a grid layout
		 *    with 1 col and 3 rows.
		 */
		// approach 1:
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// approach 2 (takes two steps):
		//p.setLayout(new GridLayout(3,1)); // (rows, cols)

		/* set horizontal alignment using setAlignmentY (seems backwards, but docs say it is correct:
		 * http://docs.oracle.com/javase/7/docs/api/javax/swing/JComponent.html#setAlignmentY(float))
		 * */
		//p.setAlignmentY(LEFT_ALIGNMENT); 

		p.add(rb1);
		p.add(rb2);
		p.add(rb3);

		add(p/*, BorderLayout.CENTER*/); // placing p in a borderlayout overrides any alignment setting (switching to left)

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
