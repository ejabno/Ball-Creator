package model;

import java.util.LinkedList;

public interface InfoProvider {
    LinkedList<Ball> getBallsList();
    int getTotalBallCountEver();
}
