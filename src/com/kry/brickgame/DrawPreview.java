package com.kry.brickgame;

import java.awt.Graphics;

public class DrawPreview extends Draw implements IGameListener{

	private static final long serialVersionUID = 505936396946023720L;

	public DrawPreview() {
		super(Game.PREVIEW_WIDTH, Game.PREVIEW_HEIGHT);
	}
	
	public void paint(Graphics g) {
		super.paint(g);

	}

	@Override
	public void previewChanged(GameEvent event) {
		setBoard(event.getBoard());
		repaint();
	}

}
