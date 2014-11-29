package com.kry.brickgame.UI;

import static com.kry.brickgame.UI.UIConsts.lineNormaStroke;
import static com.kry.brickgame.UI.UIConsts.lineNormalColor;
import static com.kry.brickgame.UI.UIConsts.lineOverColor;
import static com.kry.brickgame.UI.UIConsts.lineOverStroke;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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

	public static JButton getCloseButton() {
		return new ButtonClose();
	}

	public static JButton getMinimizeButton() {
		return new ButtonMinimize();
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
			setFocusable(false);
			setContentAreaFilled(false);
			setActionCommand(key.toString());
			addMouseListener(btnMouseListener);
		}
	}

	private static class ButtonClose extends AbstractMenuButton {
		private static final long serialVersionUID = -7518553794423312339L;

		public ButtonClose() {
			super();
		}

		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			// save previous graphical properties for the further restore
			Stroke oldStroke = g2d.getStroke();
			Paint oldPaint = g2d.getPaint();

			g2d.setColor(isMouseOver ? lineOverColor : lineNormalColor);
			g2d.setStroke(isMouseOver ? lineOverStroke : lineNormaStroke);
			
			g2d.drawLine(gap, gap, size.width, size.height);
			g2d.drawLine(gap, size.height, size.width, gap);

			// restore properties from the backup
			g2d.setStroke(oldStroke);
			g2d.setPaint(oldPaint);
			super.paintComponent(g2d);
		}

		@Override
		void doWhenMouseReleased(MouseEvent e) {
			JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(
					JFrame.class, e.getComponent());
			frame.dispatchEvent(new WindowEvent(frame,
					WindowEvent.WINDOW_CLOSING));
		}

	}
	
	private static class ButtonMinimize extends AbstractMenuButton {
		private static final long serialVersionUID = -7518553794423312339L;

		public ButtonMinimize() {
			super();
		}

		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			// save previous graphical properties for the further restore
			Stroke oldStroke = g2d.getStroke();
			Paint oldPaint = g2d.getPaint();
			
			g2d.setColor(isMouseOver ? lineOverColor : lineNormalColor);
			g2d.setStroke(isMouseOver ? lineOverStroke : lineNormaStroke);

			g2d.drawLine(gap, size.height, size.width, size.height);

			// restore properties from the backup
			g2d.setStroke(oldStroke);
			g2d.setPaint(oldPaint);
			super.paintComponent(g2d);
		}

		@Override
		void doWhenMouseReleased(MouseEvent e) {
			JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(
					JFrame.class, e.getComponent());
			frame.setState(Frame.ICONIFIED);
		}

	}

	public static abstract class AbstractMenuButton extends
	JButton {
		protected boolean isMouseOver;

		protected final Dimension size = new Dimension(10, 10);
		protected final int gap = 2;

		public AbstractMenuButton() {
			super();
			setFocusable(false);
			setContentAreaFilled(false);
			setBorderPainted(false);
			
			Dimension sizeWithGap = new Dimension(size.width + gap*2, size.height + gap*2);
			
			setMinimumSize(sizeWithGap);
			setPreferredSize(sizeWithGap);
			setMaximumSize(sizeWithGap);

			isMouseOver = false;
			addMouseListener(menuMouseAdapter);
		}

		private MouseAdapter menuMouseAdapter = new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				isMouseOver = true;
				repaint();
			}

			public void mouseExited(MouseEvent e) {
				isMouseOver = false;
				repaint();
			}

			public void mouseReleased(MouseEvent e) {
				doWhenMouseReleased(e);
			}
		};

		abstract void doWhenMouseReleased(MouseEvent e);
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
