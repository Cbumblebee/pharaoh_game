package com.example.pharaohgame_try2;

import com.example.pharaohgame_try2.tile.TileManager;

public class CollisionChecker {
    ForegroundScreen foregroundScreen;
    public CollisionChecker (ForegroundScreen foregroundScreen) {
        this.foregroundScreen = foregroundScreen;
    }
    public void checkTile (DisplayedObject displayedObject, TileManager tileManager) {
         int objectLeftWorldX = (int) (displayedObject.getWorldXCoordinate() + displayedObject.solidAreaOfCharacter.getX());
         int objectRightWorldX = (int) (displayedObject.getWorldXCoordinate() + displayedObject.solidAreaOfCharacter.getX() + displayedObject.solidAreaOfCharacter.getWidth());
         int objectTopWorldY = (int) (displayedObject.getWorldYCoordinate() + displayedObject.solidAreaOfCharacter.getY());
         int objectBottomWorldY = (int) (displayedObject.getWorldYCoordinate() + displayedObject.solidAreaOfCharacter.getY() + displayedObject.solidAreaOfCharacter.getHeight());

         int objectLeftCol = objectLeftWorldX / foregroundScreen.getTileSize();
         int objectRightCol = objectRightWorldX / foregroundScreen.getTileSize();
         int objectTopRow = objectTopWorldY / foregroundScreen.getTileSize();
         int objectBottomRow = objectBottomWorldY / foregroundScreen.getTileSize();

         int tileNum1, tileNum2;

         switch (displayedObject.direction) {
             //predicting the next move and see if there is collision
             case "left":
                 objectLeftCol = (objectLeftWorldX - displayedObject.speed) / foregroundScreen.getTileSize();
                 tileNum1 = tileManager.mapTileNumber[objectLeftCol][objectTopRow];
                 tileNum2 = tileManager.mapTileNumber[objectLeftCol][objectBottomRow];
                 if (tileManager.tile[tileNum1].collision == true || tileManager.tile[tileNum2].collision == true) {
                     displayedObject.collisionOn  = true;
                 }
                 break;
             case "right":
                 objectRightCol = (objectRightWorldX + displayedObject.speed) / foregroundScreen.getTileSize();
                 tileNum1 = tileManager.mapTileNumber[objectRightCol][objectTopRow];
                 tileNum2 = tileManager.mapTileNumber[objectRightCol][objectBottomRow];
                 if (tileManager.tile[tileNum1].collision == true || tileManager.tile[tileNum2].collision == true) {
                     displayedObject.collisionOn  = true;
                 }
                 break;
             case "up": //ok
                 objectTopRow = (objectTopWorldY - displayedObject.speed) / foregroundScreen.getTileSize();
                 tileNum1 = tileManager.mapTileNumber[objectLeftCol][objectTopRow];
                 tileNum2 = tileManager.mapTileNumber[objectRightCol][objectTopRow];
                 if (tileManager.tile[tileNum1].collision == true || tileManager.tile[tileNum2].collision == true) {
                     displayedObject.collisionOn  = true;
                 }
                 break;
             case "down":
                 objectBottomRow = (objectBottomWorldY + displayedObject.speed) / foregroundScreen.getTileSize();
                 tileNum1 = tileManager.mapTileNumber[objectLeftCol][objectBottomRow];
                 tileNum2 = tileManager.mapTileNumber[objectRightCol][objectBottomRow];
                 if (tileManager.tile[tileNum1].collision == true || tileManager.tile[tileNum2].collision == true) {
                     displayedObject.collisionOn  = true;
                 }
                 break;
         }
    }
    public int checkObject (DisplayedObject displayedObject, boolean isPlayer) {
        int index = 999;

        for (int i = 0; i < foregroundScreen.obj.length; i++) {
            if (foregroundScreen.obj[i] != null) {
                //get player's solid area position
                displayedObject.solidAreaOfCharacter.setX(displayedObject.getWorldXCoordinate() + displayedObject.solidAreaOfCharacter.getX());
                displayedObject.solidAreaOfCharacter.setY(displayedObject.getWorldYCoordinate() + displayedObject.solidAreaOfCharacter.getY());
                //get object's solid area position
                foregroundScreen.obj[i].solidAreaOfObject.setX(foregroundScreen.obj[i].worldX);
                foregroundScreen.obj[i].solidAreaOfObject.setY(foregroundScreen.obj[i].worldY);

                switch (displayedObject.direction) {
                    case "up": //=up
                        displayedObject.solidAreaOfCharacter.setY(displayedObject.solidAreaOfCharacter.getY() - displayedObject.speed);
                        if (displayedObject.solidAreaOfCharacter.intersects(foregroundScreen.obj[i].solidAreaOfObject.getBoundsInParent())) {
                            if(foregroundScreen.obj[i].collision == true) /*if it is solid*/ {
                                displayedObject.collisionOn = true;
                            }
                            if(isPlayer == true) {
                                index = i;
                            }
                        }
                        break;
                    case "down": //=down
                        displayedObject.solidAreaOfCharacter.setY(displayedObject.solidAreaOfCharacter.getY() + displayedObject.speed);
                        if (displayedObject.solidAreaOfCharacter.intersects(foregroundScreen.obj[i].solidAreaOfObject.getBoundsInParent())) {
                            if(foregroundScreen.obj[i].collision == true) /*if it is solid*/ {
                                displayedObject.collisionOn = true;
                            }
                            if(isPlayer == true) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        displayedObject.solidAreaOfCharacter.setX(displayedObject.solidAreaOfCharacter.getX() - displayedObject.speed);
                        if (displayedObject.solidAreaOfCharacter.intersects(foregroundScreen.obj[i].solidAreaOfObject.getBoundsInParent())) {
                            if(foregroundScreen.obj[i].collision == true) /*if it is solid*/ {
                                displayedObject.collisionOn = true;
                            }
                            if(isPlayer == true) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        displayedObject.solidAreaOfCharacter.setX(displayedObject.solidAreaOfCharacter.getX() + displayedObject.speed);
                        if (displayedObject.solidAreaOfCharacter.intersects(foregroundScreen.obj[i].solidAreaOfObject.getBoundsInParent())) {
                            if(foregroundScreen.obj[i].collision == true) /*if it is solid*/ {
                                displayedObject.collisionOn = true;
                            }
                            if(isPlayer == true) {
                                index = i;
                            }
                        }
                        break;
                }
                displayedObject.solidAreaOfCharacter.setX(displayedObject.solidAreaDefaultX);
                displayedObject.solidAreaOfCharacter.setY(displayedObject.solidAreaDefaultY);
                foregroundScreen.obj[i].solidAreaOfObject.setX(foregroundScreen.obj[i].solidAreaOfObjectDefaultX);
                foregroundScreen.obj[i].solidAreaOfObject.setY(foregroundScreen.obj[i].solidAreaOfObjectDefaultY);
            }
        }
        return index;
    }
    //NPC_Monster Collision
    public int checkPlayerToNPCCollision(DisplayedObject player, DisplayedObject target) {
        int index = 999;

        if (target != null) {
            //get player's solid area position
            player.solidAreaOfCharacter.setX(player.getWorldXCoordinate() + player.solidAreaOfCharacter.getX());
            player.solidAreaOfCharacter.setY(player.getWorldYCoordinate() + player.solidAreaOfCharacter.getY());
            //get npc's solid area position
            target.solidAreaOfCharacter.setX(target.getWorldXCoordinate() + target.solidAreaOfCharacter.getX());
            target.solidAreaOfCharacter.setY(target.getWorldYCoordinate() + target.solidAreaOfCharacter.getY());

            switch (player.direction) {
                case "up": //=up
                    player.solidAreaOfCharacter.setY(player.solidAreaOfCharacter.getY() - player.speed);
                    if (player.solidAreaOfCharacter.intersects(target.solidAreaOfCharacter.getBoundsInParent())) {
                        System.out.println("up collision");
                        player.collisionOn = true;
                        foregroundScreen.setGameState(foregroundScreen.loseState);
                        index = 100;
                    }
                    break;
                case "down": //=down
                    player.solidAreaOfCharacter.setY(player.solidAreaOfCharacter.getY() + player.speed);
                    if (player.solidAreaOfCharacter.intersects(target.solidAreaOfCharacter.getBoundsInParent())) {
                        System.out.println("down collision");
                        player.collisionOn = true;
                        foregroundScreen.setGameState(foregroundScreen.loseState);
                        index = 100;
                    }
                    break;
                case "left":
                    player.solidAreaOfCharacter.setX(player.solidAreaOfCharacter.getX() - player.speed);
                    if (player.solidAreaOfCharacter.intersects(target.solidAreaOfCharacter.getBoundsInParent())) {
                        System.out.println("left collision");
                        player.collisionOn = true;
                        foregroundScreen.setGameState(foregroundScreen.loseState);
                        index = 100;
                    }
                    break;
                case "right":
                    player.solidAreaOfCharacter.setX(player.solidAreaOfCharacter.getX() + player.speed);
                    if (player.solidAreaOfCharacter.intersects(target.solidAreaOfCharacter.getBoundsInParent())) {
                        System.out.println("right collision");
                        player.collisionOn = true;
                        foregroundScreen.setGameState(foregroundScreen.loseState);
                        index = 100;
                        index = 100;
                    }
                    break;

            }
            player.solidAreaOfCharacter.setX(player.solidAreaDefaultX);
            player.solidAreaOfCharacter.setY(player.solidAreaDefaultY);
            target.solidAreaOfCharacter.setX(target.solidAreaDefaultX);
            target.solidAreaOfCharacter.setY(target.solidAreaDefaultY);
        }
        return index;
    }
    public void checkNPCToPlayerCollision(DisplayedObject npc) {
        if (npc != null) {
            //get player's solid area position
            foregroundScreen.playerCharacter.solidAreaOfCharacter.setX(foregroundScreen.playerCharacter.getWorldXCoordinate() + foregroundScreen.playerCharacter.solidAreaOfCharacter.getX());
            foregroundScreen.playerCharacter.solidAreaOfCharacter.setY(foregroundScreen.playerCharacter.getWorldYCoordinate() + foregroundScreen.playerCharacter.solidAreaOfCharacter.getY());
            //get npc's solid area position
            npc.solidAreaOfCharacter.setX(npc.getWorldXCoordinate() + npc.solidAreaOfCharacter.getX());
            npc.solidAreaOfCharacter.setY(npc.getWorldYCoordinate() + npc.solidAreaOfCharacter.getY());

            switch (npc.direction) {
                /*case "up": //=up
                    player.solidAreaOfCharacter.setY(player.solidAreaOfCharacter.getY() - player.speed);
                    if (player.solidAreaOfCharacter.intersects(npc.solidAreaOfCharacter.getBoundsInParent())) {
                        System.out.println("up collision");
                        player.collisionOn = true;
                    }
                    break;
                case "down": //=down
                    player.solidAreaOfCharacter.setY(player.solidAreaOfCharacter.getY() + player.speed);
                    if (player.solidAreaOfCharacter.intersects(npc.solidAreaOfCharacter.getBoundsInParent())) {
                        System.out.println("down collision");
                        player.collisionOn = true;
                    }
                    break;*/
                case "left":
                    npc.solidAreaOfCharacter.setX(npc.solidAreaOfCharacter.getX() - npc.speed);
                    if (npc.solidAreaOfCharacter.intersects(foregroundScreen.playerCharacter.solidAreaOfCharacter.getBoundsInParent())) {
                        System.out.println("left collision");
                        npc.collisionOn = true;
                        foregroundScreen.setGameState(foregroundScreen.loseState);
                    }
                    break;
                case "right":
                    npc.solidAreaOfCharacter.setX(npc.solidAreaOfCharacter.getX() + npc.speed);
                    if (npc.solidAreaOfCharacter.intersects(foregroundScreen.playerCharacter.solidAreaOfCharacter.getBoundsInParent())) {
                        System.out.println("right collision");
                        npc.collisionOn = true;
                        foregroundScreen.setGameState(foregroundScreen.loseState);
                    }
                    break;

            }
            foregroundScreen.playerCharacter.solidAreaOfCharacter.setX(foregroundScreen.playerCharacter.solidAreaDefaultX);
            foregroundScreen.playerCharacter.solidAreaOfCharacter.setY(foregroundScreen.playerCharacter.solidAreaDefaultY);
            npc.solidAreaOfCharacter.setX(npc.solidAreaDefaultX);
            npc.solidAreaOfCharacter.setY(npc.solidAreaDefaultY);
        }
    }
}
