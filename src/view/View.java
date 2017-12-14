package view;

import model.Ball;
import model.InfoProvider;
import model.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.ListIterator;

public class View extends JFrame {
    public ViewPanelInner panel;

    private int count, lifetimeTotalCount;
    private JLabel countLabel, lifetimeCountLabel;

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
        countLabel = new JLabel(String.valueOf(count) + " balls on screen");
        panel.add(countLabel, BorderLayout.PAGE_START);
        countLabel.setHorizontalAlignment(JLabel.CENTER);

        lifetimeTotalCount = 0;
        lifetimeCountLabel = new JLabel(String.valueOf(lifetimeTotalCount) + " total balls ever made");
        panel.add(lifetimeCountLabel, BorderLayout.PAGE_END);
        lifetimeCountLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    public void start() {
        setVisible(true);
    }

    class ViewPanelInner extends JPanel implements Observer {
        public ViewPanelInner() {
            setBackground(Color.WHITE);
            setLayout(new BorderLayout());
        }

        @Override
        public void gameChanged() {
            repaint();
        }

        @Override
        protected synchronized void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHints(rh);

            LinkedList<Ball> balls = infoProvider.getBallsList();
            ListIterator ballsList = balls.listIterator();
            try {
                while (ballsList.hasNext()) {
                    Ball b = (Ball) ballsList.next();
                    int x = b.getX();
                    int y= b.getY();
                    int diameter = b.getDiameter();
                    int radius = b.getDiameter() / 2;
                    Color c = b.getColor();
                    g2.fillOval(x - radius, y - radius, diameter, diameter);
                    g2.setColor(c);
                }
            }
            catch (ConcurrentModificationException e) {}

            count = balls.size();
            countLabel.setText(String.valueOf(count) + " balls on screen");

            lifetimeTotalCount = infoProvider.getTotalBallCountEver();
            lifetimeCountLabel.setText(String.valueOf(lifetimeTotalCount) + " total balls ever made");
        }

    }

}
