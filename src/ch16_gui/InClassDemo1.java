package ch16_gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class InClassDemo1 extends JFrame {

	ArrayList<JButton> buttons = new ArrayList<JButton>();
	ArrayList<JCheckBox> checks = new ArrayList<JCheckBox>();
	ArrayList<JRadioButton> rbs = new ArrayList<JRadioButton>();
	
	public static void main(String[] args) {
		InClassDemo1 app = new InClassDemo1();
		app.setSize(500, 400);
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		app.show();
	}
	
	InClassDemo1() {
		
		JLabel titleLbl = new JLabel("The Title");
		titleLbl.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				titleLbl.setText("Over Title");								
			}

			@Override
			public void mouseExited(MouseEvent e) {
				titleLbl.setText("Default Title");				
			}
			
		});
		
		add(titleLbl, BorderLayout.NORTH);
		
		JButton ahoyBtn = new JButton("Say Ahoy!");
		JButton arrgBtn = new JButton("Say Arrg!");
		
		buttons.add(ahoyBtn);
		buttons.add(arrgBtn);
		
		for (JButton b: buttons) {
			b.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(b.getText());
				}
				
			});
		}
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,1));
		p.add(ahoyBtn);
		p.add(arrgBtn);
		
		add(p, BorderLayout.WEST);
		
		
	}

}
