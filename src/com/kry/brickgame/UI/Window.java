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

import java.awt.BorderLayout;
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

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

import com.kry.brickgame.Main;
import com.kry.brickgame.IO.GameLoader;
import com.kry.brickgame.games.Game;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.SplashScreen;

public class Window extends JFrame {
	
	transient WindowListener windowListener = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			// save current window size
			getSettingsManager().setSize(e.getWindow().getSize());
			// set game paused
			Main.getGame().pause();
			try {
				if (!getSettingsManager().getExitConfirmation()
						|| CloseOptionPane.show(e.getComponent()) == JOptionPane.YES_OPTION) {
					Main.getGame().keyPressed(KeyPressed.KeyShutdown);
				}
			} catch (Exception exp) {
				exp.printStackTrace();
				// exit from app in any case
				System.exit(0);
			}
		}
		
		@Override
		public void windowIconified(WindowEvent e) {
			// set game paused
			Main.getGame().pause();
			// and save state
			Main.getGame().saveState();
		}
	};
	
	transient ComponentListener componentListener = new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			Component c = e.getComponent();
			
			// get size in compliance with DEVICE_ASPECT_RATIO
			Dimension size = UIUtils.getDimensionWithAspectRatio(c.getSize(), DEVICE_ASPECT_RATIO);
			
			// check the size not less than min values
			int width = Math.max(size.width, MIN_WIDTH);
			int height = Math.max(size.height, MIN_HEIGHT);
			
			// set new size if the current is not correct
			if (width != c.getSize().width || height != c.getSize().height) {
				setSize(width, height);
			}
		}
	};
	
	private static final long serialVersionUID = 3466619047314091863L;
	
	/**
	 * Create the application.
	 */
	public Window() {
		try {
			// set cross-platform LookAndFeel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		getSettingsManager().loadProperties();
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
			// set initial size of window as half of screen height
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			
			int winHeight = screenSize.height / 2;
			int winWidth = Math.round(winHeight * DEVICE_ASPECT_RATIO);
			windowSize = new Dimension(winWidth, winHeight);
		}
		
		setSize(UIUtils.getDimensionWithAspectRatio(windowSize, DEVICE_ASPECT_RATIO));
		
		// place window in the center of the screen
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		addWindowListener(windowListener);
		
		final GameDrawPanel drawPanel = new GameDrawPanel();
		
		drawPanel.setLayout(new MigLayout(getInsetsInPercents(INSET_TOP, INSET_LEFT, INSET_BOTTOM,
				INSET_RIGHT) + ", nocache", "[][][][grow][]", "[]["
				+ getFormatedPercent(210, MIN_HEIGHT) + ":n,grow][][][][]"));
		
		/* -- Window buttons -- */
		if (getToolkit().isFrameStateSupported(ICONIFIED)) {
			drawPanel.add(
					ButtonsFactory.getMinimizeButton(),
					"cell 4 0, alignx right, aligny center, "
							+ getDimensionInPercents(WINDOW_BTN_SIZE, WINDOW_BTN_SIZE));
		}
		drawPanel.add(ButtonsFactory.getCloseButton(), "cell 4 0, alignx right, aligny center, "
				+ getDimensionInPercents(WINDOW_BTN_SIZE, WINDOW_BTN_SIZE));
		/* ---- */
		
		/* -- Control buttons -- */
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyUp),
				"cell 1 3, alignx center, aligny center, "
						+ getDimensionInPercents(CONTROL_BTN_SIZE, CONTROL_BTN_SIZE));
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyLeft),
				"cell 0 4, alignx center, aligny center, "
						+ getDimensionInPercents(CONTROL_BTN_SIZE, CONTROL_BTN_SIZE));
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyDown),
				"cell 1 5, alignx center, aligny center, "
						+ getDimensionInPercents(CONTROL_BTN_SIZE, CONTROL_BTN_SIZE));
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyRight),
				"cell 2 4, alignx center, aligny center, "
						+ getDimensionInPercents(CONTROL_BTN_SIZE, CONTROL_BTN_SIZE));
		/* ---- */
		
		/* Rotate button */
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyRotate),
				"cell 4 3 1 3, alignx right, aligny center, "
						+ getDimensionInPercents(ROTATE_BTN_SIZE, ROTATE_BTN_SIZE));
		
		/* Menu buttons */
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyShutdown),
				"cell 3 2, alignx center, aligny bottom, "
						+ getDimensionInPercents(MENU_BTN_SIZE, MENU_BTN_SIZE));
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyReset),
				"cell 3 2, alignx center, aligny bottom, "
						+ getDimensionInPercents(MENU_BTN_SIZE, MENU_BTN_SIZE));
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyMute),
				"cell 3 2, alignx center, aligny bottom, "
						+ getDimensionInPercents(MENU_BTN_SIZE, MENU_BTN_SIZE));
		/* ---- */
		
		/* Start button */
		drawPanel.add(
				ButtonsFactory.getButton(KeyPressed.KeyStart),
				"cell 3 3, alignx center, aligny bottom, "
						+ getDimensionInPercents(START_BTN_SIZE, START_BTN_SIZE));
		
		setContentPane(drawPanel);
		
		addComponentListener(componentListener);
		addKeyListener(Main.gameKeyAdapter);
		Game.addGameListener(drawPanel);
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
				new ButtonsFactory.ButtonsKeyEventDispatcher());
		
		setTitle("Brick Game");
		Game game = GameLoader.loadGame();
		if (game == null) {
			game = new SplashScreen();
		}
		// try to load saved properties
		Game.setMuted(getSettingsManager().getMuted());
		Main.gameSelector.setRotation(getSettingsManager().getRotation());
		
		Main.setGame(game);
	}
}
