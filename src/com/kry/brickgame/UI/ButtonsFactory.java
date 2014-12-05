package com.kry.brickgame.UI;

import static com.kry.brickgame.UI.UIConsts.lineNormaStroke;
import static com.kry.brickgame.UI.UIConsts.lineNormalColor;
import static com.kry.brickgame.UI.UIConsts.lineOverColor;
import static com.kry.brickgame.UI.UIConsts.lineOverStroke;

import java.awt.Event;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
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
	private static abstract class AbstractWindowButton extends JButton {
		private static final long serialVersionUID = 7175204924783660101L;

		protected final int gap = 2;
		protected boolean isMouseOver;

		private final MouseAdapter menuMouseAdapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				isMouseOver = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				isMouseOver = false;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				doWhenMouseReleased(e);
			}
		};

		public AbstractWindowButton() {
			super();
			setFocusable(false);
			setContentAreaFilled(false);
			setBorderPainted(false);

			isMouseOver = false;
			addMouseListener(menuMouseAdapter);
		}

		abstract void doWhenMouseReleased(MouseEvent e);
	}

	private static class ButtonClose extends AbstractWindowButton {
		private static final long serialVersionUID = -7518553794423312339L;

		@Override
		void doWhenMouseReleased(MouseEvent e) {
			// if mouse released upon the component
			if (e.getComponent().contains(e.getPoint())) {
				JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class,
						e.getComponent());
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// save previous graphical properties for the further restore
			Stroke oldStroke = g2d.getStroke();
			Paint oldPaint = g2d.getPaint();

			g2d.setColor(isMouseOver ? lineOverColor : lineNormalColor);
			g2d.setStroke(isMouseOver ? lineOverStroke : lineNormaStroke);

			int sideLength = Math.min(getWidth(), getHeight());

			g2d.drawLine(gap, gap, sideLength - gap, sideLength - gap);
			g2d.drawLine(gap, sideLength - gap, sideLength - gap, gap);

			// restore properties from the backup
			g2d.setStroke(oldStroke);
			g2d.setPaint(oldPaint);
			super.paintComponent(g2d);
		}

	}

	private static class ButtonMinimize extends AbstractWindowButton {
		private static final long serialVersionUID = -7518553794423312339L;

		@Override
		void doWhenMouseReleased(MouseEvent e) {
			// if mouse released upon the component
			if (e.getComponent().contains(e.getPoint())) {
				JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class,
						e.getComponent());
				frame.setState(Frame.ICONIFIED);
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// save previous graphical properties for the further restore
			Stroke oldStroke = g2d.getStroke();
			Paint oldPaint = g2d.getPaint();

			g2d.setColor(isMouseOver ? lineOverColor : lineNormalColor);
			g2d.setStroke(isMouseOver ? lineOverStroke : lineNormaStroke);

			int sideLength = Math.min(getWidth(), getHeight());

			g2d.drawLine(gap, sideLength - gap, sideLength - gap, sideLength - gap);

			// restore properties from the backup
			g2d.setStroke(oldStroke);
			g2d.setPaint(oldPaint);
			super.paintComponent(g2d);
		}
	}

	/**
	 * Provides the effect of pressing the button when pressing the
	 * corresponding keyboard key
	 */
	public static class ButtonsKeyEventDispatcher implements KeyEventDispatcher {
		/**
		 * Sets the button to pressed state.
		 * 
		 * @param btn
		 *            button
		 */
		private static void setPressed(JButton btn) {
			setPressed(btn, true);
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

		/**
		 * Sets the button to unpressed state.
		 * 
		 * @param btn
		 *            button
		 */
		private static void setUnpressed(JButton btn) {
			setPressed(btn, false);
		}

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == Event.KEY_PRESS) {
				KeyPressed key = GameKeyAdapter.keycodeMap.get(e.getKeyCode());
				if (key != null && buttons != null) {
					setPressed(buttons.get(key));
				}
			} else if (e.getID() == Event.KEY_RELEASE) {
				KeyPressed key = GameKeyAdapter.keycodeMap.get(e.getKeyCode());
				if (key != null && buttons != null) {
					setUnpressed(buttons.get(key));
				}
			}
			return false;
		}
	}

	public static class GameButton extends JButton {
		private static final long serialVersionUID = 575073369779220772L;
		/**
		 * Action key, with which the button is associated (read only)
		 */
		public final KeyPressed mappedKey;

		/**
		 * The button's default icon
		 */
		private final BufferedImage normal;
		/**
		 * The button's rollover (hover) icon
		 */
		private final BufferedImage hover;
		/**
		 * The button's pressed icon
		 */
		private final BufferedImage pressed;

		/**
		 * Changes icon size when changing the button size
		 */
		ComponentListener btnResizeListener = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setIcon(getScaledIcon(normal));
				setRolloverIcon(getScaledIcon(hover));
				setPressedIcon(getScaledIcon(pressed));
			}
		};

		/**
		 * Create button, assigned to the specified key
		 * 
		 * @param key
		 *            action key
		 */
		public GameButton(KeyPressed key) {
			super();
			mappedKey = key;

			setFocusable(false);
			setContentAreaFilled(false);
			setBorderPainted(false);
			setRolloverEnabled(true);

			setActionCommand(key.toString());

			normal = UIUtils.getImage("/images/btn_" + key.toString() + "_n.png");
			hover = UIUtils.getImage("/images/btn_" + key.toString() + "_h.png");
			pressed = UIUtils.getImage("/images/btn_" + key.toString() + "_p.png");

			addMouseListener(btnMouseListener);
			addComponentListener(btnResizeListener);

			setMinimumSize(getPreferredSize());
			setMaximumSize(getPreferredSize());
		}

		/**
		 * Returns a scaled version of specified image to fit in the size of the
		 * component
		 * 
		 * @param image
		 *            image in normal size
		 * @return scaled icon
		 */
		private ImageIcon getScaledIcon(BufferedImage image) {
			return new ImageIcon(image.getScaledInstance(getWidth(), getHeight(),
					Image.SCALE_SMOOTH));
		}
	}

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
		if (null == btnMouseListener) {
			btnMouseListener = new ButtonMouseListener();
		}
		if (null == buttons) {
			buttons = new HashMap<>();
		}

		GameButton button = buttons.get(key);
		// if button not created yet
		if (null == button) {
			button = new GameButton(key);
			buttons.put(key, button);
		}

		return button;
	}

	/**
	 * Gets standard-looking close button
	 * 
	 * @return a close button
	 */
	public static JButton getCloseButton() {
		return new ButtonClose();
	}

	/**
	 * Gets standard-looking minimize button
	 * 
	 * @return a minimize button
	 */
	public static JButton getMinimizeButton() {
		return new ButtonMinimize();
	}

}
