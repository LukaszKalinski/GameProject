package com.example.game;

public class Village {

    public int coordX;
    public int coordY;
    public String name;

    public Village(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
