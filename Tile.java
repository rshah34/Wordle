package org.cis1200.Wordle;

import javax.swing.*;

public class Tile extends JLabel { /*
                                    * all standard code for the tile class representing each
                                    * individual tile that we are typing
                                    */

    private String character;
    private String color;

    public Tile(String character, String color) {
        this.character = character;
        this.color = color;
    }

    public String getChar() {
        return character;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setChar(String character) {
        this.character = character;
    }

}
