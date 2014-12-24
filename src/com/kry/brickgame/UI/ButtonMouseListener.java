package com.kry.brickgame.UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
	
	private static class Repeater implements Runnable {
		private final KeyPressed key;
		
		public Repeater(KeyPressed key) {
			this.key = key;
		}
		
		@Override
		public void run() {
			Main.getGame().keyPressed(key);
		}
	}
	
	/**
	 * Menu buttons (Shutdown, Reset, Mute, Start)
	 */
	private static final List<KeyPressed> menuKeys = Collections.unmodifiableList(Arrays.asList(
	        KeyPressed.KeyShutdown, KeyPressed.KeyReset, KeyPressed.KeyMute, KeyPressed.KeyStart));
	
	/**
	 * Threads for processing of pressed keys
	 */
	private final ScheduledExecutorService scheduledThreadPool = Executors
	        .newScheduledThreadPool(5);
	
	/**
	 * Pressed keys
	 */
	private final Map<KeyPressed, ScheduledFuture<?>> pressedKeys;
	
	public ButtonMouseListener() {
		pressedKeys = new HashMap<>();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		final JButton btn = (JButton) e.getSource();
		KeyPressed key = KeyPressed.valueOf(btn.getActionCommand());
		
		if (menuKeys.contains(key)) {
			// special behavior for KeyShutdown
			if (key != KeyPressed.KeyShutdown) {
				Main.getGame().keyPressed(key);
			}
		} else {
			// process the pressing buttons as well as pressing a key
			ScheduledFuture<?> pressedKey = scheduledThreadPool.scheduleAtFixedRate(new Repeater(
			        key), 300, 40, TimeUnit.MILLISECONDS);
			pressedKeys.put(key, pressedKey);
			
			Main.getGame().keyPressed(key);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		KeyPressed key = KeyPressed.valueOf(btn.getActionCommand());
		
		if (menuKeys.contains(key)) {
			// special behavior for KeyShutdown (when mouse released upon the
			// pressed KeyShutdown button)
			if (key == KeyPressed.KeyShutdown && e.getComponent().contains(e.getPoint())) {
				// send closing event to parent frame
				JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class,
				        e.getComponent());
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			} else {
				Main.getGame().keyReleased(key);
			}
		} else {
			pressedKeys.get(key).cancel(true);
			pressedKeys.remove(key);
			
			Main.getGame().keyReleased(key);
		}
	}
}