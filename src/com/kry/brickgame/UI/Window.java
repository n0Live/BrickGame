package com.kry.brickgame.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

	public JFrame frame;

	private int x;
	private int y;

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

		frame = new JFrame();
		//frame.setResizable(false);
		//frame.setUndecorated(true);

		frame.setBounds(0, 0, 360, 640);

		// place window in the center of the screen
		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		frame.addWindowListener(new WindowListener());

		final GameDrawPanel drawPanel = new GameDrawPanel();
		drawPanel.setBackground(bgColor);
		// drawPanel.setOpaque(true);
		frame.getContentPane().add(drawPanel);

		Game.addGameListener(drawPanel);
		drawPanel.setLayout(new MigLayout("", "[][][][360px][][]",
				"[][480px][][][][]"));

		JButton btnMinimize = new JButton("_");
		btnMinimize.setFocusable(false);
		btnMinimize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setState(ICONIFIED);
			}
		});
		drawPanel.add(btnMinimize, "cell 4 0,alignx right");
		
		JButton btnClose = ButtonsFactory.getButton(KeyPressed.KeyOnOff);
		btnClose.setText("X");
		
		drawPanel.add(btnClose, "cell 5 0,alignx right");

		drawPanel.add(ButtonsFactory.getButton(KeyPressed.KeyUp), "cell 1 2");
		drawPanel.add(ButtonsFactory.getButton(KeyPressed.KeyLeft), "cell 0 3");
		drawPanel.add(ButtonsFactory.getButton(KeyPressed.KeyRight), "cell 2 3");
		drawPanel.add(ButtonsFactory.getButton(KeyPressed.KeyDown), "cell 1 4");
		drawPanel.add(ButtonsFactory.getButton(KeyPressed.KeyRotate), "cell 4 3");

		frame.addKeyListener(Main.gameKeyAdapter);

		MouseListener mouseListener = new MouseListener();

		frame.addMouseListener(mouseListener);
		frame.addMouseMotionListener(mouseListener);

		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new ButtonsFactory.ButtonsKeyEventDispatcher());

		setTitle("Brick Game");
		Game game = GameLoader.loadGame();
		if (game == null)
			game = new SplashScreen();
		Main.setGame(game);
	}

	private class MouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			x = e.getX();
			y = e.getY();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			frame.setLocation(e.getX() + frame.getX() - x,
					e.getY() + frame.getY() - y);
			frame.repaint();
		}
	}
	
	private class WindowListener extends WindowAdapter{
		@Override
	    public void windowIconified(WindowEvent e) {
			Main.getGame().saveState();
		}

	}

}
