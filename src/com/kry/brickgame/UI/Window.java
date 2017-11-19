package com.kry.brickgame.UI;

import static com.kry.brickgame.IO.SettingsManager.getSettingsManager;
import static com.kry.brickgame.UI.UIConsts.CONTROL_BTN_SIZE;
import static com.kry.brickgame.UI.UIConsts.DEVICE_ASPECT_RATIO;
import static com.kry.brickgame.UI.UIConsts.INSET_BOTTOM;
import static com.kry.brickgame.UI.UIConsts.INSET_LEFT;
import static com.kry.brickgame.UI.UIConsts.INSET_RIGHT;
import static com.kry.brickgame.UI.UIConsts.INSET_TOP;
import static com.kry.brickgame.UI.UIConsts.MENU_BTN_SIZE;
import static com.kry.brickgame.UI.UIConsts.MIN_HEIGHT;
import static com.kry.brickgame.UI.UIConsts.MIN_WIDTH;
import static com.kry.brickgame.UI.UIConsts.ROTATE_BTN_SIZE;
import static com.kry.brickgame.UI.UIConsts.START_BTN_SIZE;
import static com.kry.brickgame.UI.UIConsts.WINDOW_BTN_SIZE;
import static com.kry.brickgame.UI.UIUtils.getDimensionInPercents;
import static com.kry.brickgame.UI.UIUtils.getFormatedPercent;
import static com.kry.brickgame.UI.UIUtils.getInsetsInPercents;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

import com.ivan.xinput.XInputDevice;
import com.ivan.xinput.XInputDevice14;
import com.ivan.xinput.enums.XInputButton;
import com.ivan.xinput.exceptions.XInputNotLoadedException;
import com.kry.brickgame.Main;
import com.kry.brickgame.IO.GameLoader;
import com.kry.brickgame.games.Game;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.SplashScreen;

public class Window extends JFrame {

	private static final class XInputDevicePolling implements Runnable {
		/**
		 * Minimum delta value to avoid mistakes
		 */
		private final float MIN_VALUE = 0.1f;
		/**
		 * That axis value and above will be processed
		 */
		private final float GOAL_VALUE = 0.5f;

		private final XInputDevice device;
		private final XInputListener listener;

		XInputDevicePolling(final int playerNum, final XInputListener listener)
				throws XInputNotLoadedException {
			this.listener = listener;
			this.device = XInputDevice.getDeviceFor(playerNum);
			device.removeListener(listener);
			device.addListener(listener);
		}

		/**
		 * Checks the value of axis has changed
		 */
		private void fireAxisChanged() {
			if (device != null && listener != null) {
				float lx_delta = device.getDelta().getAxes().getLXDelta();
				float ly_delta = device.getDelta().getAxes().getLYDelta();
				float lx = device.getComponents().getAxes().lx;
				float ly = device.getComponents().getAxes().ly;
				float lx_last = device.getLastComponents().getAxes().lx;
				float ly_last = device.getLastComponents().getAxes().ly;

				// Left trigger X-axis
				if (lx >= GOAL_VALUE && lx_delta != 0f)
					listener.keyPressed(XInputButton.DPAD_RIGHT);
				if (lx <= -GOAL_VALUE && lx_delta != 0f)
					listener.keyPressed(XInputButton.DPAD_LEFT);
				if (lx_delta < -MIN_VALUE && lx_last <= -MIN_VALUE) {
					listener.keyReleased(XInputButton.DPAD_LEFT);
				} else if (lx_delta > MIN_VALUE && lx_last >= MIN_VALUE) {
					listener.keyReleased(XInputButton.DPAD_RIGHT);
				}

				// Left trigger Y-axis
				if (ly >= GOAL_VALUE && ly_delta != 0f)
					listener.keyPressed(XInputButton.DPAD_UP);
				if (ly <= -GOAL_VALUE && ly_delta != 0f)
					listener.keyPressed(XInputButton.DPAD_DOWN);
				if (ly_delta < -MIN_VALUE && ly_last <= -MIN_VALUE) {
					listener.keyReleased(XInputButton.DPAD_DOWN);
				} else if (ly_delta > MIN_VALUE && ly_last >= MIN_VALUE) {
					listener.keyReleased(XInputButton.DPAD_UP);
				}
			}
		}

