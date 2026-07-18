package com.example.pharaohgame_try2;

import com.example.pharaohgame_try2.monster.NPC_Monster;
import com.example.pharaohgame_try2.object.ParentObject;
import javafx.scene.canvas.GraphicsContext;

public class ForegroundScreen extends ScreenMap {
    public ParentObject[] obj = new ParentObject[10]; //i can have max 10 objects displayed
    public void setupGame () { objectSetter.setObject(this); }
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public GraphicsContext foregroundGc = this.getGraphicsContext2D();
    public PlayerCharacter playerCharacter = new PlayerCharacter(this, foregroundGc, collisionChecker);
    public ObjectSetter objectSetter = new ObjectSetter(this);
    public NPC_Monster monster = new NPC_Monster(this, collisionChecker);
}
