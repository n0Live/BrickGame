package com.kry.brickgame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.kry.brickgame.Board.Cell;
import com.kry.brickgame.games.Game.Status;

/**
 * @author noLive
 * 
 */
public class Draw extends JPanel implements GameListener {

	// TODO put in order

	private static final long serialVersionUID = 1043017116324502441L;

	/**
	 * Size of a single cell of a board
	 */
	public static final int SQUARE_SIZE = 18;

	/* LABELS */
	private static final String HI = "HI";
	private static final String SCORE = "SCORE";
	private static final String NEXT = "NEXT";
	private static final String LINES = "LINES";
	private static final String SPEED = "SPEED";
	private static final String LEVEL = "LEVEL";
	private static final String ROTATE = "ROTATE";
	private static final String PAUSE = "PAUSE";
	private static final String GAME_OVER = "GAME OVER";
	private static final String COMING_SOON = "COMING SOON";

	/* ICONS */
	private static final String ICON_MUSIC = "\ue602";
	private static final String ICON_ROTATE_RIGTH = "\ue600";
	private static final String ICON_ROTATE_LEFT = "\ue601";
	private static final String ICON_PAUSE = "\ue603";

	private Board board = null;
	private Board preview = null;

	/**
	 * Color of inactive elements
	 */
	private final Color emptyColor = new Color(60, 60, 60, 20);
	/**
	 * Color of active elements
	 */
	private final Color fullColor = new Color(40, 40, 40, 255);
	/**
	 * Color of elements that change their state from active to inactive
	 */
	private Color blinkColor = fullColor;
	/**
	 * Background color
	 */
	private Color bgColor = new Color(136, 153, 107);

	/* Numerical values */
	private String scores;
	private String speed;
	private String level;

	/**
	 * Game status
	 */
	private Status status;

	/**
	 * Font for digital data (score, etc.)
	 */
	private Font digitalFont;
	/**
	 * Font for textual data (labels, etc.)
	 */
	private Font textFont;
	/**
	 * Font of icons
	 */
	private Font iconFont;

	/* Font sizes */
	private final int digitalFontSize = 26;
	private final int textFontSize = 11;
	private final int iconFontSize = 12;

	private Dimension size;

	/**
	 * Main canvas, combining all elements
	 */
	private BufferedImage canvas;
	/**
	 * Canvas that is used to display the main board
	 */
	private BufferedImage boardCanvas;
	/**
	 * Canvas that is used to display the preview board
	 */
	private BufferedImage previewCanvas;

	private BufferedImage background;

	/**
	 * Flag for blinking "Pause" icon
	 */
	private boolean showPauseIcon = false;

