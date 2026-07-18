package com.example.pharaohgame_try2.object;

import javafx.scene.image.Image;

public class Object_CatNpcHeart extends ParentObject {
    public Object_CatNpcHeart () {
        name = "CatWithHeart";
        image = new Image(getClass().getResource("/com/example/pharaohgame_try2/objects/cat_heart.png").toString());
        collision = true;
    }
}
