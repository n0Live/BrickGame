package com.kry.brickgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.kry.brickgame.Board.Cells;

public class Draw extends JPanel implements GameListener {

	private static final long serialVersionUID = 1043017116324502441L;

	private Board board = null;

	private Color emptyColor = new Color(220, 220, 220, 30);
	private Color fullColor = new Color(40, 40, 40, 255);
	private Color blinkColor = fullColor;

	protected Board getBoard() {
		return board;
	}

	protected void setBoard(Board board) {
		this.board = board;
	}

	private int width;
	private int height;
	private Dimension size = null;

	private BufferedImage canvas = null;

	public Draw(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	int squareWidth() {
		return (int) getSize().getWidth() / width;
	}

	int squareHeight() {
		return (int) getSize().getHeight() / height;
	}

	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		size = getSize();
		int windowWidth = size.width;
		int windowHeight = size.height;

		if ((canvas == null) || (canvas.getWidth() != windowWidth)
				|| (canvas.getHeight() != windowHeight)) {
			initCanvas(windowWidth, windowHeight);
		}
		
		canvasSetBackground(getBackground());
		paintCanvas();

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.drawRenderedImage(canvas, null);
	}

	private void initCanvas(int width, int height) {
		canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	protected void canvasSetBackground(Color bgColor) {
		if (canvas == null)
			return;
		Graphics2D g2d = canvas.createGraphics();
		g2d.setBackground(bgColor);
		g2d.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		g2d.dispose();
	}

	protected void paintCanvas() {
		if ((canvas == null) || (board == null))
			return;

		int boardTop = canvas.getHeight() - height * squareHeight();

		Graphics2D g2d = canvas.createGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				Cells fill = board.getCell(j, height - i - 1);
				drawSquare(g2d, 0 + j * squareWidth(), boardTop + i
						* squareHeight(), fill);
			}
		}
		g2d.dispose();
	}

	private void drawSquare(Graphics g, int x, int y, Cells fill) {
		Color colors[] = { emptyColor, fullColor, blinkColor };

		Color color = colors[fill.ordinal()];

		g.setColor(color);
		g.drawRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
		g.drawRect(x + 2, y + 2, squareWidth() - 4, squareHeight() - 2);
		g.fillRect(x + 1 + (squareWidth() / 4), y + 1 + (squareHeight() / 4),
				squareWidth() / 2, squareHeight() / 2);

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
	}

	@Override
	public void previewChanged(GameEvent event) {
	}

	@Override
	public void statusChanged(GameEvent event) {
	}

	@Override
	public void infoChanged(GameEvent event) {
	}

}
