package com.example.pharaohgame_try2;

import com.example.pharaohgame_try2.name_data.NameSaver;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Key;
import java.util.HashSet;
import java.util.jar.Attributes;

public class HelloApplication extends Application {
    static HashSet<String> currentlyActiveKeys;
    public Scene scene;
    public ForegroundScreen foregroundScreen = new ForegroundScreen(); //Canvas
    public BackgroundScreen backgroundScreen = new BackgroundScreen(); //Canvas for tiles
    //public GraphicsContext backgroundGc = backgroundScreen.getGraphicsContext2D();
    public GraphicsContext foregroundGc = foregroundScreen.getGraphicsContext2D();
    NameSaver nameSaver = new NameSaver();
    public UI ui = new UI(foregroundScreen, foregroundGc, nameSaver);
    private long lastKeyPressTime = 0;
    private static final long KEY_PRESS_DELAY = 200_000_000; // 200 milliseconds


    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        scene = new Scene(root);
        StackPane stackPane = new StackPane();
        scene.setFill(Color.web("#eda25c"));
        stage.setScene(scene);


        stackPane.getChildren().addAll(foregroundScreen, ui.setupStackPane());
        //borderPane.setCenter(ui.getVBox());
        root.getChildren().addAll(backgroundScreen, stackPane);
        prepareActionHandlers();


        //Startzeit
        final long startNanoTime = System.nanoTime();

        new AnimationTimer(){
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                foregroundScreen.foregroundGc.clearRect(0,0, foregroundScreen.screenWidth, foregroundScreen.screenHeight);
                backgroundScreen.backgroundGc.clearRect(0,0,backgroundScreen.maxScreenWidth,backgroundScreen.maxScreenHeight);
                whichKeyIsActive(currentNanoTime);

                ui.draw();
                if (foregroundScreen.getGameState() == foregroundScreen.playState) {
                    //TILES
                    backgroundScreen.tileManager.draw(foregroundScreen.playerCharacter);

                    //OBJECTS
                    for (int i = 0; i < foregroundScreen.obj.length; i++) {
                        if(foregroundScreen.obj[i] != null) {
                            foregroundScreen.obj[i].draw(foregroundScreen.foregroundGc, foregroundScreen, i);
                        }
                    }

                    //PLAYER
                    if (foregroundScreen.getGameState() == foregroundScreen.playState) {
                        foregroundScreen.playerCharacter.direction = whichKeyIsActive(currentNanoTime);
                    }
                    foregroundScreen.playerCharacter.drawAnimatedImages(backgroundScreen.tileManager, t);
                    foregroundScreen.playerCharacter.drawNameAboveCharacter(nameSaver.getName());

                    //MONSTER
                    foregroundScreen.monster.drawAnimatedImages(backgroundScreen.tileManager, t);
                }
            }
        }.start();
        backgroundScreen.tileManager.draw(foregroundScreen.playerCharacter);
        foregroundScreen.setupGame();
        stage.setTitle("2D Game");
        stage.setScene(scene);
        stage.show();
    }
    private void prepareActionHandlers() {
        currentlyActiveKeys = new HashSet<String>();
        scene.setOnKeyPressed( key -> currentlyActiveKeys.add(key.getCode().toString()));
        scene.setOnKeyReleased( key -> currentlyActiveKeys.remove(key.getCode().toString()));
    }
    private String whichKeyIsActive (long currentNanoTime) {
        if (foregroundScreen.getGameState() == foregroundScreen.titleState) {
            if (currentNanoTime - lastKeyPressTime < KEY_PRESS_DELAY) {
                return "else";
            }
            if(currentlyActiveKeys.contains("ENTER") || currentlyActiveKeys.contains("P")) {
                foregroundScreen.setGameState(foregroundScreen.playState);
            }
        }
        else if (foregroundScreen.getGameState() == foregroundScreen.playState) {
            if (currentlyActiveKeys.contains("LEFT")) {
                lastKeyPressTime = currentNanoTime;
                return "left";
            }
            if (currentlyActiveKeys.contains("RIGHT")) {
                lastKeyPressTime = currentNanoTime;
                return "right";
            }
            if (currentlyActiveKeys.contains("DOWN")) {
                lastKeyPressTime = currentNanoTime;
                return "down";
            }
            if (currentlyActiveKeys.contains("UP")) {
                lastKeyPressTime = currentNanoTime;
                return "up";
            }
            // Handle the "b" key for break
            if (currentlyActiveKeys.contains("B")) {
                if (currentNanoTime - lastKeyPressTime < KEY_PRESS_DELAY) {
                    return "else";
                }
                lastKeyPressTime = currentNanoTime;
                foregroundScreen.setGameState(foregroundScreen.pauseState);
            }
            // Handle the "r" key for reset
            if (currentlyActiveKeys.contains("R")) {
                lastKeyPressTime = currentNanoTime;
                ui.resetGame();
            }
        }
        else if (foregroundScreen.getGameState() == foregroundScreen.loseState) {
            if (currentNanoTime - lastKeyPressTime < KEY_PRESS_DELAY) {
                return "else";
            }
            if(currentlyActiveKeys.contains("ENTER") || currentlyActiveKeys.contains("R")) {
                lastKeyPressTime = currentNanoTime;
                ui.resetGame();
            }
        }
        else if (foregroundScreen.getGameState() == foregroundScreen.winState) {
            if (currentNanoTime - lastKeyPressTime < KEY_PRESS_DELAY) {
                return "else";
            }
            if(currentlyActiveKeys.contains("ENTER") || currentlyActiveKeys.contains("R")) {
                lastKeyPressTime = currentNanoTime;
                ui.resetGame();
            }
        }
        else if (foregroundScreen.getGameState() == foregroundScreen.pauseState) {
            if (currentNanoTime - lastKeyPressTime < KEY_PRESS_DELAY) {
                return "else";
            }
            // Handle the "b" key for pause/resume
            if (currentlyActiveKeys.contains("B")) {
                lastKeyPressTime = currentNanoTime;
                foregroundScreen.setGameState(foregroundScreen.playState);
            }
            // Handle the "r" key for reset
            if (currentlyActiveKeys.contains("R")) {
                lastKeyPressTime = currentNanoTime;
                ui.resetGame();
            }
            if (currentlyActiveKeys.contains("DOWN")) {
                lastKeyPressTime = currentNanoTime;
                ui.menuNum--;
                return "down";
            }
            if (currentlyActiveKeys.contains("UP")) {
                lastKeyPressTime = currentNanoTime;
                ui.menuNum++;
                return "up";
            }
            if(currentlyActiveKeys.contains("ENTER")) {
                lastKeyPressTime = currentNanoTime;
                switch (Math.abs(ui.getMenuNum()) % 3) {
                    case 0:
                        ui.resetGame();
                        break;
                    case 1:
                        foregroundScreen.setGameState(foregroundScreen.playState);
                        break;
                    case 2:
                        foregroundScreen.setGameState(foregroundScreen.loseState);
                        break;
                }
            }
        }
        return "else";
    }

    public static void main(String[] args) {
        launch();
    }
}