package edu.uchicago.cs.java.finalproject.mvc.model;

import edu.uchicago.cs.java.finalproject.sounds.Sound;

import java.awt.*;
import java.util.*;

/**
 * The action of Tetris happens within a machine called a tetrion.
 *
 * The largest part of the tetrion consists of the playfield measuring ten spaces across by twenty spaces down.
 * It has other parts explained below.
 *
 * Randomly selected tetrominoes, or shapes consisting of four square blocks, fall from the top of the playfield one at a time.
 * Each tetromino enters the playfield with a given orientation and color depending on its shape.
 * Part of the tetrion, called the piece preview, shows the next pieces that will enter the playfield.
 *
 * The player can rotate the falling tetromino ninety degrees at a time within the plane of the playfield by pressing the counterclockwise or clockwise rotation buttons, provided the piece has room to rotate.
 * Some versions of the game nudge the tetromino away from the wall or other blocks in order to make room.
 *
 * The player can shift the falling tetromino sideways one space at a time by pressing the left or right arrow or holding it for quicker movement, provided the piece has room to move.
 * Pieces cannot shift through walls or other blocks.
 *
 * At the top left, or in some cases the bottom right, of the tetrion is an area called the hold box where a player can store a tetromino for later use.
 * At any time while a tetromino falls, the player can move it to the hold box, and any tetromino held earlier now moves to the top of the playfield.
 * After moving a tetromino to the hold box, the player must then lock the resulting tetromino before holding again (see below).
 *
 * Each tetromino moves downward slowly by itself. Generally a player can use some method to "drop" the tetromino, or make it move downward faster.
 * Once the tetromino lands on the floor or other blocks, the piece will delay shortly before locking in which time the player can move it.
 * After locking, a player can no longer move the tetromino.
 *
 * When a tetromino locks and by doing so fills all empty spaces within one or more rows of the playfield, those full rows will clear.
 * Remaining blocks above will move down by as many rows removed.
 *
 * If the playfield has not filled up with blocks, the next piece enters.

 If the playfield has not filled up with blocks, the next piece enters.
 * Created by terencezhao on 11/19/15.
 */

public class Tetrion {

    public static final Color BACKGROUND_COLOR = Color.BLACK;
    public static boolean isPlaying;
    public static boolean isWinner;
    public static boolean isLoser;
    public static boolean isPaused;
    public static int score;

    public static final int COLUMN_COUNT = 12;
    public static final int ROW_COUNT = 21;
    public enum DIRECTION {LEFT, RIGHT, DOWN}

    private Playfield playfield;
    private ScoreBoard scoreBoard;
    private LeftField leftField;
    private RightField rightField;
    private Tetromino current;
    private LinkedList<Tetromino> preview;
    private LinkedList<Tetromino> hold;

    private int lines;
    private int level;
    private int timer;
    private boolean isHoldReady;

    public Tetrion() {
        playfield = new Playfield();
        scoreBoard = new ScoreBoard();
        leftField = new LeftField();
        rightField = new RightField();
        preview = new LinkedList<>();
        hold = new LinkedList<>();
        score = 0;
        scoreBoard.setScore(score);
        level = 1;
        leftField.setLevel(level);
        lines = 10;
        rightField.setLinesLeft(lines);
        isPaused = false;
        isPlaying = false;
        isWinner = false;
        isLoser = false;
        timer = 0;
        isHoldReady = true;
    }

