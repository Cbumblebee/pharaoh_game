package com.example.pharaohgame_try2;

import com.example.pharaohgame_try2.name_data.NameSaver;
import com.example.pharaohgame_try2.object.Object_CatNpc;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.util.jar.Attributes;

public class UI {
    ForegroundScreen foregroundScreen;
    GraphicsContext foregroundGc;
    Canvas canvas;
    VBox vBox;
    public int menuNum = 0;
    NameSaver nameSaver;

    public UI(ForegroundScreen foregroundScreen, GraphicsContext foregroundGc, NameSaver nameSaver) {
        this.foregroundScreen = foregroundScreen;
        this.foregroundGc = foregroundGc;
        this.canvas = new Canvas(foregroundScreen.getScreenWidth(), foregroundScreen.getScreenHeight());
        this.vBox = createVBox();
        this.nameSaver = nameSaver;
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(Font.font("arial"));
        gc.setFill(Color.web("#5c3c1f"));

        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Title state
        if (foregroundScreen.getGameState() == foregroundScreen.titleState) {
            //drawTitleScreen(gc);
            vBox.setVisible(true); // Show the VBox on the title screen
        } else {
            vBox.setVisible(false); // Hide the VBox on other screens
        }
        // Play state
        if (foregroundScreen.getGameState() == foregroundScreen.playState) {
            // play State
        }
        // Pause state
        if (foregroundScreen.getGameState() == foregroundScreen.pauseState) {
            drawPauseScreen(gc);
        }
        if (foregroundScreen.getGameState() == foregroundScreen.loseState) {
            drawLoseScreen(gc);
        }
        if (foregroundScreen.getGameState() == foregroundScreen.winState) {
            drawWinScreen(gc);
        }
    }

    public void drawPauseScreen(GraphicsContext gc) {
        String text = "PAUSED";
        Font pauseFont = Font.font("Arial", FontWeight.BOLD, 80);
        gc.setFont(pauseFont);
        int x = getXForCenteredText(text, pauseFont);
        int y = foregroundScreen.tileSize * 3;
        gc.fillText(text, x, y);

        // Menu
        Font menuFont = Font.font("Arial", FontWeight.NORMAL, 20);
        gc.setFont(menuFont);

        text = "NEW GAME OR PRESS 'R'";
        x = getXForCenteredText(text, menuFont);
        y += foregroundScreen.tileSize * 5;
        gc.fillText(text, x, y);
        if (Math.abs(menuNum) % 3 == 0) {
            gc.fillText(">", x - foregroundScreen.tileSize, y);
        }

        text = "RESUME PLAY AND PRESS 'B'";
        x = getXForCenteredText(text, menuFont);
        y += foregroundScreen.tileSize;
        gc.fillText(text, x, y);
        if (Math.abs(menuNum) % 3 == 1) {
            gc.fillText(">", x - foregroundScreen.tileSize, y);
        }

        text = "QUIT";
        x = getXForCenteredText(text, menuFont);
        y += foregroundScreen.tileSize;
        gc.fillText(text, x, y);
        if (Math.abs(menuNum) % 3 == 2) {
            gc.fillText(">", x - foregroundScreen.tileSize, y);
        }
    }

    /*public void drawTitleScreen(GraphicsContext gc) {
        // Title
        String text = "ANUBIS";
        Font titleFont = Font.font("Arial", FontWeight.BOLD, 80);
        gc.setFont(titleFont);
        int x = getXForCenteredText(text, titleFont);
        int y = foregroundScreen.tileSize * 3;
        gc.fillText(text, x, y);

        // Menu
        Font menuFont = Font.font("Arial", FontWeight.NORMAL, 20);
        gc.setFont(menuFont);

        text = "PLAY";
        x = getXForCenteredText(text, menuFont);
        y += foregroundScreen.tileSize;
        gc.fillText(text, x, y);
        gc.fillText(">", x - foregroundScreen.tileSize, y);
    }*/

    public void drawLoseScreen(GraphicsContext gc) {
        // Title
        String text = "GAME OVER";
        Font titleFont = Font.font("Arial", FontWeight.BOLD, 80);
        gc.setFont(titleFont);
        int x = getXForCenteredText(text, titleFont);
        int y = foregroundScreen.screenHeight / 2;
        gc.fillText(text, x, y);

        Font menuFont = Font.font("Arial", FontWeight.NORMAL, 20);
        gc.setFont(menuFont);
        text = "NEW GAME OR PRESS 'R'";
        x = getXForCenteredText(text, menuFont);
        y += foregroundScreen.tileSize * 3;
        gc.fillText(text, x, y);
        gc.fillText(">", x - foregroundScreen.tileSize, y);
    }

    public void drawWinScreen(GraphicsContext gc) {
        // Title
        String text = "YOU WON!";
        Font titleFont = Font.font("Arial", FontWeight.BOLD, 80);
        gc.setFont(titleFont);
        int x = getXForCenteredText(text, titleFont);
        int y = foregroundScreen.screenHeight / 2;
        gc.fillText(text, x, y);

        Font menuFont = Font.font("Arial", FontWeight.NORMAL, 20);
        gc.setFont(menuFont);
        text = "NEW GAME OR PRESS 'R'";
        x = getXForCenteredText(text, menuFont);
        y += foregroundScreen.tileSize * 3;
        gc.fillText(text, x, y);
        gc.fillText(">", x - foregroundScreen.tileSize, y);
    }

