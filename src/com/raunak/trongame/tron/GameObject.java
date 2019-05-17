package com.raunak.trongame.tron;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Evan Tichenor (evan.tichenor@gmail.com)
 * @version 1.0, 6/15/2017
 */
public abstract class GameObject {

    protected Point2D position;

    public GameObject() {
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
