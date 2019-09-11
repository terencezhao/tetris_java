package edu.uchicago.cs.java.finalproject.mvc.model;

import java.awt.*;

/**
 * The O Tetromino.
 * Other names include square and block.
 * Created by terencezhao on 11/18/15.
 */
public class O extends Tetromino {

    private Color color;
    private Color[][] rotationMatrix;
    private Color[][] rotated;

    public O() {
        super();
        spawn();
    }

    @Override
    public void rotate() {
        rotated = new Color[2][2];
        for(int x = 0; x < 2; x++) {
            for(int y = 0; y < 2; y++) {
                rotated[x][y] = rotationMatrix[y][1 - x];
            }
        }
    }

    @Override
    public Color[][] getRotationMatrix() {
        return rotationMatrix;
    }

    @Override
    public Block[] getPreview() {
        Block blockOne = new Block(2, 1, color);
        Block blockTwo = new Block(3, 1, color);
        Block blockThree = new Block(2, 2, color);
        Block blockFour = new Block(3, 2, color);
        Block[] preview = new Block[]{blockOne, blockTwo, blockThree, blockFour};
        return preview;
    }

    @Override
    public void spawn() {
        color = Color.YELLOW;
        setTLCX(5);
        setTLCY(0);
        Block blockOne = new Block(5, 0, color);
        Block blockTwo = new Block(6, 0, color);
        Block blockThree = new Block(5, 1, color);
        Block blockFour = new Block(6, 1, color);
        Block[] blocks = new Block[]{blockOne, blockTwo, blockThree, blockFour};

        // rotation matrix
        rotationMatrix = new Color[][]{
                {color, color},
                {color, color}
        };
        super.setBlocks(blocks);
    }

    @Override
    public void updateRotation() {
        rotationMatrix = rotated;
    }
}
