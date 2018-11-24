package ch16_gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class CheckBoxDemoFix extends JFrame implements ItemListener {

	JCheckBox cb1, cb2, cb3;
	
	public static void main(String[] args) {
		CheckBoxDemoFix app = new CheckBoxDemoFix();
		app.setSize(120, 120);
		app.setTitle("Demo Fix");
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		app.setVisible(true);

	}
	
	CheckBoxDemoFix() {
		
		cb1 = new JCheckBox("Check Me Out!");
		cb2 = new JCheckBox("Check Me Out!");
		cb3 = new JCheckBox("Thanks.", true);
		
		JPanel p = new JPanel();
		
		/* two ways to left-align our check boxes:
		 * 1. set the flow layout to left align
		 * 2. stack them in a grid layout
		 *    with 1 col and 3 rows.
		 */
		// approach 1:
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// approach 2:
		//p.setLayout(new GridLayout(3,1));
		/* set horizontal alignment using setAlignmentY (seems backwards, but docs say it is correct:
		 * http://docs.oracle.com/javase/7/docs/api/javax/swing/JComponent.html#setAlignmentY(float))
		 */
		//p.setAlignmentY(LEFT_ALIGNMENT); 

		p.add(cb1);
		p.add(cb2);
		p.add(cb3);
		
		add(p);
		
		// add listeners last:
		cb1.addItemListener(this);
		cb2.addItemListener(this);
		cb3.addItemListener(this);
		
	}

	public void itemStateChanged(ItemEvent check) {
		
//		if (check.getSource() == cb1) {
//			cb1.setText(cb1.isSelected()? "Thanks." : "Check Me Out!");
//		} else if (check.getSource() == cb2) {
//			cb2.setText(cb2.isSelected()? "Thanks." : "Check Me Out!");
//		} else if (check.getSource() == cb3) {
//			cb3.setText(cb3.isSelected()? "Thanks." : "Check Me Out!");
//		}
		
		// or, since we are doing the exact some thing for each option:
		JCheckBox cb = (JCheckBox) check.getSource();
		cb.setText(cb.isSelected()? "Thanks." : "Check Me Out!");
		
	}
}
