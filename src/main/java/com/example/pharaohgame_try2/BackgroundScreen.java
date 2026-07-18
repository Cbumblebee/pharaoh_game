package com.example.pharaohgame_try2;

import com.example.pharaohgame_try2.tile.TileManager;
import javafx.scene.canvas.GraphicsContext;

public class BackgroundScreen extends ScreenMap {
    public GraphicsContext backgroundGc = this.getGraphicsContext2D();

    TileManager tileManager = new TileManager(backgroundGc,this);

}
