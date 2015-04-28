package com.kry.brickgame.UI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import com.kry.brickgame.Main;
import com.kry.brickgame.games.Game;
import com.kry.brickgame.games.GameConsts.KeyPressed;

/**
 * @author noLive
 */
public class GameKeyAdapter extends KeyAdapter {
	public final static Map<Integer, KeyPressed> keycodeMap;
	static {
		keycodeMap = new LinkedHashMap<>();
		keycodeMap.put(KeyEvent.VK_LEFT, KeyPressed.KeyLeft);
		keycodeMap.put(KeyEvent.VK_RIGHT, KeyPressed.KeyRight);
		keycodeMap.put(KeyEvent.VK_DOWN, KeyPressed.KeyDown);
		keycodeMap.put(KeyEvent.VK_UP, KeyPressed.KeyUp);
		keycodeMap.put(KeyEvent.VK_SPACE, KeyPressed.KeyRotate);
		keycodeMap.put(KeyEvent.VK_ENTER, KeyPressed.KeyStart);
		keycodeMap.put(KeyEvent.VK_ESCAPE, KeyPressed.KeyShutdown);
		keycodeMap.put(KeyEvent.VK_P, KeyPressed.KeyStart);
		keycodeMap.put(KeyEvent.VK_R, KeyPressed.KeyReset);
		keycodeMap.put(KeyEvent.VK_M, KeyPressed.KeyMute);
		keycodeMap.put(KeyEvent.VK_W, KeyPressed.KeyUp);
		keycodeMap.put(KeyEvent.VK_A, KeyPressed.KeyLeft);
		keycodeMap.put(KeyEvent.VK_S, KeyPressed.KeyDown);
		keycodeMap.put(KeyEvent.VK_D, KeyPressed.KeyRight);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		Game game = Main.getGame();
		if (game != null && keycodeMap.containsKey(keycode)) {
			game.keyPressed(keycodeMap.get(keycode));
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int keycode = e.getKeyCode();
		Game game = Main.getGame();
		if (game != null && keycodeMap.containsKey(keycode)) {
			game.keyReleased(keycodeMap.get(keycode));
		}
	}
}
