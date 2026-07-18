package com.example.pharaohgame_try2.object;

import javafx.scene.image.Image;

public class Object_Sarcophagus extends ParentObject {
    public Object_Sarcophagus() {
        name = "Sarcophagus";
        image = new Image(getClass().getResource("/com/example/pharaohgame_try2/objects/sarcophagus_.png").toString());
        collision = true;
    }
}
