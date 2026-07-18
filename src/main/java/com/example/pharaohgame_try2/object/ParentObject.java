package com.example.pharaohgame_try2.object;

import com.example.pharaohgame_try2.ForegroundScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class ParentObject {
    Image image;
    String name;
    public boolean collision = false;
    public boolean visibility = true;
    public String objectsSpecificQuality = "";
    public int worldX, worldY;
    public Rectangle solidAreaOfObject = new Rectangle(8,8,32,32);
    public int solidAreaOfObjectDefaultX = 0;
    public int solidAreaOfObjectDefaultY = 0;

    public void draw(GraphicsContext foregroundGc, ForegroundScreen foregroundScreen, int objInt) {
        int screenX = worldX - foregroundScreen.playerCharacter.getWorldXCoordinate() + foregroundScreen.playerCharacter.getScreenXCoordinate();
        int screenY = worldY - foregroundScreen.playerCharacter.getWorldYCoordinate() + foregroundScreen.playerCharacter.getScreenYCoordinate();


        if (worldX + foregroundScreen.getTileSize() * 2 > foregroundScreen.playerCharacter.getWorldXCoordinate() - foregroundScreen.playerCharacter.getScreenXCoordinate() &&
                worldX - foregroundScreen.getTileSize() * 2 < foregroundScreen.playerCharacter.getWorldXCoordinate() + foregroundScreen.playerCharacter.getScreenXCoordinate() &&
                worldY + foregroundScreen.getTileSize() * 2 > foregroundScreen.playerCharacter.getWorldYCoordinate() - foregroundScreen.playerCharacter.getScreenYCoordinate() &&
                worldY - foregroundScreen.getTileSize() * 2 < foregroundScreen.playerCharacter.getWorldYCoordinate() + foregroundScreen.playerCharacter.getScreenYCoordinate()) {

            if (foregroundScreen.obj[objInt].visibility /*visibility of coin*/){
                foregroundGc.drawImage(image, screenX, screenY, foregroundScreen.getTileSize(), foregroundScreen.getTileSize());
            }

        }
    }
}
