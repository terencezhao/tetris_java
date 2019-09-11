package edu.uchicago.cs.java.finalproject.mvc.model;

import java.awt.*;

/**
 * The Tetromino is an abstract representation of a tetris game piece.
 * It is defined as a structure made up of exactly 4 blocks.
 * It can be rotated clockwise in 90 degree increments.
 * There are 7 types of tetrominos in the game of tetris including: I, O, T, J, L, S, and Z.
 * Created by terencezhao on 11/18/15.
 */
public abstract class Tetromino {

    public enum Type {I, O, T, J, L, S, Z}
    private Block[] blocks;
    private int tlcX;
    private int tlcY;

    public abstract void rotate();

    public int getTLCX() {
        return tlcX;
    }
    public int getTLCY() {
        return tlcY;
    }
    public void setTLCX(int tlcX) {
        this.tlcX = tlcX;
    }
    public void setTLCY(int tlcY) {
        this.tlcY = tlcY;
    }

    public abstract Color[][] getRotationMatrix();

    public Block[] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[] blocks) {
        this.blocks = blocks;
    }

    public abstract Block[] getPreview();

    public abstract void spawn();

    public abstract void updateRotation();

}
