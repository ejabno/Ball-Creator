package model;

import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class Model implements Runnable, InfoProvider {
    private static int UPDATERATE = 13;

    private int maxBallSize, minBallSize;
    private Observer observer;
    private LinkedList<Ball> ballsList;
    private int totalBallCountEver;
    private MusicPlayer musicPlayer;

    public Model(int min, int max) {
        maxBallSize = max;
        minBallSize = min;
        ballsList = new LinkedList<Ball>();
        totalBallCountEver = 0;

        musicPlayer = new MusicPlayer();
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
        int deltaD = rand.nextInt(3) + 1;
        Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        Ball newB = new Ball(xLoc, yLoc, diameter, deltaD, color);
        ballsList.add(newB);
        totalBallCountEver++;
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
                Thread.sleep(UPDATERATE);
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

    @Override
    public int getTotalBallCountEver() {
        return totalBallCountEver;
    }

    public void musicPlayerToggleLeft() {
        musicPlayer.toggleLeftLoop();
    }

    public void musicPlayerToggleRight() {
        musicPlayer.toggleRightLoop();
    }

}
