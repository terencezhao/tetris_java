package edu.uchicago.cs.java.finalproject.mvc.model;

import java.awt.*;

/**
 * The T Tetromino.
 * Other names include The Tetris Block,T turn
 * Created by terencezhao on 11/18/15.
 */
public class T extends Tetromino {

    private Color color;
    private Color[][] rotationMatrix;
    private Color[][] rotated;

    public T() {
        super();
        spawn();
    }

    @Override
    public void rotate() {
        rotated = new Color[3][3];
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                rotated[x][y] = rotationMatrix[y][2 - x];
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
        Block blockFour = new Block(2, 1, color);
        Block[] preview = new Block[]{blockOne, blockTwo, blockThree, blockFour};
        return preview;
    }

    @Override
    public void spawn() {
        color = Color.MAGENTA;
        setTLCX(4);
        setTLCY(0);
        Block blockOne = new Block(5, 0, color);
        Block blockTwo = new Block(4, 1, color);
        Block blockThree = new Block(5, 1, color);
        Block blockFour = new Block(6, 1, color);
        Block[] blocks = new Block[]{blockOne, blockTwo, blockThree, blockFour};

        // rotation matrix
        rotationMatrix = new Color[][]{
                {Playfield.WELL, color, Playfield.WELL},
                {color, color, Playfield.WELL},
                {Playfield.WELL, color, Playfield.WELL}
        };
        super.setBlocks(blocks);
    }

    @Override
    public void updateRotation() {
        rotationMatrix = rotated;
    }
}
