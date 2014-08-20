package com.kry.brickgame;

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

import javax.swing.JPanel;

import com.kry.brickgame.Board.Cells;
import com.kry.brickgame.Game.Status;

public class Draw extends JPanel implements GameListener {

	private static final long serialVersionUID = 1043017116324502441L;

	private static final int SQUARE_SIZE = 26;

	private static final String HI = "HI";
	private static final String SCORE = "SCORE";
	private static final String NEXT = "NEXT";
	private static final String LINES = "LINES";
	private static final String SPEED = "SPEED";
	private static final String LEVEL = "LEVEL";
	private static final String ROTATE = "ROTATE";
	private static final String PAUSE = "PAUSE";
	private static final String GAME_OVER = "GAME OVER";

	private Font digital;
	private Font textFont;

	private Board board = null;
	private Board preview = null;

	private final Color emptyColor = new Color(40, 40, 40, 40);
	private final Color fullColor = new Color(40, 40, 40, 255);
	private Color blinkColor = fullColor;
	private Color bgColor;

	private String dScores = "0";
	private String dSpeed = "1";
	private String dLevel = "1";

	private Status status;

	private FontMetrics fm;

	private final float digitalFontSize = (float) 46;
	private final int textFontSize = 20;

	private Dimension size = null;

	private BufferedImage canvas = null;
	private BufferedImage boardCanvas = null;
	private BufferedImage previewCanvas = null;

