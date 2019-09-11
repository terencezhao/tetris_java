package edu.uchicago.cs.java.finalproject.mvc.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * The ScoreBoard keeps a tally of the game points earned while playing.
 * It is located above the playfield and updates each time a line is cleared.
 * Bonus points can be earned by clearing multiple lines simultaneously.
 * Performing a "Tetris", which means clearing 4 lines at once is worth 1200 points.
 * Created by terencezhao on 12/3/15.
 */
public class ScoreBoard {
    public static  final int WIDTH = 28;
    public static int HEIGHT = 7;
    public static final Color BACKGROUND = Color.DARK_GRAY;
    public static final Color NUMBER = Color.WHITE;

    private Color[][] blocks;

    public ScoreBoard() {
        blocks = new Color[WIDTH][HEIGHT];
        // Well
        for(int x = 0; x < WIDTH - 1; x++) {
            for(int y = 0; y < HEIGHT - 1; y++) {
                blocks[x][y] = BACKGROUND;
            }
        }
    }

    public Color[][] getBlocks() {
        return blocks;
    }

    public void setScore(int score) {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                blocks[x][y] = BACKGROUND;
            }
        }
        ArrayList<Integer> digits = new ArrayList<>();
        if(score == 0) {
            digits.add(0);
        }
        while (score > 0) {
            digits.add(score % 10);
            score /= 10;
        }
        int offset = 4;
        for(int digit : digits) {
            Color[][] scorePixels = Digit.pixels(digit, NUMBER, BACKGROUND);
            for(int row = 0; row < 5; row++) {
                for(int col = 0; col < 3; col++) {
                    blocks[28 - offset + col][row + 1] = scorePixels[row][col];
                }
            }
            offset += 4;
        }
    }
}
