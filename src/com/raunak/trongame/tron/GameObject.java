package com.raunak.trongame.tron;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;


public abstract class GameObject {

    Point2D position;

    GameObject() {
        position = new Point2D(0, 0);
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public abstract void draw(GraphicsContext g);
}