    public LinkedList<Tetromino> getPreview() {
        return preview;
    }
    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public Playfield getPlayfield() {
        return playfield;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public LeftField getLeftField() {
        return leftField;
    }

    public RightField getRightField() {
        return rightField;
    }

    /**
     * Randomly chooses the next tetromino to be played.
     */
    public void populatePreview() {
        Random random = new Random();
        int type = random.nextInt(6);
        String tetrominoType = Tetromino.Type.values()[type].name();
        Tetromino tetromino = null;
        switch(tetrominoType) {
            case "I":
                tetromino = new I();
                break;
            case "O":
                tetromino = new O();
                break;
            case "T":
                tetromino = new T();
                break;
            case "J":
                tetromino = new J();
                break;
            case "L":
                tetromino = new L();
                break;
            case "S":
                tetromino = new S();
                break;
            case "Z":
                tetromino = new Z();
                break;
            default:
                break;
        }
        preview.offer(tetromino);
        rightField.setPreview(preview.peek());
    }

    /**
     * This method takes the tetromino from the preview and puts it in the playfield.
     * It then re-populates the preview for the next tetromino
     * @param tetromino
     * @return
     */
    public Tetromino spawnTetromino(Tetromino tetromino) {
        current = tetromino;
        for(Block block : current.getBlocks()) {
            if(playfield.getBlocks()[block.getX()][block.getY()] != Playfield.WELL) {
                lose();
            } else {
                playfield.getBlocks()[block.getX()][block.getY()] = block.getColor();
            }
        }
        populatePreview();
        return current;
    }

    /**
     * This method moves the current tetromino one space in the given direction.
     * We first clear the current tetromino from the playfield so that we don't collide with ourself.
     * We then need to update the top left and top right reference point of the rotation matrix.
     * Before actually moving the tetromino we calculate its intended target position and check if movement is allowed.
     * If there are any other tetrominos or walls found in the way, we redraw in the original position.
     * Once a tetromino can no longer move down, it becomes locked and the next one comes into play.
     * @param direction
     * @return
     */
    public boolean moveCurrentTetromino(DIRECTION direction) {
        boolean isMovable = true;
        Block[] moved = new Block[4];

        // Remove current piece from the playfield to avoid block colliding with itself
        for(Block block : current.getBlocks()) {
            playfield.getBlocks()[block.getX()][block.getY()] = Playfield.WELL;
        }

        // Update rotation matrix
        int tlcX = current.getTLCX();
        int tlcY = current.getTLCY();
        switch(direction) {
            case LEFT:
                tlcX -= 1;
                break;
            case RIGHT:
                tlcX += 1;
                break;
            case DOWN:
                tlcY += 1;
                break;
            default:
                break;
        }

        for(int index = 0; index < current.getBlocks().length; index++) {
            Block block = current.getBlocks()[index];
            int x = block.getX();
            int y = block.getY();
            switch(direction) {
                case LEFT:
                    x -= 1;
                    break;
                case RIGHT:
                    x += 1;
                    break;
                case DOWN:
                    y += 1;
                    break;
                default:
                    break;
            }
            Color targetBlock = playfield.getBlocks()[x][y];
            if(targetBlock.equals(Playfield.WELL)) {
                Block movedBlock = new Block(x, y, block.getColor());
                moved[index] = movedBlock;
            } else {
                isMovable = false;
                break;
            }
        }
        if(isMovable) {
            // draw the tetromino in its new position
            for(Block block : moved) {
                playfield.getBlocks()[block.getX()][block.getY()] = block.getColor();
            }
            // update top left corner of rotation matrix
            current.setTLCX(tlcX);
            current.setTLCY(tlcY);
            current.setBlocks(moved);
        } else {
            // Draw the tetromino back to its current position
            for(Block block : current.getBlocks()) {
                playfield.getBlocks()[block.getX()][block.getY()] = block.getColor();
            }
            // Lock the Tetromino if it cannot move down any further
            if(direction.equals(DIRECTION.DOWN)) {
                Sound.playSound(Sound.land);
                populatePreview();
                clearLines();
                spawnTetromino(preview.poll());
                isHoldReady = true;
            }
        }
        return isMovable;
    }

    /**
     * Rotating a tetromino is somewhat similar to moving, in that we check for potential collisions before taking action.
     * Each tetromino type has an initial orientation that is stored in its rotation matrix.
     * On each rotatation, we essential do a transpose of the x and y coordinates of each block and then reverse the rows.
     * This creates a new rotation matrix where all the blocks are rotated 90 clockwise from their previous positions.
     * In order to draw the rotated pieces, we need to update the reference point to which the actual position will be transcribed.
     * We only update the rotation matrix once a piece is confirmed to be able to rotate successfully in its current state.
     */
    public void rotateCurrentTetromino() {
        current.rotate();
        Color[][] rotated = current.getRotationMatrix();
        Color[][] playfield = getPlayfield().getBlocks();
        // Remove current piece from the playfield to avoid block colliding with itself
        for(Block block : current.getBlocks()) {
            playfield[block.getX()][block.getY()] = Playfield.WELL;
        }
        boolean isRotatable = true;
        for(int x = 0; x < rotated.length; x++) {
            for(int y = 0; y < rotated.length; y++) {
                if(rotated[x][y] != Playfield.WELL) {
                    // Check that the corresponding playfield block is open
                    int targetX = x + current.getTLCX();
                    int targetY = y + current.getTLCY();
                    if(targetX < 1 || x > 11 || targetY < 0 || targetY > 21) {
                        isRotatable = false;
                        break;
                    }
                    if(playfield[targetX][targetY] != Playfield.WELL) {
                        isRotatable = false;
                        break;
                    }
                }
            }
        }
        if(isRotatable) {
            current.updateRotation();
            rotated = current.getRotationMatrix();
            Sound.playSound(Sound.rotate);
            Block[] rotatedBlocks = new Block[4];
            int index = 0;
            for(int x = 0; x < rotated.length; x++) {
                for(int y = 0; y < rotated.length; y++) {
                    if(rotated[x][y] != Playfield.WELL) {
                        Block rotatedBlock = new Block(current.getTLCX() + x, current.getTLCY() + y, rotated[x][y]);
                        rotatedBlocks[index] = rotatedBlock;
                        index++;
                        playfield[x + current.getTLCX()][y + current.getTLCY()] = rotated[x][y];
                    }
                }
            }
            current.setBlocks(rotatedBlocks);
        } else {
            for(Block block : current.getBlocks()) {
                playfield[block.getX()][block.getY()] = block.getColor();
            }
        }
    }

    /**
     * This method simulates a instantaneous drop by repeatedly calling move down on the tetromino
     */
    public void dropCurrentTetromino() {
        while(moveCurrentTetromino(DIRECTION.DOWN)) {}
    }

    /**
     * Holding the current tetromino stores it for later use.
     * If the holding space is unoccupied, the current tetromino will be saved and the next one will spawn.
     * However, if the holding space is occupied, then the current tetromino will be swapped.
     * The tetromino that was in holding will be re-spawned from the top of the playfield.
     */
    public void holdCurrentTetromino() {
        if (isHoldReady) {
            Sound.playSound(Sound.option);
            for(Block block : current.getBlocks()) {
                playfield.getBlocks()[block.getX()][block.getY()] = Playfield.WELL;
            }
            if (hold.isEmpty()) {
                hold.offer(current);
                spawnTetromino(preview.poll());
            } else {
                hold.offer(current);
                Tetromino held = hold.poll();
                held.spawn();
                spawnTetromino(held);
            }
            leftField.setHold(hold.peek());
            isHoldReady = false;
        } else {
            // already used hold recently, need to lock current piece to use hold again
        }
    }

    /**
     * This method scans the playfield for complete horizontal lines and removes them.
     * It is also responsible for shifting down the remaining blocks above based on the number of lines cleared.
     * When lines are cleared, the number of lines remaining and the score will also be updated.
     */
    public void clearLines() {
        int linesCleared = 0;
        for(int row = 21; row >= 0 ; row--) {
            boolean hasLine = true;
            for (int col = 1; col < 11; col++) {
                if (playfield.getBlocks()[col][row].equals(Playfield.WELL)) {
                    hasLine = false;
                    break;
                }
            }
            if (hasLine) {
                for (int col = 1; col < 11; col++) {
                    playfield.getBlocks()[col][row] = Playfield.WELL;
                }
                linesCleared++;
            }
        }
        if(linesCleared > 0) {
            Sound.playSound(Sound.clearLine);
        }
        if(linesCleared == 4) {
            Sound.playSound(Sound.tetris);
        }
        updateScore(linesCleared);
        lines -= linesCleared;
        rightField.setLinesLeft(lines);
        if(lines <= 0) {
            Sound.playSound(Sound.levelUp);
            clearPlayfield();
            hold.clear();
            leftField.clearHold();
        }

        // shift blocks down
        for(int i = 0; i < linesCleared; i++) {
            for(int row = 21; row > 0; row--) {
                boolean isEmpty = true;
                for(int col = 1; col < 11; col++) {
                    if(playfield.getBlocks()[col][row] != Playfield.WELL) {
                        isEmpty = false;
                    }
                }
                if(isEmpty) {
                    for(int col = 1; col < 11; col++) {
                        playfield.getBlocks()[col][row] = playfield.getBlocks()[col][row - 1];
                        playfield.getBlocks()[col][row - 1] = Playfield.WELL;
                    }
                }
            }
        }
    }

    /**
     * Used to wipe all blocks from the playfield.
     */
    private void clearPlayfield() {
        for(int x = 1; x < 11; x++) {
            for(int y = 0; y < 22; y++) {
                playfield.getBlocks()[x][y] = Playfield.WELL;
            }
        }
        level++;
        leftField.setLevel(level);
        lines = 10;
        rightField.setLinesLeft(lines);
    }

    /**
     * Handles the scoring for the game.
     * Clearing multiple lines gives more points
     * @param linesCleared
     */
    private void updateScore(int linesCleared) {
        switch(linesCleared) {
            case 1:
                score += 40 * level;
                break;
            case 2:
                score += 100 * level;
                break;
            case 3:
                score += 300 * level;
                break;
            case 4:
                score += 1200 * level;
        }
        scoreBoard.setScore(score);
    }

    /**
     * Sets the game state to win
     */
    public void win() {
        isPlaying = false;
        Sound.playSound(Sound.win);
        isWinner = true;
    }

    /**
     * Sets the game state to lose
     */
    public void lose() {
        Sound.playSound(Sound.gateClose);
        Sound.stopMusic();
        isPlaying = false;
        isLoser = true;
    }


}
