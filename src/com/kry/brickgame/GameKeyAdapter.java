package com.kry.brickgame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import com.kry.brickgame.games.GameConsts.KeyPressed;

/**
 * @author noLive
 * 
 */
public class GameKeyAdapter extends KeyAdapter {
	private final static Map<Integer, KeyPressed> keycodeMap;
	static {
		keycodeMap = new HashMap<>();
		keycodeMap.put(KeyEvent.VK_LEFT, KeyPressed.KeyLeft);
		keycodeMap.put(KeyEvent.VK_RIGHT, KeyPressed.KeyRight);
		keycodeMap.put(KeyEvent.VK_DOWN, KeyPressed.KeyDown);
		keycodeMap.put(KeyEvent.VK_UP, KeyPressed.KeyUp);
		keycodeMap.put(KeyEvent.VK_SPACE, KeyPressed.KeyRotate);
		keycodeMap.put(KeyEvent.VK_ENTER, KeyPressed.KeyStart);
		keycodeMap.put(KeyEvent.VK_P, KeyPressed.KeyStart);
		keycodeMap.put(KeyEvent.VK_R, KeyPressed.KeyReset);
		keycodeMap.put(KeyEvent.VK_M, KeyPressed.KeyMute);
		keycodeMap.put(KeyEvent.VK_ESCAPE, KeyPressed.KeyOnOff);
	}

	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		if (keycodeMap.containsKey(keycode))
			CommandAdapter.keyPressed(keycodeMap.get(keycode));
	}

	public void keyReleased(KeyEvent e) {
		int keycode = e.getKeyCode();
		if (keycodeMap.containsKey(keycode))
			CommandAdapter.keyReleased(keycodeMap.get(keycode));
	}
}
