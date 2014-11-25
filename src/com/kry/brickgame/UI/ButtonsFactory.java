package com.kry.brickgame.UI;

import java.awt.Event;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonModel;
import javax.swing.JButton;

import com.kry.brickgame.games.GameConsts.KeyPressed;

public class ButtonsFactory {
	private static ButtonMouseListener btnMouseListener = null;
	private static Map<KeyPressed, GameButton> buttons = null;

	public static GameButton getButton(KeyPressed key) {
		if (null == btnMouseListener)
			btnMouseListener = new ButtonMouseListener();
		if (null == buttons)
			buttons = new HashMap<>();

		GameButton button = buttons.get(key);

		if (null == button) {
			button = new GameButton(key);
			buttons.put(key, button);
		}

		return button;
	}

	public static class GameButton extends JButton {
		private static final long serialVersionUID = 575073369779220772L;
		
		public final KeyPressed mappedKey;

		public GameButton(KeyPressed key) {
			super();
			this.mappedKey = key;
			this.setActionCommand(key.toString());
			this.setFocusable(false);
			this.addMouseListener(btnMouseListener);
		}
	}

	public static class ButtonsKeyEventDispatcher implements KeyEventDispatcher {

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			GameButton btn;
			ButtonModel btnMdl;
			if (e.getID() == Event.KEY_PRESS) {
				KeyPressed key = GameKeyAdapter.keycodeMap.get(e.getKeyCode());
				if (key != null && buttons != null) {
					btn = buttons.get(key);
					if (btn != null) {
						btnMdl = btn.getModel();
						btnMdl.setArmed(true);
						btnMdl.setPressed(true);
					}
				}
			} else if (e.getID() == Event.KEY_RELEASE) {
				KeyPressed key = GameKeyAdapter.keycodeMap.get(e.getKeyCode());
				if (key != null && buttons != null) {
					btn = buttons.get(key);
					if (btn != null) {
						btnMdl = btn.getModel();
						btnMdl.setPressed(false);
						btnMdl.setArmed(false);
					}
				}
			}
			return false;
		}

	}
}