    public int getXForCenteredText(String text, Font font) {
        Text t = new Text(text);
        t.setFont(font);
        double length = t.getBoundsInLocal().getWidth();
        int x = (int) ((foregroundScreen.getScreenWidth() - length) / 2);
        return x;
    }

    public int getMenuNum() {
        return menuNum;
    }

    public void resetGame() {
        foregroundScreen.setGameState(foregroundScreen.playState);

        // reset Objects
        for (int i = 0; i < 3; i++) {
            foregroundScreen.obj[i].objectsSpecificQuality = "";
        }
        foregroundScreen.obj[0].collision = true;
        foregroundScreen.obj[0].visibility = true;
        foregroundScreen.obj[1] = new Object_CatNpc();
        foregroundScreen.obj[1].worldX = 23 * foregroundScreen.tileSize;
        foregroundScreen.obj[1].worldY = 40 * foregroundScreen.tileSize;

        // reset Player
        foregroundScreen.playerCharacter.setDefaultValues();
    }

    public VBox createVBox() {
        // VBox creation
        VBox vBox = new VBox();

        // Title
        String text = "ANUBIS";
        Font titleFont = Font.font("Arial", FontWeight.BOLD, 80);
        Text titleText = new Text(text);
        titleText.setFont(titleFont);
        titleText.setFill(Color.web("#5c3c1f")); // Set title color

        // Menu
        Font menuFont = Font.font("Arial", FontWeight.NORMAL, 20);
        Text playText = new Text("PRESS 'P' TO PLAY");
        playText.setFont(menuFont);
        playText.setFill(Color.web("#5c3c1f")); // Set menu text color

        // Calculate alignment for title and menu
        double titleWidth = titleText.getLayoutBounds().getWidth();
        double playWidth = playText.getLayoutBounds().getWidth();
        double xTitle = (foregroundScreen.getScreenWidth() - titleWidth) / 2;
        double xPlay = (foregroundScreen.getScreenWidth() - playWidth) / 2;
        double yTitle = 100; // Adjust Y position of title
        double yPlay = yTitle + 100; // Adjust Y position of menu

        titleText.setX(xTitle);
        titleText.setY(yTitle);
        playText.setX(xPlay);
        playText.setY(yPlay);

        // User input
        Label nameLabel = new Label("Enter your name:");
        nameLabel.setFont(menuFont);
        nameLabel.setTextFill(Color.web("#5c3c1f")); // Set label text color
        TextField nameField = new TextField("PLAYER_1");
        nameField.setAlignment(Pos.BASELINE_CENTER);
        nameField.setFont(menuFont);
        nameField.setStyle("-fx-text-fill: white; -fx-background-color: transparent;"); // Set text field style

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        Button okButton = new Button("OK");
        okButton.setFont(menuFont);
        okButton.setTextFill(Color.web("#5c3c1f")); // Set button text color
        okButton.setStyle("-fx-background-color: transparent; -fx-border-color: #5c3c1f; -fx-border-width: 2px;"); // Set button style
        Button editButton = new Button("Edit name");
        editButton.setFont(menuFont);
        editButton.setTextFill(Color.web("#5c3c1f")); // Set button text color
        editButton.setStyle("-fx-background-color: transparent; -fx-border-color: #5c3c1f; -fx-border-width: 2px;"); // Set button style
        editButton.setDisable(true);
        hBox.getChildren().addAll(okButton, editButton);

        // Explanation message
        String messageText =
                "Hello, you've been at the Judgment Hall of the Dead, but your heart was heavier than the feather. Normally, you would have been condemned to eternal damnation as a result. However, there was a glitch in the system, and you have been resurrected. Take this chance and perform a good deed to lighten your heart.\n\n" +
                        "But beware! Avoid Ammit, as she should have been the one to send you to eternal damnation.\n\n" +
                        "Press 'B' to pause/resume and 'R' to reset the game.";
        TextFlow messageFlow = new TextFlow();
        Text message = new Text(messageText);
        Font messageTextFont = Font.font("Arial", FontWeight.NORMAL, 16);
        message.setFont(messageTextFont);
        message.setFill(Color.web("#5c3c1f")); // Set message text color
        message.setWrappingWidth(foregroundScreen.getScreenWidth() - 6 * foregroundScreen.getTileSize());
        messageFlow.getChildren().add(message);

        // Align elements in VBox
        vBox.getChildren().addAll(titleText, playText, nameLabel, nameField, hBox, message);
        vBox.setSpacing(20); // Set spacing between elements
        vBox.setAlignment(javafx.geometry.Pos.CENTER); // Center align VBox content

        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                okButton.setDisable(false);
                editButton.setDisable(true);
            }
        });
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String playerName = nameField.getText();

                nameSaver.setName(playerName);
                System.out.println("Player Name: " + playerName);
                System.out.println("NameData: " + nameSaver.getName());
                vBox.setVisible(false); // Hide the VBox after input
                okButton.setDisable(true);
                editButton.setDisable(false);
                foregroundScreen.setGameState(foregroundScreen.titleState);
            }
        });

        vBox.setVisible(false); // Initially hide the VBox

        return vBox;
    }


    public StackPane setupStackPane() {
        StackPane stackPane = new StackPane();
        HBox hBox = new HBox(vBox);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        stackPane.getChildren().addAll(canvas, hBox);
        stackPane.setAlignment(javafx.geometry.Pos.CENTER); // Center align StackPane content
        return stackPane;
    }
}
