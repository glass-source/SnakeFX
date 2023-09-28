package com.asunder.snakefx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SnakeFX extends Application {

    private static Stage mainStage;
    private static Scene scene;

    @Override
    public void start(Stage stage) {
        var root = new Group();
        var controller = new SnakeController();
        scene = new Scene(root);
        controller.initSnake(root);
        controller.initScore(root);
        var timer = controller.getAnimationTimer();
        timer.start();
        scene.setOnKeyPressed(controller::onKeyPressed);
        scene.setFill(Color.LIGHTBLUE);
        stage.setFullScreenExitHint("");
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