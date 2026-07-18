package com.example.pharaohgame_try2.object;

import javafx.scene.image.Image;

public class Object_Coffin extends ParentObject {
    public Object_Coffin () {
        name = "Coffin";
        image = new Image(getClass().getResource("/com/example/pharaohgame_try2/objects/cat_npc.png").toString());
        collision = true;
    }
}