	public Draw() {
		super();
		/* initialize the fonts */
		textFont = new Font(Font.SANS_SERIF, Font.BOLD, textFontSize);
		try {
			// trying to get the font from a resource file
			digitalFont = Font.createFont(
					Font.TRUETYPE_FONT,
					getClass().getResourceAsStream(
							"/fonts/Segment7Standard.otf"))//
					.deriveFont((float) digitalFontSize);// set the font size

		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			// if error just create a monospaced font
			digitalFont = new Font(Font.MONOSPACED, Font.PLAIN, digitalFontSize);
		}
		try {
			iconFont = Font.createFont(Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/fonts/icomoon.ttf"))
					.deriveFont((float) iconFontSize);

		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			// If error does not use the font
			iconFont = null;
		}

		try {
			background = new BufferedImage(360, 640,
					BufferedImage.TYPE_INT_ARGB);

			Graphics g = background.getGraphics();
			g.setColor(Color.DARK_GRAY);
			g.drawRoundRect(0, 0, 360, 640, 30, 30);
			g.drawImage(
					ImageIO.read(getClass().getResourceAsStream(
							"/images/background.png")), 0, 0, 360, 640,
					Color.DARK_GRAY, null);
		} catch (Exception e) {
			e.printStackTrace();
			background = null;
		}

		scores = "0";
		speed = "1";
		level = "1";

	}

	protected Board getBoard() {
		return board;
	}

	protected void setBoard(Board board) {
		this.board = board;

		if (boardCanvas == null)
			boardCanvas = initCanvas(board);
	}

	protected Board getPreview() {
		return preview;
	}

	protected void setPreview(Board preview) {
		this.preview = preview;

		if (previewCanvas == null)
			previewCanvas = initCanvas(preview);
	}

	/**
	 * Converting the width of the board from cells to pixels
	 * 
	 * @param board
	 *            the board for which determine the width in pixels
	 * @return the width of the board in pixels
	 * @see #boardHeightInPixels
	 */
	private static int boardWidthInPixels(Board board) {
		return board.getWidth() * SQUARE_SIZE;
	}

	/**
	 * Converting the height of the board from the <b>visible</b> cells to
	 * pixels
	 * 
	 * @param board
	 *            the board for which determine the width in pixels
	 * @return the height of the board in pixels
	 * @see #boardWidthInPixels
	 */
	private static int boardHeightInPixels(Board board) {
		return board.getHeight() * SQUARE_SIZE;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// bgColor = getBackground();

		// get new size of this component
		size = getSize();
		// if the main canvas is not created or the size changed
		/*
		 * if ((canvas == null) || (canvas.getWidth() != size.width) ||
		 * (canvas.getHeight() != size.height)) { // create the main canvas
		 * canvas = initCanvas(size.width, size.height); }
		 */
		canvas = initCanvas(SQUARE_SIZE * 14 + (SQUARE_SIZE / 2) - 1,
				SQUARE_SIZE * 20 + 5 + (SQUARE_SIZE / 2));

		clearCanvas(canvas, bgColor);

		// draw the board and the preview
		updateCanvas(boardCanvas, board);
		updateCanvas(previewCanvas, preview);
		canvasSetBorder(boardCanvas);

		// append the board and the preview to the main canvas
		appendCanvas(canvas, boardCanvas, 2, (SQUARE_SIZE / 4) + 2);
		appendCanvas(canvas, previewCanvas, boardCanvas.getWidth() + 4,
				(5 * SQUARE_SIZE) + (SQUARE_SIZE / 4) + 2);
		// append labels and icons
		drawLabelsAndIcons();

		// TODO draw background above canvas
		appendCanvas(background, canvas, 50, 52);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.drawRenderedImage(background, null);
	}

	/**
	 * Creating the canvas specified width and height
	 * 
	 * @param width
	 *            width of the created canvas
	 * @param height
	 *            height of the created canvas
	 */
	private static BufferedImage initCanvas(int width, int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * Creating the canvas based on a board
	 * 
	 * @param board
	 *            board, the width and the height of which will be used to
	 *            create of the canvas
	 * @return {@code null} if the board is not defined, otherwise - the new
	 *         canvas
	 */
	private static BufferedImage initCanvas(Board board) {
		if (board == null)
			return null;

		// increasing of the width and height by 2 needed to draw borders
		return initCanvas(boardWidthInPixels(board) + 2,
				boardHeightInPixels(board) + 2);
	}

	/**
	 * Clears the canvas by filling it with the background color
	 * 
	 * @param canvas
	 *            the canvas to clear
	 * @param bgColor
	 *            the background color
	 */
	protected static void clearCanvas(BufferedImage canvas, Color bgColor) {
		if (canvas == null)
			return;

		Graphics2D g2d = canvas.createGraphics();
		g2d.setBackground(bgColor);
		g2d.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		g2d.dispose();
	}

	/**
	 * Draws the borders around the canvas
	 * 
	 * @param canvas
	 *            the canvas to draw the borders
	 */
	protected void canvasSetBorder(BufferedImage canvas) {
		if (canvas == null)
			return;

		Graphics2D g2d = canvas.createGraphics();

		g2d.setColor(fullColor);
		g2d.setStroke(new BasicStroke(5));// set thickness of the line
		g2d.drawRect(0, 0, canvas.getWidth() - 1, canvas.getHeight() - 1);

		g2d.dispose();
	}

	/**
	 * Draws the contents of the board to the canvas
	 * 
	 * @param canvas
	 *            the target canvas
	 * @param board
	 *            the board whose contents need to be drawn
	 */
	protected void drawBoardOnCanvas(BufferedImage canvas, Board board) {
		if ((canvas == null) || (board == null))
			return;

		int boardWidth = board.getWidth();
		int boardHeight = board.getHeight();

		Graphics2D g2d = canvas.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// draw squares of the board
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				// "boardHeight - i - 1" - the board reads from the bottom up
				Cell fill = board.getCell(j, boardHeight - i - 1);
				drawSquare(g2d, j * SQUARE_SIZE + 1, i * SQUARE_SIZE + 1, fill);
			}
		}
		g2d.dispose();
	}

