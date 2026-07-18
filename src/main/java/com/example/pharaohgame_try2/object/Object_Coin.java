package com.example.pharaohgame_try2.object;

import javafx.scene.image.Image;

public class Object_Coin extends ParentObject {
    public boolean gathered = false;

    public Object_Coin() {
        name = "Coin";
        image = new Image(getClass().getResource("/com/example/pharaohgame_try2/objects/coin.png").toString());

        collision = true;
    }

}
