package com.kry.brickgame.UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;

import com.kry.brickgame.Main;
import com.kry.brickgame.games.GameConsts.KeyPressed;

/**
 * MouseListener for game buttons
 * <p>
 * Provides the processing of the press buttons as well as pressing a keyboard
 * keys
 * 
 * @author noLive
 */
class ButtonMouseListener extends MouseAdapter {
	/**
	 * Threads for processing of pressed keys
	 */
	private ScheduledExecutorService scheduledThreadPool = Executors
			.newScheduledThreadPool(5);
	/**
	 * Pressed keys
	 */
	private Map<KeyPressed, ScheduledFuture<?>> pressedKeys;

	public ButtonMouseListener() {
		pressedKeys = new HashMap<>();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		final JButton btn = (JButton) e.getSource();
		KeyPressed key = KeyPressed.valueOf(btn.getActionCommand());

		// process the pressing buttons as well as pressing a key
		ScheduledFuture<?> pressedKey = scheduledThreadPool
				.scheduleAtFixedRate(new Repeater(key), 300, 40,
						TimeUnit.MILLISECONDS);
		pressedKeys.put(key, pressedKey);

		Main.getGame().keyPressed(key);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		KeyPressed key = KeyPressed.valueOf(btn.getActionCommand());

		pressedKeys.get(key).cancel(true);
		pressedKeys.remove(key);

		Main.getGame().keyReleased(key);
	}

	private class Repeater implements Runnable {
		private KeyPressed key;

		public Repeater(KeyPressed key) {
			this.key = key;
		}

		@Override
		public void run() {
			Main.getGame().keyPressed(key);
		}

	}
}