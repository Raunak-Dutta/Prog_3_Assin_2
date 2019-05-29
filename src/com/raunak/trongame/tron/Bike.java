package com.raunak.trongame.tron;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.raunak.trongame.tron.TronGame.*;


public class Bike extends GameObject {

    private Color bikeColor;

    private Orientation lastOrientation;
    private Orientation currentOrientation;
    private List<Path> paths;

    Bike(Color bikeColor, Point2D position) {
        this.bikeColor = bikeColor;

        this.position = position;

        paths = new ArrayList<>();

        currentOrientation = Orientation.UP;
        lastOrientation = Orientation.UP;
    }

    public Color getBikeColor() {
        return bikeColor;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void addPath(Path p) {
        paths.add(p);
    }

    public Orientation getLastOrientation() {
        return lastOrientation;
    }

    public Orientation getCurrentOrientation() {
        return currentOrientation;
    }

    boolean setOrientation(Orientation orientation) {
        if(currentOrientation.isOpposite(orientation))
            return false;

        lastOrientation = currentOrientation;
        currentOrientation = orientation;
        return true;
    }

    public Point2D getDirection() {
        return currentOrientation.getRotation();
    }

    synchronized boolean hasCrashed(List<Bike> bikes) {
        for(Bike other : bikes) // bikes also include this bike
            for(Path p : other.paths)
                if(position.equals(p.getPosition()))
                    return true;
        return false;
    }

    synchronized boolean hasHitWall() {
        return position.getX() >= TILES_X || position.getX() < 0 ||
                position.getY() >= TILES_Y || position.getY() < 0;
    }

    void update() {
        paths.add(new Path(position, lastOrientation.getOpposite(), currentOrientation));
        lastOrientation = currentOrientation;
        position = position.add(currentOrientation.getRotation());

        if(position.getX() >= TILES_X)
            position = position.subtract(1, 0);
        else if(position.getX() < 0)
            position = position.add(1, 0);

        if(position.getY() >= TILES_Y)
            position = position.subtract(0, 1);
        else if(position.getY() < 0)
            position = position.add(0, 1);
    }

    public synchronized void draw(GraphicsContext g) {
        // draw bike
        g.setFill(bikeColor);
        if(currentOrientation.getRotation().getX() == 0) // up and down
            g.fillRect(
                    position.getX() * BLOCK_SIZE + BIKE_SPACING,
                    position.getY() * BLOCK_SIZE,
                    BLOCK_SIZE - 2 * BIKE_SPACING,
                    BLOCK_SIZE);
        else
            g.fillRect(
                    position.getX() * BLOCK_SIZE,
                    position.getY() * BLOCK_SIZE + BIKE_SPACING,
                    BLOCK_SIZE,
                    BLOCK_SIZE - 2 * BIKE_SPACING);

        // draw paths
        paths.forEach(path -> path.draw(g));
    }
}
