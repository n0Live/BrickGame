package com.kry.brickgame;

import java.awt.Graphics;

import javax.swing.JLabel;

public class DrawBoard extends Draw implements IGameListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel statusbar;
	private String info = "0";

	public DrawBoard(Window parent) {
		super(Game.BOARD_WIDTH, Game.BOARD_HEIGHT - Game.UNSHOWED_LINES);

		setFocusable(true);

		statusbar = parent.getStatusBar();
	}

	public void paint(Graphics g) {
		super.paint(g);

	}

	@Override
	public void boardChanged(GameEvent event) {
		setBoard(event.getBoard());
		repaint();
	}

	@Override
	public void statusChanged(GameEvent event) {
		switch (event.getStatus()) {
		case Paused:
			statusbar.setText("Pause");
			break;
		case GameOver:
			statusbar.setText("Game Over");
			break;
		default:
			statusbar.setText(info);
			break;
		}
	}

	@Override
	public void infoChanged(GameEvent event) {
		info = event.getInfo();
		statusbar.setText(info);
	}

}
