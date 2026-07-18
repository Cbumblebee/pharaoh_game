package com.example.pharaohgame_try2.monster;

import com.example.pharaohgame_try2.*;
import com.example.pharaohgame_try2.tile.TileManager;
import javafx.scene.image.Image;

public class NPC_Monster extends DisplayedObject {
    Image[] walkingCharacterArray;
    public AnimatedImage walkingCharacterRight, walkingCharacterLeft;
    ForegroundScreen foregroundScreen;
    CollisionChecker collisionChecker;
    String[] directions = {"right", "left"};
    int directionIndex = 0;
    public NPC_Monster(ForegroundScreen foregroundScreen, CollisionChecker collisionChecker) {
        this.foregroundScreen = foregroundScreen;
        this.collisionChecker = collisionChecker;
        walkingCharacterRight = new AnimatedImage();
        walkingCharacterLeft = new AnimatedImage();
        setDefaultValues();
        animateAllImages();
    }
    private void setDefaultValues() {
        direction = directions[directionIndex];
        speed = 1;
        this.worldXCoordinate = foregroundScreen.getTileSize() * 22; //the starting position
        this.worldYCoordinate = foregroundScreen.getTileSize() * 8;
        solidAreaOfCharacter.setX(2);
        solidAreaOfCharacter.setY(5);
        solidAreaOfCharacter.setWidth(44);
        solidAreaOfCharacter.setHeight(38);
        solidAreaDefaultX = (int) solidAreaOfCharacter.getX();
        solidAreaDefaultY = (int) solidAreaOfCharacter.getY();
    }

    private void animateAllImages() {
        //~~~RIGHT~~~
        buildWalkingCharacterArray("right");
        walkingCharacterRight.frames = walkingCharacterArray;
        walkingCharacterRight.duration = 0.100; //Framerate von Zehntelsekunden
        //~~~LEFT~~~
        buildWalkingCharacterArray("left");
        walkingCharacterLeft.frames = walkingCharacterArray;
        walkingCharacterLeft.duration = 0.100; //Framerate von Zehntelsekunden
    }
    private void buildWalkingCharacterArray(String filename) {
        walkingCharacterArray = new Image[3];
        for (int i = 0; i < 3; i++) {
            int j = i + 1;
            String imagePath = "/com/example/pharaohgame_try2/monster/crocodile_" + filename + j + ".png";
            try {
                walkingCharacterArray[i] = new Image(getClass().getResourceAsStream(imagePath));
                if (walkingCharacterArray[i] == null) {
                    throw new IllegalArgumentException("Image not found: " + imagePath);
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + imagePath);
                e.printStackTrace();
            }
        }
    }

    public void moveCharacter(TileManager tileManager) {
        collisionOn = false;
        //Check TILE collision
        collisionChecker.checkTile(this, tileManager);

        if (collisionOn) {
            this.directionIndex = Math.abs(this.directionIndex - 1);
            this.direction = this.directions[this.directionIndex];
        }
        //Check OBJECT collision
        collisionChecker.checkObject(this, false);
        //Check PLAYER collision
        collisionChecker.checkNPCToPlayerCollision(this);

        //if collision is false, npc can move
        if (collisionOn == false) {
            switch (direction) {
                case "left":
                    minimizeXCoordinate();
                    break;
                case "right":
                    maximizeXCoordinate();
                    break;
            }
        } else {
            System.out.println("collision is true");
        }
        this.screenXCoordinate = worldXCoordinate - foregroundScreen.playerCharacter.getWorldXCoordinate() + foregroundScreen.playerCharacter.getScreenXCoordinate();
        this.screenYCoordinate = worldYCoordinate - foregroundScreen.playerCharacter.getWorldYCoordinate() + foregroundScreen.playerCharacter.getScreenYCoordinate();
    }
    public void drawAnimatedImages(TileManager tileManager, double t) {
        moveCharacter(tileManager);

        if (this.worldXCoordinate + foregroundScreen.getTileSize() * 2 > foregroundScreen.playerCharacter.getWorldXCoordinate() - foregroundScreen.playerCharacter.getScreenXCoordinate() &&
            this.worldXCoordinate - foregroundScreen.getTileSize() * 2 < foregroundScreen.playerCharacter.getWorldXCoordinate() + foregroundScreen.playerCharacter.getScreenXCoordinate() &&
            this.worldYCoordinate + foregroundScreen.getTileSize() * 2 > foregroundScreen.playerCharacter.getWorldYCoordinate() - foregroundScreen.playerCharacter.getScreenYCoordinate() &&
            this.worldYCoordinate - foregroundScreen.getTileSize() * 2 < foregroundScreen.playerCharacter.getWorldYCoordinate() + foregroundScreen.playerCharacter.getScreenYCoordinate()) {

            switch (direction) {
                case "left":
                    foregroundScreen.foregroundGc.drawImage(walkingCharacterLeft.getFrame(t),
                            getScreenXCoordinate(), getScreenYCoordinate(),
                            foregroundScreen.getTileSize(), foregroundScreen.getTileSize());
                    break;
                case "right":
                    foregroundScreen.foregroundGc.drawImage(walkingCharacterRight.getFrame(t),
                            getScreenXCoordinate(), getScreenYCoordinate(),
                            foregroundScreen.getTileSize(), foregroundScreen.getTileSize());
                    break;
            }
        }
    }
}

