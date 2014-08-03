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
					Cells fill = board.getCell(j,height - i - 1);
					drawSquare(g, 0 + j * squareWidth(), boardTop + i
							* squareHeight(), fill);
				}
			}
		} catch (Exception e) {
			Logger.getAnonymousLogger().info(e.getLocalizedMessage());
		}
	}

	private void drawSquare(Graphics g, int x, int y, Cells fill) {
		Color colors[] = { new Color(80, 80, 80), new Color(204, 102, 102),
				new Color(102, 204, 102), new Color(102, 102, 204),
				new Color(204, 204, 102), new Color(204, 102, 204),
				new Color(102, 204, 204), new Color(218, 170, 0) };

		Color color = colors[fill.ordinal()];

		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);

		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y
				+ squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x
				+ squareWidth() - 1, y + 1);

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