	/**
	 * Draws one square from a board
	 * 
	 * @param g2d
	 *            {@code Graphics2D} object, which used to draw on it
	 * @param x
	 *            x-coordinate of upper left corner of the square to be drawn
	 * @param y
	 *            y-coordinate of upper left corner of the square to be drawn
	 * @param fill
	 *            fill type: {@code Full}, {@code Empty} or {@code Blink}
	 */
	private void drawSquare(Graphics2D g2d, int x, int y, Cell fill) {
		Color colors[] = { emptyColor, fullColor, blinkColor };

		Color color = colors[fill.ordinal()];
		g2d.setColor(color);

		// set thickness of the line
		g2d.setStroke(new BasicStroke((float) 2));
		// draw the frame
		g2d.drawRect(x + 2, y + 2, SQUARE_SIZE - 4, SQUARE_SIZE - 4);
		// draw the inner square
		g2d.fillRect(x + 2 + (SQUARE_SIZE / 4), y + 2 + (SQUARE_SIZE / 4),// x +
																			// 1
				SQUARE_SIZE / 2 - 2, SQUARE_SIZE / 2 - 2);
	}

	/**
	 * Clears the canvas and draws on it the contents of the board
	 * 
	 * @param canvas
	 *            the target canvas
	 * @param board
	 *            the board whose contents need to be drawn
	 */
	protected void updateCanvas(BufferedImage canvas, Board board) {
		if (canvas == null)
			return;

		clearCanvas(canvas, bgColor);
		drawBoardOnCanvas(canvas, board);
	}

	/**
	 * Adding to the {@code targetCanvas} the image from the
	 * {@code sourceCanvas}
	 * 
	 * @param targetCanvas
	 *            the canvas on which the image to be added
	 * @param sourceCanvas
	 *            the canvas, the image from which to be added
	 * @param x
	 *            x-coordinate of upper left corner of the image to be added
	 * @param y
	 *            y-coordinate of upper left corner of the image to be added
	 */
	protected void appendCanvas(BufferedImage targetCanvas,
			BufferedImage sourceCanvas, int x, int y) {
		if ((targetCanvas == null) || (sourceCanvas == null))
			return;

		Graphics2D g2d = targetCanvas.createGraphics();

		int width = (sourceCanvas.getWidth() > targetCanvas.getWidth()) ? targetCanvas
				.getWidth() : sourceCanvas.getWidth();
		int height = (sourceCanvas.getHeight() > targetCanvas.getHeight()) ? targetCanvas
				.getHeight() : sourceCanvas.getHeight();

		g2d.drawImage(sourceCanvas, x, y, width, height, this);

		g2d.dispose();
	}