	public Draw() {
		super();

		try {
			digital = Font.createFont(
					Font.TRUETYPE_FONT,
					getClass().getResourceAsStream(
							"/fonts/Segment7Standard.otf")).deriveFont(
					digitalFontSize);

		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		textFont = new Font(Font.SANS_SERIF, Font.PLAIN, textFontSize);
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

	int boardWidthInSquares(Board board) {
		return board.getWidth() * SQUARE_SIZE;
	}

	int boardHeightInSquares(Board board) {
		return (board.getHeight() - board.getUnshowedLines()) * SQUARE_SIZE;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		size = getSize();
		bgColor = getBackground();

		if ((canvas == null) || (canvas.getWidth() != size.width + 2)
				|| (canvas.getHeight() != size.height + 2)) {
			canvas = initCanvas(size.width, size.height);
		}

		clearCanvas(canvas, bgColor);

		updateCanvas(boardCanvas, board);
		updateCanvas(previewCanvas, preview);
		canvasSetBorder(boardCanvas, board, fullColor);

		appendCanvas(canvas, boardCanvas, 0, 0);
		appendCanvas(canvas, previewCanvas, boardCanvas.getWidth()
				+ SQUARE_SIZE, 5 * SQUARE_SIZE);

		drawScoresAndStatus();

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.drawRenderedImage(canvas, null);
	}

	private BufferedImage initCanvas(int width, int height) {
		return new BufferedImage(width + 2, height + 2,
				BufferedImage.TYPE_INT_ARGB);
	}

	private BufferedImage initCanvas(Board board) {
		if (board == null)
			return null;

		return initCanvas(boardWidthInSquares(board),
				boardHeightInSquares(board));
	}

	protected void clearCanvas(BufferedImage canvas, Color bgColor) {
		if (canvas == null)
			return;

		Graphics2D g2d = canvas.createGraphics();
		g2d.setBackground(bgColor);
		g2d.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		g2d.dispose();
	}

	protected void canvasSetBorder(BufferedImage canvas, Board board,
			Color color) {
		if (canvas == null)
			return;

		Graphics2D g2d = canvas.createGraphics();
		g2d.setColor(fullColor);
		g2d.drawRect(0, 0, canvas.getWidth() - 1, canvas.getHeight() - 1);
		g2d.dispose();
	}

	protected void paintBoardCanvas(BufferedImage canvas, Board board) {
		if ((canvas == null) || (board == null))
			return;

		int boardWidth = board.getWidth();
		int boardHeight = board.getHeight() - board.getUnshowedLines();

		Graphics2D g2d = canvas.createGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < boardHeight; ++i) {
			for (int j = 0; j < boardWidth; ++j) {
				Cells fill = board.getCell(j, boardHeight - i - 1);
				drawSquare(g2d, j * SQUARE_SIZE + 1, i * SQUARE_SIZE + 1, fill);
			}
		}

		g2d.dispose();
	}

	private void drawSquare(Graphics g, int x, int y, Cells fill) {
		Color colors[] = { emptyColor, fullColor, blinkColor };

		Color color = colors[fill.ordinal()];

		g.setColor(color);
		g.drawRect(x + 1, y + 1, SQUARE_SIZE - 2, SQUARE_SIZE - 2);
		g.drawRect(x + 2, y + 2, SQUARE_SIZE - 4, SQUARE_SIZE - 2);
		g.fillRect(x + 1 + (SQUARE_SIZE / 4), y + 1 + (SQUARE_SIZE / 4),
				SQUARE_SIZE / 2, SQUARE_SIZE / 2);

	}

	protected void appendCanvas(BufferedImage toCanvas,
			BufferedImage fromCanvas, int x, int y) {
		if ((toCanvas == null) || (fromCanvas == null))
			return;

		Graphics2D g2d = toCanvas.createGraphics();

		g2d.drawImage(fromCanvas, x, y, fromCanvas.getWidth(),
				fromCanvas.getHeight(), null);

		g2d.dispose();
	}

	protected void drawTextOnCanvas(BufferedImage canvas, String init,
			String digits, Font font, int x, int y) {
		if (canvas == null)
			return;

		Graphics2D g2d = canvas.createGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setFont(font);

		fm = g2d.getFontMetrics();

		g2d.setBackground(bgColor);
		g2d.clearRect(x, y - font.getSize(), fm.stringWidth(init),
				font.getSize());

		StringBuilder formatString = new StringBuilder("%")
				.append(init.length()).append(".").append(init.length())
				.append("s");

		g2d.setColor(emptyColor);
		g2d.drawString(String.format(formatString.toString(), init), x, y);

		g2d.setColor(fullColor);
		g2d.drawString(String.format(formatString.toString(), digits), x, y);

		g2d.dispose();

	}

	protected void drawScoresAndStatus() {
		if ((canvas == null) || (dScores == null))
			return;

		int x = boardCanvas.getWidth() + 2;
		int y = (int) digitalFontSize;
		drawTextOnCanvas(canvas, "18888", dScores, digital, x, y);

		x += 12;
		y += 10 + textFontSize - 2;
		drawTextOnCanvas(canvas, HI, "", textFont, x, y);
		drawTextOnCanvas(canvas, SCORE, SCORE, textFont,
				x + 2 + fm.stringWidth(HI), y);

		y += 16 + textFontSize + 6;
		drawTextOnCanvas(canvas, NEXT, NEXT,
				textFont.deriveFont((float) textFont.getSize() + 4), x - 6, y);
		drawTextOnCanvas(canvas, LINES, "",
				textFont.deriveFont((float) textFont.getSize() + 4),
				x + fm.stringWidth(NEXT), y);

		x += 10;
		y += 10 + 5 * SQUARE_SIZE + digitalFontSize;
		drawTextOnCanvas(canvas, "18", dSpeed, digital, x, y);
		drawTextOnCanvas(canvas, "18", dLevel, digital,
				x + 6 + fm.stringWidth("18"), y);

		x -= 12;
		y += 10 + textFontSize;
		drawTextOnCanvas(canvas, SPEED, SPEED, textFont, x, y);
		drawTextOnCanvas(canvas, LEVEL, LEVEL, textFont,
				x + 6 + fm.stringWidth(SPEED), y);

		y += 20 + textFontSize;
		drawTextOnCanvas(canvas, ROTATE, "", textFont, x, y);

		y += 30 + textFontSize;
		drawTextOnCanvas(canvas, PAUSE,
				((status == Status.Paused) ? PAUSE : ""), textFont, x, y);

		y += 50 + textFontSize;
		drawTextOnCanvas(canvas, GAME_OVER,
				((status == Status.GameOver) ? GAME_OVER : ""), textFont, x, y);
	}

	protected void updateCanvas(BufferedImage canvas, Board board) {
		if (canvas == null)
			return;

		clearCanvas(canvas, bgColor);
		paintBoardCanvas(canvas, board);
	}

	public void blinking() {
		if (blinkColor.equals(fullColor)) {
			blinkColor = emptyColor;
		} else {
			blinkColor = fullColor;
		}

		repaint();
	}

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
		dScores = event.getInfo();
	}

}
