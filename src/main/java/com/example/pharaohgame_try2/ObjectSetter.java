package com.example.pharaohgame_try2;

import com.example.pharaohgame_try2.object.Object_CatNpc;
import com.example.pharaohgame_try2.object.Object_Coin;
import com.example.pharaohgame_try2.object.Object_Sarcophagus;

import java.util.Objects;

public class ObjectSetter {
    ForegroundScreen foregroundscreen;
    public ObjectSetter (ForegroundScreen foregroundscreen) {
        this.foregroundscreen = foregroundscreen;
    }

    public void setObject (ForegroundScreen foregroundscreen) {
        //if (foregroundscreen.obj[0].objectsSpecificQuality != "coin gathered") {
            foregroundscreen.obj[0] = new Object_Coin();
            foregroundscreen.obj[0].worldX = 23 * foregroundscreen.tileSize;
            foregroundscreen.obj[0].worldY = 7 * foregroundscreen.tileSize;
        //}
        System.out.println(foregroundscreen.obj[0].objectsSpecificQuality);

        //if (!Objects.equals(foregroundscreen.obj[0].objectsSpecificQuality, "cat is satisfied")) {
            foregroundscreen.obj[1] = new Object_CatNpc();
            foregroundscreen.obj[1].worldX = 23 * foregroundscreen.tileSize;
            foregroundscreen.obj[1].worldY = 40 * foregroundscreen.tileSize;
        /*} else if (Objects.equals(foregroundscreen.obj[0].objectsSpecificQuality, "cat is satisfied")) {
            foregroundscreen.obj[1] = new Object_CatNpc();
            foregroundscreen.obj[1].worldX = 23 * foregroundscreen.tileSize;
            foregroundscreen.obj[1].worldY = 40 * foregroundscreen.tileSize;
        }*/


        foregroundscreen.obj[2] = new Object_Sarcophagus();
        foregroundscreen.obj[2].worldX = 22 * foregroundscreen.tileSize;
        foregroundscreen.obj[2].worldY = 21 * foregroundscreen.tileSize;
    }
}
