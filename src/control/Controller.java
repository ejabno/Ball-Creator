package control;

import model.Model;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements MouseListener, Runnable {
    private View view;
    private Model model;

    private boolean leftMouseHeld;
    private boolean rightMouseHeld;

    public Controller() {
        model = new Model(25, 75);
        view = new View(1000, 675, this, model);

        model.addObserver(view.panel);

        leftMouseHeld = false;
        rightMouseHeld = false;

    }

    public void start() {
        view.start();
        model.start();
        ballCreateLoop();
    }

    @Override
    public void mouseEntered(MouseEvent m) {}

    @Override
    public void mouseExited(MouseEvent m) {}

    @Override
    public void mousePressed(MouseEvent m) {
        switch (m.getButton()) {
            case MouseEvent.BUTTON1:
                leftMouseHeld = true;
                model.musicPlayerToggleLeft();
                break;
            case MouseEvent.BUTTON3:
                rightMouseHeld = true;
                model.musicPlayerToggleRight();
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent m) {
        switch (m.getButton()) {
            case MouseEvent.BUTTON1:
                leftMouseHeld = false;
                model.musicPlayerToggleLeft();
                break;
            case MouseEvent.BUTTON3:
                rightMouseHeld = false;
                model.musicPlayerToggleRight();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent m) {}

    public void ballCreateLoop() {
        Thread mouseHoldThread = new Thread(this);
        mouseHoldThread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30);
                Point p = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(p, view);
                if (leftMouseHeld) {
                    int x = p.x;
                    int y = p.y;
                    model.createBall(x, y);
                }
                if (rightMouseHeld) {
                    int x = view.getWidth() - p.x;
                    int y = view.getHeight() - p.y;
                    model.createBall(x, y);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
