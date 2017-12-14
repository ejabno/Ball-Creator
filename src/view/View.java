package view;

import model.Ball;
import model.InfoProvider;
import model.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.ListIterator;

public class View extends JFrame {
    public ViewPanelInner panel;

    private int count;
    private JLabel countLabel;

    private InfoProvider infoProvider;

    public View(int w, int h, MouseListener listener, InfoProvider info) {
        infoProvider = info;

        setTitle("Balls");
        setSize(w, h);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        panel = new ViewPanelInner();
        panel.addMouseListener(listener);
        add(panel);

        count = 0;
        countLabel = new JLabel(String.valueOf(count));
        panel.add(countLabel);
    }

    public void start() {
        setVisible(true);
    }

    class ViewPanelInner extends JPanel implements Observer {

        @Override
        public void gameChanged() {
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            LinkedList<Ball> balls = infoProvider.getBallsList();
            ListIterator ballsList = balls.listIterator();
            while (ballsList.hasNext()) {
                Ball b = (Ball) ballsList.next();
                int x = b.getX();
                int y= b.getY();
                int diameter = b.getDiameter();
                int radius = b.getDiameter() / 2;
                Color c = b.getColor();
                g.fillOval(x - radius, y - radius, diameter, diameter);
                g.setColor(c);
            }
            count = balls.size();
            countLabel.setText(String.valueOf(count));
        }

    }

}