		@Override
		public void run() {
			if (device == null) return;
			while (!Thread.currentThread().isInterrupted()) {
				try {
					if (device.poll()) fireAxisChanged();
					// decrease CPU usage
					Thread.sleep(30);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		}
	}

	private static final class XInputWaitToConnect implements Runnable {
		private final XInputListener xInputListener;

		/**
		 * Returns a player number for the first connected XInput device or {@code -1} if nothing connected
		 * @return the player number or {@code -1}
		 */
		private static int findFirstConnectedXInputDevice() {
			int firstConnectedDevice = -1;
			try {
				for (int i = 0; i < XInputDevice.getAllDevices().length; i++) {
					XInputDevice device = XInputDevice.getDeviceFor(i);
					device.poll();
					if (device.isConnected()) {
						firstConnectedDevice = i;
						break;
					}
				}
			} catch (XInputNotLoadedException e) {
				e.printStackTrace();
			}
			return firstConnectedDevice;
		}

		public XInputWaitToConnect() {
			xInputListener = new XInputListener();
		}

		@Override
		public void run() {
			int playerNum;
			while (!Thread.currentThread().isInterrupted()) {
				try {
					// decrease CPU usage
					Thread.sleep(500);
					
					playerNum = findFirstConnectedXInputDevice();
					if (playerNum >= 0) {
						setXInputListener(playerNum, xInputListener);
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		}

	}

	private static final class ComponentEventListener extends ComponentAdapter
			implements Serializable {
		private static final long serialVersionUID = 730763037448145667L;

		public ComponentEventListener() {
		}

		@Override
		public void componentResized(ComponentEvent e) {
			Component c = e.getComponent();

			// get size in compliance with DEVICE_ASPECT_RATIO
			Dimension size = UIUtils.getDimensionWithAspectRatio(c.getSize(),
					DEVICE_ASPECT_RATIO);

			// check the size not less than min values
			int width = Math.max(size.width, MIN_WIDTH);
			int height = Math.max(size.height, MIN_HEIGHT);

			// set new size if the current is not correct
			if (width != c.getSize().width || height != c.getSize().height) {
				c.setSize(width, height);
			}

		}
	}

	private static final class WindowEventListener extends WindowAdapter
			implements Serializable {
		private static final long serialVersionUID = -8159433562933801036L;

		public WindowEventListener() {
			super();
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// save current window size
			getSettingsManager().setSize(e.getWindow().getSize());
			// set game paused
			Game game = Main.getGame();
			if (game != null) game.pause();
			try {
				if (!getSettingsManager().getExitConfirmation()
						|| CloseOptionPane.show(e.getComponent()) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			} catch (Exception exp) {
				exp.printStackTrace();
				// exit from app in any case
				System.exit(0);
			}
		}

		@Override
		public void windowIconified(WindowEvent e) {
			Game game = Main.getGame();
			if (game != null) {
				game.pause();// set game paused
				game.saveState();// and save state
			}
		}
	}

	/**
	 * Gets the XInput device for the specified player and sets an event
	 * listener for it.
	 * 
	 * @param playerNum
	 *            the player number
	 * @param listener
	 *            the event listener
	 */
	static void setXInputListener(final int playerNum,
			final XInputListener listener) {
		try {
			xInputPolling.execute(new XInputDevicePolling(playerNum, listener));
		} catch (XInputNotLoadedException e) {
			System.err.println("The XInput native library failed to load");
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = 3466619047314091863L;

	private static ExecutorService xInputPolling;

	private final WindowListener windowListener = new WindowEventListener();
	private final ComponentListener componentListener = new ComponentEventListener();

	/**
	 * Create the application.
	 */
	public Window() {
		try {
			// set cross-platform LookAndFeel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);

		// try to load saved window size
		Dimension windowSize = getSettingsManager().getSize();
		if (windowSize == null) {
			// set initial size of window as two-thirds of the screen height
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int winHeight = screenSize.height * 2 / 3;
			int winWidth = Math.round(winHeight * DEVICE_ASPECT_RATIO);
			windowSize = new Dimension(winWidth, winHeight);
		}
		setSize(UIUtils.getDimensionWithAspectRatio(windowSize,
				DEVICE_ASPECT_RATIO));

		// place window in the center of the screen
		setLocationRelativeTo(null);

		final GameDrawPanel drawPanel = new GameDrawPanel();
		drawPanel.setLayout(new MigLayout(getInsetsInPercents(INSET_TOP,
				INSET_LEFT, INSET_BOTTOM, INSET_RIGHT) + ", nocache",
				"[][][][grow][]", "[][" + getFormatedPercent(210, MIN_HEIGHT)
						+ ":n,grow][][][][]"));

		/* -- Window buttons -- */
		if (getToolkit().isFrameStateSupported(ICONIFIED)) {
			drawPanel.add(
					ButtonsFactory.getMinimizeButton(),
					"cell 4 0, alignx right, aligny center, "
							+ getDimensionInPercents(WINDOW_BTN_SIZE,
									WINDOW_BTN_SIZE));
		}
		drawPanel.add(
				ButtonsFactory.getCloseButton(),
				"cell 4 0, alignx right, aligny center, "
						+ getDimensionInPercents(WINDOW_BTN_SIZE,
								WINDOW_BTN_SIZE));
		/* ---- */

		/* -- Control buttons -- */
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyUp),
				"cell 1 3, alignx center, aligny center, "
						+ getDimensionInPercents(CONTROL_BTN_SIZE,
								CONTROL_BTN_SIZE));
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyLeft),
				"cell 0 4, alignx center, aligny center, "
						+ getDimensionInPercents(CONTROL_BTN_SIZE,
								CONTROL_BTN_SIZE));
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyDown),
				"cell 1 5, alignx center, aligny center, "
						+ getDimensionInPercents(CONTROL_BTN_SIZE,
								CONTROL_BTN_SIZE));
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyRight),
				"cell 2 4, alignx center, aligny center, "
						+ getDimensionInPercents(CONTROL_BTN_SIZE,
								CONTROL_BTN_SIZE));
		/* ---- */

		/* Rotate button */
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyRotate),
				"cell 4 3 1 3, alignx right, aligny center, "
						+ getDimensionInPercents(ROTATE_BTN_SIZE,
								ROTATE_BTN_SIZE));

