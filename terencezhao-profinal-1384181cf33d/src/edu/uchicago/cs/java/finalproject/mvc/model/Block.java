package edu.uchicago.cs.java.finalproject.mvc.model;

import java.awt.*;

/**
 * A Block is the general unit of work in a game of tetris. Each block represents one square on the playfield.
 * Blocks have an x and y coordinate to determine their position and a color.
 * Created by terencezhao on 11/18/15.
 */
public class Block {

    private int x;
    private int y;
    private Color color;

    public Block(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void clear() {
        color = Tetrion.BACKGROUND_COLOR;
    }
}
