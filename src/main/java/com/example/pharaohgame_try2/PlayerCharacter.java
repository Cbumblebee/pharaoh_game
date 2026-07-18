package com.example.pharaohgame_try2;

import com.example.pharaohgame_try2.object.Object_CatNpcHeart;
import com.example.pharaohgame_try2.tile.TileManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Objects;

public class PlayerCharacter extends DisplayedObject {
    public AnimatedImage walkingCharacterRight, walkingCharacterLeft, walkingCharacterForward, walkingCharacterBackward;
    Image[] walkingCharacterArray;
    CollisionChecker collisionChecker;
    GraphicsContext foregroundGc;
    String lastActiveKey;
    ForegroundScreen foregroundScreen;

    //Konstruktor
    public PlayerCharacter (ForegroundScreen foregroundScreen, GraphicsContext foregroundGc, CollisionChecker collisionChecker) {
        //the foregroundscreen from DisplayedObject, (Superklasse)
        this.foregroundScreen = foregroundScreen;
        this.foregroundGc = foregroundGc;
        this.collisionChecker = collisionChecker;

        setDefaultValues();

        screenXCoordinate = foregroundScreen.screenWidth/2 - (foregroundScreen.getTileSize()/2);
        screenYCoordinate = foregroundScreen.screenHeight/2 - (foregroundScreen.getTileSize()/2);

        //solidAreaOfCharacter = new Rectangle();
        solidAreaOfCharacter.setX(8);
        solidAreaOfCharacter.setY(8);
        solidAreaOfCharacter.setWidth(32);
        solidAreaOfCharacter.setHeight(32);
        solidAreaDefaultX = (int) solidAreaOfCharacter.getX();
        solidAreaDefaultY = (int) solidAreaOfCharacter.getY();

        walkingCharacterRight = new AnimatedImage();
        walkingCharacterLeft = new AnimatedImage();
        walkingCharacterForward = new AnimatedImage();
        walkingCharacterBackward = new AnimatedImage();

        animateAllImages();
    }
    public void setDefaultValues() {
        this.worldXCoordinate = foregroundScreen.getTileSize() * 23; //the starting position
        this.worldYCoordinate = foregroundScreen.getTileSize() * 21;
        this.speed = 4;
    }
    public void moveCharacter(TileManager tileManager) {
        //Check TILE collision
        collisionOn = false;
        collisionChecker.checkTile(this, tileManager);

        //Check OBJECT collision
        int objIndex = collisionChecker.checkObject(this, true);

        int npcIndex = collisionChecker.checkPlayerToNPCCollision(this, foregroundScreen.monster);
        //interactNPC(npcIndex);

        if (objIndex != 999) {
            //TODO: if sarcophagus is accessible
            /*switch (objIndex) {
                case 0:
                    foregroundScreen.obj[objIndex].collision = false;
                    foregroundScreen.obj[objIndex].visibility = false;
                    foregroundScreen.obj[objIndex].objectsSpecificQuality = "coin gathered";
                    break;
                case 1:
                    foregroundScreen.obj[objIndex].objectsSpecificQuality = "cat is satisfied";
                    foregroundScreen.obj[1] = new Object_CatNpcHeart();
                    foregroundScreen.obj[1].worldX = 23 * foregroundScreen.tileSize;
                    foregroundScreen.obj[1].worldY = 40 * foregroundScreen.tileSize;
                    break;
                case 2:
                    foregroundScreen.obj[objIndex].objectsSpecificQuality = "access possible";
                    break;
            }*/
            //Collision with Coin
            if (objIndex == 0) {
                System.out.println("coin gotten");
                foregroundScreen.obj[0].collision = false;
                foregroundScreen.obj[0].visibility = false;
                foregroundScreen.obj[0].objectsSpecificQuality = "coin gathered";
                foregroundScreen.obj[1].objectsSpecificQuality = "cat is satisfiable"; //cat
            }
            //Collision with Cat
            if (objIndex == 1 &&
                Objects.equals(foregroundScreen.obj[objIndex].objectsSpecificQuality, "cat is satisfiable")) {

                foregroundScreen.obj[1] = new Object_CatNpcHeart();
                foregroundScreen.obj[1].worldX = 23 * foregroundScreen.tileSize;
                foregroundScreen.obj[1].worldY = 40 * foregroundScreen.tileSize;
                foregroundScreen.obj[1].objectsSpecificQuality = "cat is satisfied";
                foregroundScreen.obj[2].objectsSpecificQuality = "access possible"; //sarcophagus

                System.out.println("cat is satisfied: " + foregroundScreen.obj[1].objectsSpecificQuality);
            }
            //Collision with Sarcophagus
            if (objIndex == 2 &&
                Objects.equals(foregroundScreen.obj[2].objectsSpecificQuality, "access possible") &&
                Objects.equals(foregroundScreen.obj[1].objectsSpecificQuality, "cat is satisfied")) {
                System.out.println("accessible sarcophagus" + foregroundScreen.obj[2].objectsSpecificQuality + foregroundScreen.obj[1].objectsSpecificQuality);
                foregroundScreen.setGameState(foregroundScreen.winState);
            }
        }

        //if collision is false, player can move
        if (collisionOn == false) {
            switch (direction) {
                case "up": //=up
                    minimizeYCoordinate();
                    break;
                case "down": //=down
                    maximizeYCoordinate();
                    break;
                case "left":
                    minimizeXCoordinate();
                    break;
                case "right":
                    maximizeXCoordinate();
                    break;
            }
        }
    }
    private void animateAllImages() {
        //~~~RIGHT~~~
        buildWalkingCharacterArray("right_");
        walkingCharacterRight.frames = walkingCharacterArray;
        walkingCharacterRight.duration = 0.100; //Framerate von Zehntelsekunden
        //~~~LEFT~~~
        buildWalkingCharacterArray("left_");
        walkingCharacterLeft.frames = walkingCharacterArray;
        walkingCharacterLeft.duration = 0.100; //Framerate von Zehntelsekunden
        //~~~FORWARD OR DOWN~~~
        buildWalkingCharacterArray("forward_");
        walkingCharacterForward.frames = walkingCharacterArray;
        walkingCharacterForward.duration = 0.100; //Framerate von Zehntelsekunden
        //~~~BACKWARD OR UP~~~
        buildWalkingCharacterArray("backward_");
        walkingCharacterBackward.frames = walkingCharacterArray;
        walkingCharacterBackward.duration = 0.100; //Framerate von Zehntelsekunden
    }
    private void buildWalkingCharacterArray(String filename) {
        walkingCharacterArray = new Image[3];
        for (int i = 0; i < 3; i++) {
            int j = i + 1;
            walkingCharacterArray[i] = new Image(getR(filename + j + ".png"));
        }
    }

