package model;

import java.awt.*;
import java.util.HashMap;

public class Ball {
    private int xLoc;
    private int yLoc;
    private int diameter;
    private int deltaD;
    private Color color;

    public Ball(int x, int y, int d, int dd, Color c)  {
        xLoc = x;
        yLoc = y;
        diameter = d;
        deltaD = dd;
        color = c;
    }

    public void shrink() {
        diameter -= deltaD;
    }

    public boolean isVisible() {
        return (diameter > 0);
    }

    public int getX() {
        return xLoc;
    }

    public int getY() {
        return yLoc;
    }

    public int getDiameter() {
        return diameter;
    }

    public int getDeltaD() {
        return deltaD;
    }

    public Color getColor() {
        return color;
    }

}
