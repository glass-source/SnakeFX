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
    public Rectangle[] getSnakeBody() {
        return snakeBody;
    }
    private KeyCode currentDirection;
    public Rectangle drawScore = new Rectangle(20, 20, Color.GOLD);
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

                            if (snakeHeadX == drawScore.getTranslateX() && snakeHeadY == drawScore.getTranslateY()) {
                                currentScore++;
                                drawScore.setTranslateX(20 * new Random().nextInt(1, 25));
                                drawScore.setTranslateY(20 * new Random().nextInt(1, 25));
                                scoreLabel.setText("Score: " + (currentScore - 5));
                                snakeBody[currentScore - 1] = new Rectangle(20, 20);
                                var snakePart = snakeBody[currentScore - 1];
                                snakePart.setFill(Color.GREEN);
                                snakePart.setStroke(Color.BLACK);
                                snakePart.setStrokeWidth(2);
                                snakePart.setX(0);
                                snakePart.setY(0);
                                snakePart.setTranslateX(-20);
                                snakePart.setTranslateY(-20);
                                Group group = (Group) SnakeFX.getScene().getRoot();
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
            
            case R -> {
                stopGame();
            }

            case ESCAPE, SPACE -> isRunning = !isRunning;
        }

    }

}