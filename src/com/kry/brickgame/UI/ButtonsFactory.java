package com.kry.brickgame.UI;

import java.awt.Event;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonModel;
import javax.swing.JButton;

import com.kry.brickgame.games.GameConsts.KeyPressed;

/**
 * Factory class for the creating a game buttons
 * 
 * @author noLive
 */
public class ButtonsFactory {
	/**
	 * Mouse listener for the buttons
	 */
	private static ButtonMouseListener btnMouseListener = null;
	/**
	 * Map of the added buttons, assigned to the keys
	 */
	private static Map<KeyPressed, GameButton> buttons = null;

	/**
	 * Returns button, assigned to the specified key
	 * 
	 * @param key
	 *            action key
	 * @return button, assigned to the specified key
	 */
	public static GameButton getButton(KeyPressed key) {
		// creating only in first call
		if (null == btnMouseListener)
			btnMouseListener = new ButtonMouseListener();
		if (null == buttons)
			buttons = new HashMap<>();

		GameButton button = buttons.get(key);
		// if button not created yet
		if (null == button) {
			button = new GameButton(key);
			buttons.put(key, button);
		}

		return button;
	}

	public static class GameButton extends JButton {
		private static final long serialVersionUID = 575073369779220772L;
		/**
		 * Action key, with which the button is associated (read only)
		 */
		public final KeyPressed mappedKey;

		/**
		 * Create button, assigned to the specified key
		 * 
		 * @param key
		 *            action key
		 */
		public GameButton(KeyPressed key) {
			super();
			this.mappedKey = key;
			this.setActionCommand(key.toString());
			this.setFocusable(false);
			this.addMouseListener(btnMouseListener);
		}
	}

	/**
	 * Provides the effect of pressing the button when pressing the
	 * corresponding keyboard key
	 */
	public static class ButtonsKeyEventDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == Event.KEY_PRESS) {
				KeyPressed key = GameKeyAdapter.keycodeMap.get(e.getKeyCode());
				if (key != null && buttons != null) {
					// set pressed
					setPressed(buttons.get(key), true);
				}
			} else if (e.getID() == Event.KEY_RELEASE) {
				KeyPressed key = GameKeyAdapter.keycodeMap.get(e.getKeyCode());
				if (key != null && buttons != null) {
					// set unpressed
					setPressed(buttons.get(key), false);
				}
			}
			return false;
		}

		/**
		 * Sets the button to pressed or unpressed.
		 * 
		 * @param btn
		 *            button
		 * @param b
		 *            whether or not the button should be pressed
		 */
		private static void setPressed(JButton btn, boolean b) {
			if (btn != null) {
				ButtonModel btnMdl = btn.getModel();
				btnMdl.setArmed(b);
				btnMdl.setPressed(b);
			}
		}

	}
}
