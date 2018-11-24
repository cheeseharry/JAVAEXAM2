package ch16_gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class CheckBoxDemo extends JFrame implements ItemListener {

	JCheckBox cb1, cb2, cb3;
	
	public static void main(String[] args) {
		CheckBoxDemo app = new CheckBoxDemo();
		app.setSize(120, 120);
		app.setTitle("Demo");
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		app.setVisible(true);

	}
	
	CheckBoxDemo() {
		
		cb1 = new JCheckBox("Check Me Out!");
		cb2 = new JCheckBox("Check Me Out!");
		cb3 = new JCheckBox("Thanks.", true);
		
		JPanel p = new JPanel();
		
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
