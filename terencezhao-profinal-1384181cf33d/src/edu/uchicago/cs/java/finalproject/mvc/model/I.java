package edu.uchicago.cs.java.finalproject.mvc.model;

import java.awt.*;

/**
 * The I Tetromino, also known as "straight", "stick", or "long" is line shaped
 * and is the only piece that can simultaneously clear four lines.
 *
 * Created by terencezhao on 11/18/15.
 */
public class I extends Tetromino {

    private Color color;
    private Color[][] rotationMatrix;
    private Color[][] rotated;

    public I() {
        super();
        spawn();
    }

    // To rotate clockwise by 90 degrees, first transpose blocks, then reverse rows
    @Override
    public void rotate() {
        rotated = new Color[4][4];
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                rotated[x][y] = rotationMatrix[y][3 - x];
            }
        }
    }

    @Override
    public Color[][] getRotationMatrix() {
        return rotationMatrix;
    }


    @Override
    public Block[] getPreview() {
        Block blockOne = new Block(1, 2, color);
        Block blockTwo = new Block(2, 2, color);
        Block blockThree = new Block(3, 2, color);
        Block blockFour = new Block(4, 2, color);
        Block[] preview = new Block[]{blockOne, blockTwo, blockThree, blockFour};
        return preview;
    }

    @Override
    public void spawn() {
        color = Color.CYAN;
        setTLCX(4);
        setTLCY(0);
        Block blockOne = new Block(4, 1, color);
        Block blockTwo = new Block(5, 1, color);
        Block blockThree = new Block(6, 1, color);
        Block blockFour = new Block(7, 1, color);
        Block[] blocks = new Block[]{blockOne, blockTwo, blockThree, blockFour};

        // rotation matrix

        rotationMatrix = new Color[][]{
                {Playfield.WELL, color, Playfield.WELL, Playfield.WELL},
                {Playfield.WELL, color, Playfield.WELL, Playfield.WELL},
                {Playfield.WELL, color, Playfield.WELL, Playfield.WELL},
                {Playfield.WELL, color, Playfield.WELL, Playfield.WELL}
        };
        super.setBlocks(blocks);
    }

    @Override
    public void updateRotation() {
        rotationMatrix = rotated;
    }


}
