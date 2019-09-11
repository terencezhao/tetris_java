package edu.uchicago.cs.java.finalproject.mvc.model;

import java.awt.*;

/**
 * The Leftfield is on the left side of the main playfield.
 * This panel contains the current level of the game, which determines the speed in which Tetrominos fall.
 * It also holds the saved piece which can be swapped once per locked block.
 *
 * Created by terencezhao on 11/26/15.
 */
public class LeftField {
    public static  final int WIDTH = 8;
    public static int HEIGHT = 21;
    public static final Color BACKGROUND = Color.LIGHT_GRAY;
    public static final Color NUMBER = Color.BLACK;

    private Color[][] blocks;

    /**
     * Set up the background of the playfield
     */
    public LeftField() {
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

    /**
     * Draw the held tetromino in the proper location
     * @param tetromino
     */
    public void setHold(Tetromino tetromino) {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT / 2; y++) {
                blocks[x][y] = BACKGROUND;
            }
        }
        for(Block block : tetromino.getPreview()) {
            blocks[block.getX() + 1][block.getY()] = block.getColor();
        }
    }

    /**
     * Update the value based on the game level.
     *
     * @param level
     */
    public void setLevel(int level) {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = HEIGHT / 2; y < HEIGHT; y++) {
                blocks[x][y] = BACKGROUND;
            }
        }
        int tensDigit = level / 10;
        int onesDigit = level % 10;
        // Tens Digit
        if(tensDigit > 0) {
            Color[][] tensDigitPixels = Digit.pixels(tensDigit, NUMBER, BACKGROUND);
            for(int row = 0; row < 5; row++) {
                for(int col = 0; col < 3; col++) {
                    blocks[1 + col][15 + row] = tensDigitPixels[row][col];
                }
            }
        }
        // Ones Digit
        Color[][] onesDigitPixels = Digit.pixels(onesDigit, NUMBER, BACKGROUND);
        for(int row = 0; row < 5; row++) {
            for(int col = 0; col < 3; col++) {
                blocks[4 + col][15 + row] = onesDigitPixels[row][col];
            }
        }
    }

    public void clearHold() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT / 2; y++) {
                blocks[x][y] = BACKGROUND;
            }
        }
    }
}
