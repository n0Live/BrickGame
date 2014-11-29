package com.kry.brickgame.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

import com.kry.brickgame.GameLoader;
import com.kry.brickgame.Main;
import com.kry.brickgame.games.Game;
import com.kry.brickgame.games.GameConsts.KeyPressed;
import com.kry.brickgame.games.SplashScreen;

public class Window extends JFrame {
	private static final long serialVersionUID = 3466619047314091863L;

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Color bgColor = new Color(109, 119, 92);
		Color bgColor = new Color(136, 153, 107);

		// frame.setResizable(false);
		 setUndecorated(true);

		setBounds(0, 0, 360, 640);

		// place window in the center of the screen
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		addWindowListener(new WindowListener());

		final GameDrawPanel drawPanel = new GameDrawPanel();
		drawPanel.setBackground(bgColor);
		// drawPanel.setOpaque(true);
		//frame.getContentPane().add(drawPanel);
		setContentPane(drawPanel);

		Game.addGameListener(drawPanel);
		drawPanel.setLayout(new MigLayout("", "[][][][grow][][]", "[][grow][][][][]"));

		JButton btnMinimize = ButtonsFactory.getMinimizeButton();
		drawPanel.add(btnMinimize, "cell 4 0,alignx right");

		JButton btnClose = ButtonsFactory.getCloseButton();//ButtonsFactory.getButton(KeyPressed.KeyOnOff);
		//btnClose.setText("X");

		drawPanel.add(btnClose, "cell 5 0,alignx right");

		drawPanel.add(ButtonsFactory.getButton(KeyPressed.KeyUp), "cell 1 2");
		drawPanel.add(ButtonsFactory.getButton(KeyPressed.KeyLeft), "cell 0 3");
		drawPanel
				.add(ButtonsFactory.getButton(KeyPressed.KeyRight), "cell 2 3");
		drawPanel.add(ButtonsFactory.getButton(KeyPressed.KeyDown), "cell 1 4");
		drawPanel.add(ButtonsFactory.getButton(KeyPressed.KeyRotate),
				"cell 4 3");

		addKeyListener(Main.gameKeyAdapter);

		//MouseListener mouseListener = new MouseListener();

		//frame.addMouseListener(mouseListener);
		//frame.addMouseMotionListener(mouseListener);

		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(
						new ButtonsFactory.ButtonsKeyEventDispatcher());

		setTitle("Brick Game");
		Game game = GameLoader.loadGame();
		if (game == null)
			game = new SplashScreen();
		Main.setGame(game);
	}

	private class WindowListener extends WindowAdapter {
		@Override
		public void windowIconified(WindowEvent e) {
			Main.getGame().saveState();
		}

		@Override
		public void windowClosing(WindowEvent e) {
			Main.getGame().keyPressed(KeyPressed.KeyOnOff);
		}

	}

}
