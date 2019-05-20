package com.raunak.trongame.tron;;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import static com.raunak.trongame.tron.TronGame.BLOCK_SIZE;
import static com.raunak.trongame.tron.TronGame.PATH_SPACING;


public class Path extends GameObject {

    /**
     * directionIn = last direction of the bike
     * directionOut = current direction of the bike
     */
    private Orientation directionIn, directionOut;

    public Path(Point2D position, Orientation directionIn, Orientation directionOut) {
        this.position = position;
        this.directionIn = directionIn;
        this.directionOut = directionOut;
    }

    public Point2D getPosition() {
        return position;
    }

    public Orientation getDirectionIn() {
        return directionIn;
    }

    public Orientation getDirectionOut() {
        return directionOut;
    }

    @Override
    public synchronized void draw(GraphicsContext g) {
        g.fillRect(
                position.getX() * BLOCK_SIZE + PATH_SPACING,
                position.getY() * BLOCK_SIZE + PATH_SPACING,
                BLOCK_SIZE - 2 * PATH_SPACING,
                BLOCK_SIZE - 2 * PATH_SPACING
        );

        drawOrientation(g, directionIn);
        drawOrientation(g, directionOut);
    }

    private synchronized void drawOrientation(GraphicsContext g, Orientation o) {
        switch (o) {
            case UP:
                g.fillRect(
                        position.getX() * BLOCK_SIZE + PATH_SPACING,
                        position.getY() * BLOCK_SIZE,
                        BLOCK_SIZE - 2 * PATH_SPACING,
                        PATH_SPACING
                );
                break;
            case DOWN:
                g.fillRect(
                        position.getX() * BLOCK_SIZE + PATH_SPACING,
                        position.getY() * BLOCK_SIZE + BLOCK_SIZE - PATH_SPACING,
                        BLOCK_SIZE - 2 * PATH_SPACING,
                        PATH_SPACING
                );
                break;
            case LEFT:
                g.fillRect(
                        position.getX() * BLOCK_SIZE,
                        position.getY() * BLOCK_SIZE + PATH_SPACING,
                        PATH_SPACING,
                        BLOCK_SIZE - 2 * PATH_SPACING
                );
                break;
            case RIGHT:
                g.fillRect(
                        position.getX() * BLOCK_SIZE + BLOCK_SIZE - PATH_SPACING,
                        position.getY() * BLOCK_SIZE + PATH_SPACING,
                        PATH_SPACING,
                        BLOCK_SIZE - 2 * PATH_SPACING
                );
                break;
        }
    }

    @Override
    public String toString() {
        return "com.raunak.trongame.tron.Path{" +
                "position=" + position +
                ", directionIn=" + directionIn +
                ", directionOut=" + directionOut +
                '}';
    }
}
