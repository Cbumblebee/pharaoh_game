package com.example.pharaohgame_try2.object;

import javafx.scene.image.Image;

public class Object_CatNpc extends ParentObject {
    public Object_CatNpc () {
        name = "Cat";
        image = new Image(getClass().getResource("/com/example/pharaohgame_try2/objects/cat.png").toString());
        collision = true;
    }
}
