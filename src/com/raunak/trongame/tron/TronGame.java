package com.raunak.trongame.tron;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class TronGame extends Canvas {

    public static final int BLOCK_SIZE = 25;
    public static final int TILES_X = 30;
    public static final int TILES_Y = 30;
    public static final int BIKE_SPACING = BLOCK_SIZE / 5;
    public static final int PATH_SPACING = BLOCK_SIZE / 3;

    public static final int WIDTH = BLOCK_SIZE * TILES_X;
    public static final int HEIGHT = BLOCK_SIZE * TILES_Y;

    public boolean paused;

    private ArrayList<KeyCode> keysPressed;

    private List<Bike> bikes;

    public TronGame() {
        this(WIDTH, HEIGHT);
    }

    public TronGame(double width, double height) {
        super(width, height);

        startGame();
    }

    public boolean isPaused() {
        return paused;
    }

    public void update() {
        if(!paused) {
            // game logic, movement
            keys();

            bikes.forEach(Bike::update);

            // collision detection
            // hit wall
            Bike bike1 = bikes.get(0);
            Bike bike2 = bikes.get(1);

            if(bike1.hasHitWall())
                gameOver("Red Bike");
            else if(bike2.hasHitWall())
                gameOver("Blue Bike");

            // hit path
            if(bike1.hasCrashed(bikes))
                gameOver("Red Bike");
            else if(bike2.hasCrashed(bikes))
                gameOver("Blue Bike");

            // render
            GraphicsContext g = getGraphicsContext2D();
            g.setFill(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            bikes.forEach(bike -> bike.draw(g));
        }
    }

    public void keys() {
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
    }

    public void setKeysPressed(ArrayList<KeyCode> keysPressed) {
        this.keysPressed = keysPressed;
    }

    public void gameOver(String playerWin) {
        paused = true;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(playerWin + " wins!");
        alert.setHeaderText(playerWin + " wins!");
        alert.setContentText("Want to play again?");

        // this fixes an error. cause why not
        alert.show();
        alert.setOnHidden(x -> {
            if(alert.getResult() != null && alert.getResult().equals(ButtonType.OK))
                startGame();
            else Platform.exit();
        });
    }

    public void startGame() {
        bikes = new ArrayList<>();

        int offsetFromWall = 2;

        bikes.add(new Bike(Color.BLUE, new Point2D(offsetFromWall, TILES_Y / 2)));
        bikes.add(new Bike(Color.RED, new Point2D(TILES_X - offsetFromWall, TILES_Y / 2)));

        paused = false;
    }
}