    public void drawAnimatedImages(TileManager tileManager, double t) {
        //making sure the game is not paused
        if (this.foregroundScreen.getGameState() == this.foregroundScreen.playState) {
            moveCharacter(tileManager);
        }
        if (this.foregroundScreen.getGameState() == this.foregroundScreen.pauseState) {}

        switch (direction) {
            case "left":
                foregroundGc.drawImage(walkingCharacterLeft.getFrame(t),
                        getScreenXCoordinate(), getScreenYCoordinate(),
                        foregroundScreen.getTileSize(), foregroundScreen.getTileSize());
                lastActiveKey = "left";
                break;
            case "right":
                foregroundGc.drawImage(walkingCharacterRight.getFrame(t),
                        getScreenXCoordinate(), getScreenYCoordinate(),
                        foregroundScreen.getTileSize(), foregroundScreen.getTileSize());
                lastActiveKey = "right";
                break;
            case "up":
                foregroundGc.drawImage(walkingCharacterBackward.getFrame(t),
                        getScreenXCoordinate(), getScreenYCoordinate(),
                        foregroundScreen.getTileSize(), foregroundScreen.getTileSize());
                lastActiveKey = "up";
                break;
            case "down":
                foregroundGc.drawImage(walkingCharacterForward.getFrame(t),
                        getScreenXCoordinate(), getScreenYCoordinate(),
                        foregroundScreen.getTileSize(), foregroundScreen.getTileSize());
                lastActiveKey = "down";
                break;
            case "else":
                if (lastActiveKey != null) {
                    switch (lastActiveKey) {
                        case "left":
                            foregroundGc.drawImage(walkingCharacterLeft.getFrame(0),
                                    getScreenXCoordinate(), getScreenYCoordinate(),
                                    foregroundScreen.getTileSize(), foregroundScreen.getTileSize());
                            break;
                        case "right":
                            foregroundGc.drawImage(walkingCharacterRight.getFrame(0),
                                    getScreenXCoordinate(), getScreenYCoordinate(),
                                    foregroundScreen.getTileSize(), foregroundScreen.getTileSize());
                            break;
                        case "up":
                            foregroundGc.drawImage(walkingCharacterBackward.getFrame(0),
                                    getScreenXCoordinate(), getScreenYCoordinate(),
                                    foregroundScreen.getTileSize(), foregroundScreen.getTileSize());
                            break;
                        case "down":
                            foregroundGc.drawImage(walkingCharacterForward.getFrame(0),
                                    getScreenXCoordinate(), getScreenYCoordinate(),
                                    foregroundScreen.getTileSize(), foregroundScreen.getTileSize());
                            break;
                    }
                } else {
                    foregroundGc.drawImage(walkingCharacterRight.getFrame(0),
                            getScreenXCoordinate(), getScreenYCoordinate(),
                            foregroundScreen.getTileSize(), foregroundScreen.getTileSize());
                }
                break;
        }
        collisionOn = false;
        collisionChecker.checkTile(this, tileManager);
    }
    /*public void interactNPC(int index) {
        if (index == 100) {
            System.out.println("Collision with NPC");
            //TODO:Game Over Panel
        }
    }*/
    public void drawNameAboveCharacter(String name) {
        Font nameFont = Font.font("Arial", FontWeight.EXTRA_LIGHT, 10);
        foregroundGc.setFont(nameFont);
        Text text = new Text(name);
        double length = text.getBoundsInLocal().getWidth();
        int x = (int) (getScreenXCoordinate() + foregroundScreen.getTileSize() / 2 - length / 2);
        int y = getScreenYCoordinate() - foregroundScreen.getTileSize() / 4;
        foregroundGc.fillText(name, x, y);
    }

}