	/**
	 * Draws the text labels on the canvas.
	 * <p>
	 * First, the {@code backgroundText} is drawn in {@code emptyColor} color,
	 * then the {@code foregroundText} is drawn in {@code fullColor} color. The
	 * length of the {@code backgroundText} should not exceed the length of the
	 * {@code foregroundText}.
	 * 
	 * @param canvas
	 *            the target canvas
	 * @param backgroundText
	 *            the text that displayed in the background
	 * @param foregroundText
	 *            the text that displayed in the foreground
	 * @param font
	 *            text font
	 * @param x
	 *            x-coordinate of lower left corner of the text to be drawn
	 * @param y
	 *            y-coordinate of lower left corner of the text to be drawn
	 * 
	 */
	protected void drawTextOnCanvas(BufferedImage canvas,
			String backgroundText, String foregroundText, Font font, int x,
			int y) {
		if (canvas == null)
			return;

		Graphics2D g2d = canvas.createGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setFont(font);
		// need to determine the size of the text area
		FontMetrics fm = g2d.getFontMetrics();

		// clear the text area
		g2d.setBackground(bgColor);
		g2d.clearRect(x, y - font.getSize(), fm.stringWidth(backgroundText),
				font.getSize());

		// forming a string for text formatting, based on backgroundText
		// like "%5.5s"
		// http://download.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax
		StringBuilder formatString = new StringBuilder("%")
				.append(backgroundText.length()).append(".")
				.append(backgroundText.length()).append("s");

		// draws the backgroundText
		g2d.setColor(emptyColor);
		g2d.drawString(String.format(formatString.toString(), backgroundText),
				x, y);

		// draws the foregroundText
		g2d.setColor(fullColor);
		g2d.drawString(String.format(formatString.toString(), foregroundText),
				x, y);

		g2d.dispose();

	}

	/**
	 * Draws all text labels and icons on the main canvas
	 */
	protected void drawLabelsAndIcons() {
		if ((canvas == null) || (scores == null))
			return;

		Font biggerTextFont = textFont.deriveFont((float) (textFontSize + 1));
		FontMetrics fm;
		int x, y, space;

		int indent = 2;

		/* Scores */
		x = indent + boardCanvas.getWidth();
		y = indent + digitalFontSize;

		drawTextOnCanvas(canvas, "18888", scores, digitalFont, x, y);
		/* --- */

		/* Scores label */
		x = indent * 4 + boardCanvas.getWidth();
		y = indent + (2 * SQUARE_SIZE) + textFontSize;
		fm = getGraphics().getFontMetrics(textFont);
		space = fm.stringWidth(HI) + 1;

		drawTextOnCanvas(canvas, HI, "", textFont.deriveFont(Font.PLAIN), x, y);
		drawTextOnCanvas(canvas, SCORE, SCORE, textFont.deriveFont(Font.PLAIN),
				x + space, y);
		/* --- */

		/* Music icon */
		space = fm.stringWidth(HI + SCORE) + indent * 4;

		if (iconFont != null)
			drawTextOnCanvas(canvas, ICON_MUSIC, ICON_MUSIC, iconFont, x
					+ space, y);
		/* --- */

		/* Next/Lines labels */
		x = indent * 4 + boardCanvas.getWidth();
		y = (4 * SQUARE_SIZE) + indent * 2 + biggerTextFont.getSize();
		fm = getGraphics().getFontMetrics(biggerTextFont);
		space = fm.stringWidth(NEXT) + indent * 2;

		drawTextOnCanvas(canvas, NEXT, NEXT, biggerTextFont, x, y);
		drawTextOnCanvas(canvas, LINES, "", biggerTextFont, x + space, y);
		/* --- */

		/* Speed and Level */
		x = indent + boardCanvas.getWidth();
		y = (SQUARE_SIZE / 2) + (9 * SQUARE_SIZE) + digitalFontSize;
		fm = getGraphics().getFontMetrics(digitalFont);
		space = fm.stringWidth("18") + (SQUARE_SIZE / 4);

		drawTextOnCanvas(canvas, "18", speed, digitalFont, x, y);
		drawTextOnCanvas(canvas, "18", level, digitalFont, x + space, y);
		/* --- */

		/* Speed and Level labels */
		x = indent * 4 + boardCanvas.getWidth();
		y = (SQUARE_SIZE / 2) + (11 * SQUARE_SIZE) + textFontSize;
		fm = getGraphics().getFontMetrics(textFont);
		space = fm.stringWidth(SPEED) + indent * 2;

		drawTextOnCanvas(canvas, SPEED, SPEED, textFont, x, y);
		drawTextOnCanvas(canvas, LEVEL, LEVEL, textFont, x + space, y);
		/* --- */

		/* Rotate label */
		x = indent * 4 + boardCanvas.getWidth();
		y = (SQUARE_SIZE / 2) + (13 * SQUARE_SIZE) + textFontSize;

		drawTextOnCanvas(canvas, ROTATE, "", textFont, x, y);
		/* --- */

		/* Rotate icons (left/right) */
		fm = getGraphics().getFontMetrics(textFont);
		space = fm.stringWidth(ROTATE) + indent;

		if (iconFont != null) {
			drawTextOnCanvas(canvas, ICON_ROTATE_RIGTH, "", iconFont,
					x + space, y - indent);

			space = fm.stringWidth(ROTATE + ICON_ROTATE_RIGTH) + indent * 2 + 1;
			drawTextOnCanvas(canvas, ICON_ROTATE_LEFT, "", iconFont, x + space,
					y + indent);
		}
		/* --- */

		/* Pause label */
		x = indent * 4 + boardCanvas.getWidth();
		y = (SQUARE_SIZE / 2) + (15 * SQUARE_SIZE) + textFontSize;

		drawTextOnCanvas(canvas, PAUSE,
				((status == Status.Paused) ? PAUSE : ""), textFont, x, y);
		/* --- */

		/* Pause icon */
		fm = getGraphics().getFontMetrics(textFont);
		space = fm.stringWidth(PAUSE);

		if (iconFont != null)
			drawTextOnCanvas(canvas, ICON_PAUSE, ((showPauseIcon) ? ICON_PAUSE
					: ""), iconFont.deriveFont((float) digitalFontSize), x
					+ space, y + digitalFontSize);
		/* --- */

		/* Game Over label */
		x = indent * 4 + boardCanvas.getWidth();
		y = (SQUARE_SIZE / 2) + (18 * SQUARE_SIZE) + biggerTextFont.getSize();
		drawTextOnCanvas(canvas, GAME_OVER,
				((status == Status.GameOver) ? GAME_OVER : ""), biggerTextFont,
				x, y);
		/* --- */

		/* Coming Soon label */
		fm = getGraphics().getFontMetrics(digitalFont);
		space = fm.stringWidth(COMING_SOON);

		x = boardCanvas.getWidth() / 2 - space / 2;
		y = boardCanvas.getHeight() / 2;
		if (status == Status.ComingSoon) {
			drawTextOnCanvas(canvas, COMING_SOON, COMING_SOON, digitalFont, x,
					y);
		}
		/* --- */
	}

