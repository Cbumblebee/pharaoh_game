package com.example.pharaohgame_try2.tile;
import com.example.pharaohgame_try2.DisplayedObject;
import com.example.pharaohgame_try2.ForegroundScreen;
import com.example.pharaohgame_try2.PlayerCharacter;
import com.example.pharaohgame_try2.ScreenMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager extends DisplayedObject {
    public Tile[] tile;
    GraphicsContext backgroundGc;
    ScreenMap backgroundScreen;
    public int[][] mapTileNumber;
    PlayerCharacter playerCharacter;
    public TileManager(GraphicsContext backgroundGc, ScreenMap backgroundScreen) {

        this.backgroundGc = backgroundGc;
        this.backgroundScreen = backgroundScreen;
        //this.playerCharacter = playerCharacter;

        tile = new Tile[10];
        mapTileNumber = new int[backgroundScreen.getMaxWorldCol()][backgroundScreen.getMaxWorldRow()];

        getTileImage();

        loadMap("/com/example/pharaohgame_try2/maps/world01.txt");

    }

    public void getTileImage() {
        String imgPath = "/com/example/pharaohgame_try2/tiles/";

        tile[0] = new Tile();
        tile[0].image = new Image(getClass().getResource(imgPath+"sand.png").toString()); //

        tile[1] = new Tile();
        tile[1].image = new Image(getClass().getResource(imgPath+"concrete.png").toString()); //
        tile[1].collision = true;

        tile[2] = new Tile();
        tile[2].image = new Image(getClass().getResource(imgPath+"concrete.png").toString()); //
        tile[2].collision = true;

        tile[3] = new Tile();
        tile[3].image = new Image(getClass().getResource(imgPath+"sand.png").toString()); //

        tile[4] = new Tile();
        tile[4].image = new Image(getClass().getResource(imgPath+"sandstone.jpg").toString()); //
        tile[4].collision = true;

        tile[5] = new Tile();
        tile[5].image = new Image(getClass().getResource(imgPath+"sand.png").toString()); //
    }
    public void loadMap(String mapURI) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(mapURI); //get the map
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //read the map

            int col = 0;
            int row = 0;

            while (col < backgroundScreen.getMaxWorldCol() && row < backgroundScreen.getMaxWorldRow()) { //get to the first cell
                String line = bufferedReader.readLine(); //gives a String of the first line i suppose

                while (col < backgroundScreen.getMaxWorldCol()) {
                    //fills out line by line
                    String[] numbers = line.split(" "); //gives a String
                    int num = Integer.parseInt(numbers[col]); //str -> int
                    mapTileNumber[col][row] = num;
                    col++;
                }
                if (col == backgroundScreen.getMaxWorldCol()) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Probleme mit loadMap: " + e.getMessage());
        }

    }
    public void draw(PlayerCharacter playerCharacter) {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < backgroundScreen.getMaxWorldCol() && worldRow < backgroundScreen.getMaxWorldRow()) {

            int tileNum = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * backgroundScreen.getTileSize();
            int worldY = worldRow * backgroundScreen.getTileSize();
            int screenX = worldX - playerCharacter.getWorldXCoordinate() + playerCharacter.getScreenXCoordinate();
            int screenY = worldY - playerCharacter.getWorldYCoordinate() + playerCharacter.getScreenYCoordinate();


            if (worldX + backgroundScreen.getTileSize() * 2 > playerCharacter.getWorldXCoordinate() - playerCharacter.getScreenXCoordinate() &&
                worldX - backgroundScreen.getTileSize() * 2 < playerCharacter.getWorldXCoordinate() + playerCharacter.getScreenXCoordinate() &&
                worldY + backgroundScreen.getTileSize() * 2 > playerCharacter.getWorldYCoordinate() - playerCharacter.getScreenYCoordinate() &&
                worldY - backgroundScreen.getTileSize() * 2 < playerCharacter.getWorldYCoordinate() + playerCharacter.getScreenYCoordinate()) {

                backgroundGc.drawImage(tile[tileNum].image,screenX,screenY, backgroundScreen.getTileSize(), backgroundScreen.getTileSize());
            }


            worldCol++;

            if(worldCol == backgroundScreen.getMaxWorldCol()) {
                worldCol = 0;
                //x = 0;
                worldRow++;
                //y += bgScreen.getTileSize();
            }
        }
    }

}
