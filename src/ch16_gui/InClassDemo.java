package ch16_gui;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class InClassDemo extends JFrame {

	JLabel titleLbl;
	ImageIcon iconDefault, iconOver;
	JButton ahoyBtn, arrgBtn, yohoBtn;
	AudioClip ahoyClip;

	JRadioButton dineIn, delivery, pickup;

	public static void main(String args[]) {
		InClassDemo app = new InClassDemo();
		app.setSize(500,400);
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		app.show();
	}

	InClassDemo() {

		// set the layout for the jframe:
		setLayout(new BorderLayout());
		
		// default image icon setup in two steps:
		Image img1 = Toolkit.getDefaultToolkit().getImage("capt-jacks.png");
		iconDefault = new ImageIcon(img1.getScaledInstance(500, 80, Image.SCALE_SMOOTH)); // getScaledInstance() resizes the image

		// or, in just one long line:
		iconOver = new ImageIcon(Toolkit.getDefaultToolkit().getImage("parrot-cocktail.png").getScaledInstance(500, 80, Image.SCALE_SMOOTH));

		titleLbl = new JLabel();
		titleLbl.setIcon(iconDefault);

		titleLbl.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent e) {
				titleLbl.setIcon(iconOver); 
			}

			@Override
			public void mouseExited(MouseEvent e) {
				titleLbl.setIcon(iconDefault);
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

		});

		add(titleLbl, BorderLayout.NORTH);

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3,1));

		ahoyBtn = new JButton("Say Ahoy!");
		p.add(ahoyBtn);

		arrgBtn = new JButton("Say Arrg!");
		p.add(arrgBtn);

		yohoBtn = new JButton("Say Yoho!");
		p.add(yohoBtn);

		add(p, BorderLayout.WEST);

		URL ahoyURL;
		try {
			ahoyURL = new URL("file:ahoy-matey.wav");
			ahoyClip = Applet.newAudioClip(ahoyURL);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		ahoyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ahoyClip.play();
			}
		});

		// setup the radio buttons:
		dineIn = new JRadioButton("Dine In", true);
		delivery = new JRadioButton("Delivery");
		pickup = new JRadioButton("Take Out");

		// group to make them act together:
		ButtonGroup group = new ButtonGroup();
		group.add(dineIn);
		group.add(delivery);
		group.add(pickup);

		// null layout panel for the radio buttons:
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(null);
		// need to give this a size (the width is required in west and east regions):
		eastPanel.setPreferredSize(new Dimension(100, 80)); 
//		eastPanel.setBackground(Color.red);
		
		// set bounds for all objects added to the null layout:
		dineIn.setBounds(0, 0, 100, 24);
		delivery.setBounds(0, 24, 100, 24);
		pickup.setBounds(0, 48, 100, 24);
		
		// add to the null layout:
		eastPanel.add(dineIn);
		eastPanel.add(delivery);
		eastPanel.add(pickup);

		// put this panel in on the right side:
		add(eastPanel, BorderLayout.EAST);

	}
}