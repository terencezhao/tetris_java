package edu.uchicago.cs.java.finalproject.mvc.view;

import java.awt.*;

/**
 *
 */
public class TetrisPanel extends Panel {

    public static final int SCORE_BOARD_HEIGHT = 7;
    public static final int SCORE_BOARD_WIDTH = 28;
    public static final int BLOCK_WIDTH = 20;
    public static final int BLOCK_HEIGHT = 20;
    public static final int PLAYFIELD_WIDTH = 12;
    public static final int PLAYFIELD_HEIGHT = 21;
    public static final int MARGIN_WIDTH = 8;
	public static final int FRAME_WIDTH = (PLAYFIELD_WIDTH + 2 * MARGIN_WIDTH) * BLOCK_WIDTH;
	public static final int FRAME_HEIGHT = BLOCK_HEIGHT * (SCORE_BOARD_HEIGHT + PLAYFIELD_HEIGHT);
	public static final Dimension FRAME_DIMENSIONS = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
	// ==============================================================
	// FIELDS 
	// ============================================================== 
	 
	// The following "off" vars are used for the off-screen double-bufferred image.
    private Dimension dimOff;
    private Image imgOff;
    private Graphics grpOff;

    private TetrisFrame tetrisFrame;
	private Font fnt = new Font("SansSerif", Font.BOLD, 12);
	private Font fntBig = new Font("SansSerif", Font.BOLD + Font.ITALIC, 36);
	private FontMetrics fmt; 
	private int nFontWidth;
	private int nFontHeight;
	private String strDisplay = "";
	

	// ==============================================================
	// CONSTRUCTOR 
	// ==============================================================
	
	public TetrisPanel(){
	    tetrisFrame = new TetrisFrame();
		tetrisFrame.getContentPane().add(this);
        tetrisFrame.getContentPane().setPreferredSize(FRAME_DIMENSIONS);
		tetrisFrame.pack();
		initView();

		tetrisFrame.setTitle("TETRIS");
		tetrisFrame.setResizable(false);
		tetrisFrame.setVisible(true);
		this.setFocusable(true);

        if (grpOff == null || FRAME_DIMENSIONS.width != dimOff.width || FRAME_DIMENSIONS.height != dimOff.height) {
            dimOff = FRAME_DIMENSIONS;
            imgOff = createImage(FRAME_DIMENSIONS.width, FRAME_DIMENSIONS.height);
            grpOff = imgOff.getGraphics();
        }
	}
	
	
	// ==============================================================
	// METHODS 
	// ==============================================================

