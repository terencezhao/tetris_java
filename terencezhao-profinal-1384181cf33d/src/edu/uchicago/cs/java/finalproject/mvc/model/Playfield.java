package edu.uchicago.cs.java.finalproject.mvc.model;

import java.awt.*;

/**
 * The Playfield is the center stage where the game happens.
 * It is composed of a well in which Tetromino pieces can be manipulated
 * and a wall which acts as a boundary in which Tetrominos cannot pass through.
 * The standard tetris playfield is 10 blocks wide and 20 blocks tall.
 * In our implementation, the height include 22 blocks, but the top 2 are not visible.
 *
 * Created by terencezhao on 11/25/15.
 */
public class Playfield {

    public static  final int WIDTH = 12;
    public static int HEIGHT = 23;
    public static final Color WALL = Color.DARK_GRAY;
    public static final Color WELL = Color.BLACK;

    private Color[][] blocks;

    public Playfield() {
        blocks = new Color[WIDTH][HEIGHT];
        // Well
        for(int x = 1; x < WIDTH - 1; x++) {
            for(int y = 0; y < HEIGHT - 1; y++) {
                blocks[x][y] = WELL;
            }
        }
        // Walls
        for(int y = 0; y < HEIGHT - 1; y++) {
            blocks[0][y] = WALL;
            blocks[WIDTH-1][y] = WALL;
        }
        for(int x = 0; x < WIDTH; x++) {
            blocks[x][HEIGHT-1] = WALL;
        }
    }

    public Color[][] getBlocks() {
        return blocks;
    }

}
