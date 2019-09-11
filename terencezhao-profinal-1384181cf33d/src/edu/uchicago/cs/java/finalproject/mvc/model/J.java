package edu.uchicago.cs.java.finalproject.mvc.model;

import java.awt.*;

/**
 * The J Tetromino.
 * Other names include gamma, inverse L, or left gun.
 * Created by terencezhao on 11/18/15.
 */
public class J extends Tetromino {

    private Color color;
    private Color[][] rotationMatrix;
    private Color[][] rotated;

    public J() {
        super();
        spawn();
    }

    public void spawn() {
        color = Color.BLUE;
        setTLCX(4);
        setTLCY(0);
        Block blockOne = new Block(4, 0, color);
        Block blockTwo = new Block(4, 1, color);
        Block blockThree = new Block(5, 1, color);
        Block blockFour = new Block(6, 1, color);
        Block[] blocks = new Block[]{blockOne, blockTwo, blockThree, blockFour};

        // rotation matrix
        rotationMatrix = new Color[][]{
                {color, color, Playfield.WELL},
                {Playfield.WELL, color, Playfield.WELL},
                {Playfield.WELL, color, Playfield.WELL}
        };
        super.setBlocks(blocks);
    }

    @Override
    public void updateRotation() {
        rotationMatrix = rotated;
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
        Block blockOne = new Block(1, 1, color);
        Block blockTwo = new Block(1, 2, color);
        Block blockThree = new Block(2, 2, color);
        Block blockFour = new Block(3, 2, color);
        Block[] preview = new Block[]{blockOne, blockTwo, blockThree, blockFour};
        return preview;
    }

}
