package com.asunder.snakefx;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class SnakeController {

    public int directionX = -1;
    public int directionY = 0;
    private boolean isRunning = false;
    public int currentScore = 5;
    private int gameSpeed = 96;
    private final Rectangle[] snakeBody = new Rectangle[1000];
    private final double[] xPositions = new double[1000];
    private final double[] yPositions = new double[1000];
    public Rectangle getSnakeHead() {
        return snakeBody[0];
    }
    private KeyCode currentDirection;
    public Rectangle drawnScore = new Rectangle(20, 20, Color.GOLD);
    public Label scoreLabel = new Label("Score: 0");

    private void stopGame() {
        isRunning = false;
    }

    public AnimationTimer getAnimationTimer() {

        return new AnimationTimer() {
            private long previousTime = 0;
            @Override
            public void handle(long now) {
                try {

                    if (now - previousTime >= gameSpeed * 1_000_000L) {
                        if (isRunning) {
                            var snakeHeadX = getSnakeHead().getTranslateX();
                            var snakeHeadY = getSnakeHead().getTranslateY();

                            for (int i = (currentScore) - 1; i > 0; i--) {
                                xPositions[i] = xPositions[i - 1];
                                yPositions[i] = yPositions[i - 1];
                            }

                            xPositions[0] += directionX * 20;
                            yPositions[0] += directionY * 20;

                            for (int i = 0; i < (currentScore); i++) {
                                if (snakeBody[i] != null) {
                                    var snakePart = snakeBody[i];

                                    if (i >= 1) {
                                        if (snakeHeadX == snakePart.getTranslateX() && snakeHeadY == snakePart.getTranslateY()) {
                                            isRunning = false;
                                        }
                                    }

                                    snakePart.setTranslateX(xPositions[i]);
                                    snakePart.setTranslateY(yPositions[i]);
                                }
                            }

                            if (snakeHeadX == drawnScore.getTranslateX() && snakeHeadY == drawnScore.getTranslateY()) {
                                currentScore++;
                                drawnScore.setTranslateX(20 * new Random().nextInt(1, 25));
                                drawnScore.setTranslateY(20 * new Random().nextInt(1, 25));
                                scoreLabel.setText("Score: " + (currentScore - 5));
                                snakeBody[currentScore - 1] = new Rectangle(20, 20);
                                var snakePart = snakeBody[currentScore - 1];
                                var group = (Group) SnakeFX.getScene().getRoot();
                                snakePart.setFill(Color.GREEN);
                                snakePart.setStroke(Color.BLACK);
                                snakePart.setStrokeWidth(2);
                                snakePart.setX(0);
                                snakePart.setY(0);
                                snakePart.setTranslateX(-20);
                                snakePart.setTranslateY(-20);
                                group.getChildren().add(snakePart);
                            }

                            if (snakeHeadX < -1) isRunning = false;
                            if (snakeHeadY < -1) isRunning = false;
                            if (snakeHeadX > SnakeFX.getMainStage().getWidth()) isRunning = false;
                            if (snakeHeadY > SnakeFX.getMainStage().getHeight()) isRunning = false;

                        }
                        previousTime = now;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    public void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP, W -> {
                if (currentDirection != KeyCode.DOWN) {
                    directionX = 0;
                    directionY = -1;
                    currentDirection = event.getCode();
                }
            }

            case DOWN, S -> {
                if (currentDirection != KeyCode.UP) {
                    directionX = 0;
                    directionY = 1;
                    currentDirection = event.getCode();
                }
            }

            case RIGHT, D -> {
                if (currentDirection != KeyCode.LEFT) {
                    directionY = 0;
                    directionX = 1;
                    currentDirection = event.getCode();
                }
            }

            case LEFT, A -> {
                if (currentDirection != KeyCode.RIGHT) {
                    directionY = 0;
                    directionX = -1;
                    currentDirection = event.getCode();
                }
            }
            
            case R -> stopGame();

            case ESCAPE, SPACE -> isRunning = !isRunning;
        }

    }

    public void initSnake(Group root) {
        for (int i = 0; i < 5; i++) {
            snakeBody[i] = new Rectangle(20, 20);
            snakeBody[i].setX(0);
            snakeBody[i].setY(0);
            snakeBody[i].setFill(Color.GREEN);
            snakeBody[i].setStroke(Color.BLACK);
            snakeBody[i].setStrokeWidth(2);
            root.getChildren().add(snakeBody[i]);
        }

        snakeBody[0].setFill(Color.RED);
    }

    public void initScore(Group root) {
        scoreLabel.setTranslateX(0);
        scoreLabel.setTranslateY(0);
        drawnScore.setX(0);
        drawnScore.setY(0);
        drawnScore.setTranslateY(120);
        drawnScore.setTranslateX(120);
        drawnScore.setStroke(Color.BLACK);
        drawnScore.setStrokeWidth(2);
        root.getChildren().add(drawnScore);
        root.getChildren().add(scoreLabel);
    }

}