	/**
	 * Changes the {@code blinkColor} color from {@code fullColor} to
	 * {@code emptyColor} and vice versa
	 */
	public void blinkingSquares() {
		if (blinkColor.equals(fullColor))
			blinkColor = emptyColor;
		else
			blinkColor = fullColor;

		repaint();
	}

	/**
	 * Changes the {@code showPauseIcon} flag from {@code true} to {@code false}
	 * and vice versa, for blinking "Pause" icon
	 */
	public void blinkingPauseIcon() {
		if (status == Status.Paused)
			showPauseIcon = !showPauseIcon;
		else
			showPauseIcon = false;

		repaint();
	}

	/* Events */
	@Override
	public void boardChanged(GameEvent event) {
		setBoard(event.getBoard());
		repaint();
	}

	@Override
	public void previewChanged(GameEvent event) {
		setPreview(event.getBoard());
		repaint();
	}

	@Override
	public void statusChanged(GameEvent event) {
		status = event.getStatus();
	}

	@Override
	public void infoChanged(GameEvent event) {
		scores = event.getInfo();
	}

	@Override
	public void speedChanged(GameEvent event) {
		speed = String.valueOf(event.getSpeed());
	}

	@Override
	public void levelChanged(GameEvent event) {
		level = String.valueOf(event.getLevel());
	}

}
