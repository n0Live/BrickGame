package com.kry.brickgame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.kry.brickgame.Game.KeyPressed;

public class GameKeyAdapter extends KeyAdapter {

	public void keyPressed(KeyEvent e) {

		int keycode = e.getKeyCode();

		switch (keycode) {
		case KeyEvent.VK_LEFT:
			Main.getGame().keyPressed(KeyPressed.KeyLeft);
			break;
		case KeyEvent.VK_RIGHT:
			Main.getGame().keyPressed(KeyPressed.KeyRight);
			break;
		case KeyEvent.VK_DOWN:
			Main.getGame().keyPressed(KeyPressed.KeyDown);
			break;
		case KeyEvent.VK_UP:
			Main.getGame().keyPressed(KeyPressed.KeyUp);
			break;
		case KeyEvent.VK_SPACE:
			Main.getGame().keyPressed(KeyPressed.KeyRotate);
			break;
		case 'p':
			Main.getGame().keyPressed(KeyPressed.KeyStart);
			break;
		case 'P':
			Main.getGame().keyPressed(KeyPressed.KeyStart);
			break;
		case 'm':
			Main.getGame().keyPressed(KeyPressed.KeyReset);
			break;
		case 'M':
			Main.getGame().keyPressed(KeyPressed.KeyReset);
			break;
		}
	}

	public void keyReleased(KeyEvent e) {

		int keycode = e.getKeyCode();

		switch (keycode) {
		case KeyEvent.VK_LEFT:
			Main.getGame().keyReleased(KeyPressed.KeyLeft);
			break;
		case KeyEvent.VK_RIGHT:
			Main.getGame().keyReleased(KeyPressed.KeyRight);
			break;
		case KeyEvent.VK_DOWN:
			Main.getGame().keyReleased(KeyPressed.KeyDown);
			break;
		case KeyEvent.VK_UP:
			Main.getGame().keyReleased(KeyPressed.KeyUp);
			break;
		case KeyEvent.VK_SPACE:
			Main.getGame().keyReleased(KeyPressed.KeyRotate);
			break;
		case 'p':
			Main.getGame().keyReleased(KeyPressed.KeyStart);
			break;
		case 'P':
			Main.getGame().keyReleased(KeyPressed.KeyStart);
			break;
		case 'm':
			Main.getGame().keyReleased(KeyPressed.KeyReset);
			break;
		case 'M':
			Main.getGame().keyReleased(KeyPressed.KeyReset);
			break;
		}
	}
}
