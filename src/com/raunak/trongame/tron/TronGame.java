package com.raunak.trongame.tron;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class TronGame extends Canvas {

    static final int BLOCK_SIZE = 25;
    static final int TILES_X = 30;
    static final int TILES_Y = 30;
    static final int BIKE_SPACING = BLOCK_SIZE / 5;
    static final int PATH_SPACING = BLOCK_SIZE / 3;

    static final int WIDTH = BLOCK_SIZE * TILES_X;
    static final int HEIGHT = BLOCK_SIZE * TILES_Y;

    private boolean paused;

    private ArrayList<KeyCode> keysPressed;

    private List<Bike> bikes;

    public TronGame() {
        this(WIDTH, HEIGHT);
    }

    TronGame(double width, double height) {
        super(width, height);

        startGame();
    }

    boolean isPaused() {
        return paused;
    }

    void update() {
        if(!paused) {
            // game logic, movement
            keys();

            bikes.forEach(Bike::update);

            // collision detection
            // hit wall
            Bike bike1 = bikes.get(0);
            Bike bike2 = bikes.get(1);
            Bike bike3 = bikes.get(2);

            if(bike1.hasHitWall())
                gameOver("Red Bike");
            else if(bike2.hasHitWall())
                gameOver("Blue Bike");


            // hit path
            if(bike1.hasCrashed(bikes) && !bike2.hasCrashed(bikes))
                gameOver("Red Bike");
            else if(bike2.hasCrashed(bikes) && !bike1.hasCrashed(bikes))
                gameOver("Blue Bike");
            else if(bike2.hasCrashed(bikes) && bike1.hasCrashed(bikes))
                gameOver("No one");

            // render
            GraphicsContext g = getGraphicsContext2D();
            g.setFill(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            bikes.forEach(bike -> bike.draw(g));
        }
    }

    private void keys() {
        Bike bike1 = bikes.get(0);
        if(keysPressed.contains(KeyCode.W))
            bike1.setOrientation(Orientation.UP);
        else if(keysPressed.contains(KeyCode.A))
            bike1.setOrientation(Orientation.LEFT);
        else if(keysPressed.contains(KeyCode.S))
            bike1.setOrientation(Orientation.DOWN);
        else if(keysPressed.contains(KeyCode.D))
            bike1.setOrientation(Orientation.RIGHT);

        Bike bike2 = bikes.get(1);
        if(keysPressed.contains(KeyCode.UP))
            bike2.setOrientation(Orientation.UP);
        else if(keysPressed.contains(KeyCode.LEFT))
            bike2.setOrientation(Orientation.LEFT);
        else if(keysPressed.contains(KeyCode.DOWN))
            bike2.setOrientation(Orientation.DOWN);
        else if(keysPressed.contains(KeyCode.RIGHT))
            bike2.setOrientation(Orientation.RIGHT);

        Bike bike3 = bikes.get(2);
        if(keysPressed.contains(KeyCode.I))
            bike3.setOrientation(Orientation.UP);
        else if(keysPressed.contains(KeyCode.J))
            bike3.setOrientation(Orientation.LEFT);
        else if(keysPressed.contains(KeyCode.K))
            bike3.setOrientation(Orientation.DOWN);
        else if(keysPressed.contains(KeyCode.L))
            bike3.setOrientation(Orientation.RIGHT);
    }

    void setKeysPressed(ArrayList<KeyCode> keysPressed) {
        this.keysPressed = keysPressed;
    }

    private void gameOver(String playerWin) {
        paused = true;
        System.out.println(playerWin + " win!  ");
        List<Path> deduped0 = bikes.get(0).getPaths().stream().distinct().collect(Collectors.toList());
        Set<Path> set0 = new HashSet<>(deduped0);
        System.out.println("Blue:  "+set0.size());
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                {  //this took me so fucking long to figure out.

                    JDialog.setDefaultLookAndFeelDecorated(true);
                    int response = JOptionPane.showConfirmDialog(null, (playerWin + " wins! Restart? \n"
                                    +playerWin+" : "+set0.size()+" Points"), playerWin + " wins!",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (response == JOptionPane.YES_OPTION) {
                        startGame();
                        JOptionPane.getRootFrame().dispose();
                        paused = false;
                    } else {
                        Platform.exit();
                        if(!Thread.currentThread().isInterrupted()) {
                            Thread.currentThread().interrupt();
                            System.exit(0);
                        }
                        else{
                            System.out.println("Thread is not intereputed");
                        }

                    }
                }

            }
        };
        Thread message = new Thread(r);
        message.start();
    }

    private void startGame() {
        bikes = new ArrayList<>();

        int offsetFromWall = 2;

        bikes.add(new Bike(Color.BLUE, new Point2D(offsetFromWall, TILES_Y / 2)));  //change bike colors and behiviour
        bikes.add(new Bike(Color.RED, new Point2D(TILES_X - offsetFromWall, TILES_Y / 2))); //add bikes
        bikes.add(new Bike(Color.PINK, new Point2D(((TILES_X - offsetFromWall) /2), TILES_Y / 2))); //add bikes

        paused = false;
    }
}
