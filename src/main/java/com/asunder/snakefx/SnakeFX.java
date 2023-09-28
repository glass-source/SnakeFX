package com.asunder.snakefx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SnakeFX extends Application {

    private static Stage mainStage;
    private static Scene scene;

    @Override
    public void start(Stage stage) {
        stage.setFullScreenExitHint("");
        var root = new Group();
        scene = new Scene(root);
        var controller = new SnakeController();
        var snakeBody = controller.getSnakeBody();
        var drawnScore = controller.drawScore;
        var scoreLabel = controller.scoreLabel;
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
        scoreLabel.setTranslateX(0);
        scoreLabel.setTranslateY(0);
        drawnScore.setX(0);
        drawnScore.setY(0);
        drawnScore.setTranslateY(120);
        drawnScore.setTranslateX(120);
        drawnScore.setStroke(Color.BLACK);
        drawnScore.setStrokeWidth(2);
        root.getChildren().add(drawnScore);
        root.getChildren().add(controller.scoreLabel);
        scene.setOnKeyPressed(controller::onKeyPressed);
        scene.setFill(Color.LIGHTBLUE);
        var timer = controller.getAnimationTimer();
        timer.start();
        stage.setTitle("SnakeFX");
        stage.setScene(scene);
        stage.show();
        mainStage = stage;
    }

    public static void main(String[] args) {
        launch();
    }
    public static Stage getMainStage() {
        return mainStage;
    }
    public static Scene getScene() {return scene;}

}