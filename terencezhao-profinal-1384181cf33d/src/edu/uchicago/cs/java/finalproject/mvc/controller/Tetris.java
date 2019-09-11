package edu.uchicago.cs.java.finalproject.mvc.controller;

import edu.uchicago.cs.java.finalproject.mvc.model.Tetrion;
import edu.uchicago.cs.java.finalproject.mvc.view.TetrisPanel;
import edu.uchicago.cs.java.finalproject.sounds.Sound;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Tetris is the controller class for the game. It is composed of a Tetrion which represents the game model
 * and a tetrisPanel which represents the view. This class is responsible for mapping key events to in game actions,
 * maintaining the animation thread and timing of in game events such as the auto-dropping of blocks due to gravity.
 *
 */
public class Tetris implements Runnable, KeyListener {

	private Tetrion tetrion;
	private TetrisPanel tetrisPanel;
	public final static int ANIMATION_DELAY = 45;
	private Thread animationThread;
    private static int timer = 0;

	// Controls
    private final int ONE = 49; // Music Type A
    private final int TWO = 50; // Music Type B
    private final int THREE = 51; // Music Type C
    private final int M_KEY = 77; // Mute
	private final int O_KEY = 79; // Options
	private final int P_KEY = 80; // Pause
    private final int Q_KEY = 81; // Quit
    private final int S_KEY = 83;
	private final int ENTER = 10; // Start
	private final int SHIFT = 16; // save the current tetromino for later
	private final int SPACE_BAR = 32; // instantly drops tetromino down
	private final int LEFT_ARROW = 37; // moves tetromino left one square
	private final int UP_ARROW = 38; // rotates tetromino orientation
	private final int RIGHT_ARROW = 39; // moves tetromino right one square
    private final int DOWN_ARROW = 40; // moves tetromino down one square

	public Tetris() {
        tetrisPanel = new TetrisPanel();
        tetrion = new Tetrion();
		tetrisPanel.addKeyListener(this);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Tetris game = new Tetris();
                    game.startAnimationThread();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}

	private void startAnimationThread() {
		if (animationThread == null) {
			animationThread = new Thread(this);
			animationThread.start();
		}
	}

    /**
     * While the game is running, it can be in different states including: playing, paused, win, and lose.
     * While in game, the tetrion will automatically move the current piece 1 space downward to represent gravity.
     * As the level increases, the rate at which tetrominos drop automatically increases as well.
     */
	public void run() {
        Sound.musicIntro.loop(Clip.LOOP_CONTINUOUSLY);
		animationThread.setPriority(Thread.MIN_PRIORITY);
        long lStartTime = System.currentTimeMillis();

		while (Thread.currentThread() == animationThread) {
            tetrisPanel.drawBackground();
            if(!tetrion.isPlaying && !tetrion.isLoser && !tetrion.isWinner) {
                tetrisPanel.showIntroScreen();
            } else if(tetrion.isPaused) {
                tetrisPanel.showPauseScreen();
            } else if(tetrion.isWinner) {
                tetrisPanel.drawWinScreen();
            } else if(tetrion.isLoser) {
                tetrisPanel.drawLoseScreen();
            } else { // In Game
                if(tetrion.getLevel() > 10) {
                    tetrion.win();
                } else {
                    tetrisPanel.drawMatrix(tetrion.getPlayfield().getBlocks(), tetrion.getLeftField().getBlocks(), tetrion.getRightField().getBlocks(), tetrion.getScoreBoard().getBlocks());
                    incrmentTimer();
                    if(timer % (20 / (tetrion.getLevel())) == 0) {
                        tetrion.moveCurrentTetromino(Tetrion.DIRECTION.DOWN);
                    }
                }
            }
            try {
                lStartTime += ANIMATION_DELAY;
                Thread.sleep(Math.max(0, lStartTime - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                continue;
            }
		}
	}

    /**
     * Increments the game clock 1 tick per animation thread frame.
     */
    private void incrmentTimer() {
        if (timer == Integer.MAX_VALUE)
            timer = 0;
        else
            timer++;
    }

	// ===============================================
	// KEYLISTENER METHODS
	// ===============================================

    /**
     * When the game starts we set the state of the tetrion to playing.
     * We loop the game music, populate the preview and spawn the first tetromino into the playfield.
     */
    private void startGame() {
        if(!tetrion.isPlaying) {
            tetrion.isPlaying = true;
            tetrion.isPaused = false;
            Sound.musicIntro.stop();
            Sound.musicTypeA.loop(Clip.LOOP_CONTINUOUSLY);

            if(tetrion.getPreview().isEmpty()) {
                tetrion.populatePreview();
            }
            tetrion.spawnTetromino(tetrion.getPreview().poll());
        }
    }

    /**
     * When the game is paused, all game activity stops until it is unpaused.
     */
    private void pauseGame() {
        if(tetrion.isPlaying) {
            if(tetrion.isPaused) {
                tetrion.isPaused = false;
            } else {
                tetrion.isPaused = true;
            }
        }
    }

    /**
     * Tetrominos can be moves in any direction besides up, one space at a time.
     * However, tetrominos will collide with other tetrominos and walls and will not be able to move over them.
     * When a tetromino can no longer continue moving downward, it will be become locked.
     * @param direction
     */
    private void moveTetromino(Tetrion.DIRECTION direction) {
        if(tetrion.isPlaying && !tetrion.isPaused()) {
            Sound.playSound(Sound.move);
            tetrion.moveCurrentTetromino(direction);
        }
    }

    /**
     * Each tetromino can be rotated in increments of 90 degrees in the clockwise direction.
     * Walls and other locked tetrominos can impede rotation
     */
    private void rotateTetromino() {
        if(tetrion.isPlaying && !tetrion.isPaused()) {
            timer = 0;
            tetrion.rotateCurrentTetromino();
        }
    }

    /**
     * Dropping the tetromino instantly moves it as far down as possible and locks it immediately.
     */
    private void dropTetromino() {
        if(tetrion.isPlaying && !tetrion.isPaused()) {
            tetrion.dropCurrentTetromino();
        }
    }

    private void holdTetromino() {
        if(tetrion.isPlaying && !tetrion.isPaused()) {
            tetrion.holdCurrentTetromino();
        }
    }

    /**
     * This handles key input events used to interface with the game
     * @param e
     */
	@Override
	public void keyPressed(KeyEvent e) {
		int nKey = e.getKeyCode();
        System.out.println(nKey);
		switch (nKey) {
            case ONE:
                Sound.playMusic(Sound.musicTypeA);
                break;
            case TWO:
                Sound.playMusic(Sound.musicTypeB);
                break;
            case THREE:
                Sound.playMusic(Sound.musicTypeC);
                break;
            case S_KEY:
                startGame();
                break;
            case ENTER:
                startGame();
                break;
            case P_KEY:
                pauseGame();
                break;
            case Q_KEY:
                System.exit(0);
                break;
            case UP_ARROW:
                rotateTetromino();
                break;
            case LEFT_ARROW:
                moveTetromino(Tetrion.DIRECTION.LEFT);
                break;
            case RIGHT_ARROW:
                moveTetromino(Tetrion.DIRECTION.RIGHT);
                break;
            case DOWN_ARROW:
                moveTetromino(Tetrion.DIRECTION.DOWN);
                break;
            case SPACE_BAR:
                dropTetromino();
                break;
            case SHIFT:
                holdTetromino();
            default:
                break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

    }

}


