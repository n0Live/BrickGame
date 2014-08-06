package com.kry.brickgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.logging.Logger;

import javax.swing.JPanel;

import com.kry.brickgame.Board.Cells;

public class Draw extends JPanel implements IGameListener {

	private static final long serialVersionUID = 1043017116324502441L;

	private Board board;

	protected Board getBoard() {
		return board;
	}

	protected void setBoard(Board board) {
		this.board = board;
	}

	private int width;
	private int height;

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

	public void paint(Graphics g) {
		super.paint(g);

		try {
			Dimension size = getSize();
			int boardTop = (int) size.getHeight() - height * squareHeight();

			for (int i = 0; i < height; ++i) {
				for (int j = 0; j < width; ++j) {
					Cells fill = board.getCell(j, height - i - 1);
					drawSquare(g, 0 + j * squareWidth(), boardTop + i
							* squareHeight(), fill);
				}
			}
		} catch (Exception e) {
			Logger.getAnonymousLogger().info(e.getLocalizedMessage());
		}
	}

	private void drawSquare(Graphics g, int x, int y, Cells fill) {
		Color colors[] = { new Color(220, 220, 220, 30), new Color(40, 40, 40, 255) };

		Color color = colors[fill.ordinal()];

		g.setColor(color);
		g.drawRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
		g.drawRect(x + 2, y + 2, squareWidth() - 4, squareHeight() - 2);
		g.fillRect(x + 1 + (squareWidth() / 4), y + 1 + (squareHeight() / 4),
				squareWidth() / 2, squareHeight() / 2);

	}

	@Override
	public void boardChanged(GameEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void previewChanged(GameEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void statusChanged(GameEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infoChanged(GameEvent event) {
		// TODO Auto-generated method stub

	}
}
