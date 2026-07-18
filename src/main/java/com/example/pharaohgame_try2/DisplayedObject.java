package com.example.pharaohgame_try2;

import javafx.scene.shape.Rectangle;

public abstract class DisplayedObject {

    public int worldXCoordinate = 0;
    public int worldYCoordinate = 0;
    public int screenXCoordinate; //indicates, where we draw the player on the screen
    public int screenYCoordinate;
    public int speed = 1;
    public Rectangle solidAreaOfCharacter = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public String direction;

    //getResources - Method
    public String getR(String filename) {
        String str = String.valueOf(HelloApplication.class.getResource(filename));
        try {
            return str;
        } catch (Exception e) {
            System.out.println("img not found");
            return str;
        }
    }
    /*public void setAction() {}
    public void update() {}*/


    //Getter, Setter, Change Coordinates
    public int getScreenXCoordinate() {
        return screenXCoordinate;
    }
    public int getScreenYCoordinate() {
        return screenYCoordinate;
    }
    public void minimizeXCoordinate() {
        this.worldXCoordinate = this.worldXCoordinate - this.speed;
    }
    public void maximizeXCoordinate() {
        this.worldXCoordinate = this.worldXCoordinate +  this.speed;
    }
    public void minimizeYCoordinate() {
        this.worldYCoordinate = this.worldYCoordinate - speed;
    }
    public void maximizeYCoordinate() {
        this.worldYCoordinate = this.worldYCoordinate + speed;
    }
    public int getWorldXCoordinate() {
        return worldXCoordinate;
    }
    public int getWorldYCoordinate() {
        return worldYCoordinate;
    }
}
