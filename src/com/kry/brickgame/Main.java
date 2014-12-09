package com.kry.brickgame;

import java.awt.EventQueue;

import com.kry.brickgame.UI.GameKeyAdapter;
import com.kry.brickgame.UI.Window;
import com.kry.brickgame.games.Game;
import com.kry.brickgame.games.GameSelector;

/**
 * The main class to launching
 * 
 * @author noLive
 * @since 01.08.2014
 */
public final class Main {

	/**
	 * The current game
	 */
	private static Game game;
	/**
	 * The current thread of the game
	 */
	private static Thread gameThread;
	/**
	 * The selection screen of a game
	 */
	public static final GameSelector gameSelector = new GameSelector();
	/**
	 * Observer to {@code KeyEvent}
	 */
	public static final GameKeyAdapter gameKeyAdapter = new GameKeyAdapter();

	public static Game getGame() {
		return game;
	}

	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Window window = new Window();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void setGame(Game game) {
		Main.game = game;
		gameThread = new Thread(game, "TGameThread");
		gameThread.start();
	}

}
