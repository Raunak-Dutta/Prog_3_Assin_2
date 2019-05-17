package com.raunak.trongame.tron;

import javafx.geometry.Point2D;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 6/6/2017
 */
public enum Orientation {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public Point2D getRotation() {
        switch(this) {
            case UP:    return new Point2D(0, -1);
            case DOWN:  return new Point2D(0, 1);
            case LEFT:  return new Point2D(-1 , 0);
            case RIGHT: return new Point2D(1, 0);
        }

        return new Point2D(0, 0);
    }

    public Orientation getOpposite() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
        }

        return UP;
    }

    public boolean isOpposite(Orientation o) {
        return o.getRotation().add(getRotation()).distance(0, 0) == 0;
    }

    @Override
    public String toString() {
        return name();
    }
}
