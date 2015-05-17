package com.kry.brickgame;

import java.awt.EventQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kry.brickgame.UI.GameKeyAdapter;
import com.kry.brickgame.UI.Window;
import com.kry.brickgame.games.Game;

/**
 * The main class to launching
 * 
 * @author noLive
 * @since 01.08.2014
 */
public final class Main {
	
	private static class MainGameLoop implements Runnable {
		public MainGameLoop() {
		}
		
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					setGame(getGame().call());
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		}
	}
	
	/**
	 * The current game
	 */
	private static volatile Game game;
	/**
	 * The current thread of the game
	 */
	private static ExecutorService gameThread;
	
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
	
	static void setGame(Game game) {
		Main.game = game;
	}
	
	public static void setGameLoop(final Game game) {
		gameThread = Executors.newSingleThreadExecutor();
		setGame(game);
		gameThread.execute(new MainGameLoop());
	}
	
}