		/* Menu buttons */
		drawPanel.add(ButtonsFactory.getButton(KeyPressed.KeyShutdown),
				"cell 3 2, alignx center, aligny top, "
						+ getDimensionInPercents(MENU_BTN_SIZE, MENU_BTN_SIZE));
		drawPanel.add(ButtonsFactory.getButton(KeyPressed.KeyReset),
				"cell 3 2, alignx center, aligny top, "
						+ getDimensionInPercents(MENU_BTN_SIZE, MENU_BTN_SIZE));
		drawPanel.add(ButtonsFactory.getButton(KeyPressed.KeyMute),
				"cell 3 2, alignx center, aligny top, "
						+ getDimensionInPercents(MENU_BTN_SIZE, MENU_BTN_SIZE));
		/* ---- */

		/* Start button */
		drawPanel
				.add(ButtonsFactory.getButton(KeyPressed.KeyStart),
						"cell 3 3, alignx center, aligny bottom, "
								+ getDimensionInPercents(START_BTN_SIZE,
										START_BTN_SIZE));

		setContentPane(drawPanel);

		addWindowListener(windowListener);
		addComponentListener(componentListener);
		addKeyListener(Main.gameKeyAdapter);
		Game.addGameListener(drawPanel);
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(
						new ButtonsFactory.ButtonsKeyEventDispatcher());

		boolean xInputAvailability = XInputDevice14.isAvailable()
				|| XInputDevice.isAvailable();
		if (xInputAvailability) {
			System.out.println("XInput native library version: "
					+ XInputDevice.getLibraryVersion());
			xInputPolling = Executors.newSingleThreadExecutor();
			xInputPolling.execute(new XInputWaitToConnect());
		} else {
			System.err.println("XInput isn't available on this platform.");
		}

		setTitle("Brick Game");
		Game game = GameLoader.loadGame();
		if (game == null) {
			game = new SplashScreen();
		}
		// try to load saved properties
		Game.setMuted(getSettingsManager().getMuted());

		Main.setGameLoop(game);
	}
}
