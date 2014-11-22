package com.kry.brickgame.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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

		/*
		 * frame.setBounds(0, 0, Drawer.SQUARE_SIZE * (10 + 6) + Drawer.SQUARE_SIZE,
		 * Drawer.SQUARE_SIZE * 20 + Drawer.SQUARE_SIZE);
		 */
		frame.setBounds(0, 0, 360, 640);

		// place window in the center of the screen
		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		final GameDrawPanel drawPanel = new GameDrawPanel();
		drawPanel.setBackground(bgColor);
		// drawPanel.setOpaque(true);
		frame.getContentPane().add(drawPanel);

		Game.addGameListener(drawPanel);
		drawPanel.setLayout(new MigLayout("", "[][][][360px][][]",
				"[][480px][][][][]"));

		JButton btnMinimize = new JButton("_");
		btnMinimize.setFocusable(false);
		drawPanel.add(btnMinimize, "cell 4 0,alignx right");

		ButtonMouseListener btnMouseListener = new ButtonMouseListener();
		
		JButton btnClose = new JButton("X");
		btnClose.setActionCommand("KeyOnOff");
		btnClose.setFocusable(false);
		btnClose.addMouseListener(btnMouseListener);
		
		
		drawPanel.add(btnClose, "cell 5 0,alignx right");

		ActionListener btnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				ButtonModel btnMdl = btn.getModel();
				switch (btn.getText()) {
				case "U":
					if (btnMdl.isArmed())
						Main.getGame().keyPressed(KeyPressed.KeyUp);
					else
						Main.getGame().keyReleased(KeyPressed.KeyUp);
					break;
				case "L":
					Main.getGame().keyPressed(KeyPressed.KeyLeft);
					break;
				case "R":
					Main.getGame().keyPressed(KeyPressed.KeyRight);
					break;
				case "D":
					if (btnMdl.isArmed())
						Main.getGame().keyPressed(KeyPressed.KeyDown);
					else
						Main.getGame().keyReleased(KeyPressed.KeyDown);
					break;
				case "Rotate":
					Main.getGame().keyPressed(KeyPressed.KeyRotate);
					break;
				default:
					break;
				}

			}
		};


		JButton btnU = new JButton("U");
		btnU.setActionCommand("KeyUp");
		btnU.setFocusable(false);
		btnU.addActionListener(btnListener);
		btnU.addMouseListener(btnMouseListener);

		JButton btnL = new JButton("L");
		btnL.setActionCommand("KeyLeft");
		btnL.setFocusable(false);
		// btnL.addActionListener(btnListener);
		btnL.addMouseListener(btnMouseListener);

		JButton btnR = new JButton("R");
		btnR.setActionCommand("KeyRight");
		btnR.setFocusable(false);
		// btnR.addActionListener(btnListener);
		btnR.addMouseListener(btnMouseListener);

		final JButton btnD = new JButton("D");
		btnD.setActionCommand("KeyDown");
		btnD.setFocusable(false);
		// btnD.addActionListener(btnListener);
		btnD.addMouseListener(btnMouseListener);

		JButton btnRotate = new JButton("Rotate");
		btnRotate.setActionCommand("KeyRotate");
		btnRotate.setFocusable(false);
		// btnRotate.addActionListener(btnListener);
		btnRotate.addMouseListener(btnMouseListener);

		drawPanel.add(btnU, "cell 1 2");
		drawPanel.add(btnL, "cell 0 3");
		drawPanel.add(btnR, "cell 2 3");
		drawPanel.add(btnD, "cell 1 4");
		drawPanel.add(btnRotate, "cell 4 3");

		frame.addKeyListener(Main.gameKeyAdapter);

		MouseListener mouseListener = new MouseListener();

		frame.addMouseListener(mouseListener);
		frame.addMouseMotionListener(mouseListener);

		new Timer("BlinkingSquares", true).schedule(new TimerTask() {
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						drawPanel.blinkingSquares();
					}
				});
			}
		}, 0, 10);
		new Timer("BlinkingPause", true).schedule(new TimerTask() {
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						drawPanel.blinkingPauseIcon();
					}
				});
			}
		}, 0, 500);

		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						ButtonModel btnMdl;
						if (e.getID() == Event.KEY_PRESS) {
							if (e.getKeyCode() == KeyEvent.VK_DOWN) {
								btnMdl = btnD.getModel();
								btnMdl.setArmed(true);
								btnMdl.setPressed(true);
							}
						} else if (e.getID() == Event.KEY_RELEASE) {
							if (e.getKeyCode() == KeyEvent.VK_DOWN) {
								btnMdl = btnD.getModel();
								btnMdl.setPressed(false);
								btnMdl.setArmed(false);
							}
						}
						return false;
					}
				});

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

	private class ButtonMouseListener extends MouseAdapter {
		private Set<KeyPressed> keyPressed;

		public ButtonMouseListener() {
			keyPressed = new HashSet<>();

			new Timer("KeyPressed", true).schedule(new TimerTask() {
				@Override
				public void run() {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							for (KeyPressed key : keyPressed) {
								Main.getGame().keyPressed(key);
							}
							;
						}
					});
				}
			}, 0, 70);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			final JButton btn = (JButton) e.getSource();
			keyPressed.add(KeyPressed.valueOf(btn.getActionCommand()));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			JButton btn = (JButton) e.getSource();
			KeyPressed key =KeyPressed.valueOf(btn.getActionCommand());
			keyPressed.remove(key);
			Main.getGame().keyReleased(key);
			
		}
	}

}
