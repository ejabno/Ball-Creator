package model;

import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class Model implements Runnable, InfoProvider {
    private int maxBallSize, minBallSize;
    private Observer observer;
    private LinkedList<Ball> ballsList;

    public Model(int min, int max) {
        maxBallSize = max;
        minBallSize = min;
        ballsList = new LinkedList<Ball>();
    }

    public void addObserver(Observer obs) {
        observer = obs;
    }

    public synchronized void shrinkBalls() {
        ListIterator ballIt = ballsList.listIterator();
        while (ballIt.hasNext()) {
            Ball b = (Ball) ballIt.next();
            b.shrink();
        }
    }

    public synchronized void checkVisiBalls() {
        ListIterator ballIt = ballsList.listIterator();
        while (ballIt.hasNext()) {
            Ball b = (Ball) ballIt.next();
            if (!b.isVisible()) {
                ballIt.remove();
            }
        }
    }

    public synchronized void createBall(int xLoc, int yLoc) {
        Random rand = new Random();
        int diameter = rand.nextInt(maxBallSize) + minBallSize;
        int deltaD = rand.nextInt(5) + 1;
        Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        Ball newB = new Ball(xLoc, yLoc, diameter, deltaD, color);
        ballsList.add(newB);
    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    public void notifyObserver() {
        observer.gameChanged();
    }

    @Override
    public void run() {
        while (true) {
            try {
                checkVisiBalls();
                shrinkBalls();
                notifyObserver();
                Thread.sleep(20);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public LinkedList<Ball> getBallsList() {
        return ballsList;
    }
}
