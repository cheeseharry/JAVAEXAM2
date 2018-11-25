package ch17_printing; /**
 * Created by leizheng on 10/5/18.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class WebAdGenerator2 extends JFrame implements ActionListener, MouseMotionListener, MouseListener {
    static Random rand = new Random();
    static Double price = rand.nextDouble()*100;
    static int stockNo = rand.nextInt(10000);
    static double total = 0;
    Color changeColor = Color.YELLOW;

    CustomPanel cp = new CustomPanel();
    JPanel panel = new JPanel();
    JButton button = new JButton("Reset");
    JButton create = new JButton("Create");
    private JTextField resultTf;
    private JComboBox comparatorBox;


    public static void main(String[] args) {
        WebAdGenerator2 app = new WebAdGenerator2();
        app.setSize(1024, 768);
        app.setDefaultCloseOperation(EXIT_ON_CLOSE);
        app.setTitle("WebAdGenerator2");
        app.setVisible(true);

    }

    WebAdGenerator2() {

        comparatorBox = new JComboBox();
        comparatorBox.setFont(new Font("Monospaced", Font.PLAIN, 12));
        comparatorBox.addItem("PINK");
        comparatorBox.addItem("GREEN");
        comparatorBox.addItem("GRAY");
        comparatorBox.addActionListener(this);

        comparatorBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (comparatorBox.getSelectedIndex()) {
                    case 0:
                        changeColor = Color.magenta; break;
                    case 1: changeColor = Color.GREEN; break;
                    case 2: changeColor = Color.GRAY; break;
                }

                cp.repaint();
            }
        });


        //cp.add(comparatorBox);

        cp.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                total = total + price;
                total = (double) Math.round(total * 100) / 100;
                resultTf.setText(total+"$");
                price = price*1.1;
                cp.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                changeColor = Color.BLUE;
                cp.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                changeColor = Color.BLUE;
                cp.repaint();
            }
        });
        cp.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (cp.p.contains(e.getX(), e.getY())) {
                    changeColor = Color.BLUE;
                }
                if (cp.contains(e.getX(),e.getY())){
                    changeColor = Color.ORANGE;
                    cp.repaint();
                }
                else {
                    changeColor = Color.YELLOW;
                    cp.repaint();
                }
            }

        });

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Random rand = new Random();
                price = rand.nextDouble()*100;
                price = (double) Math.round(price * 100) / 100;

                total = 0;
                resultTf.setText(total+"$");
                cp.repaint();
            }

        });

        create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cp.p = new Polygon();
                cp.repaint();
                cp.setVisible(true);
            }

        });

        //cp.add(button);
        panel.add(create);
        panel.add(button);
        panel.add(comparatorBox);
        JLabel resultLbl = new JLabel("Total: ");
        //cp.add(resultLbl);
        panel.add(resultLbl);

        resultTf = new JTextField(8);
        //resultTf.setHorizontalAlignment(JTextField.SOUTH_EAST);
        //cp.add(resultTf);
        panel.add(resultTf);

        add(cp,BorderLayout.CENTER);

        add(panel,BorderLayout.SOUTH);

        cp.setVisible(false);

        //add(button, BorderLayout.SOUTH);
    }

    class CustomPanel extends JPanel {

        // attributes of this custom panel:
        Polygon p = new Polygon();
        Color fill = Color.GREEN;

        // draw code:
        public void paintComponent(Graphics g1d) {

            Graphics2D g = (Graphics2D) g1d;
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


            g.setColor( changeColor );
            g.fillRect(216, 5, 320, 320);

            g.setColor( new Color( 0, 0, 0, 255) );

            g.setFont( new Font("Monospaced", 1, 28) );
            g.drawString("Pirate Boat", 222, 60);
            g.drawString("Product No 1922", 222, 90);
            price = (double) Math.round(price * 100) / 100;
            g.drawString("Only" + price +"$", 222, 136);

            g.setColor( new Color( 255, 51, 51, 255) );

            Polygon Obj20 = new Polygon();
            Obj20.addPoint(414, 105);
            Obj20.addPoint(514, 205);
            Obj20.addPoint(414, 205);
            g.fillPolygon(Obj20);

            g.setColor( new Color( 0, 0, 0, 255) );
            g.fillRect(370, 204, 160, 19);

        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        Graphics2D g = (Graphics2D) cp.getGraphics();
        g.clearRect(0, 0, cp.getWidth(), cp.getHeight());
        g.setColor(Color.BLACK);

    }

    @Override
    public void mouseDragged(MouseEvent squeek) {
    }

    @Override
    public void mouseMoved(MouseEvent squeek) {
        Graphics g = cp.getGraphics();
        if (g != null) {
            Point p = new Point(squeek.getX(), squeek.getY());
            g.clearRect(0, 0, cp.getWidth(), cp.getHeight());
            if (cp.contains(p)) {
                g.setColor(Color.RED);
                g.setColor(Color.WHITE);
                g.drawString("in", p.x, p.y);
            } else {
                g.setColor(Color.BLACK);
                g.drawString("out", p.x, p.y);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent squeek) {
        //total = total + price;
        //total = (double) Math.round(total * 100) / 100;
        //resultTf.setText(total+"$");
        //price = price*1.1;
    }

    @Override
    public void mouseEntered(MouseEvent squeek) {
        //changeColor = Color.GREEN;
        //cp.repaint();

    }

    @Override
    public void mouseExited(MouseEvent squeek) {
        //changeColor = Color.PINK;
        //cp.repaint();
    }

    @Override
    public void mousePressed(MouseEvent squeek) {
    }

    @Override
    public void mouseReleased(MouseEvent squeek) {
    }


}
