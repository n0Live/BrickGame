package com.kry.brickgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Window extends JFrame {
	private static final long serialVersionUID = 3466619047314091863L;

	JFrame frame;

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
		Color bgColor = new Color(109, 119, 92);

		frame = new JFrame();
		frame.setBounds(100, 100, 430, 565);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		final Draw drawPanel = new Draw();
		drawPanel.setBackground(bgColor);
		frame.getContentPane().add(drawPanel);

		Game.addGameListener(drawPanel);
		frame.addKeyListener(Main.gameKeyAdapter);

		Timer timer = new Timer("Blinking", true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						drawPanel.blinking();
					}
				});
			}
		}, 0, 10);

		setTitle("Brick Game");
		Main.setGame(Main.gameSelector);
	}

}