    public void drawBackground() {
        // Fill in background with black.

        Graphics graphics = getGraphics();
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, FRAME_DIMENSIONS.width, FRAME_DIMENSIONS.height);
    }

	private void initView() {
		Graphics graphics = getGraphics();			// get the graphics context for the panel
        graphics.setFont(fnt);						// take care of some simple font stuff
		fmt = graphics.getFontMetrics();
		nFontWidth = fmt.getMaxAdvance();
		nFontHeight = fmt.getHeight();
        graphics.setFont(fntBig);					// set font info
	}

    public void drawMatrix( Color[][] playfield, Color[][] leftField, Color[][] rightField, Color[][] scoreBoard) {
        for(int x = 0; x < 28; x++) {
            for(int y = 0; y < SCORE_BOARD_HEIGHT; y++) {
                Color color = scoreBoard[x][y];
                grpOff.setColor(color);
                grpOff.fill3DRect(x * BLOCK_WIDTH, y * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT, true);
            }
        }
        for(int x = 0; x < PLAYFIELD_WIDTH; x++) {
            for(int y = 0; y < PLAYFIELD_HEIGHT; y++) {
                Color color = playfield[x][y + 2];
                grpOff.setColor(color);
                grpOff.fill3DRect((x + MARGIN_WIDTH) * BLOCK_WIDTH, (y + SCORE_BOARD_HEIGHT) * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT, true);
            }
        }
        for(int x = 0; x < MARGIN_WIDTH; x++) {
            for(int y = 0; y < PLAYFIELD_HEIGHT; y++) {
                Color color = leftField[x][y];
                grpOff.setColor(color);
                grpOff.fill3DRect(x * BLOCK_WIDTH, (y + SCORE_BOARD_HEIGHT) * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT, true);
            }
        }
        for(int x = 0; x < MARGIN_WIDTH; x++) {
            for(int y = 0; y < PLAYFIELD_HEIGHT; y++) {
                Color color = rightField[x][y];
                grpOff.setColor(color);
                grpOff.fill3DRect((x + PLAYFIELD_WIDTH + MARGIN_WIDTH) * BLOCK_WIDTH, (y + SCORE_BOARD_HEIGHT) * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT, true);
            }
        }
        Graphics graphics = getGraphics();
        graphics.drawImage(imgOff, 0, 0, this);
    }

    // TODO: DRAW "YOU WIN!" IN BLOCK LETTERS, then show high score page
    public void drawWinScreen() {
        for(int x = 0; x < 28; x++) {
            for(int y = 0; y < 28; y++) {
                grpOff.setColor(Color.LIGHT_GRAY);
                grpOff.fill3DRect(x * BLOCK_WIDTH, y * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_WIDTH, true);
            }
        }
        Graphics graphics = getGraphics();
        graphics.drawImage(imgOff, 0, 0, this);
    }

    // TODO: DRAW "GAME OVER" IN BLOCK LETTERS, then show high score page
    public void drawLoseScreen() {
        for(int x = 0; x < 28; x++) {
            for(int y = 0; y < 28; y++) {
                grpOff.setColor(Color.DARK_GRAY);
                grpOff.fill3DRect(x * BLOCK_WIDTH, y * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_WIDTH, true);
            }
        }

        Color letter = Color.BLACK;
        Color other = Color.DARK_GRAY;
        Color[][] game = {
                {letter, letter, letter, letter, other, other , letter, letter, other , other, letter, other , other , other , letter, other, letter, letter, letter, letter},
                {letter, other , other , other , other, letter, other , other , letter, other, letter, letter, other , letter, letter, other, letter, other , other , other },
                {letter, other , letter, letter, other, letter, letter, letter, letter, other, letter, other , letter, other , letter, other, letter, letter, letter, other },
                {letter, other , other , letter, other, letter, other , other , letter, other, letter, other , other , other , letter, other, letter, other , other , other },
                {letter, other , other , letter, other, letter, other , other , letter, other, letter, other , other , other , letter, other, letter, other , other , other },
                {letter, other , other , letter, other, letter, other , other , letter, other, letter, other , other , other , letter, other, letter, other , other , other },
                {letter, other , other , letter, other, letter, other , other , letter, other, letter, other , other , other , letter, other, letter, other , other , other },
                {letter, letter, letter, letter, other, letter, other , other , letter, other, letter, other , other , other , letter, other, letter, letter, letter, letter}
        };

        Color[][] over = {
                {letter, letter, letter, letter, other, letter, other , other , letter, other, letter, letter, letter, letter, other, letter, letter, letter, letter},
                {letter, other , other , letter, other, letter, other , other , letter, other, letter, other , other , other , other, letter, other , other , letter},
                {letter, other , other , letter, other, letter, other , other , letter, other, letter, letter, letter, other , other, letter, letter, letter, letter},
                {letter, other , other , letter, other, letter, other , other , letter, other, letter, other , other , other , other, letter, letter, other , other },
                {letter, other , other , letter, other, letter, other , other , letter, other, letter, other , other , other , other, letter, other , letter, other },
                {letter, other , other , letter, other, letter, other , other , letter, other, letter, other , other , other , other, letter, other , other , letter},
                {letter, other , other , letter, other, letter, other , other , letter, other, letter, other , other , other , other, letter, other , other , letter},
                {letter, letter, letter, letter, other, other , letter, letter, other , other, letter, letter, letter, letter, other, letter, other , other , letter}
        };

        for(int x = 0; x < game.length; x++) {
            for(int y = 0; y < game[0].length; y++) {
                Color color = game[x][y];
                grpOff.setColor(color);
                grpOff.fill3DRect((4 + y) * BLOCK_HEIGHT, (5 + x) * BLOCK_WIDTH, BLOCK_WIDTH, BLOCK_HEIGHT, true);
            }
        }

        for(int x = 0; x < over.length; x++) {
            for(int y = 0; y < over[0].length; y++) {
                Color color = over[x][y];
                grpOff.setColor(color);
                grpOff.fill3DRect((4 + y) * BLOCK_HEIGHT, (15 + x) * BLOCK_WIDTH, BLOCK_WIDTH, BLOCK_HEIGHT, true);
            }
        }


        Graphics graphics = getGraphics();
        graphics.drawImage(imgOff, 0, 0, this);
    }

    public void showPauseScreen() {

        Graphics graphics = getGraphics();
        graphics.setColor(Color.WHITE);

        strDisplay = "T E T R I S";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, 100);

        strDisplay = "Use the LEFT, RIGHT, and DOWN arrow keys to move the block.";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, FRAME_DIMENSIONS.height / 4
                        + nFontHeight + 20);

        strDisplay = "Use the UP arrow key to rotate the block.";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, FRAME_DIMENSIONS.height / 4
                        + nFontHeight + 40);

        strDisplay = "Use the SPACE key to instantly drop the block.";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, FRAME_DIMENSIONS.height / 4
                        + nFontHeight + 60);

        strDisplay = "Use the SHIFT key to save a block for later.";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, FRAME_DIMENSIONS.height / 4
                        + nFontHeight + 80);

        strDisplay = "Use the 1, 2, and 3 keys to change the game music.";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, FRAME_DIMENSIONS.height / 4
                        + nFontHeight + 100);

        strDisplay = "SCORE is shown in upper right.";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, FRAME_DIMENSIONS.height / 4
                        + nFontHeight + 150);

        strDisplay = "Clearing multiple lines gives bonus points.";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, FRAME_DIMENSIONS.height / 4
                        + nFontHeight + 170);

        strDisplay = "LEVEL is shown in lower left.";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, FRAME_DIMENSIONS.height / 4
                        + nFontHeight + 200);

        strDisplay = "Blocks drop faster at higher levels.";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, FRAME_DIMENSIONS.height / 4
                        + nFontHeight + 220);

        strDisplay = "LINES remaining are shown in lower right.";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, FRAME_DIMENSIONS.height / 4
                        + nFontHeight + 250);
        strDisplay = "Clear all the remaining lines to advance to the next level.";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, FRAME_DIMENSIONS.height / 4
                        + nFontHeight + 270);

        strDisplay = "Press P to un-pause the game.";
        graphics.drawString(strDisplay,
                (FRAME_DIMENSIONS.width - fmt.stringWidth(strDisplay)) / 2, FRAME_DIMENSIONS.height / 4
                        + nFontHeight + 350);
    }

	// This method draws some text to the middle of the screen before/after a game
	public void showIntroScreen() {
        Color back = Color.BLACK;
        Color redT = Color.RED;
        Color orangeE = Color.ORANGE;
        Color yellowT = Color.YELLOW;
        Color greenR = Color.GREEN;
        Color cyanI = Color.CYAN;
        Color magentaS = Color.MAGENTA;
        Color[][] title = {
                {redT, redT, redT, back, orangeE, orangeE, orangeE, back, yellowT, yellowT, yellowT, back, greenR, greenR, greenR, back, cyanI, back, magentaS, magentaS, magentaS},
                {back, redT, back, back, orangeE, back   , back   , back, back   , yellowT, back   , back, greenR, back  , greenR, back, back , back, magentaS, back    , back    },
                {back, redT, back, back, orangeE, orangeE, back   , back, back   , yellowT, back   , back, greenR, greenR, greenR, back, cyanI, back, magentaS, magentaS, magentaS},
                {back, redT, back, back, orangeE, back   , back   , back, back   , yellowT, back   , back, greenR, greenR, back  , back, cyanI, back, back    , back    , magentaS},
                {back, redT, back, back, orangeE, back   , back   , back, back   , yellowT, back   , back, greenR, greenR, greenR, back, cyanI, back, back    , back    , magentaS},
                {back, redT, back, back, orangeE, back   , back   , back, back   , yellowT, back   , back, greenR, back  , greenR, back, cyanI, back, back    , back    , magentaS},
                {back, redT, back, back, orangeE, back   , back   , back, back   , yellowT, back   , back, greenR, back  , greenR, back, cyanI, back, back    , back    , magentaS},
                {back, redT, back, back, orangeE, orangeE, orangeE, back, back   , yellowT, back   , back, greenR, back  , greenR, back, cyanI, back, magentaS, magentaS, magentaS}
        };
        for(int x = 0; x < 28; x++) {
            for(int y = 0; y < 28; y++) {
                grpOff.setColor(Color.BLACK);
                grpOff.fill3DRect(x * BLOCK_WIDTH, y * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_WIDTH, true);
            }
        }

        for(int x = 0; x < title.length; x++) {
            for(int y = 0; y < title[0].length; y++) {
                Color color = title[x][y];
                grpOff.setColor(color);
                grpOff.fill3DRect((3 + y) * BLOCK_HEIGHT, (10 + x) * BLOCK_WIDTH, BLOCK_WIDTH, BLOCK_HEIGHT, true);
            }
        }
        Graphics graphics = getGraphics();
        Graphics off = imgOff.getGraphics();
        off.setColor(Color.WHITE);
        off.drawString("PRESS ENTER", (FRAME_WIDTH - fmt.stringWidth(strDisplay)) / 2 - 40, FRAME_HEIGHT - 100);
        graphics.drawImage(imgOff, 0, 0, this);
	}
	
	public TetrisFrame getFrm() {return this.tetrisFrame;}
	public void setFrm(TetrisFrame frm) {this.tetrisFrame = frm;}
}