package edu.uchicago.cs.java.finalproject.mvc.model;

import java.awt.*;

/**
 * The RightField is on the right of the main playfield.
 * Its purpose is to display the preview of the next tetromino.
 * It also displays the current number of lines remaining to be cleared before entering the next level
 * Created by terencezhao on 11/26/15.
 */
public class RightField {
    public static  final int WIDTH = 8;
    public static int HEIGHT = 21;
    public static final Color BACKGROUND = Color.LIGHT_GRAY;
    public static final Color NUMBER = Color.BLACK;

    private Color[][] blocks;

    public RightField() {
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

    public void setPreview(Tetromino tetromino) {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT / 2; y++) {
                blocks[x][y] = BACKGROUND;
            }
        }
        for(Block block : tetromino.getPreview()) {
            blocks[1 + block.getX()][block.getY()] = block.getColor();
        }
    }

    public void setLinesLeft(int linesLeft) {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = HEIGHT / 2; y < HEIGHT; y++) {
                blocks[x][y] = BACKGROUND;
            }
        }
        int tensDigit = linesLeft / 10;
        int onesDigit = linesLeft % 10;
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
}
