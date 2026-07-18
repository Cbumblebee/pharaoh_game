package com.example.pharaohgame_try2.name_data;

import java.io.*;
import java.util.ArrayList;

public class NameSaver {

    public String currentName = "Player_1";

    public String getName() {
        String zeichenkette = "";
        try (InputStream eingabe = new FileInputStream(new File("C:\\Users\\Maja Schachreiter\\IdeaProjects\\PharaohGame_try2\\src\\main\\resources\\com\\example\\pharaohgame_try2\\names\\name.txt"))) {
            int zeichen;
            String str = "C:\\Users\\Maja Schachreiter\\IdeaProjects\\PharaohGame_try2\\src\\main\\resources\\com\\example\\pharaohgame_try2\\names";
            String str2 = "C:\\Users\\Maja Schachreiter\\IdeaProjects\\PharaohGame_try2\\src\\main\\java\\com\\example\\pharaohgame_try2\\name_data";

            while ((zeichen = eingabe.read()) != -1) { //-1 ist das Ende vom Eingelesenen
                //jetzt casten wir die Zeichen in lesbares um (char/ASCII -> byte)
                zeichenkette += (char) zeichen;
            }
        } catch (IOException e) {
            System.out.println("not found name in getName" + e);
        }
        return zeichenkette;
    }
    public void setName(String nameStr) {
        try (OutputStream ausgabe = new FileOutputStream(new File("C:\\Users\\Maja Schachreiter\\IdeaProjects\\PharaohGame_try2\\src\\main\\resources\\com\\example\\pharaohgame_try2\\names\\name.txt"))) {
            //wenn Datei nd existiert, autom. neu angelegt
            //Zeichenketten additional müssen immer als Byte geschrieben werden
            ausgabe.write(nameStr.getBytes()); //TODO
        } catch (IOException e) {
            System.out.println("not found name in setName" + e);
        }
    }
}

